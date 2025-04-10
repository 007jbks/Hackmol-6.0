from fastapi import FastAPI,HTTPException,Depends,UploadFile,File,BackgroundTasks
from pydantic import BaseModel
from typing import List,Annotated
import models
from database import engine,sessionLocal,Base
from sqlalchemy.orm import Session
#for the token system
from auth import create_access_token,verify_access_token
from fastapi import Form
from sqlalchemy import case, func
from typing import Optional
##################################################################
#For email part
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
#for gemini api
from gemini_api import image_url_to_base64
from gemini_api import describe_pet_traits_from_image
from gemini_api import get_dist
from gemini_api import chat
import requests
import json
import base64
import logging



def send_email(to_email, subject, body):
    sender_email = "jon00doe00297@gmail.com"
    sender_password = "bdaykyslqvzdulsn"  # Use an App Password, not your regular password!

    msg = MIMEMultipart()
    msg["From"] = sender_email
    msg["To"] = to_email
    msg["Subject"] = subject
    
    msg.attach(MIMEText(body, "plain"))

    try:
        server = smtplib.SMTP("smtp.gmail.com", 587)
        server.starttls()
        server.login(sender_email, sender_password)
        server.send_message(msg)
        server.quit()
        print("Email sent successfully!")
    except Exception as e:
        print(f"Error sending email: {e}")


#################################################################
#for image storage
import cloudinary
import cloudinary.uploader
from cloudinary.utils import cloudinary_url
import smtplib
smtplib.SMTP.debuglevel = 1  # Add this at the top of your file


# Configuration       
cloudinary.config( 
    cloud_name = "dhc8pzsgc", 
    api_key = "329559697711525", 
    api_secret = "deemYEvqxgWItqd9CZIbQIm7WPo", 
    secure=True
)
#################################################################

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "Hello, World!"}

#Base.metadata.drop_all(engine) 
#models.Base.metadata.drop_all(bind=engine)
models.Base.metadata.create_all(bind=engine)

class UserCreate(BaseModel):
    username : str
    password : str
    email : str
    registration_number : Optional[int] = None
    address : str

class UserLogin(BaseModel):
    email : str
    password : str

class Filters(BaseModel):
    species:str


def get_db():
    db = sessionLocal()
    try:
        yield db
    finally:
        db.close()

db_dependencies = Annotated[Session,Depends(get_db)]

######################### Authentication system ############################

@app.post("/signup")
def signup(user:UserCreate,db:db_dependencies):
    u = db.query(models.User).filter(models.User.username==user.username).first()
    
    if not u:
        new_user = models.User(username=user.username,hashed_password=user.password,email=user.email,ngo_registration_number = user.registration_number,
        is_ngo=bool(user.registration_number),address=user.address)
        new_user.set_password(user.password)
        db.add(new_user)
        db.commit()
        db.refresh(new_user)
        access_token = create_access_token(data={"sub": new_user.username})
        return {"access_token": access_token, "token_type": "bearer"}
    else:
        raise HTTPException(status_code=400,detail="Username taken")

@app.get("/users")
def get_user(db:db_dependencies):
    return db.query(models.User).all()

@app.get("/pets")
def get_pet(db:db_dependencies):
    return db.query(models.Pet).all()

@app.post("/login")
def login(user:UserLogin,db:db_dependencies):
    new_user = db.query(models.User).filter(models.User.email==user.email).first()
    if new_user and new_user.verify_password(user.password) :
        access_token = create_access_token(data={"sub": new_user.username})
        return {"access_token": access_token, "token_type": "bearer"}
    else:
        raise HTTPException(status_code=400,detail="Incorrect password or username.Try Again")
    

##################################################################



############################# MAIN LOGIC OF APP STARTS HERE ###################


@app.post("/sell")
def sell_pet(
    name: str = Form(...),
    species: str = Form(...),
    token: str = Form(...),
    file: UploadFile = File(...),
    breed: str = Form(...),
    gender: str = Form(...),
    weight: int = Form(...),
    age: int = Form(...),
    health: str = Form(...),
    description: str = Form(...),
    color_markings: str = Form(...),
    db: Session = Depends(get_db)
):
    # Verify user from token
    username = verify_access_token(token)
    seller = db.query(models.User).filter(models.User.username == username).first()
    if not seller:
        raise HTTPException(status_code=400, detail="User doesn't exist.")

    # Upload image to Cloudinary
    result = cloudinary.uploader.upload(file.file, folder="pets_images")
    image_url = result.get("secure_url")
    if not image_url:
        raise HTTPException(status_code=500, detail="Failed to upload image.")

    # Get traits from Gemini
    traits_response = describe_pet_traits_from_image(image_url)
    try:
        traits = traits_response  # Ensure it's valid JSON
    except json.JSONDecodeError:
        raise HTTPException(status_code=500, detail="Failed to parse traits JSON.")

    # Check for existing pet
    existing_pet = db.query(models.Pet).filter(
        models.Pet.name == name,
        models.Pet.seller_id == seller.id
    ).first()
    if existing_pet:
        raise HTTPException(status_code=400, detail="You already listed this pet.")

    # Create and save pet
    pet = models.Pet(
        name=name,
        species=species,
        seller_id=seller.id,
        image=image_url,
        age=age,
        weight=weight,
        health=health,
        description=description,
        breed=breed,
        color_markings=color_markings,
        gender=gender,
        traits=traits
    )
    db.add(pet)
    db.commit()
    db.refresh(pet)

    return {
        "message": f"{name} listed successfully.",
        "traits": traits
    }




from  gemini_api import get_dist  # Assuming your get_dist() function is here



# Setup logger
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

@app.post("/interest")
def search_pet(
    species: str = Form(...),
    token: str = Form(...),
    breed: str = Form(...),
    gender: str = Form(...),
    weight_range: str = Form(...),
    age_range: str = Form(...),
    color_markings: str = Form(...),
    db: Session = Depends(get_db)
):
    username = verify_access_token(token)
    poi = db.query(models.User).filter(models.User.username == username).first()
    if not poi:
        logger.warning(f"User '{username}' does not exist.")
        raise HTTPException(status_code=400, detail="user doesn't exist")

    user_address = poi.address
    logger.info(f"Searching pets for user '{username}' at address: {user_address}")

    try:
        min_weight, max_weight = map(int, weight_range.split('-')) 
        min_age, max_age = map(int, age_range.split('-'))
    except ValueError:
        logger.error(f"Invalid range format received: weight='{weight_range}', age='{age_range}'")
        return {"error": "Invalid range format. Use 'min-max'"}

    score_expr = (
        case((models.Pet.species == species, 1), else_=0) +
        case((models.Pet.breed == breed, 1), else_=0) +
        case((models.Pet.gender == gender, 1), else_=0) +
        case((models.Pet.color_markings.ilike(f"%{color_markings}%"), 1), else_=0) +
        case((models.Pet.weight >= min_weight, 1), else_=0) +
        case((models.Pet.weight <= max_weight, 1), else_=0) +
        case((models.Pet.age >= min_age, 1), else_=0) +
        case((models.Pet.age <= max_age, 1), else_=0)
    ).label("match_score")

    pets_with_scores = db.query(models.Pet, score_expr).filter(score_expr >= 3).all()
    logger.info(f"Found {len(pets_with_scores)} pets with score >= 3.")

    nearby_matches = []

    for pet, score in pets_with_scores:
        owner = db.query(models.User).filter(models.User.id == pet.seller_id).first()
        if not owner or not owner.address:
            logger.warning(f"Pet {pet.id} has no owner or owner has no address.")
            continue

        logger.debug(f"Comparing pet address '{owner.address}' with user address '{user_address}'")

        try:
            if get_dist(user_address, owner.address): 
                logger.info(f"Pet {pet.id} matched on distance with user '{username}'.")
                nearby_matches.append({
                    "id": pet.id,
                    "name": pet.name,
                    "species": pet.species,
                    "breed": pet.breed,
                    "gender": pet.gender,
                    "weight": pet.weight,
                    "age": pet.age,
                    "color_markings": pet.color_markings,
                    "health": pet.health,
                    "description": pet.description,
                    "image_url": pet.image
                })
        except Exception as e:
            logger.error(f"Error calculating distance for pet {pet.id}: {e}")
            continue

    logger.info(f"Returning {len(nearby_matches)} nearby pet matches for user '{username}'")
    return {"matches": nearby_matches}




@app.post("/buy")
def buy_pet(
    id: int = Form(...),
    token: str = Form(...),
    background_tasks: BackgroundTasks = BackgroundTasks(),
    db: Session = Depends(get_db)
):
    pet = db.query(models.Pet).filter(models.Pet.id == id).first()
    if not pet:
        raise HTTPException(status_code=404, detail="Pet not found")

    username = verify_access_token(token)
    buyer = db.query(models.User).filter(models.User.username == username).first()
    seller = db.query(models.User).filter(models.User.id == pet.seller_id).first()

    if not buyer or not seller:
        raise HTTPException(status_code=400, detail="Invalid buyer or seller")

    if seller.id == buyer.id:
        raise HTTPException(status_code=400, detail="You cannot buy your own pet")

    # Update DB
    pet.potential_owner_id = buyer.id
    db.commit()
   
    # Prepare and send email
    subject = f"Interest in your pet: {pet.name}"
    body = (
        f"Hello {seller.username},\n\n"
        f"{buyer.username} is interested in adopting your pet '{pet.name}' ({pet.species}, {pet.breed}).\n"
        f"Contact Info: {buyer.email if hasattr(buyer, 'email') else 'N/A'}\n\n"
        f"Please log in to view their profile and respond to the request.\n\n"
        f"- Pet Adoption Platform"
    )
    print(f"📧 Sending email to: {seller.email}")

    background_tasks.add_task(send_email, seller.email, subject, body)

    return {"message": "Request successfully sent and seller notified."}


@app.post("/facial_match")
def facial_match(file: UploadFile = File(...), db: Session = Depends(get_db),
                 token : str = Form(...)
                 ):
    username = verify_access_token(token)
    user = db.query(models.User).filter(models.User.username==username).first()
    result = cloudinary.uploader.upload(file.file, folder="pets_images")
    image_url = result.get("secure_url")
    if not image_url:
        raise HTTPException(status_code=500, detail="Failed to upload image.")

    # Get traits from Gemini
    traits_response = describe_pet_traits_from_image(image_url)
    user_traits = json.loads(traits_response) if isinstance(traits_response, str) else traits_response

    pets = db.query(models.Pet).all()
    best_matches = []

    for pet in pets:
        try:
            pet_traits = json.loads(pet.traits) if isinstance(pet.traits, str) else pet.traits
        except:
            continue

        if not pet_traits:
            continue
        if pet.seller_id == user.id:
            continue

        matches = sum(
            1 for key in user_traits if key in pet_traits and user_traits[key] == pet_traits[key]
        )
        best_matches.append((matches, pet))

    best_matches.sort(reverse=True, key=lambda x: x[0])

    # If there are matches, return the best one
    if best_matches and best_matches[0][0] > 0:
        top_match = best_matches[0][1]
    else:
        # Emergency fallback: return a random pet (or customize this logic)
        top_match = pets[0] if pets else None

    if not top_match:
        raise HTTPException(status_code=404, detail="No pets available.")

    return {
        "matched_pet": {
            "name": top_match.name,
            "id":top_match.id,
            "image": top_match.image,
            "traits": top_match.traits,
            "description": top_match.description,
            # add more fields if needed
        }
    }



###########################################################################
#This will be the updates section

#Firstly transfer the ownership from the prev owner to new owner
@app.post("/transfer")
def transfer(
    token: str = Form(...),
    new_owner_email_id: str = Form(...),
    pet_name: str = Form(...),
    db: Session = Depends(get_db)
):
    username = verify_access_token(token)
    user = db.query(models.User).filter(models.User.username == username).first()
    if not user:
        raise HTTPException(status_code=404, detail="Invalid user")
    pet = db.query(models.Pet).filter(models.Pet.name == pet_name).first()
    if not pet:
        raise HTTPException(status_code=404, detail="Pet not found")
    if pet.seller_id != user.id:
        raise HTTPException(status_code=403, detail="You are not the original seller")
    new_owner = db.query(models.User).filter(models.User.email == new_owner_email_id).first()
    if not new_owner:
        raise HTTPException(status_code=404, detail="New owner not found")
    pet.owner_id = new_owner.id
    db.commit()
    return {"message": f"{new_owner.username} now owns {pet.name}"}

############################ Updating the pet's status ######################

@app.post("/update")
def update():
    pass

################New send mail logic for this######################

def send_email(to_email: str, subject: str, body: str,f:str):
    sender_email = "jon00doe00297@gmail.com"
    sender_password = "bdaykyslqvzdulsn"  
    if not to_email or '@' not in to_email:
        print(f"❌ Skipping invalid email: {to_email}")
        return
    
    try:
        msg = MIMEText(body)
        msg["Subject"] = subject
        msg["From"] = f
        msg["To"] = to_email

        with smtplib.SMTP("smtp.gmail.com", 587) as server:
            server.starttls()
            server.login(sender_email, sender_password)
            server.send_message(msg)

        print(f"✅ Email sent to: {to_email}")

    except Exception as e:
        print(f"🚨 Failed to send email to {to_email}: {str(e)}")

############################### Alerting All NGOs ##########################

@app.post("/report")
def report(
    background_tasks: BackgroundTasks,
    species: str = Form(...),
    breed: str = Form(...),
    gender: str = Form(...),
    markings: str = Form(...),
    age: int = Form(...),
    features: str = Form(...),
    health: str = Form(...),
    behaviour: str = Form(...),
    location: str = Form(...),
    token: str = Form(...),
    db: Session = Depends(get_db)
):
    username = verify_access_token(token)
    user = db.query(models.User).filter(models.User.username == username).first()
    
    if not user:
        raise HTTPException(status_code=400, detail="Invalid token")

    # Get all NGOs or users
    recipients = db.query(models.User).filter(models.User.is_ngo == True).all()
    

    for recipient in recipients:
        subject = f"New Pet Report Alert - {species.title()} ({breed})"
        body = (
            f"Hello {recipient.username},\n\n"
            f"A new pet has been reported:\n"
            f"Species: {species}\nBreed: {breed}\nGender: {gender}\nAge: {age}\n"
            f"Health: {health}\nBehaviour: {behaviour}\nMarkings: {markings}\n"
            f"Features: {features}\nLocation: {location}\n\n"
            f"Reported by: {user.username} ({user.email})\n"
            f"Please respond if you can help.\n\n"
            f"- Pet Alert System"
        )

        print(f"📧 Sending email to: {recipient.email}")
        background_tasks.add_task(send_email, recipient.email, subject, body,user.email)
        print(f"message sent")

    return {"message": "Report submitted and notifications sent."}



######################## CHATBOT ##################################

@app.post("/sendChat")
def send_chat(query:str,db:db_dependencies):
    
    response = chat(query)
    return {"bot says:":response}


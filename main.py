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
##################################################################
#For email part
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

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

# class PetCreate(BaseModel):
#     name : str
#     species :str
#     token: str
#     age :int
#     breed :str
#     gender :str
#     weight : str
#     health:str
#     description:str
    
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
        new_user = models.User(username=user.username,hashed_password=user.password,email=user.email)
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

@app.post("/deleteuser")
def delete_user(id:int,db:db_dependencies):
    user = db.query(models.User).filter(models.User.id==id).first()
    if not user:
        raise HTTPException(status_code=404,detail = "User not found")
    db.delete(user)
    db.commit()
    return {"message":"User deleted successfully"}

@app.post("/login")
def login(user:UserLogin,db:db_dependencies):
    new_user = db.query(models.User).filter(models.User.email==user.email).first()
    if new_user and new_user.verify_password(user.password) :
        access_token = create_access_token(data={"sub": new_user.username})
        return {"access_token": access_token, "token_type": "bearer"}
    else:
        raise HTTPException(status_code=400,detail="Incorrect password or username.Try Again")
    

############################# MAIN LOGIC OF APP STARTS HERE ###################

#This is for selling of a pet:


@app.post("/sell")
def sell_pet(
    name: str = Form(...),
    species: str = Form(...),
    token: str = Form(...),
    file: UploadFile = File(...),
    breed : str = Form(...),
    gender : str = Form(...),
    weight : int = Form(...),
    age : int = Form(...),
    health : str = Form(...),
    description : str = Form(...),
    color_markings : str = Form(...),
    db: Session = Depends(get_db)
):
    username = verify_access_token(token)
    seller = db.query(models.User).filter(models.User.username == username).first()
    if not seller:
        raise HTTPException(status_code=400,detail="user doesn't exist")
    result = cloudinary.uploader.upload(file.file, folder="pets_images")
    image_url = result.get("secure_url")

    if not image_url:
        raise HTTPException(status_code=500, detail="Failed to get image URL")

    existing_pet = db.query(models.Pet).filter(
        models.Pet.name == name,
        models.Pet.seller_id == seller.id
    ).first()

    if existing_pet:
        raise HTTPException(status_code=400, detail="You already listed this pet.")

    pet = models.Pet(name=name, 
                     species=species,
                       seller_id=seller.id,
                         image=image_url,
                         age=age,
                         weight=weight,
                         health=health,
                         description=description,
                         breed=breed,
                         color_markings = color_markings,
                         gender=gender
                         )
    db.add(pet)
    db.commit()
    db.refresh(pet)

    return {"message": f"{name} added successfully"}




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
        raise HTTPException(status_code=400, detail="user doesn't exist")

    try:
        min_weight, max_weight = map(int, weight_range.split('-')) 
        min_age, max_age = map(int, age_range.split('-'))
    except ValueError:
        return {"error": "Invalid range format. Use 'min-max'"}

    # Partial matching using match score
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

    matched_pets = db.query(models.Pet, score_expr).having(score_expr >= 4).all()

    return {
        "matches": [
            {
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
            }
            for pet, _ in matched_pets
        ]
    }


# @app.get("/pets")
# def get_pets(db:db_dependencies):
#     return db.query(models.Pet).all()

# @app.post("/interest")
# def search_pet(
#     species: str = Form(...),
#     token: str = Form(...),
#     breed : str = Form(...),
#     gender : str = Form(...),
#     weight_range : str = Form(...),
#     age_range : str = Form(...),
#     color_markings : str = Form(...),
#     db: Session = Depends(get_db)
# ):
#     username = verify_access_token(token)
#     poi = db.query(models.User).filter(models.User.username == username).first()
#     if not poi:
#         raise HTTPException(status_code=400,detail="user doesn't exist")
#     try:
#         min_weight, max_weight = map(int, weight_range.split('-')) 
#         min_age,max_age = map(int,age_range.split('-'))
#     except ValueError:
#         return {"error": "Invalid range format. Use 'min-max'"}
#     #Matching Algorithm
#     pets = db.query(models.Pet).filter(
#         models.Pet.species == species,
#         models.Pet.breed == breed,
#         models.Pet.gender == gender,
#         models.Pet.color_markings.ilike(f"%{color_markings}%"),
#         models.Pet.weight >= min_weight,
#         models.Pet.weight <= max_weight,
#         models.Pet.age >= min_age,
#         models.Pet.age <= max_age
#     ).all()

#     return {
#         "matches": [
#             {
#                 "id": pet.id,
#                 "name": pet.name,
#                 "species": pet.species,
#                 "breed": pet.breed,
#                 "gender": pet.gender,
#                 "weight": pet.weight,
#                 "age": pet.age,
#                 "color_markings": pet.color_markings,
#                 "health":pet.health,
#                 "description":pet.description,
#                 "image_url":pet.image
#             }
#             for pet in pets
#         ]
#     }


# @app.post("/buy")
# def buy_pet(
#     id: int = Form(...),
#     token :str = Form(...),
#     db: Session = Depends(get_db)
# ):
#     pet = db.query(models.Pet).filter(models.Pet.id==id).first()
#     username = verify_access_token(token)
#     buyer = db.query(models.User).filter(models.User.username==username).first()
#     seller = db.query(models.User).filter(models.User.id==pet.seller_id).first()
#     if seller.id == buyer.id:
#         raise HTTPException(status_code=400,detail="You cannot buy your own pet")
#     pet.potential_owner_id = buyer.id
#     return {"message":"Request Successfully sent"}


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


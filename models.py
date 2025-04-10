from sqlalchemy import Column, Integer, String,ForeignKey , JSON, Boolean
from database import Base
from sqlalchemy.orm import relationship
import bcrypt

class User(Base):
    __tablename__ = "user"
    
    id = Column(Integer, primary_key=True, index=True)
    username = Column(String, index=True)  
    hashed_password = Column(String) 
    ngo_registration_number = Column(Integer, index=True,nullable=True)
    is_ngo = Column(Boolean,index=True,nullable=True)
    email = Column(String,index=True)
    address = Column(String,index=True)
    contact = Column(String,index=True)

    def set_password(self, password: str):
        salt = bcrypt.gensalt()
        self.hashed_password = bcrypt.hashpw(password.encode('utf-8'), salt).decode('utf-8')

    def verify_password(self, password: str) -> bool:
        return bcrypt.checkpw(password.encode('utf-8'), self.hashed_password.encode('utf-8'))


class Pet(Base):
    __tablename__ = "pets"
    id = Column(Integer,primary_key=True,index=True)
    name = Column(String,index=True)
    species = Column(String,index=True)
    image = Column(String,index=True)
    age = Column(Integer,index=True)
    breed = Column(String,index=True)
    gender = Column(String,index=True)
    weight = Column(Integer,index=True)
    health = Column(String,index=True)
    description = Column(String,index=True,nullable=True)
    color_markings = Column(String,index=True)
    traits = Column(JSON)
    seller_id = Column(Integer, ForeignKey("user.id"), index=True, nullable=False)
    owner_id = Column(Integer, ForeignKey("user.id"), index=True, nullable=True)  
    potential_owner_id = Column(Integer, ForeignKey("user.id"),index=True)
    update_image_url = Column(String,index=True)
    update_date = Column(String,index=True)
    


class Abandoned(Base):
    __tablename__ = "abandoned"
    species = Column(String,index=True,primary_key=True)
    breed = Column(String,index=True,nullable=True)
    gender = Column(String,index=True)
    markings = Column(String,index=True)
    age = Column(Integer,index=True,nullable=True)
    features = Column(String,index=True)
    health = Column(String,index=True)
    behaviour = Column(String,index=True)
    location = Column(String,index=True)
    other = Column(String,index=True,nullable=True)




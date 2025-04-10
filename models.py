from sqlalchemy import Column, Integer, String, ForeignKey
from database import Base
from sqlalchemy.orm import relationship
import bcrypt

class User(Base):
    __tablename__ = "user"
    
    id = Column(Integer, primary_key=True, index=True)
    username = Column(String, unique=True, index=True)  
    hashed_password = Column(String) 
    email = Column(String,index=True)

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

    seller_id = Column(Integer, ForeignKey("user.id"), index=True, nullable=False)
    owner_id = Column(Integer, ForeignKey("user.id"), index=True, nullable=True)  
    potential_owner_id = Column(Integer, ForeignKey("user.id"),index=True)


class NGO(Base):
    __tablename__ = "ngo" 
    id = Column(Integer, primary_key=True, index=True)
    username = Column(String, unique=True, index=True)  
    ngo_registration_number = Column(String, unique=True, index=True)
    hashed_password = Column(String) 
    email = Column(String, index=True)

    def set_password(self, password: str):
        salt = bcrypt.gensalt()
        self.hashed_password = bcrypt.hashpw(password.encode('utf-8'), salt).decode('utf-8')

    def verify_password(self, password: str) -> bool:
        return bcrypt.checkpw(password.encode('utf-8'), self.hashed_password.encode('utf-8'))
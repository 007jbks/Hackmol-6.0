#NGO registration
@app.post("/ngo/signup")
def register_ngo(
    username: str = Form(...),
    ngo_registration_number: int = Form(...),
    password: str = Form(...),
    email: str = Form(...),
    db: Session = Depends(get_db)
):
    # Check if the ID, username, registration number, or email already exists
    existing_ngo = db.query(models.NGO).filter(
        (models.NGO.id == id) |
        (models.NGO.username == username) |
        (models.NGO.ngo_registration_number == ngo_registration_number) |
        (models.NGO.email == email)
    ).first()

    if existing_ngo:
        raise HTTPException(status_code=400, detail="NGO with given ID, username, registration number, or email already exists.")

    # Create the NGO instance
    new_ngo = models.NGO(
        id=id,
        username=username,
        ngo_registration_number=ngo_registration_number,
        email=email
    )
    new_ngo.set_password(password)  

    db.add(new_ngo)
    db.commit()
    db.refresh(new_ngo)
    access_token = create_access_token(data={"sub": new_ngo.username})

    return {
        "message": "NGO registered successfully!",
        "ngo_id": new_ngo.id,
        "username": new_ngo.username,
        "access token":access_token,
    }
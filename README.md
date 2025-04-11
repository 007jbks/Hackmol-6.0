# Hackmol-6.0

# 🐾 PETS - Pet Adoption & Reporting Platform (Backend)

Welcome to the backend API for **PETS**, a platform that allows users to adopt pets, report lost/found animals, match pets by facial traits, and much more. This backend is built with **FastAPI** and uses **SQLAlchemy**, **JWT Authentication**, and supports emailing functionalities.

---

## 🚀 Tech Stack

- **FastAPI** - Web framework for APIs
- **SQLAlchemy** - ORM for database interactions
- **SQLite / PostgreSQL / MySQL** - Database (configurable)
- **Cloudinary** - Image upload and hosting
- **JWT** - Token-based authentication
- **SMTP** - Email alerts to NGOs or sellers
- **BackgroundTasks** - For async email sending

---

## 📦 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/pets-backend.git
cd pets-backend
```

### 2. Create and Activate Virtual Environment

```bash
python3 -m venv .venv
source .venv/bin/activate  # On Windows: .venv\Scripts\activate
```

### 3. Install Dependencies

```bash
pip install -r requirements.txt
```

### 4. Set Up Environment Variables (if required)

- Set your Cloudinary and Gmail SMTP credentials.

### 5. Run the Server

```bash
uvicorn main:app --reload
```

---

## 🛣️ API Endpoints

### ✅ Default

| Method | Route | Description |
|--------|-------|-------------|
| GET | `/` | Test route - returns a welcome message |

---

### 👤 Authentication & User Management

| Method | Route | Description |
|--------|-------|-------------|
| POST | `/signup` | Register a new user |
| POST | `/login` | Login and receive access token |
| GET | `/users` | Get details of the current logged-in user (based on token) |

---

### 🐶 Pet Features

| Method | Route | Description |
|--------|-------|-------------|
| GET | `/pets` | Get list of available pets |
| POST | `/sell` | Post a pet for adoption |
| POST | `/interest` | Search for pets based on location and preferences |
| POST | `/buy` | Express interest in adopting a pet |
| POST | `/transfer` | Transfer pet ownership to a new user |
| POST | `/update` | Update pet care status |
| POST | `/facial_match` | Match pets based on facial traits using uploaded photo |

---

### 📢 Reporting & Communication

| Method | Route | Description |
|--------|-------|-------------|
| POST | `/report` | Report an abandoned pet to all registered NGOs |
| POST | `/sendChat` | Send a query to the AI chatbot built around gemini 2.0 flash to ease the user's experience with their pets|

---

## 📧 Email Functionality

- Sends email to seller when someone expresses interest in their pet.
- Sends broadcast emails to NGOs when a pet is reported.
- Uses Gmail SMTP with application-specific password.

## 🧠 Facial Matching Logic

- Uses a vision model (Gemini) to analyze uploaded images.
- Compares pet traits stored in the DB and returns the closest match.

---

## 🗃️ Database Models

- **User**: Stores user credentials, NGO flag, and contact info.
- **Pet**: Details about pets including traits, image, owner, etc.

> Managed using SQLAlchemy ORM.

---

## 🧪 Testing

You can test the API using:

- Swagger UI: `http://localhost:8000/docs`
- Postman or any API testing tool

---

## 📂 Project Structure

```bash
├── main.py                # FastAPI app
├── models.py              # SQLAlchemy models
├── database.py            # DB connection and session
├── auth.py                # JWT token handling
├── email_utils.py         # Email sending functions
├── pet_utils.py           # Matching, traits, etc.
├── requirements.txt
├── README.md
```

---

## 🙋 Contributing

Pull requests are welcome. For major changes, please open an issue first.

---

## 🔒 Security Notes

- All sensitive routes require JWT token.
- Email addresses are validated.
- Facial recognition results are only accessible via authentication.

---

## 🧾 License

This project is licensed under the [MIT License](LICENSE).

---

## ✨ Author

- 👨‍💻 Developed by [Kartik Sirohi](https://github.com/007jbks)
- 📧 Email: kartiksirohi383@gmail.com

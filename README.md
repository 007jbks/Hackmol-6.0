# Hackmol-6.0
# 🐾 PETS - Pet Adoption & Rehoming Platform

Pets is a Kotlin-powered pet adoption platform where users can adopt pets, report abandoned animals to NGOs, buy and sell pets, and even find pets that resemble them through visual search powered by Google's **Gemini AI**.

## 🌟 Features

- 🐶 **Adopt & Rehome Pets:** Browse a wide range of pets available for adoption or rehoming by individuals or NGOs.
- 📸 **Visual Search (AI-based):** Upload a photo of yourself to find pets that visually resemble you, thanks to Gemini AI's image-matching capabilities.
- 🐾 **Report Abandoned Animals:** Help protect animals by reporting lost or abandoned pets directly to local NGOs for fast action and care.
- 💰 **Buy & Sell Pets:** A secure marketplace to connect pet lovers and responsible breeders or pet owners.
- 👤 **User-Friendly Dashboard:** Sign up to create your profile, list pets, track adoptions, and more.
- 📍 **Geo-location Enabled:** Find pets and NGOs nearby using location-based services.

## 🧠 Powered By

- 💬 **Gemini AI** for visual similarity and personalized pet matching.
- 🧑‍💻 **Kotlin** for robust backend logic and seamless Android app development.


## 🚀 Getting Started

1. **Clone the Repository**

```bash
git clone https://github.com/your-username/pawconnect.git
cd pawconnect


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

## ✨ Authors

- 👨‍💻 Developed by [Kartik Sirohi(backend), Ayush Podar(frontend), Aayush Sardana(frontend) , Samaira Wahi(UI/UX)]
- 📧 Email: kartiksirohi383@gmail.com

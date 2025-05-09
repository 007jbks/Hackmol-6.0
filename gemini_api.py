import requests
import json
import base64
import re

API_KEY = API_KEY_HERE
URL = f"https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key={API_KEY}"

# Convert image URL to base64
def image_url_to_base64(image_url):
    response = requests.get(image_url)
    if response.status_code == 200:
        return base64.b64encode(response.content).decode('utf-8')
    else:
        raise Exception(f"Failed to fetch image from URL: {response.status_code}")

# Query Gemini and extract clean JSON trait dictionary
def describe_pet_traits_from_image(image_url):
    base64_image = image_url_to_base64(image_url)

    prompt = """Here is an image of a pet or person. Based ONLY on visible features, classify the following traits using the EXACT terms provided below. If any feature is unclear, guess the most likely option based on the image quality. Always return a full JSON object.

- Face shape: round, oval, triangular, square, long
- Eye size: big, small, average
- Nose shape: flat, pointy, broad, snub
- Ear type: floppy, pointed, hidden, upright
- Fur: fluffy, smooth, short, long

Output strictly in this JSON format (no explanation or extra text):
{
  "face_shape": "...",
  "eye_size": "...",
  "nose_shape": "...",
  "ear_type": "...",
  "fur": "..."
}

"""

    data = {
        "contents": [
            {
                "parts": [
                    {
                        "inlineData": {
                            "mimeType": "image/jpeg",
                            "data": base64_image
                        }
                    },
                    {
                        "text": prompt
                    }
                ]
            }
        ]
    }

    headers = {
        "Content-Type": "application/json"
    }

    response = requests.post(URL, headers=headers, data=json.dumps(data))

    if response.status_code == 200:
        try:
            result = response.json()
            text = result["candidates"][0]["content"]["parts"][0]["text"]

            # Extract the JSON block from text, even if inside triple backticks
            json_text = re.search(r"{.*}", text, re.DOTALL)
            if json_text:
                traits = json.loads(json_text.group())
                return traits
            else:
                raise Exception("No valid JSON object found in Gemini response.")
        except Exception as e:
            print("Error:", e)
            print("Raw response:", json.dumps(result, indent=2))
    else:
        print("Request failed with status:", response.status_code)
        print(response.text)


import requests

def get_coordinates(address):
    url = "https://api.opencagedata.com/geocode/v1/json"
    params = {
        "q": address,
        "key": "80abcd9793604bfb8f0f4769b4c8ecd0"
    }
    response = requests.get(url, params=params)
    data = response.json()
    if data['results']:
        lat = data['results'][0]['geometry']['lat']
        lng = data['results'][0]['geometry']['lng']
        return lat, lng
    return None, None

from math import radians, cos, sin, asin, sqrt


def haversine(lat1, lon1, lat2, lon2):
    R = 6371  # Radius of Earth in km

    dlat = radians(lat2 - lat1)
    dlon = radians(lon2 - lon1)

    a = sin(dlat/2)**2 + cos(radians(lat1)) * cos(radians(lat2)) * sin(dlon/2)**2
    c = 2 * asin(sqrt(a))
    return R * c


def get_dist(address1,address2):
    lat1,lon1 = get_coordinates(address1)
    lat2,lon2 = get_coordinates(address2)
    dist = haversine(lat1,lon1,lat2,lon2)
    if dist<=100:
        return True
    else:
        return False
    
print(get_dist("Delhi","Delhi"))




def chat(query,API_KEY=API_KEY,URL=URL,system_prompt="You are now a chatbot for this pet adoption site where you must help the user with whatever help he/she needs respond only in human like chatbot form"):
    

    data = {
        "contents": [{
            "parts": [{"text":system_prompt+query}]
        }]
    }

    headers = {
        "Content-Type": "application/json"
    }

    response = requests.post(URL, headers=headers, data=json.dumps(data))

    if response.status_code == 200:
        result = response.json()  
        
        try:
            response_text = result["candidates"][0]["content"]["parts"][0]["text"]
            return response_text
        except (KeyError, IndexError) as e:
            print("Error parsing response:", e)
            print("Full response:", result)
    else:
        print("Error:", response.status_code, response.text)

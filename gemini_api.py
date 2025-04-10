import requests
import json
import base64
import re

API_KEY = "AIzaSyAJh-FWcmAyN4lt3MD7c7s_aBe0qFXOgoo"
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

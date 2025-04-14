from flask import Flask, request, jsonify
import base64
import mimetypes
import os
from groq import Groq

# Initialize Flask app
app = Flask(__name__)

# Initialize Groq client with your API key
client = Groq(api_key="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")

# Path to uploaded images
UPLOAD_FOLDER = './uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

# Ensure the upload folder exists
if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)

def encode_image_to_base64(image_path):
    """Encode the image to base64 format."""
    if not os.path.exists(image_path):
        raise FileNotFoundError(f"Image not found: {image_path}")
    with open(image_path, "rb") as img_file:
        return base64.b64encode(img_file.read()).decode("utf-8")

def get_mime_type(image_path):
    """Get the mime type of the image."""
    mime_type, _ = mimetypes.guess_type(image_path)
    return mime_type or "image/jpeg"

@app.route('/detect', methods=['POST'])
def detect_nsfw():
    """Detect harmful content in the image using Groq AI."""
    if 'file' not in request.files:
        return jsonify({"error": "No file part"}), 400

    file = request.files['file']

    if file.filename == '':
        return jsonify({"error": "No selected file"}), 400

    # Save the uploaded file to the server
    image_path = os.path.join(app.config['UPLOAD_FOLDER'], file.filename)
    file.save(image_path)

    image_b64 = encode_image_to_base64(image_path)
    mime_type = get_mime_type(image_path)

    # Create base64 image URL for Groq API
    base64_url = f"data:{mime_type};base64,{image_b64}"

    # Call Groq API for image analysis
    try:
        response = client.chat.completions.create(
            model="meta-llama/llama-4-scout-17b-16e-instruct",
            temperature=0,
            max_completion_tokens=1024,
            top_p=1,
            stream=False,
            stop=None,
            messages=[
                {
                    "role": "user",
                    "content": [
                        {
                            "type": "text",
                            "text": (
                                "You are a content moderation AI. Your task is to examine the image below and detect "
                                "if it contains any harmful, explicit, violent, or NSFW content (e.g., weapons such as knives, "
                                "guns, kitchen knives, or other dangerous objects, as well as blood, gore, fighting, or nudity). If the image contains "
                                "anything harmful or explicit, respond with 'yes' followed by a brief reason. If not, respond 'no'."
                            )
                        },
                        {
                            "type": "image_url",
                            "image_url": {
                                "url": base64_url
                            }
                        }
                    ]
                }
            ]
        )

        detection_result = response.choices[0].message.content
        return jsonify({
            "message": "Image processed successfully",
            "detection_result": detection_result
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# Run the Flask app
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001, debug=True)

import sys
import whisper

def transcribe(audio_path):
    # Load the model
    model = whisper.load_model("base")

    # Load the audio file
    audio = whisper.load_audio(audio_path)
    audio = whisper.pad_or_trim(audio)

    # Make a prediction
    result = model.transcribe(audio)

    # Print the result
    print(result["text"])

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python whisper_script.py <audio_file_path>")
        sys.exit(1)

    audio_file_path = sys.argv[1]
    transcribe(audio_file_path)

const fetch = require('node-fetch');

async function forwardAudioToWhisper(filePath) {
    const response = await fetch('https://api.openai.com/v1/whisper', {
        method: 'POST',
        headers: {
            'Authorization': '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',
            'Content-Type': 'audio/wav'
        },
        body: fs.createReadStream(filePath)
    });
    const data = await response.json();
    return data.transcription;
}
import java.io.*;

public class WhisperService {

    public String transcribeAudio(String audioFilePath) {
        String output = "";
        
        // Declare BufferedReader outside the try block to ensure they are in scope for the finally block
        BufferedReader reader = null;
        BufferedReader errorReader = null;

        // Construct the command to execute the Python script
        String[] command = new String[]{"python", "path_to_whisper_script/whisper_script.py", audioFilePath};

        try {
            // Start the process
            Process process = Runtime.getRuntime().exec(command);

            // Initialize the reader for the process's input stream
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
            }

            // Wait for the process to finish
            process.waitFor();

            // Check for errors
            if (process.exitValue() != 0) {
                // Initialize the reader for the process's error stream
                errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    System.err.println(errorLine);
                }
                // You could throw an exception or return an error message here
                output = "Error in Whisper script execution.";
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            output = "Exception occurred during Whisper script execution.";
        } finally {
            // Close both the readers if they are initialized
            try {
                if (reader != null) {
                    reader.close();
                }
                if (errorReader != null) {
                    errorReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return output;
    }
}

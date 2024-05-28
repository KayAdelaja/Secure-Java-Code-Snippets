import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SecureOSCommand {

    // Execute a secure OS command
    public String executeCommand(List<String> command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        return output.toString();
    }

    public static void main(String[] args) {
        SecureOSCommand secureOSCommand = new SecureOSCommand();
        List<String> command = List.of("ls", "-la");
        try {
            String result = secureOSCommand.executeCommand(command);
            System.out.println("Command output: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

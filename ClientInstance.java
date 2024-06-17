package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientInstance {
    // Define attributes for the ClientInstance object
    private String hostname;
    private int port;
    private String clientId;

    public ClientInstance(String hostname, int port) {
        // Constructor for the client instance
        this.hostname = hostname;
        this.port = port;
    }

    public void start() throws IOException {
        // Todo: create a parent menu

        // Execute code for interacting with the server
        try (
                Socket socket = new Socket(hostname, port);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            this.clientId = socket.getLocalAddress().getHostAddress();
            Serializer serializer = new Serializer(false);

            System.out.println("Connection with server successful");
            System.out.print("[" + this.clientId + "] -> ");

            // Continuously read from the console and send to the server
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                String serializedCommand = serializer.serialize(userInput);

                output.println(serializedCommand);

                String response = input.readLine();
                if (response == null) {
                    System.out.println("Server closed the connection");
                    break;
                }
                System.out.println("Response: " + response);
                System.out.print("[" + this.clientId + "] -> ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

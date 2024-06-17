package Client;

import org.json.*;

public class Serializer {
    private boolean isAuthenticated;

    public Serializer(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String login(String[] arr) {
        JSONObject obj = new JSONObject();
        obj.put("type", "login");
        obj.put("isAuthenticated", true); // Assuming successful login sets isAuthenticated to true
        obj.put("tokens", arr);

        // Simulate successful login
        this.isAuthenticated = true;

        return obj.toString(4);
    }

    public String register(String[] arr) {
        JSONObject obj = new JSONObject();
        obj.put("type", "register");
        obj.put("isAuthenticated", false); // Registration doesn't change authentication status in this example
        obj.put("tokens", arr);

        return obj.toString(4);
    }

    public void logout() {
        this.isAuthenticated = false;
    }

    public String serialize(String command) {
        String[] tokens = command.split("\\s+");

        if (tokens.length == 0) {
            return "Invalid command";
        }

        String action = tokens[0];
        String[] args = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, args, 0, tokens.length - 1);

        if (!isAuthenticated && action.equals("register")) {
            return this.register(args);
        }

        switch (action) {
            case "login":
                return this.login(args);
            case "logout":
                this.logout();
                return "{\"type\":\"logout\",\"status\":\"success\"}";
            default:
                return "Invalid command";
        }
    }

    public static void main(String[] args) {
        Serializer sample = new Serializer(false);

        // Test login command
        String result = sample.serialize("login frank ogenrwothjimfrank@gmail.com");
        System.out.println(result);

        // Test register command
        result = sample.serialize("register john doe johndoe@gmail.com");
        System.out.println(result);

        // Simulate successful login by setting isAuthenticated to true
        sample.isAuthenticated = true;

        // Test logout command
        result = sample.serialize("logout");
        System.out.println(result);
    }
}

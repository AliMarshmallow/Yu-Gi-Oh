package controller;

import java.util.ArrayList;

public class ChatRoom {
    public static ArrayList<String> allMessages = new ArrayList<>();

    public static void addMessage(String username, String message) {
        String string = username + ": " +  message;
        allMessages.add(string);
    }

    public static String getAllMessages() {
        String messages = "";
        for (String message: allMessages) {
            messages += message + "\n";
        }
        return messages;
    }
}

package controller;

import model.user.User;

import java.util.HashMap;
import java.util.Map;

public class OnlineUser {

    public static String getUser() {
        StringBuilder data = new StringBuilder();
        HashMap<String, User> users = RunServer.getUsersLoggedIn();
        for (Map.Entry<String, User> user : users.entrySet()) {
            User user1 = user.getValue();
            data.append(user1.getNickname()).append("\n");
        }
        return data.toString();
    }

}

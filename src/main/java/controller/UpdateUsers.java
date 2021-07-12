package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import model.user.User;

import java.util.ArrayList;

public class UpdateUsers {
    public static String update() {
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            jsonArray.add(gson.toJsonTree(user));
        }
        return jsonArray.toString();
    }

}

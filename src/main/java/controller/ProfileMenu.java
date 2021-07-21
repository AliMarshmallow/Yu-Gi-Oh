package controller;

import model.user.User;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class ProfileMenu {
    private JSONObject jsonResult = new JSONObject();

    public String profileChangeNickname(String nickname, String token) {
        HashMap<String, User> users = RunServer.getUsersLoggedIn();
        if (!users.containsKey(token)) {
            jsonResult.put("type", "Error");
            jsonResult.put("message", "token invalid!");
            return jsonResult.toJSONString();
        }
        if (!canChangeNickname(nickname)) {
            jsonResult.put("type", "Error");
            jsonResult.put("message", "nickname already used");
            return jsonResult.toJSONString();
        }
        User user = users.get(token);
        user.setNickname(nickname);
        jsonResult.put("type", "Successful");
        jsonResult.put("message", "nickname change successfully");
        return jsonResult.toJSONString();
    }

    public String profileChangePassword(String currentPassword, String newPassword, String token) {
        HashMap<String, User> users = RunServer.getUsersLoggedIn();
        if (!users.containsKey(token)) {
            jsonResult.put("type", "Error");
            jsonResult.put("message", "token invalid!");
            return jsonResult.toJSONString();
        }
        if (isPasswordWrong(currentPassword, users.get(token))) {
            jsonResult.put("type", "Error");
            jsonResult.put("message", "wrong password");
            return jsonResult.toJSONString();
        }
        if (currentPassword.equals(newPassword)) {
            jsonResult.put("type", "Error");
            jsonResult.put("message", "equal password");
            return jsonResult.toJSONString();
        }
        users.get(token).setPassword(newPassword);
        jsonResult.put("type", "Successful");
        jsonResult.put("message", "password change successfully");
        return jsonResult.toJSONString();
    }

    public boolean canChangeNickname(String nickname) {
        return User.getUserByNickname(nickname) == null;
    }

    public boolean isPasswordWrong(String password, User currentUser) {
        return !currentUser.getPassword().equals(password);
    }
}

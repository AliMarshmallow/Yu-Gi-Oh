package controller;

import model.user.User;
import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Login {
    private JSONObject jsonResult = new JSONObject();

    public String loginUser(String username, String password) {
        if (isUsernameAndPasswordMatch(username,password)){
            jsonResult.put("type","Error");
            jsonResult.put("message","wrong password");
            return jsonResult.toJSONString();
        }
        if (isUserLoggedIn(username)){
            jsonResult.put("type","Error");
            jsonResult.put("message","user already logged in");
            return jsonResult.toJSONString();
        }
        String token = UUID.randomUUID().toString();
        RunServer.addUser(User.getUserByUsername(username),token);
        jsonResult.put("type","Successful");
        jsonResult.put("token",token);
        return jsonResult.toJSONString();
    }

    public boolean isUsernameAndPasswordMatch(String username, String password) {
        if (User.getUserByUsername(username) == null) {
            return false;
        }
        User user = User.getUserByUsername(username);
        assert user != null;
        return user.getPassword().equals(password);
    }

    public boolean isUserLoggedIn(String username){
        HashMap<String,User> users = RunServer.getUsersLoggedIn();
        for (Map.Entry<String, User> set : users.entrySet()) {
            if (set.getValue().getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
}

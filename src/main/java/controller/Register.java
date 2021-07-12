package controller;

import model.user.User;
import org.json.simple.JSONObject;

public class Register {
    private JSONObject jsonResult = new JSONObject();
    public String registerNewUser(String username, String nickname, String password) {
        if (isUsernameExist(username)){
            jsonResult.put("type","Error");
            jsonResult.put("message","User with username "+username+" Exist");
            return jsonResult.toJSONString();
        }
        else if (isNicknameExist(nickname)){
            jsonResult.put("type","Error");
            jsonResult.put("message","User with nickname "+nickname+" Exist");
            return jsonResult.toJSONString();
        }else {
            new User(username,nickname,password);
            jsonResult.put("type","Successful");
            jsonResult.put("message","User registered successfully");
            return jsonResult.toJSONString();
        }
    }

    public boolean isUsernameExist(String username) {
        if (User.getUserByUsername(username) != null) {
            return true;
        }
        return false;
    }
    public boolean isNicknameExist(String nickname) {
        if (User.getUserByNickname(nickname) != null) {
            return true;
        }
        return false;
    }
}

package controller;

import model.user.User;
import org.json.simple.JSONObject;

import java.util.UUID;

public class Register {
    private JSONObject jsonResult = new JSONObject();
    public String registerNewUser(String username, String nickname, String password) {
        if (isUsernameExist(username)){
            jsonResult.put("type","Error");
            jsonResult.put("message","User with username Exist");
            return jsonResult.toJSONString();
        }
        else if (isNicknameExist(nickname)){
            jsonResult.put("type","Error");
            jsonResult.put("message","User with nickname Exist");
            return jsonResult.toJSONString();
        }else {
            User user = new User(username,nickname,password);
            String token = UUID.randomUUID().toString();
            RunServer.addUser(user,token);
            jsonResult.put("type","Successful");
            jsonResult.put("token",token);
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

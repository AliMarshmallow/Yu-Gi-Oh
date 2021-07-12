package controller;

import model.user.User;
import org.json.simple.JSONObject;

public class UpdatePicture {

    public static String update(String username,String address){
        JSONObject jsonResult = new JSONObject();
        User.getUserByUsername(username).setCharacterFileAddress(address);
        jsonResult.put("type","Successful");
        jsonResult.put("message","change");
        return jsonResult.toJSONString();
    }
}

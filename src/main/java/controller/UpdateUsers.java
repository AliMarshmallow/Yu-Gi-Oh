package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import model.user.User;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class UpdateUsers {
    public static Object update(String num) {
        int number = Integer.parseInt(num);
        return User.getAllUsers().get(number);
    }

    public static String count(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("number",User.getAllUsers().size());
        return jsonObject.toJSONString();
    }

}

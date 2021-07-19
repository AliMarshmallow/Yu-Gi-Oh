package controller;

import model.Auction;
import model.card.Card;
import model.user.User;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class AuctionController {

    public String newAuction(String startOffer,String cardName,String token){
        JSONObject jsonResult = new JSONObject();
        HashMap<String, User> users = RunServer.getUsersLoggedIn();
        if (!users.containsKey(token)) {
            jsonResult.put("type", "Error");
            jsonResult.put("message", "token invalid!");
            return jsonResult.toJSONString();
        }
        User user = users.get(token);
        if (user.getCards(cardName) == null){
            jsonResult.put("type", "Error");
            jsonResult.put("message", "you dont have this card!!");
            return jsonResult.toJSONString();
        }
        Card card = user.getCards(cardName);
        user.deleteCard(cardName);
        new Auction(card,user,Integer.parseInt(startOffer));
        jsonResult.put("type", "Successful");
        jsonResult.put("message", "Auction added successfully");
        return jsonResult.toJSONString();
    }
    public String active(String token){
        JSONObject jsonResult = new JSONObject();
        HashMap<String, User> users = RunServer.getUsersLoggedIn();
        if (!users.containsKey(token)) {
            jsonResult.put("type", "Error");
            jsonResult.put("message", "token invalid!");
            return jsonResult.toJSONString();
        }
        return Auction.getActiveAuction();
    }

}

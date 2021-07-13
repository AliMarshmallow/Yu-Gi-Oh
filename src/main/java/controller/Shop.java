package controller;

import com.google.gson.Gson;
import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.user.User;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Shop {

    public String buyCard(String cardName,String token) {
        Gson gson = new Gson();
        JSONObject jsonResult = new JSONObject();
        HashMap<String, User> users = RunServer.getUsersLoggedIn();
        if (!users.containsKey(token)){
            jsonResult.put("type","Error");
            jsonResult.put("message","token invalid!");
            return jsonResult.toJSONString();
        }
        if (Card.getCardByName(cardName) == null) {
            jsonResult.put("type","Error");
            jsonResult.put("message","wrong card name");
            return jsonResult.toJSONString();
        }
        Card card = Card.getCardByName(cardName);
        if (card.getPrice() > users.get(token).getCredit()) {
            jsonResult.put("type","Error");
            jsonResult.put("message","not enough money");
            return jsonResult.toJSONString();
        }
        String result = "";
        if (card instanceof Monster) {
            Monster monster = (Monster) card;
            Monster monster1 = new Monster(monster.getName(), monster.getLevel(), monster.getAttribute()
                    , monster.getMonsterType(), monster.getCardType(), monster.getAttack(), monster.getDefence(), monster.getDescription(), monster.getPrice());
            users.get(token).addCard(monster1);
            jsonResult.put("type","monster");
            result += jsonResult.toJSONString()+"#####";
            result += gson.toJson(card);
            return result;
        }
        else if (card instanceof Spell) {
            Spell spell = (Spell) card;
            Spell spell1 = new Spell(spell.getName(), spell.getIcon(), spell.getDescription(), spell.getStatus(), spell.getPrice());
            users.get(token).addCard(spell1);
            jsonResult.put("type","spell");
            result += jsonResult.toJSONString()+"#####";
            result += gson.toJson(card);
            return result;
        }
        else {
            Trap trap = (Trap) card;
            Trap trap1 = new Trap(trap.getName(), trap.getIcon(), trap.getDescription(), trap.getStatus(), trap.getPrice());
            users.get(token).addCard(trap1);
            jsonResult.put("type","trap");
            result += jsonResult.toJSONString()+"#####";
            result += gson.toJson(card);
            return result;
        }
    }

    public String countCard(String cardName,String token){
        JSONObject jsonResult = new JSONObject();
        HashMap<String, User> users = RunServer.getUsersLoggedIn();
        if (!users.containsKey(token)){
            jsonResult.put("type","Error");
            jsonResult.put("message","token invalid!");
            return jsonResult.toJSONString();
        }
        User user = users.get(token);
        jsonResult.put("count",user.showNumberOfCard(cardName));
        return jsonResult.toJSONString();
    }
}

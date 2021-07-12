package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import model.user.Deck;
import model.user.User;

import java.util.ArrayList;

public class UpdateDeck {
    public static String update() {
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        ArrayList<Deck> decks = Deck.getAllDecks();
        for (Deck deck : decks) {
            jsonArray.add(gson.toJsonTree(deck));
        }
        return jsonArray.toString();
    }
}

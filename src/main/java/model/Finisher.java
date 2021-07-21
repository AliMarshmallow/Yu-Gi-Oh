package model;

import com.google.gson.Gson;
import com.sun.source.tree.ArrayAccessTree;
import model.card.Card;
import model.user.Deck;
import model.user.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Finisher {
    public synchronized static void finish() throws IOException {
        ArrayList<User> allUsers = User.getAllUsers();
        for (User user : allUsers) {

            Gson gson = new Gson();
            String fileAddress = "resources/users/" + user.getUsername() + ".json";

            try (FileWriter writer = new FileWriter(fileAddress)) {
                gson.toJson(user, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        ArrayList<Deck> decks = Deck.getAllDecks();
        for (Deck deck : decks) {
            Gson gson = new Gson();
            String fileAddress = "resources/decks/" + deck.getName() + deck.getCreatorUsername() + ".json";

            try (FileWriter writer = new FileWriter(fileAddress)) {
                gson.toJson(deck, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArrayList<Auction> auctions = Auction.getAuctions();
        for (Auction auction : auctions) {
            Gson gson = new Gson();
            String fileAddress = "resources/auctions/" + auction.getUserCreate() + "-" + auction.getId() + ".json";
            try (FileWriter writer = new FileWriter(fileAddress)) {
                gson.toJson(auction, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        HashMap<String, Integer> cardNumber = Card.getCardNumber2();
        HashMap<String, Boolean> cardBoolean = Card.getCardBoolean();

        Gson gson = new Gson();
        String fileAddress = "resources/cardNumber/number.json";
        try (FileWriter writer = new FileWriter(fileAddress)) {
            gson.toJson(cardNumber, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileAddress2 = "resources/cardNumber/boolean.json";
        try (FileWriter writer = new FileWriter(fileAddress2)) {
            gson.toJson(cardBoolean, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

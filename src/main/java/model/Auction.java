package model;

import model.card.Card;
import model.user.User;

import java.util.ArrayList;

public class Auction {

    private User userCreate;
    private User userLastOffer;
    private int lastOffer;
    private int startOffer;
    private Card card;
    private long time;
    private static ArrayList<Auction> activeAuction = new ArrayList<>();


    public Auction(Card card, User userCreate, int startOffer) {
        this.card = card;
        this.userCreate = userCreate;
        this.startOffer = startOffer;
        this.lastOffer = startOffer;
        this.time = System.currentTimeMillis();
    }

    public User getUserCreate() {
        return userCreate;
    }

    public User getUserLastOffer() {
        return userLastOffer;
    }

    public int getLastOffer() {
        return lastOffer;
    }

    public int getStartOffer() {
        return startOffer;
    }

    public Card getCard() {
        return card;
    }

    public boolean endAuction() {
        if (System.currentTimeMillis() - time >= 900000) {
            activeAuction.remove(this);
            //TODO sell
            return true;
        }
        return false;
    }

    public static String getActiveAuction() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Auction auction : activeAuction) {
            if (auction.endAuction()) {
                continue;
            }
            stringBuilder.append(auction.getCard().getName() + " " + auction.getUserCreate()
                    + " " + auction.getUserLastOffer() + " " + auction.getLastOffer()+"\n");
        }
        return stringBuilder.toString();
    }
}

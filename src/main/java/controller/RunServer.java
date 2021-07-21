package controller;

import model.Auction;
import model.Finisher;
import model.Initializer;
import model.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class RunServer {

    private static JSONParser parser = new JSONParser();
    private static HashMap<String, User> usersLoggedIn = new HashMap<>();
    private static boolean isObject = false;

    public static void runApp() {
        try {
            Initializer.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ServerSocket serverSocket = new ServerSocket(8585);
            while (true) {
                Socket socket = serverSocket.accept();
                startNewThread(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startNewThread(Socket socket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                getInputAndProcess(dataInputStream, dataOutputStream,objectOutputStream);
                dataInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void getInputAndProcess(DataInputStream dataInputStream, DataOutputStream dataOutputStream,ObjectOutputStream objectOutputStream) throws IOException {
        while (true) {
            String input;
            try {
                input = dataInputStream.readUTF();
            } catch (SocketException e) {
                break;
            }
            Object result = process(input);
            System.out.println(result.toString());
            if (!isObject) {
                dataOutputStream.writeUTF((String) result);
                dataOutputStream.flush();
            }else {
                isObject = false;
                objectOutputStream.writeObject(result);
                objectOutputStream.flush();
            }
            Finisher.finish();
        }
    }

    static Object process(String input) {
        JSONObject jsonInput = null;
        Object result;
        try {
            jsonInput = (JSONObject) parser.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String command = jsonInput.get("command").toString();
        switch (command) {
            case "register" -> {
                Register register = new Register();
                result = register.registerNewUser(jsonInput.get("username").toString(), jsonInput.get("nickname").toString(), jsonInput.get("password").toString());
            }
            case "login" -> {
                Login login = new Login();
                result = login.loginUser(jsonInput.get("username").toString(), jsonInput.get("password").toString());
            }
            case "logout" -> result = logout(jsonInput.get("username").toString(), jsonInput.get("token").toString());
            case "changePassword" -> {
                ProfileMenu profileMenu = new ProfileMenu();
                String currentPassword = jsonInput.get("currentPassword").toString();
                String newPassword = jsonInput.get("newPassword").toString();
                String token = jsonInput.get("token").toString();
                result = profileMenu.profileChangePassword(currentPassword, newPassword, token);
            }
            case "changeNickname" -> {
                ProfileMenu profileMenu = new ProfileMenu();
                String nickname = jsonInput.get("nickname").toString();
                String token = jsonInput.get("token").toString();
                result = profileMenu.profileChangeNickname(nickname, token);
            }
            case "scoreboard" -> {
                Scoreboard scoreboard = new Scoreboard();
                result = scoreboard.getSortUsersByScore();
            }
            case "updateUsers" -> {
                isObject = true;
                result = UpdateUsers.update(jsonInput.get("number").toString());
            }
            case "countUsers" -> {
                result = UpdateUsers.count();
            }

            case "updateDeck" ->{
                result = UpdateDeck.update();
            }
            case "updatePicture" ->{
                result = UpdatePicture.update(jsonInput.get("username").toString(),jsonInput.get("address").toString());
            }
            case "shop" -> {
                Shop shop = new Shop();
                String cardName = jsonInput.get("cardName").toString();
                String token = jsonInput.get("token").toString();
                result = shop.buyCard(cardName,token);
            }
            case "countCard" -> {
                Shop shop = new Shop();
                String cardName = jsonInput.get("cardName").toString();
                String token = jsonInput.get("token").toString();
                result = shop.countCard(cardName,token);
            }
            case "getOnline" -> {
                result = OnlineUser.getUser();
            }
            case "addAuction" -> {
                AuctionController auction = new AuctionController();
                result = auction.newAuction(jsonInput.get("startOffer").toString(), jsonInput.get("cardName").toString(), jsonInput.get("token").toString());
            }
            case "getActiveAuction" -> {
                AuctionController auction = new AuctionController();
                result = auction.active(jsonInput.get("token").toString());
            }
            case "adminAdd" ->{
                Shop shop = new Shop();
                result = shop.addCard(jsonInput.get("cardName").toString(),jsonInput.get("number").toString());
            }
            case "adminBan" ->{
                Shop shop = new Shop();
                result = shop.banCard(jsonInput.get("cardName").toString());
            }
            case "adminRemove" -> {
                Shop shop = new Shop();
                result = shop.removeCard(jsonInput.get("cardName").toString(),jsonInput.get("number").toString());
            }
            default -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "Error");
                jsonObject.put("message", "Bad request");
                result = jsonObject.toJSONString();
            }
        }
        return result;
    }

    public static void addUser(User user, String token) {
        usersLoggedIn.put(token, user);
    }

    public static void removeUser(String token) {
        usersLoggedIn.remove(token);
    }

    public static HashMap<String, User> getUsersLoggedIn() {
        return usersLoggedIn;
    }

    public static String logout(String username, String token) {
        JSONObject jsonResult = new JSONObject();
        if (!usersLoggedIn.containsKey(token)) {
            jsonResult.put("type", "Error");
            jsonResult.put("message", "token invalid!");
            return jsonResult.toJSONString();
        }
        removeUser(token);
        jsonResult.put("type", "Successful");
        jsonResult.put("message", "user logged out");
        return jsonResult.toJSONString();
    }
}

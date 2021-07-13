package controller;

import model.user.User;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Scoreboard {
    public String getSortUsersByScore() {
        ArrayList<User> allUsers = User.getAllUsers();
        User[] allUsersForSort = allUsers.toArray(new User[0]);
        for (int i = 0; i < allUsersForSort.length; i++) {
            int flagForEnd = 0;
            for (int j = 0; j < allUsersForSort.length - 1; j++) {
                if (allUsersForSort[j].getScore() < allUsersForSort[j + 1].getScore()
                        || (allUsersForSort[j].getScore() == allUsersForSort[j + 1].getScore() &&
                        allUsersForSort[j].getNickname().compareTo(allUsersForSort[j + 1].getNickname()) > 0)) {
                    User holderUser = allUsersForSort[j];
                    allUsersForSort[j] = allUsersForSort[j + 1];
                    allUsersForSort[j + 1] = holderUser;
                    flagForEnd = 1;
                }
            }
            if (flagForEnd == 0)
                break;
        }
        String result = printScoreboard(allUsersForSort);
        return result;
    }

    private String printScoreboard(User[] allUserSorted) {
        int rankCounter = 2;
        int rankHolder = 1;
        String terminalOutput = "";
        terminalOutput += "1- " + toStringForOneUser(allUserSorted[0]);
        terminalOutput += "\n";
        for (int i = 1; i < 10; i++) {
            if (allUserSorted.length == i) {
                break;
            }
            if (allUserSorted[i].getScore() == allUserSorted[i - 1].getScore())
                terminalOutput += rankHolder + "- " + toStringForOneUser(allUserSorted[i]);
            else {
                terminalOutput += rankCounter + "- " + toStringForOneUser(allUserSorted[i]);
                rankHolder = rankCounter;
            }
            terminalOutput += "\n";
            rankCounter += 1;
        }
        return terminalOutput;
    }

    private String toStringForOneUser(User user) {
        return user.getNickname() + " : " + user.getScore();
    }
}

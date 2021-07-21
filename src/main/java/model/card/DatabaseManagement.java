package model.card;

import model.user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagement {
    public static void initDatabase() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:resources/db.sqlite";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String query = """
                CREATE TABLE IF NOT EXISTS monsters (
                   id INTEGER PRIMARY KEY,
                   name STRING,
                   level INTEGER,
                   attribute STRING,
                   monster_type STRING,
                   card_type STRING,
                   attack INTEGER,
                   defence INTEGER,
                   description STRING,
                   price INTEGER
                );""";

        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        query = """
                CREATE TABLE IF NOT EXISTS spells (
                   id INTEGER PRIMARY KEY,
                   name STRING,
                   icon STRING,
                   description STRING,
                   status STRING,
                   price INTEGER,
                   card_type STRING
                );""";

        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        query = """
                CREATE TABLE IF NOT EXISTS traps (
                   id INTEGER PRIMARY KEY,
                   name STRING,
                   icon STRING,
                   description STRING,
                   status STRING,
                   price INTEGER,
                   card_type STRING
                );""";

        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addMonster(Monster monster) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:resources/db.sqlite";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String query = "INSERT INTO monsters (name, level, attribute, monster_type, card_type, attack, defence, description, price) " +
                "VALUES (" +
                "'" + monster.name.replace("'", "''") + "', " +
                "" + monster.level + ", " +
                "'" + monster.attribute.replace("'", "''") + "', " +
                "'" + monster.monsterType.replace("'", "''") + "', " +
                "'" + monster.cardType.replace("'", "''") + "', " +
                "" + monster.attack + ", " +
                "" + monster.defence + ", " +
                "'" + monster.description.replace("'", "''") + "', " +
                "" + monster.price + "" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Monster " + monster.name + " saved");
    }

    public static void addSpell(Spell spell) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:resources/db.sqlite";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String query = "INSERT INTO spells (name, icon, description, status, price, card_type) VALUES (" +
                "'" + spell.name.replace("'", "''") + "', " +
                "'" + spell.icon.replace("'", "''") + "', " +
                "'" + spell.description.replace("'", "''") + "', " +
                "'" + spell.status.replace("'", "''") + "', " +
                "" + spell.price + ", " +
                "'" + spell.cardType.replace("'", "''") + "'" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Spell " + spell.name + " saved");
    }

    public static void addTrap(Trap trap) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:resources/db.sqlite";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String query = "INSERT INTO traps (name, icon, description, status, price, card_type) VALUES (" +
                "'" + trap.name.replace("'", "''") + "', " +
                "'" + trap.icon.replace("'", "''") + "', " +
                "'" + trap.description.replace("'", "''") + "', " +
                "'" + trap.status.replace("'", "''") + "', " +
                "" + trap.price + ", " +
                "'" + trap.cardType.replace("'", "''") + "'" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Trap " + trap.name + " saved");
    }
}

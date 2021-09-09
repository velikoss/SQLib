package mrnavastar.sqlib.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlManager {

    private static Connection connection;

    public static void connectSQLITE(String path, String databaseName) {
        try {
            String url = "jdbc:sqlite:" + path + "/" + databaseName + ".db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connectMYSQL(String address, String port, String databaseName, String user, String pass) {
        try {
            String url = "jdbc:mysql://" + address + ":" + port + "/" + databaseName;
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void beginTransaction() {
        try {
            String sql = "BEGIN EXCLUSIVE;";
            Statement stmt = connection.createStatement();
            stmt.setQueryTimeout(30);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void endTransaction() {
        try {
            String sql = "COMMIT;";
            Statement stmt = connection.createStatement();
            stmt.setQueryTimeout(30);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(String name) {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS " + name + " (ID TEXT PRIMARY KEY, STRINGS TEXT, INTS TEXT, FLOATS " +
                    "TEXT, DOUBLES TEXT, BOOLEANS TEXT, JSON TEXT, NBT TEXT, BLOCKPOS TEXT, UUIDS TEXT, LITERALTEXTS TEXT, " +
                    "MUTABLETEXTS TEXT, ITEMSTACKS TEXT)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setQueryTimeout(30);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createRow(String tableName, String id) {
        try {
            String sql = "INSERT OR REPLACE INTO " + tableName + " (ID) VALUES(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setQueryTimeout(30);
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRow(String tableName, String id) {
        try {
            String sql = "DELETE FROM " + tableName + " WHERE ID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setQueryTimeout(30);
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> listIds(String tableName) {
        try {
            String sql = "SELECT ID FROM " + tableName;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setQueryTimeout(30);
            List<String> ids = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) ids.add(resultSet.getString(1));
            return ids;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String search(String tableName, String dataType, Object data) {
        try {
            String sql = "SELECT ID FROM " + tableName + " WHERE " + dataType + " = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setQueryTimeout(30);
            stmt.setString(1, (String) data);
            ResultSet resultSet = stmt.executeQuery();
            JsonParser parser = new JsonParser();
            String id = resultSet.getString("ID");

            if (data != null) return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject readJson(String tableName, String id, String dataType) {
        try {
            String sql = "SELECT " + dataType + " FROM " + tableName + " WHERE ID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setQueryTimeout(30);
            stmt.setString(1, id);
            ResultSet resultSet = stmt.executeQuery();
            JsonParser parser = new JsonParser();
            String data = resultSet.getString(dataType);

            if (data != null) return parser.parse(data).getAsJsonObject();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeJson(String tableName, String id, String dataType, JsonObject data) {
        try {
            String sql = "UPDATE " + tableName + " SET " + dataType + " = ? WHERE ID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setQueryTimeout(30);
            stmt.setString(1, data.toString());
            stmt.setString(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
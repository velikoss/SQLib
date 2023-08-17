package mrnavastar.sqlib.database;

public class MariaDBDatabase extends AuthenticatedDatabase {

    public MariaDBDatabase(String modId, String name, String address, String port, String username, String password) {
        super(modId, name, address, port, username, password);
    }

    @Override
    public String getConnectionUrl() {
        return "jdbc:mariadb://" + address + ":" + port + "/" + name;
    }

    @Override
    public String getTableCreationQuery(String tableName, String columns) {
        return "CREATE TABLE IF NOT EXISTS %s (ID TEXT, %s, PRIMARY KEY (ID(256)));".formatted(tableName, columns);
    }
}

# SQLib
A simple fabric based sql wrapper made with a focus on minecraft use cases

# Important Note:
This library is not a full fledged sql wrapper, and does not provide full access to many sql features. 
The main focus of this library is to provide an easy and simple way to store data in your mods.
If you are looking for a more advanced database I recomend taking a look at something like [Nitrite](https://github.com/nitrite/nitrite-java).

# Usage
The database must first be setup to use either sqlite or mysql like so:

- SQLITE
``` java
Database.TYPE = SqlTypes.SQLITE;
Database.DATABASE_NAME = "MyAmazingDatabase";
Database.SQLITE_DIRECTORY = "/database/folder";

Database.init();
```
- MYSQL
``` java
Database.TYPE = SqlTypes.MYSQL;
Database.DATABASE_NAME = "MyAmazingDatabase";
Database.MYSQL_ADDRESS = "127.0.0.1";
Database.MYSQL_PORT = "3306";
Database.MYSQL_USERNAME = "MrNavaStar";
Database.MYSQL_PASSWORD = "123456";

Database.init();
```

Now a table can be added to store data. This table will automatically be added to the database:
``` java
Table playerData = new Table("PlayerData");
```
In order to store data in the table you need a DataContainer object. This will simply provide a way to handle your data in an object orientied manner:
``` java
DataContainer player = new DataContainer(playerUuid);
playerData.put(player);
```
Finally, you can use the DataContainer to put, get, and drop data:

``` java
player.put("NickName", "Bob Ross");
player.put("Health", 100);

String name = player.getString("NickName");
String health = player.getInt("Health);

player.dropString("NickName");
player.dropInt("Health");
```

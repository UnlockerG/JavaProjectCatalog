import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBHandler {
    public static Connection getConnection(String path){
        try {
            Class.forName("org.sqlite.JDBC");
            var connection = DriverManager.getConnection(path);
            System.out.println("Соединение с базой данных успешно!");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void updateProduct(Connection connection, List<catalogColumn> catalogColumns) {
        Connection connection1;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            connection1 = DriverManager.getConnection("jdbc:sqlite:./catalog.sqlite");
            statement = connection1.createStatement();
            String dropSql = "DROP TABLE IF EXISTS catalogColumns";
            statement.executeUpdate(dropSql);
            String createSql = "CREATE TABLE catalogColumns " +
                    "(NAME  CHAR(50) NOT NULL, " +
                    "PRODUCTARTICUL CHAR(10) PRIMARY KEY NOT NULL," +
                    "PRICE FLOAT NOT NULL, " +
                    "STOCK INT NOT NULL )";
            statement.executeUpdate(createSql);
            int i = 0;

            while (i < catalogColumns.size()){
                var catalogColumn = catalogColumns.get(i);
                String name = catalogColumn.name;
                String articul = catalogColumn.articul;
                double price = catalogColumn.price;
                int stock = catalogColumn.stock;

                String query = "INSERT INTO catalogColumns VALUES (" +
                        "'" + name + "', " +
                        "'" + articul + "', " +
                        "'" + price +  "', " +
                        "'" + stock + "')";
                statement.addBatch(query);
                i++;
            }
            statement.executeBatch();
            statement.close();
            connection1.close();
            System.out.printf("Успешно обновлено %s строк%n", i);
            } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    public static double getAveragePrice (Connection connection, String name) throws SQLException {
        String querry = "SELECT AVG(PRICE) FROM catalogColumns " +
                "WHERE NAME LIKE '%"+name+"%'";
        Statement statement = connection.createStatement();
        statement.execute(querry);
        var result = statement.getResultSet();
        return Double.parseDouble(result.getString(1));
    }
}



import java.io.IOException;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        var connection = DBHandler.getConnection("jdbc:sqlite:./catalog.sqlite");
        Parser parser = new Parser(connection);
        try{
            var date = Parser.getCatalogFromCSV("src/main/resources/Catalog.csv");
            DBHandler.updateProduct(connection, date);
            TaskHandler.CreateHistogram("CatalogAveragePrice.png",
                    DBHandler.getConnection("jdbc:sqlite:./catalog.sqlite"),
                    "Товары",
                    "Средняя цена, руб.",
                    1024,
                    768);
            if (connection !=null) {
                connection.close();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

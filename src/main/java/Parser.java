import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Connection connection;

    public Parser(Connection connection) {
        this.connection =  connection;
    }
    public static List<catalogColumn> getCatalogFromCSV(String filePath) throws IOException {
        List<catalogColumn> catalogColumns = new ArrayList<>();
        List<String> readLines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        readLines.remove(0);

        for (String readLine : readLines) {
            String[] splitText = readLine.split(";");
            ArrayList<String> columnList = new ArrayList<>();

            for (String str : splitText){
                if (ColumnPart(str)){
                    String lastText = columnList.get(columnList.size() - 1);
                    columnList.set(columnList.size() - 1, lastText + "," + str);
                } else {
                    columnList.add(str);
                }
            }
            catalogColumn catalogColumn = new catalogColumn();
            catalogColumn.name = columnList.get(0);
            catalogColumn.articul = columnList.get(1);
            catalogColumn.price = Double.parseDouble(columnList.get(2));
            catalogColumn.stock = Integer.parseInt(columnList.get(3));
            catalogColumns.add(catalogColumn);

        }
        return catalogColumns;
    }

    private static boolean ColumnPart (String text) {
        String trimText = text.trim();
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }

    public Connection getConnection() {return connection;}
}

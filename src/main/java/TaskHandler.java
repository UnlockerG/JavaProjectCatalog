import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TaskHandler {
    public static void CreateHistogram (String filename, Connection  connection, String XAxis, String YAxis, int height, int width){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            addAveragePrice(connection, dataset, "Автокормушка");
            addAveragePrice(connection, dataset, "Лоток для кормления цыплят");
            addAveragePrice(connection, dataset, "Фильтр для аквариума");
            addAveragePrice(connection, dataset, "Щетка");
            addAveragePrice(connection, dataset, "Туалет");
            addAveragePrice(connection, dataset, "Кормушка");
            addAveragePrice(connection, dataset, "Термометр");
            addAveragePrice(connection, dataset, "Нагреватель для аквариума");
            addAveragePrice(connection, dataset, "Помпа");
            addAveragePrice(connection, dataset, "Аквариум");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String title = filename.split("\\.")[0];
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                XAxis,
                YAxis,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(225, 225, 225));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        SaveHistogram(chart, filename, width, height);
    }

    private static void addAveragePrice(Connection connection, DefaultCategoryDataset dataset, String rowKey) throws SQLException{
        dataset.addValue(DBHandler.getAveragePrice(connection,rowKey), rowKey, "");
    }

    private static void SaveHistogram(JFreeChart chart, String name, int width, int height) {
        try{
            ChartUtilities.saveChartAsPNG(new File(name), chart, width,height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Гистограмма \"" +name+ "\" сохранена!");
    }

}


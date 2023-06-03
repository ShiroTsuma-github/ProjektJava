package proj1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;


import javax.swing.*;
import java.awt.*;



public class Histogram extends JPanel {

    private DefaultCategoryDataset dataset;
    private int position = 0;

    public Histogram() {
        this.dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Histogram",
                "Rząd",
                "Wartość",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 0, 255));

        ChartPanel chartPanel = new ChartPanel(chart);
        this.add(chartPanel);
    }

    public Image getChartImage() {
        JFreeChart chart = ChartFactory.createBarChart(
                "Histogram",
                "Rząd",
                "Wartość",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        return chart.createBufferedImage(400, 400);
    }

    public void updateDataset(int value, int row, int column) {
        String category = "Row " + (row + 1);
        if (category.equals("Invalid")) {
            System.out.println("Invalid value");
            return;
        }
        int frequency = dataset.getValue(category, String.valueOf(column + 1)).intValue();
        dataset.setValue(frequency + 1, category, String.valueOf(column + 1));
        JFreeChart chart = ChartFactory.createBarChart(
                "Histogram",
                "Rząd",
                "Wartość",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        CategoryPlot plot = chart.getCategoryPlot();
        StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(column, plot.getDrawingSupplier().getNextPaint());
    }

    public void reset() {
        position = 0;
        dataset.clear();
    }
}
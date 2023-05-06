package proj1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class Histogram extends JPanel {

    private DefaultCategoryDataset dataset;

    public Histogram() {
        this.dataset = new DefaultCategoryDataset();
        this.setCategories();

        JFreeChart chart = ChartFactory.createBarChart(
                "Histogram",
                "Przedział",
                "Częstotliwość",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 0, 255));

        ChartPanel frame = new ChartPanel(chart);
        this.add(frame);
    }

    public Image getChartImage() {
        JFreeChart chart = ChartFactory.createBarChart(
                "Histogram",
                "Przedział",
                "Częstotliwość",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        return chart.createBufferedImage(400, 400);
    }

    public void setCategories()
    {
        this.addCategory("-100--50");
        this.addCategory("-49--1");
        this.addCategory("0");
        this.addCategory("1-49");
        this.addCategory("50-100");
        this.addCategory("Poza przedziałem");
    }

    public void setMaximumSize(Dimension d) {
        super.setMaximumSize(d);
    }

    public void addCategory(String category) {
        dataset.addValue(0, "Częstotliwość", category);
    }

    public String getRange(int value)
    {
        if( isBetween(value, -100, -50) ) return "-100--50";
        else if( isBetween(value, -49, -1) ) return "-49--1";
        else if( value == 0) return "0";
        else if( isBetween(value, 1, 49) ) return "1-49";
        else if( isBetween(value, 50, 100) ) return "50-100";
        else return "Poza przedziałem";
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
      }

    public void updateDataset(int value) {
        String category = getRange(value);
        if (category.equals("Invalid")) {
            System.out.println("Invalid value");
            return;
        }
        int frequency = dataset.getValue("Częstotliwość", category).intValue();
        dataset.setValue(frequency + 1, "Częstotliwość", category);
        JFreeChart chart = ChartFactory.createBarChart(
                "Histogram",
                "Przedział",
                "Częstotliwość",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 0, 255));
    }

    public void reset() {
        dataset.clear();
        this.setCategories();
    }
}

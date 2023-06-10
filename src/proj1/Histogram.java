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

/**
 * Klasa reprezentująca wykres histogramu.
 * Wykorzystuje bibliotekę JFreeChart do generowania wykresu słupkowego.
 * Przechowuje dane kategorii w postaci DefaultCategoryDataset.
 */
public class Histogram extends JPanel {

    private DefaultCategoryDataset dataset;
    private int position = 0;

    /**
     * Konstruktor klasy Histogram.
     * Inicjalizuje dataset dla wykresu.
     * Tworzy wykres słupkowy za pomocą biblioteki JFreeChart i ustawia jego właściwości.
     * Tworzy panel z wykresem i dodaje go do komponentu.
     */
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

    /**
     * Metoda zwracająca obraz wykresu.
     * Tworzy nowy wykres słupkowy za pomocą biblioteki JFreeChart i ustawia jego właściwości.
     * Tworzy buforowany obraz wykresu o określonym rozmiarze.
     * Zwraca ten obraz.
     */
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

    /**
     * Metoda dodająca wartość do datasetu wykresu.
     * Zwiększa licznik pozycji.
     * Dodaje wartość do datasetu dla określonego rzędu i kolumny.
     */
    public void addValue(int value, int row, int col) {
        position++;
        dataset.addValue(value, "Rząd " + String.valueOf(row), String.valueOf(col + 1));
    }

    /**
     * Metoda resetująca wykres.
     * Resetuje licznik pozycji i czyści dataset.
     */
    public void reset() {
        position = 0;
        dataset.clear();
    }
}
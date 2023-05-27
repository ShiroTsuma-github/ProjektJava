package proj1;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.toedter.calendar.JCalendar;

public class Vcontroller implements ActionListener, ListSelectionListener, TableModelListener, PropertyChangeListener{
    private TableModel model;
    private View view;
    private ListModel listModel;
    private JCalendar calendar;
    private Histogram histogram;

    public Vcontroller() {
        Locale.setDefault(new Locale("pl", "PL"));
        this.model = new TableModel(5, 5);
        this.listModel = new ListModel();
        this.calendar = new JCalendar();
        this.histogram = new Histogram();
        this.updateChart();
        this.view = new View(model, listModel, this, this.calendar, this.histogram);
        this.model.toggleCellsEdit(false);
        this.view.showView();
    }

    private void handleAdd()
    {
        try {
            this.model.setValueAt(Integer.parseInt(this.view.getInputValue()),(int) view.getInputRow() - 1, (int) view.getInputColumn() - 1);
        } catch (NullPointerException np) {
            this.view.showMessage( "Brak danych do wprowadzenia");
        } catch (NumberFormatException se) {
            this.view.showMessage("Wprowadzono nieprawidłową wartość");
        } catch (ArrayIndexOutOfBoundsException te) {
            this.view.showMessage("Wprowadzono nieprawidłowy wiersz lub rząd");
        }
    }

    public void handleSum() {
        this.view.updateResult("Suma: " + this.model.getSum());
    }

    private void handleClear(){
        this.model.clear();
    }

    private void handleFill(){
        this.model.fillRandom();
    }

    private void handleAvg()
    {
        double average = this.model.getAverage();
        this.view.updateResult("Średnia: " + average);
    }

    private void handleMin(Boolean... args)
    {
        int min = this.model.getMin();
        if(args.length == 0)
        {
            this.view.updateResult("Minimum: " + min);
        }
        else
        {
            this.view.addToResult("Minimum: " + min + "\n");
        }
        
    
    }

    private void handleMax(Boolean... args)
    {
        int max = this.model.getMax();
        if(args.length == 0)
        {
            this.view.updateResult("Maksimum: " + max);
        }
        else
        {
            this.view.addToResult("\n" + "Maksimum: " + max + "\n");
        }
    }

    private void handleMinMax()
    {
        handleMin();
        handleMax(true);
    }

    private void handleSave(){
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                for (int i = 0; i < this.model.getRowCount(); i++) {
                    for (int j = 0; j < this.model.getColumnCount(); j++) {
                        writer.write(String.valueOf(this.model.getValueAt(i, j)) + ",");
                    }
                    writer.write("\n");
                }
            } catch (IOException ex) {
                System.out.println("error");
                this.view.showMessage("Saving failed: " + ex.getMessage());
            }
        }
    }

    private void handlePrint2()
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        Boolean wasHere = false;
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pf, int pageIndex) {
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }
                if(wasHere) return NO_SUCH_PAGE;
                Boolean wasHere = true;

                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pf.getImageableX(), pf.getImageableY());

                // MessageFormat header = new MessageFormat("Table Print");
                // MessageFormat footer = new MessageFormat("- {0} -");
                // Boolean cont = table.print(mode, header, footer);
                Boolean cont = false;
                if (cont) {
                    return PAGE_EXISTS;
                } else {
                    return NO_SUCH_PAGE;
                }
            }
        });
        
            try {
                job.print();
            } catch (PrinterException ex) {
                this.view.showMessage("Printing failed: " + ex.getMessage());
            }
    }

    private void handlePrint()
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        Histogram histogram = this.histogram;
        Image image = histogram.getChartImage();
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }
        
                Graphics2D g2d = (Graphics2D) graphics;
        

                int imageWidth = image.getWidth(null);
                int imageHeight = image.getHeight(null);
                int x = (int) pageFormat.getImageableX() + ((int) pageFormat.getImageableWidth() - imageWidth) / 2;
                int y = (int) pageFormat.getImageableY();
                g2d.drawImage(image, x, y, null);

                int numRows = model.getRowCount();
                int numCols = model.getColumnCount();
                int rowHeight = 40;
                int cellPadding = 5;
                int tableWidth = numCols * (60 + cellPadding);
                int tableX = (int) pageFormat.getImageableX() + ((int) pageFormat.getImageableWidth() - tableWidth) / 2;
                int tableY = y + imageHeight + cellPadding;
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols; col++) {
                        Object value = model.getValueAt(row, col);
                        int cellX = tableX + col * (60 + cellPadding);
                        int cellY = tableY + row * rowHeight;
                        g2d.drawString(value.toString(), cellX, cellY + rowHeight);
                    }
                }
        
                return PAGE_EXISTS;
            }
        });
            try {
                job.print();
            } catch (PrinterException ex) {
                this.view.showMessage("Printing failed: " + ex.getMessage());
            }
    }

    public static BufferedImage scaleImage(BufferedImage src, int w, int h)
    {
        BufferedImage img = 
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    private void handleLoad(){
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (Scanner scanner = new Scanner(file)) {
                int row = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] cells = line.split(",");
                    for (int col = 0; col < cells.length; col++) {
                        
                        this.model.setValueAt(Integer.parseInt(cells[col]), row, col);
                    }
                    row++;
                }
            } catch (IOException ex) {
                this.view.showMessage("Loading failed: ");
            } catch (ArrayIndexOutOfBoundsException ex) {
                this.view.showMessage("Wczytanie nieudane: " + "Zła ilośc danych w pliku");
            } catch (NumberFormatException ex) {
                this.view.showMessage("Loading failed: " + ex.getMessage());
            }
    }
    }

    private void updateChart()
    {
        this.histogram.reset();
        for (int i = 0; i < this.model.getRowCount(); i++) {
            for (int j = 0; j < this.model.getColumnCount(); j++) {
                this.histogram.updateDataset((int) this.model.getValueAt(i, j));
            }
        }
    }

    @Override
    public void tableChanged(TableModelEvent e)
    {
        this.model.incUpdateCount();
        this.updateChart();
        this.view.updateResult("Ilość zmian tabeli: " + this.model.getUpdateCount());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Object source = e.getSource();
        if (source instanceof JList) {
            JList<String> list = (JList<String>) source;
            String selectedOption = list.getSelectedValue();
            switch (selectedOption) {
                case "Suma elementów":
                    handleSum();
                    break;
                case "Średnia elementów":
                    handleAvg();
                    break;
                case "Wartość max i min":
                    handleMinMax();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // System.out.println("Action performed");
        Object source = e.getSource();
        if (source instanceof JButton)
        {
            JButton button = (JButton) source;
            switch (button.getToolTipText()) {
                case "Zadania":
                    this.view.toggleTaskPane();
                    break;
                case "Zapisz":
                    handleSave();
                    break;
                case "Body zapisz":
                    handleSave();
                    break;
                case "Drukuj":
                    handlePrint2();
                    break;
                case "Wczytaj":
                    handleLoad();
                    break;
                case "Dodaj":
                    handleAdd();
                    break;
                case "Body dodaj":
                    handleAdd();
                    break;
                case "Wyczyść":
                    handleClear();
                    break;
                case "Body wyczyść":
                    handleClear();
                    break;
                case "Wypełnij":
                    handleFill();
                    break;
                case "Body wypełnij":
                    handleFill();
                    break;
                case "Suma":
                    handleSum();
                    break;
                case "Średnia":
                    handleAvg();
                    break;
                case "Minimum":
                    handleMin();
                    break;
                case "Maksimum":
                    handleMax();
                    break;
                case "O programie":
                    this.view.getAbout();
                    break;
                case "Pomoc":
                    this.view.getHelp();
                    break;
                default:
                    break;
            }
        }
        else if( source instanceof JMenuItem)
        {
            JMenuItem button = (JMenuItem) source;
            switch (button.getName()) {
                case "Zapisz":
                    handleSave();
                    break;
                case "Drukuj":
                    handlePrint2();
                    break;
                case "Wczytaj":
                    handleLoad();
                    break;
                case "Wyczyść":
                    handleClear();
                    break;
                case "Wyjście":
                    System.exit(0);
                    break;
                case "Suma":
                    handleSum();
                    break;
                case "Średnia":
                    handleAvg();
                    break;
                case "Minimum":
                    handleMin();
                    break;
                case "Maksimum":
                    handleMax();
                    break;
                case "O programie":
                    this.view.getAbout();
                    break;
                case "Pomoc":
                    this.view.getHelp();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if (source instanceof JCalendar) {
            JCalendar calendar = (JCalendar) source;
            if (calendar.getDate() != null) {
                if (this.view != null)
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = formatter.format(calendar.getDate());
                    this.view.updateResult("Wybrana data: " + formattedDate);
                }
            }
        }
        // System.out.println(ev.getnewValue());
    }


    public static void main(String[] args) {
        new Vcontroller();
    }
}

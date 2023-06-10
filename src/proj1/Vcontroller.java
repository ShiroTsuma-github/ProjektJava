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

import org.apache.log4j.Logger;

import com.toedter.calendar.JCalendar;

public class Vcontroller implements ActionListener, ListSelectionListener, TableModelListener, PropertyChangeListener{
    private TableModel model;
    private View view;
    private ListModel listModel;
    private JCalendar calendar;
    private Histogram histogram;
    private Log4JApp loggerApp = new Log4JApp();
    private Logger logger = Logger.getLogger(Vcontroller.class);


     /**
     * Konstruktor klasy Vcontroller. Inicjalizuje obiekty modelu, widoku, listy, kalendarza i histogramu.
     * Ustawia domyślną lokalizację na "pl_PL".
     * Wywołuje metodę updateChart() i tworzy widok.
     * Wyłącza edycję komórek w modelu.
     * Wyświetla widok.
     */
    public Vcontroller() {
        // logger.getLogger(getClass())
        logger = loggerApp.formatLogger(logger);
        logger.info("Uruchomiono program");
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

    /**
     * Obsługuje zdarzenie dodawania wartości do komórki.
     * Próbuje ustawić wprowadzoną wartość w odpowiedniej komórce modelu.
     * Obsługuje wyjątki związane z błędnymi danymi lub indeksami komórek.
    */
    private void handleAdd()
    {
        logger.info("Dodawanie wartości do komórki");
        try {
            this.model.setValueAt(Integer.parseInt(this.view.getInputValue()),(int) view.getInputRow() - 1, (int) view.getInputColumn() - 1);
        } catch (NullPointerException np) {
            logger.error("Brak danych do wprowadzenia");
            this.view.showMessage( "Brak danych do wprowadzenia");
        } catch (NumberFormatException se) {
            logger.error("Wprowadzono nieprawidłową wartość");
            this.view.showMessage("Wprowadzono nieprawidłową wartość");
        } catch (ArrayIndexOutOfBoundsException te) {
            logger.error("Wprowadzono nieprawidłowy wiersz lub rząd");
            this.view.showMessage("Wprowadzono nieprawidłowy wiersz lub rząd");
        }
    }

    /**
     * Obsługuje zdarzenie sumowania wartości w modelu.
     * Aktualizuje wynik w widoku.
     */
    public void handleSum() {
        logger.info("Sumowanie wartości w modelu");
        this.view.updateResult("Suma: " + this.model.getSum());
    }

    /**
     * Obsługuje zdarzenie czyszczenia modelu.
     * Wywołuje metodę clear() w modelu.
     */
    private void handleClear(){
        logger.info("Czyszczenie modelu");
        this.model.clear();
    }

    /**
     * Obsługuje zdarzenie wypełniania modelu losowymi wartościami.
     * Wywołuje metodę fillRandom() w modelu.
     */
    private void handleFill(){
        logger.info("Wypełnianie modelu losowymi wartościami");
        this.model.fillRandom();
    }

    /**
     * Obsługuje zdarzenie obliczania średniej wartości w modelu.
     * Pobiera wartość średniej z modelu i aktualizuje wynik w widoku.
     */
    private void handleAvg()
    {
        logger.info("Obliczanie średniej wartości w modelu");
        double average = this.model.getAverage();
        this.view.updateResult("Średnia: " + average);
    }

    /**
     * Obsługuje zdarzenie znajdowania najmniejszej wartości w modelu.
     * Pobiera najmniejszą wartość z modelu i aktualizuje wynik w widoku.
     * Jeśli przekazane są argumenty (Boolean... args), dodaje wynik.
     */
    private void handleMin(Boolean... args)
    {
        logger.info("Znajdowanie najmniejszej wartości w modelu");
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

    /**
     * Obsługuje zdarzenie znajdowania największej wartości w modelu.
     * Pobiera największą wartość z modelu i aktualizuje wynik w widoku.
     * Jeśli przekazane są argumenty (Boolean... args), dodaje wynik.
     */
    private void handleMax(Boolean... args)
    {
        logger.info("Znajdowanie największej wartości w modelu");
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

    /**
     * Obsługuje obliczanie wartości minimalnej i maksymalnej.
     * Wywołuje funkcję handleMin() i handleMax(true).
     */
    private void handleMinMax()
    {
        logger.info("Obliczanie wartości minimalnej i maksymalnej");
        handleMin();
        handleMax(true);
    }

    /**
     * Obsługuje zapisywanie danych do pliku.
     * Tworzy okno dialogowe JFileChooser i pozwala użytkownikowi wybrać plik do zapisu.
     * Następnie zapisuje zawartość modelu do wybranego pliku.
     * W przypadku wystąpienia błędu, wyświetla odpowiednie komunikaty.
     */
    private void handleSave(){
        logger.info("Zapisywanie danych do pliku");
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
                logger.error("Błąd zapisu do pliku: " + ex.getMessage());
                System.out.println("error");
                this.view.showMessage("Saving failed: " + ex.getMessage());
            }
        }
    }

    /**
     * Obsługuje drukowanie danych na drukarkę.
     * Tworzy obiekt PrinterJob, inicjalizuje zmienną wasHere na false.
     * Ustawia obiekt Printable, który definiuje sposób drukowania.
     * Jeśli drukowanie jest możliwe, wywołuje funkcję print() na obiekcie job.
     * W przypadku wystąpienia błędu, wyświetla odpowiedni komunikat.
     */
    private void handlePrint2()
    {
        logger.info("Drukowanie danych na drukarkę");
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
                logger.error("Błąd drukowania: " + ex.getMessage());
                this.view.showMessage("Printing failed: " + ex.getMessage());
            }
    }

    /**
     * Oryginalna metoda nie wspierająca drukowania wykresu.
     * Obsługuje drukowanie danych na drukarkę.
     * Tworzy obiekt PrinterJob, pobiera obraz wykresu z obiektu histogram.
     * Ustawia obiekt Printable, który definiuje sposób drukowania.
     * Jeśli drukowanie jest możliwe, wywołuje funkcję print() na obiekcie job.
     * W przypadku wystąpienia błędu, wyświetla odpowiedni komunikat.
     */
    private void handlePrint()
    {
        logger.info("Drukowanie danych na drukarkę");
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
                logger.error("Błąd drukowania: " + ex.getMessage());
                this.view.showMessage("Printing failed: " + ex.getMessage());
            }
    }

    /**
    * Skaluje obraz o podanych wymiarach.
    * Tworzy nowy obraz o rozmiarze (w, h) i typie BufferedImage.TYPE_INT_RGB.
    * Iteruje po nowym obrazie i przypisuje piksele z oryginalnego obrazu po odpowiednich transformacjach.
    * Zwraca przeskalowany obraz.
    *
    * @param src Oryginalny obraz do skalowania.
    * @param w   Nowa szerokość obrazu.
    * @param h   Nowa wysokość obrazu.
    * @return Przeskalowany obraz.
    */
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

    /**
     * Obsługuje wczytywanie danych z pliku.
     * Tworzy okno dialogowe JFileChooser i pozwala użytkownikowi wybrać plik do wczytania.
     * Następnie wczytuje dane z pliku linia po linii.
     * Każda linia jest rozdzielana na poszczególne komórki za pomocą separatora ','.
     * Wartości są parsowane na typ Integer i przypisywane do odpowiednich komórek modelu.
     * W przypadku wystąpienia błędów, wyświetla odpowiednie komunikaty.
     */
    private void handleLoad(){
        logger.info("Wczytywanie danych z pliku");
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
                logger.error("Wczytywanie nieudane: " + ex.getMessage());
                this.view.showMessage("Loading failed: ");
            } catch (ArrayIndexOutOfBoundsException ex) {
                logger.error("Wczytywanie nieudane: " + "Zła ilośc danych w pliku");
                this.view.showMessage("Wczytanie nieudane: " + "Zła ilośc danych w pliku");
            } catch (NumberFormatException ex) {
                logger.error("Wczytywanie nieudane: " + "Zły format danych w pliku");
                this.view.showMessage("Loading failed: " + ex.getMessage());
            }
    }
    }

    /**
     * Aktualizuje wykres histogramu na podstawie aktualnej zawartości modelu.
     * Resetuje histogram, a następnie iteruje po modelu i dodaje wartości do histogramu.
     */
    private void updateChart()
    {
        logger.info("Aktualizacja wykresu");
        this.histogram.reset();
        for (int i = 0; i < this.model.getRowCount(); i++) {
            for (int j = 0; j < this.model.getColumnCount(); j++) {
                this.histogram.addValue((int) this.model.getValueAt(i, j), i, j);
            }
        }
    }

    /**
     * Metoda wywoływana po zmianie danych w tabeli.
     * Inkrementuje licznik aktualizacji modelu, aktualizuje wykres histogramu i aktualizuje widok.
     *
     * @param e Zdarzenie zmiany w tabeli.
     */
    @Override
    public void tableChanged(TableModelEvent e)
    {
        logger.info("Aktualizacja danych w tabeli");
        this.model.incUpdateCount();
        this.updateChart();
        this.view.updateResult("Ilość zmian tabeli: " + this.model.getUpdateCount());
    }

    /**
     * Metoda wywoływana po zmianie zaznaczenia w liście.
     * Sprawdza wybraną opcję z listy i wykonuje odpowiednie akcje.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        logger.info("Wybrano opcję z listy");
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

    /**
     * Obsługuje zdarzenie ActionListener.
     * Sprawdza źródło zdarzenia, a następnie wykonuje odpowiednie akcje na podstawie tooltipu lub nazwy przycisku.
     * Wykonuje różne operacje w zależności od wybranej akcji.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("Wykonanie akcji");
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

    /**
     * Obsługuje zmianę właściwości.
     * Sprawdza źródło zmiany właściwości i w przypadku zmiany w JCalendar aktualizuje widok.
     *
     * @param evt Zdarzenie zmiany właściwości.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        logger.info("Aktualizacja daty");
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


    /**
     * Metoda główna programu.
     * Tworzy nową instancję klasy Vcontroller, która jest kontrolerem aplikacji.
     */
    public static void main(String[] args) {
        new Vcontroller();
    }
}

package proj1;

import javax.swing.*;

import org.apache.log4j.Logger;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.JTipOfTheDay;
import com.l2fprod.common.swing.tips.DefaultTip;
import com.l2fprod.common.swing.tips.DefaultTipModel;

import com.toedter.calendar.JCalendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.prefs.Preferences;

/**
 * Klasa reprezentująca widok aplikacji.
 * Rozszerza klasę JFrame, która dostarcza podstawowe funkcjonalności okna aplikacji.
 * Zawiera wszystkie elementy interfejsu użytkownika, takie jak menu, toolbar, panel główny, tabela, panele zadań, listę opcji, obszar wyników itp.
 */
public class View extends JFrame{

    private Log4JApp loggerApp = new Log4JApp();
    private Logger logger = Logger.getLogger(View.class);
    
    /**
     * Zmienne menu.
     */
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, helpMenu, viewMenu, calcMenu;
    private JMenuItem newMenuItem, openMenuItem, saveMenuItem, exitMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem,
                      aboutMenuItem, authorMenuItem, sumMenuItem, averageMenuItem, minMenuItem, maxMenuItem;
    // zmienne do toolbara
    private JToolBar toolBar;
    private JButton taskButton, saveButton, printButton, loadButton, addButton, clearButton, fillButton, sumButton, averageButton,
                    minButton, maxButton, helpButton, authorButton;
    HelpDoc helpWindow = null;
    Author authorWindow = null;
    // Zmienne do body
    private JPanel mainPanel;
    private JTable table;
    private JTaskPane taskPane;
    JList<String> optionsList;
    JTextArea resultsArea;
    JSpinner wiersz, kolumna;
    JFormattedTextField wartosc;
    private JButton bodyAddButton, bodyClearButton, bodyFillButton, bodySaveButton;
    // Table model
    private TableModel tableModel;
    private ListModel listModel;
    private Vcontroller controller;
    private JCalendar calendar;
    private Histogram histogram;

    static final int WIDTH = 1400;
    static final int HEIGHT = 800;
    static final String TITLE = "Aplikacja MVC";


    /**
     * Konstruktor klasy View.
     * Inicjalizuje obiekty i ustawia wygląd interfejsu użytkownika.
     */
    public View(TableModel model,
                ListModel listModel,
                Vcontroller controller,
                JCalendar calendar, 
                Histogram histogram) {
        this.tableModel = model;
        this.listModel = listModel;
        this.controller = controller;
        this.calendar = calendar;
        this.histogram = histogram;
        logger = loggerApp.formatLogger(logger);
        logger.info("Inicjalizacja widoku");
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
    
        CreateGUI();
        addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent e){
                logger.info("Zamykanie aplikacji");
                System.exit(0);
            }
        });
    }
    /**
     * Metoda wyświetlająca widok aplikacji.
     * Ustawia widoczność okna na true.
     */
    public void showView()
    {
        logger.info("Wyświetlanie widoku");
        setVisible(true);
    }

    /**
     * Tworzy interfejs użytkownika, zawierający pasek menu, pasek narzędzi, panel z elementami interaktywnymi
     * oraz wywołuje funkcję odpowiedzialną za wyświetlanie porady dnia po uruchomieniu okna.
     * Metoda jest wywoływana w celu zainicjowania GUI.
     */
    private void CreateGUI()
    {
        logger.info("Tworzenie GUI");
        createMenuBar();
        createToolBar();
        createBody();
        SwingUtilities.invokeLater(() -> {
            createTipOfTheDay();
        });
    }

    /**
     * Tworzy okno z losowym poradnikiem dnia.
     * Tworzy obiekt JTipOfTheDay i ustawia model porad.
     * Wyświetla okno dialogowe z poradą dnia.
     */
    private void createTipOfTheDay()
    {
        logger.info("Inicjalizacja porady dnia");
        JTipOfTheDay tipOfTheDay = new JTipOfTheDay();

        // Create the tip model
        DefaultTipModel tipModel = new DefaultTipModel();
        tipModel.add(new DefaultTip("Porada 2", "Kiedy aplikacja wyświetla błąd, zawsze sprawdź najpierw swoje umiejętności."));
        tipModel.add(new DefaultTip("Porada 3", "Jeśli masz problem z interfejsem, to znaczy, że nie jesteś wystarczająco inteligentny."));
        tipModel.add(new DefaultTip("Porada 4", "Zawsze pamiętaj, że to ty jesteś odpowiedzialny za wszystkie błędy w aplikacji."));
        tipModel.add(new DefaultTip("Porada 5", "Wszelkie problemy wynikają z twojej nieznajomości doskonałej aplikacji."));
        tipModel.add(new DefaultTip("Porada 6", "Gdy aplikacja działa nieprawidłowo, zacznij od zastanowienia się, co zrobiłeś źle."));
        tipModel.add(new DefaultTip("Porada 7", "Nie zgłaszaj błędów - to tylko marnowanie czasu wsparcia technicznego."));
        tipModel.add(new DefaultTip("Porada 8", "Kiedy aplikacja się zawiesza, zastanów się, czy na pewno ją używasz zgodnie z zasadami."));
        tipModel.add(new DefaultTip("Porada 9", "Zawsze zakładaj, że aplikacja działa idealnie, a wszelkie błędy są wynikiem twojej wyobraźni."));
        tipModel.add(new DefaultTip("Porada 10", "Nie trać czasu na zgłaszanie błędów - nikt nie uwierzy, że to wina aplikacji."));
        tipModel.add(new DefaultTip("Porada 11", "To idealna aplikacja."));
        tipModel.add(new DefaultTip("Porada 12", "Wszystko działa jak należy."));
        tipModel.add(new DefaultTip("Porada 13", "W programie nie ma błędów."));
        tipModel.add(new DefaultTip("Porada 14", "Jeśli coś nie działa, z pewnością jest to wina użytkownika."));

        Random random = new Random();
        int randomNumber = random.nextInt(13) + 1;
        Preferences preferences = Preferences.userRoot().node("Aplikacja MVC");
        tipOfTheDay.setModel(tipModel);
        tipOfTheDay.setCurrentTip(randomNumber);
        tipOfTheDay.showDialog(null, preferences, false);
    }

    /**
     * Tworzy pasek menu i dodaje menu oraz akcje do poszczególnych elementów.
     * Tworzy również panel nawigacyjny i dodaje przyciski z kategoriami i akcjami do nich.
     */
    private void createMenuBar() {
        logger.info("Tworzenie paska menu");
        menuBar = new JMenuBar();
        fileMenu = createJMenu("Plik", KeyEvent.VK_P, true);
        editMenu = createJMenu("Edycja", KeyEvent.VK_E, false);
        viewMenu = createJMenu("Widok", KeyEvent.VK_W, false);
        calcMenu = createJMenu("Obliczenia", KeyEvent.VK_O, true);
        helpMenu = createJMenu("Pomoc", KeyEvent.VK_H, true);
        newMenuItem = new JMenuItem("Nowy");
        newMenuItem.setName("Wyczyść");
        openMenuItem = new JMenuItem("Otwórz");
        openMenuItem.setName("Wczytaj");
        saveMenuItem = new JMenuItem("Zapisz");
        saveMenuItem.setName("Zapisz");
        exitMenuItem = new JMenuItem("Wyjście");
        exitMenuItem.setName("Wyjście");
        cutMenuItem = new JMenuItem("Wytnij");
        copyMenuItem = new JMenuItem("Kopiuj");
        pasteMenuItem = new JMenuItem("Wklej");
        sumMenuItem = new JMenuItem("Suma");
        sumMenuItem.setName("Suma");
        averageMenuItem = new JMenuItem("Średnia");
        averageMenuItem.setName("Średnia");
        minMenuItem = new JMenuItem("Minimum");
        minMenuItem.setName("Minimum");
        maxMenuItem = new JMenuItem("Maksimum");
        maxMenuItem.setName("Maksimum");
        aboutMenuItem = new JMenuItem("Pomoc");
        aboutMenuItem.setName("Pomoc");
        authorMenuItem = new JMenuItem("Autor");
        authorMenuItem.setName("O programie");
        // Menu plik
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        //  Menu edycja
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        // Menu obliczenia
        calcMenu.add(sumMenuItem);
        calcMenu.add(averageMenuItem);
        calcMenu.add(minMenuItem);
        calcMenu.add(maxMenuItem);
        // Menu pomocy
        helpMenu.add(aboutMenuItem);
        helpMenu.add(authorMenuItem);
        // Dodanie menu do paska menu
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(calcMenu);
        menuBar.add(helpMenu);
        // Dodanie paska menu do okna
        setJMenuBar(menuBar);
        // Dodanie akcji do menu




        taskPane = new JTaskPane();
        // Tworzenie kategorii w panelu nawigacyjnym
        JTaskPaneGroup group1 = new JTaskPaneGroup();
        group1.setExpanded(true); // Rozwiń kategorię na starcie
        group1.setText("Plik");

        JButton zapisz = new JButton("Zapisz");
        zapisz.setToolTipText("Zapisz");
        group1.add(zapisz);

        JButton drukuj = new JButton("Drukuj");
        drukuj.setToolTipText("Drukuj");
        group1.add(drukuj);

        JButton wczytaj = new JButton("Wczytaj");
        wczytaj.setToolTipText("Wczytaj");
        group1.add(wczytaj);

        taskPane.add(group1);

        JTaskPaneGroup group2 = new JTaskPaneGroup();
        group2.setExpanded(true);
        group2.setText("Edycja");

        JButton dodaj = new JButton("Dodaj");
        dodaj.setToolTipText("Dodaj");
        group2.add(dodaj);
        JButton wyczysc = new JButton("Wyczyść");
        wyczysc.setToolTipText("Wyczyść");
        group2.add(wyczysc);

        JButton wypelnij = new JButton("Wypełnij");
        wypelnij.setToolTipText("Wypełnij");
        group2.add(wypelnij);

        taskPane.add(group2);

        JTaskPaneGroup group3 = new JTaskPaneGroup();
        group3.setExpanded(true); 
        group3.setText("Obliczenia");

        JButton suma = new JButton("Suma");
//        suma.setBorder(null);
//        suma.setContentAreaFilled(false);
        suma.setToolTipText("Suma");
        group3.add(suma);
        JButton srednia = new JButton("Średnia");
        srednia.setToolTipText("Średnia");
        group3.add(srednia);
        JButton minimum = new JButton("Minimum");
        minimum.setToolTipText("Minimum");
        group3.add(minimum);
        JButton maksimum = new JButton("Maksimum");
        maksimum.setToolTipText("Maksimum");
        group3.add(maksimum);
        taskPane.add(group3);

        JTaskPaneGroup group4 = new JTaskPaneGroup();
        group4.setExpanded(true); 
        group4.setText("Pomoc");

        JButton autor = new JButton("O programie");
        autor.setToolTipText("O programie");
        group4.add(autor);
        JButton pomoc = new JButton("Pomoc");
        pomoc.setToolTipText("Pomoc");
        group4.add(pomoc);
        taskPane.add(group4);


        taskPane.setVisible(false);

        // Dodawanie panelu nawigacyjnego do kontenera głównego
        add(taskPane, BorderLayout.WEST);





        newMenuItem.addActionListener(controller);
        openMenuItem.addActionListener(controller);
        saveMenuItem.addActionListener(controller);
        cutMenuItem.addActionListener(controller);
        copyMenuItem.addActionListener(controller);
        pasteMenuItem.addActionListener(controller);
        exitMenuItem.addActionListener(controller);
        sumMenuItem.addActionListener(controller);
        averageMenuItem.addActionListener(controller);
        minMenuItem.addActionListener(controller);
        maxMenuItem.addActionListener(controller);
        aboutMenuItem.addActionListener(controller);
        authorMenuItem.addActionListener(controller);

        zapisz.addActionListener(controller);
        drukuj.addActionListener(controller);
        wczytaj.addActionListener(controller);
        dodaj.addActionListener(controller);
        wyczysc.addActionListener(controller);
        wypelnij.addActionListener(controller);
        suma.addActionListener(controller);
        srednia.addActionListener(controller);
        minimum.addActionListener(controller);
        maksimum.addActionListener(controller);
        autor.addActionListener(controller);
        pomoc.addActionListener(controller);
    }

    /**
     * Tworzy obiekt ImageIcon na podstawie ścieżki do pliku obrazu.
     * Może skalować obraz do określonego rozmiaru.
     *
     * @param path ścieżka do pliku obrazu
     * @param size opcjonalny parametr określający wymiary skalowanego obrazu
     * @return obiekt ImageIcon
     */
    private ImageIcon createImageIcon(String path, Integer... size) {
        ImageIcon img = new ImageIcon(getClass().getResource(path));
        Image scaledImg = img.getImage().getScaledInstance(size.length > 0 ? size[0] : 30,
        size.length > 0 ? size[0] : 30, Image.SCALE_SMOOTH);
        img = new ImageIcon(scaledImg);
        return img;
    }

    /**
     * Tworzy pasek narzędziowy i dodaje na nim przyciski z ikonami.
     */
    private void createToolBar() {
        logger.info("Tworzenie paska narzędziowego");
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        taskButton = new JButton(createImageIcon("/grafika/menu.png"));
        taskButton.setToolTipText("Zadania");
        toolBar.add(taskButton);
        toolBar.add(Box.createHorizontalStrut(10));
        saveButton = new JButton(createImageIcon("/grafika/save.png"));
        saveButton.setToolTipText("Zapisz");
        toolBar.add(saveButton);
        printButton = new JButton(createImageIcon("/grafika/printer.png"));
        printButton.setToolTipText("Drukuj");
        toolBar.add(printButton);
        loadButton = new JButton(createImageIcon("/grafika/load.png"));
        loadButton.setToolTipText("Wczytaj");
        toolBar.add(loadButton);
        toolBar.add(Box.createHorizontalStrut(10));

        addButton = new JButton(createImageIcon("/grafika/add.png"));
        addButton.setToolTipText("Dodaj");
        toolBar.add(addButton);
        clearButton = new JButton(createImageIcon("/grafika/clear.png"));
        clearButton.setToolTipText("Wyczyść");
        toolBar.add(clearButton);
        fillButton = new JButton(createImageIcon("/grafika/disc.png"));
        fillButton.setToolTipText("Wypełnij");
        toolBar.add(fillButton);
        toolBar.add(Box.createHorizontalStrut(10));
    
        sumButton = new JButton(createImageIcon("/grafika/sum.png"));
        sumButton.setToolTipText("Suma");
        toolBar.add(sumButton);
        averageButton = new JButton(createImageIcon("/grafika/average.png"));
        averageButton.setToolTipText("Średnia");
        toolBar.add(averageButton);
        minButton = new JButton(createImageIcon("/grafika/min.png"));
        minButton.setToolTipText("Minimum");
        toolBar.add(minButton);
    

        maxButton = new JButton(createImageIcon("/grafika/max.png"));
        maxButton.setToolTipText("Maksimum");
        toolBar.add(maxButton);
        toolBar.add(Box.createHorizontalStrut(10));
    
        authorButton = new JButton(createImageIcon("/grafika/about.png"));
        authorButton.setToolTipText("O programie");
        toolBar.add(authorButton);
        helpButton = new JButton(createImageIcon("/grafika/help.png"));
        helpButton.setToolTipText("Pomoc");

        toolBar.add(helpButton);
        add(toolBar, BorderLayout.PAGE_START);

        taskButton.addActionListener(controller);
        saveButton.addActionListener(controller);
        printButton.addActionListener(controller);
        loadButton.addActionListener(controller);
        addButton.addActionListener(controller);
        clearButton.addActionListener(controller);
        fillButton.addActionListener(controller);
        sumButton.addActionListener(controller);
        averageButton.addActionListener(controller);
        minButton.addActionListener(controller);
        maxButton.addActionListener(controller);
        authorButton.addActionListener(controller);
        helpButton.addActionListener(controller);
    }

    /**
     * Tworzy panel zawierający interaktywne elementy związane z edycją danych w tabeli.
     * Tworzy etykiety, pola tekstowe, przyciski, tabelę, obszar wyników, listę opcji i inne elementy interfejsu.
     * Dodaje akcje do poszczególnych elementów.
     */
    private void createBody() {
        logger.info("Tworzenie panelu z interaktywnymi elementami");

        JLabel wartoscLabel = new JLabel("Wprowadź liczbę:");
        // wartoscLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        wartosc = new JFormattedTextField(0);
        wartosc.setMargin(new Insets(5, 5, 5, 5));
        JLabel wierszLabel = new JLabel("Numer wiersza:");
        // wierszLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wiersz = new JSpinner(new SpinnerNumberModel(1, 1, this.tableModel.getRowCount(), 1));

        JLabel kolumnaLabel = new JLabel("Numer kolumny:");
        kolumnaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        kolumna = new JSpinner(new SpinnerNumberModel(1, 1, this.tableModel.getColumnCount() + 1, 1));
        // left six just to show exception handling
        table = new JTable(this.tableModel);
        this.tableModel.addTableModelListener(controller);
        bodyAddButton = new JButton("Dodaj    ", createImageIcon("/grafika/add.png", 20));
        bodyAddButton.setToolTipText("Body dodaj");
        bodyClearButton = new JButton("Wyzeruj", createImageIcon("/grafika/clear.png", 20));
        bodyClearButton.setToolTipText("Body wyczyść");
        bodyFillButton = new JButton("Wypełnij", createImageIcon("/grafika/disc.png", 20));
        bodyFillButton.setToolTipText("Body wypełnij");
        bodySaveButton = new JButton("Zapisz  ", createImageIcon("/grafika/save.png", 20));
        bodySaveButton.setToolTipText("Body zapisz");
        bodyAddButton.setPreferredSize(new Dimension(110, 30));
        bodyClearButton.setPreferredSize(new Dimension(110, 30));
        bodyFillButton.setPreferredSize(new Dimension(110, 30));
        bodySaveButton.setPreferredSize(new Dimension(110, 30));
        resultsArea= new JTextArea(5, 5);
        resultsArea.setEditable(false);
        JList<String> optionsList = new JList<>(listModel.getOptions());
        optionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        optionsList.addListSelectionListener(controller);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 3, 5, 5));
        controlPanel.add(wartoscLabel);
        controlPanel.add(wartosc);
        controlPanel.add(wierszLabel);
        controlPanel.add(wiersz);
        controlPanel.add(kolumnaLabel);
        controlPanel.add(kolumna);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(10, 10, 0, 10);

        // Panel for adding vertical buttons with constraints defined above
        // Added to tablePanel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(bodyAddButton, c);
        buttonPanel.add(bodyClearButton, c);
        buttonPanel.add(bodyFillButton, c);
        buttonPanel.add(bodySaveButton, c);
        // Panel for JList 
        // Added to tempPanel
        JPanel optionListPanel = new JPanel();
        optionListPanel.add(new JScrollPane(optionsList));
        // Temporary panel for adding optionListPanel to place it on the right bottom side
        // Added to tablePanel
        JPanel tempPanel = new JPanel(new BorderLayout());
        JPanel temp2 = new JPanel(new BorderLayout());
        temp2.add(optionListPanel, BorderLayout.WEST);
        temp2.add(this.histogram, BorderLayout.EAST);
        this.calendar.addPropertyChangeListener(controller);
        this.calendar.setPreferredSize(new Dimension(400, 200));
        tempPanel.add(this.calendar, BorderLayout.EAST);
        tempPanel.add(temp2, BorderLayout.WEST);
        // Main panel for things related to table
        // Added to mainPanel
        JPanel tablePanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(buttonPanel, BorderLayout.EAST);
        tablePanel.add(tempPanel, BorderLayout.SOUTH);

        // Panel to house all panels
        // Adding all panels to mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(resultsArea), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(resultsArea), BorderLayout.SOUTH);
        add(mainPanel);

        bodyAddButton.addActionListener(controller);
        bodyClearButton.addActionListener(controller);
        bodyFillButton.addActionListener(controller);
        bodySaveButton.addActionListener(controller);
    }

    /**
     * Tworzy menu o podanej nazwie, skrócie klawiaturowym i włączonym/wyłączonym stanie.
     * @param name nazwa menu
     * @param keyEvent skrót klawiaturowy
     * @param enable flaga określająca, czy menu ma być włączone (true) czy wyłączone (false)
     * @return utworzone menu
     */
    public JMenu createJMenu(String name, int keyEvent, boolean enable) {
		JMenu jMenu = new JMenu(name);
		jMenu.setMnemonic(keyEvent);
		jMenu.setEnabled(enable);
		return jMenu;
	}

    /**
     * Zwraca wartość wprowadzoną w polu tekstowym.
     * @return wprowadzona wartość
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Zwraca wartość wprowadzoną w polu tekstowym.
     * @return wprowadzona wartość
     */
    public String getInputValue() {
        return wartosc.getText();
    }

    /**
     * Zwraca wartość wprowadzoną w spinnerze dla numeru wiersza.
     * @return numer wiersza
     */
    public int getInputRow() {
        return (int) wiersz.getValue();
    }
    /**
     * Zwraca wartość wprowadzoną w spinnerze dla numeru kolumny.
     * @return numer kolumny
     */
    public int getInputColumn() {
        return (int) kolumna.getValue();
    }

    /**
     * Aktualizuje obszar wyników poprzez dodanie podanego tekstu.
     * @param text tekst do dodania
     */
    public void updateResult(String text)
    {
        resultsArea.append(text + "\n");
    }

    /**
     * Dodaje tekst do obszaru wyników.
     * @param text tekst do dodania
     */
    public void addToResult(String text)
    {
        resultsArea.append(text);
    }

    /**
     * Przełącza widoczność panelu zadań (taskPane). Jeśli panel jest widoczny, zostaje ukryty, a jeśli jest ukryty, zostaje pokazany.
     * Gdy panel staje się widoczny, ustawia preferowany rozmiar kalendarza na (400, 200), a gdy jest ukryty - na (300, 200).
     * Wywołuje ponowne walidowanie komponentów oraz odświeżenie ich wyglądu.
     */
    public void toggleTaskPane()
    {
        logger.info("Zmiana widoczności panelu zadań");
        boolean visible = taskPane.isVisible();
        taskPane.setVisible(!visible);

            if (visible) {
                this.calendar.setPreferredSize(new Dimension(400, 200));
            } else {
                this.calendar.setPreferredSize(new Dimension(300, 200));
            }
            revalidate();
            repaint();
    }

    /**
     * Zwraca referencję do tabeli (JTable).
     * @return referencja do tabeli
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Wyświetla okno pomocy. Jeśli okno pomocy (helpWindow) już istnieje, staje się widoczne, w przeciwnym razie tworzy nowe okno pomocy i je wyświetla.
     */
    public void getHelp()
    {
        logger.info("Wyświetlenie okna pomocy")
        if (helpWindow != null) {
            helpWindow.setVisible(true);
        } else {
            helpWindow = new HelpDoc();
            helpWindow.setVisible(true);
        }
    }

    /**
     * Wyświetla okno informacyjne o autorze. Jeśli okno autora (authorWindow) już istnieje, staje się widoczne, w przeciwnym razie tworzy nowe okno autora i je wyświetla.
     */
    public void getAbout()
    {
        logger.info("Wyświetlenie okna informacji o autorze")
        if (authorWindow != null) {
            authorWindow.setVisible(true);
        } else {
            authorWindow = new Author();
            authorWindow.setVisible(true);
        }
    }
}
        


package proj1;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
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
import javax.swing.event.ListSelectionListener;

public class View extends JFrame{
    
    // zmienne do menu
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, helpMenu, viewMenu, calcMenu;
    private JMenuItem newMenuItem, openMenuItem, saveMenuItem, exitMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem,
                        aboutMenuItem, authorMenuItem, sumMenuItem, averageMenuItem, minMenuItem, maxMenuItem;
    // zmienne do toolbara
    private JToolBar toolBar;
    private JButton saveButton, printButton, loadButton, addButton, clearButton, fillButton, sumButton, averageButton,
                    minButton, maxButton, helpButton, authorButton;
    HelpDoc helpWindow = null;
    Author authorWindow = null;
    // Zmienne do body
    private JPanel mainPanel;
    private JTable table;
    JList<String> optionsList;
    JTextArea resultsArea;
    JSpinner wiersz, kolumna;
    JFormattedTextField wartosc;
    private JButton bodyAddButton, bodyClearButton, bodyFillButton, bodySaveButton;
    // Table model
    private TableModel tableModel;
    private ListModel listModel;
    private Vcontroller controller;

    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final String TITLE = "Aplikacja MVC";

    public View(TableModel model, ListModel listModel, Vcontroller controller) {
        this.tableModel = model;
        this.listModel = listModel;
        this.controller = controller;
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
    
        CreateGUI();
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
    public void showView()
    {
        setVisible(true);
    }


    private void CreateGUI()
    {
        createMenuBar();
        createToolBar();
        createBody();
    }

    private void createMenuBar() {
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
    }
    private ImageIcon createImageIcon(String path, Integer... size) {
        ImageIcon img = new ImageIcon(getClass().getResource(path));
        Image scaledImg = img.getImage().getScaledInstance(size.length > 0 ? size[0] : 30,
        size.length > 0 ? size[0] : 30, Image.SCALE_SMOOTH);
        img = new ImageIcon(scaledImg);
        return img;
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
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

    private void createBody() {

        JLabel wartoscLabel = new JLabel("Wprowadź liczbę:");
        wartoscLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wartosc = createNumberTextField();
        wartosc.setMargin(new Insets(5, 5, 5, 5));

        JLabel wierszLabel = new JLabel("Numer wiersza:");
        wierszLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wiersz = new JSpinner(new SpinnerNumberModel(1, 1, this.tableModel.getRowCount(), 1));

        JLabel kolumnaLabel = new JLabel("Numer kolumny:");
        kolumnaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        kolumna = new JSpinner(new SpinnerNumberModel(1, 1, this.tableModel.getColumnCount() + 1, 1));
        // left six just to show exception handling
        table = new JTable(this.tableModel);
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
        tempPanel.add(optionListPanel, BorderLayout.WEST);
        // Main panel for things related to table
        // Added to mainPanel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
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

    public JMenu createJMenu(String name, int keyEvent, boolean enable) {
		JMenu jMenu = new JMenu(name);
		jMenu.setMnemonic(keyEvent);
		jMenu.setEnabled(enable);
		return jMenu;
	}

    public JFormattedTextField createNumberTextField() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        NumberFormat floatFormat = new DecimalFormat("#0.00", symbols);
        NumberFormatter floatFormatter = new NumberFormatter(floatFormat);
        floatFormatter.setValueClass(Float.class);
        floatFormatter.setMinimum(Float.NEGATIVE_INFINITY);
        floatFormatter.setMaximum(Float.MAX_VALUE);
        floatFormatter.setAllowsInvalid(true);
        floatFormatter.setCommitsOnValidEdit(true);
        return new JFormattedTextField(floatFormatter);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public String getInputValue() {
        return wartosc.getText();
    }
    public int getInputRow() {
        return (int) wiersz.getValue();
    }
    public int getInputColumn() {
        return (int) kolumna.getValue();
    }

    public void updateResult(String text)
    {
        resultsArea.setText(text);
    }
    public void addToResult(String text)
    {
        resultsArea.append(text);
    }

    public JTable getTable() {
        return table;
    }
    public void getHelp()
    {
        if (helpWindow != null) {
            helpWindow.setVisible(true);
        } else {
            helpWindow = new HelpDoc();
            helpWindow.setVisible(true);
        }
    }
    public void getAbout()
    {
        if (authorWindow != null) {
            authorWindow.setVisible(true);
        } else {
            authorWindow = new Author();
            authorWindow.setVisible(true);
        }
    }
}
        


package proj1;

import javax.swing.*;

import java.text.MessageFormat;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Aplikacja extends JFrame implements ActionListener{

    // zmienne do menu
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, helpMenu, viewMenu, calcMenu;
    private JMenuItem newMenuItem, openMenuItem, saveMenuItem, exitMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem,
                        aboutMenuItem, authorMenuItem;
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

    static final long serialVersionUID = 1L;
    static final String TITLE = "Aplikacja";
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    public Aplikacja(){
        super(TITLE);
        setSize(WIDTH, HEIGHT);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        CreateGUI();

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = createJMenu("Plik", KeyEvent.VK_P, true);
        editMenu = createJMenu("Edycja", KeyEvent.VK_E, false);
        viewMenu = createJMenu("Widok", KeyEvent.VK_W, false);
        calcMenu = createJMenu("Obliczenia", KeyEvent.VK_O, false);
        helpMenu = createJMenu("Pomoc", KeyEvent.VK_H, true);
        newMenuItem = new JMenuItem("Nowy");
        openMenuItem = new JMenuItem("Otwórz");
        saveMenuItem = new JMenuItem("Zapisz");
        exitMenuItem = new JMenuItem("Wyjście");
        cutMenuItem = new JMenuItem("Wytnij");
        copyMenuItem = new JMenuItem("Kopiuj");
        pasteMenuItem = new JMenuItem("Wklej");
        aboutMenuItem = new JMenuItem("O programie");
        authorMenuItem = new JMenuItem("Autor");
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
        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);
        authorMenuItem.addActionListener(this);
    }

    private ImageIcon createImageIcon(String path, Integer... size) {
//        ImageIcon img = new ImageIcon(System.getProperty("user.dir") + path);
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
        toolBar.add(saveButton);
        printButton = new JButton(createImageIcon("/grafika/printer.png"));
        toolBar.add(printButton);
        loadButton = new JButton(createImageIcon("/grafika/load.png"));
        toolBar.add(loadButton);
        toolBar.add(Box.createHorizontalStrut(10));

        addButton = new JButton(createImageIcon("/grafika/add.png"));
        toolBar.add(addButton);
        clearButton = new JButton(createImageIcon("/grafika/clear.png"));
        toolBar.add(clearButton);
        fillButton = new JButton(createImageIcon("/grafika/disc.png"));
        toolBar.add(fillButton);
        toolBar.add(Box.createHorizontalStrut(10));
    
        sumButton = new JButton(createImageIcon("/grafika/sum.png"));
        toolBar.add(sumButton);
        averageButton = new JButton(createImageIcon("/grafika/average.png"));
        toolBar.add(averageButton);
        minButton = new JButton(createImageIcon("/grafika/min.png"));
        toolBar.add(minButton);
    

        maxButton = new JButton(createImageIcon("/grafika/max.png"));
        toolBar.add(maxButton);
        toolBar.add(Box.createHorizontalStrut(10));
    
        authorButton = new JButton(createImageIcon("/grafika/about.png"));
        toolBar.add(authorButton);
        helpButton = new JButton(createImageIcon("/grafika/help.png"));

        toolBar.add(helpButton);
        add(toolBar, BorderLayout.PAGE_START);

        saveButton.addActionListener(this);
        printButton.addActionListener(this);
        loadButton.addActionListener(this);
        addButton.addActionListener(this);
        clearButton.addActionListener(this);
        fillButton.addActionListener(this);
        sumButton.addActionListener(this);
        averageButton.addActionListener(this);
        minButton.addActionListener(this);
        maxButton.addActionListener(this);
        authorButton.addActionListener(this);
        helpButton.addActionListener(this);
    }

    private void createBody() {

        JLabel wartoscLabel = new JLabel("Wprowadź liczbę:");
        wartoscLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        wartosc = createNumberTextField();
        wartosc = new JFormattedTextField(0);
        wartosc.setMargin(new Insets(5, 5, 5, 5));

        JLabel wierszLabel = new JLabel("Numer wiersza:");
        wierszLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wiersz = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

        JLabel kolumnaLabel = new JLabel("Numer kolumny:");
        kolumnaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        kolumna = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
        // left six just to show exception handling
        table = new JTable(5, 5);
        this.handleClear();
        bodyAddButton = new JButton("Dodaj    ", createImageIcon("/grafika/add.png", 20));
        bodyAddButton.setToolTipText("Body dodaj");
        bodyClearButton = new JButton("Wyzeruj", createImageIcon("/grafika/clear.png", 20));
        bodyClearButton.setToolTipText("Body wyzeruj");
        bodyFillButton = new JButton("Wypełnij", createImageIcon("/grafika/disc.png", 20));
        bodyFillButton.setToolTipText("Body wypełnij");
        bodySaveButton = new JButton("Zapisz  ", createImageIcon("/grafika/save.png", 20));
        bodySaveButton.setToolTipText("Body zapisz");
        bodyAddButton.setPreferredSize(new Dimension(110, 30));
        bodyClearButton.setPreferredSize(new Dimension(110, 30));
        bodyFillButton.setPreferredSize(new Dimension(110, 30));
        bodySaveButton.setPreferredSize(new Dimension(110, 30));
        resultsArea= new JTextArea(5, 5);
    

        optionsList= new JList<>(new String[]{"Suma elementów", "Średnia elementów", "Wartość max i min"});
        optionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        optionsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedOption = optionsList.getSelectedValue();
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
        });
        
        // Panel for fields adding values to table
        // Added to mainPanel
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

        bodyAddButton.addActionListener(this);
        bodyClearButton.addActionListener(this);
        bodyFillButton.addActionListener(this);
        bodySaveButton.addActionListener(this);
    }
    
    private void CreateGUI()
    {
        createMenuBar();
        createToolBar();
        createBody();
    }

    public JMenu createJMenu(String name, int keyEvent, boolean enable) {
		JMenu jMenu = new JMenu(name);
		jMenu.setMnemonic(keyEvent);
		jMenu.setEnabled(enable);
		return jMenu;
	}


    private void handleAdd()
    {
        try {
            int row = (int) wiersz.getValue();
            int column = (int) kolumna.getValue();
            Object value = (int) wartosc.getValue();
            table.getModel().setValueAt(value, row -1, column -1);
        } catch( NullPointerException np) {
            JOptionPane.showMessageDialog(this, "Brak danych do wprowadzenia", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException se) {
            JOptionPane.showMessageDialog(this, "Zły format liczby", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException te) {
            JOptionPane.showMessageDialog(this, "Zły wiersz lub kolumna", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleClear(){
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.getModel().setValueAt(0, i, j);
            }
        }
    }

    private void handleFill(){
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                int value = (int) (Math.random() * -200 + 100);
                table.getModel().setValueAt(value, i, j);
            }
        }
    }
    private void handleSum(){
        long sum = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getModel().getValueAt(i, j);
                sum += (int) value;
            }
        }
        resultsArea.setText("Suma: " + sum);
    }

    private void handleAvg()
    {
        long sum = 0;
        int count = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getModel().getValueAt(i, j);
                sum += (int) value;
                count++;
            }
        }
        double average = count > 0 ? sum / count : 0;
        resultsArea.setText("Średnia: " + average);
    }

    private void handleMin(Boolean... args)
    {
        long min = Integer.MAX_VALUE;
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getModel().getValueAt(i, j);
                if ((int) value < min) {
                    min = (int) value;
                }
            }
        }
        if (min == Integer.MAX_VALUE) min = 0;
        if(args.length == 0)
        {
            resultsArea.setText("Minimum: " + min);
        }
        else
        {
            resultsArea.append("Minimum: " + min + "\n");
        }
        
    
    }

    private void handleMax(Boolean... args)
    {
        long max = Integer.MIN_VALUE;
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getModel().getValueAt(i, j);
                if ((int) value > max) {
                    max = (int) value;
                }
            }
        }
        if (max == Integer.MIN_VALUE) max = 0;
        if(args.length == 0)
        {
            resultsArea.setText("Maksimum: " + max);
        }
        else
        {
            resultsArea.append("\n" + "Maksimum: " + max + "\n");
        }
    }

    private void handleMinMax()
    {
        handleMin();
        handleMax(true);
    }

    private void handlePrint()
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

                JTable.PrintMode mode = JTable.PrintMode.FIT_WIDTH;
                try {
                    MessageFormat header = new MessageFormat("Table Print");
                    MessageFormat footer = new MessageFormat("- {0} -");
                    Boolean cont = table.print(mode, header, footer);
                    if (cont) {
                        return PAGE_EXISTS;
                    } else {
                        return NO_SUCH_PAGE;
                    }
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(null, "Printing failed: " + ex.getMessage());
                    return NO_SUCH_PAGE;
                }
            }
        });
        
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Printing failed: " + ex.getMessage());
            }
    }

    private void handleSave(){
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        writer.write(table.getValueAt(i, j).toString() + ",");
                    }
                    writer.write("\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Saving failed: " + ex.getMessage());
            }
        }
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
                        table.setValueAt(cells[col], row, col);
                    }
                    row++;
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Loading failed: " + ex.getMessage());
            }
    }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == newMenuItem) {
            handleClear();
        } else if (source == openMenuItem) {
            handleLoad();
        } else if ((source == saveMenuItem) || (source == saveButton) || (source == bodySaveButton)) {
            handleSave();
        } else if (source == cutMenuItem) {
            System.out.println("Wytnij");
        } else if (source == copyMenuItem) {
            System.out.println("Kopiuj");
        } else if (source == pasteMenuItem) {
            System.out.println("Wklej");
        } else if ((source == exitMenuItem)) {
            System.exit(0);
        } else if ((source == aboutMenuItem) || (source == helpButton)) {
            if (helpWindow != null) {
                helpWindow.setVisible(true);
            } else {
                helpWindow = new HelpDoc();
                helpWindow.setVisible(true);
            }
        } else if((source == authorMenuItem) || (source == authorButton)){
            if (authorWindow != null) {
                authorWindow.setVisible(true);
            } else {
                authorWindow = new Author();
                authorWindow.setVisible(true);
            }
		} else if((source == addButton) || (source == bodyAddButton))
        {
            handleAdd();
           
        } else if((source == clearButton) || (source == bodyClearButton))
        {
            handleClear();
        } else if((source == fillButton) || (source == bodyFillButton))
        {
            handleFill();
        } else if((source == saveButton) || (source == bodySaveButton))
        {
            System.out.println("Zapisz");
        } else if(source == minButton)
        {
            handleMin();
        } else if(source == maxButton)
        {
            handleMax();
        } else if(source == sumButton)
        {
            handleSum();
        } else if(source == averageButton)
        {
            handleAvg();
        } else if (source == printButton) {
            handlePrint();
        } else if (source == loadButton){
            handleLoad();
        }
    }
    public static void main(String[] args) {

        new Aplikacja();
    }
    
}

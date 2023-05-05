package proj1;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;

public class Vcontroller implements ActionListener{
    private TableModel model;
    private View view;
    private ListModel listModel;

    public Vcontroller() {
        this.model = new TableModel(5, 5);
        this.listModel = new ListModel();
        this.view = new View(model, listModel, this);
        this.view.showView();
    }

    private void handleAdd()
    {
        try {
            this.model.setValueAt(Double.parseDouble(this.view.getInputValue()),(int) view.getInputRow() - 1, (int) view.getInputColumn() - 1);
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
        double min = this.model.getMin();
        if(args.length == 0)
        {
            this.view.updateResult("Minimum: " + min);
        }
        else
        {
            this.view.updateResult("Minimum: " + min + "\n");
        }
        
    
    }

    private void handleMax(Boolean... args)
    {
        double max = this.model.getMax();
        if(args.length == 0)
        {
            this.view.updateResult("Maksimum: " + max);
        }
        else
        {
            this.view.updateResult("\n" + "Maksimum: " + max + "\n");
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

    private void handlePrint()
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        Boolean wasHere = false;
        View view = this.view;
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
                    Boolean cont = view.getTable().print(mode, header, footer);
                    if (cont) {
                        return PAGE_EXISTS;
                    } else {
                        return NO_SUCH_PAGE;
                    }
                } catch (PrinterException ex) {
                    view.showMessage("Printing failed: " + ex.getMessage());
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
                        if (cells[col].equals("null")) {
                            this.model.setValueAt(null, row, col);
                            continue;
                        }
                        this.model.setValueAt(Double.parseDouble(cells[col]), row, col);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed");
        Object source = e.getSource();
        if (source instanceof JButton)
        {
            JButton button = (JButton) source;
            switch (button.getToolTipText()) {
                case "Zapisz":
                    handleSave();
                    break;
                case "Body zapisz":
                    handleSave();
                    break;
                case "Drukuj":
                    handlePrint();
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
    }


    public static void main(String[] args) {
        new Vcontroller();
    }
    
}

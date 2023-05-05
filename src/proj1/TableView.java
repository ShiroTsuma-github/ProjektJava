package proj1;

import javax.swing.*;
import javax.swing.event.TableModelListener;

public class TableView extends JFrame implements TableModelListener {
    
    private JTable table;
    private JSpinner wartosc;
    private JSpinner wiersz;
    private JSpinner kolumna;
    private JList<String> optionsList;

    public TableView() {
        super("My App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table = new JTable(new TableModel(5, 5));
        table.getModel().addTableModelListener(this);

        optionsList = new JList<>(new String[]{"Suma elementów", "Średnia elementów", "Wartość max i min"});
        optionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        optionsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedOption = optionsList.getSelectedValue();
                switch (selectedOption) {
                    case "Suma elementów":
                        // handleSum();
                        break;
                    case "Średnia elementów":
                        // handleAvg();
                        break;
                    case "Wartość max i min":
                        // handleMinMax();
                        break;
                    default:
                        break;
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 3, 5, 5));
        controlPanel.add(new JLabel("Wartość:"));
        controlPanel.add(wartosc = new JSpinner());
        controlPanel.add(new JLabel("Wiersz:"));
        controlPanel.add(wiersz = new JSpinner());
        controlPanel.add(new JLabel("Kolumna:"));
        controlPanel.add(kolumna = new JSpinner());

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints;
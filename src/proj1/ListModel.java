package proj1;


import javax.swing.AbstractListModel;

public class ListModel extends AbstractListModel<String> {
    private String[] options = {"Suma elementów", "Średnia elementów", "Wartość max i min"};

    public int getSize() {
        return options.length;
    }

    public String getElementAt(int index) {
        return options[index];
    }
    public String[] getOptions() {
        return options;
    }
}
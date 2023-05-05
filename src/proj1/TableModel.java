package proj1;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel{
    private Object[][] data;

    public TableModel(int rows, int columns) {
        data = new Object[rows][columns];
    }

    public Object getValueAt(int row, int column) {
        return data[row][column];
    }

    public void setValueAt(Object value, int row, int column) {
        data[row][column] = value;
        fireTableCellUpdated(row, column);
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return data[0].length;
    }

    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public void addTableModelListener(TableModelListener listener) {
        listenerList.add(TableModelListener.class, listener);
    }

    public void removeTableModelListener(TableModelListener listener) {
        listenerList.remove(TableModelListener.class, listener);
    }
}


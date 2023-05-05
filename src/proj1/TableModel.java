package proj1;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel{
    private Object[][] data;
    

    public TableModel(int rows, int columns) {
        data = new Object[rows][columns];
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++){
                data[i][j] = 0;
            }
        }
    }

    public Object getValueAt(int row, int column) {
        return data[row][column];
    }

    public void setValueAt(Object value, int row, int column) throws NullPointerException, NumberFormatException, ArrayIndexOutOfBoundsException{
        try {
            int rowPos = (int) row;
            int colPos = (int) column;
            if (value instanceof Integer) {
                data[rowPos][colPos] = value;
            }
            else{
                data[rowPos][colPos] = Integer.parseInt((String) value);
            }
        } catch( NullPointerException np) {
            throw new NullPointerException();
        } catch (NumberFormatException se) {
            throw new NumberFormatException();
        } catch (ArrayIndexOutOfBoundsException te) {
            throw new ArrayIndexOutOfBoundsException();
        }
        fireTableDataChanged();
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

    public int getSum()
    {
        int sum = 0;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if(data[i][j] == null) {
                    continue;
                }
                sum += (int) data[i][j];
            }
        }
        return sum;
    }

    public double getAverage()
    {
        int sum = 0;
        int count = 0;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if(data[i][j] == null) {
                    continue;
                }
                sum += (int) data[i][j];
                count ++;
            }
        }
        count = count == 0 ? 1 : count;
        return (double) sum / count;
    }

    public int getMin()
    {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if(data[i][j] == null) {
                    continue;
                }
                if ((int) data[i][j] < min) {
                    min = (int) data[i][j];
                }
            }
        }
        min = min == Integer.MAX_VALUE ? 0 : min;
        return min;
    }

    public int getMax()
    {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if(data[i][j] == null) {
                    continue;
                }
                if ((int) data[i][j] > max) {
                    max = (int) data[i][j];
                }
            }
        }
        max = max == Double.NEGATIVE_INFINITY ? 0 : max;
        return max;
    }

    public void fillRandom()
    {
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                double value = Math.random() * -200 + 100;
                this.data[i][j] = (int) value;
            }
        }
        fireTableDataChanged();
    }

    public void clear()
    {
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                this.data[i][j] = null;
                // Maybe need to set to 0 instead of null?
            }
        }
        fireTableDataChanged();
    }

    public void addTableModelListener(TableModelListener listener) {
        listenerList.add(TableModelListener.class, listener);
    }

    public void removeTableModelListener(TableModelListener listener) {
        listenerList.remove(TableModelListener.class, listener);
    }
}


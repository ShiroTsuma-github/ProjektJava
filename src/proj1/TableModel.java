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

    public void setValueAt(Object value, int row, int column) throws NullPointerException, NumberFormatException, ArrayIndexOutOfBoundsException{
        try {
            int rowPos = (int) row;
            int colPos = (int) column;
            if (value == null) {
                data[rowPos][colPos] = value;
            } else if (value instanceof Double) {
                data[rowPos][colPos] = value;
            }
            else{
                data[rowPos][colPos] = Double.parseDouble((String) value);
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

    public double getSum()
    {
        double sum = 0;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if(data[i][j] == null) {
                    continue;
                }
                sum += (double) data[i][j];
            }
        }
        return sum;
    }

    public double getAverage()
    {
        double sum = 0;
        int count = 0;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if(data[i][j] == null) {
                    continue;
                }
                sum += (double) data[i][j];
                count ++;
            }
        }
        count = count == 0 ? 1 : count;
        return (double) sum / count;
    }

    public double getMin()
    {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if(data[i][j] == null) {
                    continue;
                }
                if ((double) data[i][j] < min) {
                    min = (double) data[i][j];
                }
            }
        }
        min = min == Double.POSITIVE_INFINITY ? 0 : min;
        return min;
    }

    public double getMax()
    {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                if(data[i][j] == null) {
                    continue;
                }
                if ((double) data[i][j] > max) {
                    max = (double) data[i][j];
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
                this.data[i][j] = value;
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


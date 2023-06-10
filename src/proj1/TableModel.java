package proj1;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * Klasa reprezentująca model tabeli.
 * Rozszerza klasę AbstractTableModel, która dostarcza podstawowe funkcjonalności modelu tabeli.
 * Przechowuje dane tabeli jako dwuwymiarową tablicę obiektów.
 */
public class TableModel extends AbstractTableModel{
    private Object[][] data;
    private int tableUpdateCount = 0;
    private boolean editable = true;
    private String[] columns = {"1", "2", "3", "4", "5"};
    

    /**
     * Konstruktor klasy TableModel.
     * Inicjalizuje tablicę danych o rozmiarze rows x columns i wypełnia ją wartościami początkowymi (0).
     */
    public TableModel(int rows, int columns) {
        data = new Object[rows][columns];
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++){
                data[i][j] = 0;
            }
        }
    }

     /**
     * Metoda zwracająca klasę kolumny o danym indeksie.
     * Zwraca klasę obiektu z pierwszego wiersza i danym indeksie kolumny.
     */
    @Override
        public Class<?> getColumnClass(int columnIndex) {
            return data[0][columnIndex].getClass();
        }

    /**
     * Metoda zwracająca nazwę kolumny o danym indeksie.
     * Zwraca nazwę kolumny o danym indeksie z tablicy columns.
     */
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    /**
     * Metoda zmieniająca dostępność edycji komórek tabeli.
     * Ustawia wartość flagi editable na podstawie parametru editable.
     */
    public void toggleCellsEdit(boolean editable)
    {
        this.editable = editable;
    }

    /**
     * Metoda zwiększająca licznik aktualizacji tabeli.
     * Inkrementuje wartość tableUpdateCount.
     */
    public void incUpdateCount(){
        tableUpdateCount++;
    }

    /**
     * Metoda zwracająca liczbę aktualizacji tabeli.
     * Zwraca wartość tableUpdateCount.
     */
    public int getUpdateCount(){
        return tableUpdateCount;
    }

    /**
     * Metoda zwracająca wartość w komórce o określonym wierszu i kolumnie.
     * Zwraca wartość z tablicy data dla danego wiersza i kolumny.
     */
    public Object getValueAt(int row, int column) {
        return data[row][column];
    }

    
    /**
     * Metoda ustawiająca wartość w komórce o określonym wierszu i kolumnie.
     * Ustawia wartość w tablicy data dla danego wiersza i kolumny na podstawie podanego value.
     * Rzuca wyjątki NullPointerException, NumberFormatException i ArrayIndexOutOfBoundsException w przypadku niepoprawnych danych.
     * Wywołuje metodę fireTableDataChanged(), informując o zmianie danych w modelu.
     */
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

    /**
     * Metoda zwracająca liczbę wierszy w tabeli.
     * Zwraca długość tablicy data.
     */
    public int getRowCount() {
        return data.length;
    }

    /**
     * Metoda zwracająca liczbę kolumn w tabeli.
     * Zwraca długość pierwszego wiersza tablicy data.
     */
    public int getColumnCount() {
        return data[0].length;
    }

    /**
     * Metoda sprawdzająca czy komórka o danym wierszu i kolumnie jest edytowalna.
     * Zwraca wartość flagi editable, określającą czy komórki tabeli są edytowalne.
     */
    public boolean isCellEditable(int row, int column) {
        return this.editable;
    }

    /**
     * Metoda obliczająca sumę wartości w tabeli.
     * Przechodzi przez wszystkie komórki tabeli, sumując wartości.
     * Ignoruje puste komórki.
     * Zwraca sumę wartości.
     */
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

    /**
     * Metoda obliczająca średnią wartości w tabeli.
     * Przechodzi przez wszystkie komórki tabeli, sumując wartości i zliczając liczbę komórek.
     * Ignoruje puste komórki.
     * Jeśli liczba komórek wynosi 0, zwraca 1, aby uniknąć dzielenia przez zero.
     * Zwraca średnią wartości.
     */
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

    /**
     * Metoda znajdująca najmniejszą wartość w tabeli.
     * Przechodzi przez wszystkie komórki tabeli, porównując wartości i aktualizując najmniejszą wartość.
     * Ignoruje puste komórki.
     * Jeśli tabela nie zawiera żadnych wartości, zwraca 0.
     * Zwraca najmniejszą wartość.
     */
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

    /**
     * Metoda znajdująca największą wartość w tabeli.
     * Przechodzi przez wszystkie komórki tabeli, porównując wartości i aktualizując największą wartość.
     * Ignoruje puste komórki.
     * Jeśli tabela nie zawiera żadnych wartości, zwraca 0.
     * Zwraca największą wartość.
     */
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

    /**
     * Metoda wypełniająca tabelę losowymi wartościami.
     * Przechodzi przez wszystkie komórki tabeli i przypisuje im losową wartość z zakresu (-200, 100).
     * Wywołuje metodę fireTableDataChanged(), informując o zmianie danych w modelu.
     */
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

    /**
     * Metoda czyszcząca zawartość tabeli.
     * Przechodzi przez wszystkie komórki tabeli i ustawia ich wartość na 0.
     * Wywołuje metodę fireTableDataChanged(), informując o zmianie danych w modelu.
     */
    public void clear()
    {
        fireTableDataChanged();
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                this.data[i][j] = 0;
            }
        }
        fireTableDataChanged();
    }

    /**
     * Metoda dodająca słuchacza zmian modelu tabeli.
     * Dodaje słuchacza do listy listenerów.
     */
    public void addTableModelListener(TableModelListener listener) {
        listenerList.add(TableModelListener.class, listener);
    }

    /**
     * Metoda usuwająca słuchacza zmian modelu tabeli.
     * Usuwa słuchacza z listy listenerów.
     */
    public void removeTableModelListener(TableModelListener listener) {
        listenerList.remove(TableModelListener.class, listener);
    }
}





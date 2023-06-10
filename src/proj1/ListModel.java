package proj1;


import javax.swing.AbstractListModel;

/**
 * Klasa reprezentująca model listy opcji.
 * Rozszerza klasę AbstractListModel, która dostarcza podstawowe funkcjonalności modelu listy.
 * Przechowuje tablicę opcji jako dane modelu.
 */
public class ListModel extends AbstractListModel<String> {
    private String[] options = {"Suma elementów", "Średnia elementów", "Wartość max i min"};

    /**
     * Metoda zwracająca liczbę elementów w modelu.
     * Zwraca rozmiar tablicy opcji.
     */
    public int getSize() {
        return options.length;
    }

    /**
     * Metoda zwracająca element listy dla określonego indeksu.
     * Zwraca opcję z tablicy opcji dla określonego indeksu.
     */
    public String getElementAt(int index) {
        return options[index];
    }
    
    /**
     * Metoda zwracająca tablicę opcji.
     * Zwraca referencję do tablicy opcji.
     */
    public String[] getOptions() {
        return options;
    }
}
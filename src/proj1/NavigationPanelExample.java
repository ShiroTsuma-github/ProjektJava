package proj1;

import javax.swing.*;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

import java.awt.*;

public class NavigationPanelExample {
    public static void main(String[] args) {
        // Tworzenie okna głównego
        JFrame frame = new JFrame("Panel nawigacyjny");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        // Tworzenie kontenera głównego
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Tworzenie panelu nawigacyjnego
        JTaskPane taskPane = new JTaskPane();

        // Tworzenie kategorii w panelu nawigacyjnym
        JTaskPaneGroup group1 = new JTaskPaneGroup();
        group1.setExpanded(true); // Rozwiń kategorię na starcie
        // group1.setTitle("Kategoria 1");

        // Dodawanie elementów do kategorii
        group1.add(new JButton("Element 1"));
        group1.add(new JButton("Element 2"));
        group1.add(new JButton("Element 3"));

        // Dodawanie kategorii do panelu nawigacyjnego
        taskPane.add(group1);

        JTaskPaneGroup group2 = new JTaskPaneGroup();
        group2.setExpanded(false); // Zwiń kategorię na starcie
        // group2.setTitle("Kategoria 2");

        group2.add(new JButton("Element 4"));
        group2.add(new JButton("Element 5"));
        group2.add(new JButton("Element 6"));

        taskPane.add(group2);

        // Dodawanie panelu nawigacyjnego do kontenera głównego
        contentPane.add(new JScrollPane(taskPane), BorderLayout.CENTER);

        // Wyśrodkowanie okna na ekranie
        frame.setLocationRelativeTo(null);

        // Wyświetlanie okna
        frame.setVisible(true);
    }
}
package proj1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * Klasa Author reprezentuje okno dialogowe wyświetlające informacje o programie.
 * Rozszerza klasę JDialog i implementuje interfejs ActionListener.
 */
public class Author extends JDialog implements ActionListener{
    
	private JLabel l0, l1, l2, l3, l4, l5;
	private JLabel lBorder, lIcon;
	private JButton jBOk;
	private Font font1 = new Font("TimesRoman", Font.PLAIN, 22);
	private Font font2 = new Font("TimesRoman", Font.PLAIN, 12);
	private Font font3 = new Font("TimesRoman", Font.BOLD, 12);
	private Font font4 = new Font("TimesRoman", Font.PLAIN, 12);
	private Border line = null;

	/**
     * Konstruktor klasy Author.
     * Inicjalizuje i konfiguruje elementy interfejsu użytkownika.
     */
    public Author()
    {
        setTitle("Informacje o programie");
		setSize(370,255);
		setResizable(false);
        addWindowListener	(new WindowAdapter(){ 
			public void windowClosing(WindowEvent e){ 
				setVisible(false);				
			}	
		});	
        Dimension dialogSize = getSize();		
		// pobranie rozdzielczosci pulpitu
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
		if(dialogSize.height > screenSize.height) 
			dialogSize.height = screenSize.height;
		if(dialogSize.width > screenSize.width)
			dialogSize.height = screenSize.width;
			
		// rozmieszczenie aplikacji na srodku ekranu
		setLocation((screenSize.width-dialogSize.width)/2,   
						(screenSize.height-dialogSize.height)/2);
		
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);

        try {
			lIcon = new JLabel(new ImageIcon(
				getClass().getResource("/grafika/author_logo.png")));
		} 
		catch(Exception e) { 
			lIcon = new JLabel();
		}
		l0 = new JLabel("Projekt Aplikacji");
		l0.setFont(font1);
		l0.setHorizontalAlignment(SwingConstants.CENTER);
		l1 = new JLabel("wersja 0.0.1");
		l1.setFont(font2);
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l2 = new JLabel("Copyright (C) by 2023");
		l2.setFont(font2);
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l3 = new JLabel("Tomasz Góralski");
		l3.setFont(font3);
		l3.setHorizontalAlignment(SwingConstants.CENTER);
		l4 = new JLabel("Politechnika Koszalinska - WEiI");
		l4.setFont(font3);
		l4.setHorizontalAlignment(SwingConstants.CENTER);
		l5 = new JLabel("e-mail: email@gmail.pl");
		l5.setFont(font4);
		lBorder = new JLabel(""); 
		jBOk = new JButton("Ok");
		jBOk.addActionListener(this);
		line = new EtchedBorder(EtchedBorder.LOWERED);
		
		lIcon.setBounds(10,15,120,150);
		l0.setBounds(135,20,210,30);
		l1.setBounds(135,50,210,30);
		l2.setBounds(135,100,210,20);
		l3.setBounds(135,120,210,20);
		l4.setBounds(135,140,210,20);
		lBorder.setBounds(5,175,dialogSize.width-14,2);
		l5.setBounds(10,184,200,20);
		jBOk.setBounds(dialogSize.width-75,182,60,25);
		
		lBorder.setBorder(line);
		contentPane.add(l0);
		contentPane.add(l1);
		contentPane.add(l2);
		contentPane.add(l3);
		contentPane.add(l4);
		contentPane.add(l5);
		contentPane.add(lBorder);
		contentPane.add(jBOk);
		contentPane.add(lIcon);
	}
	/**
     * Obsługuje zdarzenie ActionListener.
     * Sprawdza źródło zdarzenia i wykonuje odpowiednie akcje w przypadku naciśnięcia przycisku "Ok".
     *
     * @param eAE Zdarzenie ActionListener.
     */
    public void actionPerformed(ActionEvent eAE) {
		if(eAE.getSource() == jBOk) {
			setVisible(false);
		}
	}
    
}

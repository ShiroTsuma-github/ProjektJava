package proj1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class HelpDoc extends JDialog{
        
    private JEditorPane editorPane;
    private URL opisUrl;

    public HelpDoc(){
        super();
        setSize(400, 300);
        setTitle("Pomoc");
        setVisible(true);
        setResizable(true);

        Dimension dialogSize = getSize();		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
        if(dialogSize.height > screenSize.height) 
            dialogSize.height = screenSize.height;
        if(dialogSize.width > screenSize.width)
            dialogSize.height = screenSize.width;
        setLocation((screenSize.width-dialogSize.width)/2,   
            (screenSize.height-dialogSize.height)/2);

        setLayout(new BorderLayout());

        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        opisUrl = proj1.HelpDoc.class.getResource(
                "/pomoc/index.html");

        setURLPage();


        editorPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent event) {
                if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        editorPane.setPage(event.getURL());
                    }
                    catch(IOException e) {
                        editorPane.setText("Exception: "+e);
                    }
                }
            }

            // @Override
            // public void hyperlinkUpdate(HyperlinkEvent e) {
            //     throw new UnsupportedOperationException("Unimplemented method 'hyperlinkUpdate'");
            // }
        }); 

        this.add(new JScrollPane(editorPane), BorderLayout.CENTER);
    }
    private void setURLPage() {
        try {
            editorPane.setPage(opisUrl);
        }
        catch(Exception e) {
            editorPane.setText("Nie mozna otworzy plikow z pomoca " + opisUrl);
        } 
    }
}


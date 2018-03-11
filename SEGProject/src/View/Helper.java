package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by asherfischbaum on 18/04/2017.
 */

// Class to create and open the help document for use of the user
public class Helper extends JFrame{

    public Helper()
    {
        this.setSize(new Dimension(400, 500));
        this.setMinimumSize(new Dimension(400, 500));
        this.setResizable(true);
    }

    public void init()
    {
    	
    	// Main panel the document will be displayed in 
        JPanel main = new JPanel();

        // TextArea to display the user guide 
        JTextArea helpText = new JTextArea();
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setText(Controller.getContr().getHelp());
        helpText.setEditable(false);

        // Allows the user guide to be scrollable and resizable
        JScrollPane scroller = new JScrollPane(helpText);
        
        // Only allows vertical scroll
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        main.setLayout(new GridLayout(1, 1));
        main.add(scroller);

        this.setContentPane(main);
        this.setVisible(true);
    }
}

/*
 * Created on May 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package SRC.COM.SUNRISE.CLIENT;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

/**
 * @author user6
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AlphaNumListener implements KeyListener
{
    private int textLength;

    public AlphaNumListener()
    {
        textLength = 1000;
    }
    public AlphaNumListener(int limit)
    {
        textLength=limit;
    }
    public void keyPressed(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) 
    {
        JTextField source = (JTextField)e.getSource();
        if((source.getText().length()>=textLength && e.getKeyChar()!='\b') || (e.getKeyChar()<'0' || e.getKeyChar()>'9') && (e.getKeyChar()<'A' || e.getKeyChar()>'Z') && (e.getKeyChar()<'a' || e.getKeyChar()>'z') && (e.getKeyChar()!='\b' && e.getKeyChar()!=' '))
            e.consume();
    }

   
}

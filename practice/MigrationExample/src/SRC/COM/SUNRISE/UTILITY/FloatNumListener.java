package SRC.COM.SUNRISE.UTILITY;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FloatNumListener implements KeyListener
{
	private int selection_period=1;
	
	private JTextField jt;
	
    public void keyPressed(KeyEvent e) {
    	jt=(JTextField)e.getSource();	
    	jt.setHorizontalAlignment(SwingConstants.RIGHT);
  
    	if(e.getKeyCode()==KeyEvent.VK_RIGHT ){
    		setSelection(e);
    	}
    	
    	if((e.getKeyChar()<'0' || e.getKeyChar()>'9')) {
    		e.consume();
    	}
    }
    
    public void keyTyped(KeyEvent e)
    {
        jt=(JTextField)e.getSource();	
        jt.setHorizontalAlignment(SwingConstants.RIGHT);
	
        try {
        	if(Long.parseLong(jt.getText().trim().substring(0,jt.getText().indexOf("."))) == 0 && Integer.parseInt(jt.getText().trim().substring(jt.getText().indexOf(".")+1,jt.getText().length())) == 0 && selection_period == 1) {
        		jt.setText("00.00");
        		jt.select(0,jt.getText().indexOf("."));
        		selection_period = 1;
        	} /*else if ( selection_period == 1 && jt.getText().trim().substring(0,jt.getText().indexOf(".")).length() != 0 && Long.parseLong(jt.getText().trim().substring(0,jt.getText().indexOf("."))) != 0) {
        		jt.setCaretPosition(jt.getText().indexOf("."));
        		selection_period = 1;
        	}*//* else if (selection_period == 1) {
        		jt.select(0,jt.getText().indexOf("."));
        		selection_period = 1;
        	}*/
        } catch( Exception nf ) {
        	jt.setText("00.00");
        	jt.select(0,jt.getText().indexOf("."));
        	selection_period = 1;
        }
        if( e.getKeyChar() == '.' ) {
        	setSelection(e);
        }else if( e.getKeyChar() == '\b' ) {
        	if(selection_period == 2 && jt.getCaretPosition() == jt.getText().indexOf(".")+1) {
        		e.consume();
        	}
        } else if((e.getKeyChar()<'0' || e.getKeyChar()>'9') ) {
            e.consume();
        } else if(jt.getSelectionStart() == 0 && jt.getSelectionEnd() == jt.getText().length()) {
        	jt.select(0,jt.getText().indexOf("."));
    		selection_period = 1;
        } else if(selection_period == 2) {
        	if(jt.getText().trim().substring(jt.getText().indexOf(".")+1,jt.getText().length()).length() > 1) {
        		jt.select(jt.getText().indexOf(".")+1,jt.getText().length());
        		selection_period = 2;
        		// e.consume();
        	}
        } else {
        	/*jt.select(0,jt.getText().indexOf("."));
    		selection_period = 1;*/
        }
       
    }
    
    public void keyReleased(KeyEvent e){}
    
    private void setSelection(KeyEvent e) {
    	e.consume();
		if (selection_period == 1) {
			jt.select(jt.getText().indexOf(".")+1,jt.getText().length());
			selection_period = 2;
		} else {
			jt.select(0,jt.getText().indexOf("."));
    		selection_period = 1;
		}
    }
}

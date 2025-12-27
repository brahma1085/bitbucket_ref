package SRC.COM.SUNRISE.UTILITY;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NumListener implements KeyListener{

	
	private int textLength;
	public NumListener(){
		textLength = 10;
		
	}
	public NumListener(int limit){
		textLength=limit;
	}
	public void keyPressed(KeyEvent e){}
	
	public void keyTyped(KeyEvent e)
	{
		JTextField source = (JTextField)e.getSource();
			
		source.setHorizontalAlignment(SwingConstants.RIGHT);
		/*if(source.getText().trim().equals("") && e.getKeyChar()=='0')
		{
			System.out.println(" inside out");
			e.consume();//Consuming the first zero
		}*/
		if((source.getText().length()>=textLength && e.getKeyChar()!='\b') || ((e.getKeyChar()<'0' || e.getKeyChar()>'9') && e.getKeyChar()!='\b'))
			e.consume();
	}
	public void keyReleased(KeyEvent e)
	{
	}
	
}

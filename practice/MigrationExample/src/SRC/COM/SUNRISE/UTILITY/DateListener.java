package SRC.COM.SUNRISE.UTILITY;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import SRC.COM.SUNRISE.UTILITY.EXCEPTION.DateFormateException;







public class DateListener  implements KeyListener, FocusListener{
	
	
	
	private int selection_period=1;
	private int first_selection=0;
	private int second_selection=0;
	private int third_selection=0;
	private int current_selection=1;
	private char previous_char='1';
	private JTextField jt;
public void keyPressed(KeyEvent e)
{
	try{
		jt=(JTextField)e.getSource();
		jt.removeFocusListener(this);
		jt.addFocusListener(this);
		if(jt.getText().toString().trim().length()<1){
            //ship.....28/05/2007
			//jt.setText("dd/mm/yyyy");
            jt.setText(Validation.getSysDate());
            /////////
			jt.select(0,2);
			selection_period=2;
			first_selection=0;
			current_selection=1;
			e.consume();
		}if(jt.getText().toString().trim().length() > 1 && jt.getText().toString().trim().length() <=10 && e.getKeyCode()==KeyEvent.VK_TAB){
			if(jt.getText().toString().trim().length()<10){
				e.consume();
			}
			else if(jt.getText().toString().trim().length()>10){
				e.consume();
			}
			else if(jt.getText().toString().trim().length()==10){
			
			}
			//e.consume();
		}
		else {
			if(e.getKeyCode()==KeyEvent.VK_RIGHT ){
				e.consume();
				if(selection_period==1 && ( third_selection !=1 && third_selection !=2 && third_selection !=3 )){
					jt.select(0,2);
					selection_period=2;
					first_selection=0;
					current_selection=1;
				}else if(selection_period==2 && first_selection !=1){ // && second_selection !=1
					jt.select(3,5);
					selection_period=3;
					second_selection=0;
					current_selection=2;
				}else if(selection_period==3 && second_selection !=1){
					jt.select(6,10);
					selection_period=1;
					third_selection=0;
					current_selection=3;
				}
			} else {
				e.consume();
			}
		}
	}catch(Exception exe){exe.printStackTrace();}

}


public void keyTyped(KeyEvent e){
	try{
		jt=(JTextField)e.getSource();
		jt.removeFocusListener(this);
		jt.addFocusListener(this);
		if( e.getKeyChar() != KeyEvent.VK_RIGHT && jt.getCaretPosition()>=10 && current_selection != 3){
			e.consume();
		} else if(jt.getText().toString().trim().length()<1){
			jt.setText("dd/mm/yyyy");
			jt.select(0,2);
			selection_period=2;
			first_selection=0;
			current_selection=1;
		} else {
			if((e.getKeyChar()<'0' || e.getKeyChar()>'9') ){
				e.consume();
			} else {
				if(current_selection==1){
					if(first_selection>=2){
						e.consume();
					}  else if(first_selection==0 && (e.getKeyChar()>'3')){
						e.consume();
					} else if(first_selection==1 && previous_char=='3' && (e.getKeyChar()>'1')){
						e.consume();
					} else if(first_selection==1 && previous_char=='0' && (e.getKeyChar()=='0')){
						e.consume();
					} else {
						previous_char = e.getKeyChar();
						first_selection++;
					}
				}
					
				if(current_selection==2 ){
					if(second_selection>=2){
						e.consume();
					} else if(second_selection==0 && (e.getKeyChar()>'1')){
						e.consume();
					} else if(second_selection==1 && previous_char=='1' && (e.getKeyChar()>'2')){
						e.consume();
					} else if(second_selection==1 && previous_char=='0' && (e.getKeyChar()=='0')){
						e.consume();
					} else {
						previous_char = e.getKeyChar();
						second_selection++;
					}
				}
					
				if(current_selection==3){
					if(third_selection>=4){
						e.consume();
					} else if(third_selection==0 && (e.getKeyChar()=='0')){
						e.consume();
					} else {
						third_selection++;
					}
				}
			}
		}
	}catch(Exception exe){exe.printStackTrace();}
	
}

	public void keyReleased(KeyEvent e){}


	public void focusGained(FocusEvent e) {
	
	}


	public void focusLost(FocusEvent e) {
	
		boolean value = false;
		KeyListener[] key_listners;
		try {
			
			key_listners =  jt.getKeyListeners();
			if ( key_listners != null ) {
				for (int i = 0; i < key_listners.length; i++) {
					if (key_listners[i] instanceof DateListener) {
						value = true;
					}
				}
			}
			
			if(value && !jt.getText().toString().trim().equalsIgnoreCase("dd/mm/yyyy")) {
				Validation.checkDate(jt.getText().toString().trim());
				if(jt.getText().toString().trim().length() > 1 && jt.getText().toString().trim().length() < 10 ) {
					throw new DateFormateException("Invalid date format");
				}
			}
		} catch ( DateFormateException de) {
			JOptionPane.showMessageDialog(null,de);
		}
	}

}

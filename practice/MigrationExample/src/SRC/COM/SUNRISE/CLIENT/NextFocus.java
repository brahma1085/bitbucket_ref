/*
 * Created on May 30, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package SRC.COM.SUNRISE.CLIENT;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

/**
 * @author Murugesh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NextFocus implements KeyListener{
	private JComponent next_component;
			
	public NextFocus(JComponent next_component) {
		this.next_component=next_component;
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_Q && e.isAltDown())
			next_component.requestFocus();
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

}

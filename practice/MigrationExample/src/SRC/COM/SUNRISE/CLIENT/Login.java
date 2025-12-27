package SRC.COM.SUNRISE.CLIENT;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import SRC.COM.SUNRISE.SERVER.LoginCheck;
import SRC.COM.SUNRISE.UTILITY.Validation;




public class Login extends JFrame implements  ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextField txt_uid, txt_tml;
	private JPasswordField txt_pwd;
	private JButton button_ok, button_cancel;
	private JPanel panel_con,panel_west,panel_east;
	private JLabel lbl_datetime;
	private int width;
	private int height;
	LoginCheck loginCheck; 
	public Login(){
		//this.setUndecorated(true);
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension screen_dimension=kit.getScreenSize();
		width=(int)screen_dimension.getWidth();
		height=(int)screen_dimension.getHeight();
		panel_west=new JPanel();
		panel_west.add(new JLabel("  "));
		panel_west.setBackground(new Color(163,154,241));
		
		/*panel_east=new JPanel();
		ImageIcon leafIcon = new ImageIcon("./src/images/ACBLENDS.jpg");*/
		
		//panel_east.add(new JLabel(leafIcon));
		//panel_east.setBackground(new Color(163,154,241));
		
		panel_con = (JPanel) getContentPane();
		panel_con.setLayout(new BorderLayout());
		panel_con.setBackground(new Color(253, 254, 233));

		addComponent(new JLabel(" L O G I N   S C R E E N "), panel_con, 100, 10, 180, 20);
		addComponent(lbl_datetime = new JLabel(" " + getSysDate() + "   " + getSysTime()), panel_con, 100, 40, 180, 10);
		addComponent(new JLabel("User name : ", SwingConstants.RIGHT), panel_con, 30,	90, 80, 20);
		addComponent(txt_uid = new JTextField("1044"), panel_con, 120, 90, 100, 20);
		txt_uid.setForeground(Color.BLUE);
		addComponent(new JLabel("Password : ", SwingConstants.RIGHT), panel_con, 30, 130, 80, 20);
		addComponent(txt_pwd = new JPasswordField("1044"), panel_con, 120, 130, 100, 20);
		txt_pwd.setForeground(Color.BLUE);
		addComponent(new JLabel("Tml Name : ", SwingConstants.RIGHT), panel_con, 30, 170, 80, 20);
		addComponent(txt_tml = new JTextField("ca99"), panel_con, 120, 170, 100, 20);
		txt_tml.setForeground(Color.BLUE);
		addComponent(button_ok = new JButton("S U B M I T"), panel_con, 50, 210, 100, 20);
		button_ok.setBackground(new Color(253, 254, 242));
		button_ok.setForeground(Color.BLUE);
		button_ok.addActionListener(this);
		button_ok.requestFocus();

		addComponent(button_cancel = new JButton("C A N C E L"), panel_con, 170, 210, 100, 20);
		button_cancel.addActionListener(this);
		button_cancel.setBackground(new Color(253, 254, 242));
		button_cancel.setForeground(Color.RED);

		addComponent(new JLabel(), panel_con, 50, 90, 80, 20);
		setBounds(width/3, height/4, 320, 270);
		setResizable(false);
		setVisible(true);
		setTitle("Login Screen");
		button_ok.setMnemonic('S');
		button_cancel.setMnemonic('C');
		new timethread();
		//this.getContentPane().add(panel_west,BorderLayout.WEST);
		loginCheck=new LoginCheck();
		//this.getContentPane().add(panel_east,BorderLayout.NORTH);

	}
	public void addComponent(JComponent jt, JPanel jp, int x, int y, int w,	int h) 
    {
		jt.setBounds(x, y, w, h);
		jt.setFont(new Font("Times New Roman", Font.BOLD, 11));
		jp.add(jt);
	}
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource()==button_cancel){
			System.exit(0);
		}
		if(ae.getSource()==button_ok){
			this.setVisible(false);
			if(!txt_uid.getText().trim().equals("")||(txt_uid.getText().trim().length()!=0)) {
				if(!txt_pwd.getText().trim().equals("")||(txt_pwd.getText().trim().length()!=0)) {
					if(!txt_tml.getText().trim().equals("")||(txt_tml.getText().trim().length()!=0)) {
						int res=loginCheck.loginCheck(txt_uid.getText(), txt_pwd.getText(), txt_tml.getText());
						//int res=3;
						System.out.println("Result===>"+res);
						if(res>=1)
							      MainScreen.getInstance(txt_uid.getText(),txt_tml.getText()) ;//new MainScreen(txt_uid.getText(),txt_tml.getText());
						else
							JOptionPane.showMessageDialog(null,"Incorrect login ! ");
							/*new Login();*/
					}
					else {
						JOptionPane.showMessageDialog(null,"Please enter your terminal code");
						this.txt_uid.requestFocus();
						
					}
				}else {
					JOptionPane.showMessageDialog(null,"Please enter your Password");
					this.txt_pwd.requestFocus();
				}
			}else {
				JOptionPane.showMessageDialog(null,"Please enter your login name");
				this.txt_tml.requestFocus();
			}
			
		}
	}
	public static String getSysTime() 
    { 
		return (new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds());
	}
	public static String getSysDate() 
    {  
		Calendar c = Calendar.getInstance();

		try 
        {
			return(Validation.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR))); 
			
		} 
        catch(Exception e) 
        {
			e.printStackTrace();
		}

		return null;
	}
	
	class timethread extends Thread 
    {
		timethread() 
        {
			start();
		}

		public void run() 
        {
			for (;;) 
            {
				try 
                {
					lbl_datetime.setText(" " + getSysDate() + "   " + getSysTime());
				} 
                catch(Exception e) 
                {
					e.printStackTrace();
				}
                
				try 
                {
					Thread.sleep(1000);
				} 
                catch(InterruptedException e) 
                {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) 
    {  
		new Login();
	}
	
}

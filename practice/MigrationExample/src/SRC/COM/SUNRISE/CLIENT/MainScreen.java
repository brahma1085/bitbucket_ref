package SRC.COM.SUNRISE.CLIENT;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import SRC.COM.SUNRISE.SERVER.*;
import SRC.COM.SUNRISE.UTILITY.*;



public class MainScreen extends JFrame implements WindowListener,ActionListener,MouseListener{

	private static MainScreen mainScreen;
	public static JInternalFrame ji = null;	
	JMenuBar jmb=null;

	JMenu master,transaction;
	JMenu mast[] ,tran[] = null ;
	public JMenuItem masteritem0[]=new JMenuItem[11];	

	public static JScrollPane jsp[]=new JScrollPane[1];
	GridBagConstraints gbc=null;	
	public static JPanel con,js,js1;
	JLabel lbl_copyright;
	JLabel lbl_user,lbl_tml,lbl_datetime;
	static public Heading head_panel;
	JPanel main_panel;
	
	public static MainScreen getInstance(String user,String tml) {
		
		if ( mainScreen == null)
			return mainScreen = new MainScreen( user,tml);
		else 
			return mainScreen;
	}
	public static MainScreen getInstance() {
		
		if ( mainScreen == null)
			return null;
		else 
			return mainScreen;
	}
	
	
	public MainScreen(String user,String tml){

		con=(JPanel)getContentPane();
		con.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;  
		gbc.insets = new Insets(0,0,0,0);	
		this.setBackground(new Color(253,254,233));
		head_panel = new Heading(user,tml);
		
		lbl_user=new JLabel(user);
		lbl_tml=new JLabel(tml);
		lbl_datetime=new JLabel("");//+ getSysDate() + "   " + getSysTime())
		ji = new JInternalFrame("SunBank Banking Solution",false,false,true,false);
	
		System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 111");

		ji.setBorder(BorderFactory.createEmptyBorder());
		
		js=(JPanel)ji.getContentPane();
		js.setLayout(new BorderLayout());
		//js.setBorder(BorderFactory.createEtchedBorder(1));
		con.setBackground(new Color(253,254,233));
		js.setBackground(new Color(253,254,233));
		GetDBConnection db = new GetDBConnection();
		this.addWindowListener(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		addComponent(head_panel,con,0,0,1.0,0.0,1,1);
		
		addComponent(ji,con,2,0,1.0,1.0,1,1);

		jmb=new JMenuBar();
		jmb.addMouseListener(this);
		jmb.setBackground(new Color(232,255,238));

		master = new JMenu("Master");
		MenuListener menilis = new MenuListener(this);
		
		master.add(masteritem0[0]=new JMenuItem("Customer Master"));
		master.add(masteritem0[1]=new JMenuItem("Share Master "));
		master.add(masteritem0[2]=new JMenuItem("Account Master"));
		master.add(masteritem0[3]=new JMenuItem("Deposit Master")); 
		//added by Vinay
		master.add(masteritem0[4]=new JMenuItem("Loan Master"));
		
		//added by geetha
		master.add(masteritem0[5]=new JMenuItem("Recurring Deposit"));
		master.add(masteritem0[6]=new JMenuItem("Deposit Transaction Entry"));
		
		
		//added by Vinay
		master.add(masteritem0[7]=new JMenuItem("Loan Sanctioning and Disbursement"));
		master.add(masteritem0[8]=new JMenuItem("Loan Recovery"));
		
		//added for ramnagar
		master.add(masteritem0[9]=new JMenuItem("Vehicle"));
		master.add(masteritem0[10]=new JMenuItem("Property"));
		
		
		/*master.add(masteritem0[2]=new JMenuItem("Customer Master"));
		master.add(masteritem0[3]=new JMenuItem("Deposit Master"));
		master.add(masteritem0[4]=new JMenuItem("Loan Master"));
		master.add(masteritem0[5]=new JMenuItem("ODCC Master "));*/
		
		new timethread();

		ji.setJMenuBar(jmb);

		jmb.add(master);

		System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 333");
		
        System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 444");
		
        setExtendedState(Frame.MAXIMIZED_BOTH);

        this.setSize(new Dimension(1000,1000));
        ji.setSize(new Dimension(1000,1000));
        ji.setVisible(true);
		setVisible(true);

		masteritem0[0].addActionListener(menilis);
		masteritem0[1].addActionListener(menilis);
		masteritem0[2].addActionListener(menilis);
		masteritem0[3].addActionListener(menilis);
		masteritem0[4].addActionListener(menilis);
		masteritem0[5].addActionListener(menilis);
		masteritem0[6].addActionListener(menilis);
		masteritem0[7].addActionListener(menilis);
		masteritem0[8].addActionListener(menilis);
		masteritem0[9].addActionListener(menilis);
		masteritem0[10].addActionListener(menilis);
		
		/*masteritem0[3].addActionListener(menilis);
		masteritem0[4].addActionListener(menilis);
		masteritem0[5].addActionListener(menilis);
		masteritem0[6].addActionListener(menilis);*/
		
		System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 555");
		
	}

	/*public static void main(String[] as){

		new MainScreen();
	}
*/
	public void addComponent (JComponent jt,JPanel jp,int gy,int gx,double wx,double wy,int gw,int gh)
	{
		gbc.gridx=gx;
		gbc.gridy=gy;
		gbc.weightx=wx;
		gbc.weighty=wy;
		gbc.gridheight=gh;
		gbc.gridwidth=gw;
		jt.setFont(new Font("Times New Roman",Font.BOLD,11));
		jp.add(jt,gbc);

	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent arg0) {

		System.exit(0);
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
			return (Validation.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR))); 
			 
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

	/*public static MainScreen getInstance() {
		if(mainScreen==null) {
			mainScreen=new MainScreen();
		}
	}*/
	public Heading getHead_panel() {
		return head_panel;
	}

	public void setHead_panel(Heading head_panel) {
		this.head_panel = head_panel;
	}


}

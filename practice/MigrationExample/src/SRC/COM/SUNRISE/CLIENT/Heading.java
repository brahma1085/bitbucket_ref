package SRC.COM.SUNRISE.CLIENT;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import SRC.COM.SUNRISE.UTILITY.*;

public class Heading extends JPanel implements ActionListener
{
    static final long serialVersionUID = 1L;
    
	JLabel head,date,time,namel,tml,location;
    
	Calendar c;
	static timethread t;
	public String uname,utml,br_location,bank_name;
	String dt;
	JLabel dummy1,dummy2,photo;	
	GridBagConstraints gbc;
	JButton button_lock,button_esc;
	private JComboBox combo_tml;
	private JButton button_pending;

	Display display;

	
	public Heading(String name,String utml)
	{
        
		setLayout(new GridBagLayout());
		
		try
		{
		}
		catch(Exception ex)
		{
		    ex.printStackTrace();
		}
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;  // components grow in both dimensions
		gbc.insets = new Insets(0,0,0,0);
		uname=name;
        
		setBackground(new Color(253,254,233));//223,244,253
        
		this.utml=utml;
		head=new JLabel("",SwingConstants.CENTER);
		location=new JLabel("",SwingConstants.CENTER);
		date=new JLabel();
		time=new JLabel();

		try
		{
			//
			String heads="DB$$DB";
		    StringTokenizer st=new StringTokenizer(heads,"$$$$$$");

		    head.setText(st.nextToken());
		    head.setForeground(new Color(39,57,222));
		    br_location=st.nextToken();
		    bank_name=head.getText().trim();// Swapna  
		    location.setText(br_location); //modified by Riswan 23rdMar
		    location.setForeground(Color.blue);

		    
		    dt = Login.getSysDate();
		    System.out.println("date "+dt);

		    time.setText(Login.getSysTime());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		    JOptionPane.showMessageDialog(null,e);
		}

		for(int i=0;i<60;i++){
			addComponent(new JLabel("   "),this,0,i,0,0,1,1);
		}
		//addComponent(namel=new JLabel(new ImageIcon("src/newimages/logo5.gif")),this,0,0,0.0,0.0,5,2);
		//addComponent(icon,this,0,0,0.0,0.0,1,1);
		
		addComponent(namel=new JLabel(),this,0,0,0.0,0.0,5,2);
		addComponent(head,this,0,8,0.0,0.0,23,1);
		head.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		dummy1=new JLabel("User:");
		dummy2=new JLabel("Tml:",SwingConstants.RIGHT);
		
		JLabel namet,tml;
		
		addComponent(dummy1,this,0,38,0.0,0.0,2,1);
		
		addComponent(namet=new JLabel(name),this,0,41,0.0,0.0,4,1);
		
		addComponent(dummy2,this,0,47,0.0,0.0,2,1);
		
		addComponent(tml=new JLabel(utml),this,0,48,0.0,0.0,2,1);
		tml.setVisible(false);
		
		/*addComponent(photo=new JLabel(new ImageIcon("src/newimages/bug.gif")),this,0,48,0.0,0.0,5,2);
		//photo.setBorder(BorderFactory.createLoweredBevelBorder());
		//photo.setBounds(0,0,2,1);
*/			
		dummy1.setForeground(Color.black);
		dummy2.setForeground(Color.black);
		
		namet.setForeground(Color.blue);
		tml.setForeground(Color.blue);
		
		date.setFont(new Font("Times New Roman",Font.BOLD,10));
		
		addComponent(location,this,1,18,0.0,0.0,10,1);		
		location.setFont(new Font("Times New Roman",Font.BOLD,14));

		addComponent(time,this,1,38,0.0,0.0,10,1);
		time.setFont(new Font("Times New Roman",Font.BOLD,11));
		time.setForeground(Color.blue);
		//time.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		/*button_lock=new JButton();
		button_lock.setIcon(new ImageIcon("./src/newimages/keylockopen.gif"));*/
		/*addComponent(button_lock,this,1,50,0.0,0.0,2,1);
		button_lock.setSize(new Dimension(1,1));
		button_lock.setBackground(Colors.setMainPanelColor());
		button_lock.setBorder(BorderFactory.createEmptyBorder());
		button_lock.addActionListener(this);
		*/
		combo_tml=new JComboBox();
		addComponent(combo_tml,this,0,50,0.0,0.0,6,1);
		combo_tml.setBackground(Colors.setComboColor());
		combo_tml.addItem(utml);
		/*button_esc=new JButton();
		addComponent(button_esc,this,1,53,0.0,0.0,2,1);
		button_esc.setBackground(Colors.setMainPanelColor());
		button_esc.setIcon(new ImageIcon("./src/newimages/logout.gif"));
		//button_esc.setForeground(Color.RED);
		button_esc.setBorder(BorderFactory.createEmptyBorder());
		button_esc.addActionListener(this);*/
		//combo_tml.addItem("LD01");
		
		/*button_pending=new JButton();
//		/addComponent(button_pending,this,1,55,0.0,0.0,2,1);
		button_pending.setBackground(Colors.setMainPanelColor());
		button_pending.setIcon(new ImageIcon("./src/newimages/pending.gif"));
		//button_esc.setForeground(Color.RED);
		button_pending.setBorder(BorderFactory.createEmptyBorder());
		button_pending.addActionListener(this);
*/		
		setBorder(BorderFactory.createRaisedBevelBorder());
		t=new timethread();
		//addComboTmlList(new String[]{"LN01","LN02","LN03","LD01"});
		//combo_tml.addItemListener(this);
	}
	
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
	
	public void addComboTmlList(Vector user_roles){
		combo_tml.removeActionListener(this);
		for(int i=0;i<user_roles.size();i++)
			combo_tml.addItem(user_roles.get(i));
		combo_tml.addActionListener(this);
		//combo_tml.addItem(user_roles.);
	}

	public void setTerminal(String tml) {
		combo_tml.removeActionListener(this);
		combo_tml.setSelectedItem(tml);
		combo_tml.addActionListener(this);
	}
	public void addComponent (JComponent jt,JPanel jp,int x,int y,int w,int h)
	{
		jt.setBounds(x,y,w,h);
		jt.setFont(new Font("Times New Roman",Font.BOLD,11));
		jp.add(jt);
	}

	public String getUid()
	{	
	    return uname;
	}
	
	public String getTml()
	{	
	    return utml;
	}
	
	public String getBankName()
	{
	    return(head.getText());
	}
	
	public String getBankLocation()
	{
	    return(location.getText());
	}
	
	//ship......19/06/2006
	public String getSysDateTime()
	{
	    return(getSysDate()+" "+getSysTime());
	}
	//////////
	
	public String getSysDate()
	{
	    return(dt);
	}
	
	public String getSysTime()
	{
	    String string_time=time.getText();
	    
	    return(string_time.substring(time.getText().indexOf(" ")+1));
	}

	class timethread extends Thread
	{
		timethread()
		{
			start();
		}

		public void run()
		{
			for(;;)
			{
			    try
			    {
			    	date.setText(Login.getSysDate());
			        time.setText(dt+"  "+Login.getSysTime());
			    }
			    catch(Exception e){}
				try
				{
				  	Thread.sleep(1000);
				}
				catch(InterruptedException e){}
			}
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button_lock){
		int value=JOptionPane.showConfirmDialog(null,"Do You Want To Lock ?","Lock",JOptionPane.YES_NO_OPTION);
			if(value==JOptionPane.YES_OPTION){
				button_lock.setIcon(new ImageIcon("./src/newimages/keylock.gif"));
				//MainScreen.js.hide();
			//	MainScreen.js.setVisible(false);
				display=new Display();
			}
		//}*/
		}
		if(e.getSource() == button_esc) {
			/*MainScreen.js.removeAll();
			MainScreen.js.repaint();*/
		}
		
		if(e.getSource() == button_pending) {
			/*new PendingTrayList();*/
		}
		
		if(e.getSource()==combo_tml){
			/*String pre_tml=null;
			try {
				String login=null;
				String ip_address = InetAddress.getLocalHost().getHostAddress();
				//String ip_address = "192.168.1.55";
				pre_tml = getTml();
                
				file_logger.info(" tml :"+combo_tml.getSelectedItem().toString()+" addr :"+ip_address);
                
				admin_remote.checkTerminal(combo_tml.getSelectedItem().toString(),ip_address);
				if(!pre_tml.equalsIgnoreCase(combo_tml.getSelectedItem().toString()) ) {
					admin_remote.doLogout(pre_tml);
					login = cmnint.checkLogin(this.getUid(),combo_tml.getSelectedItem().toString(),getSysDate(),getSysTime());
				}
				
				if(login != null && login.equalsIgnoreCase("W")){
					AccessConfig access_config = AccessConfig.getInstance(getUid(),combo_tml.getSelectedItem().toString(),"W");
					access_config.setEnabled(combo_tml.getSelectedItem().toString());
					MainScreen.jsp[0].removeAll();
					utml = combo_tml.getSelectedItem().toString();
				} else if(login == null || !login.equalsIgnoreCase("H")){
					if(login != null) {
						JOptionPane.showMessageDialog(null,login);
					}
					combo_tml.removeActionListener(this);
					combo_tml.setSelectedItem(pre_tml);
					combo_tml.addActionListener(this);
				}
				
			}catch(LoginException le) {
				combo_tml.removeActionListener(this);
				combo_tml.setSelectedItem(pre_tml);
				combo_tml.addActionListener(this);
				JOptionPane.showMessageDialog(null,le);
			}catch(Exception exe) {
				//Login.file_logger.info("aaaa");
				exe.printStackTrace();
			}*/
		}
	
	}
		class Display extends JDialog implements ActionListener
		{
		    private GridBagConstraints gbc;
		    private JPasswordField pass_txt;
		    private JDialog dialog;
		    private JPanel panel;
		    private JLabel lbl_logo,lbl_name,lbl_text,lbl_tml,lbl_pass,lbl_user,lbl_role;
		    private JButton button_ok,button_cancle;
//		    /CommonRemote commonRemote;
		    
		    public Display() {
		        Container con=getContentPane();
		        getContentPane().setLayout(new GridBagLayout());
				gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;
				gbc.insets = new Insets(0,0,0,0);
				dialog=new JDialog(this,true);
				panel=new JPanel(new GridBagLayout());
				panel.setSize(300,200);
				panel.setVisible(true);
				dialog.getContentPane().add(panel);
				lbl_logo=new JLabel(new ImageIcon("./src/newimages/coreTrail.gif"),SwingConstants.LEFT);
				lbl_text=new JLabel("ENTER PASSWORD TO UNLOCK");
				//lbl_text.setFont(new Font("Times New Roman",Font.BOLD,14));
				lbl_user=new JLabel("USER");
				lbl_name=new JLabel(""+getUid()+"");
				lbl_tml=new JLabel("TML");
				lbl_role=new JLabel(""+getTml()+"");
				lbl_pass=new JLabel("PASSWORD");
				button_ok=new JButton("OK");
				button_cancle=new JButton("CANCEL");
				
				for(int i=0;i<30;i++){
					addComponent(new JLabel("   "),panel,0,i,0,0,1,1);
				}
				addComponent(lbl_logo,panel,0,1,0,0,7,1);
				addComponent(lbl_text,panel,0,8,0,0,20,1);
				addComponent(new JLabel(" "),panel,1,0,0,0.0,1,1);
				addComponent(lbl_user,panel,2,4,0,0,8,1);
				addComponent(lbl_name,panel,2,14,0,0,10,1);
				addComponent(lbl_tml,panel,3,4,0,0,8,1);
				addComponent(lbl_role,panel,3,14,0,0,10,1);
				addComponent(lbl_pass,panel,4,4,0,0,8,1);
				addComponent(pass_txt=new JPasswordField(),panel,4,14,0,0,10,1);
				pass_txt.setForeground(Color.BLUE);
				addComponent(new JLabel(" "),panel,5,0,0,0.0,10,1);
				addComponent(button_ok,panel,6,6,0,0,6,1);
				button_ok.addActionListener(this);
				addComponent(button_cancle,panel,6,14,0,0,6,1);
				button_cancle.addActionListener(this);
				panel.setBackground(Colors.setMainPanelColor());
				button_ok.setBackground(Colors.setButtonColor());
				button_cancle.setBackground(Colors.setButtonColor());
				button_ok.setForeground(Color.BLUE);
				button_cancle.setForeground(Color.BLUE);
				addComponent(new JLabel(" "),panel,7,0,0,1.0,1,1);
				
				dialog.setResizable(false);
				dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				dialog.setLocation(250,150);
				dialog.setSize(300,200);
				try{
					/*CommonHome commonHome = (CommonHome)HomeFactory.getFactory().lookUpHome("COMMON");
					commonRemote = commonHome.create();
	                
					file_logger.info(" remote "+commonRemote);*/
				}
				catch(Exception e){e.printStackTrace();}
				dialog.show();
		    }
				
		    
		    public void addComponent (JComponent jt,JPanel jp,int gy,int gx,double wx,double wy,int gw,int gh)
			{
				gbc.gridx=gx;
				gbc.gridy=gy;
				gbc.weightx=wx;
				gbc.weighty=wy;
				gbc.gridheight=gh;
				gbc.gridwidth=gw;
				jt.setFont(new Font("Times New Roman",Font.BOLD,14));
				jp.add(jt,gbc);		
			}
			
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==button_ok){
					int result=0;
	                
					/*file_logger.info("pass==>"+new String(pass_txt.getPassword()));
					file_logger.info("uid==>"+getUid());
					file_logger.info("tml==>"+getTml());
					file_logger.info(" remote "+commonRemote);
	                
					try
	                {
						result=commonRemote.getPassword(getUid(),getTml(),new String(pass_txt.getPassword()));
					}
					catch(Exception ex){ex.printStackTrace();}*/
					if(result==1){
						dialog.hide();
//					/	MainScreen.js.setVisible(true);
						button_lock.setIcon(new ImageIcon("./src/newimages/keylockopen.gif"));
					}
					else{
						JOptionPane.showMessageDialog(this,"INVALID PASSW0RD !");
						pass_txt.setText("");
						pass_txt.requestFocus();
					}
				}
				else if(e.getSource()==button_cancle){
					pass_txt.setText("");
					pass_txt.requestFocus();
				}
			}

	       
		}

		
	/*public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==combo_tml){
			try {
				
				String ip_address = InetAddress.getLocalHost().getHostAddress();
				
				//String ip_address = "192.168.1.55";
				
				String pre_tml = getTml();
				Login.file_logger.info(" tml :"+combo_tml.getSelectedItem().toString()+" addr :"+ip_address);
				admin_remote.checkTerminal(combo_tml.getSelectedItem().toString(),ip_address);
				admin_remote.doLogout(pre_tml);
				
				AccessConfig access_config = AccessConfig.getInstance(getUid(),combo_tml.getSelectedItem().toString(),"W");
				access_config.setEnabled(combo_tml.getSelectedItem().toString());
				MainScreen.jsp[0].removeAll();
				utml = combo_tml.getSelectedItem().toString();
			}catch(LoginException le) {
				JOptionPane.showMessageDialog(null,le);
			}catch(Exception exe) {
				Login.file_logger.info("aaaa");
			}
		}
	}*/
	
}


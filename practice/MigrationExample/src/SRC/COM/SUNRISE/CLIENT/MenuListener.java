package SRC.COM.SUNRISE.CLIENT;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanTransactionObject;



public class MenuListener implements ActionListener {

	MainScreen main;
	JPanel panel ;
	JScrollPane jsp ;
	
	public MenuListener(MainScreen mai ) {
		main = mai;
		
	}
	
	
	public void actionPerformed(ActionEvent arg) {

		if(panel != null) {
			MainScreen.js.remove(jsp);
		}
		
		jsp = null;
		
		
		
		if ( arg.getSource() == main.masteritem0[0]) {
			
			CIDPanel cid = new CIDPanel();
			jsp = new JScrollPane(cid,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 666");
				
			/*MainScreen.js.add(cid,BorderLayout.CENTER);
			
			MainScreen.js.setBorder(BorderFactory.createEtchedBorder(1));
			MainScreen.js.setSize(new Dimension(700,700));
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 777");*/
			panel = cid;
		}
		
		else if ( arg.getSource() == main.masteritem0[2]) {
			
			SBCAPanel sbca =  SBCAPanel.getInstance(0);
			sbca.clearScreen();
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 666");
			
			
			/*MainScreen.js.add(sbca,BorderLayout.CENTER);
			MainScreen.js.setBorder(BorderFactory.createEtchedBorder(1));
			MainScreen.js.setSize(new Dimension(700,700));
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 777");*/
			panel = sbca;
			jsp = new JScrollPane(sbca);
		}
		
		/*else if ( arg.getSource() == main.masteritem0[2]) {
			
			JointHolder joi = new JointHolder();
			//jsp.add(per);
			jsp = new JScrollPane(joi);
			
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 666");
			
			MainScreen.js.add(per,BorderLayout.CENTER);
			MainScreen.js.setBorder(BorderFactory.createEtchedBorder(1));
			MainScreen.js.setSize(new Dimension(700,700));
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 777");
			panel = joi;
		
		}*/
		
		else if ( arg.getSource() == main.masteritem0[1]) {
			
			NewShare share=new NewShare();
			//jsp.add(per);
			jsp = new JScrollPane(share);
			
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 666");
			
			/*MainScreen.js.add(per,BorderLayout.CENTER);
			MainScreen.js.setBorder(BorderFactory.createEtchedBorder(1));
			MainScreen.js.setSize(new Dimension(700,700));
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 777");*/
			panel = share;
		
		}
		
		else if ( arg.getSource() == main.masteritem0[3]) {
			
			
			DepositPanel depos = DepositPanel.getInstance(0);
			//jsp.add(per);
			jsp = new JScrollPane(depos);
			
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 77777");
			
			/*MainScreen.js.add(per,BorderLayout.CENTER);
			MainScreen.js.setBorder(BorderFactory.createEtchedBorder(1));
			MainScreen.js.setSize(new Dimension(700,700));
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 777");*/
			panel = depos;
		
		}
		else if ( arg.getSource() == main.masteritem0[4]) {
			
			
			LoanPanel loans = LoanPanel.getInstance(0);
			//jsp.add(per);
			jsp = new JScrollPane(loans);
			
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 77777");
			
			/*MainScreen.js.add(per,BorderLayout.CENTER);
			MainScreen.js.setBorder(BorderFactory.createEtchedBorder(1));
			MainScreen.js.setSize(new Dimension(700,700));
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++ 777");*/
			panel = loans;
		
		}
		
		//added by geetha
		

		else if ( arg.getSource() == main.masteritem0[5]) {
			
			
			RDDataEntry rd_de = RDDataEntry.getInstance(0);
			jsp = new JScrollPane(rd_de);
			
			System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++888888");
			
			panel = rd_de;
		
		}
		
        else if ( arg.getSource() == main.masteritem0[6]) {
			
			
			DepTranEntry dep_trn = DepTranEntry.getInstance(0);
			jsp = new JScrollPane(dep_trn);
			
			System.out.println(" +++++++++++++++++++++++++++++++++++++++++++999999");
			
			panel = dep_trn;
		
		}
		
        else if(arg.getSource() == main.masteritem0[7]) {
        	LoanTran loantran = LoanTran.getInstance(0);
            jsp = new JScrollPane(loantran);
			
			System.out.println(" +++++++++++++++++++++++++++++++++++++++++++999999");
			
			panel = loantran;
        	
        }
		
        else if(arg.getSource() == main.masteritem0[8])
        {
        	LoanTransaction tran = LoanTransaction.getInstance(0);
        	jsp = new JScrollPane(tran);
			
			System.out.println(" +++++++++++++++++++++++++++++++++++++++++++999999");
			
			panel = tran;
        }
		//added onb 25-10-2010***************
		
        else if(arg.getSource() == main.masteritem0[9])
        {
        	Vehicle vc = Vehicle.getInstance(0);
        	jsp = new JScrollPane(vc);
			
			System.out.println(" +++++++++++++++++++++++++++++++++++++++++++999999");
			
			panel = vc;
        }
        else if(arg.getSource() == main.masteritem0[10])
        {
        	Property prop = Property.getInstance(0);
        	jsp = new JScrollPane(prop);
			
			System.out.println(" +++++++++++++++++++++++++++++++++++++++++++999999");
			
			panel = prop;
        }
		
		/*jsp.setPreferredSize(new Dimension(1000,1000));*/
		MainScreen.js.add( jsp,BorderLayout.CENTER);
		MainScreen.js.setBorder(BorderFactory.createEtchedBorder(1));
		MainScreen.js.setSize(new Dimension(700,700));
		System.out.println(" ++++++++++++++++++++++++++++++++++++++++++++10101010");
		
		
		/*MainScreen.js.repaint();
		MainScreen.ji.repaint();*/
		MainScreen.js.revalidate();
		
	}

}

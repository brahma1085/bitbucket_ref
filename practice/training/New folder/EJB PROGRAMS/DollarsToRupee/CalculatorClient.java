
//Calculator client which invokes the Remote EJB Object.....


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.util.*;

public class CalculatorClient extends JFrame 
{
  JLabel lblInput; 
  JLabel lblResult;
  JTextField txt;
  JButton btn;

  public CalculatorClient()
  {
    lblInput=new JLabel("Enter the amount in Dollars($)");
    lblResult=new JLabel("");
    txt=new JTextField(40);
    btn=new JButton("CALCULATE Rs.");

    Container con=this.getContentPane();
    con.setLayout(new GridLayout(2,2));

    con.add(lblInput);
    con.add(txt);
  
    con.add(btn);
    con.add(lblResult);

    btn.addActionListener(new AddEvent());

    setSize(300,200);
    setVisible(true);

    //show();
  }

  public class AddEvent implements ActionListener
  {
    public void actionPerformed(ActionEvent ae)
    {
      if(ae.getSource()==btn)
      {
        double amtInDollar=Double.parseDouble(txt.getText().trim());
         
        try
        {
          Properties p=new Properties();
	  p.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
	  p.put(Context.PROVIDER_URL,"t3://localhost:7001");
          Context ic=new InitialContext(p); //Getting a JNDI naming context..

          Object ob=ic.lookup("CalculatorJNDI"); //Looking the Home Object..
 
          //Narrowing the Object to Home Object
          CalculatorHome home=(CalculatorHome) PortableRemoteObject.narrow(ob,CalculatorHome.class);

          CalculatorRemote remote=home.create(); //Getting the Remote Object...

          double amtInRs=remote.dollarToRs(amtInDollar);

          lblResult.setText("Result in Rs. : "+String.valueOf(amtInRs));
        }
        catch(Exception e)
        {
          System.err.println("Exception : "+e);
          e.printStackTrace();
        }
     }
   }
 }

 public static void main(String args[])
 {
   new CalculatorClient();
 }

}

 


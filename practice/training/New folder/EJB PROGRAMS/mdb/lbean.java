import javax.ejb.*;
import javax.jms.*;

public class lbean implements MessageDrivenBean,MessageListener
{
protected MessageDrivenContext ctx;

public void setMessageDrivenContext(MessageDrivenContext ctx)
{
this.ctx=ctx;
}

public void ejbCreate()
{
System.err.println("ejbCreate()");
}

public void ejbRemove()
{
System.err.println("ejbRemove()");
}


 public void onMessage(Message msg) {
    TextMessage tm = (TextMessage) msg;
    try {
      String text = tm.getText();
      System.err.println("Received new quote : " + text);
    }
    catch(JMSException ex) {
      ex.printStackTrace();
    }
  }



}
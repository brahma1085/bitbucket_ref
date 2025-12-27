import javax.naming.*;
import javax.jms.*;
import java.util.*;

public class Client
{
public static void main(String ad[])throws Exception
{
Properties p=new Properties();
p.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
p.put(Context.PROVIDER_URL,"t3://localhost:7001");
Context ctx=new InitialContext(p);
TopicConnectionFactory tf=(TopicConnectionFactory)ctx.lookup("bi");
TopicConnection tc=tf.createTopicConnection();
TopicSession se=tc.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
Topic tp=(Topic)ctx.lookup("quotes");
TopicPublisher pub=se.createPublisher(tp);
TextMessage msg=se.createTextMessage();
msg.setText("this is a simple msg from Sreenivas");
pub.publish(msg);
}
}
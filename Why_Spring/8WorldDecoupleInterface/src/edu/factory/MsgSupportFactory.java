package edu.factory;

import java.util.Properties;

import edu.decoupleinterface.MsgProvider;
import edu.decoupleinterface.MsgRender;

public class MsgSupportFactory {
	private static Properties properties = new Properties();
	private static MsgRender msgRender = null;
	private static MsgProvider msgProvider = null;
	private static MsgSupportFactory msgSupportFactory = new MsgSupportFactory();
	static {
		try {
			properties.load(MsgSupportFactory.class.getClassLoader()
					.getResourceAsStream("messagefactory.properties"));
			String render1 = properties.getProperty("render1");
			String render2 = properties.getProperty("render2");
			String provider1 = properties.getProperty("provider1");
			String provider2 = properties.getProperty("provider2");
			msgRender = (MsgRender) Class.forName(render1).newInstance();
			// msgRender=(MsgRender)Class.forName(render2).newInstance();
			msgProvider = (MsgProvider) Class.forName(provider1).newInstance();
			// msgProvider=(MsgProvider)Class.forName(provider2).newInstance();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new ExceptionInInitializerError(
					"error in loading proper details");
		}
	}

	public MsgRender getMsgRender() {
		return msgRender;
	}

	public MsgProvider getMsgProvider() {
		return msgProvider;
	}

	public static MsgSupportFactory getInstance() {
		return msgSupportFactory;
	}
}

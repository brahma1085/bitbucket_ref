package edu.test;

import edu.decoupleinterface.MsgProvider;
import edu.decoupleinterface.MsgRender;
import edu.factory.MsgSupportFactory;

public class WorldDecoupleFactory {

	public static void main(String[] args) {
		MsgSupportFactory factory = MsgSupportFactory.getInstance();
		MsgProvider msgProvider = factory.getMsgProvider();
		MsgRender msgRender = factory.getMsgRender();
		msgRender.setMsgProvider(msgProvider);
		msgRender.render();
	}
}

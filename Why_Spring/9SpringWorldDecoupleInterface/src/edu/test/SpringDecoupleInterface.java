package edu.test;

import edu.decoupleinterface.MsgRender;
import edu.factory.MsgSupportFactory;

public class SpringDecoupleInterface {
	public static void main(String[] args) {
		MsgRender msgRender = MsgSupportFactory.getMsgRender();
		msgRender.render();
	}
}

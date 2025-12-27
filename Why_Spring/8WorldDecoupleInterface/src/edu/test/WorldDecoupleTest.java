package edu.test;

import edu.decouple.ErrMsgRender;
import edu.decouple.MsgProvider1;
import edu.decouple.MsgProvider2;
import edu.decouple.OutMsgRender;
import edu.decoupleinterface.MsgProvider;
import edu.decoupleinterface.MsgRender;

public class WorldDecoupleTest {

	public static void main(String[] args) {
		MsgProvider msgProvider = new MsgProvider1();
		MsgProvider msgProvider2 = new MsgProvider2();
		MsgRender msgRender = new OutMsgRender();
		MsgRender msgRender2 = new ErrMsgRender();
		msgRender.setMsgProvider(msgProvider);
		msgRender.render();
		System.out.println(msgRender.getMsgProvider());
		msgRender.setMsgProvider(msgProvider2);
		msgRender.render();
		System.out.println(msgRender.getMsgProvider());
		msgRender2.setMsgProvider(msgProvider);
		msgRender2.render();
		System.out.println(msgRender2.getMsgProvider());
		msgRender2.setMsgProvider(msgProvider2);
		msgRender2.render();
		System.out.println(msgRender2.getMsgProvider());
	}
}

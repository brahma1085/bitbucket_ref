package edu.test;

import edu.decoupled.MsgProvider1;
import edu.decoupled.OutMsgRender;

public class WorldDecoupledTest1 {

	public static void main(String[] args) {
		MsgProvider1 provider1 = new MsgProvider1();
		// MsgProvider2 provider2 = new MsgProvider2();
		OutMsgRender outMsgRender = new OutMsgRender();
		outMsgRender.setMsgRender(provider1);
		// outMsgRender.setMsgRender(provider2);
		outMsgRender.render();
	}
}

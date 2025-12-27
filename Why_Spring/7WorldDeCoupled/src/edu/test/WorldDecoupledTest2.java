package edu.test;

import edu.decoupled.MsgProvider1;
import edu.decoupled.OutMsgRender;

public class WorldDecoupledTest2 {
	public static void main(String[] args) {
		MsgProvider1 provider1 = new MsgProvider1();
		OutMsgRender outMsgRender = new OutMsgRender();
		outMsgRender.setMsgRender(provider1);
		outMsgRender.render();
	}
}

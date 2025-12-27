package edu.test;

import edu.decoupled.ErrMsgRender;
import edu.decoupled.MsgProvider2;

public class WorldDecoupledTest3 {

	public static void main(String[] args) {
		MsgProvider2 provider2 = new MsgProvider2();
		ErrMsgRender errMsgRender = new ErrMsgRender(provider2);
		errMsgRender.render();
	}
}

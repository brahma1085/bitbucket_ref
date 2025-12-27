package edu.decouple;

import edu.decoupleinterface.MsgProvider;

public class MsgProvider2 implements MsgProvider {

	@Override
	public String getMessage() {
		return "Hello World";
	}

}

package edu.decouple;

import edu.decoupleinterface.MsgProvider;
import edu.decoupleinterface.MsgRender;

public class ErrMsgRender implements MsgRender {
	private MsgProvider msgProvider;

	public MsgProvider getMsgProvider() {
		return msgProvider;
	}

	public void setMsgProvider(MsgProvider msgProvider) {
		this.msgProvider = msgProvider;
	}

	@Override
	public void render() {
		if (msgProvider.equals(null))
			throw new RuntimeException("message provider required");
		System.err.println(msgProvider.getMessage());
	}

}

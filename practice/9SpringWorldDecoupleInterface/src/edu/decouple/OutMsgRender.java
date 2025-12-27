package edu.decouple;

import edu.decoupleinterface.MsgProvider;
import edu.decoupleinterface.MsgRender;

public class OutMsgRender implements MsgRender {
	private MsgProvider msgProvider;

	public MsgProvider getMsgProvider() {
		return msgProvider;
	}

	public void setMsgProvider(MsgProvider msgProvider) {
		this.msgProvider = msgProvider;
	}

	@Override
	public void render() {
		if (msgProvider == null)
			throw new RuntimeException("message provider required");
		System.out.println(msgProvider.getMessage());
	}

}

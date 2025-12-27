package edu.decoupleinterface;

public interface MsgRender {
	void setMsgProvider(MsgProvider msgProvider);

	void render();

	MsgProvider getMsgProvider();
}

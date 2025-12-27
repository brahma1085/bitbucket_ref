package edu.decoupled;

public class OutMsgRender {
	private MsgProvider1 provider1 = null;

	public void setMsgRender(MsgProvider1 provider1) {
		this.provider1 = provider1;
	}

	public void render() {
		if (provider1 == null)
			throw new RuntimeException("message provider is required");
		System.out.println(provider1.getMessage());
	}
}

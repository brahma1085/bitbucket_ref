package edu.decoupled;

public class ErrMsgRender {
	private MsgProvider2 provider2 = null;

	public ErrMsgRender(MsgProvider2 provider2) {
		super();
		this.provider2 = provider2;
	}

	public void render() {
		if (provider2 == null)
			throw new RuntimeException("message provider is required");
		System.err.println(provider2.getMessage());
	}
}

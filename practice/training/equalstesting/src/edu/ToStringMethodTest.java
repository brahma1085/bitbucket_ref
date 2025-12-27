package edu;

public class ToStringMethodTest {

	private String companyName;
	private String companyAddress;

	public ToStringMethodTest(String companyName, String companyAddress) {
		this.companyName = companyName;
		this.companyAddress = companyAddress;
	}

	public static void main(String[] args) {
		ToStringMethodTest test = new ToStringMethodTest("ABC private Ltd",
				"10, yy Street, CC Town");
		System.out.println(test);
	}

	public String toString() {
		return ("Company Name: " + companyName + "\n" + "Company Address: " + companyAddress);
	}

}

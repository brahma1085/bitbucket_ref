package exceptions;

public class AccountNotFoundException extends Exception {
	public String toString()
	{
		return "Account not found";
	}
}
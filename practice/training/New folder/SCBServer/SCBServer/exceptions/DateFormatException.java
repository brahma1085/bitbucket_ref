package exceptions;

public class DateFormatException extends Exception {
	public DateFormatException(String s) {
		super(s); 
	}
	public String toString(){
		return super.getMessage();
	} 
}

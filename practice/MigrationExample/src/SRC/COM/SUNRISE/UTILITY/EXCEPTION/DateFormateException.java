package SRC.COM.SUNRISE.UTILITY.EXCEPTION;

public class DateFormateException extends Exception {
	public DateFormateException(String s) {
		super(s); 
	}
	public String toString(){
		return super.getMessage();
	} 
}

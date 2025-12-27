public class Wolfe{
	public static void main(String argv[]){
		Wolfe w = new Wolfe();
		w.Go();
	}
	public void Go(){
		Account personal = new Account();
		personal.rate=7;
		Account business = new Account();
		business.rate =5;
		deduct(business);
		System.out.println(business.balance);
		System.out.println(personal.rate);

	}
	public void deduct(Account a){
		a.balance=200;
	}

}	
class Account{
	static int rate;
	public int balance=100;
}


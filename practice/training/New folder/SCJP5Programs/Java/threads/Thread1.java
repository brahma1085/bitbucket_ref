package threads;

class Thread1 implements Runnable{
	private int x;
	private int y;
	public synchronized void run() {
	    x++;
	    y++;
	    System.out.println(x+" "+y);
	}

	public static void main(String args[]) {
	    Thread1 that=new Thread1();
	    (new Thread(that)).start();
	    (new Thread(that)).start();
	}
}

//a) will print x ,y in order 11 22

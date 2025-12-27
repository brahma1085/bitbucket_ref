package threads;

class ThreadA {

static int k;

public static void main(String[] args) {

ThreadB b = new ThreadB();
b.start();

synchronized(b) { // Assume main thread acquires lock before lock ThreadB

try {

	b.wait(1);
	for (int i = 0; i < 10; i++) { // line (1)
	System.out.println("ThreadA : " + k++);
	Thread.sleep(1000);
}
} catch (InterruptedException e) {}
}
}
}

class ThreadB extends Thread {

static int k;

public void run() {

synchronized(this) {

try {

for (int i = 0; i < 10; i++) {

System.out.println("ThreadB : " + k++);
Thread.sleep(1000);
}
} catch (InterruptedException ie) {}

notify();
} // line (2)
}
}

/* output:
 ThreadB : 0...ThreadB : 9
followed by ThreadA : 0...ThreadA : 9
*/
/*
You are right if the thread run method called before you lock the thread in your main function.
"b.start();" does not directly call run method and thread scheduler will call run method. 
If your main function lock the thread object first then you will get: "Thread A: 0- 9" first.
*/
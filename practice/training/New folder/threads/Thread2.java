package threads;

class Counter {
       int v = 0;
       synchronized void inc() { v++; }
       synchronized void dec() { v--; }
}

public class Thread2 {
       Counter i;
       Counter j;
       Counter k;

       public synchronized void a() {
       	i.inc();
	System.out.println("a");
	i.dec();
       }

       public synchronized void b() {
	i.inc(); j.inc(); k.inc();
	System.out.println("b");
	i.dec(); j.dec(); k.dec();
       }

       public void c() {
	k.inc();
	System.out.println("c");
	k.dec();
       }
}

/* choose correct answers:
1)   i.v is guaranteed always to be 0 or 1.
2)   j.v is guaranteed always to be 0 or 1.
3)   k.v is guaranteed always to be 0 or 1
4)   j.v will always be greater than or equal to k.v at any give time.
5)   k.v will always be greater than or equal to j.v at any give time.
*/

/* answer: 1) and 2)
When a thread calls an object's synchronized method, the WHOLE object is locked. 
This means that if another thread tries to call ANY synchronized method of the same 
object, the call will block until the lock is released (which happens when the original 
call finishes).
*/


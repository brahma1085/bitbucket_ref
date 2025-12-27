package classes;

class Point {
	int x = 0, y = 0;
	void move(int dx, int dy) { 
		x += dx; y += dy; 
		System.out.println("Point's move()");
	}
}

class SlowPoint extends Point {
	int xLimit, yLimit;
	void move(int dx, int dy) {
		super.move(limit(dx, xLimit), limit(dy, yLimit));
		System.out.println("SlowPoint's move()");
	}
	static int limit(int d, int limit) {
		return d > limit ? limit : d < -limit ? -limit : d;
	}
                  SlowPoint(int xLimit, int yLimit) {
		this.xLimit = xLimit;
		this.yLimit = yLimit;
                  }
}


public class Overriding3 {
   public static void main(String[] args) {
        Point sp = new SlowPoint(10, 10);
        sp.move(1, 1);
   }
}

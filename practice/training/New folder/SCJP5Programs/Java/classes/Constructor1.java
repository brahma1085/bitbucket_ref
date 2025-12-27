package classes;

class Point {
	int x, y;
	Point(int x, int y) { this.x=x; this.y=y;}
}

class ColoredPoint extends Point {
	static final int WHITE = 0, BLACK=1;
	int color;
	static int getColor() { return WHITE;}
	ColoredPoint(int x, int y) {
		this(x, y, WHITE);  //ok
		//this(x, y, color);  // err
		//this(x, y, getColor());  //ok
	}
	ColoredPoint(int x, int y, int color) {
		super(x,y);
		this.color = color;
	}
}


public class Constructor1 {
   public static void main(String[] args) {
        Point sp = new ColoredPoint(10, 10);
   }
}

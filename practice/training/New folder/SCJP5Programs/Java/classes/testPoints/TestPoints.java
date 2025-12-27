package testPoints;

import morePoints.*;

public class TestPoints {
    public static void main(String[] args) {
          points.Point p3d = OnePoint.getOne();
          p3d.move(3,4);
          //p3d.move(3,4,5);  // compiler err
          int x = p3d.x;
          int y = p3d.y;
          //int z = p3d.z;  // compiler err

          Point4d  p4d = new Point4d();
          p4d.move(3,4);
          p4d.move(3,4,5);
          int x1 = p4d.x;
          int y1 = p4d.y;
          int z1 = p4d.z;
    }
}
package src;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;

public class MouseHandler extends MouseAdapter {

	private static String OS = System.getProperty("os.name").toLowerCase();
	private static double distance;

	public static boolean mouseOver(GameObject circle) {
		if (OS.indexOf("win") >= 0) {
			Point point = MouseInfo.getPointerInfo().getLocation();
			double temp1x = point.getX();
			double temp1y = point.getY();

			Point temp = Window.frame.getLocationOnScreen();
			double temp2x = temp.getX();
			double temp2y = temp.getY();

			double mx = temp1x - temp2x + (42.0 * Game.HRATIO); // - (60.0 * Game.HRATIO); value was 20 on windows
			double my = temp1y - temp2y + (19.0 * Game.WRATIO); // - (70.0 * Game.HRATIO);

			System.out.println(mx + ", " + my);
			double cx = circle.getX() + (30.0 * Game.HRATIO * Circle.getCSMultiplier());
			double cy = circle.getY() + (30.0 * Game.HRATIO * Circle.getCSMultiplier());
			System.out.println(cx + ", " + cy);
			double distX = mx - cx;
			double distY = my - cy;
			distance = Math.sqrt((distX * distX) + (distY * distY));
			System.out.println(distance);
			//System.out.println();
			System.out.println(Circle.getCSMultiplier());
			System.out.println(30.0 * Game.HRATIO * Circle.getCSMultiplier());
			System.out.println();
			System.out.println();
		} else if (OS.indexOf("mac") >= 0) {
			Point point = MouseInfo.getPointerInfo().getLocation();
			double temp1x = point.getX();
			double temp1y = point.getY();

			Point temp = Window.frame.getLocationOnScreen();
			double temp2x = temp.getX();
			double temp2y = temp.getY();

			double mx = temp1x - temp2x + (42.0 * Game.HRATIO); // - (60.0 * Game.HRATIO); value was 20 on windows
			double my = temp1y - temp2y + (19.0 * Game.WRATIO); // - (70.0 * Game.HRATIO);

			System.out.println(mx + ", " + my);
			double cx = circle.getX() + (30.0 * Game.HRATIO * Circle.getCSMultiplier());
			double cy = circle.getY() + (30.0 * Game.HRATIO * Circle.getCSMultiplier());
			System.out.println(cx + ", " + cy);
			double distX = mx - cx;
			double distY = my - cy;
			distance = Math.sqrt((distX * distX) + (distY * distY));
			System.out.println(distance);
			System.out.println();
			System.out.println(Circle.getCSMultiplier());
			System.out.println(30.0 * Game.HRATIO * Circle.getCSMultiplier());
			System.out.println();
			System.out.println();
		}
		if (distance <= (30.0 * Game.HRATIO * Circle.getCSMultiplier())) {
			// System.out.println(distance);
			return true;
		} else {
			// System.out.println(distance);
			return false;
		}
	}

//	// POINT/CIRCLE
//	boolean pointCircle(float px, float py, float cx, float cy, float r) {
//
//	  // get distance between the point and circle's center
//	  // using the Pythagorean Theorem
//	  float distX = px - cx;
//	  float distY = py - cy;
//	  float distance = sqrt( (distX*distX) + (distY*distY) );
//
//	  // if the distance is less than the circle's
//	  // radius the point is inside!
//	  if (distance <= r) {
//	    return true;
//	  }
//	  return false;
//	}
//	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
//		if (mx > x && mx < x + width) {
//			if (my > y && my < y + height) {
//				return true;
//			} else {
//				return false;
//			}
//		} else {
//			return false;
//		}
//	}

}

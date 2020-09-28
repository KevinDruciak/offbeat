package src;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends GameObject {

	private static double ODMultiplier = 1.0;
	private static double ARMultiplier = 1.0;
	private static double CSMultiplier = 1.0;
	private double approachCircleSize = 200.0 * Game.HRATIO;
	private static int ARTiming;
	private static HUDCircle HUDCircle;
	private static MouseHandler mouseHandler;
	private Handler handler;

	public Circle(int x, int y, ID id, Handler handler, HUDCircle hudisplay, MouseHandler mouseHandler) {
		super(x, y, id);
		this.handler = handler;
	    HUDCircle = hudisplay;
	}

	public static int getARTiming() {
		return ARTiming;
	}
	public static double getCSMultiplier() {
		return CSMultiplier;
	}

	public void tick() {

		approachCircleSize = approachCircleSize - (ARMultiplier * Game.HRATIO);
		approachCircleSize = Game.clamp(approachCircleSize, 0.0 * Game.HRATIO, 200.0 * Game.HRATIO);
//		if (approachCircleSize > (30.0 * Game.HRATIO) && approachCircleSize < (34.0 * Game.HRATIO)) {
//			handler.removeObject(this);
//		}
		if (approachCircleSize < (30.0 * Game.HRATIO)) {
			handler.removeObject(this);
			HUDCircle.setValues(4);
			HUDCircle.setX(this.getX() - (int)(8.0 * Game.HRATIO));
			HUDCircle.setY(this.getY() + (int)(4.0 * Game.HRATIO));
			System.out.println("miss");
		}
	}

	public static void setAR(String ar) {
		double AR = Double.parseDouble(ar);
		if (AR >= 5.0) {
			ARMultiplier = 1200 - ((AR - 5.0) * 150);
		} else if (AR < 5.0) {
			ARMultiplier = 1800 - (AR * 120);
		}
		ARTiming = (int) ARMultiplier;
		ARMultiplier = ARMultiplier / 3.3333333333333;
		ARMultiplier = 200.0 / ARMultiplier;
	}

	public static void setOD(String od) {
		double OD = Double.parseDouble(od);
		ODMultiplier = OD;
	}

	public static void setCS(String cs) {
		double CS = Double.parseDouble(cs);
		//CSMultiplier = CS;
		if (CS == 4.0) {
			CSMultiplier = 1.0;
		} else if (CS > 4.0) {
			CSMultiplier = 60.0 - ((CS - 4.0) * 10);
			CSMultiplier = CSMultiplier / 60.0;
		} else if (CS < 4.0) {
			CSMultiplier = 60.0 + ((4.0 - CS) * 10);
			CSMultiplier = CSMultiplier / 60.0;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		
		g.drawOval(x - (int) ((approachCircleSize * CSMultiplier) / 2), y - (int) ((approachCircleSize * CSMultiplier) / 2), (int) (approachCircleSize * CSMultiplier),
				(int) (approachCircleSize * CSMultiplier));
		// g.setColor(Color.WHITE);
		g.setColor(Color.GRAY);
		g.drawOval(x - ((int) (30.0 * Game.HRATIO * CSMultiplier)), y - ((int) (30.0 * Game.HRATIO * CSMultiplier)), (int) (60.0 * Game.HRATIO * CSMultiplier),
				(int) (60.0 * Game.HRATIO * CSMultiplier));
		g.setColor(Color.WHITE);
		g.fillOval(x - ((int) (30.0 * Game.HRATIO * CSMultiplier)), y - ((int) (30.0 * Game.HRATIO * CSMultiplier)), (int) (60.0 * Game.HRATIO * CSMultiplier),
				(int) (60.0 * Game.HRATIO * CSMultiplier));
		g.setColor(Color.GRAY);
		g.drawOval(x - ((int) (30.0 * Game.HRATIO * CSMultiplier * .9)), y - ((int) (30.0 * Game.HRATIO * CSMultiplier * .9)), (int) (60.0 * Game.HRATIO * CSMultiplier * .9),
				(int) (60.0 * Game.HRATIO * CSMultiplier * .9));
		g.setColor(Color.BLACK);
		g.fillOval(x - ((int) (30.0 * Game.HRATIO * CSMultiplier * .9)), y - ((int) (30.0 * Game.HRATIO * CSMultiplier * .9)), (int) (60.0 * Game.HRATIO * CSMultiplier * .9),
				(int) (60.0 * Game.HRATIO * CSMultiplier * .9));
		g.setColor(Color.GREEN);
		g.fillOval(x - ((int) (30.0 * Game.HRATIO * CSMultiplier * .1)), y - ((int) (30.0 * Game.HRATIO * CSMultiplier * .1)), (int) (60.0 * Game.HRATIO * CSMultiplier * .1),
				(int) (60.0 * Game.HRATIO * CSMultiplier * .1));
//		g.fillOval(x - ((int) (30.0 * Game.HRATIO * .1)), y - ((int) (30.0 * Game.HRATIO * .1)), (int) (60.0 * Game.HRATIO * .1),
//				(int) (60.0 * Game.HRATIO * .1));
	
		// while (approachCircleSize != 30) {
//		g.setColor(Color.WHITE);
//		g.drawOval(x - (int) (approachCircleSize / 2), y - (int) (approachCircleSize / 2), (int) approachCircleSize,
//				(int) approachCircleSize);
//		 
	}

	public double getACSize() {
		return approachCircleSize;
	}

	public static boolean checkPress(GameObject circle) {
//		if (approachCircleSize > (59.0 * Game.HRATIO) && approachCircleSize < (61.0 * Game.HRATIO)) {
//			handler.removeObject(this);
//		}
		if (MouseHandler.mouseOver(circle)) {
			if ((circle.getACSize() > (50.0 * Game.HRATIO) && (circle.getACSize() < (70.0 * Game.HRATIO)))) {
				System.out.println("300");
				HUDCircle.setValues(1);
				HUDCircle.setX(circle.getX() - (int)(24.0 * Game.HRATIO));
				HUDCircle.setY(circle.getY() + (int)(4.0 * Game.HRATIO));
				return true;
			} else if (circle.getACSize() > (40.0 * Game.HRATIO) && (circle.getACSize() < (80.0 * Game.HRATIO))) {
				System.out.println("100");
				HUDCircle.setValues(2);
				HUDCircle.setX(circle.getX() - (int)(24.0 * Game.HRATIO));
				HUDCircle.setY(circle.getY() + (int)(4.0 * Game.HRATIO));
				return true;
			} else if (circle.getACSize() > (30.0 * Game.HRATIO) && (circle.getACSize() < (90.0 * Game.HRATIO))) {
				System.out.println("50");
				HUDCircle.setValues(3);
				HUDCircle.setX(circle.getX() - (int)(16.0 * Game.HRATIO));
				HUDCircle.setY(circle.getY() + (int)(4.0 * Game.HRATIO));
				return true;
			} else if (circle.getACSize() <= (30.0 * Game.HRATIO) || (circle.getACSize() >= (90.0 * Game.HRATIO))) {
				System.out.println("miss");
				HUDCircle.setValues(4);
				HUDCircle.setX(circle.getX() - (int)(8.0 * Game.HRATIO));
				HUDCircle.setY(circle.getY() + (int)(4.0 * Game.HRATIO));
				return true;
			}
		} else {
			System.out.println("else");
		}
		return false;
	}
}

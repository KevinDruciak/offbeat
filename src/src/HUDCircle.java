package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import static src.Game.STATE.Game;

public class HUDCircle {

	private int greenValue = 255;
	private String accuracy = "";
	private int score = 0;
	private int streak = 0;
	private double life = 100.0;
	private int val = 0;
	private int maxStreak = 0;
	private int newVal = val;
	private int oldVal = newVal;
	private int counter = 0;
	private Game game;
	private Handler handler;
	private Timing timer;
	private double HPMultiplier = 0.0;
	private int HPDrain = 0;
	LinkedList<Integer> hits = new LinkedList<Integer>();
	private int num50 = 0;
	private int num100 = 0;
	private int num300 = 0;
	private int numMiss = 0;
	private int numMax = 0;
	private int circleX = 0;
	private int circleY = 0;

	public HUDCircle(Game game, Handler handler, Timing timer) {
		this.game = game;
		this.handler = handler;
		this.timer = timer;
	}

	// change for circle
	// i think this hjp drain should be fine
	public void setHP(String hp) {
		double drain = Double.parseDouble(hp);
		HPMultiplier = drain;
		HPDrain = 100 - (int) (drain * 9);
		// if (HPDrain == 0)
	}

	// change for circle
	public String calculateAccuracy() {
		double acc = ((double) ((50.0 * getNum50()) + (100.0 * getNum100()) + (300.0 * getNum300()))
				/ (double) (300.0 * (getNumMiss() + getNum50() + getNum100() + getNum300())));
		acc *= 100;
		String formatted = String.format("%,.2f%%", acc);
		return formatted;
	}

	// used for UR
//	public void addHit(int value) {
//		if (hits.size() < 21) {
//			hits.add(value);
//		} else {
//			hits.remove();
//			hits.add(value);
//		}
//	}

	// change for circle
	public void setValues(int choice) {
		switch (choice) {
		case 1:
			life += 12 - HPMultiplier;
			setScore(getScore() + (300 * getStreak()));
			setStreak(getStreak() + 1);
			// setNumAmazing(getNumAmazing() + 1);
			setNum300(getNum300() + 1);
			setAccuracy(calculateAccuracy());
			val = 1;
			break;
		case 2:
			life += 9 - HPMultiplier;
			setScore(getScore() + (100 * getStreak()));
			setStreak(getStreak() + 1);
			// setNumPerfect(getNumPerfect() + 1);
			setNum100(getNum100() + 1);
			setAccuracy(calculateAccuracy());
			val = 2;
			break;
		case 3:
			life += 6 - HPMultiplier;
			setScore(getScore() + (50 * getStreak()));
			setStreak(getStreak() + 1);
			// setNumGood(getNumGood() + 1);
			setNum50(getNum50() + 1);
			setAccuracy(calculateAccuracy());
			val = 3;
			break;
		case 4:
			life -= HPMultiplier;
			setStreak(0);
			// setNumAverage(getNumAverage() + 1);
			setNumMiss(getNumMiss() + 1);
			setAccuracy(calculateAccuracy());
			val = 4;
			break;
		// add cases for when i implement sliders
		}
	}

	// change for circle
	public void setDefault() {
		greenValue = 255;
		setAccuracy("");
		setScore(0);
		setStreak(0);
		setMaxStreak(0);
		life = 100;
		setNum300(0);
		setNum100(0);
		setNum50(0);
		setNumMiss(0);
		// setNumAmazing(0);
		// setNumPerfect(0);
		// setNumGood(0);
		// setNumAverage(0);
		// setNumMiss(0);
		// setNumBoo(0);
	}

	// might need to change a bit here
	public void tick() {
		//System.out.println(getAccuracy());
		counter++;
		numMax = num300 + num100 + num50;
		if (getStreak() > maxStreak) {
			setMaxStreak(getStreak());
		}
		if (counter == 100 && newVal == oldVal) {
			val = 0;
			counter = 0;
		}
		if (counter % (int) (HPDrain) == 0 && !handler.object.isEmpty()) {
			life--;
		}
		life =  src.Game.clamp((int) life, 0, 100);
		greenValue =  src.Game.clamp(greenValue, 0, 255);
		greenValue = (int) (life * 2);

		if (life <= 0) {
			timer.reset();
			for (int i = 0; i < handler.object.size(); i++) {
				handler.object.get(i).setVelY(0);
			}
			game.gameState =  src.Game.STATE.Fail;
		}
	}

	// complete redo for circle
	public void render(Graphics g) {
		g.setColor(Color.gray);
//		// drawn playfield area
//		g.drawRect((int) (52.0 * Game.WRATIO), (int) (40.0 * Game.HRATIO), (int) (512.0 * Game.WRATIO),
//				(int) (384.0 * Game.HRATIO));
		g.fillRect((int) (590.0 *  src.Game.WRATIO), (int) (80.0 *  src.Game.HRATIO), (int) (24.0 *  src.Game.WRATIO),
				(int) (300.0 *  src.Game.HRATIO));
		g.setColor(new Color(75, greenValue, 0));

		g.fillRect((int) (590.0 *  src.Game.WRATIO),
				(int) (380.0 *  src.Game.HRATIO)
						-  src.Game.clamp((int) ((double) life * (3.0 *  src.Game.HRATIO)), 0, (int) (300.0 *  src.Game.HRATIO)),
				(int) (24.0 *  src.Game.WRATIO),
				src.Game.clamp((int) ((double) life * (3.0 *  src.Game.HRATIO)), 0, (int) (300.0 *  src.Game.HRATIO)));
		g.setColor(Color.white);
		g.drawRect((int) (590.0 *  src.Game.WRATIO), (int) (80.0 *  src.Game.HRATIO), (int) (24.0 *  src.Game.WRATIO),
				(int) (300.0 *  src.Game.HRATIO));
//		g.setColor(Color.white);
//		g.fillRect((int) (205.0 * Game.WRATIO), (int) (380.0 * Game.HRATIO), (int) (50.0 * Game.WRATIO),
//				(int) (16.0 * Game.HRATIO));
//		g.fillRect((int) (265.0 * Game.WRATIO), (int) (380.0 * Game.HRATIO), (int) (50.0 * Game.WRATIO),
//				(int) (16.0 * Game.HRATIO));
//		g.fillRect((int) (325.0 * Game.WRATIO), (int) (380.0 * Game.HRATIO), (int) (50.0 * Game.WRATIO),
//				(int) (16.0 * Game.HRATIO));
//		g.fillRect((int) (385.0 * Game.WRATIO), (int) (380.0 * Game.HRATIO), (int) (50.0 * Game.WRATIO),
//				(int) (16.0 * Game.HRATIO));
		g.drawString("" + getScore(), (int) (15.0 *  src.Game.WRATIO), (int) (64.0 *  src.Game.HRATIO));
		g.drawString("" + getAccuracy(), (int) (15.0 *  src.Game.WRATIO), (int) (80.0 * src.Game.HRATIO));
		g.drawString("" + getStreak(), (int) (15.0 *  src.Game.WRATIO), (int) (96.0 *  src.Game.HRATIO));

//		g.drawRect();
//		g.drawRect();
//		g.drawRect();
//		for (int i = 0; i < hits.size(); i++) {
//			g.drawRect();
//		}
		Font fnt = new Font("courier", 1, (int) (30.0 *  src.Game.HRATIO));
		g.setFont(fnt);
		switch (val) {
		case 1:
			break;
		case 2:
			g.setColor(Color.GREEN);
			g.drawString("100", circleX, circleY);
			break;
		case 3:
			g.setColor(Color.BLUE);
			g.drawString("50", circleX, circleY);
			break;
		case 4:
			g.setColor(Color.RED);
			g.drawString("x", circleX, circleY);
			break;
		}
	}

	public void setMaxStreak(int maxStreak) {
		this.maxStreak = maxStreak;
	}

	public int getMaxStreak() {
		return this.maxStreak;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public int getStreak() {
		return streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}

	public int getNumMiss() {
		return numMiss;
	}

	public void setNumMiss(int numMiss) {
		this.numMiss = numMiss;
	}

	public int getNum50() {
		return num50;
	}

	public void setNum50(int num50) {
		this.num50 = num50;
	}

	public int getNum100() {
		return num100;
	}

	public void setNum100(int num100) {
		this.num100 = num100;
	}

	public int getNum300() {
		return num300;
	}

	public void setNum300(int num300) {
		this.num300 = num300;
	}

	public void setX(int x) {
		this.circleX = x;
	}
	
	public void setY(int y) {
		this.circleY = y;
	}
}

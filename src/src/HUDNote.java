package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import static src.Game.STATE.Game;

public class HUDNote {

	private int greenValue = 255;
	private String accuracy = "";
	private int score = 0;
	private int streak = 0;
	private double life = 100.0;
	private int numAmazing = 0;
	private int numPerfect = 0;
	private int numGood = 0;
	private int numAverage = 0;
	private int numMiss = 0;
	private int numBoo = 0;
	private int numMax = 0;
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
	//LinkedList<Integer> hits = new LinkedList<Integer>();

	public HUDNote(Game game, Handler handler, Timing timer) {
		this.game = game;
		this.handler = handler;
		this.timer = timer;
	}

//	public int calculateScore(int value) {
//		System.out.println(game.getMaxCombo());
//		return (int)((1000000 * 0.5 / game.getMaxCombo())*(value));
//	}
	
	public void setHP(String hp) {
		double drain = Double.parseDouble(hp);
		HPMultiplier = drain;
		HPDrain = 100 - (int)(drain * 9);
	}
	public String calculateAccuracy() {
		double acc = (((50.0 * getNumAverage()) + (100.0 * getNumGood())
				+ (200.0 * getNumPerfect()) + (300.0 * (getNumAmazing() + numMax)))
				/ (300.0 * (getNumMiss() + getNumAverage() + getNumGood() + getNumPerfect() + getNumAmazing() + numMax + getNumMiss())));
		acc *= 100;
		String formatted = String.format("%,.2f%%", acc);
		return formatted;
	}

	//used for UR
//	public void addHit(int value) {
//		if (hits.size() < 21) {
//			hits.add(value);
//		} else {
//			hits.remove();
//			hits.add(value);
//		}
//	}
	
	public void setValues(int choice) {
		switch (choice) {
		case 1:
			life += 12 - HPMultiplier;
			setScore(getScore() + (300));
			setStreak(getStreak() + 1);
			setNumAmazing(getNumAmazing() + 1);
			setAccuracy(calculateAccuracy());
			val = 1;
			break;
		case 2:
			life += 12 - HPMultiplier;
			setScore(getScore() + (300));
			setStreak(getStreak() + 1);
			setNumPerfect(getNumPerfect() + 1);
			setAccuracy(calculateAccuracy());
			val = 2;
			break;
		case 3:
			life += 12 - HPMultiplier;
			setScore(getScore() + (200));
			setStreak(getStreak() + 1);
			setNumGood(getNumGood() + 1);
			setAccuracy(calculateAccuracy());
			val = 3;
			break;
		case 4:
			life += 12 - HPMultiplier;
			setScore(getScore() + (100));
			setStreak(getStreak() + 1);
			setNumAverage(getNumAverage() + 1);
			setAccuracy(calculateAccuracy());
			val = 4;
			break;
		case 5:
			life -= HPMultiplier;
			setStreak(0);
			setNumMiss(getNumMiss() + 1);
			setAccuracy(calculateAccuracy());
			val = 5;
			break;
		case 6:
			life -= HPMultiplier;
			setNumBoo(getNumBoo() + 1);
			val = 6;
			break;
		}
	}

	public void setDefault() {
		greenValue = 255;
		setAccuracy("");
		setScore(0);
		setStreak(0);
		setMaxStreak(0);
		life = 100;
		setNumAmazing(0);
		setNumPerfect(0);
		setNumGood(0);
		setNumAverage(0);
		setNumMiss(0);
		setNumBoo(0);
	}

	public void tick() {
		counter++;
		numMax = numAmazing + numPerfect + numGood + numAverage;
		if (getStreak() > maxStreak) {
			setMaxStreak(getStreak());
		}
		if (counter == 100 && newVal == oldVal) {
			val = 0;
			counter = 0;
		}
		if (counter % (int)(HPDrain) == 0 && !handler.object.isEmpty()) {
			life--;
		}
		life =  src.Game.clamp((int)life, 0, 100);
		greenValue =  src.Game.clamp(greenValue, 0, 255);
		greenValue = (int)(life * 2);
		
		if (life <= 0) {
			timer.reset();
			for (int i = 0; i < handler.object.size(); i++) {
				handler.object.get(i).setVelY(0);
			}
			game.gameState =  src.Game.STATE.Fail;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.gray);
		//drawn playfield area
		//g.drawRect((int)(52.0* Game.WRATIO), (int)(40.0* Game.HRATIO), (int)(512.0* Game.WRATIO), (int)(384.0* Game.HRATIO));
		g.fillRect((int) (590.0 *  src.Game.WRATIO), (int) (80.0 *  src.Game.HRATIO), (int) (24.0 *  src.Game.WRATIO), (int) (300.0 *  src.Game.HRATIO));
	    g.setColor(new Color(75, greenValue, 0));
	    
		g.fillRect((int) (590.0 *  src.Game.WRATIO), (int) (380.0 *  src.Game.HRATIO) -  src.Game.clamp((int)((double) life * (3.0 *  src.Game.HRATIO)), 0, (int) (300.0 *  src.Game.HRATIO)), (int) (24.0 *  src.Game.WRATIO),  src.Game.clamp((int)((double) life * (3.0 *  src.Game.HRATIO)), 0, (int) (300.0 *  src.Game.HRATIO)));
		g.setColor(Color.white);
		g.drawRect((int) (590.0 *  src.Game.WRATIO), (int) (80.0 *  src.Game.HRATIO), (int) (24.0 *  src.Game.WRATIO), (int) (300.0 *  src.Game.HRATIO));
		g.setColor(Color.white);
		g.fillRect((int) (205.0 *  src.Game.WRATIO), (int) (380.0 *  src.Game.HRATIO), (int) (50.0 *  src.Game.WRATIO), (int) (16.0 * src.Game.HRATIO));
		g.fillRect((int) (265.0 *  src.Game.WRATIO), (int) (380.0 *  src.Game.HRATIO), (int) (50.0 *  src.Game.WRATIO), (int) (16.0 * src. Game.HRATIO));
		g.fillRect((int) (325.0 *  src.Game.WRATIO), (int) (380.0 *  src.Game.HRATIO), (int) (50.0 *  src.Game.WRATIO), (int) (16.0 *  src.Game.HRATIO));
		g.fillRect((int) (385.0 *  src.Game.WRATIO), (int) (380.0 *  src.Game.HRATIO), (int) (50.0 *  src.Game.WRATIO), (int) (16.0 * src.Game.HRATIO));
		g.drawString("" + getScore(), (int) (15.0 *  src.Game.WRATIO), (int) (64.0 *  src.Game.HRATIO));
		g.drawString("" + getAccuracy(), (int) (15.0 *  src.Game.WRATIO), (int) (80.0 *  src.Game.HRATIO));
		g.drawString("" + getStreak(), (int) (15.0 *  src.Game.WRATIO), (int) (96.0 *  src.Game.HRATIO));
		
//		g.drawRect();
//		g.drawRect();
//		g.drawRect();
//		for (int i = 0; i < hits.size(); i++) {
//			g.drawRect();
//		}
		Font fnt = new Font("courier", 1, (int) (40.0 *  src.Game.HRATIO));
		g.setFont(fnt);
		switch (val) {
		case 1:
			g.setColor(new Color(255, 0, 0));
			g.drawString("A", (int) (184.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			g.setColor(new Color(255, 128, 0));
			g.drawString("M", (int) (226.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			g.setColor(new Color(255, 255, 0));
			g.drawString("A", (int) (268.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			g.setColor(new Color(64, 255, 0));
			g.drawString("Z", (int) (310.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			g.setColor(new Color(0, 255, 191));
			g.drawString("I", (int) (352.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			g.setColor(new Color(0, 64, 255));
			g.drawString("N", (int) (394.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			g.setColor(new Color(128, 0, 255));
			g.drawString("G", (int) (436.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			break;
		case 2:
			g.setColor(new Color(0, 64, 255));
			g.drawString("PERFECT", (int) (235.0 * src. Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			break;
		case 3:
			g.setColor(new Color(64, 255, 0));
			g.drawString("GOOD", (int) (271.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			break;
		case 4:
			g.setColor(new Color(255, 255, 0));
			g.drawString("AVERAGE", (int) (237.0 * src. Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			break;
		case 5:
			g.setColor(new Color(255, 0, 0));
			g.drawString("MISS", (int) (273.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
			break;
		case 6:
			g.setColor(new Color(102, 51, 0));
			g.drawString("BOO", (int) (284.0 *  src.Game.WRATIO), (int) (200.0 *  src.Game.HRATIO));
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

	public int getNumAmazing() {
		return numAmazing;
	}

	public void setNumAmazing(int numAmazing) {
		this.numAmazing = numAmazing;
	}

	public int getNumPerfect() {
		return numPerfect;
	}

	public void setNumPerfect(int numPerfect) {
		this.numPerfect = numPerfect;
	}

	public int getNumGood() {
		return numGood;
	}

	public void setNumGood(int numGood) {
		this.numGood = numGood;
	}

	public int getNumAverage() {
		return numAverage;
	}

	public void setNumAverage(int numAverage) {
		this.numAverage = numAverage;
	} 

	public int getNumMiss() {
		return numMiss;
	}

	public void setNumMiss(int numMiss) {
		this.numMiss = numMiss;
	}

	public int getNumBoo() {
		return numBoo;
	}

	public void setNumBoo(int numBoo) {
		this.numBoo = numBoo;
	}
}

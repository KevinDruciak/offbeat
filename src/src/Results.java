package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static src.Game.STATE.Game;

public class Results extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUDNote HUDNote;
	private SongSelect songSelect;
	private SpawnerNote spawnerNote;
	private SpawnerCircle spawnerCircle;
	private HUDCircle HUDCircle;
	private int a = 0;

	public Results(Game game, Handler handler, HUDNote HUDNote, SongSelect songSelect, SpawnerNote spawnerNote,
			SpawnerCircle spawnerCircle, HUDCircle HUDCircle) {
		this.game = game;
		this.handler = handler;
		this.HUDNote = HUDNote;
		this.songSelect = songSelect;
		this.spawnerNote = spawnerNote;
		this.spawnerCircle = spawnerCircle;
		this.HUDCircle = HUDCircle;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (mouseOver(mx, my, (int) (30.0 * src.Game.WRATIO), (int) (140.0 * src.Game.HRATIO), (int) (210.0 * src.Game.WRATIO),
				(int) (50.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Results) {
			// spawnerNote.setTimer();
			songSelect.restart();
			if (songSelect.getMode() == 3) {
				spawnerNote.restartMusic();
			} else if (songSelect.getMode() == 0) {
				spawnerCircle.restartMusic();
			}
			a = 0;
		}
		if (mouseOver(mx, my, (int) (30.0 * src.Game.WRATIO), (int) (270.0 * src.Game.HRATIO), (int) (210.0 * src.Game.WRATIO),
				(int) (50.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Results) {
			spawnerNote.setTimer();
			spawnerNote.setplayCount();
			for (int i = 0; i < handler.object.size(); i++) {
				while (handler.object.size() != 0) {
					GameObject tempObject = handler.object.get(i);
					handler.removeObject(tempObject);
				}
			}
			if (songSelect.getMode() == 3) {
				HUDNote.setDefault();
			}
			if (songSelect.getMode() == 0) {
				HUDCircle.setDefault();
			}
			// HUDNote.setDefault();
			songSelect.setBeatmap(" ");
			game.gameState = src.Game.STATE.SongSelect;
			a = 0;
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void tick() {
		if (songSelect.getMode() == 3) {
			spawnerNote.stopMusic();
		} else if (songSelect.getMode() == 0) {
			spawnerCircle.stopMusic();
		}
	}

	public void render(Graphics g) {
		if (songSelect.getMode() == 3) {
			Font fnt = new Font("courier", 1, (int) (50.0 * src.Game.HRATIO));
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Results", (int) (33.0 * src.Game.WRATIO), (int) (80.0 * src.Game.HRATIO));

			Font fnt2 = new Font("courier", 1, (int) (20.0 * src.Game.HRATIO));
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("retry", (int) (105.0 * src.Game.WRATIO), (int) (170.0 * src.Game.HRATIO));
			g.drawString("quit", (int) (110.0 * src.Game.WRATIO), (int) (300.0 * src.Game.HRATIO));
			g.drawRect((int) (30.0 * src.Game.WRATIO), (int) (140.0 * src.Game.HRATIO), (int) (210.0 * src.Game.WRATIO),
					(int) (50.0 * src.Game.HRATIO));
			g.drawRect((int) (30.0 * src.Game.WRATIO), (int) (270.0 * src.Game.HRATIO), (int) (210.0 * src.Game.WRATIO),
					(int) (50.0 * src.Game.HRATIO));

			Font fnt3 = new Font("courier", 1, (int) (15.0 * src.Game.HRATIO));
			g.setFont(fnt3);
			g.setColor(Color.black);
			g.fillRect((int) (610.0 * src.Game.WRATIO), 0, (int) (200.0 * src.Game.WRATIO), (int) (600.0 * src.Game.HRATIO));
			g.fillRect((int) (270.0 * src.Game.WRATIO), 0, (int) (400.0 * src.Game.WRATIO), (int) (15.0 * src.Game.HRATIO));
			g.fillRect((int) (270.0 * src.Game.WRATIO), (int) (435.0 * src.Game.HRATIO), (int) (400.0 * src.Game.WRATIO),
					(int) (25.0 * src.Game.HRATIO));
			g.setColor(Color.white);
			g.drawRect((int) (270.0 * src.Game.WRATIO), (int) (10.0 * src.Game.HRATIO), (int) (350.0 * src.Game.WRATIO),
					(int) (430.0 * src.Game.HRATIO));
			Font fnt4 = new Font("courier", 1, (int) (25.0 * src.Game.HRATIO));
			g.setFont(fnt4);
			g.drawString("Score:      " + HUDNote.getScore(), (int) (280.0 * src.Game.WRATIO), (int) (39.0 * src.Game.HRATIO));
			g.drawString("Accuracy:   " + HUDNote.getAccuracy(), (int) (280.0 * src.Game.WRATIO),
					(int) (83.0 * src.Game.HRATIO));
			g.drawString("Max Streak: " + HUDNote.getMaxStreak(), (int) (280.0 * src.Game.WRATIO),
					(int) (131.0 * src.Game.HRATIO));
			g.drawString("Amazing:    " + HUDNote.getNumAmazing(), (int) (280.0 * src.Game.WRATIO),
					(int) (179.0 * src.Game.HRATIO));
			g.drawString("Perfect:    " + HUDNote.getNumPerfect(), (int) (280.0 * src.Game.WRATIO),
					(int) (227.0 * src.Game.HRATIO));
			g.drawString("Good:       " + HUDNote.getNumGood(), (int) (280.0 * src.Game.WRATIO),
					(int) (276.0 * src.Game.HRATIO));
			g.drawString("Average:    " + HUDNote.getNumAverage(), (int) (280.0 * src.Game.WRATIO),
					(int) (324.0 * src.Game.HRATIO));
			g.drawString("Miss:       " + HUDNote.getNumMiss(), (int) (280.0 * src.Game.WRATIO),
					(int) (372.0 * src.Game.HRATIO));
			g.drawString("Boo:        " + HUDNote.getNumBoo(), (int) (280.0 * src.Game.WRATIO),
					(int) (420.0 * src.Game.HRATIO));
		}
		if (songSelect.getMode() == 0) {
			Font fnt = new Font("courier", 1, (int) (50.0 * src.Game.HRATIO));
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Results", (int) (33.0 * src.Game.WRATIO), (int) (80.0 * src.Game.HRATIO));

			Font fnt2 = new Font("courier", 1, (int) (20.0 * src.Game.HRATIO));
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("retry", (int) (105.0 * src.Game.WRATIO), (int) (170.0 * src.Game.HRATIO));
			g.drawString("quit", (int) (110.0 * src.Game.WRATIO), (int) (300.0 * src.Game.HRATIO));
			g.drawRect((int) (30.0 * src.Game.WRATIO), (int) (140.0 * src.Game.HRATIO), (int) (210.0 * src.Game.WRATIO),
					(int) (50.0 * src.Game.HRATIO));
			g.drawRect((int) (30.0 * src.Game.WRATIO), (int) (270.0 * src.Game.HRATIO), (int) (210.0 * src.Game.WRATIO),
					(int) (50.0 * src.Game.HRATIO));

			Font fnt3 = new Font("courier", 1, (int) (15.0 * src.Game.HRATIO));
			g.setFont(fnt3);
			g.setColor(Color.black);
			g.fillRect((int) (610.0 * src.Game.WRATIO), 0, (int) (200.0 * src.Game.WRATIO), (int) (600.0 * src.Game.HRATIO));
			g.fillRect((int) (270.0 * src.Game.WRATIO), 0, (int) (400.0 * src.Game.WRATIO), (int) (15.0 * src.Game.HRATIO));
			g.fillRect((int) (270.0 * src.Game.WRATIO), (int) (435.0 * src.Game.HRATIO), (int) (400.0 * src.Game.WRATIO),
					(int) (25.0 * src.Game.HRATIO));
			g.setColor(Color.white);
			g.drawRect((int) (270.0 * src.Game.WRATIO), (int) (10.0 * src.Game.HRATIO), (int) (350.0 * src.Game.WRATIO),
					(int) (430.0 * src.Game.HRATIO));
			Font fnt4 = new Font("courier", 1, (int) (25.0 * src.Game.HRATIO));
			g.setFont(fnt4);
			g.drawString("Score:      " + HUDCircle.getScore(), (int) (280.0 * src.Game.WRATIO), (int) (39.0 * src.Game.HRATIO));
			g.drawString("Accuracy:   " + HUDCircle.getAccuracy(), (int) (280.0 * src.Game.WRATIO),
					(int) (83.0 * src.Game.HRATIO));
			g.drawString("Max Streak: " + HUDCircle.getMaxStreak(), (int) (280.0 * src.Game.WRATIO),
					(int) (131.0 * src.Game.HRATIO));
			g.drawString("300:        " + HUDCircle.getNum300(), (int) (280.0 * src.Game.WRATIO),
					(int) (179.0 * src.Game.HRATIO));
			g.drawString("100:        " + HUDCircle.getNum100(), (int) (280.0 * src.Game.WRATIO),
					(int) (227.0 * src.Game.HRATIO));
			g.drawString("50:         " + HUDCircle.getNum50(), (int) (280.0 * src.Game.WRATIO),
					(int) (276.0 * src.Game.HRATIO));
			g.drawString("Miss:       " + HUDCircle.getNumMiss(), (int) (280.0 * src.Game.WRATIO),
					(int) (324.0 * src.Game.HRATIO));
		}
	}
}

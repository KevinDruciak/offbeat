package src;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static src.Game.STATE.Game;
import src.Game;

public class Fail extends MouseAdapter {

	private Game game;
	private Handler handler;
	private SpawnerNote spawnerNote;
	private HUDNote HUDNote;
	private SongSelect songSelect;
	private SpawnerCircle spawnerCircle;
	private int a = 0;

	public Fail(Game game, Handler handler, SpawnerNote spawnerNote, HUDNote HUDNote, SongSelect songSelect,
			SpawnerCircle spawnerCircle) {
		this.game = game;
		this.handler = handler;
		this.spawnerNote = spawnerNote;
		this.HUDNote = HUDNote;
		this.songSelect = songSelect;
		this.spawnerCircle = spawnerCircle;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (mouseOver(mx, my, (int) (213.0 * src.Game.WRATIO), (int) (180.0 *  src.Game.HRATIO), (int) (213.0 *  src.Game.WRATIO),
				(int) (60.0 * src.Game.HRATIO)) && game.gameState ==  src.Game.STATE.Fail) {
			for (int i = 0; i < handler.object.size(); i++) {
				while (handler.object.size() != 0) {
					GameObject tempObject = handler.object.get(i);
					handler.removeObject(tempObject);
				}
			}
			songSelect.restart();
			// spawnerNote.restartMusic();
			if (songSelect.getMode() == 3) {
				spawnerNote.restartMusic();
			} else if (songSelect.getMode() == 0) {
				spawnerCircle.restartMusic();
			}
			a = 0;
		}
		if (mouseOver(mx, my, (int) (213.0 *  src.Game.WRATIO), (int) (300.0 *  src.Game.HRATIO), (int) (213.0 *  src.Game.WRATIO),
				(int) (60.0 *  src.Game.HRATIO)) && game.gameState ==  src.Game.STATE.Fail) {
			spawnerNote.setTimer();
			for (int i = 0; i < handler.object.size(); i++) {
				while (handler.object.size() != 0) {
					GameObject tempObject = handler.object.get(i);
					handler.removeObject(tempObject);
				}
			}
			HUDNote.setDefault();
			songSelect.setBeatmap(" ");
			game.gameState =  src.Game.STATE.SongSelect;
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

	public void render(Graphics g) {

		if (game.gameState ==  src.Game.STATE.Fail) {
			Font fnt = new Font("courier", 1, (int) (50.0 *  src.Game.HRATIO));
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("failed", (int) (228.0 *  src.Game.WRATIO), (int) (100.0 *  src.Game.HRATIO));
			g.setColor(Color.white);
			g.drawRect((int) (213.0 *  src.Game.WRATIO), (int) (180.0 *  src.Game.HRATIO), (int) (213.0 *  src.Game.WRATIO),
					(int) (60.0 *  src.Game.HRATIO));
			g.drawRect((int) (213.0 *  src.Game.WRATIO), (int) (300.0 *  src.Game.HRATIO), (int) (213.0 *  src.Game.WRATIO),
					(int) (60.0 *  src.Game.HRATIO));

			Font fnt2 = new Font("courier", 1, (int) (25.0 *  src.Game.HRATIO));
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("retry", (int) (280.0 *  src.Game.WRATIO), (int) (215.0 *  src.Game.HRATIO));
			g.drawString("quit", (int) (286.0 *  src.Game.WRATIO), (int) (338.0 *  src.Game.HRATIO));
		}
	}

	public void tick() {
		if (a == 0) {
			// spawnerNote.restartMusic();
			if (songSelect.getMode() == 3) {
				spawnerNote.stopMusic();
			} else if (songSelect.getMode() == 0) {
				spawnerCircle.stopMusic();
			}
			// spawnerNote.stopMusic();
			a++;
		}
	}
}

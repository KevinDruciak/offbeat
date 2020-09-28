package src;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;

import static src.Game.STATE.Game;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private Game game;
	private boolean[] keyDown = new boolean[4];
	private SpawnerNote spawnerNote;
	private SongSelect songSelect;
	private SpawnerCircle spawnerCircle;

	public KeyInput(Handler handler, Game game, HUDNote HUDNote, SpawnerNote spawnerNote, SongSelect songSelect,
			SpawnerCircle spawnerCircle) {
		this.handler = handler;
		this.game = game;
		this.spawnerNote = spawnerNote;
		this.songSelect = songSelect;
		this.spawnerCircle = spawnerCircle;
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (game.gameState ==  src.Game.STATE.SongSelect) {
			if (key == KeyEvent.VK_UP) {
				songSelect.setMWheelValue(1);
			} else if (key == KeyEvent.VK_DOWN) {
				songSelect.setMWheelValue(0);
			} else if (key == KeyEvent.VK_ENTER && !songSelect.getBeatmap().contentEquals(" ")) {
				songSelect.playBeatmap();
			} else if (key == KeyEvent.VK_RIGHT) {
				songSelect.setBeatmap(1);
			} else if (key == KeyEvent.VK_LEFT) {
				songSelect.setBeatmap(0);
			}
		}
		if (key == KeyEvent.VK_ESCAPE && game.gameState ==  src.Game.STATE.Game) {
			if (songSelect.getMode() == 3) {
				spawnerNote.stopMusic();
			} else if (songSelect.getMode() == 0) {
				spawnerCircle.stopMusic();
			}
			// spawnerNote.setTimer();
			// spawnerNote.setTimer();

			while (handler.object.size() != 0) {
				for (int i = 0; i < handler.object.size(); i++) {
					handler.removeObject(handler.object.get(i));
				}
			}
			game.gameState = src.Game.STATE.Results;
		}
		if (game.gameState == src.Game.STATE.Game) {
			if (songSelect.getMode() == 3) {
				for (int i = 0; i < src.Game.clamp(handler.object.size(), 0, 4); i++) {
					GameObject tempObject = handler.object.get(i);
					if (key == KeyEvent.VK_D && tempObject.getId() == ID.Note1) {
						keyDown[0] = true;
						if (Note.checkPress(tempObject)) {
							handler.removeObject(tempObject);
							break;
						}
					} else if (key == KeyEvent.VK_F && tempObject.getId() == ID.Note2) {
						keyDown[1] = true;
						if (Note.checkPress(tempObject)) {
							handler.removeObject(tempObject);
							break;
						}
					} else if (key == KeyEvent.VK_J && tempObject.getId() == ID.Note3) {
						keyDown[2] = true;
						if (Note.checkPress(tempObject)) {
							handler.removeObject(tempObject);
							break;
						}
					} else if (key == KeyEvent.VK_K && tempObject.getId() == ID.Note4) {
						keyDown[3] = true;
						if (Note.checkPress(tempObject)) {
							handler.removeObject(tempObject);
							break;
						}
					}
				}
			} else if (songSelect.getMode() == 0) {
				if ((key == KeyEvent.VK_Z || key == KeyEvent.VK_X) && !handler.object.isEmpty()) {
					// System.out.println(handler.object.size());
					if (Circle.checkPress(handler.object.getFirst())) {
						handler.removeObject(handler.object.getFirst());
					}
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_D) {
			keyDown[0] = false;
		} else if (key == KeyEvent.VK_F) {
			keyDown[1] = false;
		} else if (key == KeyEvent.VK_J) {
			keyDown[2] = false;
		} else if (key == KeyEvent.VK_K) {
			keyDown[3] = false;
		}
	}
}

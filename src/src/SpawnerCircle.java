package src;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;

import static src.Game.STATE.Game;

public class SpawnerCircle{

	private Handler handler;
	private HUDCircle HUDCircle;
	private Game game;
	private Timing timer;
	private Music music;
	private MouseHandler mouseHandler;
	
	private int i = 0, t = 0, a = 0;
	private int[][] notes;
//create HUDCircle
	public SpawnerCircle(Handler handler, HUDCircle HUDCircle, Game game, Timing timer, MouseHandler mouseHandler) throws FileNotFoundException {
		this.handler = handler;
		this.HUDCircle = HUDCircle;
		this.game = game;
		this.timer = timer;
		this.mouseHandler = mouseHandler;
	}

	public void stopMusic() {
		music.stopMusic();
	}

	public void pauseMusic() {
		music.pauseMusic();
	}

	public void resumeMusic() {
		music.resumeMusic();
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public void restartMusic() {
		music.restartMusic();
	}

	public void setTimer() {
		t = 0;
		i = 0;
		a = 0;
		timer.reset();
	}

	public void spawnNotes(int[][] notes) {
		//System.out.println("called");
		timer.start();
		this.notes = notes;
	}
	
	public void tick() {
		if (a == 0) {
			if (music.playMusic()) {
				a = 1;
			}
		}
		if (i < notes.length - 1) {
			//value was 12.0
			if (notes[i][2] > timer.getElapsedTime() - (int) (12.0 * src.Game.HRATIO)
					&& notes[i][2] < timer.getElapsedTime() + (int) (12.0 * src.Game.HRATIO)) {
				handler.addObject(new Circle((int) ((double)notes[i][0] * src.Game.HRATIO) + (int) (53.0 * src.Game.HRATIO), (int) ((double)notes[i][1] * src.Game.WRATIO) + (int) (40.0 * src.Game.WRATIO), ID.Circle, handler, HUDCircle, mouseHandler));
				i++;
			}
		} else {
			t++;
			if (t == 600) {
				timer.stop();
				music.pauseMusic();
				game.gameState = src.Game.STATE.Results;
			}
		}
	}
}

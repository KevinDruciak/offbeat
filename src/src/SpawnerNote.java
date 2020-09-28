package src;

import java.io.FileNotFoundException;
import java.util.Arrays;

//import Game.STATE;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import static src.Game.STATE.Game;

public class SpawnerNote {
	private Game game;
	private Timing timer;
	private Music music;
	private Handler handler;
	private HUDNote HUDNote;
	private int playCount = 0;

	private int i = 0, t = 0, a = 0;
	public int[][] notes;

	public SpawnerNote(Handler handler, HUDNote HUDNote, Game game, Timing timer) throws FileNotFoundException {
		this.game = game;
		this.timer = timer;
		this.handler = handler;
		this.HUDNote = HUDNote;
	}

	public int getMaxCombo() {
		return notes.length;
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
		// timer.start();
		// music.playMusic();
	}

	public void restartMusic() {
		music.restartMusic();
	}

	public void setTimer() {
		t = 0;
		i = 0;
		a = 0;
		timer.reset();
		//timer.stop();
	}

	public void startTimer() {
		timer.start();
	}
	
	public void spawnNotes(int[][] notes) {
		// System.out.println("called3");
	    timer.start();
		this.notes = notes;
		
	}

	public void tick() {
		//System.out.println("ticking");
		 //System.out.println(timer.getElapsedTime());
		//if (music != null) {
//			if (a == 0 && music.getStatus() != true && playCount == 0) {
//				//if (music.playMusic()) {
//					// System.out.println("playing");
//				
//					playCount++;
//					a = 1;
//				}
//			//}
//		}
		if (a == 0 && playCount == 0) {
			music.playMusic();
			a++;
			playCount++;
			//timer.start();
		}
		// music.playMusic();
		// music.playMusic();
		// System.out.println(Arrays.deepToString(notes));
		// music.playMusic();
		if (i < notes.length - 1) {
			if (notes[i][0] == 64 && (notes[i][1] > timer.getElapsedTime() - (int) (12.0 * src.Game.HRATIO)
					&& notes[i][1] < timer.getElapsedTime() + (int) (12.0 * src.Game.HRATIO))) {
				handler.addObject(new Note((int) (205.0 * src.Game.WRATIO), 0, ID.Note1, handler, HUDNote));
				i++;
			}
			if (notes[i][0] == 192 && (notes[i][1] > timer.getElapsedTime() - (int) (12.0 * src.Game.HRATIO)
					&& notes[i][1] < timer.getElapsedTime() + (int) (12.0 * src.Game.HRATIO))) {
				handler.addObject(new Note((int) (265.0 * src.Game.WRATIO), 0, ID.Note2, handler, HUDNote));
				i++;
			}
			if (notes[i][0] == 320 && (notes[i][1] > timer.getElapsedTime() - (int) (12.0 * src.Game.HRATIO)
					&& notes[i][1] < timer.getElapsedTime() + (int) (12.0 * src.Game.HRATIO))) {
				handler.addObject(new Note((int) (325.0 * src.Game.WRATIO), 0, ID.Note3, handler, HUDNote));
				i++;
			}
			if (notes[i][0] == 448 && (notes[i][1] > timer.getElapsedTime() - (int) (12.0 * src.Game.HRATIO)
					&& notes[i][1] < timer.getElapsedTime() + (int) (12.0 * src.Game.HRATIO))) {
				handler.addObject(new Note((int) (385.0 * src.Game.WRATIO), 0, ID.Note4, handler, HUDNote));
				i++;
			}
		} else {
			t++;
			if (t == 600) {
				timer.stop();
				music.stopMusic();
				game.gameState = src.Game.STATE.Results;
			}
		}
	}

	public void setplayCount() {
		playCount = 0;
	}
}

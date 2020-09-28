package src;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Music {

	private HashMap<String, String> mapFiles;
	private String beatmap;
	MediaPlayer mediaPlayer;
	private Handler handler;
	private Timing timer;
	private SongSelect songSelect;

	public Music(Game game, Handler handler, Timing timer, SongSelect songSelect) {
		mapFiles = game.getAudiofiles();
		this.handler = handler;
		this.timer = timer;
		this.songSelect = songSelect;
		this.beatmap = songSelect.getBeatmap();
	}

	public void openAudioFile() {
		File f = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString() + "/"
				+ (beatmap.substring(0, beatmap.length() - 4)) + "/" + mapFiles.get(beatmap));
		// System.out.println(f.toURI().toURL().toExternalForm());
		// System.out.println(f.toURI().toString());
		Media music = new Media(f.toURI().toString());
		// Media music = new Media(f.toURI().toURL().toExternalForm());
		// while (mediaPlayer == null) {
		mediaPlayer = new MediaPlayer(music);
		// }
		mediaPlayer.setVolume(1.0);
		// mediaPlayer.setOnError(() -> System.out.println("Error : " +
		// mediaPlayer.getError().toString()));
		// System.out.println("music is set");
	}

	public boolean playMusic() {
			Thread thread = new Thread() {
				public void run() {
					while (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
						if (timer.getElapsedTime() > 0 && timer.getElapsedTime() < 1000) {
							// System.out.println("called1");
//							try {
//								Thread.sleep(100);
//								mediaPlayer.setStartTime(new Duration(0));
//								mediaPlayer.seek(Duration.ZERO);
//								mediaPlayer.play();
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
						}
					}
				}
			};
			thread.start();
		return true;
	}

	public void pauseMusic() {
		mediaPlayer.pause();
	}

	public void resumeMusic() {
		mediaPlayer.play();
	}

	public boolean getStatus() {
		if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
			return true;
		} else {
			return false;
		}
	}

	public void restartMusic() {
		// mediaPlayer.seek(Duration.ZERO);
		// mediaPlayer.stop();
		// System.out.println(mediaPlayer.getStatus());
		// mediaPlayer.setStartTime(new Duration(0));
		// System.out.println(f.toURI().toString());
//		while (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {// && timer.getElapsedTime() > 460) {
//			System.out.println(mediaPlayer.getStatus());
//			// System.out.println(timer.getElapsedTime());
//			if (timer.getElapsedTime() > 440) {
//				// Thread.sleep(1);
//				// mediaPlayer.stop();
//				mediaPlayer.setStartTime(new Duration(0));
//
//				// mediaPlayer.seek(Duration.ZERO);
//				// System.out.println("called");
//				// mediaPlayer.stop();
//				openAudioFile();
//				// mediaPlayer.play();
//				// mediaPlayer.play();
//			}
//			// System.out.println("called1");
//			// openAudioFile();
//			mediaPlayer.play();
//			// mediaPlayer.play();
//		}
//		System.out.println(mediaPlayer.getStatus());
//		// System.out.println(mediaPlayer);
		System.out.println(songSelect.getMode());
		if (songSelect.getMode() == 3) {
			Thread thread = new Thread() {
				public void run() {
					//System.out.println("Thread Running2");
					//System.out.println(mediaPlayer.getStatus());
					while (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {// && timer.getElapsedTime() > 460) {
						//System.out.println(mediaPlayer.getStatus());
						// System.out.println(timer.getElapsedTime());
						//System.out.println("called");
						if (timer.getElapsedTime() > 340) {
							//System.out.println("called");
							//System.out.println(timer.getElapsedTime());
							try {
								Thread.sleep(100);
								mediaPlayer.setStartTime(new Duration(0));
								mediaPlayer.seek(Duration.ZERO);
								mediaPlayer.play();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//System.out.println("trying to play the damn song2");
//						mediaPlayer.setStartTime(new Duration(0));
//						mediaPlayer.seek(Duration.ZERO);
//						mediaPlayer.play();
						}
					}
				}
			};
			// thread.setPriority(10);
			thread.start();
		} else if (songSelect.getMode() == 0) {
			
			Thread thread = new Thread() {
				public void run() {
					//System.out.println("Thread Running2");
					while (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {// && timer.getElapsedTime() > 460) {
						// System.out.println(mediaPlayer.getStatus());
						// System.out.println(timer.getElapsedTime());
						if (timer.getElapsedTime() > 150 + (Circle.getARTiming() - 800)) {
							//System.out.println(Circle.getARTiming());
							try {
								Thread.sleep(100);
								mediaPlayer.setStartTime(new Duration(0));
								mediaPlayer.seek(Duration.ZERO);
								mediaPlayer.play();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//System.out.println("trying to play the damn song2");
//							mediaPlayer.setStartTime(new Duration(0));
//							mediaPlayer.seek(Duration.ZERO);
//							mediaPlayer.play();
						}
					}
				}
			};
			// thread.setPriority(10);
			thread.start();
		}
	}

	public void stopMusic() {
		// mediaPlayer.stop();
		mediaPlayer.pause();
		mediaPlayer.seek(Duration.ZERO);
		// mediaPlayer.dispose();
	}
}

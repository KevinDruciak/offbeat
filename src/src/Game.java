package src;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import javafx.embed.swing.JFXPanel;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -427742772996586875L;

	public static int WIDTH = 640, HEIGHT = 480;
	public static boolean fullscreen = false;
	public static int RHEIGHT = 480;
	public static int RWIDTH = 640;
	public static double HRATIO;
	public static double WRATIO;

	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private HUDNote HUDNote;
	private HUDCircle HUDCircle;
	private SpawnerNote spawnerNote;
	private Menu menu;
	private SongSelect songSelect;
	private Graphics g;
	private Fail fail;
	private Results results;
	private Timing timer;
	private Reader reader;
	private SpawnerCircle spawnerCircle;
	private MouseHandler mouseHandler;
	private int index = 0;
	private int mode;

	public enum STATE {
		Game, Pause, Score, Help, Menu, SongSelect, Fail, Results, Settings;
	};

	public static void setRatios(int height, int width) {
		HRATIO = (double) height / (double) HEIGHT;
		WRATIO = (double) width / (double) WIDTH;
		RHEIGHT = (int) ((double)HEIGHT * HRATIO);
		RWIDTH = (int) ((double)WIDTH * WRATIO);
	}
	
	public int getMaxCombo() {
		return spawnerNote.getMaxCombo();
	}
	
	public String[] getBeatmaps() throws IOException {
		File f = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString());
		System.out.println(f.toString());
		String[] paths = f.list();
		System.out.println(Arrays.toString(paths));
		String[] beatmaps = new String[paths.length];
		for (int i = 0; i < paths.length; i++) {
			File fp = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString() + "/" + paths[i]);
			String[] files = fp.list();
			//System.out.println(Arrays.toString(files));
			//System.out.println(files[0].toString());
			//System.out.println(files[0].substring(files[0].length() - 4));
			if (files[0].substring(files[0].length() - 4).equals(".osu")) {
				beatmaps[index] = files[0];
				index++;
			} else if (files[1].substring(files[1].length() - 4).equals(".osu")) {
				beatmaps[index] = files[1];
				index++;
			} else if (files[2].substring(files[2].length() - 4).equals(".osu")) {
				beatmaps[index] = files[2];
				index++;
			}
		}
		return beatmaps;
	}

	public HashMap<String, String> getAudiofiles() {
		File f = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString());
		String[] paths = f.list();

		HashMap<String, String> audioFiles = new HashMap<String, String>();
		for (int i = 0; i < paths.length; i++) {
			File fp = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString() + "/" + paths[i]);
			String[] files = fp.list();
			if (files[0].substring(files[0].length() - 4).equals(".osu")) {
				if (files[1].substring(files[1].length() - 4).equals(".jpg")) {
					audioFiles.put(files[0], files[2]);
				} else if (files[1].substring(files[1].length() - 4).equals(".mp3")) {
					audioFiles.put(files[0], files[1]);
				}
			} else if (files[0].substring(files[0].length() - 4).equals(".mp3")) {
				if (files[1].substring(files[1].length() - 4).equals(".jpg")) {
					audioFiles.put(files[2], files[0]);
				} else if (files[1].substring(files[1].length() - 4).equals(".osu")) {
					audioFiles.put(files[1], files[0]);
				}
			} else if (files[0].substring(files[0].length() - 4).equals(".jpg")) {
				if (files[1].substring(files[1].length() - 4).equals(".osu")) {
					audioFiles.put(files[1], files[2]);
				} else if (files[1].substring(files[1].length() - 4).equals(".mp3")) {
					audioFiles.put(files[2], files[1]);
				}
			}
		}
		return audioFiles;
	}
	
	public HashMap<String, String> getBackgrounds() {
		File f = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString());
		String[] paths = f.list();

		HashMap<String, String> backgroundImages = new HashMap<String, String>();
		for (int i = 0; i < paths.length; i++) {
			File fp = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString() + "/" + paths[i]);
			String[] files = fp.list();
			if (files[0].substring(files[0].length() - 4).equals(".osu")) {
				if (files[1].substring(files[1].length() - 4).equals(".jpg")) {
					backgroundImages.put(files[0], files[1]);
				} else if (files[1].substring(files[1].length() - 4).equals(".mp3")) {
					backgroundImages.put(files[0], files[2]);
				}
			} else if (files[0].substring(files[0].length() - 4).equals(".mp3")) {
				if (files[1].substring(files[1].length() - 4).equals(".jpg")) {
					backgroundImages.put(files[2], files[1]);
				} else if (files[1].substring(files[1].length() - 4).equals(".osu")) {
					backgroundImages.put(files[1], files[2]);
				}
			} else if (files[0].substring(files[0].length() - 4).equals(".jpg")) {
				if (files[1].substring(files[1].length() - 4).equals(".osu")) {
					backgroundImages.put(files[1], files[0]);
				} else if (files[1].substring(files[1].length() - 4).equals(".mp3")) {
					backgroundImages.put(files[2], files[0]);
				}
			}
		}
		return backgroundImages;
	}

	public STATE gameState = STATE.Menu;

	public Game() throws IOException, URISyntaxException {
		handler = new Handler();
		timer = new Timing();
		mouseHandler = new MouseHandler();
		menu = new Menu(this);
		HUDNote = new HUDNote(this, handler, timer);
		HUDCircle = new HUDCircle(this, handler, timer);
		spawnerNote = new SpawnerNote(handler, HUDNote, this, timer);
		spawnerCircle = new SpawnerCircle(handler, HUDCircle, this, timer, mouseHandler);
		songSelect = new SongSelect(this, reader, spawnerNote, HUDNote, handler, timer, spawnerCircle, HUDCircle);
		fail = new Fail(this, handler, spawnerNote, HUDNote, songSelect, spawnerCircle);
		results = new Results(this, handler, HUDNote, songSelect, spawnerNote, spawnerCircle, HUDCircle);

		this.addKeyListener(new KeyInput(handler, this, HUDNote, spawnerNote, songSelect, spawnerCircle));
		this.addMouseListener(menu);
		this.addMouseListener(fail);
		this.addMouseListener(results);
		this.addMouseWheelListener(songSelect);
		this.addMouseListener(songSelect);
		this.addMouseListener(mouseHandler);

		Game.setRatios(480, 640);
		new Window(WIDTH, HEIGHT, "offbeat", this);
		@SuppressWarnings("unused")
		JFXPanel fxPanel = new JFXPanel();
		this.requestFocus();
	}

	public synchronized void start() {
		thread = new Thread(this);
		//thread.setPriority(1);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 300.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		@SuppressWarnings("unused")
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	private void tick() {
		handler.tick();
		if (gameState == STATE.Game) {
			//HUDNote.tick();
			//do this for render too
			//System.out.println(mode);
			if (mode == 3) {
				spawnerNote.tick();
				HUDNote.tick();
			} 
			if (mode == 0) {
				spawnerCircle.tick();
				HUDCircle.tick();
			}
			//HUDNote.tick();
		} else if (gameState == STATE.Menu) {
			menu.tick();
		} else if (gameState == STATE.SongSelect) {
			songSelect.tick();
		} else if (gameState == STATE.Fail) {
			fail.tick();
		} else if (gameState == STATE.Results) {
			results.tick();
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, (int) ((double) WIDTH * WRATIO), (int) ((double) HEIGHT * HRATIO));

		handler.render(g);
		if (gameState == STATE.Game) {
			if (mode == 3) {
				HUDNote.render(g);
			}
			if (mode == 0) {
				HUDCircle.render(g);
			}
			//HUDNote.render(g);
		} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Settings) {
			menu.render(g);
		} else if (gameState == STATE.SongSelect) {
			songSelect.render(g);
		} else if (gameState == STATE.Fail) {
			fail.render(g);
		} else if (gameState == STATE.Results) {
			results.render(g);
		}

		g.dispose();
		bs.show();
	} 

	public static int clamp(int var, int min, int max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}
	
	public static double clamp(double var, double min, double max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}

	public static void main(String args[]) throws IOException, URISyntaxException {
		new Game();
	}

	
}
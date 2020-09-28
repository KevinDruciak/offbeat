package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;
import static src.Game.STATE.Game;

public class SongSelect extends MouseAdapter {

	public boolean transition = false;
	public boolean upCount = true;
	private Game game;
	private String[] beatmaps;
	private int mwheelValue = 0;
	private String beatmap = " ";
	private Reader reader;
	private SpawnerNote spawnerNote;
	private SpawnerCircle spawnerCircle;
	private HUDNote HUDNote;
	private Music music;
	private Handler handler;
	private Timing timer;
	private String title = "";
	private String artist = "";
	private String version = "";
	private String mode = "";
	private String hp = "";
	private String cs = "";
	private String od = "";
	private String ar = "";
	private HashMap<String, String> backgrounds;
	private BufferedImage background;
	private ImageObserver observer;
	private int a = 200;
	private HUDCircle HUDCircle;

	public String getBeatmap() {
		return this.beatmap;
	}

	public void setBeatmap(String val) {
		this.beatmap = val;
	}

	public SongSelect(Game game, Reader reader, SpawnerNote spawnerNote, HUDNote HUDNote, Handler handler, Timing timer,
			SpawnerCircle spawnerCircle, HUDCircle HUDCircle) throws IOException {
		this.game = game;
		this.beatmaps = game.getBeatmaps();
		this.reader = reader;
		this.spawnerNote = spawnerNote;
		this.HUDNote = HUDNote;
		this.HUDCircle = HUDCircle;
		this.handler = handler;
		this.timer = timer;
		this.spawnerCircle = spawnerCircle;
		backgrounds = game.getBackgrounds();
	}

	public int getMode() {
		return Integer.parseInt(mode.substring(1));
	}

	public void restart() {
		if (Integer.parseInt(mode.substring(1)) == 3) {
			spawnerNote.setTimer();
			HUDNote.setDefault();
		} else if (Integer.parseInt(mode.substring(1)) == 0) {
			spawnerCircle.setTimer();
			HUDCircle.setDefault();
		}
		// change this later to include HUDCircle
		
		//HUDNote.setDefault();
		try {
			reader = new Reader(beatmap, spawnerNote, spawnerCircle);
			reader.startReading();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		game.setMode(Integer.parseInt(mode.substring(1)));
		music = new Music(game, handler, timer, this);
		music.openAudioFile();
		if (Integer.parseInt(mode.substring(1)) == 3) {
			spawnerNote.setMusic(music);
		} else if (Integer.parseInt(mode.substring(1)) == 0) {
			spawnerCircle.setMusic(music);
		}
		game.gameState = src.Game.STATE.Game;
	}

	public void playBeatmap() {
		try {
			reader = new Reader(beatmap, spawnerNote, spawnerCircle);
			reader.startReading();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		game.setMode(Integer.parseInt(mode.substring(1)));
		music = new Music(game, handler, timer, this);
		music.openAudioFile();
		if (Integer.parseInt(mode.substring(1)) == 3) {
			spawnerNote.setMusic(music);
			// change this later too
			HUDNote.setHP(hp);
			Note.setOD(od);
		}
		if (Integer.parseInt(mode.substring(1)) == 0) {
			spawnerCircle.setMusic(music);
			// change this later to spawnerCircle
			HUDCircle.setHP(hp);
			Circle.setOD(od);
			Circle.setAR(ar);
			Circle.setCS(cs);
		}
		restart();
		if (Integer.parseInt(mode.substring(1)) == 3) {
			spawnerNote.restartMusic();
		} else if (Integer.parseInt(mode.substring(1)) == 0) {
			spawnerCircle.restartMusic();
		}
	}

	public void openBackground() {
		File f = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString() + "/"
				+ (beatmap.substring(0, beatmap.length() - 4)) + "/" + backgrounds.get(beatmap));
		try {
			background = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		double ratio = (double) background.getWidth() / (double) background.getHeight();
		background = scaleImage(background, (int) (((double) src.Game.HEIGHT * src.Game.HRATIO) * ratio),
				(int) ((double) src.Game.HEIGHT * src.Game.HRATIO));
		int x = Math.abs(((int) ((double) src.Game.WIDTH * src.Game.WRATIO)) - background.getWidth());
		background = cropImage(background, x / 2, 0, (int) ((double) src.Game.WIDTH * src.Game.WRATIO),
				(int) ((double) src.Game.HEIGHT * src.Game.HRATIO));
	}

	private BufferedImage cropImage(BufferedImage bi, int x, int y, int w, int h) {
		BufferedImage img = bi.getSubimage(x, y, w, h);
		return img;
	}

	public BufferedImage scaleImage(BufferedImage src, int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		int x, y;
		int ww = src.getWidth();
		int hh = src.getHeight();
		int[] ys = new int[h];
		for (y = 0; y < h; y++)
			ys[y] = y * hh / h;
		for (x = 0; x < w; x++) {
			int newX = x * ww / w;
			for (y = 0; y < h; y++) {
				int col = src.getRGB(newX, ys[y]);
				img.setRGB(x, y, col);
			}
		}
		return img;
	}

	public void setBeatmap(int value) {
		if (beatmap.equals(beatmaps[0]) && value == 0) {
			return;
		}
		if (beatmap.equals(beatmaps[beatmaps.length - 1]) && value == 1) {
			return;
		}
		if (!beatmap.equals(" ")) {
			if (value == 1) {
				for (int i = 0; i < beatmaps.length; i++) {
					if (beatmap.equals(beatmaps[i])) {
						if (i - mwheelValue == 21) {
							setMWheelValue(0);
						}
						transition = true;
						beatmap = beatmaps[i + 1];
						openBackground();
						try {
							reader = new Reader(beatmap, spawnerNote, spawnerCircle);
							mode = reader.getBeatmapInfo(beatmap)[0];
							title = reader.getBeatmapInfo(beatmap)[1];
							artist = reader.getBeatmapInfo(beatmap)[2];
							version = reader.getBeatmapInfo(beatmap)[3];
							hp = reader.getBeatmapInfo(beatmap)[4];
							cs = reader.getBeatmapInfo(beatmap)[5];
							od = reader.getBeatmapInfo(beatmap)[6];
							ar = reader.getBeatmapInfo(beatmap)[7];
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						break;
					}
				}
			} else {
				for (int i = 0; i < beatmaps.length; i++) {
					if (beatmap.equals(beatmaps[i])) {
						if (i - mwheelValue == 0) {
							setMWheelValue(1);
						}
						transition = true;
						beatmap = beatmaps[i - 1];
						openBackground();
						try {
							reader = new Reader(beatmap, spawnerNote, spawnerCircle);
							mode = reader.getBeatmapInfo(beatmap)[0];
							title = reader.getBeatmapInfo(beatmap)[1];
							artist = reader.getBeatmapInfo(beatmap)[2];
							version = reader.getBeatmapInfo(beatmap)[3];
							hp = reader.getBeatmapInfo(beatmap)[4];
							cs = reader.getBeatmapInfo(beatmap)[5];
							od = reader.getBeatmapInfo(beatmap)[6];
							ar = reader.getBeatmapInfo(beatmap)[7];
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		} else {
			transition = true;
			beatmap = beatmaps[0];
			openBackground();
			try {
				reader = new Reader(beatmap, spawnerNote, spawnerCircle);
				mode = reader.getBeatmapInfo(beatmap)[0];
				title = reader.getBeatmapInfo(beatmap)[1];
				artist = reader.getBeatmapInfo(beatmap)[2];
				version = reader.getBeatmapInfo(beatmap)[3];
				hp = reader.getBeatmapInfo(beatmap)[4];
				cs = reader.getBeatmapInfo(beatmap)[5];
				od = reader.getBeatmapInfo(beatmap)[6];
				ar = reader.getBeatmapInfo(beatmap)[7];
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		for (int i = 0; i < beatmaps.length; i++) {
			if (mouseOver(mx, my, (int) (270.0 * src.Game.WRATIO),
					(int) (((((15.0 * src.Game.HRATIO)) + ((double) i * 20.0 * src.Game.HRATIO)))
							- ((double) mwheelValue * src.Game.HRATIO * 20.0)),
					(int) (350.0 * src.Game.WRATIO), (int) (20.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.SongSelect) {
				transition = true;
				beatmap = beatmaps[i];
				openBackground();
				try {
					reader = new Reader(beatmap, spawnerNote, spawnerCircle);
					mode = reader.getBeatmapInfo(beatmap)[0];
					title = reader.getBeatmapInfo(beatmap)[1];
					artist = reader.getBeatmapInfo(beatmap)[2];
					version = reader.getBeatmapInfo(beatmap)[3];
					hp = reader.getBeatmapInfo(beatmap)[4];
					cs = reader.getBeatmapInfo(beatmap)[5];
					od = reader.getBeatmapInfo(beatmap)[6];
					ar = reader.getBeatmapInfo(beatmap)[7];
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (mouseOver(mx, my, (int) (193.0 * src.Game.WRATIO), (int) (410.0 * src.Game.HRATIO), (int) (60.0 * src.Game.WRATIO),
				(int) (30.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.SongSelect) {
			if (beatmap != null) {
				playBeatmap();
			}
		}
		if (mouseOver(mx, my, (int) (20.0 * src.Game.WRATIO), (int) (410.0 * src.Game.HRATIO), (int) (65.0 * src.Game.WRATIO),
				(int) (30.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.SongSelect) {
			game.gameState = src.Game.STATE.Menu;
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

	public void mouseWheelMoved(MouseWheelEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (mouseOver(mx, my, (int) (270.0 * src.Game.WRATIO), (int) (10.0 * src.Game.HRATIO), (int) (350.0 * src.Game.WRATIO),
				(int) (430.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.SongSelect) {
			if (e.getWheelRotation() < 0) {
				mwheelValue -= 1;
				mwheelValue = src.Game.clamp(mwheelValue, 0, src.Game.clamp(mwheelValue, 0, beatmaps.length - 22));
			} else {
				mwheelValue += 1;
				mwheelValue = src.Game.clamp(mwheelValue, 0, src.Game.clamp(mwheelValue, 0, beatmaps.length - 22));
			}
		}
	}

	public void tick() {
	}

	public void render(Graphics g) {
		if (transition) {
			if (upCount) {
				if (a < 399) {
					a++;
				} else {
					upCount = false;
				}
			} else {
				if (a > 201) {
					a--;
				} else if (a == 201) {
					transition = false;
					a = 200;
					upCount = true;
				}
			}
			g.drawImage(background, 0, 0, observer);
			double p = 1.0 - (a / 400.0);
			int b = (int) (256 - 256 * p);
			g.setColor(new Color(0, 0, 0, b));
			g.fillRect(0, 0, src.Game.RWIDTH, src.Game.RHEIGHT);
		} else {
			g.drawImage(background, 0, 0, observer);
			float pp = .5f;
			int bb = (int) (256 - 256 * pp);
			g.setColor(new Color(0, 0, 0, bb));
			g.fillRect(0, 0, src.Game.RWIDTH, src.Game.RHEIGHT);
		}

		Font fnt = new Font("courier", 1, (int) (30.0 * src.Game.HRATIO));
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString("Song Select", (int) (35.0 * src.Game.WRATIO), (int) (40.0 * src.Game.HRATIO));

		Font fnt2 = new Font("courier", 1, (int) (20.0 * src.Game.HRATIO));
		g.setFont(fnt2);
		g.setColor(Color.white);
		g.drawString("back", (int) (30.0 * src.Game.WRATIO), (int) (430.0 * src.Game.HRATIO));
		g.drawString("play", (int) (200.0 * src.Game.WRATIO), (int) (430.0 * src.Game.HRATIO));

		Font fnt3 = new Font("courier", 1, (int) (15.0 * src.Game.HRATIO));
		g.setFont(fnt3);
		g.drawString("Total Beatmaps: " + beatmaps.length, (int) (10.0 * src.Game.WRATIO), (int) (70.0 * src.Game.HRATIO));
		if (mode.equals(" 0")) {
			g.drawString("Mode: standard", (int) (10.0 * src.Game.WRATIO), (int) (100.0 * src.Game.HRATIO));
		} else if (mode.equals(" 3")) {
			g.drawString("Mode: mania", (int) (10.0 * src.Game.WRATIO), (int) (100.0 * src.Game.HRATIO));
		} else {
			g.drawString("Mode: ", (int) (10.0 * src.Game.WRATIO), (int) (100.0 * src.Game.HRATIO));
		}
		try {
			g.drawString("Title: " + title.substring(0, 20), (int) (10.0 * src.Game.WRATIO), (int) (130.0 * src.Game.HRATIO));
		} catch (Exception e) {
			g.drawString("Title: " + title, (int) (10.0 * src.Game.WRATIO), (int) (130.0 * src.Game.HRATIO));
		}
		try {
			g.drawString("Artist: " + artist.substring(0, 20), (int) (10.0 * src.Game.WRATIO), (int) (160.0 * src.Game.HRATIO));
		} catch (Exception e) {
			g.drawString("Artist: " + artist, (int) (10.0 * src.Game.WRATIO), (int) (160.0 * src.Game.HRATIO));
		}
		try {
			g.drawString("Diff: " + version.substring(0, 20), (int) (10.0 * src.Game.WRATIO), (int) (190.0 * src.Game.HRATIO));
		} catch (Exception e) {
			g.drawString("Diff: " + version, (int) (10.0 * src.Game.WRATIO), (int) (190.0 * src.Game.HRATIO));

		}
		g.drawString("HP: " + hp, (int) (10.0 * src.Game.WRATIO), (int) (220.0 * src.Game.HRATIO));
		g.drawString("CS: " + cs, (int) (10.0 * src.Game.WRATIO), (int) (250.0 * src.Game.HRATIO));
		g.drawString("OD: " + od, (int) (10.0 * src.Game.WRATIO), (int) (280.0 * src.Game.HRATIO));
		g.drawString("AR: " + ar, (int) (10.0 * src.Game.WRATIO), (int) (310.0 * src.Game.HRATIO));
		for (int i = 0; i < beatmaps.length; i++) {
			if (beatmaps[i].equals(beatmap)
					&& ((int) ((15.0 * src.Game.HRATIO) + (((double) i) * (20.0 * src.Game.HRATIO))
							- (((double) mwheelValue) * (20.0 * src.Game.HRATIO)))) > (14.0 * src.Game.HRATIO)
					&& ((int) ((15.0 * src.Game.HRATIO) + (((double) i) * (20.0 * src.Game.HRATIO))
							- (((double) mwheelValue) * (20.0 * src.Game.HRATIO)))) < (451.0 * src.Game.HRATIO)) {
				g.setColor(Color.lightGray);
				g.fillRect((int) (270.0 * src.Game.WRATIO),
						(int) ((15.0 * src.Game.HRATIO) + (((double) i) * (20.0 * src.Game.HRATIO))
								- (((double) mwheelValue) * (20.0 * src.Game.HRATIO))),
						(int) (350.0 * src.Game.WRATIO), (int) (20.0 * src.Game.HRATIO));
			}
			g.setColor(Color.white);
			if (i >= mwheelValue && i <= mwheelValue + 21) {
				try {
					g.drawString((beatmaps[i].substring(0, beatmaps[i].length() - 4)).substring(0, 37),
							(int) (280.0 * src.Game.WRATIO), (int) (30.0 * src.Game.HRATIO) + (i * (int) (20.0 * src.Game.HRATIO))
									- (int) ((double) mwheelValue * 20.0 * src.Game.HRATIO));
				} catch (Exception e) {
					g.drawString((beatmaps[i].substring(0, beatmaps[i].length() - 4)), (int) (280.0 * src.Game.WRATIO),
							(int) (30.0 * src.Game.HRATIO) + (i * (int) (20.0 * src.Game.HRATIO))
									- (int) ((double) mwheelValue * 20.0 * src.Game.HRATIO));
				}
			}
		}
		g.setColor(Color.white);
		g.drawRect((int) (270.0 * src.Game.WRATIO), (int) (10.0 * src.Game.HRATIO), (int) (350.0 * src.Game.WRATIO),
				(int) (450.0 * src.Game.HRATIO));
	}

	public void setMWheelValue(int value) {
		if (value == 1) {
			mwheelValue--;
			mwheelValue = src.Game.clamp(mwheelValue, 0,
					src.Game.clamp(mwheelValue, 0, beatmaps.length - (int) (21.0 * src.Game.HRATIO)));
		} else {
			mwheelValue++;
			mwheelValue = src.Game.clamp(mwheelValue, 0,
					src.Game.clamp(mwheelValue, 0, beatmaps.length - (int) (21.0 * src.Game.HRATIO)));
		}
	}
}

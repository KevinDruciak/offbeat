package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static src.Game.STATE.Game;

public class Menu extends MouseAdapter {

	private Game game;

	public Menu(Game game) {
		this.game = game;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (mouseOver(mx, my, (int) (213.0 * src.Game.WRATIO), (int) (150.0 * src.Game.HRATIO), (int) (213.0 * src.Game.WRATIO),
				(int) (60.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Menu) {
			game.gameState = src.Game.STATE.SongSelect;
		}
		if (mouseOver(mx, my, (int) (213.0 * src.Game.WRATIO), (int) (250.0 * src.Game.HRATIO), (int) (213.0 * src.Game.WRATIO),
				(int) (60.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Menu) {
			game.gameState = src.Game.STATE.Help;
		}
		if (mouseOver(mx, my, (int) (213.0 * src.Game.WRATIO), (int) (350.0 * src.Game.HRATIO), (int) (213.0 * src.Game.WRATIO),
				(int) (60.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Menu) {
			System.exit(1);
		}
		if (mouseOver(mx, my, (int) (290.0 * src.Game.WRATIO), (int) (413.0 * src.Game.HRATIO), (int) (60.0 * src.Game.WRATIO),
				(int) (22.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Help) {
			game.gameState = src.Game.STATE.Menu;
		}
		if (mouseOver(mx, my, (int) (470.0 * src.Game.WRATIO), (int) (390.0 * src.Game.HRATIO), (int) (110.0 * src.Game.WRATIO),
				(int) (30.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Menu) {
			game.gameState = src.Game.STATE.Settings;
		}
		if (mouseOver(mx, my, (int) (290.0 * src.Game.WRATIO), (int) (413.0 * src.Game.HRATIO), (int) (60.0 * src.Game.WRATIO),
				(int) (22.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Settings) {
			game.gameState = src.Game.STATE.Menu;
		}
		if (mouseOver(mx, my, (int) (25.0 * src.Game.WRATIO), (int) (163.0 * src.Game.HRATIO), (int) (140.0 * src.Game.WRATIO),
				(int) (20.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Settings) {
			src.Game.setRatios(1080, 1440);
			Window.setResolution();
		} else if (mouseOver(mx, my, (int) (25.0 * src.Game.WRATIO), (int) (183.0 * src.Game.HRATIO),
				(int) (140.0 * src.Game.WRATIO), (int) (20.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Settings) {
			src.Game.setRatios(960, 1280);
			Window.setResolution();
		} else if (mouseOver(mx, my, (int) (25.0 * src.Game.WRATIO), (int) (203.0 * src.Game.HRATIO),
				(int) (140.0 * src.Game.WRATIO), (int) (20.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Settings) {
			src.Game.setRatios(768, 1024);
			Window.setResolution();
		} else if (mouseOver(mx, my, (int) (25.0 * src.Game.WRATIO), (int) (223.0 * src.Game.HRATIO),
				(int) (140.0 * src.Game.WRATIO), (int) (20.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Settings) {
			src.Game.setRatios(720, 960);
			Window.setResolution();
		} else if (mouseOver(mx, my, (int) (25.0 * src.Game.WRATIO), (int) (243.0 * src.Game.HRATIO),
				(int) (140.0 * src.Game.WRATIO), (int) (20.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Settings) {
			src.Game.setRatios(600, 800);
			Window.setResolution();
		} else if (mouseOver(mx, my, (int) (25.0 * src.Game.WRATIO), (int) (263.0 * src.Game.HRATIO),
				(int) (140.0 * src.Game.WRATIO), (int) (20.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Settings) {
			src.Game.setRatios(480, 640);
			Window.setResolution();
		} else if (mouseOver(mx, my, (int) (25.0 * src.Game.WRATIO), (int) (283.0 * src.Game.HRATIO),
				(int) (140.0 * src.Game.WRATIO), (int) (20.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Settings) {
			src.Game.setRatios(540, 960);
			Window.setResolution();
		} else if (mouseOver(mx, my, (int) (175.0 * src.Game.WRATIO), (int) (310.0 * src.Game.HRATIO),
				(int) (20.0 * src.Game.WRATIO), (int) (20.0 * src.Game.HRATIO)) && game.gameState == src.Game.STATE.Settings) {
			src.Game.fullscreen = !src.Game.fullscreen;
			Window.setResolution();
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

		if (game.gameState == src.Game.STATE.Menu) {
			Font fnt = new Font("courier", 1, (int) (50.0 * src.Game.HRATIO));
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("offbeat", (int) (215.0 * src.Game.WRATIO), (int) (100.0 * src.Game.HRATIO));
			g.setColor(Color.darkGray);
			g.fillRect((int) (213.0 * src.Game.WRATIO), (int) (150.0 * src.Game.HRATIO), (int) (213.0 * src.Game.WRATIO),
					(int) (60.0 * src.Game.HRATIO));
			g.fillRect((int) (213.0 * src.Game.WRATIO), (int) (250.0 * src.Game.HRATIO), (int) (213.0 * src.Game.WRATIO),
					(int) (60.0 * src.Game.HRATIO));
			g.fillRect((int) (213.0 * src.Game.WRATIO), (int) (350.0 * src.Game.HRATIO), (int) (213.0 * src.Game.WRATIO),
					(int) (60.0 * src.Game.HRATIO));
			g.setColor(Color.white);
			g.drawRect((int) (213.0 * src.Game.WRATIO), (int) (150.0 * src.Game.HRATIO), (int) (213.0 * src.Game.WRATIO),
					(int) (60.0 * src.Game.HRATIO));
			g.drawRect((int) (213.0 * src.Game.WRATIO), (int) (250.0 * src.Game.HRATIO), (int) (213.0 * src.Game.WRATIO),
					(int) (60.0 * src.Game.HRATIO));
			g.drawRect((int) (213.0 * src.Game.WRATIO), (int) (350.0 * src.Game.HRATIO), (int) (213.0 * src.Game.WRATIO),
					(int) (60.0 * src.Game.HRATIO));

			Font fnt2 = new Font("courier", 1, (int) (25.0 * src.Game.HRATIO));
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("play", (int) (290.0 * src.Game.WRATIO), (int) (187.0 * src.Game.HRATIO));
			g.drawString("help", (int) (290.0 * src.Game.WRATIO), (int) (287.0 * src.Game.HRATIO));
			g.drawString("quit", (int) (290.0 * src.Game.WRATIO), (int) (387.0 * src.Game.HRATIO));

			Font fnt3 = new Font("courier", 1, (int) (20.0 * src.Game.HRATIO));
			g.setFont(fnt3);
			g.setColor(Color.white);
			g.drawString("settings", (int) (480.0 * src.Game.WRATIO), (int) (410.0 * src.Game.HRATIO));
		} else if (game.gameState == src.Game.STATE.Help) {
			Font fnt3 = new Font("courier", 1, (int) (50.0 * src.Game.HRATIO));
			g.setFont(fnt3);
			g.setColor(Color.white);
			g.drawString("help", (int) (260.0 * src.Game.WRATIO), (int) (100.0 * src.Game.HRATIO));

			g.setColor(Color.darkGray);
			Font fnt4 = new Font("courier", 1, (int) (20.0 * src.Game.HRATIO));
			g.setFont(fnt4);
			g.drawString("Use D, F, J, and K keys to hit notes", (int) (100.0 * src.Game.WRATIO),
					(int) (150.0 * src.Game.HRATIO));
			g.drawString("when they reach the white marker", (int) (125.0 * src.Game.WRATIO), (int) (180.0 * src.Game.HRATIO));
			g.setColor(Color.white);
			g.drawString("back", (int) (296.0 * src.Game.WRATIO), (int) (430.0 * src.Game.HRATIO));
			g.setColor(Color.blue);
			g.fillRect((int) (265.0 * src.Game.WRATIO), (int) (290.0 * src.Game.HRATIO), (int) (50.0 * src.Game.WRATIO),
					(int) (16.0 * src.Game.HRATIO));
			g.fillRect((int) (385.0 * src.Game.WRATIO), (int) (350.0 * src.Game.HRATIO), (int) (50.0 * src.Game.WRATIO),
					(int) (16.0 * src.Game.HRATIO));
			g.setColor(Color.white);
			g.fillRect((int) (205.0 * src.Game.WRATIO), (int) (380.0 * src.Game.HRATIO), (int) (50.0 * src.Game.WRATIO),
					(int) (16.0 * src.Game.HRATIO));
			g.fillRect((int) (265.0 * src.Game.WRATIO), (int) (380.0 * src.Game.HRATIO), (int) (50.0 * src.Game.WRATIO),
					(int) (16.0 * src.Game.HRATIO));
			g.fillRect((int) (325.0 * src.Game.WRATIO), (int) (380.0 * src.Game.HRATIO), (int) (50.0 * src.Game.WRATIO),
					(int) (16.0 * src.Game.HRATIO));
			g.fillRect((int) (385.0 * src.Game.WRATIO), (int) (380.0 * src.Game.HRATIO), (int) (50.0 * src.Game.WRATIO),
					(int) (16.0 * src.Game.HRATIO));
		} else if (game.gameState == src.Game.STATE.Settings) {
			Font fnt3 = new Font("courier", 1, (int) (50.0 * src.Game.WRATIO));
			g.setFont(fnt3);
			g.setColor(Color.white);
			g.drawString("settings", (int) (200.0 * src.Game.WRATIO), (int) (100.0 * src.Game.HRATIO));

			Font fnt4 = new Font("courier", 1, (int) (20.0 * src.Game.HRATIO));
			g.setFont(fnt4);
			g.setColor(Color.white);
			g.drawString("back", (int) (296.0 * src.Game.WRATIO), (int) (430.0 * src.Game.HRATIO));

			g.drawString("Resolution", (int) (30.0 * src.Game.WRATIO), (int) (150.0 * src.Game.HRATIO));
			g.drawString("Fullscreen: ", (int) (30.0 * src.Game.WRATIO), (int) (325.0 * src.Game.HRATIO));
			g.drawRect((int) (175.0 * src.Game.WRATIO), (int) (310.0 * src.Game.HRATIO), (int) (20.0 * src.Game.WRATIO),
					(int) (20.0 * src.Game.HRATIO));
			if (src.Game.fullscreen) {
				g.setColor(Color.gray);
				g.fillRect((int) (175.0 * src.Game.WRATIO), (int) (310.0 * src.Game.HRATIO), (int) (20.0 * src.Game.WRATIO),
						(int) (20.0 * src.Game.HRATIO));
			} 
			g.setColor(Color.gray);
			g.drawString("1440 * 1080", (int) (30.0 * src.Game.WRATIO), (int) (180.0 * src.Game.HRATIO));
			g.drawString("1280 * 960", (int) (30.0 * src.Game.WRATIO), (int) (200.0 * src.Game.HRATIO));
			g.drawString("1024 * 768", (int) (30.0 * src.Game.WRATIO), (int) (220.0 * src.Game.HRATIO));
			g.drawString("960  * 720", (int) (30.0 * src.Game.WRATIO), (int) (240.0 * src.Game.HRATIO));
			g.drawString("800  * 600", (int) (30.0 * src.Game.WRATIO), (int) (260.0 * src.Game.HRATIO));
			g.drawString("640  * 480", (int) (30.0 * src.Game.WRATIO), (int) (280.0 * src.Game.HRATIO));
			g.drawString("400  * 300", (int) (30.0 * src.Game.WRATIO), (int) (300.0 * src.Game.HRATIO));

		}
	}

	public void tick() {

	}
}

package src;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = 4374920717406783615L;
	public static JFrame frame;
 
	public Window(int width, int height, String title, Game game) {
		frame = new JFrame(title);
		frame.setPreferredSize(
				new Dimension((int) ((double) Game.WIDTH * Game.WRATIO), (int) ((double) Game.HEIGHT * Game.HRATIO)));
		frame.setMaximumSize(
				new Dimension((int) ((double) Game.WIDTH * Game.WRATIO), (int) ((double) Game.HEIGHT * Game.HRATIO)));
		frame.setMinimumSize(
				new Dimension((int) ((double) Game.WIDTH * Game.WRATIO), (int) ((double) Game.HEIGHT * Game.HRATIO)));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}

	public static void setResolution() {
		if (Game.fullscreen) {
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
		} else if (!Game.fullscreen) {
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
		}
		frame.setPreferredSize(
				new Dimension((int) ((double) Game.WIDTH * Game.WRATIO), (int) ((double) Game.HEIGHT * Game.HRATIO)));
		frame.setMaximumSize(
				new Dimension((int) ((double) Game.WIDTH * Game.WRATIO), (int) ((double) Game.HEIGHT * Game.HRATIO)));
		frame.setMinimumSize(
				new Dimension((int) ((double) Game.WIDTH * Game.WRATIO), (int) ((double) Game.HEIGHT * Game.HRATIO)));
		frame.setBounds(0, 0, (int) ((double) Game.WIDTH * Game.WRATIO), (int) ((double) Game.HEIGHT * Game.HRATIO));
		frame.setLocationRelativeTo(null);
	}

}

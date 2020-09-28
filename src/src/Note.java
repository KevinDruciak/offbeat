package src;

import java.awt.Color;
import java.awt.Graphics;

public class Note extends GameObject {

	private Handler handler;
	private static HUDNote HUDNote;
	private static double ODMultiplier = 1.0;

	public Note(int x, int y, ID id, Handler handler, HUDNote hudisplay) {
		super(x, y, id);
		velY = (int) (2.0 * Game.HRATIO);
		this.handler = handler;
		HUDNote = hudisplay;
	}

	public void tick() {
		y += velY;
		if (y > (int) ((double) Game.HEIGHT * Game.HRATIO)) {
			handler.removeObject(this);
			HUDNote.setValues(5);
			//System.out.println("miss");
		}
	}

	public void render(Graphics g) {
		if (id == ID.Note1) {
			g.setColor(Color.blue);
		}
		if (id == ID.Note2) {
			g.setColor(Color.blue);
		}
		if (id == ID.Note3) {
			g.setColor(Color.blue);
		}
		if (id == ID.Note4) {
			g.setColor(Color.blue);
		}
		g.fillRect(x, y, (int) (50.0 * Game.WRATIO), (int) (16.0 * Game.HRATIO));
	}

	public boolean checkList(GameObject note) {
		if (handler.object.contains(note)) {
			return false;
		} else {
			return true;
		}
	} 

	public static void setOD(String od) {
		Double OD = Double.parseDouble(od);
		ODMultiplier = OD;
	}
	
	public static boolean checkPress(GameObject note) {
		if (note.getY() < ((405.0 - ODMultiplier) * Game.HRATIO) && note.getY() > ((355.0 + ODMultiplier) * Game.HRATIO)) {
			HUDNote.setValues(1);
			//HUDNote.addHit(note.getY());
			return true;
		} else if (note.getY() < ((415.0 - ODMultiplier) * Game.HRATIO) && note.getY() > ((345.0 + ODMultiplier) * Game.HRATIO)) {
			HUDNote.setValues(2);
			return true;
		} else if (note.getY() < ((425.0 - ODMultiplier) * Game.HRATIO) && note.getY() > ((335.0 + ODMultiplier) * Game.HRATIO)) {
			HUDNote.setValues(3);
			return true;
		} else if (note.getY() < ((435.0 - ODMultiplier) * Game.HRATIO) && note.getY() > ((325.0 + ODMultiplier) * Game.HRATIO)) {
			HUDNote.setValues(4);
			return true;
		} else if (note.getY() < ((445.0 - ODMultiplier) * Game.HRATIO) && note.getY() > ((315.0 + ODMultiplier) * Game.HRATIO)) {
			HUDNote.setValues(5);
			return true;
		} else if (note.getY() >= ((445.0 - ODMultiplier) * Game.HRATIO) || note.getY() <= ((315.0 + ODMultiplier) * Game.HRATIO)) {
			HUDNote.setValues(6);
			return false;
		}
		return false;
	}
	
	protected double getACSize() {
		// not used
		return 0;
	}
	
//	public static boolean checkPress(GameObject note) {
//		if (note.getY() < (int) (400.0 * Game.HRATIO) && note.getY() > (int) (360.0 * Game.HRATIO)) {
//			HUDNote.setValues(1);
//			return true;
//		} else if (note.getY() < (int) (410.0 * Game.HRATIO) && note.getY() > (int) (350.0 * Game.HRATIO)) {
//			HUDNote.setValues(2);
//			return true;
//		} else if (note.getY() < (int) (420.0 * Game.HRATIO) && note.getY() > (int) (340.0 * Game.HRATIO)) {
//			HUDNote.setValues(3);
//			return true;
//		} else if (note.getY() < (int) (430.0 * Game.HRATIO) && note.getY() > (int) (330.0 * Game.HRATIO)) {
//			HUDNote.setValues(4);
//			return true;
//		} else if (note.getY() < (int) (440.0 * Game.HRATIO) && note.getY() > (int) (320.0 * Game.HRATIO)) {
//			HUDNote.setValues(5);
//			return true;
//		} else if (note.getY() >= (int) (440.0 * Game.HRATIO) || note.getY() <= (int) (320.0 * Game.HRATIO)) {
//			HUDNote.setValues(6);
//			return false;
//		}
//		return false;
//	}
}

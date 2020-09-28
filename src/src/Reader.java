package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Reader {
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private int notes[][];
	private File currentMap;
	private String beatmap;
	private SpawnerNote spawnerNote;
	private SpawnerCircle spawnerCircle;
	private boolean b = true;
	private boolean a = true;
	private int index = 0, counter = 0;
	private String[] mapInfo = new String[8];

	public Reader(String beatmap, SpawnerNote spawnerNote, SpawnerCircle spawnerCircle) throws FileNotFoundException {
		this.beatmap = beatmap;
		this.spawnerNote = spawnerNote;
		this.spawnerCircle = spawnerCircle;
	}

	public int[][] getNotes() {
		return this.notes;
	}

	public void setNotes(int[][] notes) {
		this.notes = notes;
	}

	public boolean startReading() throws FileNotFoundException {
		currentMap = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString() + "/"
				+ (beatmap.substring(0, beatmap.length() - 4)) + "/" + beatmap);
		parseBeatmap(beatmap);
		if (mapInfo[0].equals(" 3")) {
			//System.out.println("called1");
			spawnerNote.spawnNotes(notes);
		} 
		if (mapInfo[0].equals(" 0")) {
			//System.out.println("called2");
			spawnerCircle.spawnNotes(notes);
		}
		return true;
	}

	public int getSize() throws FileNotFoundException {
		File f = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString() + "/"
				+ (beatmap.substring(0, beatmap.length() - 4)) + "/" + beatmap);
		try (Scanner scnr = new Scanner(f, ENCODING.name())) {
			while (a) {
				if (scnr.nextLine().equals("[HitObjects]")) {
					a = false;
				}
			}
			while (!a && scnr.hasNextLine()) {
				if (scnr.nextLine() != null) {
					counter++;
				}
			}
		}
		return counter;
	}

	public void parseBeatmap(String beatmap) throws FileNotFoundException {
		currentMap = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString() + "/"
				+ (beatmap.substring(0, beatmap.length() - 4)) + "/" + beatmap);
		try (Scanner scnr = new Scanner(currentMap, ENCODING.name())) {
			while (scnr.hasNextLine()) {
				String temp = scnr.nextLine();
				if (temp.equals("[HitObjects]")) {
					b = false;
					temp = scnr.nextLine();
				}
				//System.out.println("called4");
				processLine(temp);
			}
		}
		//System.out.println(Arrays.deepToString(notes));
		//System.out.println(notes.length);
		//spawnerNote.spawnNotes(notes);
		//System.out.println(mapInfo[0]);
		
	}
	
	public String[] getBeatmapInfo(String beatmap) throws FileNotFoundException {
		currentMap = new File(Paths.get("./src/beatmaps").toAbsolutePath().normalize().toString() + "/"
				+ (beatmap.substring(0, beatmap.length() - 4)) + "/" + beatmap);
		try (Scanner scnr = new Scanner(currentMap, ENCODING.name())) {
			while (scnr.hasNextLine()) {
				String temp = scnr.nextLine();
				processLine(temp);
			}
		}
		return mapInfo;
	}

	protected void processLine(String line) throws FileNotFoundException {
		try (Scanner scnr = new Scanner(line)) {
			if (b) {
				scnr.useDelimiter(":");
				while (scnr.hasNext()) {
					String temp = scnr.next();
					if (temp.equals("Mode")) {
						mapInfo[0] = scnr.next();
						if (mapInfo[0].equals(" 3")) {
							this.notes = new int[getSize() + 1][2];
						} else if (mapInfo[0].equals(" 0")) {
							this.notes = new int[getSize() + 1][3];
						}
					} else if (temp.equals("Title")) {
						mapInfo[1] = scnr.next();
					} else if (temp.equals("Artist")) {
						mapInfo[2] = scnr.next();
					} else if (temp.equals("Version")) {
						mapInfo[3] = scnr.next();
					} else if (temp.equals("HPDrainRate")) {
						mapInfo[4] = scnr.next();
					} else if (temp.equals("CircleSize")) {
						mapInfo[5] = scnr.next();
					} else if (temp.equals("OverallDifficulty")) {
						mapInfo[6] = scnr.next();
					} else if (temp.equals("ApproachRate")) {
						mapInfo[7] = scnr.next();
					}
				}
			}
			if (!b) {
				if (mapInfo[0].equals(" 3")) {
					scnr.useDelimiter(",");
					int temp = scnr.nextInt();
					notes[index][0] = temp;
					scnr.next();
					temp = scnr.nextInt();
					notes[index][1] = temp;
					while (scnr.hasNext()) {
						scnr.next();
					}
					index++;
				} else if (mapInfo[0].equals(" 0")) {
					scnr.useDelimiter(",");
					int temp = scnr.nextInt();
					notes[index][0] = temp;
					temp = scnr.nextInt();
					notes[index][1] = temp;
					temp = scnr.nextInt();
					notes[index][2] = temp;
					while(scnr.hasNext()) {
						scnr.next();
					}
					index++;
				}
			}
		}
	}
}
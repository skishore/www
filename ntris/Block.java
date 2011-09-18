package ntris_src;

import static ntris_src.Constants.*;
import static ntris_src.ntrisColor.*;
import java.awt.Color;

public class Block {
	public int x, y;
	public int angle;
	public int numSquares;
	public Point[] squares = new Point[MAXBLOCKSIZE];
	public Color color;
	public int shoveaways;
	public int localStickFrames;
	public int globalStickFrames;
	public int height;
	public boolean rotates;
	public int rowsDropped;
	
	public Block() {
		x = 0;
		y = 0;
		angle = 0;
		numSquares = 0;
		for (int i = 0; i < MAXBLOCKSIZE; i++) {
			squares[i] = new Point();
		}
		color = RED;
		shoveaways = 0;
		localStickFrames = MAXLOCALSTICKFRAMES;
		globalStickFrames = MAXGLOBALSTICKFRAMES;
		rotates = true;
		height = 0;
	}
}

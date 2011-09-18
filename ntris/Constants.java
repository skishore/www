package ntris_src;

public class Constants {
	public static final int MAXBLOCKSIZE = 10;
	public static final int NUMROWS = (24 + MAXBLOCKSIZE - 1);
	public static final int NUMCOLS = 12;

	// board states
	public static final int RESET = 0;
	public static final int PLAYING = 1;
	public static final int PAUSED = 2;
	public static final int GAMEOVER = 4;
	public static final int COUNTDOWN = 6;
	public static final int QUIT = -1;
	
	// the amount of time each "second" takes during a countdown
	public static final int SECOND = 54;
	public static final int NUMSECONDS = 3;

	// game modes
	public static final int SINGLEPLAYER = 0;
	public static final int MULTIPLAYER = 1;

	// event types
	public static final int PLACEBLOCK = 0;
	public static final int GETNEXTBLOCK = 1;
	public static final int QUEUEBLOCK = 2;
	public static final int SENDATTACK = 3;
	public static final int RECEIVEATTACK = 4;
	public static final int VICTORY = 5;
	public static final int RESETBOARD = 6;
	public static final int MAXEVENTS = 32;

	// illegal block flags - these record why a block is illegal
	// given in order of priority, so first a block is tested against
	// the edges of the board, then it is tested for overlap
	public static final int OK = 0;
	public static final int TOPEDGE = 1;
	public static final int RIGHTEDGE = 2;
	public static final int BOTTOMEDGE = 3;
	public static final int LEFTEDGE = 4;
	public static final int OVERLAP = 5;

	// this variable records how many frames go by before gravity is applied
	public static final int GRAVITY = 40;
	// the number of times a block can be shoved away from an obstacle
	public static final int MAXSHOVEAWAYS = 2;
	// the number of frames before a block sticks locally or globally
	public static final int MAXLOCALSTICKFRAMES = 24;
	public static final int MAXGLOBALSTICKFRAMES = 120;

	// constants holding movement direction data
	public static final int MOVEUP = 0;
	public static final int MOVEBACK = 1;
	public static final int MOVERIGHT = 2;
	public static final int MOVEDOWN = 3;
	public static final int MOVELEFT = 4;
	public static final int MOVEDROP = 5;
	public static final int MOVEHOLD = 6;
	public static final int ENTER = 7;
	public static final int PAUSE = 8;
	public static final int ESCAPE = 9;
	public static final int NUMKEYS = 10;
	
	// the number of blocks we preview
	public static final int PREVIEW = 5;
	// the number of frames used to animate the preview list
	public static final int PREVIEWANIMFRAMES = 3;
}

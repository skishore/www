package ntris_src;

import static ntris_src.Constants.*;
import static ntris_src.ntrisColor.*;
import static ntris_src.KeyRepeater.RepeatHandler;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ntrisApplet extends Applet implements Runnable, MouseListener, FocusListener {
	private static final long serialVersionUID = 5810;
    private static final boolean verbose = false;

	// display constants
	private static int SQUAREWIDTH;
	private static int BORDER;
	private static int SIDEBOARD;
	private int SCREENWIDTH;
	private int SCREENHEIGHT;

	// frameCount is a rhythm variable, incremented mod MAXFRAMECOUNT each frame
	private int frameCount;
	private static final int MAXFRAMECOUNT = 840;
	private static final int FRAMERATE = 60;
	private static final int TICKS_PER_SEC = 1000;
	private static final int FRAMEDELAY = (TICKS_PER_SEC/FRAMERATE);

	// the score at which we transitition between difficulty levels
	private static final int SCOREINTERVAL = 60;
	private static final double MINR = 0.1;
	private static final double MAXR = 0.9;
	private static final int HALFRSCORE = 480;

	private int difficultyLevels;
	private int[] numBlockTypes = new int[10];
	private int numSymbols;
	Block[] blockData = new Block[13000];

	private int gameState;
	private Board board;
	private PRG prg;

	private Image backBuffer;
	private Graphics bbGraphics;
	private KeyRepeater keyRepeater;

	public void init() {
		prg = new PRG(System.currentTimeMillis(), true);
		initWindow();
		try {
			openBlockData();
		} catch (IOException e) {
			System.err.println("Failure to open block data.");
		}
		initBoard();

		keyRepeater = new KeyRepeater(board, 120, 30);
        this.addKeyListener(keyRepeater);

        this.addMouseListener(this);
        this.addFocusListener(this);
		this.requestFocus();
	}

    public void mouseClicked(MouseEvent e) { }

    public void mousePressed(MouseEvent e) { 
        this.requestFocus();
    }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void focusGained(FocusEvent e) { }

    public void focusLost(FocusEvent e) { 
        if (board.getBoardState() == PLAYING) {
            board.keyPress(PAUSE);
            board.keyRelease(PAUSE);
            keyRepeater.releaseAll();
        }
    }

	public void start() {
		gameState = PLAYING;
        Thread th = new Thread(this);
        th.start();		
	}
	
	private void initWindow() {
		if (getParameter("size") != null) 
			SQUAREWIDTH = Integer.parseInt(getParameter("size"));
		if (SQUAREWIDTH <= 0) SQUAREWIDTH = 21;
		BORDER = SQUAREWIDTH;
		SIDEBOARD = 3*SQUAREWIDTH;
		
		SCREENWIDTH = SQUAREWIDTH * NUMCOLS + SIDEBOARD;
		SCREENHEIGHT = SQUAREWIDTH * (NUMROWS - MAXBLOCKSIZE + 1);

		setBackground(BLACK);
		setSize(new Dimension(SCREENWIDTH + 2*BORDER, SCREENHEIGHT + 2*BORDER));

		backBuffer = createImage(SCREENWIDTH + 2*BORDER, SCREENHEIGHT + 2*BORDER);
		bbGraphics = backBuffer.getGraphics();

        bbGraphics.setColor(GREEN);
        bbGraphics.drawRect(BORDER/2-1, BORDER/2-1, SCREENWIDTH+BORDER+2, SCREENHEIGHT+BORDER+2);
        bbGraphics.drawRect(BORDER/2-2, BORDER/2-2, SCREENWIDTH+BORDER+4, SCREENHEIGHT+BORDER+4);
	}

	public void openBlockData() throws IOException {
	    String line;
	    int x, y, difficulty;
	    BufferedReader myfile;
	    URL url;
	    URLConnection connection;
	    
        url = this.getClass().getResource("blockData.dat");
	    connection = url.openConnection();
	    myfile = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    	
	    // the file begins with the number of different types of blocks at each difficulty level
	    line = myfile.readLine();    
	    difficultyLevels = Integer.parseInt(line);
	    for (int i = 0; i < difficultyLevels; i++) {
	        line = myfile.readLine();
	        numBlockTypes[i] = Integer.parseInt(line);
	    }
	    difficulty = 0;
	    line = myfile.readLine();
	    numSymbols = Integer.parseInt(line);

	    // blank line before the block data
	    line = myfile.readLine();
	        
	        for (int i = 0; i < numBlockTypes[difficultyLevels-1] + numSymbols; i++) {
	            blockData[i] = new Block();
	            // the first two lines of this block's data record the starting position
	            line = myfile.readLine();
	            // first, how far off center the x-coordinate is
	            blockData[i].x = NUMCOLS/2 + Integer.parseInt(line);
	            // then the y-coordinate
	            line = myfile.readLine();
	            blockData[i].y = Integer.parseInt(line);
	            // next, the number of squares in the block
	            line = myfile.readLine();
	            blockData[i].numSquares = Integer.parseInt(line);
	            // read each square's local coordinates from memory
	            for (int j = 0; j < blockData[i].numSquares; j++) {
	            	line = myfile.readLine();
	                x = Integer.parseInt(line);
	                line = myfile.readLine();
	                y = Integer.parseInt(line);
	                // read x and y, record in blockData
	                blockData[i].squares[j] = new Point(x, y);
	            }
	            // lastly, read the color
	            line = myfile.readLine();
	            Color color = colorCode(Integer.parseInt(line));
	            blockData[i].color = color;
	            blockData[i].color = mixedColor(BLACK, blockData[i].color, 0.2);

	            // record the block's height in blockData
	            blockData[i].height = calculateBlockHeight(blockData[i]);

	            // blank line after each block's data
	            line = myfile.readLine();

	            if ((difficulty < difficultyLevels) && (i > numBlockTypes[difficulty])) difficulty++;
	            //if (i >= numBlockTypes[difficultyLevels-1]) 
	            //    blockData[i].color = difficultyColor(YELLOW, 
	            //    		(1.0f*(i-numBlockTypes[difficultyLevels-1]+1))/(difficultyLevels-1));
	        }
	        
	        // get data about any blocks that do not rotate
	        line = myfile.readLine();
	        x = Integer.parseInt(line);
	        // there are x blocks that do not rotate - read their indices now
	        for (int i = 0; i < x; i++) {
	        	line = myfile.readLine();
	            blockData[Integer.parseInt(line)].rotates = false;
	        }
	        myfile.close();
	}

	private int calculateBlockHeight(Block block) {
	    int highest = 0;
	    int lowest = 0;

	    for (int i = 0; i < block.numSquares; i++) {
	        if (block.squares[i].y < lowest)
	            lowest = block.squares[i].y;
	        if (block.squares[i].y > highest)
	            highest = block.squares[i].y;
	    }
	    return highest - lowest + 1;
	}
	
	private void initBoard() {
	    int numBlocks = numBlockTypes[difficultyLevels-1];

        board = new Board(BORDER, BORDER, SQUAREWIDTH, SIDEBOARD, numBlocks, blockData, SINGLEPLAYER);
        board.loadSprites(this, SCREENWIDTH, SCREENHEIGHT);
	    playTetrisGod(board);
	    board.draw(backBuffer.getGraphics());
	}
	
	public void run() {
	    long curTime, lastTime, lastSecond;
	    int numFrames;
	    
	    // numFrames stores the number of frames drawn this second
	    numFrames = 0;
	    curTime = System.currentTimeMillis();
	    lastTime = curTime;
	    lastSecond = curTime;
	    frameCount = 0;

	    while (gameState >= PLAYING) {
	        // check current time - if one frameDelay has passed draw the next frame 
		    curTime = System.currentTimeMillis();
		    if (curTime > lastTime + (long)FRAMEDELAY) {            
	            lastTime = curTime;

	            if (curTime > lastSecond + (long)TICKS_PER_SEC) {
                    if (verbose)
                        System.out.println("FPS = " + 1.0f*numFrames*TICKS_PER_SEC/(curTime - lastSecond));                                
	                lastSecond = curTime;
	                numFrames = 1;
	            } else {
	                numFrames++;
	            }
	            
	            keyRepeater.query(curTime);
	            frameCount = (frameCount + 1) % MAXFRAMECOUNT;
	            board.timeStep(frameCount);
	            playTetrisGod(board);
	        
	            repaint();
	            
	            long delay = FRAMEDELAY + curTime - System.currentTimeMillis();
	            if (delay > 0) {
		            try {
		            	Thread.sleep(FRAMEDELAY+curTime-System.currentTimeMillis());
		            }
		            catch (InterruptedException ex) { }
	            }
	            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	        }
	    }
	    System.exit(0);
	}

	void playTetrisGod(Board board) {    
	    int type = 0;
	    int numNeeded = board.numBlocksNeeded();
	    int score = board.getScore();

	    for (int i = 0; i < numNeeded; i++) {
            int level = difficultyLevel(score);
            type = (int)prg.generate()%numBlockTypes[level];
            if (type < 0) type += numBlockTypes[level];
	        board.queueBlock(type);
	    }
	}
	
	// takes in a score s and returns the current difficulty level
	int difficultyLevel(int s) {
	    double x, p, r;

	    if (difficultyLevels == 1)
	        return 0;
	    // get a random p uniformly from [0, 1]
	    p = 1.0*(prg.generate()%((int)Math.pow(2, 12)))/Math.pow(2,12);
	    if (p < 0) p++;
	    // calculate the current ratio r between the probability of different difficulties
	    x = 2.0*(s - HALFRSCORE)/HALFRSCORE;
	    r = (MAXR-MINR)*(x/Math.sqrt(1+x*x) + 1)/2 + MINR;
	    // run through difficulty levels
	    for (int i = 1; i < difficultyLevels; i++) {
	        x = 2.0*(s - (SCOREINTERVAL*i))/SCOREINTERVAL;
	        // compare p to a sigmoid which increases to 1 when score passes SCOREINTERVAL*i
	        if (p > Math.pow(r, i)*(x/Math.sqrt(1+x*x) + 1)/2)
	            // if p is still above this sigmoid, we are not yet at this difficulty level
	            return i-1;
	    }
	    return difficultyLevels - 1;
	}
	
	public void update(Graphics g) {
		board.draw(bbGraphics);
        g.drawImage(backBuffer, 0, 0, this);
	}
	
	public void destroy() {
		gameState = QUIT;
	}
}

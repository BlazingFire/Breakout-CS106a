import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;
import acm.util.*; 

public class Breakout extends GraphicsProgram {
	
	/*Private Constants*/
	private static final int NUM_BRICKS = 32;
	private static final int NUM_ROWBRICKS = 8;
	private static final int NUM_COLBRICKS = 4;  
	private static final int BRICK_WIDTH = 50;
	private static final int BRICK_HEIGHT = 20;

	private static final int PADDLE_WIDTH = 70;
	private static final int PADDLE_HEIGHT = 20;
	private static final int DIAM = 20;
	private static final int BOX_WIDTH = NUM_ROWBRICKS * BRICK_WIDTH;

	// These value are for BrickBox
	private static final int upperOffset = BRICK_HEIGHT * NUM_COLBRICKS;
	private static final int lowerOffset = upperOffset + 20;
	
	/*
	 * BOXHEIGHT is a bit random
	 * STARTX and STARTY are starting position of Box
	 */	
	private static final int STARTX = 20;
	private static final int STARTY = 30;
	private static final int BOX_HEIGHT = (2 * upperOffset) + lowerOffset + STARTY;

	public void init(){
	addMouseListeners();
}

	/* Setting up the game */
	public void run() {
		setupBox();
		setupBricks();
		setupEquipments();
	
		
		while(true){
			pause(10);
			ball.move(velX, velY);
			checkForCollisions();
		}	
	}



	/* Creates Bounding Box for the game*/
	public void setupBox() {

		verLeft = new GLine(STARTX, STARTY, STARTX, STARTY + BOX_HEIGHT);
		verRight = new GLine(STARTX + BOX_WIDTH, STARTY, STARTX + BOX_WIDTH, STARTY + BOX_HEIGHT);

		horUp = new GLine(STARTX , STARTY, STARTX + BOX_WIDTH, STARTY);
		horDown = new GLine(STARTX , STARTY + BOX_HEIGHT, STARTX + BOX_WIDTH, STARTY + BOX_HEIGHT);

		add(verLeft);
		add(verRight);
		add(horUp);
		add(horDown);

	}



	/* Creates all the bricks */
	public void setupBricks(){
		int Y = STARTY + upperOffset;
		RandomGenerator rgen = RandomGenerator.getInstance();		
	
		for(int i = 0; i < NUM_COLBRICKS; i++) {
			int X = STARTX;

			for(int j = 0; j < NUM_ROWBRICKS; j++) {
				bricks[i][j] = new GRect(X, Y, BRICK_WIDTH, BRICK_HEIGHT);
				bricks[i][j].setFilled(true);
				bricks[i][j].setColor(rgen.nextColor());
				add(bricks[i][j]);
				X += BRICK_WIDTH;
			}
			Y += BRICK_HEIGHT;
		}
	}



	/* Creates ball and paddle */
	public void setupEquipments() {
		paddle = new GRect( ( STARTX + (BOX_WIDTH - PADDLE_WIDTH) / 2), (STARTY + BOX_HEIGHT - PADDLE_HEIGHT), PADDLE_WIDTH, PADDLE_HEIGHT) ;
		paddle.setFilled(true);
		paddle.setColor(Color.RED);		

		ball = new GArc(DIAM, DIAM, 0, 360);
		ball.setFilled(true);
		ball.setColor(Color.BLUE);

		add(paddle);
		add(ball, (STARTX + (BOX_WIDTH - DIAM) / 2), (STARTY + BOX_HEIGHT - PADDLE_HEIGHT - DIAM));
	}



	/*
	 * Game Physics
    * Infinite friction on the surface of Base
	 * If Base is moving , its velocity will be added to the ball along X direction
	 */
	public void mouseMoved(MouseEvent e) {
			
			
			double X_ = paddle.getX();
			double cor = e.getX() - STARTX;
			if(cor>=0 && cor<=(BOX_WIDTH-PADDLE_WIDTH))
		/*	if(X_ < 0)
				pause(1);	
			else if(X_ > (BOX_WIDTH - PADDLE_WIDTH) )	
					pause(1);
			else */
				paddle.move(e.getX()-paddle.getX(), 0);
	
}			

	public void checkForCollisions(){

			

			double X = ball.getX() - STARTX;
			double Y = ball.getY()-STARTY;
			if(X >= (BOX_WIDTH - DIAM) || X <= 0)
				velX = -velX;
			else if(Y <= 0)
				velY = -velY;
			else if(Y > (BOX_HEIGHT-DIAM)){
				removeAll();
				add(new GLabel("GAME OVER!!!",200, 200) );
			}
			else if((Y+DIAM > paddle.getY() -STARTY) && (X>paddle.getX()-STARTX&&X<paddle.getX()+PADDLE_WIDTH-STARTX) )
				velY=-velY;
			else {
				for(int i = 0; i < NUM_COLBRICKS; i++) {
	
					for(int j = 0; j < NUM_ROWBRICKS; j++) {
						if(bricks[i][j]!= null){
						double XC = bricks[i][j].getX() - (DIAM / 2) - STARTX;
						double YC = bricks[i][j].getY() - (DIAM / 2) - STARTY;
					
						if( (Y==(YC+BRICK_WIDTH+(DIAM/2) ) || Y==(YC-(DIAM/2) ) ) && (X >= XC && X <= (XC+BRICK_WIDTH) ) ) {
							velY = -velY;
							remove(bricks[i][j]);
							bricks[i][j]=null;
							
						}
					//	else if( Y)
								}	
						
					}

				}
			}
		
	}


	public void startGame() {
		ball.move(velX, velY);
	}



	/* Private Instance Variables */
	private int velY = -1;
	private int velX = 1;
   private GRect bricks[][] = new GRect[NUM_COLBRICKS][NUM_ROWBRICKS];

	private GRect paddle;
	private GArc ball;
	// Bounding Lines of  Box
	private GLine verLeft;
	private GLine verRight;
	private GLine horUp;
	private GLine horDown;
	
	
}

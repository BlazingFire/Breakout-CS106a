/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.when colliding with paddle coz of else if in getCollidingObject();

	add gline instead of bounding box as getelementAt detects bounding box and doest reflect 
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;


public class Breakoutv1 extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;
	public static final int APPLICATION_XOffset = 4;
	public static final int APPLICATION_YOffset = APPLICATION_XOffset;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;
	
	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
	(WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
	
	/** Number of turns */
	private static final int NTURNS = 3;

	private static final int DELAY = 5;	

	public void init() {
		//setupBoundingBox();		
		setupBricks();
		setupBall();
		setupPaddle();

		addMouseListeners();
	}


	public void run() {

		while(true){		
		   ball.move(vx,vy);
			checkForBounce();
			collider = getCollidingObject();
		if(collider != OuterBox && collider != InnerBox){
			if (collider == paddle) {
				add(new GLabel("j",100,100));
				if(ball.getY() >= HEIGHT + APPLICATION_YOffset - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2 && ball.getY() < HEIGHT + APPLICATION_YOffset - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2 + 4) {
						vy = -vy;
				}
			}
			else if (collider != null)	{
			remove(collider); 
			//brickCounter--;
			vy = -vy;
		}
	}
			pause(DELAY);
		}
	}

	


	public void setupBricks() {
		double XOffset = (WIDTH -( (BRICK_WIDTH * NBRICKS_PER_ROW) + (NBRICKS_PER_ROW - 1) * BRICK_SEP )) / 2;
		
		for(int i = 0; i < NBRICK_ROWS; i++) {
			for(int j=0; j < NBRICKS_PER_ROW; j++) {
				bricks[i][j] = new GRect(BRICK_WIDTH,BRICK_HEIGHT);
				bricks[i][j].setFilled(true);
				bricks[i][j].setColor( color(i) );

				add(bricks[i][j],APPLICATION_XOffset + XOffset + j * (BRICK_WIDTH + BRICK_SEP),APPLICATION_YOffset + BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP) );
			}		
		}
	}

	
	/* Returns color of bricks according to the row */
	public Color color(int row) {
		if(row == 0 || row == 1)
			return Color.RED;
		else if(row == 2 || row == 3)
			return Color.ORANGE;
		else if(row == 4 || row == 5)
			return Color.YELLOW;
		else if(row == 6 || row == 7)
			return Color.GREEN;
		else
			return Color.CYAN;
	}

	public void setupBoundingBox() {
		 OuterBox = new GRect(APPLICATION_WIDTH + 2 * APPLICATION_XOffset, APPLICATION_HEIGHT + 2 * APPLICATION_YOffset);
		 InnerBox = new GRect(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		
		  OuterBox.setFilled(true);
		  InnerBox.setFilled(true);

		OuterBox.setColor(Color.BLACK);
		InnerBox.setColor(Color.BLUE);
		
		add(OuterBox,0 , 0);
		add(InnerBox, APPLICATION_YOffset, APPLICATION_YOffset);
	}

	
	public void setupPaddle() {
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.GREEN);
		add(paddle, APPLICATION_XOffset + ((WIDTH - PADDLE_WIDTH) / 2) , APPLICATION_YOffset + HEIGHT - PADDLE_HEIGHT - PADDLE_Y_OFFSET);
	}

	public void mouseMoved(MouseEvent e) {
		int X = e.getX();
		if(X >= APPLICATION_XOffset && X <= (APPLICATION_XOffset + WIDTH - PADDLE_WIDTH ))
			paddle.move(X - paddle.getX(), 0);
	}

	
	public void setupBall() {
		ball = new GOval(2*BALL_RADIUS, 2*BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.RED);
	
		add(ball,(APPLICATION_XOffset + (WIDTH / 2) - BALL_RADIUS), APPLICATION_YOffset + (HEIGHT / 2) - BALL_RADIUS);
		
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) 
			vx = -vx;	
	}	

	
	/* For collision with the Wall */
	public void checkForBounce() {
		
		
		/* Checking collisions with vertical Walls */
		if( (ball.getX() > APPLICATION_WIDTH + APPLICATION_XOffset - (2* BALL_RADIUS) ) || (ball.getX() < APPLICATION_XOffset) )	
			vx = -vx;
		
		/* Collision with top(horizontal) wall */
		if(ball.getY() < APPLICATION_YOffset)
			vy = -vy; 
		
		/* Collision with Base */
		if((ball.getY() + (2 * BALL_RADIUS)) > (APPLICATION_YOffset + HEIGHT  ) )
			vy = -vy;	
	}


	private GObject getCollidingObject() {

		if((getElementAt(ball.getX(), ball.getY())) != null) {
	         return getElementAt(ball.getX(), ball.getY());
	      }
		else if (getElementAt( (ball.getX() + BALL_RADIUS*2), ball.getY()) != null ){
	         return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY());
	      }
		else if(getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS*2)) != null ){
	         return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS*2);
	      }
		else if(getElementAt((ball.getX() + BALL_RADIUS*2), (ball.getY() + BALL_RADIUS*2)) != null ){
	         return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS*2);
	      }
		//need to return null if there are no objects present
		else{
	         return null;
	      }
		
	}
	


	/* Private Instance Variables */
	private double vx = 1.0;
	private double vy = 1.0;
	private GOval ball;
   private GRect bricks[][] = new GRect[NBRICK_ROWS][NBRICKS_PER_ROW];
	private GRect paddle;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GObject collider;
	private GRect OuterBox;
	private GRect InnerBox;
}

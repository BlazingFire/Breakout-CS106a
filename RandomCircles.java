import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;
import acm.util.*; 

public class RandomCircles extends GraphicsProgram {

	public void init() {
		addMouseListeners();
	}

	/* Called when mouse is clicked */	
	public void mouseClicked(MouseEvent e) {
			int Diam = rgen.nextInt(10,50);
			add(new GOval(e.getX(), e.getY(), Diam, Diam) );
	}

	
	/* Private instance variable */
	private RandomGenerator rgen = RandomGenerator.getInstance();
}

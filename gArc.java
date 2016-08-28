import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;
import acm.util.*; 

public class gArc extends GraphicsProgram {
	public void run() {
		GArc arc = new GArc(100,100,90,120);
		
		arc.setFilled(true);
		add(arc,100,100);
	}
}

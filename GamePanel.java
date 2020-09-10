import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JPanel; 
import javax.swing.Timer;
/**
 * @author CPSCValeriya
 */
public class GamePanel extends JPanel{
	/*
	 * INSTANCE DATA
	 */
	private double x , y;
	private Point pxy = null;
	private double velx = 1;
	private int radius =20;
	private Timer timer ;
	private int start;
	private int hits = 0;
	private int misses = 0;
	private Color c , starc1;
	private double cursorx, cursory ;
	private int spinx,spiny;
	private boolean starstatus;
	private ArrayList<Point> starPoints; 
	
	/**
	 * Constructor for our GamePanel, it will start the game. 
	 * In the constructor, we initialize our ArrayList of points for the stars,
	 * we set a random x and y coordinate for the first circle;
	 * Timer is made in the constructor; its delay and its event listener and we also create our countdown which will tell the code when to show the first circle.
	 * Most importantly we add our event listeners; key listener, mouselistener, and mousemotionlistener.
	 * In the constructor hits is originally 0 and misses is 0.
	 */
	public GamePanel() {
		starPoints = new ArrayList<Point>();
		starstatus = false;
		start = 5; 
		x = -50;		
		y = (int) ( Math.random() * 450);
		timer = new Timer(10,new MovingBall());
		
		//EVENT HANDLERS 
		addKeyListener(new Keys());
		setFocusable(true);
		addMouseListener(new ClickListener());
		addMouseMotionListener(new MotionListener());

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(500,500));
	}
	
	/**
	 * paintComponent method is the JPanels method which we are overriding. 
	 * We must call super.paintComponent to repaint the background.
	 * 
	 * In paintComponent we will draw all the components of our game; circles, cursor, stars, and hits+misses string.
	 * 
	 * In the paintComponent we also start the timer 
	 */
	public void paintComponent(Graphics g) {
		//BACKGROUND
		super.paintComponent(g);
		
		//CASTING 
		Graphics2D g2 = (Graphics2D) g;
		
		//CREATE CIRCLE
		c = Color.GRAY;
		g2.setColor(c);
		Ellipse2D.Double ball = new Ellipse2D.Double(x,y,radius,radius);
		g2.fill(ball);
		
		//PRINT HITS AND MISSES
		g2.setColor(Color.WHITE);
		Font f = new Font("Arial", Font.BOLD, 20);
		g2.setFont(f);
		g2.drawString("hits: "+hits, 10, 25);
		g2.drawString("misses: "+misses, 90, 25);
	
		//CREATE STARS
		g2.setColor(Color.YELLOW);
		int[] starx = {(int)cursorx,(int)cursorx-3,(int)cursorx-15,(int)cursorx-7,(int)cursorx-12,(int)cursorx,(int)cursorx+12,(int)cursorx+7,(int)cursorx+15,(int)cursorx+3};
		int[] stary = {(int)cursory+3,(int)cursory+15,(int)cursory+15,(int)cursory+20,(int)cursory+30,(int)cursory+23,(int)cursory+30,(int)cursory+20,(int)cursory+15,(int)cursory+15};
		Polygon star = new Polygon(starx,stary,10);
		g2.draw(star);
		g2.fill(star);
		
		//DRAW STARS FOR WHERE CIRCLE WAS CLICKED
		if(starstatus) {
			g2.setColor(starc1);
			for(Point p : starPoints) {
				spinx = (int)p.getX();
				spiny = (int)p.getY();
				int[] starsx = {(int)spinx,(int)spinx-3,(int)spinx-10,(int)spinx-3,(int)spinx-7,(int)spinx,(int)spinx+10,(int)spinx+3,(int)spinx+11,(int)spinx+3};
				int[] starsy = {(int)spiny+3,(int)spiny+10,(int)spiny+10,(int)spiny+15,(int)spiny+22,(int)spiny+18,(int)spiny+22,(int)spiny+15,(int)spiny+10,(int)spiny+10};
				Polygon stars = new Polygon(starsx,starsy,10);
				g2.fill(stars);		
			}
		}
		//START THE TIMER
		timer.start();
	}
	
	/**
	 * random method creates a new countdown for the next circle to appear 
	 */
	public void random() {
		start = (int)(50+ Math.random() * 300);
	}
	/**
	 * countdown method is what decrements the counter variable start which will tell the program when to show the circle
	 */
	public void countdown() {
		start--;
	}
	/**
	 * restart method creates a new (x,y) location for the next circle 
	 * as well as calls the random method which sets a random time for when the circle should show up on the screen
	 */
	public void restart() {
		x=-80;
		y=(int)(40+ Math.random() * 400);
		random();
	}
	
	/**
	 * Inner class MovingBall implements ActionListener, 
	 * this is the class that our timer will be activating/calling.
	 * 
	 * In the MovingBall method, the timer will be working alongside the counter variable start and tell the program when to show the ball on the screen.
	 * The new ball is activated IF the ball has left the screen (x>482) 
	 * 
	 * @author Valeriya Kistrina #100306343
	 *
	 */
	private class MovingBall implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.print(x);
			countdown();
			//IF START (COUNTER) HAS REACHED 0 PRINT MOVING CIRCLE ONTO PANEL
			if(start<0) {move();}
		
			//IF CIRCLE(x) HAS LEFT THE SCREEN (x>482)
			//INCREMENT MISSES, CREATE RANDOM NUMBER FOR COUNTER + RESTART CIRCLES LOCATION, THEN PRINT MOVING CIRCLE
			if(x>482) {
				misses++;
				random();
				restart();
				move();
			}	
		}
	}
	
	/**
	 * move method moves the circle by adding the x-velocity(velx) to the x coordinate of the circle(x)
	 * We must call repaint() for the updated circle to be printed
	 */
	public void move() {
		//CHANGE CIRCLES LOCATION
		x += velx;
		System.out.println(x);
		repaint();
	}
	
	/**
	 * Inner class Keys implements KeyListener,
	 * this will 'listen' for the key up and key down 
	 * which will resize the circle (key up for bigger circle, key down for smaller circle)
	 * We must call repaint() for the updated circle to be printed
	 * 
	 * We must implement all of KeyListeners methods because we are implementing the interface
	 * 
	 * @author Valeriya Kistrina #100306343
	 *
	 */
	private class Keys implements KeyListener{
		/**
		 * keyPressed method checks if the user has pressed key up or key down,
		 * if key up was pressed resize circle to be bigger
		 * if key down was pressed resize circle to be smaller
		 * We must call repaint() for the updated circle to be printed
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			//GET KEY PRESSED USING GETKEYCODE()
			int key = e.getKeyCode();
			
			//IF KEYCODE IS KEY UP THEN INCREASE RADIUS
			if( key == e.VK_UP) {
				radius+=5;
				System.out.println("pressed up");
			}
			//IF KEYCODE IS DOWN THEN DECREASE RADIUS
			if( key == e.VK_DOWN) {
				radius-=5;
				System.out.println("pressed down");
			}
			
			repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * Inner class ClickListener implements MouseListener
	 * we will be using the mouseClicked method 
	 * it will get the coordinate of the cursor 
	 * and check if it is in the same area as the circle by using the distance formula.
	 * 
	 * If the click is on the circle, 
	 * increment hits, raise speed of circle,
	 * set starstatus to true and save its coordinate so that we can print a star at that location
	 * and set a new (x,y) coordinate for the next circle 
	 * 
	 * We must call repaint for the update circle to be printed/disappear from the screen
	 * 
	 * We must implement all of MouseListeners methods because we are implementing the interface
	 * 
	 * @author Valeriya Kistrina #100306343
	 *
	 */
	private class ClickListener implements MouseListener{
		/**
		 * mouseClicked method gets the coordinate of the click so that we can calculate if the circle has been clicked.
		 * If the circle was clicked then increment hits score, the circle should disappear and the next circle speed should be higher. 
		 * We must call repaint for the update circle to be printed/disappear from the screen
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			//GET COORDINATE OF MOUSE CLICK
			pxy = e.getPoint();
			int clickx = (int)pxy.getX();
			int clicky = (int)pxy.getY();
			
			//CALCULATE DISTANCE FROM CLICK TO CIRCLE
			int distance = (int) Math.sqrt(Math.pow(x-clickx,2)+Math.pow(y-clicky,2));
		
			//IF CLICKED ...
			if(distance > 0 && distance < (radius/2)) {
				 starc1 = Color.yellow;
				 starPoints.add(pxy);
				 spinx = (int)clickx;
				 spiny = (int)clicky;
				 starstatus = true;  
				 
				System.out.println(radius);
				System.out.println("mouseclicked" + distance);
				
				x = (int)(Math.random() - 200);
				y = (int)(40 + Math.random() * 400);
				
				hits = hits+1;
				velx+=0.3;
				repaint();	
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * Inner class MotionListener implements MouseMotionListener,
	 * the MouseMoved method gets the coordinate of the mouse on the screen so that we can print the new cursor
	 * 
	 * We must implement all of MouseMotionListeners methods because we are implementing the interface
	 * 
	 * @author Valeriya Kistrina #100306343
	 * 
	 */
	private class MotionListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * mouseMoved method gets the coordinate of the mouse on the screen so that we can print the new cursor
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			cursorx = e.getX();
			cursory = e.getY();
			repaint();
		}
	}
}
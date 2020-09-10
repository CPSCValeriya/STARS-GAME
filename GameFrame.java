import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JFrame; 
/**
 * @author CPSCValeriya
 */
public class GameFrame extends JFrame { 
	/**
	 * Main method which creates the JFrame object and then adds the GamePanel to the frame.
	 * We make sure to setDefaultCloseOperation so that when we close the JFrame window the code is stopped and the window is fully closed.
	 * @param args : parameter args is for the command line arguments
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Get those stars!");
		frame.setSize(500,500);
		frame.setCursor(frame.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), null)); 
		frame.getContentPane().add(new GamePanel());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
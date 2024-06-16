import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

/**
 * Customizes a button.
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class CustomButton {
	/**
	 * Creates a new round button.
	 * @param text the name of the new button.
	 * @param height The height of the new button.
	 * @param width The width of the new button.
	 * @return Returns the new button.
	 */
	public static JButton createButton(String text, int height, int width){
		
		
		JButton newButton =  new RoundButton(text, 30, 30, new Color(0,191,255));
		newButton.setPreferredSize(new Dimension(height, width));
		return newButton;
		
	}

}
/**
 * 
 */
class RoundButton extends JButton{
	private int arcWidth;
	private int arcHeight;
	private Color color;
	/**
	 * 
	 * @param label
	 * @param arcWidth
	 * @param arcHeight
	 * @param color
	 */
	public RoundButton(String label, int arcWidth, int arcHeight, Color color) {
		super(label);
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
		setContentAreaFilled(false);
		setFocusPainted(false);
		this.color = color;
		setBackground(color);
		addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseEntered(MouseEvent e) {
	                setBackground(new Color(65,105,225));
	                repaint(); // Request a repaint after changing the background color
	            }

	            @Override
	            public void mousePressed(MouseEvent e) {
	                setBackground(Color.CYAN);
	                repaint(); // Request a repaint after changing the background color
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                setBackground(color); // Reset to original color on mouse exit
	                repaint(); // Request a repaint after changing the background color
	            }
		});
	 
	}
	/**
	 * 
	 */
	protected void paintComponent(Graphics g) {
		 Graphics2D g2d = (Graphics2D) g.create();
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        RoundRectangle2D.Float roundRect = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
	        g2d.setColor(getBackground()); // Use the background color
	        g2d.fill(roundRect);
	        g2d.setColor(getBackground()); // Use the background color
	        g2d.draw(roundRect);
	        g2d.dispose();
	        super.paintComponent(g);
	}
	/**
	 * 
	 */
	@Override 
	protected void paintBorder(Graphics g){
		
	}
	
	
}

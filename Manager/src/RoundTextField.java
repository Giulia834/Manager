import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

/**
 * Creates a round text field.
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class RoundTextField extends JTextField {

	private static final long serialVersionUID = 1L;
	private Shape shape;
    /**
     * Constructs a round text field with given size.
     * @param size
     */
    public RoundTextField(int size) {
        super(size);
        setOpaque(false);
    }
    /**
     * Paints a component with round borders.
     * @param g
     */
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         super.paintComponent(g);
    }
    /**
     * Paints a round border
     * @param g
     */
    protected void paintBorder(Graphics g) {
         g.setColor(getForeground());
         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    /**
     * Detects hits more accurately within rounded edges.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if the point (x,y) is in the rounded edges of the text field. False otherwise.
     */
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
}
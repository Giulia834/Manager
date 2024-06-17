import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A class to render an emoji.
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class EmojiRenderer extends DefaultTableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Adds an emoji to the played column of the game table.
     */
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        boolean played = (boolean) value;
        label.setText(played ? "🎮 ✔️" : "🎮 ❌");
        label.setHorizontalAlignment(SwingConstants.LEFT); 
        return label;
    }
}

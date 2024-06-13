import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class EmojiRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        boolean played = (boolean) value;
        label.setText(played ? "🎮 ✔️" : "🎮 ❌");
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o texto
        return label;
	}

}
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * 
 */
public class GameTable {

    private JTable gameTable;
    private DefaultTableModel tableModel;
    /**
     * Constructs the game table
     */
    public GameTable(){

    }
    /**
     * Updates the game table
     * 
     * @param gameManager
     */
    public void updateGameTable(GameManager gameManager ) {
        tableModel.setRowCount(0);
        System.out.println("aaaaa");
        List<Game> gameList = gameManager.getGameList();
        if (gameList != null) {
            for (Game game : gameList) {
                String tags = game.getTags() != null ? game.getTags().toString() : "";
                tableModel.addRow(new Object[]{game.getName(), game.getReleaseDate(), game.getDateAdded().toLocalDate(), tags, game.getPlayed()});
            }
        }
    }
    public JTable createTable() {
        String[] columnNames = {"Title", "Release Date", "Date Added", "Tags", "Played"};
        tableModel = new DefaultTableModel(columnNames, 0);
        gameTable = new JTable(tableModel);

        // Set custom renderer for the "Played" column
        gameTable.getColumnModel().getColumn(4).setCellRenderer(new EmojiRenderer());

        // Set column widths
        gameTable.getColumnModel().getColumn(0).setPreferredWidth(200); // Title
        gameTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Release Date
        gameTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Date Added
        gameTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Tags
        gameTable.getColumnModel().getColumn(4).setPreferredWidth(50);  // Played

        // Customize table appearance
        gameTable.setFont(new Font("Serif", Font.PLAIN, 16));
        gameTable.setRowHeight(30);
        gameTable.setShowVerticalLines(false); // Remove vertical grid lines
        gameTable.setOpaque(false); // Make the table background transparent
        
        // Additional customizations
        gameTable.setFocusable(false);
        gameTable.setIntercellSpacing(new Dimension(0, 0));
        gameTable.setRowHeight(25);
        gameTable.setSelectionBackground(new Color(65,105,225));
        gameTable.setBackground(new Color(135,206,235));
        gameTable.getTableHeader().setReorderingAllowed(false);
        gameTable.setShowVerticalLines(false);
       
        // Customize table header
        JTableHeader tableHeader = gameTable.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 40)); // Set header height
        tableHeader.setDefaultRenderer(new HeaderRenderer());
        
        return gameTable;
    }

    static class HeaderRenderer extends DefaultTableCellRenderer {
        public HeaderRenderer() {
            setOpaque(true);
            setBackground(new Color(0,191,255)); // Pastel blue background
            setFont(new Font("Serif", Font.BOLD, 16));
            setForeground(Color.BLACK);
            setHorizontalAlignment(SwingConstants.LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return this;
        }
    }
}
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GameTable {

    private JTable gameTable;
    private DefaultTableModel tableModel;
    
    public void updateGameTable(GameManager gameManager ) {
        tableModel.setRowCount(0);
        List<Game> gameList = gameManager.getGameList();
        if (gameList != null) {
            for (Game game : gameList) {
                String tags = game.getTags() != null ? game.getTags().toString() : "";
                tableModel.addRow(new Object[]{game.getName(), game.getReleaseDate(), game.getDateAdded(), tags, game.getPlayed()});
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
        gameTable.setShowHorizontalLines(true); // Remove horizontal grid lines
        gameTable.setOpaque(false); // Make the table background transparent
       

        return gameTable;
    }
}
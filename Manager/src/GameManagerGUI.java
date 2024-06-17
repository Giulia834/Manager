import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Creates a GUI to interact with the system functionalities.
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class GameManagerGUI {
    private JFrame frame;
    private TagManager tagsManager;
    private GameManager gameManager;
    private Color backgroundColor = new Color(135,206,235);
    private GameTable gt;

    /**
     * Constructs a new game list and initializes its frame
     */
    public GameManagerGUI() {
        tagsManager = new TagManager();
        gameManager = new GameManager();
        gt = new GameTable();
        initialize();
    }
    
    /**
     * Initializes the game list frame
     */
    private void initialize() {
        frame = new JFrame("Game Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        // Filters and tags Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(backgroundColor);
        leftPanel.setOpaque(false);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        // Title and search panel
        JPanel upPanel = new JPanel(new GridLayout(2,1));
        upPanel.setBackground(backgroundColor);
        
        // Title image
        ImageIcon icon = new ImageIcon("../MyGameList.png");
        JLabel title = new JLabel(icon, SwingConstants.CENTER);
        upPanel.add(title);

        // Game Management Panel
        JPanel gamePanel = new JPanel(new FlowLayout());
        gamePanel.setBackground(backgroundColor);
        JButton addGameButton = CustomButton.createButton("Add Game", 100, 50);
        JButton deleteGameButton = CustomButton.createButton("Delet Game", 100, 50);
        gamePanel.add(addGameButton);
        gamePanel.add(deleteGameButton);
        
        // Search
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(backgroundColor);
        JTextField searchTextField = new RoundTextField(20);
        JButton searchButton =  CustomButton.createButton("🔍", 100, 30);
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);
        upPanel.add(searchPanel);
        
        // Filters
        JPanel empty1 = new JPanel();
        empty1.setBackground(backgroundColor);
        JPanel empty2 = new JPanel();
        empty2.setBackground(backgroundColor);
        JPanel filtersPanel = new JPanel();
        filtersPanel.setLayout(new BoxLayout(filtersPanel, BoxLayout.Y_AXIS));
        filtersPanel.setBackground(backgroundColor);
        
        JComboBox<String> filtersComboBox = new JComboBox<String>(new String[] {"Default", "A-Z","Z-A","Release Date","Date Added"});
        filtersComboBox.setBackground(new Color(30,144,255));
        
        JComboBox<String> playedComboBox = new JComboBox<String>(new String[] {"All","Played","Not Played"});
        playedComboBox.setBackground(new Color(30,144,255));
        
        filtersPanel.add(filtersComboBox);
        filtersPanel.add(empty1);
        filtersPanel.add(playedComboBox);
        filtersPanel.add(empty2);
        leftPanel.add(filtersPanel, BorderLayout.NORTH);

        // Game Management Panel
        JButton addTagButton =  CustomButton.createButton("Add Tag", 100, 50);
        JButton deleteTagButton =  CustomButton.createButton("Delet Tag", 100, 50);
        JButton saveButton =  CustomButton.createButton("Save", 100, 50);
        gamePanel.add(addTagButton);
        gamePanel.add(deleteTagButton);
        gamePanel.add(saveButton);
        
        // Adding the tags filter to the left panel
        JScrollPane tagFilterPanel = CheckBoxPanel.tagFilterPanel(tagsManager, gameManager, gt, backgroundColor);
        leftPanel.add((tagFilterPanel), BorderLayout.CENTER);
        
        mainPanel.add(upPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(gamePanel, BorderLayout.SOUTH);
        
        // Table for displaying games
        JTable gameTable = gt.createTable();
        gameTable.getColumnModel().getColumn(4).setCellRenderer(new EmojiRenderer());
        JScrollPane scrollPane = new JScrollPane(gameTable);
        scrollPane.setBorder(null);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10)); // Remove border from JScrollPane
        scrollPane.getViewport().setOpaque(false); // Ensure the viewport is transparent
        scrollPane.setBackground(backgroundColor);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button Action Listeners
        addGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameDialog.showAddGameDialog(frame, tagFilterPanel, tagsManager, gameManager, gt, backgroundColor);
            }
        });

        deleteGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedGame(gameTable);
            }
        });

        addTagButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TagDialog.showAddTagDialog(frame, tagFilterPanel, tagsManager, backgroundColor);
            }
        });

        deleteTagButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TagDialog.showDeleteTagDialog(frame, tagFilterPanel, tagsManager, backgroundColor);
                
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tagsManager.saveTags();
                gameManager.saveGames();
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String gameName = searchTextField.getText();
                gameManager.searchGame(gameName);
                gt.updateGameTable(gameManager);
            }
        });
        
        filtersComboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (filtersComboBox.getSelectedItem().equals("A-Z")) {
        			gameManager.gameList.sort(0);
        			gt.updateGameTable(gameManager);
        		}
        		else if (filtersComboBox.getSelectedItem().equals("Z-A")) {
        			gameManager.gameList.sort(1);
        			gt.updateGameTable(gameManager);
        		}
        		else if (filtersComboBox.getSelectedItem().equals("Release Date")) {
        			gameManager.gameList.sort(2);
        			gt.updateGameTable(gameManager);
        		}
        		else if (filtersComboBox.getSelectedItem().equals("Date Added")) {
        			gameManager.gameList.sort(3);
        			gt.updateGameTable(gameManager);
        		}
        	}
        });
        
        playedComboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		gameManager.filterByPlayed(playedComboBox.getSelectedItem().toString());
        		gt.updateGameTable(gameManager);
        	}
        });
        // Setting the background color of the frame and making it visible
        frame.setContentPane(mainPanel);
        frame.getContentPane().setBackground(backgroundColor);
        frame.setVisible(true);
        gt.updateGameTable(gameManager);
    }

    /**
     * Deletes the selected game from the game list.
     * @param gameTable
     */
    private void deleteSelectedGame(JTable gameTable) {
        int selectedRow = gameTable.getSelectedRow();
        if (selectedRow != -1) {
            String gameName = (String) gameTable.getValueAt(selectedRow, 0);
            gameManager.gameList.deleteGame(gameName);
            gt.updateGameTable(gameManager);
           
        }
    }
    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @SuppressWarnings("unused")
			public void run() {
                try {
                    GameManagerGUI window = new GameManagerGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

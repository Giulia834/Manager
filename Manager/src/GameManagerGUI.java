

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class GameManagerGUI {
    private JFrame frame;
    private TagManager tagsManager;
    private GameManager gameManager;
    private DefaultTableModel tableModel;
    private Color backgroundColor = new Color(135,206,235);
    
    private GameTable gt;

    /**
     * 
     */
    public GameManagerGUI() {
        tagsManager = new TagManager();
        gameManager = new GameManager();
        initialize();
    }
    
    /**
     * 
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

        // Game Management main Panel
        JButton addTagButton =  CustomButton.createButton("Add Tag", 100, 50);
        JButton deleteTagButton =  CustomButton.createButton("Delet Tag", 100, 50);
        JButton saveButton =  CustomButton.createButton("Save", 100, 50);
        gamePanel.add(addTagButton);
        gamePanel.add(deleteTagButton);
        gamePanel.add(saveButton);
        
        leftPanel.add((CheckBoxPanel.tagFilterPanel(tagsManager, gameManager, gt, backgroundColor)), BorderLayout.CENTER);
        
        mainPanel.add(upPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(gamePanel, BorderLayout.SOUTH);
        
        // Table for displaying games
        String[] columnNames = {"Title", "Release Date", "Date Added", "Tags", "Played"};
        gt = new GameTable();
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
                showAddGameDialog();
            }
        });

        deleteGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedGame(gameTable);
            }
        });

        addTagButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddTagDialog();
            }
        });

        deleteTagButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDeleteTagDialog();
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
        frame.setContentPane(mainPanel);
        frame.getContentPane().setBackground(backgroundColor);
        frame.setVisible(true);
        gt.updateGameTable(gameManager);
    }
    
    /**
     * 
     */
    private void showAddGameDialog() {
        JDialog dialog = new JDialog(frame, "Add Game", true);
        dialog.setSize(400, 300);
        
        JPanel addGamePanel = new JPanel(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new RoundTextField(15);
        JLabel releaseDateLabel = new JLabel("Release Date (yyyy-mm-dd):");
        JTextField releaseDateField = new RoundTextField(15);
        JLabel tagsLabel = new JLabel("Tags:");
        JPanel tagsPanel = new JPanel();
        
        JCheckBox playedCheckBox = new JCheckBox("Played");
        playedCheckBox.setBackground(backgroundColor);
        
        JPanel nameFieldPanel = new JPanel();
        nameFieldPanel.setBackground(backgroundColor);
        nameFieldPanel.setBorder(new EmptyBorder(10,0,0,0));
        nameFieldPanel.add(nameField);
        
        JPanel releaseDateFieldPanel = new JPanel();
        releaseDateFieldPanel.setBackground(backgroundColor);
        releaseDateFieldPanel.setBorder(new EmptyBorder(10,0,0,0));
        releaseDateFieldPanel.add(releaseDateField);
        
        JPanel addTagButtonPanel = new JPanel();
        addTagButtonPanel.setBackground(backgroundColor);
        addTagButtonPanel.setBorder(new EmptyBorder(0,0,0,0));
        
        JPanel addButtonPanel = new JPanel();
        addButtonPanel.setBackground(backgroundColor);
        addButtonPanel.setBorder(new EmptyBorder(0,0,0,0));
        
        List<JCheckBox> tagsCheckBoxList = CheckBoxPanel.checkBoxList(tagsManager,backgroundColor);

        JButton addTagButton = CustomButton.createButton("New Tag",150,40);
        addTagButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                showAddTagDialog();
                tagsCheckBoxList.clear();
                List<Tag> tagList = tagsManager.getTagList();
                if (tagList != null) {
                    for (Tag tag : tagList) {
                    	JCheckBox tags = new JCheckBox(tag.getTagName());
                        tagsCheckBoxList.add(tags);
                        dialog.dispose();
                    }
                }
            }
        });
        addTagButtonPanel.add(addTagButton);

        JButton addButton = CustomButton.createButton("Add",150,40);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                LocalDate releaseDate = null;
                boolean played = playedCheckBox.isSelected();
                try {
                    releaseDate = LocalDate.parse(releaseDateField.getText());
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid date format. Please use yyyy-mm-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<String> selectedTagList = new ArrayList<String>();
                for (JCheckBox tagCheckBox : tagsCheckBoxList)
        	        if (tagCheckBox.isSelected()) {
        	        	selectedTagList.add((String) tagCheckBox.getText());
        	        }
                List<Tag> selectedTags = new ArrayList<>();
                List<Tag> tagList = tagsManager.getTagList();
                if (tagList != null) {
                    for (Tag tag : tagList) {
                        for (String tagName : selectedTagList) {
	                    	if (tag.getTagName().equals(tagName)) {
	                            selectedTags.add(tag);
	                        }
                    	}
                    }
                }

                Game game = new Game(name, selectedTags, releaseDate, played);
                if (!gameManager.gameList.addGame(game))
                	JOptionPane.showMessageDialog(dialog, "This name is already used.", "Error", JOptionPane.ERROR_MESSAGE);
                gt.updateGameTable(gameManager);
                dialog.dispose();
            }
        });
        addButtonPanel.add(addButton);
        
        for(JCheckBox tagCheckBox : tagsCheckBoxList)
        	tagsPanel.add(tagCheckBox);
        tagsPanel.setBackground(backgroundColor);
        JScrollPane tagsScrollPane = new JScrollPane(tagsPanel);
        tagsScrollPane.setBackground(backgroundColor);
        tagsScrollPane.setBorder(new EmptyBorder(0,0,0,0));
        tagsScrollPane.getHorizontalScrollBar().setBackground(backgroundColor);
        
        addGamePanel.add(nameLabel);
        addGamePanel.add(nameFieldPanel);
        addGamePanel.add(releaseDateLabel);
        addGamePanel.add(releaseDateFieldPanel);
        addGamePanel.add(tagsLabel);
        addGamePanel.add(tagsScrollPane);
        addGamePanel.add(new JLabel()); // Empty cell
        addGamePanel.add(addTagButtonPanel);
        addGamePanel.add(playedCheckBox);
        addGamePanel.add(addButtonPanel);
        
        dialog.setContentPane(addGamePanel);
        dialog.getContentPane().setBackground(backgroundColor);
        dialog.setVisible(true);
    }
    
    /**
     * 
     */
    private void showAddTagDialog() {
        JDialog dialog = new JDialog(frame, "Add Tag", true);
        dialog.setLayout(new GridLayout(3, 2));
        dialog.setSize(300, 150);

        JLabel nameLabel = new JLabel("Tag Name:");
        JTextField nameField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                if(!tagsManager.addTag(name, description)) {
                	JOptionPane.showMessageDialog(dialog, "Name is already used", "Erro", JOptionPane.ERROR_MESSAGE);
                	return;
                }
                dialog.dispose();
            }
        });

        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(descriptionLabel);
        dialog.add(descriptionField);
        dialog.add(new JLabel()); // Empty cell
        dialog.add(addButton);

        dialog.setVisible(true);
    }
    
    /**
     * 
     */
    private void showDeleteTagDialog() {
        JDialog dialog = new JDialog(frame, "Delete Tag", true);
        dialog.setLayout(new GridLayout(2, 2));
        dialog.setSize(300, 150);

        JLabel nameLabel = new JLabel("Tag Name:");
        JPanel tagsPanel = new JPanel();
        List<JCheckBox> tagsCheckBoxList = new ArrayList<JCheckBox>();

        List<Tag> tagList = tagsManager.getTagList();
        if (tagList != null) {
            for (Tag tag : tagList) {
            	JCheckBox tags = new JCheckBox(tag.getTagName());
                tagsCheckBoxList.add(tags);
            }
        }

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	List<String> selectedTagList = new ArrayList<String>();
            	for (JCheckBox tagsCheckBox : tagsCheckBoxList)
        	        if (tagsCheckBox.isSelected())
        	        	selectedTagList.add((String) tagsCheckBox.getText());
	                for (String tagName : selectedTagList)
	            		if (tagName != null) {
		                    tagsManager.deleteTag(tagName);
		                    dialog.dispose();
		                }
            }
        });
        
        for(JCheckBox tagsCheckBox : tagsCheckBoxList)
        	tagsPanel.add(tagsCheckBox);
        JScrollPane tagsScrollPane = new JScrollPane(tagsPanel);
        
        dialog.add(nameLabel);
        dialog.add(tagsScrollPane);
        dialog.add(new JLabel()); // Empty cell
        dialog.add(deleteButton);

        dialog.setVisible(true);
    }

    /**
     * 
     * @param gameTable
     */
    private void deleteSelectedGame(JTable gameTable) {
        int selectedRow = gameTable.getSelectedRow();
        if (selectedRow != -1) {
            String gameName = (String) gameTable.getValueAt(selectedRow, 0);
            gameManager.gameList.deleteGame(gameName);
           
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
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

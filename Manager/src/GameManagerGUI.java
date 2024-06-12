

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class GameManagerGUI {
    private JFrame frame;
    private TagManager tagsManager;
    private GameManager gameManager;
    private DefaultTableModel tableModel;

    public GameManagerGUI() {
        tagsManager = new TagManager();
        gameManager = new GameManager();
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("Game Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel(new GridLayout(3, 1));

        // Game Management Panel
        JPanel gamePanel = new JPanel(new FlowLayout());
        JButton addGameButton = new JButton("Add Game");
        JButton deleteGameButton = new JButton("Delete Game");
        // Search
        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchTextField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);
        gamePanel.add(addGameButton);
        gamePanel.add(deleteGameButton);
        mainPanel.add(gamePanel);
        
        JPanel filtersPanel = new JPanel(new FlowLayout());
        JComboBox<String> filtersComboBox = new JComboBox<String>(new String[] {"A-Z","Z-A","Release Date","Date Added"});
        JComboBox<String> playedComboBox = new JComboBox<String>(new String[] {"All","Played","Not Played"});
        filtersPanel.add(filtersComboBox);
        filtersPanel.add(playedComboBox);

        // Game Management main Panel
        JPanel gamePane1 = new JPanel(new FlowLayout());
        JButton addTagButton = new JButton("Add Tag");
        JButton deleteTagButton = new JButton("Delete Tag");
        JButton saveButton = new JButton("Save");
        gamePanel.add(addTagButton);
        gamePanel.add(deleteTagButton);
        gamePanel.add(saveButton);
        mainPanel.add(gamePanel);
        mainPanel.add(searchPanel);
       
        mainPanel.add(filtersPanel);
        
        gamePanel.add(CheckBoxPanel.tagFilterPanel(tagsManager, gameManager, this));
        frame.add(mainPanel, BorderLayout.NORTH);
        
        // Table for displaying games
        String[] columnNames = {"Title", "Release Date", "Date Added", "Tags", "Played"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable gameTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(gameTable);
        frame.add(scrollPane, BorderLayout.CENTER);

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
                updateGameTable();
            }
        });
        
        filtersComboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (filtersComboBox.getSelectedItem().equals("A-Z")) {
        			gameManager.gameList.sort(3);
        			updateGameTable();
        		}
        		else if (filtersComboBox.getSelectedItem().equals("Z-A")) {
        			gameManager.gameList.sort(0);
        			updateGameTable();
        		}
        		else if (filtersComboBox.getSelectedItem().equals("Release Date")) {
        			gameManager.gameList.sort(1);
        			updateGameTable();
        		}
        		else {
        			gameManager.gameList.sort(2);
        			updateGameTable();
        		}
        	}
        });
        
        playedComboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		gameManager.filterByPlayed(playedComboBox.getSelectedItem().toString());
        		updateGameTable();
        	}
        });
        
        frame.setVisible(true);
        updateGameTable();
    }

    private void showAddGameDialog() {
        JDialog dialog = new JDialog(frame, "Add Game", true);
        dialog.setLayout(new GridLayout(5, 2));
        dialog.setSize(400, 300);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel releaseDateLabel = new JLabel("Release Date (yyyy-mm-dd):");
        JTextField releaseDateField = new JTextField();
        JLabel tagsLabel = new JLabel("Tags:");
        JPanel tagsPanel = new JPanel();
        
        JCheckBox playedCheckBox = new JCheckBox("Played");
        
        
        List<JCheckBox> tagsCheckBoxList = CheckBoxPanel.checkBoxList(tagsManager);

        JButton addTagButton = new JButton("New Tag");
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
       

        JButton addButton = new JButton("Add");
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
                gameManager.gameList.addGame(game);
                updateGameTable();
                dialog.dispose();
            }
        });
        
        for(JCheckBox tagCheckBox : tagsCheckBoxList)
        	tagsPanel.add(tagCheckBox);
        JScrollPane tagsScrollPane = new JScrollPane(tagsPanel);
        
        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(releaseDateLabel);
        dialog.add(releaseDateField);
        dialog.add(tagsLabel);
        dialog.add(tagsScrollPane);
        dialog.add(new JLabel());
        dialog.add(addTagButton);
        dialog.add(new JLabel()); // Empty cell
        dialog.add(playedCheckBox);
        dialog.add(addButton);

        dialog.setVisible(true);
    }
    

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
                tagsManager.addTag(name, description);
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

    
    private void deleteSelectedGame(JTable gameTable) {
        int selectedRow = gameTable.getSelectedRow();
        if (selectedRow != -1) {
            String gameName = (String) gameTable.getValueAt(selectedRow, 0);
            gameManager.gameList.deleteGame(gameName);
            updateGameTable();
        }
    }

    void updateGameTable() {
        tableModel.setRowCount(0);
        List<Game> gameList = gameManager.getGameList();
        if (gameList != null) {
            for (Game game : gameList) {
                String tags = game.getTags() != null ? game.getTags().toString() : "";
                tableModel.addRow(new Object[]{game.getName(), game.getReleaseDate(), game.getDateAdded(), tags, game.getPlayed()});
            }
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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class GameDialog {
	/**
     * Shows the dialog to add a game in the list
     */
    public static void showAddGameDialog(JFrame frame, TagManager tagsManager, GameManager gameManager, GameTable gt, Color backgroundColor) {
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
                TagDialog.showAddTagDialog(frame, tagsManager, backgroundColor);
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
}

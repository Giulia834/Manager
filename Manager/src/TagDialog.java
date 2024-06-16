import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class TagDialog {
	/**
     * Shows the dialog to add a tag
     */
    public static void showAddTagDialog(JFrame frame, TagManager tagsManager, Color backgroundColor) {
        JDialog dialog = new JDialog(frame, "Add Tag", true);
   
       
        dialog.setSize(400, 250);
        dialog.setBackground(backgroundColor);
        
        JPanel addTagPanel = new JPanel();
        addTagPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Tag Name:");
        nameLabel.setBackground(backgroundColor);
        JTextField nameField = new RoundTextField(15);
        nameField.setBorder(new EmptyBorder(2, 2, 2, 0));
        
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new RoundTextField(15);
        
        JPanel nameFieldPanel = new JPanel();
        nameFieldPanel.setBackground(backgroundColor);
        nameFieldPanel.setBorder(new EmptyBorder(20,0,0,0));
        nameFieldPanel.add(nameField);
        
        JPanel descriptionFieldPanel = new JPanel();
        descriptionFieldPanel.setBackground(backgroundColor);
        descriptionFieldPanel.setBorder(new EmptyBorder(20,0,0,0));
        descriptionFieldPanel.add(descriptionField);
        
        JButton addButton = CustomButton.createButton("Add", 150, 50);
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
        JPanel aux = new JPanel();
        aux.add(addButton);
        addButton.setBorder(new EmptyBorder(2, 2, 2, 0));
        aux.setBackground(backgroundColor);
        addTagPanel.add(nameLabel);
        addTagPanel.add(nameFieldPanel);
        addTagPanel.add(descriptionLabel);
        addTagPanel.add(descriptionFieldPanel);
        addTagPanel.add(new JLabel()); // Empty cell
        addTagPanel.add(aux);
        
        dialog.setContentPane(addTagPanel);
        dialog.getContentPane().setBackground(backgroundColor);
        dialog.setVisible(true);
    }
    
    /**
     * Shows the dialog to delete a tag
     */
    public static void showDeleteTagDialog(JFrame frame, TagManager tagsManager, Color backgroundColor) {
        JDialog dialog = new JDialog(frame, "Delete Tag", true);
        dialog.setSize(300, 150);
        
        JPanel deleteTagPanel = new JPanel();
        deleteTagPanel.setLayout(new GridLayout(2, 2));

        JLabel nameLabel = new JLabel("Tag Name:");
        JPanel tagsPanel = new JPanel();
        List<JCheckBox> tagsCheckBoxList = CheckBoxPanel.checkBoxList(tagsManager,backgroundColor);
        
        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.setBorder(new EmptyBorder(5,0,0,5));
        deleteButtonPanel.setBackground(backgroundColor);
        JButton deleteButton = CustomButton.createButton("Delete", 140, 40);
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
        deleteButtonPanel.add(deleteButton);
        
        for(JCheckBox tagsCheckBox : tagsCheckBoxList)
        	tagsPanel.add(tagsCheckBox);
        tagsPanel.setBackground(backgroundColor);
        JScrollPane tagsScrollPane = new JScrollPane(tagsPanel);
        tagsScrollPane.setBackground(backgroundColor);
        tagsScrollPane.setBorder(new EmptyBorder(0,0,0,0));
        tagsScrollPane.getHorizontalScrollBar().setBackground(backgroundColor);
        
        deleteTagPanel.add(nameLabel);
        deleteTagPanel.add(tagsScrollPane);
        deleteTagPanel.add(new JLabel()); // Empty cell
        deleteTagPanel.add(deleteButtonPanel);
        
        dialog.setContentPane(deleteTagPanel);
        dialog.getContentPane().setBackground(backgroundColor);
        dialog.setVisible(true);
    }
}

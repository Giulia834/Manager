import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class CheckBoxPanel {
	/**
	 * 
	 * @param tagManager
	 * @param gameManager
	 * @param gameManagerGUI
	 * @return
	 */
	public static JScrollPane tagFilterPanel(TagManager tagManager, GameManager gameManager, GameManagerGUI gameManagerGUI) {
		List<JCheckBox> tagsCheckBoxList = checkBoxList(tagManager);
		filterCheckBox(tagsCheckBoxList, tagManager, gameManager,gameManagerGUI);
      JPanel tagsPanel = new JPanel();
      tagsPanel.setLayout(new BoxLayout(tagsPanel, BoxLayout.Y_AXIS));
      for(JCheckBox tagCheckBox : tagsCheckBoxList)
      	tagsPanel.add(tagCheckBox);
      JScrollPane tagsScrollPane = new JScrollPane(tagsPanel);
      
      return tagsScrollPane;
	}
	
	/**
	 * 
	 * @param tagManager
	 * @return
	 */
	public static List<JCheckBox> checkBoxList(TagManager tagManager) {
		  List<JCheckBox> tagsCheckBoxList = new ArrayList<JCheckBox>();
	      if (tagManager.getTagList() != null) {
	          for (Tag tag : tagManager.getTagList()) {
	          	JCheckBox tagCheckBox = new JCheckBox(tag.getTagName());
	            tagsCheckBoxList.add(tagCheckBox);
	          }
	      }
	      return tagsCheckBoxList;
	}
	
	/**
	 * 
	 * @param tagsCheckBoxList
	 * @param tagManager
	 * @param gameManager
	 * @param gameManagerGUI
	 */
	private static void filterCheckBox(List<JCheckBox> tagsCheckBoxList, TagManager tagManager, GameManager gameManager, GameManagerGUI gameManagerGUI) {
		 for(JCheckBox tagCheckBox: tagsCheckBoxList) {
	    	  tagCheckBox.addActionListener(  new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						List<Tag> tagList = new ArrayList<Tag>();
						for(JCheckBox aux: tagsCheckBoxList) {
							if(aux.isSelected()) {
								Tag t = tagManager.tagDict.get(aux.getText());
								if(t!=null) {
									tagList.add(t);
								}
							}
						}
						if(tagList.isEmpty())
							gameManager.filterByTag(tagList, false);
						gameManager.filterByTag(tagList, true);
						gameManagerGUI.updateGameTable();
							
					}
	    	  });
		
		 }
	}
}

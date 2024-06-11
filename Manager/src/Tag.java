

import java.io.Serializable;

/**
 * Represents a tag associated with a game.
 */
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tagName;
    private String description;

    /**
     * Constructs a new Tag object with the specified name and description.
     * @param tagName The name of the tag.
     * @param description The description of the tag.
     */
    public Tag(String tagName, String description) {
        this.tagName = tagName;
        this.description = description;
    }

    /**
     * Retrieves the name of the tag.
     * @return The name of the tag.
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets the name of the tag.
     * @param tagName The new name of the tag.
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Retrieves the description of the tag.
     * @return The description of the tag.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the tag.
     * @param description The new description of the tag.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the tag (its name).
     * @return The name of the tag.
     */
    @Override
    public String toString() {
        return tagName;
    }
}

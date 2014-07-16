import components.xmltree.XMLTree;

/**
 * A class representing the information of a news article.
 * 
 * @author Thomas Clark
 * 
 */
public final class NFNewsArticle {

    /**
     * The article's title
     */
    String title = "";

    /**
     * The article's content
     */
    String content = "";

    /**
     * Constructor for NFNewsArticle. The root of an article's XMLTree is passed
     * to the constructor so that it's information can be extracted.
     */
    public NFNewsArticle(XMLTree item) {
        int titleIndex = XMLTreeUtility.getChildElement(item, "title");
        if (titleIndex >= 0) {
            this.title = item.child(titleIndex).child(0).label();
        } else {
            this.title = "Empty Title";
        }
        int contentIndex = XMLTreeUtility.getChildElement(item, "media:text");
        if (contentIndex >= 0) {
            this.content = item.child(contentIndex).child(0).label();
        } else {
            this.content = "Empty Content";
        }
    }

    /**
     * Returns the news article's title
     */
    String title() {
        return this.title;
    }

    /**
     * Returns the news article's content
     */
    String sourceTitle() {
        return this.content;
    }

    /**
     * Override toString() method
     */
    @Override
    public String toString() {
        String toString = "";
        toString = this.title;
        return toString;
    }

}

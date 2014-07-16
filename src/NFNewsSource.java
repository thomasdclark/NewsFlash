import java.util.ArrayList;

import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * A class representing the information of a news source.
 * 
 * @author Thomas Clark
 */
public final class NFNewsSource {

    /**
     * URL of RSS feed. Assumes that it is a valid RSS 2.0 feed.
     */
    String rssURL = "";

    /**
     * Title of news source
     */
    String sourceTitle = "";

    /**
     * Array of news articles contained by news source
     */
    ArrayList<NFNewsArticle> newsArticles = new ArrayList<NFNewsArticle>();

    /**
     * Constructor for NFNewsSource
     */
    public NFNewsSource(String url) {
        //Set the rssURL instance variable
        this.rssURL = url;
        XMLTree feed = new XMLTree1(url);
        XMLTree channel = feed.child(0);
        int titleIndex = XMLTreeUtility.getChildElement(channel, "title");
        this.sourceTitle = channel.child(titleIndex).child(0).label();
        int firstItemIndex = XMLTreeUtility.getChildElement(channel, "item");
        if (firstItemIndex >= 0) {
            for (int i = firstItemIndex; i < channel.numberOfChildren(); i++) {
                NFNewsArticle article = new NFNewsArticle(channel.child(i));
                this.newsArticles.add(article);
            }
        }
    }

    /**
     * Returns the news source's RSS URL
     */
    String rssURL() {
        String url = this.rssURL;
        return url;
    }

    /**
     * Returns the news source's title
     */
    String sourceTitle() {
        String title = this.sourceTitle;
        return title;
    }

    /**
     * Resets the news source's RSS URL and articles
     */
    void setRSSURL(String url) {
        //Set the rssURL instance variable
        this.rssURL = url;
        //Reset news articles
        this.newsArticles = new ArrayList<NFNewsArticle>();
        XMLTree feed = new XMLTree1(url);
        XMLTree channel = feed.child(0);
        int titleIndex = XMLTreeUtility.getChildElement(channel, "title");
        this.sourceTitle = channel.child(titleIndex).child(0).label();
        int firstItemIndex = XMLTreeUtility.getChildElement(channel, "item");
        if (firstItemIndex >= 0) {
            for (int i = firstItemIndex; i < channel.numberOfChildren(); i++) {
                NFNewsArticle article = new NFNewsArticle(channel.child(i));
                this.newsArticles.add(article);
            }
        }
    }

    /**
     * Returns the news source's articles
     */
    ArrayList<NFNewsArticle> newsArticles() {
        return this.newsArticles;
    }

    /**
     * Override toString() method
     */
    @Override
    public String toString() {
        String toString = "";
        toString = "News Source:\n     " + this.sourceTitle + "\nArticles:\n";
        for (int i = 0; i < this.newsArticles.size(); i++) {
            toString = toString + "     " + this.newsArticles.get(i).toString()
                    + "\n";
        }
        return toString;
    }
}

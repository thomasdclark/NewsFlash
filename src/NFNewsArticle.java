import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
     * The article's link (URL)
     */
    String link = "";

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
        int linkIndex = XMLTreeUtility.getChildElement(item, "link");
        if (linkIndex >= 0) {
            this.link = item.child(linkIndex).child(0).label();
            this.content = this.getHTML(this.link);
        } else {
            this.link = "No link";
            this.content = "Empty content";
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
    String content() {
        return this.content;
    }

    /**
     * Returns string of html connect from a webpage
     */
    public String getHTML(String urlToRead) {
        String line;
        String content = "";
        try {
            URL url = new URL(urlToRead);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            while ((line = in.readLine()) != null) {
                content += line;
            }
            System.out.println("    Retrieved article:  " + this.title);
            in.close();
        } catch (Exception e) {
            System.out.println("    Unable to retrieved article:  "
                    + this.title);
        }
        return content;
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

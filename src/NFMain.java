import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * NewsFlash is a data analysis application that parses the daily articles
 * written by your favorite news sources, calculates the overall
 * positivity/negativity of the individual articles and news sources as a whole,
 * and ranks the news sources from most positive to most negative. Ideally,
 * NewsFlash is to be used with news sources that are reporting on similar
 * events in order to obtain an objective analysis of equal news content. The
 * goal of NewsFlash is to prove that some news sources take a more pessimistic
 * approach to reporting the news, which can greatly affect the reader's take
 * away and over understanding of what actually happened.
 * 
 * 
 * @author Thomas Clark
 * 
 */
public final class NFMain {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NFMain() {
    }

    /**
     * Checks input url to make sure it is a valid RSS 2.0 source
     */
    public static String getRSS(SimpleReader in, SimpleWriter out) {
        String rssURL = "";
        XMLTree xml = null;
        boolean validRSS = false;
        while (!validRSS) {
            out.print("Enter the URL of an RSS 2.0 news source: ");
            rssURL = in.nextLine();
            xml = new XMLTree1(rssURL);
            if (xml.isTag() && xml.label().equals("rss")) {
                if (xml.hasAttribute("version")) {
                    if (xml.attributeValue("version").equals("2.0")) {
                        validRSS = true;
                    } else {
                        out.println("The URL you input is not a valid RSS 2.0 news source.");
                    }
                } else {
                    out.println("The URL you input is not a valid RSS 2.0 news source.");
                }
            } else {
                out.println("The URL you input is not a valid RSS 2.0 news source.");
            }
        }
        return rssURL;
    }

    /**
     * Main method to begin NewsFlash program
     */
    public static void main(String[] args) {
        /*
         * Open I/O streams.
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * First version: test that NFNewsSource, NFNewsArticle, and NFXMLTree
         * classes work
         */
        out.println("Testing to make sure that written classes and functions work.  Please enter a valid RSS 2.0 news source when asked.\n");

        String url = getRSS(in, out);
        NFNewsSource newsSource = new NFNewsSource(url);
        out.println(newsSource);

        //Test that word lists are initialized correctly
        NFPositivityRanking ranking = new NFPositivityRanking();

        /*
         * Close I/O streams.
         */
        in.close();
        out.close();
    }
}
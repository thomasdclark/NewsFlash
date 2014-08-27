import java.io.IOException;
import java.util.ArrayList;

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
     * Receives and checks url (input by user) to make sure it is a valid RSS
     * 2.0 source
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
     * Checks url to make sure it is a valid RSS 2.0 source
     */
    public static boolean checkRSS(String fileName) {
        boolean validRSS = false;
        XMLTree xml = new XMLTree1(fileName);
        if (xml.isTag() && xml.label().equals("rss")) {
            if (xml.hasAttribute("version")) {
                if (xml.attributeValue("version").equals("2.0")) {
                    validRSS = true;
                }
            }
        }
        return validRSS;
    }

    /**
     * Returns instances of NFNewsSource that are taken and created from the
     * input file
     */
    public static ArrayList<NFNewsSource> getNewsSources(String fileName) {
        ArrayList<NFNewsSource> newsSources = new ArrayList<NFNewsSource>();
        SimpleReader in = new SimpleReader1L(fileName);
        String url = "";
        while (!in.atEOS()) {
            url = in.nextLine();
            if (checkRSS(url)) {
                NFNewsSource newsSource = new NFNewsSource(url);
                newsSources.add(newsSource);
            }
        }
        return newsSources;
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

        boolean quit = false;
        out.println("Welcome to the NewsFlash News Source Positivity Ranking Program");
        while (!quit) {
            out.print("What would you like to do (track, graph, quit, help):  ");
            String input = in.nextLine();
            input = input.toLowerCase();
            if (input.equals("track")) {
                out.println("  Now tracking current news source positivity.");
                out.println("  Program may take a few minutes to analyze news content.");
                String newsFile = "resources/news_sources.txt";
                ArrayList<NFNewsSource> newsSources = getNewsSources(newsFile);
                NFDataModel model = new NFDataModel(newsSources);
                out.print(model);
                model.saveToFile();
            } else if (input.equals("graph")) {
                out.println("  Now graphing past positivity data.");
                NFGraph graph = new NFGraph();
                try {
                    graph.displayPlot();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (input.equals("quit")) {
                out.print("Thank you for using NewsFlash.");
                quit = true;
            } else if (input.equals("help")) {
                out.println("  NewsFlash is a program that you can use to track the positivity of your favorite news sources.");
                out.println("  Use the file resources/news_sources.txt to add links to the RSS feeds of the news_sources that you want to track.");
                out.println("  For each news source that you choose to track, this program will go to all of its articles and determine a positivity ratio based on the number of positve to negative words.");
                out.println("  You can change the positive or negative word lists that a used in the resources directory.");
                out.println("  At the beginning of each program, you are asked to input 'track', 'graph', or 'quit'.");
                out.println("  Choosing 'track' will make the program track the positivity for each news source for the current date and time.");
                out.println("  All of the data the program collects is stored in the /data directory.");
                out.println("  Choosing 'graph' will make the program graph the data from the past four trackings.");
                out.println("  Choosing 'quit' will alloq you to quit out of the program.");
            } else {
                out.println("  Input not understood.  Please try again.");
            }
        }

        /*
         * Close I/O streams.
         */
        in.close();
        out.close();
    }
}
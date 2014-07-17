import java.util.ArrayList;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * The class that creates the positivity ranking of the news sources. It takes
 * in an array of NFNewsSources and uses a formula on their articles to rank
 * each news source.
 * 
 * @author Thomas Clark
 */
public final class NFPositivityRanking {

    /**
     * Array of news sources
     */
    ArrayList<NFNewsSource> newsSources = new ArrayList<NFNewsSource>();

    /**
     * Array of positive words
     */
    ArrayList<String> positiveWords = new ArrayList<String>();

    /**
     * Array of positive words
     */
    ArrayList<String> negativeWords = new ArrayList<String>();

    /**
     * Constructor for NFNewsSource
     */
    public NFPositivityRanking() {
        //Initialize and fill arrays holding word lists
        SimpleReader positiveIn = new SimpleReader1L(
                "resources/positive_words.txt");
        SimpleReader negativeIn = new SimpleReader1L(
                "resources/negative_words.txt");
        SimpleWriter out = new SimpleWriter1L();
        while (!positiveIn.atEOS()) {
            String word = positiveIn.nextLine();
            this.positiveWords.add(word);
            out.println(word); //TEST
        }
        while (!negativeIn.atEOS()) {
            String word = negativeIn.nextLine();
            this.negativeWords.add(word);
            out.println(word); //TEST
        }
    }
}

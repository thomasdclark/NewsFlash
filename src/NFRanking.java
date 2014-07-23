import java.util.ArrayList;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * The class represents the positivity ranking of a news source.
 * 
 * @author Thomas Clark
 */
public final class NFRanking {

    /**
     * The news source that this ranking is for
     */
    NFNewsSource newsSource;

    /**
     * Array of positive words
     */
    ArrayList<String> positiveWords = new ArrayList<String>();

    /**
     * Array of positive words
     */
    ArrayList<String> negativeWords = new ArrayList<String>();

    /**
     * Variables used to determine criteria for ranking
     */
    int positiveWordCount;
    int negativeWordCount;
    double positiveNegativeRatio;

    /**
     * Constructor for NFNewsSource
     */
    public NFRanking(NFNewsSource newsSource) {
        //Set news source
        this.newsSource = newsSource;
        this.initializeWordLists();
    }

    void initializeWordLists() {
        //Initialize and fill arrays holding word lists
        SimpleReader positiveIn = new SimpleReader1L(
                "resources/positive_words.txt");
        SimpleReader negativeIn = new SimpleReader1L(
                "resources/negative_words.txt");
        SimpleWriter out = new SimpleWriter1L();
        while (!positiveIn.atEOS()) {
            String word = positiveIn.nextLine();
            this.positiveWords.add(word);
        }
        while (!negativeIn.atEOS()) {
            String word = negativeIn.nextLine();
            this.negativeWords.add(word);
        }
    }

    void calculateRanking() {
        //Criteria to determine ranking not yet determined
    }
}

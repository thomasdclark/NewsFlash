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
    int totalWordCount;
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
        this.positiveWordCount = this.totalPositiveWords();
        this.negativeWordCount = this.totalNegativeWords();
        this.totalWordCount = this.totalPositiveWords()
                + this.totalNegativeWords();
    }

    /**
     * Creates list of positive and negative words
     */
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

    /**
     * Counts positive words in an article
     */
    int countPositiveWords(NFNewsArticle newsArticle) {
        int count = 0;
        for (int i = 0; i < this.positiveWords.size(); i++) {
            String positiveWord = this.positiveWords.get(i);
            String description = newsArticle.content;
            while (description.contains(positiveWord)) {
                count++;
                int index = description.indexOf(positiveWord);
                index += positiveWord.length();
                description = description.substring(index);
            }
        }
        return count;
    }

    /**
     * Counts negative words in an article
     */
    int countNegativeWords(NFNewsArticle newsArticle) {
        int count = 0;
        for (int i = 0; i < this.negativeWords.size(); i++) {
            String positiveWord = this.negativeWords.get(i);
            String description = newsArticle.content;
            while (description.contains(positiveWord)) {
                count++;
                int index = description.indexOf(positiveWord);
                index += positiveWord.length();
                description = description.substring(index);
            }
        }
        return count;
    }

    /**
     * Counts total number of positive words out of all articles in a news
     * source
     */
    int totalPositiveWords() {
        int count = 0;
        for (int i = 0; i < this.newsSource.newsArticles.size(); i++) {
            NFNewsArticle newsArticle = this.newsSource.newsArticles.get(i);
            count += this.countPositiveWords(newsArticle);
        }
        return count;
    }

    /**
     * Counts total number of negative words out of all articles in a news
     * source
     */
    int totalNegativeWords() {
        int count = 0;
        for (int i = 0; i < this.newsSource.newsArticles.size(); i++) {
            NFNewsArticle newsArticle = this.newsSource.newsArticles.get(i);
            count += this.countNegativeWords(newsArticle);
        }
        return count;
    }

    /**
     * Creates a number to use to rank this news source against other news
     * sources
     */
    void calculateRanking() {
        //Criteria to determine ranking not yet determined
    }

    /**
     * Override toString() method
     */
    @Override
    public String toString() {
        String toString = "Total Positive Word Count:  "
                + this.positiveWordCount + "\nTotal Negative Word Count:  "
                + this.negativeWordCount
                + "\nTotal Positive & Negative Word Count:  "
                + this.totalWordCount;
        return toString;
    }
}

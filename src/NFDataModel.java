import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The class that holds the data for this application. Holds an array of
 * NewsSources and is able to map them to calculated Rankings.
 * 
 * @author Thomas Clark
 */
public final class NFDataModel {

    /**
     * Array of news sources
     */
    ArrayList<NFNewsSource> newsSources;

    /**
     * A map to map NewsSources to Rankings
     */
    Map<NFNewsSource, NFRanking> sourcesWithRankings;

    /**
     * Constructor for NFNewsSource
     */
    public NFDataModel() {
        this.newsSources = new ArrayList<NFNewsSource>();
        this.sourcesWithRankings = new HashMap<NFNewsSource, NFRanking>();
    }

    /**
     * Constructor for NFNewsSource, where an ArrayList of news sources is given
     * initially. A ranking object is created for each news source object.
     */
    public NFDataModel(ArrayList<NFNewsSource> newsSources) {
        this.newsSources = newsSources;
        this.sourcesWithRankings = new HashMap<NFNewsSource, NFRanking>();
        for (int i = 0; i < this.newsSources.size(); i++) {
            NFRanking ranking = this.rankNewsSource(this.newsSources.get(i));
            this.sourcesWithRankings.put(this.newsSources.get(i), ranking);
        }
    }

    /**
     * Adds news source to model
     */
    void addNewsSource(NFNewsSource newsSource) {
        this.newsSources.add(newsSource);
        NFRanking ranking = this.rankNewsSource(newsSource);
        this.sourcesWithRankings.put(newsSource, ranking);
    }

    /**
     * Creates ranking object for a news source
     */
    NFRanking rankNewsSource(NFNewsSource newsSource) {
        NFRanking ranking = new NFRanking(newsSource);
        return ranking;
    }

    /**
     * Override toString() method
     */
    @Override
    public String toString() {
        String toString = "";
        for (int i = 0; i < this.newsSources.size(); i++) {
            NFNewsSource newsSource = this.newsSources.get(i);
            NFRanking ranking = this.sourcesWithRankings.get(newsSource);
            toString = toString + newsSource.toString() + "\n"
                    + ranking.toString() + "\n\n";
        }
        return toString;
    }
}

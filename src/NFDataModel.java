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

    void addNewsSource(NFNewsSource newsSource) {
        this.newsSources.add(newsSource);
    }

    NFRanking rankNewsSource(NFNewsSource newsSource) {
        NFRanking ranking = new NFRanking(newsSource);
        ranking.calculateRanking();
        return ranking;
    }
}

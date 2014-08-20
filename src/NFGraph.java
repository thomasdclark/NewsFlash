import java.io.File;
import java.util.ArrayList;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

/**
 * This class retrieves the saved data from the /data folder and uses it to
 * create a visual graph
 * 
 * @author Thomas Clark
 */
public final class NFGraph {

    class DataPoint {
        String newsSource;
        double ranking;
    }

    class DataForDate {
        String date;
        Long xValue;
        ArrayList<DataPoint> dataPoints;

        DataForDate() {
            this.dataPoints = new ArrayList<DataPoint>();
        }
    }

    /**
     * Array of news sources
     */
    ArrayList<DataForDate> allDatesData;

    /**
     * Constructor for NFNewsSource
     */
    public NFGraph() {
        this.allDatesData = new ArrayList<DataForDate>();
        File data = new File("data");
        Long lowestXValue;
        File[] listOfFiles = data.listFiles();
        if (listOfFiles.length != 0) {
            for (int i = 0; i < listOfFiles.length; i++) {
                File file = listOfFiles[i];
                DataForDate date = new DataForDate();
                SimpleReader fileIn = new SimpleReader1L(file.toString());
                date.xValue = Long.parseLong(fileIn.nextLine());
                date.date = fileIn.nextLine();
                while (!fileIn.atEOS()) {
                    DataPoint point = new DataPoint();
                    String line = fileIn.nextLine();
                    int index = line.indexOf("*");
                    point.newsSource = line.substring(0, index);
                    point.ranking = Double.parseDouble(line
                            .substring(index + 1));
                    date.dataPoints.add(point);
                }
                this.allDatesData.add(date);
            }
        }
    }
}

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
        long xValueLong;
        int xValue;
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
        long lowestXValue = 0l;
        File[] listOfFiles = data.listFiles();
        if (listOfFiles.length != 0) {
            long fileArray[] = new long[listOfFiles.length];
            for (int i = 0; i < listOfFiles.length; i++) {
                File file = listOfFiles[i];
                fileArray[i] = Long.parseLong(file.getName()
                        .replace(".txt", ""));
            }
            Arrays.sort(fileArray);
            for (int i = 0; i < fileArray.length; i++) {
                DataForDate date = new DataForDate();
                SimpleReader fileIn = new SimpleReader1L("data/" + fileArray[i]
                        + ".txt");
                date.xValueLong = Long.parseLong(fileIn.nextLine());
                if (i == 0) {
                    lowestXValue = date.xValueLong;
                } else if (date.xValue < lowestXValue) {
                    lowestXValue = date.xValueLong;
                }
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
            for (int i = 0; i < this.allDatesData.size(); i++) {
                long xValueLong = this.allDatesData.get(i).xValueLong;
                Long newXValueLong = xValueLong - lowestXValue;
                this.allDatesData.get(i).xValue = newXValueLong.intValue();
            }
        }
    }
}

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.Plots;
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
        String time;
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
     * Array of lines to be plotted
     */
    ArrayList<BarChartPlot> plots;

    /**
     * The date strings in the order that they will appear the the plot
     */
    String dateStrings[];

    /**
     * The maximum ranking in all the data that is to be plotted
     */
    double max;

    /**
     * The number of dates to be plotted. If you want to change this number, you
     * also need to tinker with the chart size in the createBarChart() method to
     * make everything fit into the JFrame
     */
    final int PLOT_DATA_SIZE = 4;

    /**
     * Constructor for NFNewsSource
     */
    public NFGraph() {
        this.allDatesData = new ArrayList<DataForDate>();
        File data = new File("data");
        File[] listOfFiles = data.listFiles();
        if (listOfFiles.length != 0) {
            long fileArray[] = new long[listOfFiles.length];
            for (int i = 0; i < listOfFiles.length; i++) {
                File file = listOfFiles[i];
                fileArray[i] = Long.parseLong(file.getName()
                        .replace(".txt", ""));
            }
            Arrays.sort(fileArray);
            long lowestXValue = fileArray[0];
            for (int i = (fileArray.length - this.PLOT_DATA_SIZE); i < fileArray.length; i++) {
                DataForDate date = new DataForDate();
                SimpleReader fileIn = new SimpleReader1L("data/" + fileArray[i]
                        + ".txt");
                date.xValueLong = Long.parseLong(fileIn.nextLine());
                date.date = fileIn.nextLine();
                date.time = fileIn.nextLine();
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

    /**
     * Creates the individual bar chart plots to be plotted
     */
    public void createBarChartPlots() {
        this.plots = new ArrayList<BarChartPlot>();
        int numberOfDates = this.allDatesData.size();
        int newsSourceSize = this.allDatesData.get(0).dataPoints.size();
        this.dateStrings = new String[numberOfDates];
        for (int i = 0; i < numberOfDates; i++) {
            this.dateStrings[i] = this.allDatesData.get(i).date + " "
                    + this.allDatesData.get(i).time;
        }
        this.max = 0.0;
        for (int i = 0; i < newsSourceSize; i++) {
            for (int j = 0; j < numberOfDates; j++) {
                if (this.allDatesData.get(j).dataPoints.get(i).ranking > this.max) {
                    this.max = this.allDatesData.get(j).dataPoints.get(i).ranking;
                }
            }
        }
        for (int i = 0; i < newsSourceSize; i++) {
            double[] y = new double[numberOfDates];
            String name = this.allDatesData.get(0).dataPoints.get(i).newsSource;
            for (int j = 0; j < numberOfDates; j++) {
                y[j] = this.allDatesData.get(j).dataPoints.get(i).ranking;
            }
            Color color;
            switch (i) {
                case 0:
                    color = Color.RED;
                    break;
                case 1:
                    color = Color.BLUE;
                    break;
                case 2:
                    color = Color.GREEN;
                    break;
                case 3:
                    color = Color.YELLOW;
                    break;
                case 4:
                    color = Color.ORANGE;
                    break;
                case 5:
                    color = Color.MAGENTA;
                    break;
                case 6:
                    color = Color.CYAN;
                    break;
                case 7:
                    color = Color.BLACK;
                    break;
                case 8:
                    color = Color.PINK;
                    break;
                case 9:
                    color = Color.WHITE;
                    break;
                case 10:
                    color = Color.DARKGRAY;
                    break;
                case 11:
                    color = Color.GRAY;
                    break;
                default:
                    color = Color.WHITE;
                    break;
            }
            BarChartPlot plot = Plots.newBarChartPlot(
                    DataUtil.scaleWithinRange(0, this.max, y), color, name);
            this.plots.add(plot);
        }
    }

    /**
     * Creates the bar chart of data
     */
    public String createBarChart() {
        BarChart chart = GCharts.newBarChart(this.plots);

        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.BLACK, 10,
                AxisTextAlignment.CENTER);
        AxisLabels date = AxisLabelsFactory.newAxisLabels("Date", 50.0);
        date.setAxisStyle(AxisStyle.newAxisStyle(Color.BLACK, 12,
                AxisTextAlignment.CENTER));
        AxisLabels dateStrings = AxisLabelsFactory
                .newAxisLabels(this.dateStrings);
        dateStrings.setAxisStyle(AxisStyle.newAxisStyle(Color.BLACK, 10,
                AxisTextAlignment.CENTER));
        AxisLabels ranking = AxisLabelsFactory.newAxisLabels("Ranking", 50.0);
        ranking.setAxisStyle(AxisStyle.newAxisStyle(Color.BLACK, 12,
                AxisTextAlignment.CENTER));
        AxisLabels rankingRange = AxisLabelsFactory.newNumericRangeAxisLabels(
                0, this.max);
        rankingRange.setAxisStyle(axisStyle);

        chart.addXAxisLabels(rankingRange);
        chart.addXAxisLabels(ranking);
        chart.addYAxisLabels(dateStrings);
        chart.addYAxisLabels(date);
        chart.addTopAxisLabels(rankingRange);

        chart.setTitle("New Source Positivity Rankings by Date", Color.BLACK,
                14);
        chart.setHorizontal(true);
        chart.setSize(689, 435);
        chart.setSpaceBetweenGroupsOfBars(10);
        chart.setSpaceWithinGroupsOfBars(0);
        chart.setBarWidth(10);
        chart.setGrid(10.0 / this.max, 600, 3, 2);
        chart.setBackgroundFill(Fills.newSolidFill(Color.WHITE));
        LinearGradientFill fill = Fills.newLinearGradientFill(0,
                Color.LIGHTGREY, 100);
        fill.addColorAndOffset(Color.LIGHTGREY, 0);
        chart.setAreaFill(fill);

        String urlString = chart.toURLString();
        return urlString;
    }

    /**
     * Displays (makes visible) the graph of data
     */
    public void displayPlot() throws IOException {
        this.createBarChartPlots();
        String urlString = this.createBarChart();
        JLabel plot = new JLabel(
                new ImageIcon(ImageIO.read(new URL(urlString))));
        JFrame frame = new JFrame("New Sources Positivity Rankings");
        frame.add(plot);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}

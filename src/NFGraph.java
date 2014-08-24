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
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;
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
     * Array of lines to be plotted
     */
    ArrayList<Line> lines;

    String dateStrings[];

    Double dateStringPoisitons[];

    double max;

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
            for (int i = 0; i < fileArray.length; i++) {
                DataForDate date = new DataForDate();
                SimpleReader fileIn = new SimpleReader1L("data/" + fileArray[i]
                        + ".txt");
                date.xValueLong = Long.parseLong(fileIn.nextLine());
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

    /**
     * Creates the lines to be plotted
     */
    public void createLines() {
        this.lines = new ArrayList<Line>();
        int numberOfDates = this.allDatesData.size();
        int newsSourceSize = this.allDatesData.get(0).dataPoints.size();
        this.dateStrings = new String[numberOfDates];
        this.dateStringPoisitons = new Double[numberOfDates];
        double[] x = new double[numberOfDates];
        for (int i = 0; i < numberOfDates; i++) {
            x[i] = this.allDatesData.get(i).xValue / (1000.0);
            this.dateStrings[i] = this.allDatesData.get(i).date;
            this.dateStringPoisitons[i] = 100.0 * (i / (double) numberOfDates);
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
                    color = Color.PINK;
                    break;
                case 7:
                    color = Color.BLACK;
                    break;
                case 8:
                    color = Color.CYAN;
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
            Line line = Plots.newLine(
                    DataUtil.scaleWithinRange(0, this.max, y), color, name);
            line.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
            line.addShapeMarkers(Shape.DIAMOND, color, 12);
            line.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);
            this.lines.add(line);
        }
    }

    /**
     * Creates the graph of data
     */
    public String createGraph() {
        LineChart chart = GCharts.newLineChart(this.lines);
        chart.setSize(700, 400);
        chart.setTitle("New Sources Positivity Rankings", Color.WHITE, 14);
        chart.setGrid(100, 100, 1, 0);
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
        LinearGradientFill fill = Fills.newLinearGradientFill(0,
                Color.newColor("363433"), 100);
        fill.addColorAndOffset(Color.newColor("2E2B2A"), 0);
        chart.setAreaFill(fill);
        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.WHITE, 10,
                AxisTextAlignment.CENTER);

        AxisLabels xAxis = AxisLabelsFactory.newAxisLabels(
                Arrays.asList(this.dateStrings),
                Arrays.asList(this.dateStringPoisitons));
        xAxis.setAxisStyle(axisStyle);
        chart.addXAxisLabels(xAxis);
        String urlString = chart.toURLString();
        return urlString;
    }

    /**
     * Displays (makes visible) the graph of data
     */
    public void displayGraph() throws IOException {
        this.createLines();
        String urlString = this.createGraph();
        JLabel plot = new JLabel(
                new ImageIcon(ImageIO.read(new URL(urlString))));
        JFrame frame = new JFrame("New Sources Positivity Rankings");
        frame.add(plot);
        frame.pack();
        frame.setVisible(true);
    }
}

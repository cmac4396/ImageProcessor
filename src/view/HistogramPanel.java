package view;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Draws the GUI component that represents the red, green, blue, and intensity value histograms
 * for an image in image processor.
 */
public class HistogramPanel extends JPanel {
  private final List<int[]> redCoords;
  private final List<int[]> greenCoords;
  private final List<int[]> blueCoords;
  private final List<int[]> intensityCoords;
  private static final int PADDING = 30;
  private static final int MAX_VALUE = 255;
  private static final int POINT_RADIUS = 3;

  /**
   * Constructs a HistogramPanel object.
   */
  public HistogramPanel() {
    this.redCoords = new ArrayList<>();
    this.greenCoords = new ArrayList<>();
    this.blueCoords = new ArrayList<>();
    this.intensityCoords = new ArrayList<>();
  }

  /**
   * Constructs a HistogramPanel object.
   * @param redHistogram    the histogram of red components of an image
   * @param greenHistogram  the histogram of green components of an image
   * @param blueHistogram   the histogram of blue components of an image
   * @param intensityHistogram  the histogram of intensity components of an image
   * @throws IllegalArgumentException   if any histogram is null
   */
  public HistogramPanel(Map<Integer, Integer> redHistogram, Map<Integer, Integer> greenHistogram,
                        Map<Integer, Integer> blueHistogram,
                        Map<Integer, Integer> intensityHistogram) {
    // check for null arguments
    if (redHistogram == null || greenHistogram == null || blueHistogram == null
            || intensityHistogram == null) {
      throw new IllegalArgumentException("Error: passed null histogram");
    }

    // convert histogram key-value pairs to list of sorted coordinates
    redCoords = histogramToCoordinates(redHistogram);
    greenCoords = histogramToCoordinates(greenHistogram);
    blueCoords = histogramToCoordinates(blueHistogram);
    intensityCoords = histogramToCoordinates(intensityHistogram);
  }

  // returns the scaled width between each point
  private double getWidthPerSegment() {
    return ((double) getWidth() - 2 * PADDING) / (MAX_VALUE - 1);
  }

  // returns the scaled height between each point
  private double getHeightPerSegment() {
    return ((double) getHeight() - 2 * PADDING) / (this.getMaxValue() - 1);
  }

  // converts a histogram of an image's color values to a sorted (by color value) list of
  // coordinates where x is the color value and y is the frequency of that value within the image
  private static List<int[]> histogramToCoordinates(Map<Integer, Integer> valueHistogram) {
    List<int[]> points = new ArrayList<>();

    // go through all possible color values and add to list of coordinates in ascending order
    // if the key = color value exists in the histogram
    for (int colorValue = 0; colorValue <= 255; colorValue++) {
      if (valueHistogram.containsKey(colorValue)) {
        int[] point = {colorValue, valueHistogram.get(colorValue)};
        points.add(point);
      }
    }

    return points;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D)g;

    // draw x and y axes
    g2D.setStroke(new BasicStroke(3f));
    g2D.drawLine(PADDING, getHeight() - PADDING, PADDING, PADDING);
    g2D.drawLine(PADDING, getHeight() - PADDING, getWidth() - PADDING, getHeight() - PADDING);

    // graph values in lines
    this.drawLines(g2D, this.redCoords, Color.RED);
    this.drawLines(g2D, this.greenCoords, Color.GREEN);
    this.drawLines(g2D, this.blueCoords, Color.BLUE);
    this.drawLines(g2D, this.intensityCoords, Color.BLACK);

    // draw histogram label
    g2D.drawString("RGB and Intensity Values", getWidth() / 2 - g2D.getFont().getSize() * 5,
            getHeight() - PADDING / 2);

  }

  // translates histogram coordinates to panel coordinates
  private int translateXCoordinate(int originalX) {
    return (int) (originalX * getWidthPerSegment() + PADDING) - POINT_RADIUS / 2;
  }

  // translates histogram coordinates to panel coordinates
  private int translateYCoordinate(int originalY) {
    return (int) ((this.getMaxValue() - originalY) * getHeightPerSegment() + PADDING)
            - POINT_RADIUS / 2;
  }

  // finds the maximum y coordinate for this graph
  private int getMaxValue() {
    return Collections.max(new ArrayList<Integer>(Arrays.asList(
            this.maxY(redCoords),
            this.maxY(greenCoords),
            this.maxY(blueCoords),
            this.maxY(intensityCoords))));
  }

  // returns the maximum y-coordinate in the list of coordinates
  private int maxY(List<int[]> coordinates) {
    int max = 0;
    for (int[] point : coordinates) {
      if (point[1] > max) {
        max = point[1];
      }
    }

    return max;
  }

  // draws lines connecting the points with color given
  private void drawLines(Graphics2D g2D, List<int[]> points, Color lineColor) {
    g2D.setColor(lineColor);
    g2D.setStroke(new BasicStroke(2f));

    for (int pointIndex = 0; pointIndex < points.size() - 1; pointIndex++) {
      int fromX = translateXCoordinate(points.get(pointIndex)[0]);
      int fromY = translateYCoordinate(points.get(pointIndex)[1]);

      int toX = translateXCoordinate(points.get(pointIndex + 1)[0]);
      int toY = translateYCoordinate(points.get(pointIndex + 1)[1]);

      g2D.drawLine(fromX, fromY, toX, toY);
    }
  }
}

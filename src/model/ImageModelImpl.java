package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents an implementation of the model.ImageModel interface, which contains a 2D list of
 * pixels that represent an image. It also contains the name of the image and the maximum RGB
 * value of the image as its fields.
 */
public class ImageModelImpl implements ImageModel {
  private final List<List<Pixel>> pixels;
  private final String name;
  private final int maxRGBValue;

  /**
   * Constructs an ImageModelImpl object.
   * @param pixels                      a grid of its pixels
   * @param name                        the name of this image
   * @param maxRGBValue                 the maximum RGB value of this image
   * @throws IllegalArgumentException   when max RGB value is invalid or name is empty
   */
  public ImageModelImpl(List<List<Pixel>> pixels, String name, int maxRGBValue) {
    // check if name is valid
    if (invalidName(name)) {
      throw new IllegalArgumentException("error: empty name");
    }

    // check if maxRBGValue is valid
    if (maxRGBValue < 0 || maxRGBValue > 255) {
      throw new IllegalArgumentException("error: invalid maximum RBG value");
    }

    this.name = name;

    this.maxRGBValue = maxRGBValue;

    this.pixels = new ArrayList<>(pixels.size());

    for (List<Pixel> pixelRow : pixels) {
      List<Pixel> thisPixelRow = new ArrayList<>();
      for (Pixel pixel : pixelRow) {
        // use model.Pixel copy constructor to copy pixel values
        try {
          thisPixelRow.add(new Pixel(pixel));
        }
        catch (NullPointerException e) {
          throw new IllegalArgumentException("error: null pixel given");
        }
      }

      this.pixels.add(thisPixelRow);
    }
  }

  /**
   * Constructs an ImageModelImpl object.
   * @param pixels                      a grid of the image's pixels
   * @param name                        the name of the image
   * @throws IllegalArgumentException   when max RGB value is invalid or name is empty
   */
  public ImageModelImpl(List<List<Pixel>> pixels, String name) {
    this(pixels, name, 255);
  }

  // returns true if given String is NOT a valid name
  protected static boolean invalidName(String name) {
    return name == null || name.strip().equals("");
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getWidth() {
    return this.pixels.get(0).size();
  }

  @Override
  public int getHeight() {
    return this.pixels.size();
  }

  @Override
  public int getMaxRGB() {
    return this.maxRGBValue;
  }

  @Override
  public List<List<Pixel>> getPixels() {
    return this.pixels;
  }

  @Override
  public ImageModel getImageAllRed(String name) {
    return this.changeAllPixels(name, Pixel.allRed());
  }

  @Override
  public ImageModel getImageAllBlue(String name) {
    return this.changeAllPixels(name, Pixel.allBlue());
  }

  @Override
  public ImageModel getImageAllGreen(String name) {
    return this.changeAllPixels(name, Pixel.allGreen());
  }

  @Override
  public ImageModel getImageAllValue(String name) {
    return this.changeAllPixels(name, Pixel.allValue());
  }

  @Override
  public ImageModel getImageAllIntensity(String name) {
    return this.changeAllPixels(name, Pixel.allIntensity());
  }

  @Override
  public ImageModel getImageAllLuma(String name) {
    return this.changeAllPixels(name, Pixel.allLuma());
  }

  @Override
  public ImageModel getImageFlipHorizontal(String name) {
    List<List<Pixel>> flippedPixels = new ArrayList<>();

    // create default list of pixels
    for (List<Pixel> pixelRow : pixels) {
      List<Pixel> thisPixelRow = new ArrayList<>();
      for (Pixel pixel : pixelRow) {
        // use model.Pixel copy constructor to copy pixel values
        thisPixelRow.add(new Pixel(0, 0, 0));
      }

      flippedPixels.add(thisPixelRow);
    }

    // change x and y positions of pixels to get vertical flip effect
    for (int row = 0; row < this.pixels.size(); row++) {
      int pixelsHeight = this.pixels.get(row).size();
      for (int col = 0; col < pixelsHeight; col++) {
        Pixel currentPixel = this.pixels.get(row).get(col);
        flippedPixels.get(row).set(pixelsHeight - col - 1, currentPixel);
      }
    }

    return new ImageModelImpl(flippedPixels, name, maxRGBValue);
  }

  @Override
  public ImageModel getImageFlipVertical(String name) {
    List<List<Pixel>> flippedPixels = new ArrayList<>(this.pixels.size());

    // create default list of pixels
    for (List<Pixel> pixelRow : pixels) {
      List<Pixel> thisPixelRow = new ArrayList<>();
      for (Pixel pixel : pixelRow) {
        // use model.Pixel copy constructor to copy pixel values
        thisPixelRow.add(new Pixel(0, 0, 0));
      }

      flippedPixels.add(thisPixelRow);
    }

    int pixelsLength = this.pixels.size();

    // change x and y positions of pixels to get horizontal flip effect
    for (int row = 0; row < pixelsLength; row++) {
      for (int col = 0; col < this.pixels.get(row).size(); col++) {
        Pixel currentPixel = this.pixels.get(row).get(col);
        flippedPixels.get(pixelsLength - row - 1).set(col, currentPixel);
      }
    }

    return new ImageModelImpl(flippedPixels, name, maxRGBValue);
  }

  @Override
  public ImageModel getImageAdjustBrightness(String name, int adjust) {
    try {
      return this.changeAllPixels(name,
              new Pixel(0, 0, 0).allAdjustBrightness(adjust, this.maxRGBValue));
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("error: invalid increment");
    }
  }

  // adjusts a value that may be invalid to fit within RGB restraints
  protected int adjustValue(int channelValue) {
    if (channelValue < 0) {
      channelValue = 0;
    }

    if (channelValue > maxRGBValue) {
      channelValue = maxRGBValue;
    }

    return channelValue;
  }

  @Override
  public ImageModel applyFilter(String name, double[][] kernel) {
    List<List<Pixel>> filteredPixels = new ArrayList<>(this.pixels.size());
    // return null if the kernel's width or height is not odd
    if (kernel.length % 2 == 0 || kernel[0].length == 0) {
      return null;
    }

    int kernelCenter = kernel.length / 2;

    for (int row = 0; row < this.getHeight(); row++) {
      List<Pixel> pixelRow = new ArrayList<>(this.getWidth());
      for (int col = 0; col < this.getWidth(); col++) {
        double newRed = 0;
        double newGreen = 0;
        double newBlue = 0;

        for (int kernelRow = 0; kernelRow < kernel.length; kernelRow++) {
          for (int kernelCol = 0; kernelCol < kernel[kernelRow].length; kernelCol++) {
            // find the relative position of the current kernel value and use to find the position
            // of the corresponding neighbor pixel in the image
            int differenceX = kernelRow - kernelCenter;
            int differenceY = kernelCol - kernelCenter;
            // if pixel with given position does not exist in image, look for next neighbor
            Pixel neighborPixel;
            try {
              neighborPixel = this.pixels.get(row + differenceX).get(col + differenceY);
            }
            catch (IndexOutOfBoundsException e) {
              continue;
            }

            // add pixel values adjusted by kernel
            double kernelValue = kernel[kernelRow][kernelCol];
            newRed += kernelValue * neighborPixel.getRed();
            newGreen += kernelValue * neighborPixel.getGreen();
            newBlue += kernelValue * neighborPixel.getBlue();
          }
        }

        int adjustedRed = this.adjustValue((int) newRed);
        int adjustedGreen = this.adjustValue((int) newGreen);
        int adjustedBlue = this.adjustValue((int) newBlue);
        // truncate double values to integer RGB values
        pixelRow.add(new Pixel(adjustedRed, adjustedGreen, adjustedBlue));
      }
      filteredPixels.add(pixelRow);
    }

    return new ImageModelImpl(filteredPixels, name, this.maxRGBValue);
  }

  @Override
  public ImageModel applyColorTransformation(String name, double[][] kernel) {
    if (invalidName(name)) {
      // will throw illegal argument exception with name error message
      return new ImageModelImpl(this.pixels, "", maxRGBValue);
    }

    try {
      return this.changeAllPixels(name,
              new Pixel(0, 0, 0).applyColorTransformation(kernel));
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("error: provided invalid kernel");
    }
  }

  @Override
  public Map<Integer, Integer> getHistogram(Function<Pixel, Integer> func) {
    Map<Integer, Integer> histogram = new HashMap<Integer, Integer>();
    for (List<Pixel> pixelRow: this.pixels) {
      for (Pixel pixel : pixelRow) {
        int color_value = func.apply(pixel);
        if (histogram.containsKey(color_value)) {
          int frequency = histogram.get(color_value);
          histogram.put(color_value, frequency + 1);
        }
        else {
          histogram.put(color_value, 1);
        }
      }
    }
    return histogram;
  }

  // changes all pixels to a single value and set to model.ImageModel with new name
  protected ImageModel changeAllPixels(String name, Function<Pixel, Pixel> pixelChange) {
    if (invalidName(name)) {
      // will throw illegal argument exception with name error message
      return new ImageModelImpl(this.pixels, "", maxRGBValue);
    }

    List<List<Pixel>> newPixels = new ArrayList<>(this.pixels.size());

    // create default list of pixels
    for (List<Pixel> pixelRow : pixels) {
      List<Pixel> newPixelRow = new ArrayList<>();
      for (Pixel pixel : pixelRow) {
        // use model.Pixel copy constructor to copy pixel values
        newPixelRow.add(new Pixel(0, 0, 0));
      }

      newPixels.add(newPixelRow);
    }

    for (int row = 0; row < pixels.size(); row++) {
      for (int col = 0; col < pixels.get(row).size(); col++) {
        Pixel currentPixel = pixels.get(row).get(col);

        // set pixel in row and column to value after applying pixelChange
        newPixels.get(row).set(col, pixelChange.apply(currentPixel));
      }
    }

    return new ImageModelImpl(newPixels, name, maxRGBValue);
  }
}

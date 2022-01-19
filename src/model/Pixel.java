package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a pixel in an image with RGB colors.
 */
public class Pixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a model.Pixel object.
   * @param red                         the red component of the pixel's RGB value
   * @param green                       the green component of the pixel's RGB value
   * @param blue                        the blue component of the pixel's RGB value
   * @throws IllegalArgumentException   when one component is not between 0 and 255 inclusive
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    // if component is not a valid RGB value, throw exception
    if (componentInvalid(red) || componentInvalid(green) || componentInvalid(blue)) {
      throw new IllegalArgumentException("error: one component is invalid");
    }

    // set values
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Constructs a pixel object.
   *
   * @param pixel                   the pixel with values that will be copied into this pixel
   * @throws NullPointerException   when pixel is null
   */
  public Pixel(Pixel pixel) {
    // if pixel is null, throw exception
    this(Objects.requireNonNull(pixel, "error: copying null pixel").getRed(),
            pixel.getGreen(), pixel.getBlue());
  }

  // returns true if the component is NOT a valid RGB value
  protected static boolean componentInvalid(int component) {
    return component < 0 || component > 255;
  }

  /**
   * Gets this pixel's red component.
   *
   * @return  the pixel's red component of its RGB value
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Gets this pixel's green component.
   *
   * @return  the pixel's green component of its RGB value
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Gets this pixel's blue component.
   *
   * @return  the pixel's blue component of its RGB value
   */
  public int getBlue() {
    return this.blue;
  }

  /**
   * Gets the pixel's value.
   *
   * @return  the maximum value of the pixel's individual components
   */
  public int getValue() {
    return Collections.max(Arrays.asList(this.red, this.green, this.blue));
  }

  /**
   * Gets the pixel's intensity.
   *
   * @return  the average value of the pixel's individual components
   */
  public int getIntensity() {
    int sum = this.red + this.green + this.blue;
    return sum / 3;
  }

  /**
   * Gets the pixel's luma.
   *
   * @return    the truncated value of the luma calculated based on the pixel's RGB components
   */
  public int getLuma() {
    return (int) (Math.floor(0.2126 * this.red + 0.7152 * this.green + 0.0722 * this.blue));
  }

  /**
   * Returns a function that sets all components of a pixel to a given value.
   *
   * @return    a function that sets pixels to a certain value
   */
  public static Pixel setComponentsTo(int value) {
    return new Pixel(value, value, value);
  }

  /**
   * Returns a function that sets all components of a pixel to its red value.
   *
   * @return function that sets all components of a pixel to its red value.
   */
  public static Function<Pixel, Pixel> allRed() {
    return new Function<Pixel, Pixel>() {
      @Override public Pixel apply(Pixel pixel) {
        return pixel.setComponentsTo(pixel.getRed());
      }
    };
  }

  /**
   * Returns a function that sets all components of a pixel to its green value.
   *
   * @return function that sets all components of a pixel to its green value.
   */
  public static Function<Pixel, Pixel> allGreen() {
    return new Function<Pixel, Pixel>() {
      @Override public Pixel apply(Pixel pixel) {
        return pixel.setComponentsTo(pixel.getGreen());
      }
    };
  }

  /**
   * Returns a function that sets all components of a pixel to its blue value.
   *
   * @return function that sets all components of a pixel to its blue value.
   */
  public static Function<Pixel, Pixel> allBlue() {
    return new Function<Pixel, Pixel>() {
      @Override public Pixel apply(Pixel pixel) {
        return pixel.setComponentsTo(pixel.getBlue());
      }
    };
  }

  /**
   * Returns a function that sets all components of a pixel to its maximum value.
   *
   * @return function that sets all components of a pixel to its maximum value.
   */
  public static Function<Pixel, Pixel> allValue() {
    return new Function<Pixel, Pixel>() {
      @Override public Pixel apply(Pixel pixel) {
        return pixel.setComponentsTo(pixel.getValue());
      }
    };
  }

  /**
   * Returns a function that sets all components of a pixel to its average value.
   *
   * @return function that sets all components of a pixel to its average value.
   */
  public static Function<Pixel, Pixel> allIntensity() {
    return new Function<Pixel, Pixel>() {
      @Override public Pixel apply(Pixel pixel) {
        return pixel.setComponentsTo(pixel.getIntensity());
      }
    };
  }

  /**
   * Returns a function that sets all components of a pixel to its luma value.
   *
   * @return function that sets all components of a pixel to its luma value.
   */
  public static Function<Pixel, Pixel> allLuma() {
    return new Function<Pixel, Pixel>() {
      @Override public Pixel apply(Pixel pixel) {
        return pixel.setComponentsTo(pixel.getLuma());
      }
    };
  }

  /**
   * Returns a function that increments/decrements all components of a pixel based on the given
   * integer.
   *
   * @return function that increments/decrements all components of a pixel
   */
  public Function<Pixel, Pixel> allAdjustBrightness(int adjust, int maxRGBValue) {
    return new Function<Pixel, Pixel>() {
      @Override public Pixel apply(Pixel pixel) {
        int newRed = pixel.getRed() + adjust;
        int newGreen = pixel.getGreen() + adjust;
        int newBlue = pixel.getBlue() + adjust;

        // if adjusted values go beyond threshold, set to closest acceptable value
        if (newRed > maxRGBValue) {
          newRed = maxRGBValue;
        }

        if (newGreen > maxRGBValue) {
          newGreen = maxRGBValue;
        }

        if (newBlue > maxRGBValue) {
          newBlue = maxRGBValue;
        }

        if (newRed < 0) {
          newRed = 0;
        }

        if (newGreen < 0) {
          newGreen = 0;
        }

        if (newBlue < 0) {
          newBlue = 0;
        }

        return new Pixel(newRed, newGreen, newBlue);
      }
    };
  }

  /**
   * Applies a color transformation to this pixel.
   * @param kernel                      the kernel used to apply the color transformation
   * @return                            a function that applies the color transformation to a pixel
   *                                    with the specified kernel
   * @throws IllegalArgumentException   if kernel's dimensions are not odd
   */
  public Function<Pixel, Pixel> applyColorTransformation(double[][] kernel)
          throws IllegalArgumentException {
    return new Function<Pixel, Pixel>() {
      // kernel has to be 3x3
      @Override
      public Pixel apply(Pixel pixel) {
        if (kernel.length != 3 || kernel[0].length != 3) {
          throw new IllegalArgumentException("error: given kernel must be 3x3");
        }

        double rgbRed = 0;
        double rgbGreen = 0;
        double rgbBlue = 0;
        for (int i = 0; i < kernel.length; i++) {
          if (i == 0) {
            rgbRed = kernel[i][0] * pixel.getRed() + kernel[i][1] * pixel.getGreen()
                    + kernel[i][2] * pixel.getBlue();
          }
          else if (i == 1) {
            rgbGreen = kernel[i][0] * pixel.getRed() + kernel[i][1] * pixel.getGreen()
                    + kernel[i][2] * pixel.getBlue();
          }
          else if (i == 2) {
            rgbBlue = kernel[i][0] * pixel.getRed() + kernel[i][1] * pixel.getGreen()
                    + kernel[i][2] * pixel.getBlue();
          }
        }

        int adjustedRed = Pixel.adjustValue((int) Math.floor(rgbRed));
        int adjustedGreen = Pixel.adjustValue((int) Math.floor(rgbGreen));
        int adjustedBlue = Pixel.adjustValue((int) Math.floor(rgbBlue));
        return new Pixel(adjustedRed, adjustedGreen, adjustedBlue);
      }
    };
  }

  /**
   * Adjusts a value to fit within RGB contraints.
   * @param channelValue    the integer representation of a RGB channel
   * @return                the channel value using the closest values between 0 and 255 inclusive
   */
  public static int adjustValue(int channelValue) {
    if (channelValue < 0) {
      channelValue = 0;
    }

    if (channelValue > 255) {
      channelValue = 255;
    }

    return channelValue;
  }

  /**
   * Returns a function that gets the red value of the pixel.
   * @return function that gets the red value of the given pixel
   */
  public static Function<Pixel, Integer> getRedFunc() {
    return new Function<Pixel, Integer>() {
      @Override
      public Integer apply(Pixel pixel) {
        return pixel.getRed();
      }
    };
  }

  /**
   * Returns a function that gets the green value of the pixel.
   * @return function that gets the green value of the given pixel
   */
  public static Function<Pixel, Integer> getGreenFunc() {
    return new Function<Pixel, Integer>() {
      @Override
      public Integer apply(Pixel pixel) {
        return pixel.getGreen();
      }
    };
  }

  /**
   * Returns a function that gets the blue value of the pixel.
   * @return function that gets the blue value of the given pixel
   */
  public static Function<Pixel, Integer> getBlueFunc() {
    return new Function<Pixel, Integer>() {
      @Override
      public Integer apply(Pixel pixel) {
        return pixel.getBlue();
      }
    };
  }

  /**
   * Returns a function that gets the intensity value of the pixel.
   * @return function that gets the intensity value of the given pixel
   */
  public static Function<Pixel, Integer> getIntensityFunc() {
    return new Function<Pixel, Integer>() {
      @Override
      public Integer apply(Pixel pixel) {
        return pixel.getIntensity();
      }
    };
  }


}

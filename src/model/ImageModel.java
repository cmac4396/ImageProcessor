package model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * This interface represents the image model which processes an image based on the methods called
 * to it.
 */
public interface ImageModel {
  /**
   * Convenience method used for testing.
   *
   * @return this image's list of pixels
   */
  List<List<Pixel>> getPixels();

  /**
   * Returns the name of this model.ImageModel.
   *
   * @return the name of this model.ImageModel
   */
  String getName();

  /**
   * Returns the width in pixels of this model.ImageModel.
   *
   * @return the width in pixels of this model.ImageModel
   */
  int getWidth();

  /**
   * Returns the height in pixels of this model.ImageModel.
   *
   * @return the height in pixels of this model.ImageModel
   */
  int getHeight();

  /**
   * Returns the max RGB value of this model.ImageModel.
   *
   * @return the max RGB value  of this model.ImageModel
   */
  int getMaxRGB();

  /**
   * Visualizes the red component of the pixels of the image.
   *
   * @param name                        the name of the new image
   * @return                            a processed image that has been greyscaled and highlights
   *                                    the red components of the image with the given name
   * @throws IllegalArgumentException   if name is invalid
   */
  ImageModel getImageAllRed(String name);

  /**
   * Visualizes the blue component of the pixels of the image.
   *
   * @param name                        the name of the new image
   * @return                            a processed image that has been greyscaled and highlights
   *                                    the blue components of the image with the given name
   * @throws IllegalArgumentException   if name is invalid
   */
  ImageModel getImageAllBlue(String name);

  /**
   * Visualizes the green component of the pixels of the image.
   *
   * @param name                        the name of the new image
   * @return                            a processed image that has been greyscaled and highlights
   *                                    the green components of the image with the given name
   * @throws IllegalArgumentException   if name is invalid
   */
  ImageModel getImageAllGreen(String name);

  /**
   * Measures brightness/intensity by visualizing the maximum value of each pixel's RGB.
   *
   * @param name                        the name of the new image
   * @return                            a processed image in which the value has been visualized to
   *                                    create a grayscale with the given name
   * @throws IllegalArgumentException   if name is invalid
   */
  ImageModel getImageAllValue(String name);

  /**
   * Measures brightness/intensity by visualizing the average of each pixel's RGB.
   *
   * @param name                        the name of the new image
   * @return                            a processed image in which the intensity has been
   *                                    visualized to create a grayscale
   * @throws IllegalArgumentException   if name is invalid
   */
  ImageModel getImageAllIntensity(String name);

  /**
   * Measures brightness/intensity by visualizing the weighted sum of each pixel's RGB.
   *
   * @param name                        the name of the new image
   * @return                            a processed image in which the luma has been visualized to
   *                                    create a grayscale
   * @throws IllegalArgumentException   if name is invalid
   */
  ImageModel getImageAllLuma(String name);

  /**
   * Flips an image horizontally.
   *
   * @param name the name of the new image
   * @return a horizontally flipped version of the image
   * @throws IllegalArgumentException if name is invalid
   */
  ImageModel getImageFlipHorizontal(String name);

  /**
   * Flips an image vertically.
   *
   * @param name the name of the new image
   * @return a vertically flipped version of the image
   * @throws IllegalArgumentException if name is invalid
   */
  ImageModel getImageFlipVertical(String name);

  /**
   * Brightens or darkens an image based on the given increment.
   *
   * @param name   the name of the new image
   * @param adjust the increment used to brighten or darken the image by
   *               positive increment brightens the image
   *               negative increment darkens the image
   * @return an image with the brightness adjusted
   * @throws IllegalArgumentException when the given integer results in an invalid pixel
   */
  ImageModel getImageAdjustBrightness(String name, int adjust);

  /**
   * Applies the given kernel to all pixels of the image.
   *
   * @param name   the name of the new image
   * @param kernel matrix to apply to the image
   * @return a new image with the kernel applied
   */
  ImageModel applyFilter(String name, double[][] kernel);

  /**
   * Multiplies the given kernel by the RGB values of the image.
   *
   * @param name   the name of the new image
   * @param kernel matrix to multiply the image pixels by
   * @return a new image with the kernel multiplies
   */
  ImageModel applyColorTransformation(String name, double[][] kernel);

  /**
   * Constructs a map containing the entries of the image.
   * @return a map containing the (value, frequency) entries of each pixel of the image
   */
  Map<Integer, Integer> getHistogram(Function<Pixel, Integer> func);
}
package model;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Represents all the write operations of an image processor, including adding images, integrating
 * with the file system, and performing image operations.
 */
public interface ProcessorModel {
  /**
   * Adds this image to the images being managed by the processor.
   * @param image   the image to be added
   */
  void addImage(ImageModel image);

  /**
   * Gets the image with the given name from the processor.
   * @param name    the name of the image
   * @return        the image in the processor with the same name as the one given
   * @throws        IllegalArgumentException if image with that name is not found
   */
  ImageModel getImage(String name);

  /**
   * Gets the name of the image that was last edited or loaded in the processor.
   * @return the name of the last image edited or loaded in the processor.
   */
  String getNameLastEdited();

  /**
   * Returns a map of image red component values and their frequencies for the image with the
   * given name.
   * @param imageName                   the name of the image to get the histogram from
   * @return                            the red component histogram of the image with given name
   * @throws IllegalArgumentException   when there is no image with the given name in the processor
   */
  Map<Integer, Integer> getRedHistogram(String imageName);

  /**
   * Returns a map of image green component values and their frequencies for the image with the
   * given name.
   * @param imageName                   the name of the image to get the histogram from
   * @return                            the green component histogram of the image with given name
   * @throws IllegalArgumentException   when there is no image with the given name in the processor
   */
  Map<Integer, Integer> getGreenHistogram(String imageName);

  /**
   * Returns a map of image blue component values and their frequencies for the image with the
   * given name.
   * @param imageName                   the name of the image to get the histogram from
   * @return                            the blue component histogram of the image with given name
   * @throws IllegalArgumentException   when there is no image with the given name in the processor
   */
  Map<Integer, Integer> getBlueHistogram(String imageName);

  /**
   * Returns a map of image intensity values and their frequencies for the image with the
   * given name.
   * @param imageName                   the name of the image to get the histogram from
   * @return                            the intensity histogram of the image with given name
   * @throws IllegalArgumentException   when there is no image with the given name in the processor
   */
  Map<Integer, Integer> getIntensityHistogram(String imageName);

  /**
   * Loads the image at a specific path into this processor.
   *
   * @param imagePath                     the path specifying the location of the image to be loaded
   * @param imageName                     the name of the image when it is stored in the processor
   * @throws IllegalArgumentException     when the imagePath does not exist or the image is
   *                                      formatted incorrectly (not PPM version 3)
   */
  void load(String imagePath, String imageName);

  /**
   * Saves the image in the processor at a specified path.
   *
   * @param imagePath                   the path specifying the location where the image will be
   *                                    saved
   * @param imageName                   the name of the image in the processor to be saved
   * @throws IllegalArgumentException   when there is no image with given name in the processor
   * @throws IllegalStateException      when the processor fails to write to the image path
   */
  void save(String imagePath, String imageName);

  /**
   * Returns a BufferedImage version of the most recently added image in the processor.
   * @return          the BufferedImage version of the latest image in the processor
   */
  BufferedImage getCurrentImage();

  /**
   * Visualizes the red component of the given image in the image processor in a new image
   * with given destination image name.
   * @param imageName                    the name of the original image in the processor
   * @param destImageName                the name of the original image visualized with its red
   *                                     component
   * @throws IllegalArgumentException    when there is no image with that given name in the
   *                                     processor
   */
  void redComponent(String imageName, String destImageName);

  /**
   * Visualizes the green component of the given image in the image processor in a new image
   * with given destination image name.
   * @param imageName                    the name of the original image in the processor
   * @param destImageName                the name of the original image visualized with its green
   *                                     component
   * @throws IllegalArgumentException    when there is no image with that given name in the
   *                                     processor
   */
  void greenComponent(String imageName, String destImageName);

  /**
   * Visualizes the blue component of the given image in the image processor in a new image
   * with given destination image name.
   * @param imageName                    the name of the original image in the processor
   * @param destImageName                the name of the original image visualized with its blue
   *                                     component
   * @throws IllegalArgumentException    when there is no image with that given name in the
   *                                     processor
   */
  void blueComponent(String imageName, String destImageName);

  /**
   * Visualizes the maximum value of each pixel in the given image in the image processor in a new
   * image with given destination image name.
   * @param imageName                   the name of the original image in the processor
   * @param destImageName               the name of the resulting image using the original's max
   *                                    values
   *
   * @throws IllegalArgumentException   when there is no image with that given name in the processor
   */
  void valueComponent(String imageName, String destImageName);

  /**
   * Visualizes the average value of each pixel in the given image in the image processor in a new
   * image with given destination image name.
   * @param imageName                   the name of the original image in the processor
   * @param destImageName               the name of the resulting image using the original's average
   *                                    values
   *
   * @throws IllegalArgumentException   when there is no image with that given name in the processor
   */
  void intensityComponent(String imageName, String destImageName);

  /**
   * Visualizes the luma of each pixel in the given image in the image processor in a new
   * image with given destination image name.
   * @param imageName                   the name of the original image in the processor
   * @param destImageName               the name of the resulting image using the original's luma
   *                                    values
   *
   * @throws IllegalArgumentException   when there is no image with that given name in the processor
   */
  void lumaComponent(String imageName, String destImageName);

  /**
   * Flips the image vertically and saves it to a new name in the image processor.
   * @param imageName                    the name of the original image in the processor
   * @param destImageName                the name of the original image after performing vertical
   *                                     flip
   * @throws IllegalArgumentException    when there is no image with that given name in the
   *                                     processor
   */
  void verticalFlip(String imageName, String destImageName);

  /**
   * Flips the image horizontally and saves it to a new name in the image processor.
   * @param imageName                    the name of the original image in the processor
   * @param destImageName                the name of the original image after performing horizontal
   *                                     flip
   * @throws IllegalArgumentException    when there is no image with that given name in the
   *                                     processor
   */
  void horizontalFlip(String imageName, String destImageName);

  /**
   * Adjusts the brightness of the image and saves it to a new name in the image processor.
   * @param increment                    the number to adjust an image's RGB values by
   * @param imageName                    the name of the original image in the processor
   * @param destImageName                the name of the original image after performing horizontal
   *                                     flip
   * @throws IllegalArgumentException    when there is no image with that given name in the
   *                                     processor
   */
  void brighten(int increment, String imageName, String destImageName);

  /**
   * Blurs the image given and saves it under another name in the image processor.
   * @param imageName                   the image that will be blurred
   * @param destImageName               the name of the blurred image
   * @throws IllegalArgumentException   when there is no image with the given name in the processor
   */
  void blur(String imageName, String destImageName);

  /**
   * Sharpens the image given and saves it under another name in the image processor.
   * @param imageName                   the image that will be sharpened
   * @param destImageName               the name of the sharpened image
   * @throws IllegalArgumentException   when there is no image with the given name in the processor
   */
  void sharpen(String imageName, String destImageName);

  /**
   * Converts the image to a greyscale image, composed only of grey shades, and saves it under
   * another name in the image processor.
   * @param imageName                   the image that will be sharpened
   * @param destImageName               the name of the sharpened image
   * @throws IllegalArgumentException   when there is no image with the given name in the processor
   */
  void greyscale(String imageName, String destImageName);

  /**
   * Converts the image to a sepia toned image and saves it under another name in the image
   * processor.
   * @param imageName                   the image that will be sharpened
   * @param destImageName               the name of the sharpened image
   * @throws IllegalArgumentException   when there is no image with the given name in the processor
   */
  void sepia(String imageName, String destImageName);
}

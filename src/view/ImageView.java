package view;

import java.io.IOException;

/**
 * This interface represents the image view which is responsible for the conversion of a model
 * to a different format.
 */
public interface ImageView {

  /**
   * Converts an model.ImageModel object into a file.
   * @param filePath                the location where the image will be saved
   * @throws IOException    when there is a problem writing to the format medium
   */
  void writeToFile(String filePath) throws IOException;
}
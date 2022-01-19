package view;

import java.io.IOException;

/**
 * Throws an IOException when any method of this view is called.
 */
public class ImageViewIOException implements ImageView {

  /**
   * Always throws an IOException.
   *
   * @param fileName        name of file that is supposed
   * @throws IOException    when this method is called
   */
  @Override
  public void writeToFile(String fileName) throws IOException {
    throw new IOException("IOException thrown from mock ImageViewException");
  }
}

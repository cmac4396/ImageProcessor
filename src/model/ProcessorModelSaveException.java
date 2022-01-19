package model;

import view.ImageView;
import view.ImageViewIOException;

/**
 * A mock processor model that throws an IOException in save but otherwise has
 * the same functionality.
 */
public class ProcessorModelSaveException extends ProcessorModelImpl {
  public ProcessorModelSaveException() {
    super();
  }

  @Override
  protected ImageView getImageView(String imageName) {
    return new ImageViewIOException();
  }
}

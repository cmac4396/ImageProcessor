package view;

import java.io.IOException;

/**
 * This interface represents operations that should be offered by
 * a view for the image processor.
 */
public interface ProcessorView {
  /**
   * Render a specific message to the provided data destination.
   * @param message message the message to be transmitted
   * @throws IOException if transmission of the file to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;
}

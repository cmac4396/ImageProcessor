package view;

import java.io.IOException;

/**
 * Represents a view implementation of the view.ProcessorView
 */
public class ProcessorViewImpl implements ProcessorView {
  private final Appendable out;

  /**
   * Constructs a view.ProcessorViewImpl with the given model
   */
  public ProcessorViewImpl() {
    this(System.out);
  }

  /**
   * Constructs a view.ProcessorViewImpl with the given model and Appendable
   * @param out                         output of processor
   * @throws IllegalArgumentException   when out is null
   */
  public ProcessorViewImpl(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("error: null appendable");
    }
    this.out = out;
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message message the message to be transmitted
   * @throws IOException if transmission of the file to the provided data destination fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message);
  }
}

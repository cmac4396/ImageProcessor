package view;

import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * A mock view for the image processor GUI to verify that communication between the
 * model, view, and controller is correct.
 */
public class MockGUIView implements ProcessorGUIView {
  private final Appendable out;

  /**
   * Constructs a MockGUIView object.
   * @param out   the out stream where all calls to this view will be logged
   */
  public MockGUIView(Appendable out) {
    if (out == null) {
      this.out = new StringBuilder("");
    }
    else {
      this.out = out;
    }
  }

  @Override
  public void showView(boolean show) {
    try {
      this.out.append("Called showView\n");
    }
    catch (IOException e) {
      throw new IllegalStateException("Error: mock view failed to log message");
    }
  }

  @Override
  public void refresh() {
    try {
      this.out.append("Called refresh\n");
    }
    catch (IOException e) {
      throw new IllegalStateException("Error: mock view failed to log message");
    }
  }

  @Override
  public void updateImage(String newImageName) {
    try {
      this.out.append("Called imagePath with argument \n");
    }
    catch (IOException e) {
      throw new IllegalStateException("Error: mock view failed to log message");
    }
  }

  @Override
  public void createMenuBar(ActionListener actionListener) {
    try {
      this.out.append("Called createMenuBar\n");
    }
    catch (IOException e) {
      throw new IllegalStateException("Error: mock view failed to log message");
    }
  }

  @Override
  public int getBrightenIncrement() {
    try {
      this.out.append("Called getBrightenIncrement\n");
    }
    catch (IOException e) {
      throw new IllegalStateException("Error: mock view failed to log message");
    }

    throw new IllegalArgumentException("Using mock view");
  }

  @Override
  public String getPathToOpen() {
    try {
      this.out.append("Called getPathToOpen\n");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Error: mock view failed to log message");
    }

    return null;
  }

  @Override
  public String getPathToSave() {
    try {
      this.out.append("Called getPathToSave\n");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Error: mock view failed to log message");
    }

    return null;
  }

  @Override
  public void displaySaveError() {
    try {
      this.out.append("Called displaySaveError\n");
    }
    catch (IOException e) {
      throw new IllegalStateException("Error: mock view failed to log message");
    }
  }
}

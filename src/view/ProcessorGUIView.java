package view;

import java.awt.event.ActionListener;

/**
 * This interface represents operations that should be offered by a view for the GUI of the image
 * processor.
 */
public interface ProcessorGUIView {

  /**
   * Makes the GUI visible to the user.
   * @param show    confirms that view should be visible
   */
  void showView(boolean show);

  /**
   * Refreshes the screen when something on the screen is updated and therefore it is redrawn.
   */
  void refresh();

  /**
   * Updates the image on display in the image processor GUI.
   * @param newImageName    the name of the new image to be displayed on the GUI in the processor
   */
  void updateImage(String newImageName);

  /**
   * Displays the menu for image operations.
   * @param actionListener    the ActionListener to attach to menu interaction
   */
  void createMenuBar(ActionListener actionListener);

  /**
   * Gets the increment for brightening the image present in the image processor GUI from the
   * JOptionPane.
   * @return  the increment for brightening the image present
   */
  int getBrightenIncrement();

  /**
   * When trying to open an image, retrieves the file path of the image requested to be opened by
   * the user.
   * @return    the file path of the image to be opened
   */
  String getPathToOpen();

  /**
   * When trying to open an image, retrieves the file path to the image requested to be saved by
   * the user.
   * @return    the file path of the image to be saved in the image processor
   */
  String getPathToSave();

  /**
   * Displays an error message if user encounters an error when attempting to save the image.
   */
  void displaySaveError();
}


package controller.command;

import controller.command.ImageProcessorCommand;
import model.ProcessorModel;

/**
 * Represents the save command for the Image Processor.
 */
public class Save implements ImageProcessorCommand {
  String imagePath;
  String imageName;

  /**
   * Constructs the save command object.
   * @param imagePath   the path that the image will be saved on
   * @param imageName   the name of the image to be saved
   */
  public Save(String imagePath, String imageName) {
    if (imagePath == null || imageName == null) {
      throw new IllegalArgumentException("error: given null path or name");
    }
    if (imagePath.equals("") || imageName.equals("")) {
      throw new IllegalArgumentException("error: given empty path or name");
    }
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  @Override
  public void executeCommand(ProcessorModel model) {
    model.save(this.imagePath, this.imageName);
  }
}

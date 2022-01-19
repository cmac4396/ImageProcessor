package controller.command;

import controller.command.ImageProcessorCommand;
import model.ProcessorModel;

/**
 * Represents the load command for the Image Processor.
 */
public class Load implements ImageProcessorCommand {
  String imagePath;
  String imageName;

  /**
   * Constructs the load command object.
   * @param imagePath     the path that the image will be saved on
   * @param imageName     the name of the image to be saved
   */
  public Load(String imagePath, String imageName) {
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
    model.load(this.imagePath, this.imageName);
  }
}


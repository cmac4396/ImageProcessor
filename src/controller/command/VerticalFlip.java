package controller.command;

import controller.command.ImageProcessorCommand;
import model.ProcessorModel;

/**
 * Represents the command to vertically flip the image for the Image Processor.
 */
public class VerticalFlip implements ImageProcessorCommand {
  String imageName;
  String destImageName;

  /**
   * Constructs the vertical flip object.
   * @param imageName        the current name of the image
   * @param destImageName    the name of the new image created
   */
  public VerticalFlip(String imageName, String destImageName) {
    if (imageName == null || destImageName == null) {
      throw new IllegalArgumentException("error: given null path or name");
    }
    if (imageName.equals("") || destImageName.equals("")) {
      throw new IllegalArgumentException("error: given empty path or name");
    }
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  @Override
  public void executeCommand(ProcessorModel model) {
    model.verticalFlip(this.imageName, this.destImageName);
  }
}

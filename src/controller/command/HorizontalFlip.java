package controller.command;

import model.ProcessorModel;

/**
 * Represents the command to horizontally flip the image for the Image Processor.
 */
public class HorizontalFlip implements ImageProcessorCommand {
  String imageName;
  String destImageName;

  /**
   * Constructs the horizontal flip object.
   * @param imageName       the current name of the image
   * @param destImageName   the name of the new image created
   */
  public HorizontalFlip(String imageName, String destImageName) {
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
    model.horizontalFlip(this.imageName, this.destImageName);
  }
}

package controller.command;

import model.ProcessorModel;

/**
 * Represents the command to apply greyscale to an image for the Image Processor.
 */
public class Greyscale implements ImageProcessorCommand {
  String imageName;
  String destImageName;

  /**
   * Constructs a Greyscale object.
   * @param imageName the current name of the image
   * @param destImageName the name of the new image created
   */
  public Greyscale(String imageName, String destImageName) {
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
    model.greyscale(this.imageName, this.destImageName);
  }
}

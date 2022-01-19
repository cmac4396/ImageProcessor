package controller.command;

import model.ProcessorModel;

/**
 * Represents the command to sharpen an image for the Image Processor.
 */
public class Sharpen implements ImageProcessorCommand {
  String imageName;
  String destImageName;

  /**
   * Constructs a Sharpen object.
   * @param imageName the current name of the image
   * @param destImageName the name of the new image created
   */
  public Sharpen(String imageName, String destImageName) {
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
    model.sharpen(this.imageName, this.destImageName);
  }
}

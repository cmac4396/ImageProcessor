package controller.command;

import model.ProcessorModel;

/**
 * Represents the command to brighten or darken an image for the Image Processor.
 */
public class Brighten implements ImageProcessorCommand {
  int increment;
  String imageName;
  String destImageName;

  /**
   * Constructs a brighten command object.
   * @param increment number to brighten or darken the image by
   * @param imageName the current name of the image
   * @param destImageName the name of the new image created
   */
  public Brighten(int increment, String imageName, String destImageName) {
    if (imageName == null || destImageName == null) {
      throw new IllegalArgumentException("error: given null path or name");
    }
    if (imageName.equals("") || destImageName.equals("")) {
      throw new IllegalArgumentException("error: given empty path or name");
    }
    this.increment = increment;
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  @Override
  public void executeCommand(ProcessorModel model) {
    model.brighten(this.increment, this.imageName, this.destImageName);
  }
}

package controller.command;

import model.ProcessorModel;

/**
 * Represents the command to blur an image in the image processor.
 */
public class Blur implements ImageProcessorCommand {
  String imageName;
  String destImageName;

  /**
   * Constructs a Blur object.
   * @param imageName                   the current name of the image
   * @param destImageName               the name of the new image created
   * @throws IllegalArgumentException   when either name is null or empty
   */
  public Blur(String imageName, String destImageName) {
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
    model.blur(this.imageName, this.destImageName);
  }
}

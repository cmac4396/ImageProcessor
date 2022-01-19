package controller.command;

import model.ProcessorModel;

/**
 * Represents the command to make the image more green for the Image Processor.
 */
public class GreenComponent implements ImageProcessorCommand {
  String imageName;
  String destImageName;

  /**
   * Constructs the green component object.
   * @param imageName         the current name of the image
   * @param destImageName     the name of the new image created
   */
  public GreenComponent(String imageName, String destImageName) {
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
    model.greenComponent(this.imageName, this.destImageName);
  }
}

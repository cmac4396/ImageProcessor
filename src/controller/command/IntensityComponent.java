package controller.command;

import controller.command.ImageProcessorCommand;
import model.ProcessorModel;

/**
 * Represents the command to greyscale the image by its intensity for the Image Processor.
 */
public class IntensityComponent implements ImageProcessorCommand {
  String imageName;
  String destImageName;

  /**
   * Constructs the intensity component object.
   * @param imageName       the current name of the image
   * @param destImageName   the name of the new image created
   */
  public IntensityComponent(String imageName, String destImageName) {
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
    model.intensityComponent(this.imageName, this.destImageName);
  }
}


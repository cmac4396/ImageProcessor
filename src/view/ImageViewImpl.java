package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import model.ImageModel;
import model.Pixel;

/**
 * Represents the file conversion implementation of view.ImageView
 */
public class ImageViewImpl implements ImageView {
  private ImageModel model;

  /**
   * Constructs an view.ImagePPMView with the given model
   * @param model model.ImageModel to be converted
   */
  public ImageViewImpl(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("error: given null model");
    }
    this.model = model;
  }

  @Override
  public void writeToFile(String filePath) throws IOException {
    // gets the file type from the end of the filepath
    String[] parsedFile = filePath.split("\\.");
    String fileType = parsedFile[parsedFile.length - 1];

    if (fileType.equalsIgnoreCase("ppm")) {
      this.writeAsPPM(filePath);
    }
    else if (fileType.equalsIgnoreCase("jpg")
            || fileType.equalsIgnoreCase("bmp")
            || fileType.equalsIgnoreCase("png")) {
      this.writeAsRegisteredFile(filePath, fileType);
    }
    else {
      throw new IllegalArgumentException("error: invalid file type given");
    }
  }

  // Writes the file as a PPM file type
  private void writeAsPPM(String filePath) throws IOException {
    StringBuilder ppmAsString = new StringBuilder("");

    String header = "P3 "
            + model.getWidth() + " " + model.getHeight() + "\n"
            + model.getMaxRGB() + "\n";
    ppmAsString.append(header);

    List<List<Pixel>> modelPixels = this.model.getPixels();
    for (List<Pixel> pixelRow : modelPixels) {
      for (Pixel pixel : pixelRow) {
        ppmAsString.append(pixel.getRed() + "\n"
                + pixel.getGreen() + "\n"
                + pixel.getBlue() + "\n");
      }
    }

    // writes string representation to the file
    FileWriter ppmOut = new FileWriter(filePath, false);
    ppmOut.write(ppmAsString.toString());
    ppmOut.close();
  }

  // Writes the file as the given file type that ImageIO.write recognizes
  private void writeAsRegisteredFile(String filePath, String fileType) throws IOException {
    // change 2d list of pixels to a buffered image
    BufferedImage bufferedImage = this.convertPixelsToBufferedImage();
    ImageIO.write(bufferedImage, fileType, new File(filePath));
  }

  // Converts a two-dimensional list of pixels to a buffered image
  private BufferedImage convertPixelsToBufferedImage() {
    int width = this.model.getWidth();
    int height = this.model.getHeight();
    // constructing a new buffered image to modify with the correct rgb values
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // converting rgb values to int values
    List<List<Pixel>> modelPixels = this.model.getPixels();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel currPixel = modelPixels.get(i).get(j);
        int rgbVal = currPixel.getRed();
        rgbVal = (rgbVal << 8) + currPixel.getGreen();
        rgbVal = (rgbVal << 8) + currPixel.getBlue();
        bufferedImage.setRGB(j, i, rgbVal);
      }
    }
    return bufferedImage;
  }
}
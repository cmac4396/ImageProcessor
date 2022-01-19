package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import javax.imageio.ImageIO;

import view.ImageViewImpl;
import view.ImageView;

/**
 * Represents a basic implementation of the image processor functions.
 */
public class ProcessorModelImpl implements ProcessorModel {
  private final List<ImageModel> images;

  /**
   * Creates a model.ProcessorModelImpl object.
   */
  public ProcessorModelImpl() {
    this.images = new ArrayList<>();
  }

  @Override
  public void addImage(ImageModel image) {
    // if there is an image with a matching name, remove it
    for (int imageIndex = 0; imageIndex < this.images.size(); imageIndex++) {
      ImageModel currentImage = this.images.get(imageIndex);

      if (currentImage.getName().equals(image.getName())) {
        this.images.remove(imageIndex);
      }
    }

    this.images.add(image);
  }

  @Override
  public ImageModel getImage(String name) {
    // look for image with name
    for (ImageModel image : this.images) {
      if (image.getName().equals(name)) {
        return image;
      }
    }

    // image not found, throw exception
    throw new IllegalArgumentException("error: image with given name not found");
  }

  @Override
  public String getNameLastEdited() {
    if (this.images.size() < 1) {
      // return an empty string if there are no images in the processor
      return "";
    }
    else {
      return this.images.get(this.images.size() - 1).getName();
    }
  }

  @Override
  public void load(String imagePath, String imageName) {
    // gets the file type from the end of the filepath
    String[] parsedFile = imagePath.split("\\.");
    String fileType = parsedFile[parsedFile.length - 1];

    if (fileType.equalsIgnoreCase("ppm")) {
      this.loadPPM(imagePath, imageName);
    } else if (fileType.equalsIgnoreCase("jpg")
            || fileType.equalsIgnoreCase("bmp")
            || fileType.equalsIgnoreCase("png")) {
      this.loadRegisteredFile(imagePath, imageName);
    } else {
      throw new IllegalArgumentException("error: invalid file given");
    }
  }

  // Loads the file if the given file type that ImageIO.write recognizes
  protected void loadRegisteredFile(String imagePath, String imageName) {
    try {
      BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
      List<List<Pixel>> pixels = new ArrayList<>(bufferedImage.getHeight());

      // converting buffered image to pixels
      for (int i = 0; i < bufferedImage.getHeight(); i++) {
        List<Pixel> pixelRow = new ArrayList<>();
        for (int j = 0; j < bufferedImage.getWidth(); j++) {
          int rgbVal = bufferedImage.getRGB(j, i);
          int red = (rgbVal >> 16) & 0xFF;
          int green = (rgbVal >> 8) & 0xFF;
          int blue = (rgbVal >> 0) & 0xFF;
          pixelRow.add(new Pixel(red, green, blue));
        }
        pixels.add(pixelRow);
      }

      this.addImage(new ImageModelImpl(pixels, imageName));
    } catch (IOException e) {
      throw new IllegalStateException("error: processor failed to read the file");
    }
  }

  // Loads a PPM file type
  protected void loadPPM(String imagePath, String imageName) {
    Scanner sc;

    // check to make sure file exists
    try {
      sc = new Scanner(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("error: loading from an imagePath that does not exist");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s);
        builder.append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    // check file format: PPM version 3
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("error: file format invalid");
    }

    // grab width and height values of image, not necessary to construct model.ImageModel object
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    // grab pixel RGB values
    List<List<Pixel>> pixels = new ArrayList<>(height);
    for (int i = 0; i < height; i++) {
      ArrayList<Pixel> pixelRow = new ArrayList<>(width);
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixelRow.add(new Pixel(r, g, b));
      }
      pixels.add(pixelRow);
    }

    // add image with parsed pixels and max RGB value to the processor
    this.addImage(new ImageModelImpl(pixels, imageName, maxValue));
  }

  // returns appropriate image view/format class for the image in the processor
  // returns null if image not in processor
  protected ImageView getImageView(String imageName) {
    try {
      return new ImageViewImpl(this.getImage(imageName));
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public BufferedImage getCurrentImage() {
    ImageModel currImage = this.images.get(this.images.size() - 1);
    int width = currImage.getWidth();
    int height = currImage.getHeight();
    // constructing a new buffered image to modify with the correct rgb values
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // converting rgb values to int values
    List<List<Pixel>> modelPixels = currImage.getPixels();
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

  @Override
  public void save(String imagePath, String imageName) {
    // check if imageName exists in processor
    ImageView view = getImageView(imageName);
    if (view == null) {
      throw new IllegalArgumentException("error: trying to save imageName not found in processor");
    }

    // catch IOException that occurs when writing to file if writing fails
    try {
      view.writeToFile(imagePath);
    } catch (IOException e) {
      throw new IllegalStateException("error: processor failed to write to file");
    }
  }

  @Override
  public void redComponent(String imageName, String destImageName) {
    this.addImage(this.getImage(imageName).getImageAllRed(destImageName));
  }

  @Override
  public void greenComponent(String imageName, String destImageName) {
    this.addImage(this.getImage(imageName).getImageAllGreen(destImageName));
  }

  @Override
  public void blueComponent(String imageName, String destImageName) {
    this.addImage(this.getImage(imageName).getImageAllBlue(destImageName));
  }

  @Override
  public void valueComponent(String imageName, String destImageName) {
    this.addImage(this.getImage(imageName).getImageAllValue(destImageName));
  }

  @Override
  public void intensityComponent(String imageName, String destImageName) {
    this.addImage(this.getImage(imageName).getImageAllIntensity(destImageName));
  }

  @Override
  public void lumaComponent(String imageName, String destImageName) {
    this.addImage(this.getImage(imageName).getImageAllLuma(destImageName));
  }

  @Override
  public void verticalFlip(String imageName, String destImageName) {
    this.addImage(this.getImage(imageName).getImageFlipVertical(destImageName));
  }

  @Override
  public void horizontalFlip(String imageName, String destImageName) {
    this.addImage(this.getImage(imageName).getImageFlipHorizontal(destImageName));
  }

  @Override
  public void brighten(int increment, String imageName, String destImageName) {
    this.addImage(this.getImage(imageName).getImageAdjustBrightness(destImageName, increment));
  }

  // Applies the given kernel to all pixels of the image
  protected void applyFilter(String imageName, String destImageName, double[][] kernel) {
    this.addImage(this.getImage(imageName).applyFilter(destImageName, kernel));
  }

  @Override
  public void blur(String imageName, String destImageName) {
    double[][] blurKernel = {{0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};
    this.applyFilter(imageName, destImageName, blurKernel);
  }

  @Override
  public void sharpen(String imageName, String destImageName) {
    double[][] sharpenKernel = {{-0.0625, -0.0625, -0.0625, -0.0625, -0.0625},
        {-0.0625, 0.25, 0.25, 0.25, -0.0625},
        {-0.0625, 0.25, 1.0, 0.25, -0.0625},
        {-0.0625, 0.25, 0.25, 0.25, -0.0625},
        {-0.0625, -0.0625, -0.0625, -0.0625, -0.0625}};
    this.applyFilter(imageName, destImageName, sharpenKernel);
  }

  // Multiplies the given kernel to all pixels of the image
  protected void applyColorTransformation(String imageName, String destImageName,
                                          double[][] kernel) {
    this.addImage(this.getImage(imageName).applyColorTransformation(destImageName, kernel));
  }

  @Override
  public void greyscale(String imageName, String destImageName) {
    double[][] lumaKernel = {{0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}};
    this.applyColorTransformation(imageName, destImageName, lumaKernel);
  }

  @Override
  public void sepia(String imageName, String destImageName) {
    double[][] sepiaKernel = {{0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}};
    this.applyColorTransformation(imageName, destImageName, sepiaKernel);
  }

  // applies function to pixel to get value for histogram
  protected Map<Integer, Integer> getHistogram(String imageName, Function<Pixel, Integer> func) {
    return this.getImage(imageName).getHistogram(func);
  }

  @Override
  public Map<Integer, Integer> getRedHistogram(String imageName) {
    return this.getHistogram(imageName, Pixel.getRedFunc());
  }

  @Override
  public Map<Integer, Integer> getGreenHistogram(String imageName) {
    return this.getHistogram(imageName, Pixel.getGreenFunc());
  }

  @Override
  public Map<Integer, Integer> getBlueHistogram(String imageName) {
    return this.getHistogram(imageName, Pixel.getBlueFunc());
  }

  @Override
  public Map<Integer, Integer> getIntensityHistogram(String imageName) {
    return this.getHistogram(imageName, Pixel.getIntensityFunc());
  }
}

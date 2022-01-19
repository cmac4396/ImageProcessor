import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import model.ImageModel;
import model.ImageModelImpl;
import model.Pixel;
import model.ProcessorModel;
import model.ProcessorModelImpl;
import model.ProcessorModelSaveException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for the ProcessorModel interface and its implementations.
 */
public class ProcessorModelTest {
  List<List<Pixel>> fourByThreePixels;
  ImageModel image1;
  ImageModel image2;
  ImageModel image3;
  ProcessorModel fourByThreeProcessor;
  ProcessorModel koalaProcessor;
  ProcessorModel exceptionIOProcessor;

  List<List<Pixel>> twoByThreePixels;
  ImageModel twoByThreeModel;
  ProcessorModel twoByThreeProcessor;

  List<List<Pixel>> threeByThreePixels;
  ImageModel threeByThreeModel;
  ProcessorModel threeByThreeProcessor;

  ImageModel bunnyModel;
  ProcessorModel bunnyProcessor;

  @Before
  public void setUp() {
    this.fourByThreePixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(228, 243, 52),
                    new Pixel(56, 135, 32),
                    new Pixel(252, 154, 142))),
            new ArrayList<>(Arrays.asList(new Pixel(207, 191, 127),
                    new Pixel(81, 163, 148),
                    new Pixel(66, 94, 1))),
            new ArrayList<>(Arrays.asList(new Pixel(162, 167, 186),
                    new Pixel(139, 72, 64),
                    new Pixel(38, 209, 210))),
            new ArrayList<>(Arrays.asList(new Pixel(129, 70, 102),
                    new Pixel(92, 80, 1),
                    new Pixel(80, 177, 113)))));

    this.image1 = new ImageModelImpl(this.fourByThreePixels, "image1", 255);
    this.image2 = new ImageModelImpl(this.fourByThreePixels, "image2", 255);
    this.image3 = new ImageModelImpl(this.fourByThreePixels, "image3", 255);

    this.fourByThreeProcessor = new ProcessorModelImpl();
    this.koalaProcessor = new ProcessorModelImpl();
    this.exceptionIOProcessor = new ProcessorModelSaveException();

    this.twoByThreePixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(100, 150, 100),
                    new Pixel(120, 170, 120),
                    new Pixel(140, 190, 140))),
            new ArrayList<>(Arrays.asList(new Pixel(100, 150, 100),
                    new Pixel(120, 170, 120),
                    new Pixel(140, 190, 140)))));
    this.twoByThreeModel = null;
    this.twoByThreeProcessor = new ProcessorModelImpl();

    this.threeByThreePixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(0, 0, 0),
                    new Pixel(255, 255, 255),
                    new Pixel(0, 0, 0))),
            new ArrayList<>(Arrays.asList(new Pixel(255, 255, 255),
                    new Pixel(0, 0, 0),
                    new Pixel(255, 255, 255))),
            new ArrayList<>(Arrays.asList(new Pixel(0, 0, 0),
                    new Pixel(255, 255, 255),
                    new Pixel(0, 0, 0)))));
    this.threeByThreeModel = null;
    this.threeByThreeProcessor = new ProcessorModelImpl();

    this.bunnyModel = null;
    this.bunnyProcessor = new ProcessorModelImpl();
  }

  @Test
  public void testProcessorModelImplConstructorSuccessful() {
    try {
      ProcessorModelImpl processor = new ProcessorModelImpl();
    }
    catch (Exception e) {
      fail("model.ProcessorModelImpl constructor throws unexpected exception");
    }
  }

  @Test
  public void testAddImage() {
    // check that image1 is not in fourByThreeProcessor
    try {
      ImageModel getImage1 = this.fourByThreeProcessor.getImage("image1");
      fail("error: getImage did not throw exception when expected");
    }
    catch (IllegalArgumentException e) {
      // add the image
      this.fourByThreeProcessor.addImage(this.image1);

      // check that image1 is in the fourByThreeProcessor
      try {
        ImageModel getImage1Again = this.fourByThreeProcessor.getImage("image1");
      }
      catch (IllegalArgumentException exception) {
        fail("error: getImage throws exception for name after adding image to processor");
      }
    }
  }

  @Test
  public void testGetImage() {
    this.fourByThreeProcessor.addImage(this.image1);
    this.fourByThreeProcessor.addImage(this.image2);
    this.fourByThreeProcessor.addImage(this.image3);

    try {
      ImageModel getImage1 = this.fourByThreeProcessor.getImage("image1");
      ImageModel getImage2 = this.fourByThreeProcessor.getImage("image2");
      ImageModel getImage3 = this.fourByThreeProcessor.getImage("image3");
    }
    catch (IllegalArgumentException e) {
      fail("error: model.ProcessorModelImpl.getImage() throws unexpected exception");
    }
  }

  @Test
  public void testGetNameLastEdited() {
    assertEquals("", this.fourByThreeProcessor.getNameLastEdited());

    this.fourByThreeProcessor.addImage(this.image1);
    this.fourByThreeProcessor.addImage(this.image2);
    this.fourByThreeProcessor.addImage(this.image3);
    assertEquals("image3", this.fourByThreeProcessor.getNameLastEdited());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetImageExceptionNameNotFound() {
    this.fourByThreeProcessor.addImage(this.image1);
    this.fourByThreeProcessor.addImage(this.image2);
    this.fourByThreeProcessor.addImage(this.image3);

    this.fourByThreeProcessor.getImage("image4");
  }

  @Test
  public void testLoadPPM() {
    // check that load does not throw exception
    try {
      this.koalaProcessor.load("res/Koala.ppm", "koala");
    }
    catch (Exception e) {
      fail("error: model.ProcessorModelImpl.load() throws unexpected exception");
    }

    // check that koala image can be found in processor
    ImageModel koalaImage = null;
    try {
      koalaImage = this.koalaProcessor.getImage("koala");
    }
    catch (IllegalArgumentException e) {
      fail("error: model.ProcessorModelImpl.load() fails to load image into processor properly");
    }

    // check contents of koala loaded correctly
    assertEquals(1024, koalaImage.getWidth());
    assertEquals(768, koalaImage.getHeight());
    assertEquals(255, koalaImage.getMaxRGB());
  }

  @Test
  public void testLoadPNG() {
    // check that load does not throw exception
    try {
      this.twoByThreeProcessor.load("res/pattern.png", "pattern");
    }
    catch (Exception e) {
      fail("error: model.ProcessorModelImpl.load() throws unexpected exception");
    }

    try {
      this.twoByThreeModel = this.twoByThreeProcessor.getImage("pattern");
    }
    catch (IllegalArgumentException e) {
      fail("error: model.ProcessorModelImpl.load() fails to load image into processor properly");
    }

    // check contents of pattern loaded correctly
    assertEquals(3, this.twoByThreeModel.getWidth());
    assertEquals(2, this.twoByThreeModel.getHeight());
    assertEquals(255, this.twoByThreeModel.getMaxRGB());
    List<List<Pixel>> pixels = this.twoByThreeModel.getPixels();
    for (int i = 0; i < this.twoByThreeModel.getHeight(); i++) {
      for (int j = 0; j < this.twoByThreeModel.getWidth(); j++) {
        // twoByThreePixels is the expected 2d list of pixels
        assertEquals(this.twoByThreePixels.get(i).get(j).getRed(), pixels.get(i).get(j).getRed());
        assertEquals(this.twoByThreePixels.get(i).get(j).getGreen(),
                pixels.get(i).get(j).getGreen());
        assertEquals(this.twoByThreePixels.get(i).get(j).getBlue(), pixels.get(i).get(j).getBlue());
      }
    }
  }

  @Test
  public void testLoadBMP() {
    // check that load does not throw exception
    try {
      this.threeByThreeProcessor.load("res/bnw.bmp", "bnw");
    }
    catch (Exception e) {
      fail("error: model.ProcessorModelImpl.load() throws unexpected exception");
    }

    try {
      this.threeByThreeModel = this.threeByThreeProcessor.getImage("bnw");
    }
    catch (IllegalArgumentException e) {
      fail("error: model.ProcessorModelImpl.load() fails to load image into processor properly");
    }

    // check contents of bnw loaded correctly
    assertEquals(3, this.threeByThreeModel.getWidth());
    assertEquals(3, this.threeByThreeModel.getHeight());
    assertEquals(255, this.threeByThreeModel.getMaxRGB());
    List<List<Pixel>> pixels = this.threeByThreeModel.getPixels();
    for (int i = 0; i < this.threeByThreeModel.getHeight(); i++) {
      for (int j = 0; j < this.threeByThreeModel.getWidth(); j++) {
        // twoByThreePixels is the expected 2d list of pixels
        assertEquals(this.threeByThreePixels.get(i).get(j).getRed(), pixels.get(i).get(j).getRed());
        assertEquals(this.threeByThreePixels.get(i).get(j).getGreen(),
                pixels.get(i).get(j).getGreen());
        assertEquals(this.threeByThreePixels.get(i).get(j).getBlue(),
                pixels.get(i).get(j).getBlue());
      }
    }
  }

  @Test
  public void testLoadJPG() {
    // check that load does not throw exception
    try {
      this.bunnyProcessor.load("res/cutebunny.jpg", "cutebunny");
    }
    catch (Exception e) {
      fail("error: model.ProcessorModelImpl.load() throws unexpected exception");
    }

    try {
      this.bunnyModel = this.bunnyProcessor.getImage("cutebunny");
    }
    catch (IllegalArgumentException e) {
      fail("error: model.ProcessorModelImpl.load() fails to load image into processor properly");
    }

    // check contents of cute bunny loaded correctly
    assertEquals(1024, this.bunnyModel.getWidth());
    assertEquals(768, this.bunnyModel.getHeight());
    assertEquals(255, this.bunnyModel.getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadExceptionFilePathDoesNotExist() {
    this.koalaProcessor.load("NotrealPath.ppm", "koala");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadExceptionPPMWrongVersion() {
    try {
      FileWriter badPPM = new FileWriter("badPPM.ppm", false);
      badPPM.write("P2");
      badPPM.close();
    } catch (IOException e) {
      fail("error: testing wrong ppm version fails");
    }

    this.koalaProcessor.load("badPPM.ppm", "koala");
  }

  // reads from a PPM version 3 file at given filePath
  private String readPPMFile(String filePath) {
    StringBuilder imageContents = new StringBuilder("");

    try {
      BufferedReader imageReader = new BufferedReader(new FileReader(filePath));
      String data = null;
      data = imageReader.readLine();
      while (data != null) {
        imageContents.append(data);
        imageContents.append("\n");
        data = imageReader.readLine();
      }
      imageReader.close();
    }
    catch (IOException e) {
      return null;
    }

    return imageContents.toString();
  }

  @Test
  public void testSavePPM() {
    this.fourByThreeProcessor.addImage(this.image1);
    this.fourByThreeProcessor.save("image1.ppm", "image1");

    String actualContents = this.readPPMFile("image1.ppm");
    // check to make sure that read was successful
    if (actualContents == null) {
      fail("error: while testing save, failed to read from file");
    }

    String expectedContents = "P3 3 4\n"
            + "255\n"
            + "228\n243\n52\n"
            + "56\n135\n32\n"
            + "252\n154\n142\n"
            + "207\n191\n127\n"
            + "81\n163\n148\n"
            + "66\n94\n1\n"
            + "162\n167\n186\n"
            + "139\n72\n64\n"
            + "38\n209\n210\n"
            + "129\n70\n102\n"
            + "92\n80\n1\n"
            + "80\n177\n113\n";
    assertEquals(expectedContents, actualContents);
  }

  @Test
  public void testSavePNG() {
    this.twoByThreeModel = new ImageModelImpl(this.twoByThreePixels, "pattern");
    this.twoByThreeProcessor.addImage(this.twoByThreeModel);
    this.twoByThreeProcessor.save("res/pattern.png", "pattern");

    // check if file exists
    File f = new File("res/pattern.png");
    assertTrue(f.exists());
  }

  @Test
  public void testSaveBMP() {
    this.threeByThreeModel = new ImageModelImpl(this.threeByThreePixels, "bnw");
    this.threeByThreeProcessor.addImage(this.threeByThreeModel);
    this.threeByThreeProcessor.save("res/bnw.png", "bnw");

    // check if file exists
    File f = new File("res/bnw.png");
    assertTrue(f.exists());
  }

  @Test
  public void testSaveJPG() {
    List<List<Pixel>> pixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(101, 90, 124),
                    new Pixel(101, 90, 124),
                    new Pixel(171, 146, 191),
                    new Pixel(171, 146, 191),
                    new Pixel(175, 193, 214),
                    new Pixel(175, 193, 214),
                    new Pixel(206, 249, 242),
                    new Pixel(206, 249, 242),
                    new Pixel(214, 202, 152),
                    new Pixel(214, 202, 152))),
            new ArrayList<>(Arrays.asList(new Pixel(101, 90, 124),
                    new Pixel(101, 90, 124),
                    new Pixel(171, 146, 191),
                    new Pixel(171, 146, 191),
                    new Pixel(175, 193, 214),
                    new Pixel(175, 193, 214),
                    new Pixel(206, 249, 242),
                    new Pixel(206, 249, 242),
                    new Pixel(214, 202, 152),
                    new Pixel(214, 202, 152)))));
    ImageModel paletteModel = new ImageModelImpl(pixels, "palette");
    this.bunnyProcessor.addImage(paletteModel);
    this.bunnyProcessor.save("res/palette.jpg", "palette");

    // check if file exists
    File f = new File("res/palette.jpg");
    assertTrue(f.exists());
  }

  @Test(expected = IllegalStateException.class)
  public void testSaveIOException() {
    this.exceptionIOProcessor.addImage(this.image1);
    this.exceptionIOProcessor.save("something.ppm", "image1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveExceptionNoImageFound() {
    this.fourByThreeProcessor.save("something.ppm", "image4");
  }

  @Test
  public void testRedComponent() {
    this.fourByThreeProcessor.addImage(image1);
    this.fourByThreeProcessor.redComponent("image1", "imageRed");

    List<List<Pixel>> redPixels = null;
    // check to see that the new image exists in the processor
    try {
      redPixels = this.fourByThreeProcessor.getImage("imageRed").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when visualizing red component");
    }

    // check that the new image has the correct values
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = redPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getRed(), currentPixel.getRed());
        assertEquals(originalPixel.getRed(), currentPixel.getGreen());
        assertEquals(originalPixel.getRed(), currentPixel.getBlue());
      }
    }

    // check that max RGB value is still the same
    assertEquals(255,
            this.fourByThreeProcessor.getImage("imageRed").getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRedComponentExceptionNoImageFound() {
    this.fourByThreeProcessor.redComponent("image1", "imageRed");
  }

  @Test
  public void testGreenComponent() {
    this.fourByThreeProcessor.addImage(image1);
    this.fourByThreeProcessor.greenComponent("image1", "imageGreen");

    List<List<Pixel>> greenPixels = null;
    // check to see that the new image exists in the processor
    try {
      greenPixels = this.fourByThreeProcessor.getImage("imageGreen").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when visualizing green component");
    }

    // check that the new image has the correct values
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = greenPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getGreen(), currentPixel.getRed());
        assertEquals(originalPixel.getGreen(), currentPixel.getGreen());
        assertEquals(originalPixel.getGreen(), currentPixel.getBlue());
      }
    }

    // check that max RGB value is still the same
    assertEquals(255, this.fourByThreeProcessor.getImage("imageGreen").getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreenComponentExceptionNoImageFound() {
    this.fourByThreeProcessor.greenComponent("image1", "imageGreen");
  }

  @Test
  public void testBlueComponent() {
    this.fourByThreeProcessor.addImage(image1);
    this.fourByThreeProcessor.blueComponent("image1", "imageBlue");

    List<List<Pixel>> bluePixels = null;
    // check to see that the new image exists in the processor
    try {
      bluePixels = this.fourByThreeProcessor.getImage("imageBlue").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when visualizing blue component");
    }

    // check that the new image has the correct values
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = bluePixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getBlue(), currentPixel.getRed());
        assertEquals(originalPixel.getBlue(), currentPixel.getGreen());
        assertEquals(originalPixel.getBlue(), currentPixel.getBlue());
      }
    }

    // check that max RGB value is still the same
    assertEquals(255, this.fourByThreeProcessor.getImage("imageBlue").getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlueComponentExceptionNoImageFound() {
    this.fourByThreeProcessor.blueComponent("image1", "imageBlue");
  }

  @Test
  public void testValueComponent() {
    this.fourByThreeProcessor.addImage(image1);
    this.fourByThreeProcessor.valueComponent("image1", "imageValue");

    List<List<Pixel>> valuePixels = null;
    // check to see that the new image exists in the processor
    try {
      valuePixels = this.fourByThreeProcessor.getImage("imageValue").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when visualizing value component");
    }

    // check that the new image has the correct values
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = valuePixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getValue(), currentPixel.getRed());
        assertEquals(originalPixel.getValue(), currentPixel.getGreen());
        assertEquals(originalPixel.getValue(), currentPixel.getBlue());
      }
    }

    // check that max RGB value is still the same
    assertEquals(255, this.fourByThreeProcessor.getImage("imageValue").getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValueComponentExceptionNoImageFound() {
    this.fourByThreeProcessor.valueComponent("image1", "imageValue");
  }

  @Test
  public void testIntensityComponent() {
    this.fourByThreeProcessor.addImage(image1);
    this.fourByThreeProcessor.intensityComponent("image1",
            "imageIntensity");

    List<List<Pixel>> intensityPixels = null;
    // check to see that the new image exists in the processor
    try {
      intensityPixels = this.fourByThreeProcessor.getImage("imageIntensity").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when visualizing intensity component");
    }

    // check that the new image has the correct values
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = intensityPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getIntensity(), currentPixel.getRed());
        assertEquals(originalPixel.getIntensity(), currentPixel.getGreen());
        assertEquals(originalPixel.getIntensity(), currentPixel.getBlue());
      }
    }

    // check that max RGB value is still the same
    assertEquals(255,
            this.fourByThreeProcessor.getImage("imageIntensity").getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIntensityComponentExceptionNoImageFound() {
    this.fourByThreeProcessor.intensityComponent("image1",
            "imageIntensity");
  }

  @Test
  public void testLumaComponent() {
    this.fourByThreeProcessor.addImage(image1);
    this.fourByThreeProcessor.lumaComponent("image1",
            "imageLuma");

    List<List<Pixel>> lumaPixels = null;
    // check to see that the new image exists in the processor
    try {
      lumaPixels = this.fourByThreeProcessor.getImage("imageLuma").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when visualizing luma component");
    }

    // check that the new image has the correct values
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = lumaPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getLuma(), currentPixel.getRed());
        assertEquals(originalPixel.getLuma(), currentPixel.getGreen());
        assertEquals(originalPixel.getLuma(), currentPixel.getBlue());
      }
    }

    // check that max RGB value is still the same
    assertEquals(255,
            this.fourByThreeProcessor.getImage("imageLuma").getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLumaComponentExceptionNoImageFound() {
    this.fourByThreeProcessor.lumaComponent("image1",
            "imageLuma");
  }

  @Test
  public void testVerticalFlip() {
    this.fourByThreeProcessor.addImage(image1);
    this.fourByThreeProcessor.verticalFlip("image1", "imageVerticalFlip");

    List<List<Pixel>> flippedPixels = null;
    // check to see that the new image exists in the processor
    try {
      flippedPixels = this.fourByThreeProcessor.getImage("imageVerticalFlip").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when applying vertical flip");
    }

    // check pixel positions are correct
    List<List<Pixel>> verticallyFlippedPixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(129, 70, 102),
                    new Pixel(92, 80, 1),
                    new Pixel(80, 177, 113))),
            new ArrayList<>(Arrays.asList(new Pixel(162, 167, 186),
                    new Pixel(139, 72, 64),
                    new Pixel(38, 209, 210))),
            new ArrayList<>(Arrays.asList(new Pixel(207, 191, 127),
                    new Pixel(81, 163, 148),
                    new Pixel(66, 94, 1))),
            new ArrayList<>(Arrays.asList(new Pixel(228, 243, 52),
                    new Pixel(56, 135, 32),
                    new Pixel(252, 154, 142)))));

    for (int row = 0; row < verticallyFlippedPixels.size(); row++) {
      for (int col = 0; col < verticallyFlippedPixels.get(row).size(); col++) {
        Pixel expectedPixel = verticallyFlippedPixels.get(row).get(col);
        Pixel actualPixel = flippedPixels.get(row).get(col);

        assertEquals(expectedPixel.getRed(), actualPixel.getRed());
        assertEquals(expectedPixel.getGreen(), actualPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), actualPixel.getBlue());
      }
    }

    // check that max rgb is still the same
    assertEquals(255,
            this.fourByThreeProcessor.getImage("imageVerticalFlip").getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipExceptionImageNotFound() {
    this.fourByThreeProcessor.verticalFlip("image1", "imageVerticalFlip");
  }

  @Test
  public void testHorizontalFlip() {
    this.fourByThreeProcessor.addImage(image1);
    this.fourByThreeProcessor.horizontalFlip("image1", "imageHorizontalFlip");

    List<List<Pixel>> flippedPixels = null;
    // check to see that the new image exists in the processor
    try {
      flippedPixels = this.fourByThreeProcessor.getImage("imageHorizontalFlip").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when applying horizontal flip");
    }

    // check pixel positions are correct
    List<List<Pixel>> horizontallyFlippedPixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(
                    new Pixel(252, 154, 142),
                    new Pixel(56, 135, 32),
                    new Pixel(228, 243, 52))),
            new ArrayList<>(Arrays.asList(new Pixel(66, 94, 1),
                    new Pixel(81, 163, 148),
                    new Pixel(207, 191, 127))),
            new ArrayList<>(Arrays.asList(new Pixel(38, 209, 210),
                    new Pixel(139, 72, 64),
                    new Pixel(162, 167, 186))),
            new ArrayList<>(Arrays.asList(new Pixel(80, 177, 113),
                    new Pixel(92, 80, 1),
                    new Pixel(129, 70, 102)))));

    for (int row = 0; row < horizontallyFlippedPixels.size(); row++) {
      for (int col = 0; col < horizontallyFlippedPixels.get(row).size(); col++) {
        Pixel expectedPixel = horizontallyFlippedPixels.get(row).get(col);
        Pixel actualPixel = flippedPixels.get(row).get(col);

        assertEquals(expectedPixel.getRed(), actualPixel.getRed());
        assertEquals(expectedPixel.getGreen(), actualPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), actualPixel.getBlue());
      }
    }

    // check that max rgb is still the same
    assertEquals(255,
            this.fourByThreeProcessor.getImage("imageHorizontalFlip").getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipExceptionImageNotFound() {
    this.fourByThreeProcessor.horizontalFlip("image1",
            "imageHorizontalFlip");
  }

  @Test
  public void testBrighten() {
    this.fourByThreeProcessor.addImage(image1);
    this.fourByThreeProcessor.brighten(1, "image1",
            "imageBright");

    List<List<Pixel>> brightPixels = null;
    // check to see that the new image exists in the processor
    try {
      brightPixels = this.fourByThreeProcessor.getImage("imageBright").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when applying horizontal flip");
    }

    // check pixel values are correct
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = brightPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getRed() + 1, currentPixel.getRed());
        assertEquals(originalPixel.getGreen() + 1, currentPixel.getGreen());
        assertEquals(originalPixel.getBlue() + 1, currentPixel.getBlue());
      }
    }

    // check maxRGB value is still the same
    assertEquals(255,
            this.fourByThreeProcessor.getImage("imageBright").getMaxRGB());
  }

  @Test
  public void testBlur() {
    ProcessorModel filterProcessor = new ProcessorModelImpl();
    filterProcessor.addImage(this.image1);
    filterProcessor.blur("image1", "imageBlur");

    List<List<Pixel>> blurPixels = null;
    // check to see that the new image exists in the processor
    try {
      blurPixels = filterProcessor.getImage("imageBlur").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when applying blur");
    }

    // check pixel positions are correct
    List<List<Pixel>> expectedPixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(
                    new Pixel(94, 111, 42),
                    new Pixel(101, 121, 58),
                    new Pixel(83, 77, 48))),
            new ArrayList<>(Arrays.asList(new Pixel(122, 132, 86),
                    new Pixel(121, 150, 101),
                    new Pixel(75, 102, 68))),
            new ArrayList<>(Arrays.asList(new Pixel(110, 98, 92),
                    new Pixel(111, 128, 105),
                    new Pixel(55, 110, 84))),
            new ArrayList<>(Arrays.asList(new Pixel(72, 52, 52),
                    new Pixel(79, 83, 59),
                    new Pixel(44, 84, 58)))));

    // check pixel values are correct
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = blurPixels.get(row).get(col);
        Pixel expectedPixel = expectedPixels.get(row).get(col);

        assertEquals(expectedPixel.getRed(), currentPixel.getRed());
        assertEquals(expectedPixel.getGreen(), currentPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), currentPixel.getBlue());
      }
    }

    // check maxRGB value is still the same
    assertEquals(255,
            filterProcessor.getImage("imageBlur").getMaxRGB());
  }

  @Test
  public void testSharpen() {
    ProcessorModel filterProcessor = new ProcessorModelImpl();
    filterProcessor.addImage(this.image1);
    filterProcessor.sharpen("image1", "imageSharpen");

    List<List<Pixel>> sharpenPixels = null;
    // check to see that the new image exists in the processor
    try {
      sharpenPixels = filterProcessor.getImage("imageSharpen").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when applying blur");
    }

    // check pixel positions are correct
    List<List<Pixel>> expectedPixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(
                    new Pixel(255, 255, 91),
                    new Pixel(243, 255, 120),
                    new Pixel(254, 196, 147))),
            new ArrayList<>(Arrays.asList(new Pixel(255, 255, 211),
                    new Pixel(255, 255, 255),
                    new Pixel(151, 219, 113))),
            new ArrayList<>(Arrays.asList(new Pixel(255, 247, 255),
                    new Pixel(255, 255, 255),
                    new Pixel(87, 255, 251))),
            new ArrayList<>(Arrays.asList(new Pixel(197, 97, 127),
                    new Pixel(206, 225, 152),
                    new Pixel(106, 224, 146)))));

    // check pixel values are correct
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = sharpenPixels.get(row).get(col);
        Pixel expectedPixel = expectedPixels.get(row).get(col);

        assertEquals(expectedPixel.getRed(), currentPixel.getRed());
        assertEquals(expectedPixel.getGreen(), currentPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), currentPixel.getBlue());
      }
    }

    // check maxRGB value is still the same
    assertEquals(255, filterProcessor.getImage("imageSharpen").getMaxRGB());
  }

  @Test
  public void testGreyscale() {
    ProcessorModel processorModel = new ProcessorModelImpl();
    processorModel.addImage(this.image1);
    processorModel.greyscale("image1", "imageGreyscale");

    List<List<Pixel>> greyscalePixels = null;
    // check to see that the new image exists in the processor
    try {
      greyscalePixels = processorModel.getImage("imageGreyscale").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when applying greyscale");
    }

    List<List<Pixel>> expectedPixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(
                    new Pixel(226, 226, 226),
                    new Pixel(110, 110, 110),
                    new Pixel(173, 173, 173))),
            new ArrayList<>(Arrays.asList(new Pixel(189, 189, 189),
                    new Pixel(144, 144, 144),
                    new Pixel(81, 81, 81))),
            new ArrayList<>(Arrays.asList(new Pixel(167, 167, 167),
                    new Pixel(85, 85, 85),
                    new Pixel(172, 172, 172))),
            new ArrayList<>(Arrays.asList(new Pixel(84, 84, 84),
                    new Pixel(76, 76, 76),
                    new Pixel(151, 151, 151)))));

    // check pixel values are correct
    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = greyscalePixels.get(row).get(col);
        Pixel expectedPixel = expectedPixels.get(row).get(col);

        assertEquals(expectedPixel.getRed(), currentPixel.getRed());
        assertEquals(expectedPixel.getGreen(), currentPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), currentPixel.getBlue());
      }
    }

    // check maxRGB value is still the same
    assertEquals(255, processorModel.getImage("imageGreyscale").getMaxRGB());
  }

  @Test
  public void testSepia() {
    ProcessorModel processorModel = new ProcessorModelImpl();
    this.twoByThreeModel = new ImageModelImpl(this.twoByThreePixels, "pattern");
    processorModel.addImage(this.twoByThreeModel);
    processorModel.sepia("pattern", "imageSepia");

    List<List<Pixel>> sepiaPixels = null;
    // check to see that the new image exists in the processor
    try {
      sepiaPixels = processorModel.getImage("imageSepia").getPixels();
    }
    catch (IllegalArgumentException e) {
      fail("error: new image not added to processor when applying greyscale");
    }

    List<List<Pixel>> expectedPixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(173, 154, 120),
                    new Pixel(200, 178, 139),
                    new Pixel(227, 202, 157))),
            new ArrayList<>(Arrays.asList(new Pixel(173, 154, 120),
                    new Pixel(200, 178, 139),
                    new Pixel(227, 202, 157)))));

    // check pixel values are correct
    for (int row = 0; row < this.twoByThreePixels.size(); row++) {
      for (int col = 0; col < this.twoByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = sepiaPixels.get(row).get(col);
        Pixel expectedPixel = expectedPixels.get(row).get(col);

        assertEquals(expectedPixel.getRed(), currentPixel.getRed());
        assertEquals(expectedPixel.getGreen(), currentPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), currentPixel.getBlue());
      }
    }

    // check maxRGB value is still the same
    assertEquals(255, processorModel.getImage("imageSepia").getMaxRGB());
  }

  @Test
  public void testGetRedHistogram() {
    this.fourByThreeProcessor.addImage(new ImageModelImpl(this.threeByThreePixels, "three"));

    Map<Integer, Integer> actualHistogram =
            this.fourByThreeProcessor.getRedHistogram("three");
    int actualHistogram0Bin = actualHistogram.getOrDefault(0, -1);
    int actualHistogram255Bin = actualHistogram.getOrDefault(255, -1);

    if (actualHistogram0Bin == -1 || actualHistogram255Bin == -1) {
      fail("error: red histogram does not contain expected key");
    }
    else {
      assertEquals(5, actualHistogram0Bin);
      assertEquals(4, actualHistogram255Bin);
    }
  }

  @Test
  public void testGetGreenHistogram() {
    this.fourByThreeProcessor.addImage(new ImageModelImpl(this.threeByThreePixels, "three"));

    Map<Integer, Integer> actualHistogram =
            this.fourByThreeProcessor.getGreenHistogram("three");
    int actualHistogram0Bin = actualHistogram.getOrDefault(0, -1);
    int actualHistogram255Bin = actualHistogram.getOrDefault(255, -1);

    if (actualHistogram0Bin == -1 || actualHistogram255Bin == -1) {
      fail("error: red histogram does not contain expected key");
    }
    else {
      assertEquals(5, actualHistogram0Bin);
      assertEquals(4, actualHistogram255Bin);
    }
  }

  @Test
  public void testGetBlueHistogram() {
    this.fourByThreeProcessor.addImage(new ImageModelImpl(this.threeByThreePixels, "three"));

    Map<Integer, Integer> actualHistogram =
            this.fourByThreeProcessor.getBlueHistogram("three");
    int actualHistogram0Bin = actualHistogram.getOrDefault(0, -1);
    int actualHistogram255Bin = actualHistogram.getOrDefault(255, -1);

    if (actualHistogram0Bin == -1 || actualHistogram255Bin == -1) {
      fail("error: red histogram does not contain expected key");
    }
    else {
      assertEquals(5, actualHistogram0Bin);
      assertEquals(4, actualHistogram255Bin);
    }
  }

  @Test
  public void testGetIntensityHistogram() {
    this.fourByThreeProcessor.addImage(new ImageModelImpl(this.threeByThreePixels, "three"));

    Map<Integer, Integer> actualHistogram =
            this.fourByThreeProcessor.getIntensityHistogram("three");
    int actualHistogram0Bin = actualHistogram.getOrDefault(0, -1);
    int actualHistogram255Bin = actualHistogram.getOrDefault(255, -1);

    if (actualHistogram0Bin == -1 || actualHistogram255Bin == -1) {
      fail("error: red histogram does not contain expected key");
    }
    else {
      assertEquals(5, actualHistogram0Bin);
      assertEquals(4, actualHistogram255Bin);
    }
  }

  @Test
  public void testGetCurrentImage() {
    this.twoByThreeModel = new ImageModelImpl(this.twoByThreePixels, "pattern");
    this.twoByThreeProcessor.addImage(this.twoByThreeModel);
    BufferedImage twoImage = this.twoByThreeProcessor.getCurrentImage();

    // converting buffered image back into pixels to compare if they are the same
    List<List<Pixel>> pixels = new ArrayList<>(twoImage.getHeight());
    for (int i = 0; i < twoImage.getHeight(); i++) {
      List<Pixel> pixelRow = new ArrayList<>();
      for (int j = 0; j < twoImage.getWidth(); j++) {
        int rgbVal = twoImage.getRGB(j, i);
        int red = (rgbVal >> 16) & 0xFF;
        int green = (rgbVal >> 8) & 0xFF;
        int blue = (rgbVal >> 0) & 0xFF;
        pixelRow.add(new Pixel(red, green, blue));
      }
      pixels.add(pixelRow);
    }

    // testing if components of the image are equal
    assertEquals(twoImage.getWidth(), this.twoByThreeModel.getWidth());
    assertEquals(twoImage.getHeight(), this.twoByThreeModel.getHeight());
    for (int row = 0; row < this.twoByThreePixels.size(); row++) {
      for (int col = 0; col < this.twoByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = pixels.get(row).get(col);
        Pixel expectedPixel = this.twoByThreePixels.get(row).get(col);

        assertEquals(expectedPixel.getRed(), currentPixel.getRed());
        assertEquals(expectedPixel.getGreen(), currentPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), currentPixel.getBlue());
      }
    }
  }
}
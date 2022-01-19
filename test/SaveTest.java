import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.command.ImageProcessorCommand;
import controller.command.Save;
import model.ImageModel;
import model.ImageModelImpl;
import model.Pixel;
import model.ProcessorModel;
import model.ProcessorModelImpl;
import model.ProcessorModelSaveException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for controller.command.Save class.
 */
public class SaveTest {
  List<List<Pixel>> fourByThreePixels;
  ImageModel image1;
  ProcessorModel fourByThreeProcessor;
  ImageProcessorCommand save;
  ProcessorModel exceptionIOProcessor;

  @Before
  public void setUp() throws Exception {
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
    this.fourByThreeProcessor = new ProcessorModelImpl();
    this.exceptionIOProcessor = new ProcessorModelSaveException();

    this.save = new Save("image1.ppm", "image1");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImagePath() {
    ImageProcessorCommand saveFail = new Save(null, "image");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImageName() {
    ImageProcessorCommand saveFail = new Save("images/image.ppm", null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testEmptyImagePath() {
    ImageProcessorCommand saveFail = new Save("", "image");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testEmptyImageName() {
    ImageProcessorCommand saveFail = new Save("images/image.ppm", "");
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
  public void testSave() {
    // add the image to the processor
    this.fourByThreeProcessor.addImage(this.image1);
    // save the image using command
    this.save.executeCommand(this.fourByThreeProcessor);

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

  @Test (expected = IllegalStateException.class)
  public void testSaveIOException() {
    this.exceptionIOProcessor.addImage(this.image1);
    this.save = new Save("something.ppm", "image1");
    this.save.executeCommand(this.exceptionIOProcessor);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSaveExceptionNoImageFound() {
    this.save = new Save("something.ppm", "image4");
    this.save.executeCommand(this.fourByThreeProcessor);
  }
}

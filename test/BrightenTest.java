import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.command.Brighten;
import controller.command.ImageProcessorCommand;
import model.ImageModel;
import model.ImageModelImpl;
import model.Pixel;
import model.ProcessorModel;
import model.ProcessorModelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the controller.command.Brighten class.
 */
public class BrightenTest {
  List<List<Pixel>> fourByThreePixels;
  ImageModel image1;
  ProcessorModel fourByThreeProcessor;
  ImageProcessorCommand brighten;

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

    this.brighten = new Brighten(1, "image1", "imageBright");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImageName() {
    ImageProcessorCommand brightenFail =
            new Brighten(1, null, "image");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullDestImageName() {
    ImageProcessorCommand brightenFail =
            new Brighten(1, "image", null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testEmptyImageName() {
    ImageProcessorCommand brightenFail =
            new Brighten(1, "", "image");
  }


  @Test (expected = IllegalArgumentException.class)
  public void testEmptyDestImageName() {
    ImageProcessorCommand brightenFail =
            new Brighten(1, "image", "");
  }

  @Test
  public void testExecuteCommand() {
    this.fourByThreeProcessor.addImage(this.image1);
    this.brighten.executeCommand(this.fourByThreeProcessor);

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
  public void testExecuteCommandOverBounds() {
    ImageProcessorCommand brightenTwenty =
            new Brighten(20, "image1", "imageBright");
    this.fourByThreeProcessor.addImage(this.image1);
    brightenTwenty.executeCommand(this.fourByThreeProcessor);

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

        if (row == 0 && col == 0) {
          assertEquals(originalPixel.getRed() + 20, currentPixel.getRed());
          assertEquals(255, currentPixel.getGreen());
          assertEquals(originalPixel.getBlue() + 20, currentPixel.getBlue());
        }
        else if (row == 0 && col == 2) {
          assertEquals(255, currentPixel.getRed());
          assertEquals(originalPixel.getGreen() + 20, currentPixel.getGreen());
          assertEquals(originalPixel.getBlue() + 20, currentPixel.getBlue());
        }
        else {
          assertEquals(originalPixel.getRed() + 20, currentPixel.getRed());
          assertEquals(originalPixel.getGreen() + 20, currentPixel.getGreen());
          assertEquals(originalPixel.getBlue() + 20, currentPixel.getBlue());
        }
      }
    }

    // check maxRGB value is still the same
    assertEquals(255,
            this.fourByThreeProcessor.getImage("imageBright").getMaxRGB());
  }
}

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.command.BlueComponent;
import controller.command.Blur;
import controller.command.GreenComponent;
import controller.command.Greyscale;
import controller.command.HorizontalFlip;
import controller.command.ImageProcessorCommand;
import controller.command.IntensityComponent;
import controller.command.LumaComponent;
import controller.command.RedComponent;
import controller.command.Sepia;
import controller.command.Sharpen;
import controller.command.ValueComponent;
import controller.command.VerticalFlip;
import model.ImageModel;
import model.ImageModelImpl;
import model.Pixel;
import model.ProcessorModel;
import model.ProcessorModelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for implementations of ImageProcessorCommand that represent image operations.
 */
public abstract class ImageOperationCommandTest {
  List<List<Pixel>> originalPixels;
  List<List<Pixel>> expectedPixels;
  ImageModel beforeImage;
  ProcessorModel model;
  ImageProcessorCommand command;

  // creates a command using the constructor of the implementation
  protected abstract ImageProcessorCommand createCommand(String imageName, String destImageName);

  // returns expected pixels for 4x3 image after executing command
  protected abstract List<List<Pixel>> getExpectedPixels();

  @Before
  public void setUp() throws Exception {
    this.originalPixels = new ArrayList<>(Arrays.asList(
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

    this.beforeImage = new ImageModelImpl(this.originalPixels, "beforeImage",
            255);
    model = new ProcessorModelImpl();
    this.model.addImage(this.beforeImage);

    this.command = createCommand("beforeImage", "afterImage");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImageName() {
    this.createCommand(null, "afterImage");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullDestImageName() {
    this.createCommand("beforeImage", null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testEmptyImageName() {
    this.createCommand("", "afterImage");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testEmptyDestImageName() {
    this.createCommand("beforeImage", "");
  }

  @Test
  public void testExecuteCommand() {
    List<List<Pixel>> actualPixels = null;
    try {
      this.command.executeCommand(this.model);
      actualPixels = this.model.getImage("afterImage").getPixels();
    }
    catch (Exception e) {
      fail("error: command throws unexpected exception");
    }

    List<List<Pixel>> expectedPixels = this.getExpectedPixels();

    // check that the new image has the correct values
    for (int row = 0; row < originalPixels.size(); row++) {
      for (int col = 0; col < originalPixels.get(row).size(); col++) {
        Pixel expectedPixel = expectedPixels.get(row).get(col);
        Pixel actualPixel = actualPixels.get(row).get(col);

        assertEquals(expectedPixel.getRed(), actualPixel.getRed());
        assertEquals(expectedPixel.getGreen(), actualPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), actualPixel.getBlue());
      }
    }

    // check that max RGB value is still the same
    assertEquals(255,
            this.model.getImage("afterImage").getMaxRGB());
  }

  /**
   * Tests for the Blur implementation of ImageProcessorCommand.
   */
  public static final class BlurTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new Blur(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
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
    }
  }

  /**
   * Tests for the Sharpen implementation of ImageProcessorCommand.
   */
  public static final class SharpenTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new Sharpen(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
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
    }
  }

  /**
   * Tests for the Greyscale implementation of ImageProcessorCommand.
   */
  public static final class GreyscaleTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new Greyscale(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
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
    }
  }

  /**
   * Tests for the Sepia implementation of ImageProcessorCommand.
   */
  public static final class SepiaTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new Sepia(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
              new ArrayList<>(Arrays.asList(
                      new Pixel(255, 255, 198),
                      new Pixel(131, 117, 91),
                      new Pixel(244, 217, 169))),
              new ArrayList<>(Arrays.asList(new Pixel(252, 224, 174),
                      new Pixel(185, 164, 128),
                      new Pixel(98, 87, 68))),
              new ArrayList<>(Arrays.asList(new Pixel(227, 202, 157),
                      new Pixel(122, 108, 84),
                      new Pixel(215, 191, 149))),
              new ArrayList<>(Arrays.asList(new Pixel(123, 110, 85),
                      new Pixel(97, 87, 67),
                      new Pixel(188, 168, 131)))));
    }
  }

  /**
   * Tests for the RedComponent implementation of ImageProcessorCommand.
   */
  public static final class RedComponentTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new RedComponent(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
              new ArrayList<>(Arrays.asList(
                      new Pixel(228, 228, 228),
                      new Pixel(56, 56, 56),
                      new Pixel(252, 252, 252))),
              new ArrayList<>(Arrays.asList(new Pixel(207, 207, 207),
                      new Pixel(81, 81, 81),
                      new Pixel(66, 66, 66))),
              new ArrayList<>(Arrays.asList(new Pixel(162, 162, 162),
                      new Pixel(139, 139, 139),
                      new Pixel(38, 38, 38))),
              new ArrayList<>(Arrays.asList(new Pixel(129, 129, 129),
                      new Pixel(92, 92, 92),
                      new Pixel(80, 80, 80)))));
    }
  }

  /**
   * Tests for the GreenComponent implementation of ImageProcessorCommand.
   */
  public static final class GreenComponentTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new GreenComponent(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
              new ArrayList<>(Arrays.asList(
                      new Pixel(243, 243, 243),
                      new Pixel(135, 135, 135),
                      new Pixel(154, 154, 154))),
              new ArrayList<>(Arrays.asList(new Pixel(191, 191, 191),
                      new Pixel(163, 163, 163),
                      new Pixel(94, 94, 94))),
              new ArrayList<>(Arrays.asList(new Pixel(167, 167, 167),
                      new Pixel(72, 72, 72),
                      new Pixel(209, 209, 209))),
              new ArrayList<>(Arrays.asList(new Pixel(70, 70, 70),
                      new Pixel(80, 80, 80),
                      new Pixel(177, 177, 177)))));
    }
  }

  /**
   * Tests for the BlueComponent implementation of ImageProcessorCommand.
   */
  public static final class BlueComponentTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new BlueComponent(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
              new ArrayList<>(Arrays.asList(
                      new Pixel(52, 52, 52),
                      new Pixel(32, 32, 32),
                      new Pixel(142, 142, 142))),
              new ArrayList<>(Arrays.asList(new Pixel(127, 127, 127),
                      new Pixel(148, 148, 148),
                      new Pixel(1, 1, 1))),
              new ArrayList<>(Arrays.asList(new Pixel(186, 186, 186),
                      new Pixel(64, 64, 64),
                      new Pixel(210, 210, 210))),
              new ArrayList<>(Arrays.asList(new Pixel(102, 102, 102),
                      new Pixel(1, 1, 1),
                      new Pixel(113, 113, 113)))));
    }
  }

  /**
   * Tests for the ValueComponent implementation of ImageProcessorCommand.
   */
  public static final class ValueComponentTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new ValueComponent(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
              new ArrayList<>(Arrays.asList(
                      new Pixel(243, 243, 243),
                      new Pixel(135, 135, 135),
                      new Pixel(252, 252, 252))),
              new ArrayList<>(Arrays.asList(new Pixel(207, 207, 207),
                      new Pixel(163, 163, 163),
                      new Pixel(94, 94, 94))),
              new ArrayList<>(Arrays.asList(new Pixel(186, 186, 186),
                      new Pixel(139, 139, 139),
                      new Pixel(210, 210, 210))),
              new ArrayList<>(Arrays.asList(new Pixel(129, 129, 129),
                      new Pixel(92, 92, 92),
                      new Pixel(177, 177, 177)))));
    }
  }

  /**
   * Tests for the IntensityComponent implementation of ImageProcessorCommand.
   */
  public static final class IntensityComponentTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new IntensityComponent(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
              new ArrayList<>(Arrays.asList(
                      new Pixel(174, 174, 174),
                      new Pixel(74, 74, 74),
                      new Pixel(182, 182, 182))),
              new ArrayList<>(Arrays.asList(new Pixel(175, 175, 175),
                      new Pixel(130, 130, 130),
                      new Pixel(53, 53, 53))),
              new ArrayList<>(Arrays.asList(new Pixel(171, 171, 171),
                      new Pixel(91, 91, 91),
                      new Pixel(152, 152, 152))),
              new ArrayList<>(Arrays.asList(new Pixel(100, 100, 100),
                      new Pixel(57, 57, 57),
                      new Pixel(123, 123, 123)))));
    }
  }

  /**
   * Tests for the LumaComponent implementation of ImageProcessorCommand.
   */
  public static final class LumaComponentTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new LumaComponent(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
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
    }
  }

  /**
   * Tests for the VerticalFlip implementation of ImageProcessorCommand.
   */
  public static final class VerticalFlipTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new VerticalFlip(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
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
    }
  }

  /**
   * Tests for the HorizontalFlip implementation of ImageProcessorCommand.
   */
  public static final class HorizontalFlipTest extends ImageOperationCommandTest {
    @Override
    protected ImageProcessorCommand createCommand(String imageName, String destImageName) {
      return new HorizontalFlip(imageName, destImageName);
    }

    @Override
    protected List<List<Pixel>> getExpectedPixels() {
      return new ArrayList<>(Arrays.asList(
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
    }
  }
}
import org.junit.Before;
import org.junit.Test;

import controller.command.ImageProcessorCommand;
import controller.command.Load;
import model.ImageModel;
import model.ProcessorModel;
import model.ProcessorModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for controller.command.Load class.
 */
public class LoadTest {
  ProcessorModel koalaProcessor;
  ImageProcessorCommand load;

  @Before
  public void setUp() throws Exception {
    this.koalaProcessor = new ProcessorModelImpl();
    this.load = new Load("res/Koala.ppm", "koala");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImagePath() {
    ImageProcessorCommand loadFail = new Load(null, "image");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImageName() {
    ImageProcessorCommand loadFail = new Load("res/image.ppm", null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testEmptyImagePath() {
    ImageProcessorCommand loadFail = new Load("", "image");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testEmptyImageName() {
    ImageProcessorCommand loadFail = new Load("res/image.ppm", "");
  }

  @Test
  public void testExecuteCommand() {
    this.load.executeCommand(this.koalaProcessor);

    // check that the image can be found in processor
    ImageModel koalaImage = this.koalaProcessor.getImage("koala");

    // check if contents of the image loaded correctly
    assertEquals(1024, koalaImage.getWidth());
    assertEquals(768, koalaImage.getHeight());
    assertEquals(255, koalaImage.getMaxRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadExceptionFilePathDoesNotExist() {
    ImageProcessorCommand loadFail = new Load("NotrealPath.ppm", "koala");
    loadFail.executeCommand(this.koalaProcessor);
  }
}
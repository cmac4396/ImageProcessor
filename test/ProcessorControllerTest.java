import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.io.StringReader;

import controller.MockReadable;
import controller.ProcessorController;
import controller.ProcessorControllerImpl;
import model.ProcessorModel;
import model.ProcessorModelImpl;
import view.MockAppendable;
import view.ProcessorView;
import view.ProcessorViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the controller.ProcessorController interface and its implementations.
 */
public class ProcessorControllerTest {
  Appendable out;
  ProcessorModel koalaProcessor;
  ProcessorView koalaProcessorView;
  ProcessorController koalaProcessorController;

  @Before
  public void setUp() {
    this.out = new StringBuffer();
    this.koalaProcessor = new ProcessorModelImpl();
    this.koalaProcessorView = new ProcessorViewImpl(this.out);
  }

  @Test
  public void testProcessorControllerConstructorSuccessful() {
    try {
      this.setKoalaControllerReadable(new InputStreamReader(System.in));
    }
    catch (Exception e) {
      fail("error: controller.ProcessorControllerImpl constructor throws unexpected exception");
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullModel() {
    new ProcessorControllerImpl(null, this.koalaProcessorView, new StringReader(""));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullView() {
    new ProcessorControllerImpl(this.koalaProcessor, null, new StringReader(""));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullReadable() {
    new ProcessorControllerImpl(this.koalaProcessor, this.koalaProcessorView, null);
  }

  // sets this koala controller to one with the given Readable
  private void setKoalaControllerReadable(Readable inputs) {
    this.koalaProcessorController = new ProcessorControllerImpl(this.koalaProcessor,
            this.koalaProcessorView, inputs);
  }

  @Test
  public void testRunProcessor() {
    StringBuilder inputBuilder = new StringBuilder("");
    // should prompt re-enter:
    // unknown command
    inputBuilder.append("laod res/Koala.ppm koala\n");
    // not enough arguments given
    inputBuilder.append("load res/Koala.ppm\n");
    // correct
    inputBuilder.append("load res/Koala.ppm koala\n");
    // given String instead of int for brighten
    inputBuilder.append("brighten notANumber koala bright-koala\n");
    // no args given
    inputBuilder.append("load      \n");
    // loading from nonexistent image path
    inputBuilder.append("load res/notAnImage.ppm koala-typo\n");
    // loading from wrong file format
    inputBuilder.append("load res/cube.gif cube\n");
    // loading from a file that is not the correct format
    inputBuilder.append("load res/badPPM.ppm koala-bad\n");
    // saving an image that does not exist in the processor
    inputBuilder.append("save res/badSave.ppm kaola\n");

    // testing normal commands
    inputBuilder.append("save Koala2.ppm koala\n");
    inputBuilder.append("red-component koala koala-red\n");
    inputBuilder.append("green-component koala koala-green\n");
    inputBuilder.append("blue-component koala koala-blue\n");
    inputBuilder.append("value-component koala koala-value\n");
    inputBuilder.append("intensity-component koala koala-intensity\n");
    inputBuilder.append("luma-component koala koala-luma\n");
    inputBuilder.append("vertical-flip koala koala-vertical-flip\n");
    inputBuilder.append("horizontal-flip koala koala-horizontal-flip\n");
    inputBuilder.append("brighten 10 koala koala-bright\n");
    inputBuilder.append("blur koala koala-blur\n");
    inputBuilder.append("sharpen koala koala-sharpen\n");
    inputBuilder.append("greyscale koala koala-greyscale\n");
    inputBuilder.append("sepia koala koala-sepia\n");

    // stack filters
    inputBuilder.append("horizontal-flip koala-vertical-flip koala-flipped-twice\n");
    inputBuilder.append("quit\n");

    this.setKoalaControllerReadable(new StringReader(inputBuilder.toString()));
    this.koalaProcessorController.runProcessor();

    String expectedOutput = "Welcome to the image processor!\n"
            + "Please enter a command in one of the following formats to start: \n"
            + "load image-path image-name\n"
            + "save image-path image-name\n"
            + "red-component image-name dest-image-name\n"
            + "green-component image-name dest-image-name\n"
            + "blue-component image-name dest-image-name\n"
            + "value-component image-name dest-image-name\n"
            + "intensity-component image-name dest-image-name\n"
            + "luma-component image-name dest-image-name\n"
            + "vertical-flip image-name dest-image-name\n"
            + "horizontal-flip image-name dest-image-name\n"
            + "brighten increment image-name dest-image-name\n"
            + "blur image-name dest-image-name\n"
            + "sharpen image-name dest-image-name\n"
            + "greyscale image-name dest-image-name\n"
            + "sepia image-name dest-image-name\n"
            + "Error: command laod not found. Please re-enter: \n"
            + "Error: Not enough arguments given. Please re-enter: \n"
            + "Command executed successfully!\n"
            + "Error: Non-integer value given when integer arg expected. Please re-enter: \n"
            + "Error: Not enough arguments given. Please re-enter: \n"
            + "error: loading from an imagePath that does not exist\n"
            + "Please re-enter: \n"
            + "error: invalid file given\n"
            + "Please re-enter: \n"
            + "error: file format invalid\n"
            + "Please re-enter: \n"
            + "error: trying to save imageName not found in processor\n"
            + "Please re-enter: \n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Command executed successfully!\n"
            + "Image processor quit. Goodbye!";
    assertEquals(expectedOutput, this.out.toString());
  }

  @Test (expected = IllegalStateException.class)
  public void testRunProcessorExceptionFailToWrite() {
    ProcessorView mockView = new ProcessorViewImpl(new MockAppendable());
    ProcessorController mockController = new ProcessorControllerImpl(this.koalaProcessor, mockView,
            new StringReader(""));
    mockController.runProcessor();
  }

  @Test (expected = IllegalStateException.class)
  public void testRunProcessorExceptionFailToRead() {
    this.setKoalaControllerReadable(new MockReadable());
    koalaProcessorController.runProcessor();
  }

  @Test (expected = IllegalStateException.class)
  public void testRunProcessorExceptionNoQuit() {
    StringBuilder inputBuilder = new StringBuilder("");
    inputBuilder.append("load Koala.ppm koala\n");
    this.setKoalaControllerReadable(new StringReader(inputBuilder.toString()));
    this.koalaProcessorController.runProcessor();
  }
}
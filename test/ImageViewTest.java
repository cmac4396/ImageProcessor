import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.ImageModel;
import model.ImageModelImpl;
import model.Pixel;
import view.ImageViewImpl;
import view.ImageView;
import view.ImageViewIOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for the ImageView class and its implementations.
 */
public class ImageViewTest {
  List<List<Pixel>> twoByTwoPixels;
  ImageModel twoByTwoModel;
  ImageView twoByTwoView;

  List<List<Pixel>> twoByThreePixels;
  ImageModel twoByThreeModel;
  ImageView twoByThreeView;

  List<List<Pixel>> threeByThreePixels;
  ImageModel threeByThreeModel;
  ImageView threeByThreeView;


  List<List<Pixel>> tenByTenPixels;
  ImageModel tenByTenModel;
  ImageView tenByTenView;

  @Before
  public void setUp() {
    this.twoByTwoPixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(76, 0, 153),
                    new Pixel(76, 0, 153))),
            new ArrayList<>(Arrays.asList(new Pixel(102, 0, 204),
                    new Pixel(102, 0, 204)))));
    this.twoByTwoModel = new ImageModelImpl(this.twoByTwoPixels, "purple", 220);
    this.twoByTwoView = new ImageViewImpl(this.twoByTwoModel);

    this.twoByThreePixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(100, 150, 100),
                    new Pixel(120, 170, 120),
                    new Pixel(140, 190, 140))),
            new ArrayList<>(Arrays.asList(new Pixel(100, 150, 100),
                    new Pixel(120, 170, 120),
                    new Pixel(140, 190, 140)))));
    this.twoByThreeModel = new ImageModelImpl(this.twoByThreePixels, "pattern",
            255);
    this.twoByThreeView = new ImageViewImpl(this.twoByThreeModel);

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
    this.threeByThreeModel = new ImageModelImpl(this.threeByThreePixels, "checkered",
            255);
    this.threeByThreeView = new ImageViewImpl(this.threeByThreeModel);

    this.tenByTenPixels = new ArrayList<>(Arrays.asList(
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
    this.tenByTenModel = new ImageModelImpl(this.tenByTenPixels, "palette");
    this.tenByTenView = new ImageViewImpl(this.tenByTenModel);
  }

  @Test
  public void testWriteToFilePPM() {
    try {
      this.twoByTwoView.writeToFile("purple.ppm");
      StringBuilder purpleContents = new StringBuilder("");

      // open newly created file and get contents
      FileReader purplePPM = new FileReader("purple.ppm");
      BufferedReader purplePPMReader = new BufferedReader(purplePPM);
      String data = purplePPMReader.readLine();
      while (data != null) {
        purpleContents.append(data);
        purpleContents.append("\n");
        data = purplePPMReader.readLine();
      }
      purplePPMReader.close();

      String expectedContents = "P3 2 2\n"
              + "220\n"
              + "76\n0\n153\n"
              + "76\n0\n153\n"
              + "102\n0\n204\n"
              + "102\n0\n204\n";

      assertEquals(expectedContents, purpleContents.toString());
    }
    catch (IOException e) {
      fail("error: writeToFile throws unexpected exception");
    }
  }

  @Test
  public void testWriteToFilePNG() {
    try {
      this.twoByThreeView.writeToFile("res/pattern.png");
      StringBuilder patternContents = new StringBuilder("");

      // check if file exists
      File f = new File("res/pattern.png");
      assertTrue(f.exists());
    } catch (IOException e) {
      fail("error: writeToFile throws unexpected exception");
    }
  }

  @Test
  public void testWriteToFileBMP() {
    try {
      this.threeByThreeView.writeToFile("res/bnw.bmp");

      File f = new File("res/bnw.bmp");
      assertTrue(f.exists());
    }
    catch (IOException e) {
      fail("error: writeToFile throws unexpected exception");
    }
  }

  @Test
  public void testWriteToFileJPG() {
    try {
      this.tenByTenView.writeToFile("res/palette.jpg");

      File f = new File("res/palette.jpg");
      assertTrue(f.exists());
    }
    catch (IOException e) {
      fail("error: writeToFile throws unexpected exception");
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void testWriteToFileInvalidFileType() throws IllegalArgumentException {
    try {
      this.threeByThreeView.writeToFile("lol.gif");
    }
    catch (IOException e) {
      fail("error: writeToFile throws unexpected exception");
    }
  }

  @Test (expected = IOException.class)
  public void testWriteToFileException() throws IOException {
    ImageView exceptionView = new ImageViewIOException();
    exceptionView.writeToFile("exception.ppm");
  }

}
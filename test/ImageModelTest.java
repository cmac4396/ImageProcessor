import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import model.ImageModel;
import model.ImageModelImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the model.ImageModel interface and its implementations.
 */
public class ImageModelTest {
  List<List<Pixel>> fourByThreePixels;
  List<List<Pixel>> twoByTwoPixels;
  List<List<Pixel>> oneByThreePixels;
  ImageModel fourByThreeImage;
  ImageModel twoByTwoImage;
  ImageModel oneByThreeImage;

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
    this.fourByThreeImage = new ImageModelImpl(fourByThreePixels, "fourByThree",
            254);

    this.twoByTwoPixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(76, 0, 153),
                    new Pixel(76, 0, 153))),
            new ArrayList<>(Arrays.asList(new Pixel(102, 0, 204),
                    new Pixel(102, 0, 204)))));
    this.twoByTwoImage = new ImageModelImpl(this.twoByTwoPixels, "purple", 220);

    this.oneByThreePixels = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new Pixel(255, 51, 153),
                    new Pixel(255, 102, 178),
                    new Pixel(255, 153, 204)))));
    this.oneByThreeImage = new ImageModelImpl(this.oneByThreePixels, "pinker",
            255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionTwoArgsEmptyName() {
    new ImageModelImpl(this.fourByThreePixels, "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionTwoArgsNullName() {
    new ImageModelImpl(this.fourByThreePixels, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionTwoArgsNullPixel() {
    this.fourByThreePixels.add(new ArrayList<Pixel>(Arrays.asList(null,
            new Pixel(92, 80, 1),
            new Pixel(80, 177, 113))));
    new ImageModelImpl(this.fourByThreePixels, "null");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionThreeArgsEmptyName() {
    new ImageModelImpl(this.fourByThreePixels, "", 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionThreeArgsNullName() {
    new ImageModelImpl(this.fourByThreePixels, null, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionThreeArgsNullPixel() {
    this.fourByThreePixels.add(new ArrayList<Pixel>(Arrays.asList(null,
            new Pixel(92, 80, 1),
            new Pixel(80, 177, 113))));
    new ImageModelImpl(this.fourByThreePixels, "null", 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionThreeArgsInvalidMaxRGB() {
    this.fourByThreePixels.add(new ArrayList<Pixel>(Arrays.asList(null,
            new Pixel(92, 80, 1),
            new Pixel(80, 177, 113))));
    new ImageModelImpl(this.fourByThreePixels, "null", -1);
  }

  @Test
  public void testGetName() {
    assertEquals("fourByThree", this.fourByThreeImage.getName());
    assertEquals("purple", this.twoByTwoImage.getName());
    assertEquals("pinker", this.oneByThreeImage.getName());

    ImageModel anotherImage = new ImageModelImpl(this.fourByThreePixels, "anotherImage",
            255);
    ImageModel anotherImage2 = new ImageModelImpl(this.fourByThreePixels, "anotherImage2",
            255);

    assertEquals("anotherImage", anotherImage.getName());
    assertEquals("anotherImage2", anotherImage2.getName());
  }

  @Test
  public void testGetWidth() {
    assertEquals(3, this.fourByThreeImage.getWidth());
    assertEquals(2, this.twoByTwoImage.getWidth());
    assertEquals(3, this.oneByThreeImage.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(4, this.fourByThreeImage.getHeight());
    assertEquals(2, this.twoByTwoImage.getHeight());
    assertEquals(1, this.oneByThreeImage.getHeight());
  }

  @Test
  public void testGetMaxRBGValue() {
    assertEquals(254, this.fourByThreeImage.getMaxRGB());
    assertEquals(220, this.twoByTwoImage.getMaxRGB());
    assertEquals(255, this.oneByThreeImage.getMaxRGB());
  }

  @Test
  public void testGetImageAllRed() {
    ImageModel fourByThreeRed = this.fourByThreeImage.getImageAllRed("fourByThreeRed");
    List<List<Pixel>> redPixels = fourByThreeRed.getPixels();

    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = redPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getRed(), currentPixel.getRed());
        assertEquals(originalPixel.getRed(), currentPixel.getGreen());
        assertEquals(originalPixel.getRed(), currentPixel.getBlue());
      }
    }
    assertEquals("fourByThreeRed", fourByThreeRed.getName());
  }

  @Test
  public void testGetImageAllBlue() {
    ImageModel fourByThreeBlue = this.fourByThreeImage.getImageAllBlue("fourByThreeBlue");
    List<List<Pixel>> bluePixels = fourByThreeBlue.getPixels();

    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = bluePixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getBlue(), currentPixel.getRed());
        assertEquals(originalPixel.getBlue(), currentPixel.getGreen());
        assertEquals(originalPixel.getBlue(), currentPixel.getBlue());
      }
    }
    assertEquals("fourByThreeBlue", fourByThreeBlue.getName());
  }

  @Test
  public void testGetImageAllGreen() {
    ImageModel fourByThreeGreen = this.fourByThreeImage.getImageAllGreen("fourByThreeGreen");
    List<List<Pixel>> greenPixels = fourByThreeGreen.getPixels();

    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = greenPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getGreen(), currentPixel.getRed());
        assertEquals(originalPixel.getGreen(), currentPixel.getGreen());
        assertEquals(originalPixel.getGreen(), currentPixel.getBlue());
      }
    }
    assertEquals("fourByThreeGreen", fourByThreeGreen.getName());
  }

  @Test
  public void testGetImageAllValue() {
    ImageModel fourByThreeValue = this.fourByThreeImage.getImageAllValue("fourByThreeValue");
    List<List<Pixel>> valuePixels = fourByThreeValue.getPixels();

    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = valuePixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getValue(), currentPixel.getRed());
        assertEquals(originalPixel.getValue(), currentPixel.getGreen());
        assertEquals(originalPixel.getValue(), currentPixel.getBlue());
      }
    }
    assertEquals("fourByThreeValue", fourByThreeValue.getName());
  }

  @Test
  public void testGetImageAllIntensity() {
    ImageModel fourByThreeIntensity =
            this.fourByThreeImage.getImageAllIntensity("fourByThreeIntensity");
    List<List<Pixel>> intensityPixels = fourByThreeIntensity.getPixels();

    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = intensityPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getIntensity(), currentPixel.getRed());
        assertEquals(originalPixel.getIntensity(), currentPixel.getGreen());
        assertEquals(originalPixel.getIntensity(), currentPixel.getBlue());
      }
    }
    assertEquals("fourByThreeIntensity", fourByThreeIntensity.getName());
  }

  @Test
  public void testGetImageAllLuma() {
    ImageModel fourByThreeLuma = this.fourByThreeImage.getImageAllLuma("fourByThreeLuma");
    List<List<Pixel>> lumaPixels = fourByThreeLuma.getPixels();

    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = lumaPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getLuma(), currentPixel.getRed());
        assertEquals(originalPixel.getLuma(), currentPixel.getGreen());
        assertEquals(originalPixel.getLuma(), currentPixel.getBlue());
      }
    }
    assertEquals("fourByThreeLuma", fourByThreeLuma.getName());
  }

  @Test
  public void testGetImageFlipHorizontal() {
    ImageModel fourByThreeHorizontalFlip =
            this.fourByThreeImage.getImageFlipHorizontal("fourByThreeHorizontalFlip");

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
        Pixel actualPixel = fourByThreeHorizontalFlip.getPixels().get(row).get(col);

        assertEquals(expectedPixel.getRed(), actualPixel.getRed());
        assertEquals(expectedPixel.getGreen(), actualPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), actualPixel.getBlue());
      }
    }
    assertEquals("fourByThreeHorizontalFlip", fourByThreeHorizontalFlip.getName());
  }

  @Test
  public void testGetImageFlipVertical() {
    ImageModel fourByThreeVerticalFlip =
            this.fourByThreeImage.getImageFlipVertical("fourByThreeVerticalFlip");

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
        Pixel actualPixel = fourByThreeVerticalFlip.getPixels().get(row).get(col);

        assertEquals(expectedPixel.getRed(), actualPixel.getRed());
        assertEquals(expectedPixel.getGreen(), actualPixel.getGreen());
        assertEquals(expectedPixel.getBlue(), actualPixel.getBlue());
      }
    }
    assertEquals("fourByThreeVerticalFlip", fourByThreeVerticalFlip.getName());
  }

  @Test
  public void testGetImageAllAdjustBrightnessIncrement() {
    ImageModel fourByThreeBrightenOne =
            this.fourByThreeImage.getImageAdjustBrightness("fourByThreeBrightenOne", 1);
    List<List<Pixel>> adjustedPixels = fourByThreeBrightenOne.getPixels();

    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = adjustedPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getRed() + 1, currentPixel.getRed());
        assertEquals(originalPixel.getGreen() + 1, currentPixel.getGreen());
        assertEquals(originalPixel.getBlue() + 1, currentPixel.getBlue());
      }
    }
    assertEquals("fourByThreeBrightenOne", fourByThreeBrightenOne.getName());
  }

  @Test
  public void testGetImageAllAdjustBrightnessIncrementOverMaxRGB() {
    ImageModel twoByTwoBrightenTwenty =
            this.twoByTwoImage.getImageAdjustBrightness("twoByTwoBrightenTwenty", 20);
    List<List<Pixel>> adjustedPixels = twoByTwoBrightenTwenty.getPixels();

    for (int row = 0; row < twoByTwoPixels.size(); row++) {
      for (int col = 0; col < twoByTwoPixels.get(row).size(); col++) {
        Pixel currentPixel = adjustedPixels.get(row).get(col);
        Pixel originalPixel = twoByTwoPixels.get(row).get(col);

        if ((row == 1 && col == 0) || (row == 1 && col == 1)) {
          assertEquals(originalPixel.getRed() + 20, currentPixel.getRed());
          assertEquals(originalPixel.getGreen() + 20, currentPixel.getGreen());
          assertEquals(220, currentPixel.getBlue());
        }
        else {
          assertEquals(originalPixel.getRed() + 20, currentPixel.getRed());
          assertEquals(originalPixel.getGreen() + 20, currentPixel.getGreen());
          assertEquals(originalPixel.getBlue() + 20, currentPixel.getBlue());
        }
      }
    }
    assertEquals("twoByTwoBrightenTwenty", twoByTwoBrightenTwenty.getName());
  }

  @Test
  public void testGetImageAllAdjustBrightnessDecrement() {
    ImageModel fourByThreeDarkenOne =
            this.fourByThreeImage.getImageAdjustBrightness("fourByThreeDarkenOne", -1);
    List<List<Pixel>> adjustedPixels = fourByThreeDarkenOne.getPixels();

    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = adjustedPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getRed() - 1, currentPixel.getRed());
        assertEquals(originalPixel.getGreen() - 1, currentPixel.getGreen());
        assertEquals(originalPixel.getBlue() - 1, currentPixel.getBlue());
      }
    }
    assertEquals("fourByThreeDarkenOne", fourByThreeDarkenOne.getName());
  }

  @Test
  public void testApplyFilter() {
    double[][] blurKernel = {{0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};
    ImageModel imageBlur = this.fourByThreeImage.applyFilter("imageBlur", blurKernel);

    List<List<Pixel>> blurPixels = null;
    // check to see that the new image exists in the processor
    try {
      blurPixels = imageBlur.getPixels();
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
    assertEquals(254, imageBlur.getMaxRGB());

    // check name
    assertEquals("imageBlur", imageBlur.getName());
  }

  @Test
  public void testApplyColorTransformation() {
    double[][] lumaKernel = {{0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}};

    ImageModel fourByThreeLuma =
            this.fourByThreeImage.applyColorTransformation("fourByThreeLuma", lumaKernel);
    List<List<Pixel>> lumaPixels = fourByThreeLuma.getPixels();

    for (int row = 0; row < fourByThreePixels.size(); row++) {
      for (int col = 0; col < fourByThreePixels.get(row).size(); col++) {
        Pixel currentPixel = lumaPixels.get(row).get(col);
        Pixel originalPixel = fourByThreePixels.get(row).get(col);

        assertEquals(originalPixel.getLuma(), currentPixel.getRed());
        assertEquals(originalPixel.getLuma(), currentPixel.getGreen());
        assertEquals(originalPixel.getLuma(), currentPixel.getBlue());
      }
    }
    assertEquals("fourByThreeLuma", fourByThreeLuma.getName());
  }

  @Test
  public void testGetHistogram() {
    Map<Integer, Integer> fourByThreeHistogram =
            this.oneByThreeImage.getHistogram(Pixel.getRedFunc());

    int value255 = fourByThreeHistogram.getOrDefault(255, -1);

    assertEquals(3, value255);
  }
}
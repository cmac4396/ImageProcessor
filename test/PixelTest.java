import org.junit.Before;
import org.junit.Test;

import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the model.Pixel class.
 */
public class PixelTest {
  private Pixel pixel;
  private Pixel pixelDiff;
  private Pixel allRed;
  private Pixel allGreen;
  private Pixel allBlue;
  private Pixel pixelRepl;

  @Before
  public void setUp() throws Exception {
    pixel = new Pixel(120, 120, 120);
    pixelDiff = new Pixel(124, 32, 76);
    allRed = new Pixel(255, 0, 0);
    allGreen = new Pixel(0, 255, 0);
    allBlue = new Pixel(0, 0, 255);
    pixelRepl = new Pixel(pixelDiff);
  }

  // testing constructor
  @Test
  public void testConstructorAllSame() {
    assertEquals(120, this.pixel.getRed());
    assertEquals(120, this.pixel.getGreen());
    assertEquals(120, this.pixel.getBlue());
  }

  @Test
  public void testConstructorDiffValues() {
    assertEquals(124, this.pixelDiff.getRed());
    assertEquals(32, this.pixelDiff.getGreen());
    assertEquals(76, this.pixelDiff.getBlue());
  }

  @Test
  public void testCopyConstructor() {
    assertEquals(124, this.pixelRepl.getRed());
    assertEquals(32, this.pixelRepl.getGreen());
    assertEquals(76, this.pixelRepl.getBlue());
  }

  // testing throwing exception
  @Test (expected = IllegalArgumentException.class)
  public void testConstructorInvalidRed() {
    new Pixel(-3, 120, 120);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorInvalidGreen() {
    new Pixel(120, 300, 120);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorInvalidBlue() {
    new Pixel(120, 120, -255);
  }

  @Test (expected = NullPointerException.class)
  public void testConstructorNullValue() {
    new Pixel(null);
  }

  @Test
  public void testGetRed() {
    assertEquals(120, this.pixel.getRed());
    assertEquals(255, this.allRed.getRed());
    assertEquals(0, this.allGreen.getRed());
    assertEquals(0, this.allBlue.getRed());
  }

  @Test
  public void testGetGreen() {
    assertEquals(120, this.pixel.getGreen());
    assertEquals(0, this.allRed.getGreen());
    assertEquals(255, this.allGreen.getGreen());
    assertEquals(0, this.allBlue.getGreen());
  }

  @Test
  public void testGetBlue() {
    assertEquals(120, this.pixel.getBlue());
    assertEquals(0, this.allRed.getBlue());
    assertEquals(0, this.allGreen.getBlue());
    assertEquals(255, this.allBlue.getBlue());
  }

  @Test
  public void testGetValue() {
    assertEquals(120, this.pixel.getValue());
    assertEquals(255, this.allRed.getValue());
    assertEquals(255, this.allBlue.getValue());
    assertEquals(124, this.pixelDiff.getValue());
    assertEquals(124, this.pixelRepl.getValue());
  }

  @Test
  public void testGetIntensity() {
    assertEquals(120, this.pixel.getIntensity());
    assertEquals(85, this.allRed.getIntensity());
    assertEquals(85, this.allBlue.getIntensity());
    assertEquals(77, this.pixelDiff.getIntensity());
    assertEquals(77, this.pixelRepl.getIntensity());
  }

  @Test
  public void testGetLuma() {
    assertEquals(120, this.pixel.getLuma());
    assertEquals(54, this.allRed.getLuma());
    assertEquals(18, this.allBlue.getLuma());
    assertEquals(54, this.pixelDiff.getLuma());
    assertEquals(54, this.pixelRepl.getLuma());
  }

  @Test
  public void testSetComponentsTo() {
    Pixel allZeroes = this.pixelDiff.setComponentsTo(0);

    assertEquals(allZeroes.getRed(), 0);
    assertEquals(allZeroes.getGreen(), 0);
    assertEquals(allZeroes.getBlue(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetComponentToExceptionValueNegative() {
    this.pixelDiff.setComponentsTo(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetComponentToExceptionValueTooLarge() {
    this.pixelDiff.setComponentsTo(256);
  }

  @Test
  public void testAllRed() {
    Pixel allRedPixelDiff = Pixel.allRed().apply(this.pixelDiff);

    assertEquals(124, allRedPixelDiff.getRed());
    assertEquals(124, allRedPixelDiff.getGreen());
    assertEquals(124, allRedPixelDiff.getBlue());
  }

  @Test
  public void testAllGreen() {
    Pixel allGreenPixelDiff = Pixel.allGreen().apply(this.pixelDiff);

    assertEquals(32, allGreenPixelDiff.getRed());
    assertEquals(32, allGreenPixelDiff.getGreen());
    assertEquals(32, allGreenPixelDiff.getBlue());
  }

  @Test
  public void testAllBlue() {
    Pixel allBluePixelDiff = Pixel.allBlue().apply(this.pixelDiff);

    assertEquals(76, allBluePixelDiff.getRed());
    assertEquals(76, allBluePixelDiff.getGreen());
    assertEquals(76, allBluePixelDiff.getBlue());
  }

  @Test
  public void testAllValue() {
    Pixel allValuePixelDiff = Pixel.allValue().apply(this.pixelDiff);

    assertEquals(124, allValuePixelDiff.getRed());
    assertEquals(124, allValuePixelDiff.getGreen());
    assertEquals(124, allValuePixelDiff.getBlue());
  }

  @Test
  public void testAllIntensity() {
    Pixel allIntensityPixelDiff = Pixel.allIntensity().apply(this.pixelDiff);

    assertEquals(77, allIntensityPixelDiff.getRed());
    assertEquals(77, allIntensityPixelDiff.getGreen());
    assertEquals(77, allIntensityPixelDiff.getBlue());
  }

  @Test
  public void testAllLuma() {
    Pixel allLumaDiff = Pixel.allLuma().apply(this.pixelDiff);

    assertEquals(54, allLumaDiff.getRed());
    assertEquals(54, allLumaDiff.getGreen());
    assertEquals(54, allLumaDiff.getBlue());
  }

  @Test
  public void testAllAdjustBrightnessIncrement() {
    Pixel allAdjustBrightnessPixelDiff =
            this.pixelDiff.allAdjustBrightness(10, 255).apply(this.pixelDiff);

    assertEquals(134, allAdjustBrightnessPixelDiff.getRed());
    assertEquals(42, allAdjustBrightnessPixelDiff.getGreen());
    assertEquals(86, allAdjustBrightnessPixelDiff.getBlue());
  }

  @Test
  public void testAllAdjustBrightnessDecrement() {
    Pixel allAdjustBrightnessPixelDiff =
            this.pixelDiff.allAdjustBrightness(-10, 255).apply(this.pixelDiff);

    assertEquals(114, allAdjustBrightnessPixelDiff.getRed());
    assertEquals(22, allAdjustBrightnessPixelDiff.getGreen());
    assertEquals(66, allAdjustBrightnessPixelDiff.getBlue());
  }

  @Test
  public void testAllAdjustBrightnessTooLow() {
    Pixel allAdjustBrightnessPixelDiff =
            this.pixelDiff.allAdjustBrightness(-40, 255).apply(this.pixelDiff);

    assertEquals(84, allAdjustBrightnessPixelDiff.getRed());
    assertEquals(0, allAdjustBrightnessPixelDiff.getGreen());
    assertEquals(36, allAdjustBrightnessPixelDiff.getBlue());
  }

  @Test
  public void testAllAdjustBrightnessTooHigh() {
    Pixel allAdjustBrightnessPixelDiff =
            this.pixelDiff.allAdjustBrightness(150, 255).apply(this.pixelDiff);

    assertEquals(255, allAdjustBrightnessPixelDiff.getRed());
    assertEquals(182, allAdjustBrightnessPixelDiff.getGreen());
    assertEquals(226, allAdjustBrightnessPixelDiff.getBlue());
  }

  @Test
  public void testApplyColorTransformation() {
    double[][] lumaKernel = {{0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}};
    Pixel applyColorTransformationDiff =
            this.pixelDiff.applyColorTransformation(lumaKernel).apply(this.pixelDiff);
    assertEquals(54, applyColorTransformationDiff.getRed());
    assertEquals(54, applyColorTransformationDiff.getGreen());
    assertEquals(54, applyColorTransformationDiff.getBlue());
  }

  @Test
  public void testAdjustValue() {
    // no adjustment needed
    assertEquals(240, Pixel.adjustValue(240));

    // too low
    assertEquals(0, Pixel.adjustValue(-4));

    // too high
    assertEquals(255, Pixel.adjustValue(268));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testApplyColorTransformationInvalidKernel() {
    double[][] kernel = {{0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}};
    Pixel applyColorTransformationDiff =
            this.pixelDiff.applyColorTransformation(kernel).apply(this.pixelDiff);
  }

  @Test
  public void testGetRedFunc() {
    int pixelVal = Pixel.getRedFunc().apply(this.pixel);
    assertEquals(120, pixelVal);
    int allRedVal = Pixel.getRedFunc().apply(this.allRed);
    assertEquals(255, allRedVal);
    int allGreenVal = Pixel.getRedFunc().apply(this.allGreen);
    assertEquals(0, allGreenVal);
    int pixelDiffVal = Pixel.getRedFunc().apply(this.pixelDiff);
    assertEquals(124, pixelDiffVal);
  }

  @Test
  public void testGetGreenFunc() {
    int pixelVal = Pixel.getGreenFunc().apply(this.pixel);
    assertEquals(120, pixelVal);
    int allRedVal = Pixel.getGreenFunc().apply(this.allRed);
    assertEquals(0, allRedVal);
    int allGreenVal = Pixel.getGreenFunc().apply(this.allGreen);
    assertEquals(255, allGreenVal);
    int pixelDiffVal = Pixel.getGreenFunc().apply(this.pixelDiff);
    assertEquals(32, pixelDiffVal);
  }

  @Test
  public void testGetBlueFunc() {
    int pixelVal = Pixel.getBlueFunc().apply(this.pixel);
    assertEquals(120, pixelVal);
    int allRedVal = Pixel.getBlueFunc().apply(this.allRed);
    assertEquals(0, allRedVal);
    int allBlueVal = Pixel.getBlueFunc().apply(this.allBlue);
    assertEquals(255, allBlueVal);
    int pixelDiffVal = Pixel.getBlueFunc().apply(this.pixelDiff);
    assertEquals(76, pixelDiffVal);
  }

  @Test
  public void testGetIntensityFunc() {
    int pixelVal = Pixel.getIntensityFunc().apply(this.pixel);
    assertEquals(120, pixelVal);
    int allRedVal = Pixel.getIntensityFunc().apply(this.allRed);
    assertEquals(85, allRedVal);
    int allBlueVal = Pixel.getIntensityFunc().apply(this.allBlue);
    assertEquals(85, allBlueVal);
    int pixelDiffVal = Pixel.getIntensityFunc().apply(this.pixelDiff);
    assertEquals(77, pixelDiffVal);
  }
}
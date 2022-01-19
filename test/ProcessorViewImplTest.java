import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import view.ProcessorView;
import view.ProcessorViewImpl;

import static org.junit.Assert.fail;

/**
 * Tests for class view.ProcessorView.
 */
public class ProcessorViewImplTest {
  private ProcessorView fourByThreeView;

  @Before
  public void setUp() {
    this.fourByThreeView = new ProcessorViewImpl();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNullAppendableTwoParam() {
    ProcessorView nullAppendable = new ProcessorViewImpl(null);
  }

  @Test
  public void testRenderMessage() {
    try {
      Appendable out = new StringBuilder();
      this.fourByThreeView = new ProcessorViewImpl(out);
      this.fourByThreeView.renderMessage("message!!");
    }
    catch (IOException e) {
      fail("error: view.ProcessorViewImpl.renderMessage() throws unexpected IO exception");
    }
  }

  @Test (expected = IOException.class)
  public void testRenderMessageException() throws IOException {
    view.MockAppendable out = new view.MockAppendable();
    this.fourByThreeView = new view.ProcessorViewImpl(out);
    this.fourByThreeView.renderMessage("message!!");
  }
}

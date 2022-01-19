import org.junit.Before;
import org.junit.Test;

import java.awt.Component;
import java.awt.Button;
import java.awt.event.ActionEvent;

import controller.ProcessorGUIControllerImpl;
import model.ProcessorModel;
import model.ProcessorModelImpl;
import view.MockGUIView;
import view.ProcessorGUIView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the ProcessorGUIControllerImpl class.
 */
public class ProcessorGUIControllerImplTest {
  ProcessorModel model;
  ProcessorGUIView mockView;
  Appendable out;
  ProcessorGUIControllerImpl controller;

  @Before
  public void setUp() throws Exception {
    this.model = new ProcessorModelImpl();
    this.out = new StringBuilder("");
    this.mockView = new MockGUIView(out);

    this.controller = new ProcessorGUIControllerImpl(model, mockView);
  }

  @Test
  public void testActionPerformed() {
    Component blankComponent = new Button();
    // call action performed using a mock view that prints to appendable when a method is called
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Open"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Save"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Open"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Red Component"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Green Component"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Blue Component"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Value Component"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Intensity Component"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Luma Component"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Horizontal Flip"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Vertical Flip"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Brighten"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Blur"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Sharpen"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Greyscale"));
    this.controller.actionPerformed(new ActionEvent(blankComponent, ActionEvent.ACTION_PERFORMED,
            "Sepia"));

    String expectedOutput = "Called createMenuBar\n"
            + "Called getPathToOpen\n"
            + "Called refresh\n"
            + "Called getPathToSave\n"
            + "Called refresh\n"
            + "Called getPathToOpen\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called getBrightenIncrement\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n"
            + "Called refresh\n";
    assertEquals(expectedOutput, this.out.toString());
  }
}
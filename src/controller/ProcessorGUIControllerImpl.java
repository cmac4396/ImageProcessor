package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.function.Function;

import controller.command.ImageProcessorCommand;
import model.ProcessorModel;
import model.ProcessorModelImpl;
import view.ProcessorGUIView;
import view.ProcessorGUIViewImpl;
import view.ProcessorViewImpl;

/**
 * Represents a controller for an interactive image processor program where input is mostly
 * determined using click events and live input if necessary. Also controls a visual display for
 * the image processor so that image operation results can be seen live.
 */
public class ProcessorGUIControllerImpl implements ProcessorController, ActionListener {
  private ProcessorModel model;
  private ProcessorGUIView view;
  private ProcessorController delegate;

  /**
   * Constructs a controller.ProcessorController object.
   *
   * @param model where the data of the image processor is stored
   * @param view  the display of the image processor
   * @throws IllegalArgumentException if model, view, or input is null
   */
  public ProcessorGUIControllerImpl(ProcessorModel model, ProcessorGUIView view) {
    if (model == null || view == null) {
      throw new IllegalStateException("error: given null objects");
    }
    this.model = model;
    this.view = view;
    this.delegate = new ProcessorControllerImpl(model, new ProcessorViewImpl(),
            new StringReader(" "));

    // creates the menu bar for image operations and adds the controller as the ActionListener
    this.view.createMenuBar(this);
  }

  /**
   * Creates a ProcessorGUIControllerImpl object.
   */
  public ProcessorGUIControllerImpl() {
    this.model = new ProcessorModelImpl();
    this.view = new ProcessorGUIViewImpl(this.model);
    this.delegate = new ProcessorControllerImpl(model, new ProcessorViewImpl(),
            new StringReader(" "));

    this.view.createMenuBar(this);
  }

  @Override
  public void runProcessor() {
    this.view.showView(true);
  }

  @Override
  public Function<String[], ImageProcessorCommand> processCommand(String command) {
    return this.delegate.processCommand(command);
  }

  // parses the command name corresponding to the name of a GUI component
  private String componentToCommand(String componentName) {
    return componentName.strip().toLowerCase().replace(' ', '-');
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Open": {
        // if trying to open a file, get the name of the file
        String pathToOpen = this.view.getPathToOpen();
        if (pathToOpen != null) {
          // parse a name for the image to be used in the processor and load into processor
          String[] parsedPath = pathToOpen.split("/");
          String imageName = parsedPath[parsedPath.length - 1].split("\\.")[0];
          this.model.load(pathToOpen, imageName);

          // load image to view
          this.view.updateImage(imageName);
        }
        break;
      }
      case "Save": {
        String pathToSave = this.view.getPathToSave();
        if (pathToSave != null) {
          try {
            this.model.save(pathToSave, this.model.getNameLastEdited());
          }
          catch (IllegalArgumentException er) {
            this.view.displaySaveError();
          }
        }
        break;
      }
      case "Brighten": {
        try {
          int increment = this.view.getBrightenIncrement();
          String currImageName = this.model.getNameLastEdited();
          String newImageName = currImageName + "Brighten";
          this.model.brighten(increment, currImageName, newImageName);
          this.view.updateImage(newImageName);
        }
        catch (IllegalArgumentException exception) {
          break;
        }

        break;
      }
      default: {
        // for commands that represent image operations with no arguments besides name, use commands
        String currImageName = this.model.getNameLastEdited();
        String newImageName = currImageName
                + e.getActionCommand().replaceAll("\\s+","");

        Function<String[], ImageProcessorCommand> commandFunc;
        // check to see if action should prompt an image operation
        try {
          commandFunc = this.processCommand(this.componentToCommand(e.getActionCommand()));
          commandFunc.apply(new String[]{currImageName, newImageName}).executeCommand(this.model);
        }
        catch (IllegalArgumentException exception) {
          break;
        }
        catch (IllegalStateException exception) {
          break;
        }

        this.view.updateImage(newImageName);
      }
    }
    this.view.refresh();
  }
}


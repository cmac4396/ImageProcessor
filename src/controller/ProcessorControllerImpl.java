package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import controller.command.BlueComponent;
import controller.command.Blur;
import controller.command.Brighten;
import controller.command.GreenComponent;
import controller.command.Greyscale;
import controller.command.HorizontalFlip;
import controller.command.ImageProcessorCommand;
import controller.command.IntensityComponent;
import controller.command.Load;
import controller.command.LumaComponent;
import controller.command.RedComponent;
import controller.command.Save;
import controller.command.Sepia;
import controller.command.Sharpen;
import controller.command.ValueComponent;
import controller.command.VerticalFlip;
import model.ProcessorModel;
import view.ProcessorView;

/**
 * Represents simple functions for the controller.ProcessorController interface.
 */
public class ProcessorControllerImpl implements ProcessorController {
  private final ProcessorModel model;
  private final ProcessorView view;
  private final Readable input;

  /**
   * Constructs a controller.ProcessorController object.
   * @param model                       where the data of the image processor is stored
   * @param view                        the display of the image processor
   * @param input                       the location where user input will be read
   * @throws IllegalArgumentException   if model, view, or input is null
   */
  public ProcessorControllerImpl(ProcessorModel model, ProcessorView view, Readable input) {
    // check null values
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("error: argument null when constructing controller");
    }

    this.model = model;
    this.view = view;
    this.input = input;
  }

  // prints message to display and returns true if message rendered successfully
  protected boolean renderMessageSuccessful(String message) {
    try {
      this.view.renderMessage(message);
    }
    catch (IOException e) {
      return false;
    }
    return true;
  }

  @Override
  public void runProcessor() {
    String welcomeMessage = "Welcome to the image processor!\n"
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
            + "sepia image-name dest-image-name\n";

    // check if displaying welcome message is successful
    if (!renderMessageSuccessful(welcomeMessage)) {
      throw new IllegalStateException("Error: controller failed to write welcome message");
    }

    // create map of commands
    Map<String, Function<String[], ImageProcessorCommand>> knownCommands =
            this.createMapOfCommands();

    Scanner scan = new Scanner(this.input);

    boolean hasNextLine = true;
    while (hasNextLine) {
      String[] userInput;
      try {
        String line = scan.nextLine();
        userInput = line.split(" ");
      }
      catch (NoSuchElementException e) {
        hasNextLine = false;
        continue;
      }

      // parse command as a String array, first String is the command name
      String commandName = userInput[0];

      if (commandName.equalsIgnoreCase("q")
              || commandName.equalsIgnoreCase("quit")) {
        // if user wants to quit, display quit message and stop program
        try {
          this.view.renderMessage("Image processor quit. Goodbye!");
          scan.close();
          return;
        } catch (IOException e) {
          throw new IllegalStateException("error: controller failed to write quit message");
        }
      }

      // get command function from known commands
      Function<String[], ImageProcessorCommand> commandFunction =
              knownCommands.getOrDefault(commandName, null);

      // ask for new input if the command is invalid
      if (commandFunction == null) {
        if (!renderMessageSuccessful(
                "Error: command " + commandName + " not found. Please re-enter: \n")) {
          throw new IllegalStateException("error: controller failed to write message");
        }
      }
      else {
        ImageProcessorCommand command;
        // if not enough arguments provided, ask to reenter
        try {
          command = commandFunction.apply(Arrays.copyOfRange(
                  userInput, 1, userInput.length));
        }
        catch (IndexOutOfBoundsException e) {
          // IndexOutOfBoundsException occurs when there are not enough arguments in the command
          if (!renderMessageSuccessful("Error: Not enough arguments given. Please re-enter: \n")) {
            throw new IllegalStateException("error: controller failed to write message");
          }
          continue;
        }
        catch (NumberFormatException e) {
          // NumberFormatException occurs when expected int argument but given something else
          String errorMessage = "Error: Non-integer value given when integer arg expected."
                  + " Please re-enter: \n";
          if (!renderMessageSuccessful(errorMessage)) {
            throw new IllegalStateException("error: controller failed to write message");
          }
          continue;
        }
        catch (IllegalArgumentException e) {
          // IllegalArgumentException occurs when argument is unexpectedly empty or null
          String errorMessage = "Error: Null or empty argument given to command."
                  + " Please re-enter: \n";
          if (!renderMessageSuccessful(errorMessage)) {
            throw new IllegalStateException("error: controller failed to write message");
          }
          continue;
        }

        try {
          command.executeCommand(this.model);
        }
        catch (IllegalArgumentException e) {
          // if exception is thrown regarding input, ask user to reenter command
          if (!renderMessageSuccessful(e.getMessage() + "\nPlease re-enter: \n")) {
            throw new IllegalStateException("error: controller failed to write message");
          }
          continue;
        }

        // after executing command, confirm to user that command was executed successfully
        if (!this.renderMessageSuccessful("Command executed successfully!\n")) {
          throw new IllegalStateException("error: controller failed to write message");
        }
      }
    }

    // if no more input and processor has not quit, throw exception:
    // scanner fails to read input or not enough input provided
    scan.close();
    throw new IllegalStateException("Error: failed to read input");
  }

  @Override
  public Function<String[], ImageProcessorCommand> processCommand(String command) {
    Function<String[], ImageProcessorCommand> commandFunc =
            createMapOfCommands().getOrDefault(command, null);

    if (commandFunc == null) {
      throw new IllegalArgumentException("Error: command not found in controller");
    }
    else {
      return commandFunc;
    }
  }

  protected Map<String, Function<String[], ImageProcessorCommand>> createMapOfCommands() {
    Map<String, Function<String[], ImageProcessorCommand>> knownCommands = new HashMap<>();
    knownCommands.put("load", (String[] args) -> new Load(args[0], args[1]));
    knownCommands.put("save", (String[] args) -> new Save(args[0], args[1]));
    knownCommands.put("red-component", (String[] args) -> new RedComponent(args[0], args[1]));
    knownCommands.put("green-component", (String[] args) -> new GreenComponent(args[0], args[1]));
    knownCommands.put("blue-component", (String[] args) -> new BlueComponent(args[0], args[1]));
    knownCommands.put("value-component", (String[] args) -> new ValueComponent(args[0], args[1]));
    knownCommands.put("intensity-component", (String[] args) ->
            new IntensityComponent(args[0], args[1]));
    knownCommands.put("luma-component", (String[] args) -> new LumaComponent(args[0], args[1]));
    knownCommands.put("vertical-flip", (String[] args) -> new VerticalFlip(args[0], args[1]));
    knownCommands.put("horizontal-flip", (String[] args) -> new HorizontalFlip(args[0], args[1]));
    knownCommands.put("brighten", (String[] args) ->
            new Brighten(Integer.parseInt(args[0]), args[1], args[2]));
    knownCommands.put("blur", (String[] args) -> new Blur(args[0], args[1]));
    knownCommands.put("sharpen", (String[] args) -> new Sharpen(args[0], args[1]));
    knownCommands.put("greyscale", (String[] args) -> new Greyscale(args[0], args[1]));
    knownCommands.put("sepia", (String[] args) -> new Sepia(args[0], args[1]));
    return knownCommands;
  }
}

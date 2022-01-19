package controller;

import java.util.function.Function;

import controller.command.ImageProcessorCommand;

/**
 * Represents functions for handling user input in an image processor.
 */
public interface ProcessorController {
  /**
   * Runs the image processor program.
   * @throws IllegalStateException    when the controller fails to read input or write to display
   */
  void runProcessor();

  /**
   * Returns a function that will execute command with the given name and another String[] args.
   * @param command the command given
   * @throws IllegalArgumentException   if command is not supported by this image processor
   */
  Function<String[], ImageProcessorCommand> processCommand(String command);
}

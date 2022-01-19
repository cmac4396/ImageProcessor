package controller.command;

import model.ProcessorModel;

/**
 * Represents the command interface for the Image Processor.
 */
public interface ImageProcessorCommand {
  /**
   * Executes the command on the given model.
   * @param model   the model to be executed on
   */
  void executeCommand(ProcessorModel model);
}


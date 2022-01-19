import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import controller.ProcessorController;
import controller.ProcessorControllerImpl;
import controller.ProcessorGUIControllerImpl;
import model.ProcessorModel;
import model.ProcessorModelImpl;
import view.ProcessorView;
import view.ProcessorViewImpl;

/**
 * Runs the image processor with the provided Koala.ppm and saves altered images to /res.
 */
public class ImageProcessor {
  /**
   * Runs image processor program.
   * @param args    arguments from command line
   */
  public static void main(String[] args) {
    ProcessorModel model = new ProcessorModelImpl();
    ProcessorView view = new ProcessorViewImpl();
    Readable inputs = new InputStreamReader(System.in);

    if (args.length > 0) {
      if (args[0].equals("-file")) {
        // using script file: parse path of file to read from
        String scriptFilePath = args[1];
        String[] filePathSplit = scriptFilePath.split("\\.");

        String extension = filePathSplit[filePathSplit.length - 1];
        // check that file extension is .txt
        if (extension.equals("txt")) {

          // read from file if valid extension
          Scanner sc = null;
          try {
            sc = new Scanner(new FileInputStream(scriptFilePath));
          }
          catch (Exception e) {
            System.out.println("Error: script file not read correctly, "
                    + "using keyboard input instead");
          }

          if (sc != null) {
            StringBuilder builder = new StringBuilder();
            // if file is read without error, record lines in file
            while (sc.hasNextLine()) {
              String s = sc.nextLine();
              builder.append(s);
              builder.append("\n");
            }

            inputs = new StringReader(builder.toString());
          }

          // run the processor with the file script as input
          ProcessorController controller = new ProcessorControllerImpl(model, view, inputs);
          controller.runProcessor();
        }
      }
      else if (args[0].equals("-text")) {
        // run the image processor using live keyboard input and text output
        ProcessorController controller = new ProcessorControllerImpl(model, view,
                new InputStreamReader(System.in));
        controller.runProcessor();
      }
    }
    else {
      // run the image processor using the GUI
      ProcessorController controller = new ProcessorGUIControllerImpl();
      controller.runProcessor();
    }
  }
}


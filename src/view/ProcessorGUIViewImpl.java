package view;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import java.util.Map;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.ProcessorModel;

/**
 * Represents a GUI view implementation for controller.ProcessorGUIView. This class is responsible
 * for communicating actions to the controller.
 */
public class ProcessorGUIViewImpl extends JFrame implements ProcessorGUIView {
  private ProcessorModel model;

  // includes two scrolls (horizontal + vertical)
  private JPanel imagePanel;
  // drop down for file
  private JPanel fileDropDown;
  // image histogram
  private HistogramPanel histogramPanel;
  // button for undo (possible)
  // panel for histogram (will have four panels inside)
  private static final int FRAME_WIDTH = 1500;
  private static final int FRAME_HEIGHT = 900;

  /**
   * Constructs an implementation of the ProcessorGUIView which creates a GUI for our Image
   * Processor.
   * @param model of the Image Processor
   */
  public ProcessorGUIViewImpl(ProcessorModel model) {
    // call to JFrame constructor
    super();

    // check if model is null
    if (model == null) {
      throw new IllegalArgumentException("Error: given null model");
    }

    this.model = model;

    // set dimensions and size for the frame
    setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    // use FlowLayout to make window dynamic
    setLayout(new FlowLayout());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // add dropdown component for file loading and saving
    this.fileDropDown = new JPanel();
    fileDropDown.setLayout(new FlowLayout(FlowLayout.LEFT));

    // add component for the current image in the processor
    this.imagePanel = new JPanel();
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));

    // add component for the histogram corresponding to the current image displayed in the processor
    this.histogramPanel = new HistogramPanel();

    this.getContentPane().add(fileDropDown);
    this.getContentPane().add(imagePanel, "West");
    this.showView(true);
  }

  @Override
  public void showView(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void refresh() {
    this.invalidate();
    this.validate();
    this.repaint();
  }

  @Override
  public void createMenuBar(ActionListener actionListener) {
    JMenuBar menuBar = new JMenuBar();

    // creating the file menu
    JMenu fileMenu = new JMenu("File");
    // creating the open option (to open a file and load into the image processor)
    JMenuItem openItem = new JMenuItem("Open");
    this.setMenuItemAction(openItem, actionListener);
    fileMenu.add(openItem);

    // creating the save option (to save a file from the image processor)
    JMenuItem saveItem = new JMenuItem("Save");
    this.setMenuItemAction(saveItem, actionListener);
    fileMenu.add(saveItem);
    menuBar.add(fileMenu);

    // creating the filter menu
    JMenu filterMenu = new JMenu("Filter");
    // creating the component visualization option (will have another popup?)
    JMenu componentMenu =  new JMenu("Component Visualization");
    this.setMenuItemAction(componentMenu, actionListener);
    filterMenu.add(componentMenu);

    // creating each component within the component visualization dropdown
    JMenuItem redItem = new JMenuItem("Red Component");
    JMenuItem greenItem = new JMenuItem("Green Component");
    JMenuItem blueItem = new JMenuItem("Blue Component");
    JMenuItem valueItem = new JMenuItem("Value Component");
    JMenuItem lumaItem = new JMenuItem("Luma Component");
    JMenuItem intensityItem = new JMenuItem("Intensity Component");
    this.setMenuItemAction(redItem, actionListener);
    this.setMenuItemAction(greenItem, actionListener);
    this.setMenuItemAction(blueItem, actionListener);
    this.setMenuItemAction(valueItem, actionListener);
    this.setMenuItemAction(lumaItem, actionListener);
    this.setMenuItemAction(intensityItem, actionListener);
    componentMenu.add(redItem);
    componentMenu.add(greenItem);
    componentMenu.add(blueItem);
    componentMenu.add(valueItem);
    componentMenu.add(lumaItem);
    componentMenu.add(intensityItem);

    // creating the flip option
    JMenu flipMenu = new JMenu("Flip");
    this.setMenuItemAction(flipMenu, actionListener);
    filterMenu.add(flipMenu);

    // creating horizontal and vertical flip options
    JMenuItem horFlip = new JMenuItem("Horizontal Flip");
    JMenuItem verFlip = new JMenuItem("Vertical Flip");
    this.setMenuItemAction(horFlip, actionListener);
    this.setMenuItemAction(verFlip, actionListener);
    flipMenu.add(horFlip);
    flipMenu.add(verFlip);

    // creating the greyscale option
    JMenuItem brightenItem = new JMenuItem("Brighten");
    this.setMenuItemAction(brightenItem, actionListener);
    filterMenu.add(brightenItem);

    // creating the blur option
    JMenuItem blurItem = new JMenuItem("Blur");
    this.setMenuItemAction(blurItem, actionListener);
    filterMenu.add(blurItem);

    // creating the greyscale option
    JMenuItem sharpenItem = new JMenuItem("Sharpen");
    this.setMenuItemAction(sharpenItem, actionListener);
    filterMenu.add(sharpenItem);

    // creating the greyscale option
    JMenuItem greyscaleItem = new JMenuItem("Greyscale");
    this.setMenuItemAction(greyscaleItem, actionListener);
    filterMenu.add(greyscaleItem);

    // creating the sepia item
    JMenuItem sepiaItem = new JMenuItem("Sepia");
    this.setMenuItemAction(sepiaItem, actionListener);
    filterMenu.add(sepiaItem);

    menuBar.add(filterMenu);
    this.fileDropDown.add(menuBar);
    this.setJMenuBar(menuBar);
  }

  // sets the action command and adds the given action listener for a menu item
  private void setMenuItemAction(JMenuItem menuItem, ActionListener actionListener) {
    menuItem.setActionCommand(menuItem.getText());
    menuItem.addActionListener(actionListener);
  }

  @Override
  public void updateImage(String imageName) {
    // create histogram panel
    Map<Integer, Integer> redHistogram = this.model.getRedHistogram(imageName);
    Map<Integer, Integer> greenHistogram = this.model.getGreenHistogram(imageName);
    Map<Integer, Integer> blueHistogram = this.model.getBlueHistogram(imageName);
    Map<Integer, Integer> intensityHistogram = this.model.getIntensityHistogram(imageName);

    // remove previous histogram from the display
    this.remove(histogramPanel);

    // reset histogram to new values
    this.histogramPanel = new HistogramPanel(redHistogram, greenHistogram, blueHistogram,
            intensityHistogram);
    this.histogramPanel.setPreferredSize(new Dimension((int) (0.4 * FRAME_WIDTH),
            (int) (0.4 * FRAME_HEIGHT)));

    // add updated histogram to display
    this.getContentPane().add(histogramPanel, "East");

    // reset the image component to use the newly edited image
    JLabel imageLabel = new JLabel();
    imageLabel.setIcon(new ImageIcon(this.model.getCurrentImage()));
    JScrollPane imageScrollable = new JScrollPane(imageLabel);
    imageScrollable.setPreferredSize(new Dimension((int) (0.5 * FRAME_WIDTH),
            (int) (0.85 * FRAME_HEIGHT)));

    // remove the old image component and add the updated image component
    this.imagePanel.removeAll();
    this.imagePanel.add(imageScrollable);
  }

  @Override
  public String getPathToOpen() {
    // open the file selection GUI at the path of this program
    final JFileChooser fileChooser = new JFileChooser(".");

    // only allow JPG, PNG, BMP, and PPM images
    FileNameExtensionFilter extensionFilter =
            new FileNameExtensionFilter("JPG, PNG, BMP, & PPM Images",
                    "jpg", "png", "bmp", "ppm");
    fileChooser.setFileFilter(extensionFilter);

    int retvalue = fileChooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      // return file path of selected file
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    else {
      return null;
    }
  }

  @Override
  public String getPathToSave() {
    // open the file selection GUI at the path of this program
    final JFileChooser fchooser = new JFileChooser(".");

    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      // return file path of selected file
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    else {
      return null;
    }
  }

  @Override
  public int getBrightenIncrement() {
    // show popup that allows user to input brightness increment/decrement
    String input = JOptionPane.showInputDialog("Increment value\nTo darken: enter a negative number"
            + "\nTo brighten: enter a positive number");

    int incrValue = 0;
    // read key input, if not valid, display error message
    try {
      incrValue = Integer.parseInt(input);
    }
    catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null,
              "Error: Did not enter a valid number");
    }
    this.refresh();
    return incrValue;
  }

  @Override
  public void displaySaveError() {
    JOptionPane.showMessageDialog(null,
            "Error: Did not enter a valid file extension.");
  }
}

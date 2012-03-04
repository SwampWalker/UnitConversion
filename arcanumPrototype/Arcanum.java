package arcanumPrototype;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Arcanum is the gui interface for the dimensional analysis package.
 * It combines four standalone panels with different capabilities for the 
 * purpose of easily comparing quantities in different unit systems.
 * 
 * @author Aaryn Tonita
 *
 */
public class Arcanum implements ChangeListener, ActionListener {

	private DimensionPanel dimensionPanel;
	private ConvertPanel convertPanel;
	private UnitPanel unitPanel;
	private QuantityPanel quantityPanel;
	private JMenuItem saveItem;
	private JMenuItem loadItem;
	private JMenuItem quitItem;
	private JFrame theFrame;
	
	public Arcanum() {
		// Setup the frame for the gui.
		theFrame = new JFrame("Arcanum cantrip");
		
		JTabbedPane tabs = new JTabbedPane();
		convertPanel = new ConvertPanel();
		tabs.add("Convert quantities",convertPanel);
		dimensionPanel = new DimensionPanel();
		dimensionPanel.addChangeListener(this);
		// This call alphabetizes the dimension combo
		convertPanel.updateDimensionCombo(dimensionPanel);
		tabs.add("Create dimensions", dimensionPanel);
		unitPanel = new UnitPanel();
		unitPanel.addChangeListener(this);
		// This call merely alphabetizes the unit system initially.
		convertPanel.updateUnitSystemCombos(unitPanel);
		tabs.add("Create unit system",unitPanel);
		
		quantityPanel = new QuantityPanel();
		quantityPanel.addChangeListener(this);
		tabs.add("Specify new quantities",quantityPanel);
		
		// Make the menu.
		menuSetup();
		
		theFrame.add(tabs);
		theFrame.setSize(640, 480);
		theFrame.setVisible(true);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Arcanum();
	}
	
	/**
	 * Helper function to set the menu up.
	 */
	private void menuSetup() {
		// Setup the menu.
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		saveItem = new JMenuItem("Save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		saveItem.addActionListener(this);
		loadItem = new JMenuItem("Load");
		loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
		loadItem.addActionListener(this);
		quitItem = new JMenuItem("Exit");
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
		quitItem.addActionListener(this);
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);
		
		JMenuBar arcanumMenu = new JMenuBar();
		arcanumMenu.add(fileMenu);
		theFrame.setJMenuBar(arcanumMenu);
	}
	
	/**
	 * Helper function to save a file. It opens a file dialog and then saves to the selected file.
	 */
	private void saveFile() {
		JFileChooser saveFileChooser = new JFileChooser();
		ArcanumFileFilter arcanumFilter = new ArcanumFileFilter();
		saveFileChooser.setFileFilter(arcanumFilter);
		saveFileChooser.showSaveDialog(this.theFrame);
		File fileToSave = saveFileChooser.getSelectedFile();
		if (fileToSave != null) {
			if (!arcanumFilter.accept(fileToSave)) {
				if (fileToSave.toString().charAt(fileToSave.toString().length()-1) == '.') {
					fileToSave = new File(fileToSave.toString() + "arc");
				} else {
					fileToSave = new File(fileToSave.toString() + ".arc");
				}
			}
			ArcanumTome arcanumTome = new ArcanumTome();
			unitPanel.writeToTome(arcanumTome);
			dimensionPanel.writeToTome(arcanumTome);
			quantityPanel.writeToTome(arcanumTome);
			try {
				fileToSave.createNewFile();
				FileOutputStream tomeFileStream = new FileOutputStream(fileToSave);
				ObjectOutputStream tomeStream = new ObjectOutputStream(tomeFileStream);
				tomeStream.writeObject(arcanumTome);
				tomeStream.flush();
				tomeStream.close();
				tomeFileStream.flush();
				tomeFileStream.close();
			} catch (FileNotFoundException exc) {
				JOptionPane.showMessageDialog(theFrame, 
						exc.toString(), "File not found", JOptionPane.ERROR_MESSAGE);
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(theFrame, 
						ioe.getMessage(), "output error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Helper function to load a file. Opens a dialog and then opens the selected file, if any.
	 */
	private void loadFile() {
		JFileChooser loadFileChooser = new JFileChooser();
		ArcanumFileFilter arcanumFilter = new ArcanumFileFilter();
		loadFileChooser.setFileFilter(arcanumFilter);
		loadFileChooser.showOpenDialog(this.theFrame);
		File fileToLoad = loadFileChooser.getSelectedFile();
		if (fileToLoad != null) {
			try {
				FileInputStream tomeInputFileStream = new FileInputStream(fileToLoad);
				ObjectInputStream tomeInputStream = new ObjectInputStream(tomeInputFileStream);
				Object readObject = tomeInputStream.readObject();
				if (readObject instanceof ArcanumTome) {
					ArcanumTome arcanumTome = (ArcanumTome) readObject;
					dimensionPanel.readFromTome(arcanumTome);
					unitPanel.readFromTome(arcanumTome);
					quantityPanel.readFromTome(arcanumTome);
				} else {
					throw new IOException();
				}
			} catch (FileNotFoundException fileException) {
				JOptionPane.showMessageDialog(theFrame,
						"The file was not found.", "File not found", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(theFrame, 
						"File was not an Arcanum Tome or was too arcane to read.", 
						"Bad file type", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(theFrame, 
						"File was not an Arcanum Tome or was too arcane to read.", 
						"Bad file type", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(dimensionPanel)) {
			convertPanel.updateDimensionCombo(dimensionPanel);
			quantityPanel.updateDimensionCombo(dimensionPanel);
		} else if (e.getSource().equals(unitPanel)) {
			if (unitPanel.hasStoredQuantity()) {
				quantityPanel.addQuantity(unitPanel.getStoredQuantity());
			} else {
				convertPanel.updateUnitSystemCombos(unitPanel);
				quantityPanel.updateUnitSystemCombo(unitPanel);
			}
		} else if (e.getSource().equals(quantityPanel)) {
			if (quantityPanel.hasStoredDimension()) {
				dimensionPanel.addDimension(quantityPanel.getStoredDimension());
			} else if (quantityPanel.hasStoredUnitSystem()) {
				unitPanel.addUnitSystem(quantityPanel.getStoredUnitSystem());
			} else {
				unitPanel.updateQuantityCombos(quantityPanel);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(saveItem)) {
			saveFile();
		} else if (e.getSource().equals(loadItem)) {
			loadFile();
		} else if (e.getSource().equals(quitItem)) {
			System.exit(0);
		}
	}
}

package atonita.unitconversion.arcanumprototype;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import atonita.unitconversion.dimensionalanalysis.CommonUnits;
import atonita.unitconversion.dimensionalanalysis.PhysicalQuantity;
import atonita.unitconversion.dimensionalanalysis.SIConstants;
import atonita.unitconversion.dimensionalanalysis.SIQuantity;
import atonita.unitconversion.dimensionalanalysis.UnitSystem;

public class UnitPanel extends JPanel implements ActionListener {
	/**
	 * The auto-generate serial version ID.
	 */
	private static final long serialVersionUID = 7123175653155618915L;
	private JComboBox [] unitCombo = new JComboBox [5]; 			// Used to specify which quantities equal unity.
	private JTextField nameField;									// Used to name the unit system.
	private JCheckBox [] suppressCheckBoxes = new JCheckBox [5];	// Used to turn on suppression of display of the physical quantities which equal unity.
	private JButton addDerivedButton;								// Adds a derived unit to the unit system (somewhat equivalent to extra dimensions)
	private JComboBox addDerivedCombo;								// 	Specifies which derived unit to add.
	private JButton deleteDerivedButton;							// Remove a derived unit from the unit system.
	private JComboBox derivedUnitCombo;								// Displays the derived units and also is used to select derived units for deletion.
	private ArrayList<Object> derivedUnits;						// An array in which the derived units are temporarily stored.
	private JButton loadButton;										// Button to load a unit system into the panels elements.
	private JComboBox unitSystemCombo;								// Specifies which unit system to load into the panel.
	private JButton saveButton;										// Save this unit system to the programs memory.
	private JButton deleteButton;									// Delete a stored unit system.
	private ArrayList<Object> storedUnitSystems;				// Array to store the saved unit systems.
	private ChangeListener theChangeListener;						// The listener we notify when the stored units change.
	private SIQuantity storedQuantity;
	
	/**
	 * The only constructor for this object. Instantiates its structures and sets the layout of the gui elements. 
	 */
	public UnitPanel() {
		// Setup some of the internal variables.
		derivedUnits = new ArrayList<Object>();
		storedUnitSystems = new ArrayList<Object>();
		
		// Create the label and text field for the name of the unit system.
		JLabel nameLabel = new JLabel(" Name: ");
		nameField = new JTextField("Unnamed");
		// Create the labels for the units.
		JLabel unit0Label = new JLabel(" Unit 1: ");
		JLabel unit1Label = new JLabel(" Unit 2: ");
		JLabel unit2Label = new JLabel(" Unit 3: ");
		JLabel unit3Label = new JLabel(" Unit 4: ");
		JLabel unit4Label = new JLabel(" Unit 5: ");
		// Create the combo boxes for the units.
		TreeSet <SIQuantity>mySet = new TreeSet<SIQuantity>(new ToStringComparator());
		for (int i = 0; i < SIConstants.ALLSICONSTANTS.length; i++) {
			mySet.add(SIConstants.ALLSICONSTANTS[i]);
		}
		Iterator<SIQuantity> myIterator = mySet.iterator();
		SIQuantity [] myArray = new SIQuantity[mySet.size()];
		int index = 0;
		while (myIterator.hasNext()) {
			myArray[index++] = (SIQuantity) myIterator.next();
		}
		for (int i = 0; i < 5; i++) {
			unitCombo[i] = new JComboBox();
			basicUnitCombo(unitCombo[i]);
		}
		unitCombo[0].setSelectedItem(SIConstants.METRE);
		unitCombo[1].setSelectedItem(SIConstants.KILOGRAM);
		unitCombo[2].setSelectedItem(SIConstants.SECOND);
		unitCombo[3].setSelectedItem(SIConstants.COULOMB);
		unitCombo[4].setSelectedItem(SIConstants.KELVIN);
		// Create check boxes for suppression of units.
		JLabel suppressLabel = new JLabel(" Suppress display of:");
		for (int i = 1; i <= 5; i++) {
			suppressCheckBoxes[i-1] = new JCheckBox("Unit " + i);
		}
		// Set some Mnemonics for the check boxes
		suppressCheckBoxes[0].setMnemonic(KeyEvent.VK_1);
		suppressCheckBoxes[1].setMnemonic(KeyEvent.VK_2);
		suppressCheckBoxes[2].setMnemonic(KeyEvent.VK_3);
		suppressCheckBoxes[3].setMnemonic(KeyEvent.VK_4);
		suppressCheckBoxes[4].setMnemonic(KeyEvent.VK_5);
		// Create the combo box for the selection of derived units.
		addDerivedButton = new JButton("Add derived unit:");
		addDerivedButton.addActionListener(this);
		addDerivedCombo = new JComboBox();
		basicUnitCombo(addDerivedCombo);
		// Create the button and combo for the derived units.
		deleteDerivedButton = new JButton("Delete derived unit:");
		deleteDerivedButton.addActionListener(this);
		derivedUnitCombo = new JComboBox();
		// Create the button and combo for loading up other unit systems.
		loadButton = new JButton("Load unit system");
		loadButton.addActionListener(this);
		unitSystemCombo = new JComboBox();
		fillUnitSystemCombo(unitSystemCombo);
		unitSystemCombo.setSelectedItem(CommonUnits.SI);
		// Create the save button and the delete button.
		saveButton = new JButton("Save this unit system");
		saveButton.addActionListener(this);
		deleteButton = new JButton("Delete the selected unit system");
		deleteButton.addActionListener(this);

		// Create the layout for the frame.
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);
		// Setup the layout, to whoever reads this... I apologize for doing this by hand.
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(nameLabel)
										.addComponent(unit0Label)
										.addComponent(unit1Label)
										.addComponent(unit2Label)
										.addComponent(unit3Label)
										.addComponent(unit4Label))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(nameField)
										.addComponent(unitCombo[0])
										.addComponent(unitCombo[1])
										.addComponent(unitCombo[2])
										.addComponent(unitCombo[3])
										.addComponent(unitCombo[4])))
						.addGroup(layout.createSequentialGroup()
								.addComponent(suppressLabel)
								.addComponent(suppressCheckBoxes[0])
								.addComponent(suppressCheckBoxes[1])
								.addComponent(suppressCheckBoxes[2])
								.addComponent(suppressCheckBoxes[3])
								.addComponent(suppressCheckBoxes[4]))
						.addGroup(layout.createSequentialGroup()
								.addComponent(addDerivedButton)
								.addComponent(addDerivedCombo))
						.addGroup(layout.createSequentialGroup()
								.addComponent(deleteDerivedButton)
								.addComponent(derivedUnitCombo))
						.addGroup(layout.createSequentialGroup()
								.addComponent(loadButton)
								.addComponent(unitSystemCombo))
						.addGroup(layout.createSequentialGroup()
								.addComponent(saveButton)
								.addComponent(deleteButton))
				)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(nameLabel)
						.addComponent(nameField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(unit0Label)
						.addComponent(unitCombo[0]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(unit1Label)
						.addComponent(unitCombo[1]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(unit2Label)
						.addComponent(unitCombo[2]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(unit3Label)
						.addComponent(unitCombo[3]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(unit4Label)
						.addComponent(unitCombo[4]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(suppressLabel)
						.addComponent(suppressCheckBoxes[0])
						.addComponent(suppressCheckBoxes[1])
						.addComponent(suppressCheckBoxes[2])
						.addComponent(suppressCheckBoxes[3])
						.addComponent(suppressCheckBoxes[4]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(addDerivedButton)
						.addComponent(addDerivedCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(deleteDerivedButton)
						.addComponent(derivedUnitCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(loadButton)
						.addComponent(unitSystemCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(saveButton)
						.addComponent(deleteButton))
				);
	}
	
	/**
	 * A private function used to fill the quantity combos until new quantities are added.
	 * @param unitCombo, a <code>JComboBox</code> which will be filled with SI constants.
	 */
	private void basicUnitCombo(JComboBox unitCombo) {
		// Create the combo boxes for the units.
		TreeSet <SIQuantity>mySet = new TreeSet<SIQuantity>(new ToStringComparator());
		for (int i = 0; i < SIConstants.ALLSICONSTANTS.length; i++) {
			mySet.add(SIConstants.ALLSICONSTANTS[i]);
		}
		Iterator<SIQuantity> myIterator = mySet.iterator();
		while (myIterator.hasNext()) {
			unitCombo.addItem(myIterator.next());
		}
		unitCombo.updateUI();
	}
	
	/**
	 * Private function used to add a derived unit to the unit system being developed.
	 * @param x, an <code>SIQuantity</code> to use as a derived unit in the unit system.
	 */
	private void addDerivedUnit(SIQuantity x) {
		if (!derivedUnits.contains(x)) {
			derivedUnits.add(x);
			derivedUnitCombo.addItem(x);
			derivedUnitCombo.updateUI();
		}
	}
	
	/**
	 * Private helper function to remove a given unit from the list of derived units in the unit system.
	 * @param x, an <code>SIQuantity</code>.
	 */
	private void removeDerivedUnit(SIQuantity x) {
		derivedUnits.remove(x);
		derivedUnitCombo.removeItem(x);
		derivedUnitCombo.updateUI();
	}
	
	/**
	 * Public function which is used to fill combo boxes with both default unit systems and
	 * the unit systems stored in this panel. It removes all objects first and then fills the
	 * box, remembering which unit was selected, so long as it is re-added.
	 * @param usCombo, a <code>JComboBox</code> object.
	 */
	public void fillUnitSystemCombo(JComboBox usCombo) {
		// Preserve selection in combo box.
		Object selected = usCombo.getSelectedItem();
		usCombo.removeAllItems();
		// Build a tree to sort the unit systems.
		TreeSet<UnitSystem> unitTree = new TreeSet<UnitSystem>(new ToStringComparator());
		// Add the default unit systems.
		for (int i = 0; i < CommonUnits.COMBOUNITSYSTEMS.length; i++) {
			unitTree.add(CommonUnits.COMBOUNITSYSTEMS[i]);
		}
		// Add the extra unit systems.
		for (int i = 0; i < storedUnitSystems.size(); i++) {
			unitTree.add((UnitSystem) storedUnitSystems.get(i));
		}
		Iterator<UnitSystem> iterator = unitTree.iterator();
		while(iterator.hasNext()) {
			usCombo.addItem(iterator.next());
		}
		usCombo.setSelectedItem(selected);
	}
	
	/**
	 * Public function used to notify this panel that there is a quantity panel with stored
	 * quantities which should be added to the quantity combo's of this panel.
	 * @param quantityPanel, a <code>QuantityPanel</code> object.
	 */
	public void updateQuantityCombos(QuantityPanel quantityPanel) {
		for (int i = 0; i < 5; i ++) {
			quantityPanel.fillQuantityCombo(unitCombo[i]);
		}
		quantityPanel.fillQuantityCombo(addDerivedCombo);
	}
	
	/**
	 * Private function used to read all of the various text field and combo boxes and to generate a 
	 * <code>UnitSystem</code> object from the chosen options.
	 * @return the created <code>UnitSystem</code>.
	 * @throws IllegalArgumentException when the unit system is inconsistent.
	 */
	private UnitSystem createUnitSystem() throws IllegalArgumentException {
		// Setup the units and their suppressors.
		SIQuantity [] units = new SIQuantity [5];
		boolean [] suppressors = new boolean [5];
		for (int i = 0; i < 5; i++) {
			suppressors[i] = suppressCheckBoxes[i].isSelected();
			Object selectedUnit = unitCombo[i].getSelectedItem();
			if (selectedUnit instanceof SIQuantity) {
				units[i] = (SIQuantity) selectedUnit;
			} else if (selectedUnit instanceof PhysicalQuantity) {
				units[i] = ((PhysicalQuantity) selectedUnit).toSI();
			} else {
				throw new IllegalArgumentException("Selected unit was of unknown class.");
			}
		}
		// Setup the derived quantities.
		SIQuantity [] derived = new SIQuantity [derivedUnits.size()];
		for (int i = 0; i < derived.length; i++) {
			Object someUnit = derivedUnits.get(i);
			if (someUnit instanceof SIQuantity) {
				derived[i] = (SIQuantity)derivedUnits.get(i);
			} else if (someUnit instanceof PhysicalQuantity){
				derived[i] = ((PhysicalQuantity) someUnit).toSI();
			} else {
				throw new IllegalArgumentException("Selected derived unit was of unknown type.");
			}
		}
		// Get the name.
		String name = nameField.getText();
		return new UnitSystem(units,suppressors,derived,name);
	}

	/**
	 * Public function used to register a <code>ChangeListener</code> with this object. Currently, only one
	 * change listener can be registered at a time, subsequent calls replace the reference.
	 * @param cl, an object which implements <code>ChangeListener</code>.
	 */
	public void addChangeListener(ChangeListener cl) {
		theChangeListener = cl;
	}
	
	/**
	 * Private function which tests if the supplied unit system is equivalent to a unit system already stored in
	 * this unit system, or equivalent to a default unit system.
	 * @param us, a <code>UnitSystem</code> to test if it is already known to this panel.
	 * @return  Will return <code>true</code> if the unit system exists, <code>false</code> otherwise.
	 */
	private boolean unitSystemExists(UnitSystem us) {
		// Loop over default unit systems.
		for (int i = 0; i < CommonUnits.COMBOUNITSYSTEMS.length; i++) {
			if (CommonUnits.COMBOUNITSYSTEMS[i].equals(us)) {
				return true;
			}
		}
		// Loop over the extra unit systems.
		for (int i = 0; i < storedUnitSystems.size(); i++) {
			if (storedUnitSystems.get(i).equals(us)) {
				return true;
			}
		}
		// If we got here we didn't find it.
		return false;
	}
	
	/**
	 * Private function used to test that there are no default or stored unit systems with the same name
	 * as the unit system supplied.
	 * @param name, a <code>String</code> to test versus the names of the default and stored unit systems.
	 * @return <code>true</code> if there is already a unit system with the same name, <code>false</code> otherwise.
	 */
	private boolean nameExists(String name) {
		// Loop over default unit systems.
		for (int i = 0; i < CommonUnits.COMBOUNITSYSTEMS.length; i++) {
			if (CommonUnits.COMBOUNITSYSTEMS[i].toString().equals(name)) {
				return true;
			}
		}
		// Loop over extra unit systems.
		for (int i = 0; i < storedUnitSystems.size(); i++) {
			if (storedUnitSystems.get(i).toString().equals(name)) {
				return true;
			}
		}
		// No match was found.
		return false;
	}
	
	/**
	 * Public function which is used to add a unit system to the list of stored unit systems in this panel.
	 * @param us a <code>UnitSystem</code> object.
	 */
	public void addUnitSystem(UnitSystem us) {
		if (nameExists(us.getName())) {
			JOptionPane.showMessageDialog(this, 
					"A unit system with that name already exists.",
					"Name taken error", JOptionPane.ERROR_MESSAGE);
		} else {
			if (!unitSystemExists(us)) {
				storedUnitSystems.add(us);
				fillUnitSystemCombo(unitSystemCombo);
				unitSystemCombo.setSelectedItem(us);
				if (theChangeListener != null) {
					theChangeListener.stateChanged(new ChangeEvent(this));
				}
			} else {
				JOptionPane.showMessageDialog(this, 
						"A unit system already exists with all those properties:\n"+getUnitSystemName(us),
						"Duplication forbidden",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Public function used to request this panel to write its stored contents to the supplied <code>ArcanumObject</code>.
	 * @param arcanumTome, an <code>ArcanumTome</code>.
	 */
	public void writeToTome(ArcanumTome arcanumTome) {
		arcanumTome.writeToTome(storedUnitSystems);
	}
	
	/**
	 * Public function used to request this panel to read all saved unit systems from the supplied <code>ArcanumObject</code>.
	 * @param arcanumTome an <code>ArcanumObject</code>.
	 */
	public void readFromTome(ArcanumTome arcanumTome) {
		Iterator<Object> iterator = arcanumTome.unitSystemIterator();
		while (iterator.hasNext()) {
			Object unitSystemObject = iterator.next();
			if (unitSystemObject instanceof UnitSystem) {
				storedUnitSystems.add(unitSystemObject);
			} else {
				// TODO: This is messy error handling.
				System.err.println(storedUnitSystems.toString());
			}
		}
		fillUnitSystemCombo(unitSystemCombo);
		if (theChangeListener != null) {
			theChangeListener.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Sets the unit combo to the selected quantity.
	 * 
	 * @param x, a <code>SIQuantity</code> to set the unitCombo to.
	 * @param i, an integer specifying which unit combo to set the selected item to
	 */
	private void setUnitComboToSelected(SIQuantity x, int i) {
		int selectedIndex = -1;
		for (int j = 0; j < unitCombo[i].getItemCount(); j++) {
			Object current = unitCombo[i].getItemAt(j);
			if (current instanceof SIQuantity) {
				SIQuantity y = (SIQuantity) current;
				if (y.equals(x)) {
					unitCombo[i].setSelectedIndex(j);
					selectedIndex = j;
				}
			}
		}
		if (selectedIndex == -1) {
			for (int j = 0; j < 5; j++) {
				unitCombo[j].addItem(x);
			}
			unitCombo[i].setSelectedItem(x);
		}
		storedQuantity = x;
		if (theChangeListener != null) {
			theChangeListener.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Public function which is used to test if this panel encountered an unknown quantity when it loaded
	 * a unit system.
	 * @return <code>true</code> if there is a stored quantity in this panel.
	 */
	public boolean hasStoredQuantity() {
		if (storedQuantity != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Public function which should be called after <code>hasStoredQuantity()</code> returns true to 
	 * acquire the stored quantity and send it to a <code>QuantityPanel</code>. The stored quantity
	 * is removed from this panel after it is retrieved by this funciton.
	 * @return the stored <code>SIQuantity</code>.
	 */
	public SIQuantity getStoredQuantity() {
		if (storedQuantity != null) {
			SIQuantity x = storedQuantity;
			storedQuantity = null;
			return x;
		}
		return null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addDerivedButton)) {
			addDerivedUnit((SIQuantity) addDerivedCombo.getSelectedItem());
		} else if (e.getSource().equals(deleteDerivedButton)) {
			removeDerivedUnit((SIQuantity) derivedUnitCombo.getSelectedItem());
		} else if (e.getSource().equals(loadButton)) {
			UnitSystem us = (UnitSystem) unitSystemCombo.getSelectedItem();
			// Fill up the derived units.
			derivedUnits.clear();
			for(int i = 0; i < us.getDerivedUnits().length; i++) {
				addDerivedUnit(us.getDerivedUnits()[i]);
			}
			// Get the actual unit quantities and their suppressors.
			for (int i = 0; i < 5; i ++) {
				SIQuantity unit = us.getUnit(i);
				setUnitComboToSelected(unit,i);
				suppressCheckBoxes[i].setSelected(us.getSuppressors()[i]);
			}
			// Get the name.
			nameField.setText(us.getName());
		} else if (e.getSource().equals(saveButton)) {
			try {
				UnitSystem us = createUnitSystem();
				this.addUnitSystem(us);
			} catch (IllegalArgumentException exc) {
				JOptionPane.showMessageDialog(this, 
						"The units chosen were inconsistent with one another.", "Inconsistent units error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource().equals(deleteButton)) {
			if (storedUnitSystems.contains(unitSystemCombo.getSelectedItem())) {
				storedUnitSystems.remove(unitSystemCombo.getSelectedItem());
				fillUnitSystemCombo(unitSystemCombo);
				unitSystemCombo.setSelectedItem(CommonUnits.SI);
				if (theChangeListener != null) {
					theChangeListener.stateChanged(new ChangeEvent(this));
				}
			} else {
				JOptionPane.showMessageDialog(this, 
						"Will not delete a default unit system.",
						"Faulty selection error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Searches the stored unit systems for the argument and returns the name.
	 * @param us the unit system to find
	 * @return the name of the unit system as stored
	 */
	private String getUnitSystemName(UnitSystem us) {
		String name = null;
		// Loop over default unit systems.
		for (int i = 0; i < CommonUnits.COMBOUNITSYSTEMS.length; i++) {
			if (CommonUnits.COMBOUNITSYSTEMS[i].equals(us)) {
				name = CommonUnits.COMBOUNITSYSTEMS[i].getName();
			}
		}
		// Loop over the extra unit systems.
		for (int i = 0; i < storedUnitSystems.size(); i++) {
			if (storedUnitSystems.get(i).equals(us)) {
				name = ((UnitSystem)storedUnitSystems.get(i)).getName();
			}
		}
		return name;
	}
}

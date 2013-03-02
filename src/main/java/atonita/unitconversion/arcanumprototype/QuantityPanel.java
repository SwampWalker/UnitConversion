package atonita.unitconversion.arcanumprototype;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import atonita.unitconversion.dimensionalanalysis.CommonUnits;
import atonita.unitconversion.dimensionalanalysis.Dimension;
import atonita.unitconversion.dimensionalanalysis.PhysicalQuantity;
import atonita.unitconversion.dimensionalanalysis.SIConstants;
import atonita.unitconversion.dimensionalanalysis.SIQuantity;
import atonita.unitconversion.dimensionalanalysis.UnitSystem;

public class QuantityPanel extends JPanel implements ActionListener, DocumentListener {
	/**
	 * The auto-generate serial version ID.
	 */
	private static final long serialVersionUID = -4764096461808171317L;
	private ChangeListener theChangeListener;
	private JTextField symbolField;
	private JTextField commentField;
	private JFormattedTextField valueField;
	private JComboBox dimensionCombo;
	private JComboBox unitSystemCombo;
	private JButton loadButton;
	private JComboBox quantityCombo;
	private JTextField displayField;
	private JButton saveButton;
	private JButton deleteButton;
	// For storage of quantities.
	private ArrayList<Object> quantityList;
	private Dimension storedDimension;
	private UnitSystem storedUnitSystem;
	
	/**
	 * The one and only constructor for this object. Instantiates its data and lays out its gui elements.
	 */
	public QuantityPanel() {
		// Set up the labels and field for specifying the quantity and its description.
		JLabel symbolLabel = new JLabel(" Symbol:");
		symbolField = new JTextField();
		JLabel commentLabel = new JLabel(" Description:");
		commentField = new JTextField();
		// The formatted text field for specifying the value of the quantity.
		JLabel valueLabel = new JLabel(" Value:");
		DecimalFormat dFormat = new DecimalFormat("#.E0");
		dFormat.setMaximumFractionDigits(309);
		valueField = new JFormattedTextField(dFormat);
		valueField.setValue(1.0e0);
		// The labels and combos for the dimension and unit system.
		JLabel dimensionLabel = new JLabel(" Dimension:");
		dimensionCombo = new JComboBox(Dimension.COMMONDIMENSIONS);
		dimensionCombo.setSelectedItem(Dimension.DIMENSIONLESS);
		JLabel unitSystemLabel = new JLabel(" Unit System:");
		unitSystemCombo = new JComboBox(CommonUnits.COMBOUNITSYSTEMS);
		unitSystemCombo.setSelectedItem(CommonUnits.SI);
		// The button and combo box for loading a pre-defined quantity.
		loadButton = new JButton("Load");
		quantityCombo = new JComboBox();
		// The field and label for displaying the current quantity.
		JLabel displayLabel = new JLabel(" Display:");
		displayField = new JTextField();
		displayField.setEditable(false);
		// The save button and list for saving things.
		saveButton = new JButton("Save");
		quantityList = new ArrayList<Object>();
		// ... and the delete button.
		deleteButton = new JButton("Delete selected");
		// Fill the quantity combo now that the quantityList has been initialised.
		this.fillQuantityCombo(quantityCombo);
		
		
		// Setup the layout of the container.
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);
		
		// The horizontal group, two columns: (labels+buttons) + (fields+combos)
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(symbolLabel)
						.addComponent(commentLabel)
						.addComponent(valueLabel)
						.addComponent(dimensionLabel)
						.addComponent(unitSystemLabel)
						.addComponent(displayLabel)
						.addComponent(loadButton)
						.addComponent(saveButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(symbolField)
						.addComponent(commentField)
						.addComponent(valueField)
						.addComponent(dimensionCombo)
						.addComponent(unitSystemCombo)
						.addComponent(displayField)
						.addComponent(quantityCombo)
						.addComponent(deleteButton))
				);
		// The vertical group, pairs of (label/button)s + (field/combo)s
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(symbolLabel)
						.addComponent(symbolField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(commentLabel)
						.addComponent(commentField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(valueLabel)
						.addComponent(valueField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dimensionLabel)
						.addComponent(dimensionCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(unitSystemLabel)
						.addComponent(unitSystemCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(displayLabel)
						.addComponent(displayField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(loadButton)
						.addComponent(quantityCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(saveButton)
						.addComponent(deleteButton))
				);

		// Register the listener.
		symbolField.getDocument().addDocumentListener(this);
		valueField.getDocument().addDocumentListener(this);
		dimensionCombo.addActionListener(this);
		unitSystemCombo.addActionListener(this);
		loadButton.addActionListener(this);
		saveButton.addActionListener(this);
		deleteButton.addActionListener(this);
		
		// Display nothing essentially.
		updateDisplay();
	}
	
	/**
	 * Registers the supplied change listener. Currently only one change listener can be registered and 
	 * subsequent calls overwrite the previous.
	 * @param l an object which implements <code>ChangeListener</code>.
	 */
	public void addChangeListener(ChangeListener l) {
		theChangeListener = l;
	}

	/**
	 * Private function used to acquire the information in the various combos and text fields and display the quantity created so far.
	 */
	private void updateDisplay() {
		String s;
		Document symbolDocument = symbolField.getDocument();
		try {
			s = symbolDocument.getText(symbolDocument.getStartPosition().getOffset(), symbolDocument.getLength());
		} catch (BadLocationException e) {
			// This exception should never be thrown, in theory; in any case, lets not crash. 
			s = "How did that happen?";
		}
		Dimension d = (Dimension) dimensionCombo.getSelectedItem();
		UnitSystem us = (UnitSystem) unitSystemCombo.getSelectedItem();
		String unitString = us.getUnitString(d);
		String valueString = valueField.getValue().toString();
		if (s.isEmpty()) {
			displayField.setText(valueString + " " + unitString);
		} else {
			displayField.setText(s + " = " + valueString + " " + unitString);
		}
	}
	
	/**
	 * Public function used to fill a given combo box with default quantities and the quanities stored in this panel.
	 * Removes all elements before filling the box, remembers the selection so long as the quantity is added by this panel.
	 * @param quantityCombo, a <code>JComboBox</code> object.
	 */
	public void fillQuantityCombo(JComboBox quantityCombo) {
		Object selected = quantityCombo.getSelectedItem();
		quantityCombo.removeAllItems();
		// Probably I should extract an interface out of SIQuantity and PhysicalQuantity...
		TreeSet<Object> myTree = new TreeSet<Object>(new ToStringComparator());
		// Populate the tree with the SIConstants.
		for (int i = 0; i < SIConstants.ALLSICONSTANTS.length; i++) {
			myTree.add(SIConstants.ALLSICONSTANTS[i]);
		}
		// Populate the tree with the stored quantities.
		for (int i = 0; i < quantityList.size(); i++) {
			myTree.add(quantityList.get(i));
		}
		Iterator<Object> iterator = myTree.iterator();
		while (iterator.hasNext()) {
			quantityCombo.addItem(iterator.next());
		}
		quantityCombo.setSelectedItem(selected);
		if (quantityCombo.getSelectedIndex() < 0) {
			quantityCombo.setSelectedIndex(0);
		}
	}
	
	/**
	 * Private function used to search dimensionCombo of to find a dimension equal to the supplied dimension.
	 * @param d a <code>Dimension</code> object.
	 * @return the index of the dimension in the combo box.
	 */
	private int getDimensionComboIndex(Dimension d) {
		for (int i = 0; i < dimensionCombo.getItemCount(); i++) {
			Dimension selected = (Dimension) dimensionCombo.getItemAt(i);
			if (selected.hasSameDimensionAs(d)) {
				return i;
			}
		}
		// Couldn't find the dimension.
		return -1;
	}
	
	/**
	 * Private function used to get the index of the supplied unit system in unitSystemCombo.
	 * @param us, a <code>UnitSystem</code>.
	 * @return the index of the supplied unit system.
	 */
	private int getUnitSystemComboIndex(UnitSystem us) {
		for (int i = 0; i < unitSystemCombo.getItemCount(); i++) {
			UnitSystem selected = (UnitSystem) unitSystemCombo.getItemAt(i);
			if (selected.equals(us)) {
				return i;
			}
		}
		// Couldn't find the unit system.
		return -1;
	}
	
	/**
	 * Tells dimensionCombo to select the supplied dimension.
	 * @param d, a <code>Dimension</code> object.
	 */
	private void setDimensionComboIndex(Dimension d) {
		int index = getDimensionComboIndex(d);
		if (index >= 0) {
			dimensionCombo.setSelectedIndex(index);
		} else {
			String name = JOptionPane.showInputDialog(this, 
					"The given quantity had an unknown Dimension.\n Please supply a name for the dimension: " + d.dimensionString() + ".",
					"Unknown dimension", JOptionPane.QUESTION_MESSAGE);
			storedDimension = new Dimension(d,name);
			dimensionCombo.addItem(storedDimension);
			dimensionCombo.setSelectedItem(storedDimension);
			if (theChangeListener != null) {
				theChangeListener.stateChanged(new ChangeEvent(this));
			}
		}
	}
	
	/**
	 * Tells unitSystemCombo to select the supplied unit system.
	 * @param us, a <code>UnitSystem</code>.
	 */
	private void setUnitSystemComboIndex(UnitSystem us) {
		int index = getUnitSystemComboIndex(us);
		if (index >= 0) {
			unitSystemCombo.setSelectedIndex(index);
		} else {
			String name = JOptionPane.showInputDialog(this, 
					"The given quantity had an unknown unit system.\n Please supply a name for the unit system.",
					us.getName());
			storedUnitSystem = new UnitSystem(us,name);
			unitSystemCombo.addItem(storedUnitSystem);
			unitSystemCombo.setSelectedItem(storedUnitSystem);
			if (theChangeListener != null) {
				theChangeListener.stateChanged(new ChangeEvent(this));
			}
		}
	}
	
	/**
	 * Public function used to request this panel write its stored quantities into the supplied tome.
	 * @param arcanumTome, a <code>ArcanumTome</code>.
	 */
	public void writeToTome(ArcanumTome arcanumTome) {
		arcanumTome.writeToTome(quantityList);
	}
	
	/**
	 * Public function used to request that this panel read saved quantities from the supplied tome.
	 * @param arcanumTome, a <code>ArcanumTome</code>.
	 */
	public void readFromTome(ArcanumTome arcanumTome) {
		Iterator<Object> iterator = arcanumTome.quantityIterator();
		while(iterator.hasNext()) {
			Object readQuantityObject = iterator.next();
			if (	(readQuantityObject instanceof SIQuantity) || 
					(readQuantityObject instanceof PhysicalQuantity)) {
				quantityList.add(readQuantityObject);
			} else {
				// TODO: messy error handling.
				System.err.println(readQuantityObject.toString());
			}
		}
		fillQuantityCombo(quantityCombo);
		if (theChangeListener != null) {
			theChangeListener.stateChanged(new ChangeEvent(this));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(loadButton)) {
			Object o = quantityCombo.getSelectedItem();
			if (o instanceof SIQuantity) {
				SIQuantity x = (SIQuantity) o;
				valueField.setValue(x.getValue());
				setUnitSystemComboIndex(CommonUnits.SI);
				setDimensionComboIndex(x.getDimension());
				symbolField.setText(x.getName());
				commentField.setText(x.getComment());
			} else {
				PhysicalQuantity x = (PhysicalQuantity) o;
				valueField.setValue(x.getValue());
				setUnitSystemComboIndex(x.getUnits());
				setDimensionComboIndex(x.getDimension());
				symbolField.setText(x.getSymbol());
				commentField.setText(x.getComment());
			}
			updateDisplay();
		} else if (e.getSource().equals(saveButton)) {
			updateDisplay();
			UnitSystem us = (UnitSystem) unitSystemCombo.getSelectedItem();
			double value = Double.valueOf(valueField.getValue().toString());
			Dimension d = (Dimension) dimensionCombo.getSelectedItem();
			String symbol = symbolField.getText();
			String comment = commentField.getText();
			if (us.equals(CommonUnits.SI)) {
				// Make an SIQuantity.
				SIQuantity x = new SIQuantity(value,d,symbol,comment);
				quantityList.add(x);
			} else {
				// Make a PhysicalQuantity
				PhysicalQuantity x = new PhysicalQuantity(value,d,us,symbol,comment);
				quantityList.add(x);
			}
			fillQuantityCombo(quantityCombo);
			if (theChangeListener != null) {
				theChangeListener.stateChanged(new ChangeEvent(this));
			}
		} else if (e.getSource().equals(deleteButton)) {
			Object selected = quantityCombo.getSelectedItem();
			if (quantityList.contains(selected)) {
				quantityList.remove(selected);
				fillQuantityCombo(quantityCombo);
				if (theChangeListener != null) {
					theChangeListener.stateChanged(new ChangeEvent(this));
				}
			} else {
				JOptionPane.showMessageDialog(this, 
						"Quantity is default, I will not delete it.", 
						"Action denied", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public boolean hasStoredDimension() {
		if (storedDimension == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public Dimension getStoredDimension() {
		Dimension d = storedDimension;
		storedDimension = null;
		return d;
	}
	
	public boolean hasStoredUnitSystem() {
		if (storedUnitSystem == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public UnitSystem getStoredUnitSystem() {
		UnitSystem us = storedUnitSystem;
		storedUnitSystem = null;
		return us;
	}
	
	public void updateDimensionCombo(DimensionPanel dimPanel) {
		dimPanel.fillDimensionCombo(dimensionCombo);
	}
	
	public void updateUnitSystemCombo(UnitPanel unitPanel) {
		unitPanel.fillUnitSystemCombo(unitSystemCombo);
	}
	
	public void addQuantity(SIQuantity x) {
		quantityList.add(x);
		fillQuantityCombo(quantityCombo);
		if (theChangeListener != null) {
			theChangeListener.stateChanged(new ChangeEvent(this));
		}
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		updateDisplay();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateDisplay();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateDisplay();
	}

}

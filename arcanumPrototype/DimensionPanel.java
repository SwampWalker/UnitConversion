package arcanumPrototype;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
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

import rationalNumbers.RationalNumber;

import dimensionalAnalysis.Dimension;

/**
 * DimensionPanel is a subclass of JPanel. It is a member of the dimensionalAnalysis package
 * and is used to create and store physical Dimensions.
 * 
 * @author Aaryn Tonita
 *
 */
public class DimensionPanel extends JPanel implements ActionListener, PropertyChangeListener, DocumentListener {
	/**
	 * The auto-generated serial version id.
	 */
	private static final long serialVersionUID = 4800653772329623367L;
	// Fields that we will read to define new dimensions.
	private JTextField nameField;						// A text field for inputting the name of a new dimension
	private JTextField dimensionField;					// A text field for displaying the dimension string: M^m L^l T^t Q^q Theta^theta
	private JFormattedTextField [] massFields;			// Number fields for the rational power of M (m above)
	private JFormattedTextField [] lengthFields;		// 					"					   L (l above)
	private JFormattedTextField [] timeFields;			//					"					   T (t above)
	private JFormattedTextField [] chargeFields;		//					"					   Q (q above)
	private JFormattedTextField [] temperatureFields;	//					"					   
	// Hold onto the references to the buttons so that we can easily check their ActionCommand.
	private JButton multiplyButton;						// A button to multiply the current dimension in the fields above by the selected dimension.
	private JButton divideButton;						// A button to divide 								"
	private JButton inputButton;						// A button to input the selected dimension in the dCombo into the fields above.
	private JButton invertButton;						// A button to invert the current dimension in the fields above.
	private JButton powerButton;						// A button to raise the current dimension to the power in the power fields below.
	private JButton yPowerButton;						// A button to multiply the selected dimension to the power below by the current dimension.
	private JButton saveButton;							// A button to save the current dimension for storage and retrieval.
	private JButton deleteButton;						// A button to delete saved dimensions.
	private JComboBox dCombo;							// A combo box to select default and stored dimensions.
	private JFormattedTextField [] powerFields;			// Fields to create a rational number.
	private Dimension currentDimension = Dimension.DENSITY; // This stores the current dimension created in the formatted fields above. Initialised to avoid a null pointer exception.
	private ChangeListener theChangeListener;			// A change listener to notify about new dimensions.
	private ArrayList<Object> storedDimensions = new ArrayList<Object>();	// An array list used to store dimensions.

	
	/**
	 * The only constructor for the dimension panel, sets up all the parameters.
	 */
	public DimensionPanel() {
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);
		
		// There is a field for specifying the name of the Dimension.
		JLabel nameLabel = new JLabel(" Name of Dimension (x): ");
		nameField = new JTextField("Unnamed");
		nameField.getDocument().addDocumentListener(this);
		// There are fields for specifying the power of a given dimension, all 5 of them:
		JLabel massLabel = new JLabel(" Mass Power: ");
		massFields = numeratorDenominatorFields();
		JLabel lengthLabel = new JLabel(" Length Power: ");
		lengthFields = numeratorDenominatorFields();
		JLabel timeLabel = new JLabel(" Time Power: ");
		timeFields = numeratorDenominatorFields();
		JLabel chargeLabel = new JLabel(" Charge Power: ");
		chargeFields = numeratorDenominatorFields();
		JLabel temperatureLabel = new JLabel(" Temperature Power: ");
		temperatureFields = numeratorDenominatorFields();
		// Create a field for displaying the result.
		JLabel dimensionLabel = new JLabel(" Dimension string: ");
		dimensionField = new JTextField();
		dimensionField.setEditable(false);
		// There is a combo box for selecting a dimension for easy manipulation.
		JLabel dComboLabel = new JLabel(" Selected dimension (y): ");
		dCombo = new JComboBox();
		this.fillDimensionCombo(dCombo);
		dCombo.setSelectedItem(Dimension.DIMENSIONLESS);
		// There is a field of buttons for manipulating chosen dimension.
		JLabel manipulateLabel = new JLabel(" Apply manipulations:");
		multiplyButton = new JButton("Multiply (xy)");
		divideButton = new JButton("Divide (x/y)");
		invertButton = new JButton("Invert (1/x)");
		inputButton = new JButton("Input (y)");
		powerButton = new JButton("Power (x^q)");
		yPowerButton = new JButton("Multiply Power (xy^q)");
		// Register this object as ActionListener
		multiplyButton.addActionListener(this);
		divideButton.addActionListener(this);
		invertButton.addActionListener(this);
		inputButton.addActionListener(this);
		powerButton.addActionListener(this);
		yPowerButton.addActionListener(this);
		// There is a label for making the powers look like rationals.
		JLabel [] slashLabel = {new JLabel("/"),new JLabel("/"),new JLabel("/"),new JLabel("/"),new JLabel("/"),new JLabel("/")};
		// The power button and field.
		JLabel powerLabel = new JLabel(" Power (q):");
		powerFields = numeratorDenominatorFields();
		// The button to save the dimension.
		saveButton = new JButton("Store dimension x");
		saveButton.addActionListener(this);
		deleteButton = new JButton("Delete dimension y");
		deleteButton.addActionListener(this);
		
		// Sequentially there will be a group of layouts and a group of fields, buttons and a combobox.
		GroupLayout.ParallelGroup leftGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		leftGroup.addComponent(nameLabel)
			.addComponent(massLabel)
			.addComponent(lengthLabel)
			.addComponent(timeLabel)
			.addComponent(chargeLabel)
			.addComponent(temperatureLabel)
			.addComponent(dimensionLabel)
			.addComponent(manipulateLabel)
			.addComponent(dComboLabel)
			.addComponent(powerLabel);
		GroupLayout.ParallelGroup rightGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		rightGroup
			.addComponent(nameField)
			.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(massFields[0])
							.addComponent(lengthFields[0])
							.addComponent(timeFields[0])
							.addComponent(chargeFields[0])
							.addComponent(temperatureFields[0]))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(slashLabel[0])
							.addComponent(slashLabel[1])
							.addComponent(slashLabel[2])
							.addComponent(slashLabel[3])
							.addComponent(slashLabel[4]))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(massFields[1])
							.addComponent(lengthFields[1])
							.addComponent(timeFields[1])
							.addComponent(chargeFields[1])
							.addComponent(temperatureFields[1])))
			.addComponent(dimensionField)
			.addComponent(dCombo)
			.addGroup(layout.createSequentialGroup()
					.addComponent(powerFields[0])
					.addComponent(slashLabel[5])
					.addComponent(powerFields[1]))
			.addGroup(layout.createSequentialGroup()
					.addComponent(multiplyButton)
					.addComponent(divideButton)
					.addComponent(invertButton)
					.addComponent(inputButton))
			.addGroup(layout.createSequentialGroup()
					.addComponent(powerButton)
					.addComponent(yPowerButton))
			.addGroup(layout.createSequentialGroup()
					.addComponent(saveButton)
					.addComponent(deleteButton));
		layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(leftGroup).addGroup(rightGroup));
		
		// Vertically there is the name label and field, then the five dimensions, then the dimension selector.
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(nameLabel)
						.addComponent(nameField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(massLabel)
						.addComponent(massFields[0])
						.addComponent(slashLabel[0])
						.addComponent(massFields[1]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lengthLabel)
						.addComponent(lengthFields[0])
						.addComponent(slashLabel[1])
						.addComponent(lengthFields[1]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(timeLabel)
						.addComponent(timeFields[0])
						.addComponent(slashLabel[2])
						.addComponent(timeFields[1]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(chargeLabel)
						.addComponent(chargeFields[0])
						.addComponent(slashLabel[3])
						.addComponent(chargeFields[1]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(temperatureLabel)
						.addComponent(temperatureFields[0])
						.addComponent(slashLabel[4])
						.addComponent(temperatureFields[1]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dimensionLabel)
						.addComponent(dimensionField))
				.addGap(manipulateLabel.getPreferredSize().height)
				.addComponent(manipulateLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dComboLabel)
						.addComponent(dCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(powerLabel)
						.addComponent(powerFields[0])
						.addComponent(slashLabel[5])
						.addComponent(powerFields[1]))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(multiplyButton)
						.addComponent(divideButton)
						.addComponent(invertButton)
						.addComponent(inputButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(powerButton)
						.addComponent(yPowerButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(saveButton)
						.addComponent(deleteButton))
		);
	}
	
	/**
	 * An internal helper function which returns a pair of JFormattedTextFields which only accept integers.
	 * It registers this panel as a change listener so that the display of the current dimension can be updated.
	 * 
	 * @return a pair of <code>JFormattedTextField</code>s
	 */
	private JFormattedTextField [] numeratorDenominatorFields() {
		JFormattedTextField fields [] = {new JFormattedTextField(NumberFormat.getIntegerInstance()),
				new JFormattedTextField(NumberFormat.getIntegerInstance())};
		fields[0].setValue(0);
		fields[1].setValue(1);
		// Add this as a ChangeListener for display of intermediate results.
		fields[0].addPropertyChangeListener(this);
		fields[1].addPropertyChangeListener(this);
		return fields;
	}
	
	/**
	 * Sets the fields to correspond to the supplied dimension. Only accessible internally.
	 * 
	 * @param d, a <code>Dimension</code> to set to the current dimension.
	 */
	private void setDimension(Dimension d) {
		massFields[0].setValue(d.getMassPower().getNumerator());
		massFields[1].setValue(d.getMassPower().getDenominator());
		lengthFields[0].setValue(d.getLengthPower().getNumerator());
		lengthFields[1].setValue(d.getLengthPower().getDenominator());
		timeFields[0].setValue(d.getTimePower().getNumerator());
		timeFields[1].setValue(d.getTimePower().getDenominator());
		chargeFields[0].setValue(d.getChargePower().getNumerator());
		chargeFields[1].setValue(d.getChargePower().getDenominator());
		temperatureFields[0].setValue(d.getTemperaturePower().getNumerator());
		temperatureFields[1].setValue(d.getTemperaturePower().getDenominator());
	}
	
	/**
	 * This internal function reads all the text field and puts the corresponding Dimension into <code>currentDimension</code>.
	 */
	private void readDimension() {
		RationalNumber massPower = getPower(massFields);
		RationalNumber lengthPower = getPower(lengthFields);
		RationalNumber timePower = getPower(timeFields);
		RationalNumber chargePower = getPower(chargeFields);
		RationalNumber temperaturePower = getPower(temperatureFields);
		Document nameDocument = nameField.getDocument();
		String label;
		try {
			label = nameDocument.getText(nameDocument.getStartPosition().getOffset(), nameDocument.getLength());
		} catch (BadLocationException e) {
			// This exception should never be thrown, in theory; in any case, lets not crash. 
			label = "How did that happen?";
		}
		currentDimension = new Dimension(massPower,lengthPower,timePower,chargePower,temperaturePower,label);
	}
	
	/**
	 * A routine which tells if a current Dimension already exists with the supplied name. Useful because duplicate names are
	 * not allowed in the TreeList which is used to sort the combo boxes.
	 * 
	 * @param name, the <code>String</code> which corresponds to the name of the Dimension we are testing for.
	 * @return true if a Dimension exists with the given name, false otherwise
	 */
	public boolean nameExists(String name) {
		// Loop over default dimensions.
		for (int i = 0; i < Dimension.COMMONDIMENSIONS.length; i++) {
			if (Dimension.COMMONDIMENSIONS[i].toString().equals(name)) {
				return true;
			}
		}
		// Loop over extra dimensions.
		for (int i = 0; i < storedDimensions.size(); i++) {
			if (storedDimensions.get(i).toString().equals(name)) {
				return true;
			}
		}
		// We didn't find a match.
		return false;
	}
	
	/**
	 * Reads a pair of formatted text fields and returns a rational number.
	 * @param fields, a pair of <code>JFormattedTextField</code> objects that only accept integers.
	 * @return the <code>RationalNumber</code> corresponding to the given fields.
	 */
	private RationalNumber getPower(JFormattedTextField [] fields) {
		RationalNumber power = new RationalNumber(	Integer.valueOf(fields[0].getValue().toString()),
													Integer.valueOf(fields[1].getValue().toString()));
		return power;
	}

	/**
	 * Normal routine for adding a change listener. This method only takes a single change listener, 
	 * multiple calls overwrite it.
	 * @param changeListener, a <code>ChangeListener</code>.
	 */
	public void addChangeListener(ChangeListener changeListener) {
		theChangeListener = changeListener;
	}
	
	/**
	 * Fills a given combo box with the default dimensions and this panel's stored dimensions.
	 * Does not change the currently selected item. The Dimensions are sorted alphabetically by name.
	 * @param dimCombo, a <code>JComboBox</code> to fill with dimensions.
	 */
	public void fillDimensionCombo(JComboBox dimCombo) {
		// Preserve the selection, otherwise this routine will get annoying.
		Object selected = dimCombo.getSelectedItem();
		dimCombo.removeAllItems();
		TreeSet<Dimension> dimensionTree = new TreeSet<Dimension>(new ToStringComparator());
		// Add the defaults.
		for (int i = 0; i < Dimension.COMMONDIMENSIONS.length; i++) {
			dimensionTree.add(Dimension.COMMONDIMENSIONS[i]);
		}
		// Add the stored dimensions.
		for (int i = 0; i < storedDimensions.size(); i++) {
			dimensionTree.add((Dimension) storedDimensions.get(i));
		}
		Iterator<Dimension> iterator = dimensionTree.iterator();
		while(iterator.hasNext()) {
			dimCombo.addItem(iterator.next());
		}
		dimCombo.setSelectedItem(selected);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(inputButton.getActionCommand())) {
			Dimension d = (Dimension) dCombo.getSelectedItem();
			setDimension(d);
			nameField.setText(d.getName());
			PropertyChangeEvent evt = new PropertyChangeEvent(this, 
					"Input", currentDimension, dCombo.getSelectedItem());
			this.propertyChange(evt);
		} else if (e.getActionCommand().equals(multiplyButton.getActionCommand())) {
			Dimension y = (Dimension) dCombo.getSelectedItem();
			setDimension(y.times(currentDimension));
		} else if (e.getActionCommand().equals(divideButton.getActionCommand())) {
			Dimension y = (Dimension) dCombo.getSelectedItem();
			setDimension(currentDimension.dividedBy(y));
		} else if (e.getActionCommand().equals(invertButton.getActionCommand())) {
			setDimension(currentDimension.inverse());
		} else if (e.getActionCommand().equals(powerButton.getActionCommand())) {
			RationalNumber q = getPower(powerFields);
			setDimension(currentDimension.pow(q));
		} else if (e.getActionCommand().equals(yPowerButton.getActionCommand())) {
			RationalNumber q = getPower(powerFields);
			Dimension y = (Dimension) dCombo.getSelectedItem();
			setDimension(currentDimension.times(y.pow(q)));
		} else if (e.getActionCommand().equals(saveButton.getActionCommand())) {
			addDimension(currentDimension);
		} else if (e.getActionCommand().equals(deleteButton.getActionCommand())) {
			if (storedDimensions.contains(dCombo.getSelectedItem())) {
				Dimension y = (Dimension) dCombo.getSelectedItem();
				storedDimensions.remove(y);
				fillDimensionCombo(dCombo);
				if (theChangeListener != null) {
					theChangeListener.stateChanged(new ChangeEvent(this));
				}
			} else {
				// They want us to delete a default dimension? Tell them they are crazy.
				JOptionPane.showMessageDialog(this,
						"Selected dimension is a default dimension. I am not going to delete it.",
						"Message", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Public function used by other classes to add a dimension to this panel's storage.
	 * @param d, a <code>Dimension</code> to add.
	 */
	public void addDimension(Dimension d) {
		if (nameExists(d.getName())) {
			JOptionPane.showMessageDialog(this,
					"Please choose a unique name for your custom dimension.",
					"Name taken error", JOptionPane.ERROR_MESSAGE);
		} else {
			storedDimensions.add(d);
			Object selected = dCombo.getSelectedItem();
			fillDimensionCombo(dCombo);
			dCombo.setSelectedItem(selected);
			if (theChangeListener != null) {
				theChangeListener.stateChanged(new ChangeEvent(this));
			}
		}
	}
	
	/**
	 * Public function used to write stored Dimensions to the <code>ArcanumTome</code> class.
	 * @param arcanumTome, the <code>ArcanumTome</code> class object to write to.
	 */
	public void writeToTome(ArcanumTome arcanumTome) {
		arcanumTome.writeToTome(storedDimensions);
	}
	
	/**
	 * Public function used to read <code>Dimension</code> objects from the <code>ArcatnumTome</code>
	 * and store them in this <code>DimensionPanel</code>.
	 * @param arcanumTome, the <code>ArcanumTome</code> to read from.
	 */
	public void readFromTome(ArcanumTome arcanumTome) {
		Iterator<Object> iterator = arcanumTome.dimensionIterator();
		while(iterator.hasNext()) {
			Object readDimensionObject = iterator.next();
			if (readDimensionObject instanceof Dimension) {
				storedDimensions.add(readDimensionObject);
			} else {
				// TODO: messy error handling.
				System.err.println(readDimensionObject.toString());
			}
		}
		fillDimensionCombo(dCombo);
		if (theChangeListener != null) {
			theChangeListener.stateChanged(new ChangeEvent(this));
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		readDimension();
		dimensionField.setText(currentDimension.getName() + " = " + currentDimension.dimensionString());
	}

	// The following three methods listen to the document of nameField and update the display area.
	// If the program was not so small, the rapid firing of readDimension() would be overkill.
	@Override
	public void changedUpdate(DocumentEvent e) {
		readDimension();
		dimensionField.setText(currentDimension.getName() + " = " + currentDimension.dimensionString());
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		readDimension();
		dimensionField.setText(currentDimension.getName() + " = " + currentDimension.dimensionString());
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		readDimension();
		dimensionField.setText(currentDimension.getName() + " = " + currentDimension.dimensionString());	
	}
}

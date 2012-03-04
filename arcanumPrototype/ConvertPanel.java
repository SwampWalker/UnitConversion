package arcanumPrototype;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;

import dimensionalAnalysis.CommonUnits;
import dimensionalAnalysis.Dimension;
import dimensionalAnalysis.PhysicalQuantity;
import dimensionalAnalysis.UnitSystem;

public class ConvertPanel extends JPanel implements ActionListener {
	/**
	 * The auto-generated serial version ID.
	 */
	private static final long serialVersionUID = 6303836179029281939L;
	// The various fields that will need to be read to do the business...
	private JFormattedTextField valueField;
	private JComboBox dimensionCombo;
	private JComboBox unitSystemCombo;
	private JComboBox newUnitSystemCombo;
	private JTextField resultField;
	
	/**
	 * The one and only constructor for this object. Sets up the panel and sets the layout.
	 */
	public ConvertPanel() {
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);
		
		// Create horizontal and vertical group.
		GroupLayout.SequentialGroup horzGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertGroup = layout.createSequentialGroup();
		ParallelGroup rightGroup = layout.createParallelGroup();
		ParallelGroup leftGroup = layout.createParallelGroup();
		
		// Create the field and label for specifying the value of the quantity.
		JLabel labelHolder = new JLabel(" Value of quantity: ");
		leftGroup = leftGroup.addComponent(labelHolder);
		DecimalFormat dFormat = new DecimalFormat("#.E0");
		dFormat.setMaximumFractionDigits(309);
		valueField = new JFormattedTextField(dFormat);
		valueField.setValue(1.0e0);
		valueField.addActionListener(this);
		rightGroup = rightGroup.addComponent(valueField);
		vertGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
				addComponent(labelHolder).addComponent(valueField));
		
		// Create the field and label for specifying the dimension of the quantity.
		labelHolder = new JLabel(" Dimension of the quantity: ");
		leftGroup = leftGroup.addComponent(labelHolder);
		dimensionCombo = new JComboBox(Dimension.COMMONDIMENSIONS);
		rightGroup = rightGroup.addComponent(dimensionCombo);
		vertGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
				addComponent(labelHolder).addComponent(dimensionCombo));
		
		// Create the label and field for specifying the quantity's system of units.
		labelHolder = new JLabel(" Unit system of the quantity: ");
		leftGroup = leftGroup.addComponent(labelHolder);
		unitSystemCombo = new JComboBox(CommonUnits.COMBOUNITSYSTEMS);
		rightGroup = rightGroup.addComponent(unitSystemCombo);
		vertGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
				addComponent(labelHolder).addComponent(unitSystemCombo));
		
		// Create the label and field for specifying the system of units we will convert to.
		labelHolder = new JLabel(" Unit system to convert the quantity to: ");
		leftGroup = leftGroup.addComponent(labelHolder);
		newUnitSystemCombo = new JComboBox(CommonUnits.COMBOUNITSYSTEMS);
		rightGroup = rightGroup.addComponent(newUnitSystemCombo);
		vertGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
				addComponent(labelHolder).addComponent(newUnitSystemCombo));
		
		// Create the text field that the result will be displayed in.
		resultField = new JTextField();
		resultField.setEditable(false);
		JButton convertButton = new JButton("Convert!");
		convertButton.addActionListener(this);
		leftGroup.addComponent(convertButton);
		rightGroup.addComponent(resultField);
		vertGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
				addComponent(convertButton).addComponent(resultField));
		
		
		horzGroup.addGroup(leftGroup);
		horzGroup.addGroup(rightGroup);
		layout.setHorizontalGroup(horzGroup);
		layout.setVerticalGroup(vertGroup);
		
	}
	
	/**
	 * Public function which notifies this object of a dimension panel with stored dimensions.
	 * The <code>DimensionPanel</code> is then used to fill the dimension combo box of this panel.
	 * @param dPanel, a <code>DimensionPanel</code> object.
	 */
	public void updateDimensionCombo(DimensionPanel dPanel) {
		dPanel.fillDimensionCombo(dimensionCombo);
	}
	
	/**
	 * Public function which notifies this object of a unit system panel with stored dimensions.
	 * The <code>UnitPanel</code> is then used to fill the dimension combo box of this panel.
	 * @param unitPanel, a <code>UnitPanel</code> object.
	 */
	public void updateUnitSystemCombos(UnitPanel unitPanel) {
		UnitSystem selected = (UnitSystem) unitSystemCombo.getSelectedItem();
		unitPanel.fillUnitSystemCombo(unitSystemCombo);
		unitSystemCombo.setSelectedItem(selected);
		selected = (UnitSystem) newUnitSystemCombo.getSelectedItem();
		unitPanel.fillUnitSystemCombo(newUnitSystemCombo);
		newUnitSystemCombo.setSelectedItem(selected);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		double value = Double.valueOf(valueField.getValue().toString());
		Dimension theDimension = (Dimension) dimensionCombo.getSelectedItem();
		UnitSystem theUnitSystem = (UnitSystem) unitSystemCombo.getSelectedItem();
		UnitSystem newUnitSystem = (UnitSystem) newUnitSystemCombo.getSelectedItem();
		PhysicalQuantity x = new PhysicalQuantity(value,theDimension,theUnitSystem);
		PhysicalQuantity y = newUnitSystem.convert(x);
		resultField.setText( x.toString() + " = " + y.toString());
	}
}

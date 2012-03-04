package scroll;

import javax.swing.JApplet;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import arcanumPrototype.ConvertPanel;
import arcanumPrototype.DimensionPanel;
import arcanumPrototype.QuantityPanel;
import arcanumPrototype.UnitPanel;

public class Scroll extends JApplet implements ChangeListener {
	/**
	 * This was an auto-generated serial version ID.
	 */
	private static final long serialVersionUID = -3018742693705617763L;
	private DimensionPanel dimensionPanel;
	private ConvertPanel convertPanel;
	private UnitPanel unitPanel;
	private QuantityPanel quantityPanel;
	
	public void init() {
		JTabbedPane tabby = new JTabbedPane();
		
		// Make the panels.
		dimensionPanel = new DimensionPanel();
		convertPanel = new ConvertPanel();
		unitPanel = new UnitPanel();
		quantityPanel = new QuantityPanel();
		
		// Add action listener.
		dimensionPanel.addChangeListener(this);
		quantityPanel.addChangeListener(this);
		unitPanel.addChangeListener(this);
		
		// Add to the tab panel.
		tabby.add("Convert quantities",convertPanel);
		tabby.add("Create Dimensions",dimensionPanel);
		tabby.add("Create unit systems",unitPanel);
		tabby.add("Create new quantities",quantityPanel);
		
		// Add to the applet.
		this.getContentPane().add(tabby);
	}
	
	public void start() {}
	public void stop() {}

	@Override
	public void stateChanged(ChangeEvent e) {
		// In hindsight, it would have been a good idea to extract this method into a ChangeListener class to avoid redundant code...
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
}

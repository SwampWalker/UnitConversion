package atonita.unitconversion.arcanumprototype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import atonita.unitconversion.dimensionalanalysis.Dimension;
import atonita.unitconversion.dimensionalanalysis.PhysicalQuantity;
import atonita.unitconversion.dimensionalanalysis.SIQuantity;
import atonita.unitconversion.dimensionalanalysis.UnitSystem;

public class ArcanumTome implements Serializable {
	/**
	 * Serial version id for the ArcanumTome
	 */
	private static final long serialVersionUID = 7712650310280051323L;
	private ArrayList<Object> dimensionList;
	private ArrayList<Object> unitSystemList;
	private ArrayList<Object> quantityList;
	
	public ArcanumTome() {
		dimensionList = new ArrayList<Object>();
		unitSystemList = new ArrayList<Object>();
		quantityList = new ArrayList<Object>();
	}
	
	/**
	 * Method to write an array to the tome. The array can only contain <code>Dimension</code>, 
	 * <code>UnitSystem</code>, <code>PhysicalQuantity</code>, or <code>SIQuantity</code> objects.
	 * 
	 * @param list, the array list to write to the tome.
	 * @throws IllegalArgumentException if the <code>ArrayList</code> contains an unknown class object.
	 */
	public void writeToTome(ArrayList<Object> list) throws IllegalArgumentException {
		Iterator<Object> iterator = list.iterator();
		while (iterator.hasNext()) {
			Object item = iterator.next();
			if (item instanceof Dimension) {
				addToTome((Dimension) item);
			} else if (item instanceof SIQuantity) {
				addToTome((SIQuantity) item);
			} else if (item instanceof PhysicalQuantity) {
				addToTome((PhysicalQuantity) item);
			} else if (item instanceof UnitSystem) {
				addToTome((UnitSystem) item);
			} else {
				throw new IllegalArgumentException("Unknown object class");
			}
		}
	}
	
	// Some internal functions which correctly select the array to write to.
	private void addToTome(Dimension d) {
		dimensionList.add(d);
	}
	private void addToTome(UnitSystem us) {
		unitSystemList.add(us);
	}
	private void addToTome(PhysicalQuantity x) {
		quantityList.add(x);
	}
	private void addToTome(SIQuantity x) {
		quantityList.add(x);
	}
	
	/**
	 * Utility method which returns an <code>Iterator</code> which iterates over the stored <code>Dimension</code> objects
	 * and should be used to read those objects.
	 * 
	 * @return a <code>Iterator<Object></code> containing only objects of class <code>Dimension</code>.
	 */
	public Iterator<Object> dimensionIterator() {
		return dimensionList.iterator();
	}
	
	/**
	 * Utility method which returns an <code>Iterator</code> which iterates over the stored <code>UnitSystem</code> objects
	 * and should be used to read those objects.
	 * 
	 * @return a <code>Iterator<Object></code> containing only objects of class <code>UnitSystem</code>.
	 */
	public Iterator<Object> unitSystemIterator() {
		return unitSystemList.iterator();
	}
	
	/**
	 * Utility method which returns an <code>Iterator</code> which iterates over the stored <code>SIQuantity</code> and
	 * <code>PhysicalQuantity</code> objects and should be used to read those objects.
	 * 
	 * @return a <code>Iterator<Object></code> containing only objects of class <code>Dimension</code> or <code>PhysicalQuantity</code>.
	 */
	public Iterator<Object> quantityIterator() {
		return quantityList.iterator();
	}
}

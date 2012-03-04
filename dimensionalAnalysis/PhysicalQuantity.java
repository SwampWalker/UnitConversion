package dimensionalAnalysis;

import java.io.Serializable;

public class PhysicalQuantity implements Dimensioned, Unitted, Serializable {
	/**
	 * The auto-generated serial version ID.
	 */
	private static final long serialVersionUID = -1134463831660579571L;
	private double value;
	private Dimension theDimensions;
	private UnitSystem theUnits;
	private String symbol;
	private String comment;

	@Override
	public Dimension getDimension() {
		return theDimensions;
	}

	@Override
	public UnitSystem getUnits() {
		return theUnits;
	}
	
	/**
	 * Returns the numerical part of this quantity in its given unit system.
	 * @return the numberical value of this physical quantity
	 */
	public double getValue() {
		return value;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getComment() {
		return comment;
	}
	
	/**
	 * Constructor from scratch.
	 * 
	 * @param x, a <code>double</code> to be used as the value.
	 * @param dim, the <code>Dimension</code> of this quantity.
	 * @param units, the <code>UnitSystem</code> specifying the units used for the value give.
	 */
	public PhysicalQuantity(double x, Dimension dim, UnitSystem units) {
		value = x;
		theDimensions = dim;
		theUnits = units;
	}
	public PhysicalQuantity(double x, Dimension dim, UnitSystem units, String s, String c) {
		this(x,dim,units);
		symbol = s;
		comment = c;
	}
	public PhysicalQuantity(SIQuantity x) {
		value = x.getValue();
		theDimensions = x.getDimension();
		theUnits = SIConstants.SIUnits;
	}
	
	public String toString() {
		if (this.getSymbol() == null) {
			return this.toPhysicalString();
		} else if (this.getComment() == null || this.getComment().isEmpty()) {
			return this.getSymbol();
		} else {
			return new String(this.getSymbol() + " - " + this.getComment());
		}
	}
	
	public String toPhysicalString() {
		return toPhysicalString(true,true);
	}
	
	public String toPhysicalString(boolean suppressUnits, boolean useDerivedUnits) {
		StringBuffer pQBuffer = new StringBuffer();
		double value = this.getValue();
		UnitSystem units = this.getUnits();
		Dimension dimension = this.getDimension();
		if (units.hasDerivedUnit(dimension)) {
			PhysicalQuantity derivedUnit = units.getDerivedUnit(dimension);
			value = value/derivedUnit.getValue();
		}
		pQBuffer.append(value);
		if (theDimensions.isDimensionless()) {
		} else {
			pQBuffer.append(" " + this.getUnits().getUnitString(
					this.getDimension(),suppressUnits,useDerivedUnits));
		}
		return new String(pQBuffer);
	}
	
	public SIQuantity toSI() {
		double massFac = Math.pow(theUnits.getMassScale().getValue(), 			theDimensions.getMassPower().toDouble());
		double lengFac = Math.pow(theUnits.getLengthScale().getValue(), 		theDimensions.getLengthPower().toDouble());
		double timeFac = Math.pow(theUnits.getTimeScale().getValue(), 			theDimensions.getTimePower().toDouble());
		double chrgFac = Math.pow(theUnits.getChargeScale().getValue(),			theDimensions.getChargePower().toDouble());
		double tempFac = Math.pow(theUnits.getTemperatureScale().getValue(), 	theDimensions.getTemperaturePower().toDouble());
		double newValue = value*massFac*lengFac*timeFac*chrgFac*tempFac;
		return new SIQuantity(newValue,theDimensions,this.getSymbol(),this.getComment());
	}
}

package atonita.unitconversion.dimensionalanalysis;

import java.io.Serializable;

import atonita.unitconversion.rationalnumbers.RationalNumber;

public class Dimension implements Dimensioned, Serializable {
	/**
	 * the auto-generated serial version id.
	 */
	private static final long serialVersionUID = -855407636792105895L;
	private RationalNumber massPower;
	private RationalNumber lengthPower;
	private RationalNumber timePower;
	private RationalNumber chargePower;
	private RationalNumber temperaturePower;
	private String name = "unnamed";
	
	public RationalNumber getMassPower() {
		return massPower;
	}
	public RationalNumber getLengthPower() {
		return lengthPower;
	}
	public RationalNumber getTimePower() {
		return timePower;
	}
	public RationalNumber getChargePower() {
		return chargePower;
	}
	public RationalNumber getTemperaturePower() {
		return temperaturePower;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public Dimension getDimension() {
		return this;
	}
	
	/**
	 * The dimension constructor. Sets the dimensions to M^m L^l T^t.
	 * 
	 * @param m, an <code>integer</code> argument giving the power of the mass dimension.
	 * @param l, an <code>integer</code> argument giving the power of the length dimension.
	 * @param t, an <code>integer</code> argument giving the power of the time dimension.
	 */
	public Dimension(int m, int l, int t) {
		massPower = new RationalNumber(m);
		lengthPower = new RationalNumber(l);
		timePower = new RationalNumber(t);
		chargePower = new RationalNumber(0);
		temperaturePower = new RationalNumber(0);
	}
	/**
	 * The dimension constructor. Sets the dimensions to M^m L^l T^t, also sets the name of this dimension.
	 * 
	 * @param m, an <code>integer</code> argument giving the power of the mass dimension.
	 * @param l, an <code>integer</code> argument giving the power of the length dimension.
	 * @param t, an <code>integer</code> argument giving the power of the time dimension.
	 * @param label, a <code>string</code> which will be the name of the dimension.
	 */
	public Dimension(int m, int l, int t, String label){
		this(m,l,t);
		name = label;
	}
	/**
	 * Dimension constructor including charge. Sets the dimensions to M^m L^l T^t Q^q.
	 * @param m, an <code>integer</code> argument giving the power of the mass dimension.
	 * @param l, an <code>integer</code> argument giving the power of the length dimension.
	 * @param t, an <code>integer</code> argument giving the power of the time dimension.
	 * @param q, an <code>integer</code> argument giving the power of the charge dimension.
	 */
	public Dimension(int m, int l, int t, int q) {
		this(m,l,t);
		chargePower = new RationalNumber(q);
	}
	/**
	 * Dimension constructor including charge. Sets the dimensions to M^m L^l T^t Q^q. Also sets the name of the dimension.
	 * @param m, an <code>integer</code> argument giving the power of the mass dimension.
	 * @param l, an <code>integer</code> argument giving the power of the length dimension.
	 * @param t, an <code>integer</code> argument giving the power of the time dimension.
	 * @param q, an <code>integer</code> argument giving the power of the charge dimension.
	 * @param label, a <code>string</code> which will be the name of the dimension.
	 */
	public Dimension(int m, int l, int t, int q, String label) {
		this(m,l,t,q);
		name = label;
	}
	/**
	 * Dimension constructor including charge and temperature. Sets the dimensions to 
	 * 	M^m L^l T^t Q^q \theta^s. Also sets the name of the dimension.
	 * @param m, an <code>integer</code> argument giving the power of the mass dimension.
	 * @param l, an <code>integer</code> argument giving the power of the length dimension.
	 * @param t, an <code>integer</code> argument giving the power of the time dimension.
	 * @param q, an <code>integer</code> argument giving the power of the charge dimension.
	 * @param s, an <code>integer</code> argument giving the power of the temperature dimension.
	 * @param label, a <code>string</code> which will be the name of the dimension.
	 */
	public Dimension(int m, int l, int t, int q, int s, String label) {
		this(m,l,t,q,s);
		name = label;
	}
	/**
	 * Dimension constructor including charge and temperature. Sets the dimensions to 
	 * 	M^m L^l T^t Q^q \theta^s.
	 * @param m, an <code>integer</code> argument giving the power of the mass dimension.
	 * @param l, an <code>integer</code> argument giving the power of the length dimension.
	 * @param t, an <code>integer</code> argument giving the power of the time dimension.
	 * @param q, an <code>integer</code> argument giving the power of the charge dimension.
	 * @param s, an <code>integer</code> argument giving the power of the temperature dimension.
	 */
	public Dimension(int m, int l, int t, int q, int s) {
		this(m,l,t,q);
		temperaturePower = new RationalNumber(s);
	}
	/**
	 * Copies the dimensions of the dimension d.
	 * 
	 * @param d, a <code>Dimension</code> object.
	 */
	public Dimension(Dimension d) {
		massPower = d.getMassPower();
		lengthPower = d.getLengthPower();
		timePower = d.getTimePower();
		chargePower = d.getChargePower();
		temperaturePower = d.getTemperaturePower();
		name = d.getName();
	}
	/**
	 * Copies the dimensions of the dimension d. Sets the name.
	 * 
	 * @param d, a <code>Dimension</code> object.
	 */
	public Dimension(Dimension d, String label) {
		massPower = d.getMassPower();
		lengthPower = d.getLengthPower();
		timePower = d.getTimePower();
		chargePower = d.getChargePower();
		temperaturePower = d.getTemperaturePower();
		name = label;
	}
	/**
	 * Returns a dimension object corresponding to the dimensions of the supplied physical quantitity.
	 * @param x, a <code>PhysicalQuantity</code> object.
	 */
	public Dimension(Dimensioned x) {
		this(x.getDimension());
	}
	public Dimension(RationalNumber m, RationalNumber l, RationalNumber t, RationalNumber q, RationalNumber s) {
		massPower = m;
		lengthPower = l;
		timePower = t;
		chargePower = q;
		temperaturePower = s;
	}
	public Dimension(RationalNumber m, RationalNumber l, RationalNumber t, RationalNumber q, RationalNumber s, String label) {
		this(m,l,t,q,s);
		name = label;
	}
	
	/**
	 * Compares this dimension to the supplied dimension, returns true if they are the same.
	 * 
	 * @param d another <code>Dimension</code>.
	 * @return
	 */
	public boolean equals(Dimension d) {
		if (	massPower == d.getMassPower() &&
				lengthPower == d.getLengthPower() &&
				timePower == d.getTimePower() &&
				chargePower == d.getChargePower() &&
				temperaturePower == d.getTemperaturePower()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Compares this dimension to the dimension of the supplied physical quantity, returns true if
	 * the physical quantity has the same dimensions as this object.
	 * 
	 * @param x, a <code>PhysicalQuantity</code>.
	 * @return
	 */
	public boolean hasSameDimensionAs(Dimensioned x) {
		if (!this.getChargePower().equals(x.getDimension().getChargePower())) {
			return false;
		}
		if (!this.getMassPower().equals(x.getDimension().getMassPower())) {
			return false;
		}
		if (!this.getLengthPower().equals(x.getDimension().getLengthPower())) {
			return false;
		}
		if (!this.getTimePower().equals(x.getDimension().getTimePower())) {
			return false;
		}
		if (!this.getTemperaturePower().equals(x.getDimension().getTemperaturePower())) {
			return false;
		}
		return true;
	}
	
	public boolean isDimensionless() {
		if (this.hasSameDimensionAs(DIMENSIONLESS)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Dimension times(Dimension d) {
		return new Dimension(this.getMassPower().add(d.getMassPower()),
							this.getLengthPower().add(d.getLengthPower()),
							this.getTimePower().add(d.getTimePower()),
							this.getChargePower().add(d.getChargePower()),
							this.getTemperaturePower().add(d.getTemperaturePower()));
	}
	public Dimension dividedBy(Dimension d) {
		return new Dimension(this.getMassPower().subtract(d.getMassPower()),
				this.getLengthPower().subtract(d.getLengthPower()),
				this.getTimePower().subtract(d.getTimePower()),
				this.getChargePower().subtract(d.getChargePower()),
				this.getTemperaturePower().subtract(d.getTemperaturePower()));
	}
	public Dimension inverse() {
		return new Dimension(this.getMassPower().multiply(-1),
								this.getLengthPower().multiply(-1),
								this.getTimePower().multiply(-1),
								this.getChargePower().multiply(-1),
								this.getTemperaturePower().multiply(-1));
	}
	public Dimension pow(RationalNumber q) {
		return new Dimension(this.getMassPower().multiply(q),
				this.getLengthPower().multiply(q),
				this.getTimePower().multiply(q),
				this.getChargePower().multiply(q),
				this.getTemperaturePower().multiply(q));
	}
	
	public String toVectorString() {
		StringBuffer sb = new StringBuffer();
		sb.append(massPower.toString());
		sb.append(" ");
		sb.append(lengthPower.toString());
		sb.append(" ");
		sb.append(timePower.toString());
		sb.append(" ");
		sb.append(chargePower.toString());
		sb.append(" ");
		sb.append(temperaturePower.toString());
		sb.append("\n");
		return (new String(sb));
	}
	
	public String dimensionString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		int dimensions = 0;
		if (this.getMassPower().isOne()) {
			sb.append("M");
			dimensions++;
		}else if (!this.getMassPower().isZero()) {
			sb.append("M^(" + String.valueOf(this.getMassPower()) + ")");
			dimensions++;
		}
		if (this.getLengthPower().isOne()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("L");
			dimensions++;
		}else if (!this.getLengthPower().isZero()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("L^(" + String.valueOf(this.getLengthPower()) + ")");
			dimensions++;
		}
		if (this.getTimePower().isOne()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("T");
			dimensions++;
		}else if (!this.getTimePower().isZero()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("T^(" + String.valueOf(this.getTimePower()) + ")");
			dimensions++;
		}
		if (this.getChargePower().isOne()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("Q");
			dimensions++;
		}else if (!this.getChargePower().isZero()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("Q^(" + String.valueOf(this.getChargePower()) + ")");
			dimensions++;
		}
		if (this.getTemperaturePower().isOne()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("\u0398");
			dimensions++;
		}else if (!this.getTemperaturePower().isZero()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("\u0398"+"^(" + String.valueOf(this.getTemperaturePower()) + ")");
			dimensions++;
		}
		if (dimensions != 0) {
			return new String(sb);
		} else {
			return "";
		}
	}
	
	// These guys are here to make it possible to make true static dimensions below without throwing ExceptionInInitializerError's.
	private static RationalNumber zero = new RationalNumber(0,1);
	private static RationalNumber one = new RationalNumber(1,1);
	private static RationalNumber two = new RationalNumber(2,1);
	private static RationalNumber three = new RationalNumber(3,1);
	
	public static Dimension DIMENSIONLESS = new Dimension(zero,zero,zero,zero,zero,"Dimensionless");

	//Fundamental dimensions involving length.
	public static Dimension LENGTH = new Dimension(zero,one,zero,zero,zero,"Length");
	public static Dimension AREA = new Dimension(zero,two,zero,zero,zero,"Area");
	public static Dimension VOLUME = new Dimension(zero,three,zero,zero,zero,"Volume");
	public static Dimension WAVENUMBER = new Dimension(zero,one.multiply(-1),zero,zero,zero,"Wavenumber");
	public static Dimension NUMBERDENSITY = new Dimension(zero,three.multiply(-1),zero,zero,zero,"Number density");

	// Fundamental measure of time.
	public static Dimension TIME = new Dimension(zero,zero,one,zero,zero,"Time");
	public static Dimension PERIOD = new Dimension(zero,zero,one,zero,zero,"Period");
	public static Dimension FREQUENCY = new Dimension(zero,zero,one.multiply(-1),zero,zero,"Frequency");

	// Dimensions derived from LENGTH and time alone.
	public static Dimension VELOCITY = new Dimension(LENGTH.dividedBy(TIME),"Velocity");
	public static Dimension ACCELERATION = new Dimension(VELOCITY.dividedBy(TIME),"Acceleration");
	
	// Fundamental measure of mass.
	public static Dimension MASS = new Dimension(one,zero,zero,zero,zero,"Mass");
	
	//Dimensions derived from mass, LENGTH and time.
	public static Dimension LINEDENSITY = new Dimension(MASS.dividedBy(LENGTH),"Line density");
	public static Dimension SURFACEDENSITY = new Dimension(MASS.dividedBy(AREA),"Surface density");
	public static Dimension DENSITY = new Dimension(MASS.dividedBy(VOLUME),"Density");
	public static Dimension MOMENTUM = new Dimension(MASS.times(VELOCITY),"Momentum");
	public static Dimension FORCE = new Dimension(MASS.times(ACCELERATION),"Force");
	public static Dimension ENERGY = new Dimension(FORCE.times(LENGTH),"Energy");
	public static Dimension POWER = new Dimension(ENERGY.dividedBy(TIME),"Power");
	public static Dimension PRESSURE = new Dimension(FORCE.dividedBy(AREA),"Pressure");
	public static Dimension ENERGYDENSITY = new Dimension(ENERGY.dividedBy(VOLUME),"Energy density");
	public static Dimension ANGULARVELOCITY = new Dimension(FREQUENCY,"Angular velocity");
	public static Dimension INERTIALMOMENT = new Dimension(MASS.times(AREA),"Inertial moment");
	public static Dimension ANGULARMOMENTUM = new Dimension(INERTIALMOMENT.times(ANGULARVELOCITY),"Angular momentum");
	public static Dimension TORQUE = new Dimension(FORCE.times(LENGTH),"Torque");
	
	// Fundamental measure of charge.
	public static Dimension CHARGE = new Dimension(zero,zero,zero,one,zero,"Charge");
	
	// Dimensions derived from mass, LENGTH, time, charge.
	public static Dimension LINECHARGEDENSITY = new Dimension(CHARGE.dividedBy(LENGTH),"Line charge density");
	public static Dimension SURFACECHARGEDENSITY = new Dimension(CHARGE.dividedBy(AREA),"Surface charge density");
	public static Dimension CHARGEDENSITY = new Dimension(CHARGE.dividedBy(VOLUME),"Charge density");
	public static Dimension CURRENT = new Dimension(CHARGE.dividedBy(TIME),"Current");
	public static Dimension VOLTAGE = new Dimension(ENERGY.dividedBy(CHARGE),"Voltage");
	public static Dimension CAPACITANCE = new Dimension(VOLTAGE.dividedBy(CHARGE),"Capacitance");
	public static Dimension RESISTANCE = new Dimension(VOLTAGE.dividedBy(CURRENT),"Resistance");
	public static Dimension INDUCTANCE = new Dimension(VOLTAGE.dividedBy(CURRENT.dividedBy(TIME)),"Inductance");
	public static Dimension MAGNETICFIELD = new Dimension(FORCE.dividedBy(CHARGE).dividedBy(VELOCITY),"Magnetic field");
	public static Dimension MAGNETICINTENSITY = new Dimension(CURRENT.dividedBy(LENGTH),"Magnetic intensity");
	/**
	 * If magnetic monopoles exist, than there are various ways of assigning dimension to them. This dimension corresponds to the weber convention
	 * in SI units.
	 */
	public static Dimension MAGNETICCHARGE = new Dimension(MAGNETICFIELD.times(AREA),"Magnetic monopole(Weber)");
	public static Dimension ELECTRICFIELD = new Dimension(VOLTAGE.dividedBy(LENGTH),"Electric field");
	
	// Temperature
	public static final Dimension TEMPERATURE = new Dimension(zero,zero,zero,zero,one,"Temperature");
	
	// In alphabetical order for easy display.
	public static Dimension [] COMMONDIMENSIONS = {DIMENSIONLESS, 
		ACCELERATION, ANGULARMOMENTUM, ANGULARVELOCITY, AREA,
		CAPACITANCE, CHARGE, CHARGEDENSITY, CURRENT,
		DENSITY,
		ELECTRICFIELD, ENERGY, ENERGYDENSITY,
		FORCE, FREQUENCY,
		INDUCTANCE, INERTIALMOMENT,
		LENGTH, LINECHARGEDENSITY, LINEDENSITY,
		MAGNETICCHARGE, MAGNETICFIELD, MAGNETICINTENSITY, MASS, MOMENTUM,
		NUMBERDENSITY,
		PERIOD, POWER, PRESSURE,
		RESISTANCE, 
		SURFACECHARGEDENSITY, SURFACEDENSITY,
		TEMPERATURE, TIME, TORQUE,
		VELOCITY, VOLTAGE, VOLUME,
		WAVENUMBER
		};
}

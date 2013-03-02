package atonita.unitconversion.dimensionalanalysis;

import java.io.Serializable;


public class SIQuantity implements Dimensioned, Serializable {
	/**
	 * The auto-generated serial version ID.
	 */
	private static final long serialVersionUID = -1208825691666507965L;
	private Dimension theDimension;
	private double theValue;
	private String name;
	private String comment;

	@Override
	public Dimension getDimension() {
		return theDimension;
	}
	
	public String unitsToString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		int dimensions = 0;
		if (theDimension.getMassPower().isOne()) {
			sb.append("kg");
			dimensions++;
		}else if (!theDimension.getMassPower().isZero()) {
			sb.append("kg^(" + String.valueOf(theDimension.getMassPower()) + ")");
			dimensions++;
		}
		if (theDimension.getLengthPower().isOne()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("m");
			dimensions++;
		}else if (!theDimension.getLengthPower().isZero()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("m^(" + String.valueOf(theDimension.getLengthPower()) + ")");
			dimensions++;
		}
		if (theDimension.getTimePower().isOne()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("s");
			dimensions++;
		}else if (!theDimension.getTimePower().isZero()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("s^(" + String.valueOf(theDimension.getTimePower()) + ")");
			dimensions++;
		}
		if (theDimension.getChargePower().isOne()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("C");
			dimensions++;
		}else if (!theDimension.getChargePower().isZero()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("C^(" + String.valueOf(theDimension.getChargePower()) + ")");
			dimensions++;
		}
		if (theDimension.getTemperaturePower().isOne()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("K");
			dimensions++;
		}else if (!theDimension.getTemperaturePower().isZero()) {
			if (dimensions > 0) { sb.append("*");}
			sb.append("K^(" + String.valueOf(theDimension.getTemperaturePower()) + ")");
			dimensions++;
		}
		if (dimensions != 0) {
			return new String(sb);
		} else {
			return "";
		}
	}

	public double getValue() {
		return theValue;
	}
	
	public String getName() {
		return new String(name);
	}
	public String getComment() {
		return new String(comment);
	}
	public void setName(String s) {
		name = s;
	}
	
	public SIQuantity(double x, Dimension d) {
		theDimension = new Dimension(d);
		theValue = x;
		name = "";
	}
	public SIQuantity(double x, Dimension d, String s) {
		theDimension = new Dimension(d);
		theValue = x;
		name = s;
	}
	public SIQuantity(double x, Dimension d, String s, String c) {
		theDimension = new Dimension(d);
		theValue = x;
		name = s;
		comment = c;
	}
	public SIQuantity(SIQuantity q) {
		this(q.getValue(),q.getDimension(),q.getName(), q.getComment());
	}
	public SIQuantity(SIQuantity q, String s) {
		this(q.getValue(),q.getDimension(),s);
	}
	public SIQuantity(SIQuantity q, String s, String c) {
		this(q.getValue(),q.getDimension(),s,c);
	}
	
	public SIQuantity plus(SIQuantity x) {
		if (theDimension.hasSameDimensionAs(x)) {
			return new SIQuantity(theValue+x.getValue(),theDimension);
		} else {
			return null;
		}
	}
	public SIQuantity times(SIQuantity x) {
		return new SIQuantity(theValue*x.getValue(),theDimension.times(x.getDimension()));
	}
	public SIQuantity times(double x) {
		return new SIQuantity(theValue*x,theDimension);
	}
	public SIQuantity minus(SIQuantity x) {
		return this.plus(x.times(-1.0e0));
	}
	public SIQuantity inverse() {
		return new SIQuantity(1.0e0/theValue,theDimension.inverse());
	}
	public SIQuantity dividedBy(SIQuantity x) {
		return new SIQuantity(theValue/x.getValue(),theDimension.dividedBy(x.getDimension()));
	}

	public String toPhysicalString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.valueOf(getValue()));
		sb.append(this.unitsToString());
		return (new String(sb));
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		if (comment != null && !comment.isEmpty()) {
			sb.append(" - " + comment);
		}
		return new String(sb);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SIQuantity)) {
			return false;
		} else {
			Dimension d = ((SIQuantity) o).getDimension();
			double val = ((SIQuantity) o).getValue();
			if (d.hasSameDimensionAs(this.getDimension())) {
				if (val == this.getValue()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
}

package atonita.unitconversion.dimensionalanalysis;

import java.io.Serializable;

import atonita.unitconversion.rationalnumbers.RationalAnalysis;
import atonita.unitconversion.rationalnumbers.RationalNumber;

public class UnitSystem implements Unitted, Serializable {

    /**
     * The auto-generate serial version ID.
     */
    private static final long serialVersionUID = -8322864303358197121L;
    // The units are the quantities which equal unity in this unit system.
    private SIQuantity[] units = new SIQuantity[5];
    // Some indexes, for ease of usage internally.
    private static final int massScale = 0;
    private static final int lengthScale = 1;
    private static final int timeScale = 2;
    private static final int chargeScale = 3;
    private static final int temperatureScale = 4;
    // Suppress certain units from appearing in the unit strings
    private boolean[] suppress = new boolean[5];
    // Enforce some derived units.
    private SIQuantity[] derivedUnits;
    private int numberDerivedUnits;
    // The important quantities for displaying numbers in the unitsystem.
    private SIQuantity[] scale = new SIQuantity[5];
    private RationalNumber[][] unitInverse = new RationalNumber[5][5];
    // The name of the unit system.
    private String name = "unnamed";

    public String getName() {
        return name;
    }

    public String toString() {
        return getName();
    }

    public SIQuantity getUnit(int i) {
        return units[i];
    }

    public SIQuantity getMassScale() {
        return scale[massScale];
    }

    public SIQuantity getLengthScale() {
        return scale[lengthScale];
    }

    public SIQuantity getTimeScale() {
        return scale[timeScale];
    }

    public SIQuantity getChargeScale() {
        return scale[chargeScale];
    }

    public SIQuantity getTemperatureScale() {
        return scale[temperatureScale];
    }

    public String getMassRelation() {
        return getUnitString(Dimension.MASS);
    }

    public String getLengthRelation() {
        return getUnitString(Dimension.LENGTH);
    }

    public String getTimeRelation() {
        return getUnitString(Dimension.TIME);
    }

    public String getChargeRelation() {
        return getUnitString(Dimension.CHARGE);
    }

    public String getTemperatureRelation() {
        return getUnitString(Dimension.TEMPERATURE);
    }

    public UnitSystem(SIQuantity[] u, boolean[] s, SIQuantity[] d, String n) {
        this(u[0], u[1], u[2], u[3], u[4], s, d, n);
    }

    /**
     * The constructor of a unit system. Requires 5 SI quantities which are to
     * equal zero in this system of units.
     *
     * @param u1, an
     * <code>SIQuantity</code>
     * @param u2, an
     * <code>SIQuantity</code>
     * @param u3, an
     * <code>SIQuantity</code>
     * @param u4, an
     * <code>SIQuantity</code>
     * @param u5, an
     * <code>SIQuantity</code>
     */
    public UnitSystem(SIQuantity u1, SIQuantity u2, SIQuantity u3,
            SIQuantity u4, SIQuantity u5, boolean[] suppressors) {
        units[0] = u1;
        units[1] = u2;
        units[2] = u3;
        units[3] = u4;
        units[4] = u5;
        numberDerivedUnits = 0;

        // Initialize a couple of local helper variables.
        Dimension[] dimensions = new Dimension[5];
        double[] values = new double[5];
        for (int i = 0; i < 5; i++) {
            dimensions[i] = units[i].getDimension();
            suppress[i] = suppressors[i];
            values[i] = units[i].getValue();
        }

        // Create the array of powers of dimension for each unit supplied.
        RationalNumber[][] array = new RationalNumber[5][5];
        for (int i = 0; i < 5; i++) {
            array[i][0] = new RationalNumber(dimensions[i].getMassPower());
            array[i][1] = new RationalNumber(dimensions[i].getLengthPower());
            array[i][2] = new RationalNumber(dimensions[i].getTimePower());
            array[i][3] = new RationalNumber(dimensions[i].getChargePower());
            array[i][4] = new RationalNumber(dimensions[i].getTemperaturePower());
        }
        // Make sure the chosen units are not redundant.
        if (RationalAnalysis.det(array).getNumerator() == 0) {
            throw new IllegalArgumentException("Unit specifiers must be independent");
        }

        // Compute the inverse of the dimensional power matrix.
        unitInverse = atonita.unitconversion.rationalnumbers.RationalAnalysis.inverse(array);
        for (int i = 0; i < 5; i++) {
            values[i] = 1.0e0;
            for (int j = 0; j < 5; j++) {
                values[i] *= Math.pow(units[j].getValue(), unitInverse[i][j].toDouble());
            }
        }

        // Compute the units.
        scale[massScale] = new SIQuantity(values[0], Dimension.MASS);
        scale[lengthScale] = new SIQuantity(values[1], Dimension.LENGTH);
        scale[timeScale] = new SIQuantity(values[2], Dimension.TIME);
        scale[chargeScale] = new SIQuantity(values[3], Dimension.CHARGE);
        scale[temperatureScale] = new SIQuantity(values[4], Dimension.TEMPERATURE);
        suppress(suppressors);
    }

    public UnitSystem(SIQuantity u1, SIQuantity u2, SIQuantity u3,
            SIQuantity u4, SIQuantity u5, boolean[] suppressors, String label) {
        this(u1, u2, u3, u4, u5, suppressors);
        name = label;
    }

    public UnitSystem(SIQuantity u1, SIQuantity u2, SIQuantity u3,
            SIQuantity u4, SIQuantity u5, boolean[] suppressors, SIQuantity[] derived) {
        this(u1, u2, u3, u4, u5, suppressors);
        numberDerivedUnits = derived.length;
        derivedUnits = new SIQuantity[numberDerivedUnits];
        System.arraycopy(derived, 0, derivedUnits, 0, numberDerivedUnits);
    }

    public UnitSystem(SIQuantity u1, SIQuantity u2, SIQuantity u3,
            SIQuantity u4, SIQuantity u5, boolean[] suppressors, SIQuantity[] derived,
            String label) {
        this(u1, u2, u3, u4, u5, suppressors);
        numberDerivedUnits = derived.length;
        derivedUnits = new SIQuantity[numberDerivedUnits];
        System.arraycopy(derived, 0, derivedUnits, 0, numberDerivedUnits);
        name = label;
    }

    public UnitSystem(SIQuantity u1, SIQuantity u2, SIQuantity u3,
            SIQuantity u4, SIQuantity u5, SIQuantity[] derived) {
        this(u1, u2, u3, u4, u5, new boolean[]{false, false, false, false, false},
                derived);
    }

    public UnitSystem(SIQuantity u1, SIQuantity u2, SIQuantity u3,
            SIQuantity u4, SIQuantity u5, SIQuantity[] derived, String label) {
        this(u1, u2, u3, u4, u5, new boolean[]{false, false, false, false, false},
                derived, label);
    }

    public UnitSystem(SIQuantity u1, SIQuantity u2, SIQuantity u3,
            SIQuantity u4, SIQuantity u5) {
        this(u1, u2, u3, u4, u5, new boolean[]{false, false, false, false, false});
    }

    public UnitSystem(SIQuantity u1, SIQuantity u2, SIQuantity u3,
            SIQuantity u4, SIQuantity u5, String label) {
        this(u1, u2, u3, u4, u5, new boolean[]{false, false, false, false, false}, label);
    }

    /**
     * Essentially duplicates a unit system.
     *
     * @param us a Unit System
     */
    public UnitSystem(UnitSystem us) {
        this(us.getUnit(0),
                us.getUnit(1),
                us.getUnit(2),
                us.getUnit(3),
                us.getUnit(4),
                us.getSuppressors(),
                us.getDerivedUnits());
    }

    public UnitSystem(UnitSystem us, String s) {
        this(us);
        name = s;
    }

    @Override
    public UnitSystem getUnits() {
        return this;
    }

    /**
     * Converts the given physical quantity into this unit system.
     *
     * @param x the physical quantity to convert
     * @return the physical quantity converted into this unit system
     */
    public PhysicalQuantity convert(PhysicalQuantity x) {
        Dimension dims = x.getDimension();
        UnitSystem system = x.getUnits();
        double massFac = system.getMassScale().getValue() / this.getMassScale().getValue();
        double lengFac = system.getLengthScale().getValue() / this.getLengthScale().getValue();
        double timeFac = system.getTimeScale().getValue() / this.getTimeScale().getValue();
        double chrgFac = system.getChargeScale().getValue() / this.getChargeScale().getValue();
        double tempFac = system.getTemperatureScale().getValue() / this.getTemperatureScale().getValue();
        massFac = Math.pow(massFac, dims.getMassPower().toDouble());
        lengFac = Math.pow(lengFac, dims.getLengthPower().toDouble());
        timeFac = Math.pow(timeFac, dims.getTimePower().toDouble());
        chrgFac = Math.pow(chrgFac, dims.getChargePower().toDouble());
        tempFac = Math.pow(tempFac, dims.getTemperaturePower().toDouble());
        double value = x.getValue() * massFac * lengFac * timeFac * chrgFac * tempFac;
        return new PhysicalQuantity(value, dims, this);
    }

    /**
     * Converts the given physical quantity into this unit system.
     *
     * @param x the SI quantity to convert
     * @return the physical quantity converted into this unit system
     */
    public PhysicalQuantity convert(SIQuantity x) {
        return convert(new PhysicalQuantity(x));
    }

    /**
     * Method to set the suppressors for the supplied units. When suppressed,
     * the units will not appear in unit strings and labels.
     *
     * @param suppressors, an array of 5 booleans which control the suppression
     * of the 5 units supplied to the constructor
     * @throws IllegalArgumentException if there are fewer or greater than 5
     * suppressors
     */
    public final void suppress(boolean[] suppressors) throws IllegalArgumentException {
        if (suppressors.length != 5) {
            throw new IllegalArgumentException("Bad number of supressors. Need 5.");
        } else {
            System.arraycopy(suppressors, 0, suppress, 0, 5);
        }
    }

    /**
     * Returns the boolean array of suppressors for this unit system.
     *
     * @return the array of suppressors
     */
    public boolean[] getSuppressors() {
        boolean[] duplicateSuppressors = new boolean[5];
        System.arraycopy(suppress, 0, duplicateSuppressors, 0, 5);
        return duplicateSuppressors;
    }

    /**
     * Adds a derived unit to this unit system. If a quantity has a dimension of
     * the derived unit, it will be displayed in units of this quantity.
     *
     * @param unit, the derived unit
     * @return
     * <code>true</code> if the dimension corresponding to the unit does not
     * already have a derived unit,
     * <code>false</code> otherwise
     */
    public boolean addDerivedUnit(SIQuantity unit) {
        boolean isForNewDimension = true;
        if (numberDerivedUnits != 0) {
            for (int i = 0; i < numberDerivedUnits; i++) {
                Dimension d = derivedUnits[i].getDimension();
                if (d.equals(unit.getDimension())) {
                    isForNewDimension = false;
                }
            }
        }
        if (isForNewDimension) {
            SIQuantity[] newDerivedUnits = new SIQuantity[numberDerivedUnits + 1];
            newDerivedUnits[0] = unit;
            for (int i = 1; i < newDerivedUnits.length; i++) {
                newDerivedUnits[i] = derivedUnits[i - 1];
            }
            numberDerivedUnits += 1;
            derivedUnits = newDerivedUnits;
        }
        return isForNewDimension;
    }

    public boolean hasDerivedUnit(Dimension d) {
        boolean isDerivedUnit = false;
        for (int i = 0; i < numberDerivedUnits; i++) {
            Dimension dim = derivedUnits[i].getDimension();
            if (d.equals(dim)) {
                isDerivedUnit = true;
            }
        }
        return isDerivedUnit;
    }

    public PhysicalQuantity getDerivedUnit(Dimension d) {
        int derivedUnitIndex = -1;
        for (int i = 0; i < numberDerivedUnits; i++) {
            Dimension dim = derivedUnits[i].getDimension();
            if (d.equals(dim)) {
                derivedUnitIndex = i;
            }
        }
        if (derivedUnitIndex < 0) {
            return null;
        } else {
            SIQuantity SIDerivedUnit = derivedUnits[derivedUnitIndex];
            PhysicalQuantity derivedUnit = this.convert(SIDerivedUnit);
            return derivedUnit;
        }
    }

    public SIQuantity[] getDerivedUnits() {
        SIQuantity[] derived = new SIQuantity[numberDerivedUnits];
        for (int i = 0; i < numberDerivedUnits; i++) {
            derived[i] = new SIQuantity(derivedUnits[i]);
        }
        return derived;
    }

    /**
     * Simple version of the unit string. Returns the proper units for the
     * dimension supplied.
     *
     * @param d, the
     * <code>dimension</code> we want the units of
     * @return the unit string for the dimension
     */
    public String getUnitString(Dimension d) {
        return getUnitString(d, true, true);
    }

    /**
     * Version of unit string that allows one to overriding of suppression and
     * derived units. Thus, although c=1, maybe we want to print powers of c in
     * the unit string. Or maybe c does not equal 1 but we want velocities in
     * terms of fraction of the speed of light.
     *
     * @param d, the
     * <code>dimension</code> we want the units of
     * @param actuallySuppress, a
     * <code>boolean</code> which controls whether the suppressors are enforced
     * or not
     * @param useDerivedUnits, a
     * <code>boolean</code> which controls whether the derived units are used or
     * not
     * @return the unit string for the dimension
     */
    public String getUnitString(Dimension d, boolean actuallySuppress, boolean useDerivedUnits) {
        int numTerms = 0;
        StringBuffer num = new StringBuffer();
        int denTerms = 0;
        StringBuffer den = new StringBuffer();
        int derivedUnitIndex = -1;

        // Detect whether the dimension has a derived unit.
        for (int i = 0; i < numberDerivedUnits; i++) {
            if (d.equals(derivedUnits[i].getDimension())) {
                derivedUnitIndex = i;
            }
        }

        if (derivedUnitIndex < 0) {
            // There was no derived unit.
            // We are going to build up the unit string with a numerator and denominator, but we do it
            //		one symbol at a time.
            //	The entire string is given by:
            //			\product_{j=0}^4 (unit[i].getName)^(\sum_{i=0}^4 d.getDimension(j)*inverse[i][j]) 
            // Where dimension[0] = mass, dimension[1] = length, ... as given by the static declarations above.
            for (int j = 0; j < 5; j++) {
                if (!suppress[j] || !actuallySuppress) {
                    RationalNumber power = stringPower(j, d);
                    StringBuffer placeHolder = num;
                    // Figure out if it goes in the denominator or numerator.
                    if (power.getNumerator() > 0) {
                        placeHolder = num;
                        numTerms += 1;
                    } else if (power.getNumerator() < 0) {
                        placeHolder = den;
                        denTerms += 1;
                        power = power.multiply(-1);
                    }
                    // Write the appropriate expression to the string buffer.
                    if (power.isInteger() && power.getNumerator() == 1) {
                        if (placeHolder.length() > 0) {
                            placeHolder.append("*");
                        }
                        placeHolder.append(units[j].getName());
                    } else if (power.isInteger() && power.getNumerator() != 0) {
                        if (placeHolder.length() > 0) {
                            placeHolder.append("*");
                        }
                        placeHolder.append(units[j].getName() + "^" + power.toString());
                    } else if (power.getNumerator() != 0) {
                        if (placeHolder.length() > 0) {
                            placeHolder.append("*");
                        }
                        placeHolder.append(units[j].getName() + "^(" + power.toString() + ")");
                    }
                }
            }
            // Now we write the string appropriately with the following cases.
            // 		1. No terms in numerator, write numerator as 1
            // 		2. Otherwise write the numerator as given above.
            //			Denominator cases:
            //				i. No terms. Don't add anything to the above.
            //				ii. Otherwise, add brackets to the above if there were more than 1 terms and append a /.
            //					a. If there is only one term in denominator, don't put brackets.
            //					b. else, add brackets.
            StringBuffer units = new StringBuffer();
            String numerator;
            if (numTerms == 0 && denTerms != 0) {
                numerator = "1";
            } else {
                numerator = new String(num);
            }
            if (denTerms == 0) {
                units.append(numerator);
            } else if ((numTerms > 1) && (denTerms == 1)) {
                units.append("[" + numerator + "]/" + new String(den));
            } else if ((numTerms > 1) && (denTerms > 1)) {
                units.append("[" + numerator + "]/[" + new String(den) + "]");
            } else if (denTerms > 1) {
                units.append(numerator + "/[" + new String(den) + "]");
            } else {
                units.append(numerator + "/" + new String(den));
            }
            return new String(units);
        } else {
            return derivedUnits[derivedUnitIndex].getName();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UnitSystem)) {
            return false;
        } else {
            UnitSystem us = (UnitSystem) obj;
            // Loop over this system's units;
            int count = 0;
            for (int i = 0; i < 5; i++) {
                // Loop over the argument's units.
                for (int j = 0; j < 5; j++) {
                    if (this.getUnit(i).equals(us.getUnit(j))) {
                        count++;
                    }
                }
            }
            if (count == 5) {
                // The units were the same. Test the suppressors.
                int suppressorsSame = 0;
                for (int i = 0; i < 5; i++) {
                    if ((this.getSuppressors()[i] && us.getSuppressors()[i])
                            || (!this.getSuppressors()[i] && !us.getSuppressors()[i])) {
                        suppressorsSame++;
                    }
                }
                boolean sameDerivedUnits = false;
                if (this.getDerivedUnits().length == us.getDerivedUnits().length) {
                    int derivedUnitsSameCount = 0;
                    // Loop over this things derived units.
                    for (int i = 0; i < this.getDerivedUnits().length; i++) {
                        // Loop over argument's derived units.
                        for (int j = 0; j < us.getDerivedUnits().length; j++) {
                            if (this.getDerivedUnits()[i].equals(us.getDerivedUnits()[j])) {
                                derivedUnitsSameCount++;
                            }
                        }
                    }
                    if (derivedUnitsSameCount == this.getDerivedUnits().length) {
                        sameDerivedUnits = true;
                    }
                }
                if (sameDerivedUnits && (suppressorsSame == 5)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    // TODO: What does this do?
    private RationalNumber stringPower(int j, Dimension d) {
        RationalNumber power = unitInverse[massScale][j].multiply(d.getMassPower());
        power = power.add(unitInverse[lengthScale][j].multiply(d.getLengthPower()));
        power = power.add(unitInverse[timeScale][j].multiply(d.getTimePower()));
        power = power.add(unitInverse[chargeScale][j].multiply(d.getChargePower()));
        power = power.add(unitInverse[temperatureScale][j].multiply(d.getTemperaturePower()));
        return power;
    }

    /**
     * A static method to convert quantities in a single line from one unit
     * system into another.
     *
     * @param value the value of the physical quantity in it's given
     * (input)units
     * @param dimension the dimension of the physical quantity
     * @param inputUnits the units of the given quantity on input
     * @param outputUnits the units desired to have the quantity in on output
     * @return the value of the physical quantity in the chosen (output) units
     */
    public static double convert(double value, Dimension dimension, UnitSystem inputUnits, UnitSystem outputUnits) {
        PhysicalQuantity x = new PhysicalQuantity(value, dimension, inputUnits);
        x = outputUnits.convert(x);
        return x.getValue();
    }
}

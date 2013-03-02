package atonita.unitconversion.dimensionalanalysis;

public final class SIConstants {
	// First, a unitless number.
	public static SIQuantity UNITLESS = new SIQuantity(1.0e0,Dimension.DIMENSIONLESS,"1", "unitless");
	
	// SI unit of Dimension.LENGTH.
	public static SIQuantity METRE = new SIQuantity(1.0e0,Dimension.LENGTH,"m", "the metre");
	
	// SI unit of time.
	public static SIQuantity SECOND = new SIQuantity(1.0e0,Dimension.TIME,"s", "the second");
	
	// SI unit of mass.
	public static SIQuantity KILOGRAM = new SIQuantity(1.0e0,Dimension.MASS,"kg", "the kilogram");
	
	// SI unit of charge.
	public static SIQuantity COULOMB = new SIQuantity(1.0e0,Dimension.CHARGE,"C", "the coulomb");
	
	// Unit of temperature.
	public static SIQuantity KELVIN = new SIQuantity(1.0e0,Dimension.TEMPERATURE,"K", "the kelvin");
	
	public static final UnitSystem SIUnits = new UnitSystem(METRE,KILOGRAM,SECOND,COULOMB,KELVIN);
	
	// Some derived units.
	/**
	 * The litre, a common unit of volume.
	 */
	public static SIQuantity LITRE = new SIQuantity(1.0E-3,Dimension.VOLUME,"L","the litre");
	/**
	 * The millilitre, a common unit of volume.
	 */
	public static SIQuantity MILLILITRE = new SIQuantity(1.0E-6,Dimension.VOLUME,"mL","the millilitre");
	/**
	 * The Newton, a derived unit of force.
	 */
	public static SIQuantity NEWTON = new SIQuantity(1.0e0,Dimension.FORCE,"N", "the Newton");
	/**
	 * The Joule, a derived unit of energy.
	 */
	public static SIQuantity JOULE = new SIQuantity(1.0e0,Dimension.ENERGY,"J", "the joule");
	/**
	 * The Watt, a derived unit of power, the rate of change of energy.
	 */
	public static SIQuantity WATT = new SIQuantity(1.0e0,Dimension.POWER,"W", "the watt");
	/**
	 * The Pascal, a derived unit of pressure, force per unit area.
	 */
	public static SIQuantity PASCAL = new SIQuantity(1.0e0,Dimension.PRESSURE,"Pa", "the pascal");
	/**
	 * The dyne, the cgs unit of force.
	 */
	public static SIQuantity DYNE = new SIQuantity(NEWTON.times(1.0e-5),"dyn", "the dyne");
	/**
	 * The erg, the cgs unit of energy.
	 */
	public static SIQuantity ERG = new SIQuantity(JOULE.times(1.0e-7),"erg", "the erg");
	/**
	 * The Barye, the cgs unit of pressure.
	 */
	public static SIQuantity BARYE = new SIQuantity(PASCAL.times(0.1),"Ba", "the barye");
	/**
	 * The ampere, in fact the fundamental unit of current, from which charge is derived, treated here as a derived quantity:
	 *  rate of change of charge.
	 */
	public static SIQuantity AMPERE = new SIQuantity(1.0e0,Dimension.CURRENT,"A", "the ampere");
	/**
	 * The henry, the derived unit of induction, which represents the ability of a current loop to form voltage from changes in current.
	 */
	public static SIQuantity HENRY = new SIQuantity(1.0e0,Dimension.INDUCTANCE,"H", "the henry");
	/**
	 * The volt, the derived unit of electric potential.
	 */
	public static SIQuantity VOLT =  new SIQuantity(1.0e0,Dimension.VOLTAGE,"V", "the volt");
	/**
	 * The ohm, the derived unit of resistance, represents the voltage lost per unit current.
	 */
	public static SIQuantity OHM = new SIQuantity(1.0e0,Dimension.RESISTANCE,"Omega", "the ohm");
	/**
	 * The Tesla, the derived unit of magnetic field strength.
	 */
	public static SIQuantity TESLA = new SIQuantity(1.0e0,Dimension.MAGNETICFIELD,"T", "the tesla");
	/**
	 * The Gauss, one ten thousandth of a Tesla, because a Tesla is just too big.
	 */
	public static SIQuantity GAUSS = new SIQuantity(TESLA.times(1.0e-4),"G", "the gauss");
	/**
	 * The Weber, an SI derived unit for magnetic flux.
	 */
	public static SIQuantity WEBER = new SIQuantity(1.0e0,Dimension.MAGNETICFIELD.times(Dimension.AREA),"Wb", "the weber");
	/**
	 * The Farad, an SI derived unit for capacitance. Named in honor of Faraday.
	 */
	public static SIQuantity FARAD = new SIQuantity(1.0e0,Dimension.CAPACITANCE,"F", "the farad");
	
	
	
	// Some fundamental physical constants.
	

	/**
	 * Avogadro's number.
	 */
	public static SIQuantity AVOGADROSNUMBER = new SIQuantity(6.0221417930E23,Dimension.DIMENSIONLESS,"N_A","Avogadro's number");
	/**
	 * The speed of light in vacuum.
	 */
	public static SIQuantity c = new SIQuantity(METRE.dividedBy(SECOND).times(299792458e0),"c", "the speed of light");
	/** 
	 * Planck's constant, the linear coefficient relating a photon's frequency to its energy.
	 */
	public static SIQuantity h = new SIQuantity(JOULE.times(SECOND).times(6.62606896e-34),"h", "Planck's constant");
	/**
	 * Planck's constant divided by 2Pi, hbar.
	 */
	public static SIQuantity hbar = new SIQuantity(JOULE.times(SECOND).times(1.054571628e-34),"hbar", "Planck's constant over 2 pi");
	/**
	 * The mass of the electron.
	 */
	public static SIQuantity melectron = new SIQuantity(KILOGRAM.times(9.10938215e-31),"m_e", "the mass of the electron");
	/**
	 * The mass of the proton.
	 */
	public static SIQuantity mproton = new SIQuantity(KILOGRAM.times(1.67262171e-27),"m_p", "the mass of the proton");
	/**
	 * The mass of the neutron.
	 */
	public static SIQuantity mneutron = new SIQuantity(KILOGRAM.times(1.67492729e-27),"m_n", "the mass of the neutron");
	/**
	 * The atomic mass unit. Roughly 1/12 of a mole of pure Carbon-12 atoms. Also known as the dalton.
	 */
	public static SIQuantity amu = new SIQuantity(KILOGRAM.times(1.660538782e-27),"u", "the atomic mass unit");
	/**
	 * The atomic mass unit, the dalton.
	 */
	public static SIQuantity dalton = new SIQuantity(amu,"Da","the dalton (atomic mass unit with symbol Da)");
	/**
	 * The mass of the sun.
	 */
	public static SIQuantity msolar = new SIQuantity(KILOGRAM.times(1.98892e30),"m_sun", "the solar mass");
	/**
	 * The Newtonian gravitational constant G. Also makes an appearance in General Relativity before naturalization.
	 */
	public static SIQuantity G = new SIQuantity(JOULE.dividedBy(KILOGRAM).dividedBy(KILOGRAM).times(METRE).times(6.67428e-11),"G", "Newton's constant of gravitation");
	/**
	 * The astronomical unit, roughly the yearly average distance of the Earth to the sun.
	 */
	public static SIQuantity AU = new SIQuantity(METRE.times(1.49598e11),"AU", "the astronomical unit");
	/**
	 * The parsec, the distance to a star which subtends 2 seconds of arclength against the more distant background stars as measured when the earth is at
	 * antipodes of its orbit.
	 */
	public static SIQuantity pc = new SIQuantity(METRE.times(30.857e15),"pc", "the parsec");
	/**
	 * The minute, 60 seconds.
	 */
	public static SIQuantity min = new SIQuantity(SECOND.times(60.0e0),"min", "the minute");
	/**
	 * The hour, 60 minutes.
	 */
	public static SIQuantity hour = new SIQuantity(min.times(60.0e0),"hr", "the hour");
	/**
	 * The day, 24 hours.
	 */
	public static SIQuantity day = new SIQuantity(hour.times(24.0e0),"day", "the day");
	/**
	 * The year. Time it takes the planet earth to go around the sun once, rounded to the nearest integer number of days.
	 */
	public static SIQuantity year = new SIQuantity(day.times(365.0e0),"yr", "the year");
	/**
	 * The distance that light travels in one year of time.
	 */
	public static SIQuantity ly = new SIQuantity(c.times(year),"ly", "the lightyear");
	/**
	 * The elementary charge. Charge of the electron, proton and relatives.
	 */
	public static SIQuantity e = new SIQuantity(1.602176487e-19,Dimension.CHARGE,"e", "the fundamental electrical charge");
	/**
	 * The electronvolt, the energy an electron acquires accelerating through an electric potential of 1 volt.
	 */
	public static SIQuantity eV = new SIQuantity(e.times(VOLT),"eV", "the electron volt");
	/**
	 * The mega-electron volt. The energy an electron acquires accelerating through an electric potential of 1 million volts.
	 */
	public static SIQuantity MeV = new SIQuantity(eV.times(1.0e6),"MeV", "the megaelectron volt");
	/**
	 * Boltzmann's constant.
	 */
	public static SIQuantity k = new SIQuantity(1.3806504e-23,Dimension.ENERGY.dividedBy(Dimension.TEMPERATURE),"k", "Boltzmann's constant");
	/**
	 * The magnetic permeability of free space.
	 */
	public static SIQuantity munought = new SIQuantity(HENRY.dividedBy(METRE).times(4.0E-7*Math.PI),"mu_0", "the permeability of free space");
	/**
	 * The permittivity of free space.
	 */
	public static SIQuantity epsilon_0 = new SIQuantity(UNITLESS.dividedBy(c.times(c).times(munought)),"epsilon_0", "the permittivity of free space");
	/**
	 * Coulomb's constant.
	 */
	public static SIQuantity k_c = new SIQuantity(UNITLESS.dividedBy( 
			epsilon_0.times( new SIQuantity(4.0e0*Math.PI,Dimension.DIMENSIONLESS) ) 
																	),"k_c", "Coulomb's constant");
	/**
	 * Avogadro's number.
	 */
	public static SIQuantity NA = new SIQuantity(6.02214179E23,Dimension.DIMENSIONLESS,"N_A","Avogadro's number");
	/**
	 * Minimum charge of an isolated magnetic monopole. 
	 */
	public static SIQuantity qm = new SIQuantity(h.dividedBy(e),"q_m","the minimum charge of a magnetic monopole");
	
	
	// Some explicit SI units.

	
	/**
	 * The statcoulomb, the unit of charge in the cgs system. Using the cgs system and setting k_c to 1 also
	 * results in the statcoulomb beinga fundamental unit of charge.
	 */
	public static SIQuantity statC = new SIQuantity(0.1e0/c.getValue(),Dimension.CHARGE,"statC", "the stat coulomb");
	/**
	 * The statvolt, derived unit of voltage in ESU.
	 */
	public static SIQuantity STATVOLT = new SIQuantity(ERG.dividedBy(statC),"statV", "the stat volt");
	/**
	 * The oersted, the derived unit of magnetic field strength in ESU.
	 */
	public static SIQuantity OERSTED = new SIQuantity(AMPERE.dividedBy(METRE).times(1000.0e0/(4.0E0*Math.PI)),"Oe", "the oersted");
	/**
	 * The maxwell, the derived unit of magnetic flux in ESU.
	 */
	public static SIQuantity MAXWELL = new SIQuantity(WEBER.times(10e-8),"Mw", "the maxwell");
	/**
	 * The centimetre.
	 */
	public static SIQuantity CENTIMETRE = new SIQuantity(0.01,Dimension.LENGTH,"cm", "the centimetre");
	/**
	 * The femtometre.
	 */
	public static SIQuantity FEMTOMETRE = new SIQuantity(1e-15,Dimension.LENGTH,"fm", "the femtometre");
	/**
	 * The gram.
	 */
	public static SIQuantity GRAM = new SIQuantity(0.001,Dimension.MASS,"g", "the gram");
	/**
	 * The milligram.
	 */
	public static SIQuantity MILLIGRAM = new SIQuantity(1e-6,Dimension.MASS,"mg","the milligram");

	/**
	 * A handy array of all the SIQuantity's defined here for the purpose of easily adding them to combo boxes.
	 */
	public static SIQuantity [] ALLSICONSTANTS = {
		AMPERE, amu, AU,
		BARYE,
		c, CENTIMETRE, COULOMB,
		dalton, day, DYNE,
		e, epsilon_0, ERG, eV,
		FARAD,FEMTOMETRE,
		G, GAUSS, GRAM,
		h, hbar, HENRY, hour,
		JOULE,
		k, k_c, KELVIN, KILOGRAM,
		LITRE,ly,
		MAXWELL, melectron, METRE, MeV, min, mneutron, mproton, msolar, munought,
		NA, NEWTON,
		OERSTED, OHM,
		PASCAL, pc,
		qm,
		SECOND, statC, STATVOLT,
		TESLA,
		UNITLESS,
		VOLT,
		WATT, WEBER,		
		year
		};
}

package atonita.unitconversion.dimensionalanalysis;

public final class CommonUnits {
	public static final SIQuantity [] SIDerivedUnits = {SIConstants.NEWTON, SIConstants.JOULE, SIConstants.PASCAL, SIConstants.WATT,
		SIConstants.AMPERE, SIConstants.OHM, SIConstants.HENRY, SIConstants.TESLA, SIConstants.FARAD, SIConstants.VOLT};
	public static final UnitSystem SI = new UnitSystem(
			SIConstants.METRE,
			SIConstants.KILOGRAM,
			SIConstants.SECOND,
			SIConstants.COULOMB,
			SIConstants.KELVIN,
			SIDerivedUnits,
			"SI: System internationale"
		);
	public static final UnitSystem MKS = new UnitSystem(
			SIConstants.METRE,
			SIConstants.KILOGRAM,
			SIConstants.SECOND,
			SIConstants.COULOMB,
			SIConstants.KELVIN,
			"MKS: metre-kilogram-second system"
		);
	public static final SIQuantity [] CGSDerivedUnits = {SIConstants.DYNE, SIConstants.ERG, SIConstants.BARYE};
	public static final UnitSystem CGS = new UnitSystem(
			SIConstants.CENTIMETRE,
			SIConstants.GRAM,
			SIConstants.SECOND,
			SIConstants.COULOMB,
			SIConstants.KELVIN,
			CGSDerivedUnits,
			"CGS: Centimetre-gram-second"
		);
	/**
	 * The CGS system without using derived units.
	 */
	public static final UnitSystem CGSNODERIVED = new UnitSystem(
			SIConstants.CENTIMETRE,
			SIConstants.GRAM,
			SIConstants.SECOND,
			SIConstants.COULOMB,
			SIConstants.KELVIN,
			"CGS: Centimetre-gram-second (no derived units)"
		);
	/**
	 * The electro-static derived units. Essentially the same as cgs derived units with some E/M stuff thrown in.
	 */
	public static final SIQuantity [] ESUDerivedUnits = {SIConstants.DYNE, SIConstants.ERG, SIConstants.BARYE,
		SIConstants.STATVOLT,SIConstants.MAXWELL,SIConstants.GAUSS};
	/**
	 * The electro-static units with derived units.
	 */
	public static final UnitSystem ESU = new UnitSystem(
			SIConstants.CENTIMETRE,
			SIConstants.GRAM,
			SIConstants.SECOND,
			SIConstants.statC,
			SIConstants.KELVIN,
			ESUDerivedUnits,
			"Electrostatic units"
		);
	/**
	 * We need to suppress Coulomb's constant in order to have electro static units. If we instead
	 * use the statCoulomb then we will see it everywhere and it is considered a derived unit.
	 */
	private static final boolean [] esuSuppressors = {false,false,false,true,false};
	/**
	 * The electro-static units without derived units.
	 */
	public static final UnitSystem ESUNODERIVED = new UnitSystem(
			SIConstants.CENTIMETRE,
			SIConstants.GRAM,
			SIConstants.SECOND,
			SIConstants.k_c,
			SIConstants.KELVIN,
			esuSuppressors,
			"ESU: Electrostatic units (no derived units)"
		);
	/**
	 * Typically G and c are suppressed and nobody mentions temperatures and electricity.
	 */
	private static final boolean [] geometricSuppressors = {true,true,false,false,false};
	/**
	 * Geometric units, where everything is measured in metres.
	 */
	public static final UnitSystem GEOMETRIC = new UnitSystem(
			SIConstants.G,
			SIConstants.c,
			SIConstants.METRE,
			SIConstants.COULOMB,
			SIConstants.KELVIN,
			geometricSuppressors,
			"Geometric units"
		);
	/**
	 * When you really want arcane units, add the solar mass. Thus you have geometric and astronomical units: geometricastro.
	 */
	public static final UnitSystem GEOMETRICASTRO = new UnitSystem(
			SIConstants.G,
			SIConstants.c,
			SIConstants.msolar,
			SIConstants.COULOMB,
			SIConstants.KELVIN,
			geometricSuppressors,
			"Geometric astrophysical units"
		);
	/**
	 * When dealing with ideal magneto hydrodynamics, we will also suppress mu_nought. I believe this is a geometric generalization
	 * of Heaviside units, but don't quote me.
	 */
	public static final UnitSystem GEOMETRICMHD = new UnitSystem(
			SIConstants.G,
			SIConstants.c,
			SIConstants.msolar,
			SIConstants.munought,
			SIConstants.KELVIN,
			geometricSuppressors,
			"Geometric magnetohydrodynamical units"
		);
	/**
	 * In Planck units, everything is non-dimensional.
	 */
	public static final boolean [] planckSuppressors = {true, true, true, true, true};
	/**
	 * Planck units.
	 */
	public static final UnitSystem PLANCK = new UnitSystem(
			SIConstants.G,
			SIConstants.c,
			SIConstants.hbar,
			SIConstants.k,
			SIConstants.k_c,
			planckSuppressors,
			"Planck units");
	/**
	 * Planck units without suppressors.
	 */
	public static final UnitSystem PLANCKNOSUPPRESSORS = new UnitSystem(
			SIConstants.G,
			SIConstants.c,
			SIConstants.hbar,
			SIConstants.k,
			SIConstants.k_c,
			"Planck units - No suppressors");
	/**
	 * Typically we don't want to see c because we think of mass as energy, and we don't want to see k_b
	 * for the analagous reason.
	 */
	private static final boolean [] meVSuppressors = {false,false,true,false,true};
	/**
	 * Nuclear physics units. Measure mass/energies in MeV and distances in fm.
	 */
	public static final UnitSystem MEV = new UnitSystem(
			SIConstants.FEMTOMETRE,
			SIConstants.MeV,
			SIConstants.c,
			SIConstants.COULOMB,
			SIConstants.k,
			meVSuppressors,
			"Nuclear physics units."
		);
	public static final UnitSystem MEVNOSUPPRESSORS = new UnitSystem(
			SIConstants.FEMTOMETRE,
			SIConstants.MeV,
			SIConstants.c,
			SIConstants.COULOMB,
			SIConstants.k,
			"Nuclear physics units."
		);
	
	/**
	 * A handy array of all the common unit systems defined here for the purposes of adding them to combo boxes.
	 */
	public static final UnitSystem [] COMBOUNITSYSTEMS = {
		SI, MKS,
		CGS, CGSNODERIVED,
		ESU, ESUNODERIVED,
		GEOMETRIC, GEOMETRICASTRO, GEOMETRICMHD,
		MEV,
		PLANCK, PLANCKNOSUPPRESSORS
	};
}

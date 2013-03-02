package atonita.unitconversion.dimensionalanalysis;

/**
 * Contains both Imperial quantities and US Customary ones.
 * @author Aaryn Tonita
 *
 */
public final class ImperialUnits {
	// First, the few--four--agreed upon units of length.
	
	/**
	 * The international inch.
	 */
	public static SIQuantity INCH = new SIQuantity(SIConstants.CENTIMETRE.times(2.54),"in","the inch");
	/**
	 * The international foot.
	 */
	public static SIQuantity FOOT = new SIQuantity(INCH.times(12),"ft","the foot");
	/**
	 * The international yard.
	 */
	public static SIQuantity YARD = new SIQuantity(FOOT.times(3),"yd","the yard");
	/**
	 * The international mile.
	 */
	public static SIQuantity MILE = new SIQuantity(YARD.times(1760),"mi","the mile");
	
	// Now, due to my Canadian heritage, I give preference to the Imperial units.
	
	/**
	 * The imperial fathom, defined--conventionally--as per the admiralty, as opposed to inversely through the nautical mile.
	 */
	public static SIQuantity FATHOM = new SIQuantity(YARD.times(2),"ftm","the fathom");
	/**
	 * The imperial furlong.
	 */
	public static SIQuantity FURLONG = new SIQuantity(YARD.times(220),"fur","the furlong");
	/**
	 * The imperial league.
	 */
	public static SIQuantity LEAGUE = new SIQuantity(MILE.times(3),"lea","the league");
	/**
	 * The cable, one tenth of an imperial nautical mile.
	 */
	public static SIQuantity CABLE = new SIQuantity(FOOT.times(608),"cb","the cable");
	/**
	 * The imperial nautical mile.
	 */
	public static SIQuantity NAUTICALMILE = new SIQuantity(CABLE.times(10),"NM","the nautical mile");
	/**
	 * The imperial surveying measure: link.
	 */
	public static SIQuantity LINK = new SIQuantity(FOOT.times(0.66),"li","the link");
	/**
	 * The imperial surveying measure: pole.
	 */
	public static SIQuantity ROD = new SIQuantity(FOOT.times(16.5),"rd","the rod"); // Base it on the foot to minimize roundoff.
	/**
	 * The imperial surveying measure: chain.
	 */
	public static SIQuantity CHAIN = new SIQuantity(FOOT.times(66),"CH","the chain"); // Base it on the foot to minimize roundoff.
	
	// Now onto my good neighbour, the USA. Is onto a word?
	
	/**
	 * The US customary survey foot.
	 */
	public static SIQuantity SURVEYFOOT = new SIQuantity(SIConstants.METRE.times(1200./3937.),"ft","the US survey foot");
	/**
	 * The US customary survey link.
	 */
	public static SIQuantity USLINK = new SIQuantity(SURVEYFOOT.times(0.66),"li","the (US customary) link");
	/**
	 * The US customary surveying measure: rod.
	 */
	public static SIQuantity USROD = new SIQuantity(SURVEYFOOT.times(16.5),"rd","the (US customary) rod");
	/**
	 * The US customary surveying measure: chain.
	 */
	public static SIQuantity USCHAIN = new SIQuantity(SURVEYFOOT.times(66),"CH","the (US customary) chain"); // Base it on the foot to minimize roundoff.
	/**
	 * The US customary surveying measure: furlong.
	 */
	public static SIQuantity USFURLONG = new SIQuantity(SURVEYFOOT.times(660),"fur","the (US customary) furlong");
	/**
	 * The US Customary surveying mile.
	 */
	public static SIQuantity SURVEYMILE = new SIQuantity(SURVEYFOOT.times(5280),"mi","the surveying mile");
	/**
	 * The US Customary surveying mile.
	 */
	public static SIQuantity USLEAGUE = new SIQuantity(SURVEYFOOT.times(15840),"lea","the (US customary) league");
	/**
	 * The US customary cable.
	 */
	public static SIQuantity USCABLE = new SIQuantity(FATHOM.times(120),"cb","the (US customary) cable");
	/**
	 * The international nautical mile.
	 */
	public static SIQuantity INTERNATIONALNAUTICALMILE = new SIQuantity(SIConstants.METRE.times(1852),"NM","the international nautical mile");
	
	// Some areas, first the UK
	
	/**
	 * The perch, a square rod.
	 */
	public static SIQuantity PERCH = new SIQuantity(ROD.times(ROD),"perch","the perch: one square rod");
	/**
	 * The rood, a furlong by one rod, also one quarter of an acre.
	 */
	public static SIQuantity ROOD = new SIQuantity(ROD.times(FURLONG),"rood","the rood: an area one furlong by one rod");
	/**
	 * The acre, everyone's favorite property.
	 */
	public static SIQuantity ACRE = new SIQuantity(FURLONG.times(CHAIN),"acre","the acre");
	
	// Now perhaps more familiar,
	/**
	 * The US customary acre.
	 */
	public static SIQuantity USACRE = new SIQuantity(USCHAIN.times(USCHAIN),"acre","the (US customary) acre");
	/**
	 * The section: 640 acres. A good place to live.
	 */
	public static SIQuantity SECTION = new SIQuantity(USACRE.times(640),"section","the (US customary) section");
	/**
	 * The township. Of which I have only "experienced" in Alberta.
	 */
	public static SIQuantity TOWNSHIP = new SIQuantity(SECTION.times(36),"twp","the (US customary) township");
	
	// Volumes----which is what one needs for drinking beer in England.
	
	/**
	 * The basis of defining imperial volumes: the gallon.
	 */
	public static SIQuantity GALLON = new SIQuantity(SIConstants.LITRE.times(4.54609),"gal","the gallon");
	/**
	 * The quart: a quarter of a gallon.
	 */
	public static SIQuantity QUART = new SIQuantity(GALLON.times(0.25),"qt","the quart");
	/**
	 * The pint: half a quart and a good mug o' ale.
	 */
	public static SIQuantity PINT = new SIQuantity(QUART.times(0.5),"pt","the pint");
	/**
	 * The cup: half a pint and a common cooking measure.
	 */
	public static SIQuantity CUP = new SIQuantity(PINT.times(0.5),"cp","the cup");
	/**
	 * The fluid ounce.
	 */
	public static SIQuantity FLUIDOUNCE = new SIQuantity(SIConstants.MILLILITRE.times(28.4130625),"fl oz","the fluid ounce");
	
	// Some US volumes
	
	/**
	 * The US gallon.
	 */
	public static SIQuantity USGALLON = new SIQuantity(INCH.times(INCH).times(231),"gal","the US (liquid) gallon");
	/**
	 * The US quart, a quarter of a gallon.
	 */
	public static SIQuantity USQUART = new SIQuantity(USGALLON.times(0.25),"qt","the US (liquid) quart");
	/**
	 * The US pint, half a quart.
	 */
	public static SIQuantity USPINT = new SIQuantity(USQUART.times(0.5),"pt","the US (liquid) pint");
	/**
	 * The US cup, half a pint. A common cooking measure.
	 */
	public static SIQuantity USCUP = new SIQuantity(USPINT.times(0.5),"cp","the US cup");
	/**
	 * The fluid ounce, one eighth of a cup.
	 */
	public static SIQuantity USFLUIDOUNCE = new SIQuantity(USCUP.times(0.125),"fl oz","the US fluid ounce");
	/**
	 * The US tablespoon, half a fluid ounce.
	 */
	public static SIQuantity USTABLESPOON = new SIQuantity(USFLUIDOUNCE.times(0.5),"Tbsp","the US tablespoon");
	/**
	 * The US teaspoon, a third of a tablespoon.
	 */
	public static SIQuantity USTEASPOON = new SIQuantity(USTABLESPOON.times(1./3.),"tsp","the US teaspoon");/**
	 * The US dry gallon.
	 */
	public static SIQuantity USDRYGALLON = new SIQuantity(INCH.times(INCH).times(268.8025),"gal","the US (dry) gallon");
	/**
	 * The US quart, a quarter of a gallon.
	 */
	public static SIQuantity USDRYQUART = new SIQuantity(USDRYGALLON.times(0.25),"qt","the US (dry) quart");
	/**
	 * The US pint, half a quart.
	 */
	public static SIQuantity USDRYPINT = new SIQuantity(USDRYQUART.times(0.5),"pt","the US (dry) pint");
	/**
	 * The US peck. Two dry gallons.
	 */
	public static SIQuantity USPECK = new SIQuantity(USDRYGALLON.times(2),"pk","the US peck");
	/**
	 * The US bushel, four dry pecks.
	 */
	public static SIQuantity USBUSHEL = new SIQuantity(USPECK.times(4),"bu","the US bushel");
	
	// Masses: and happily these are agreed upon at least.
	
	/**
	 * The pound, a unit of mass.
	 */
	public static SIQuantity POUND = new SIQuantity(SIConstants.GRAM.times(453.59237),"lb","the pound");
	/**
	 * The grain, which once formed a basis for the other weights.
	 */
	public static SIQuantity GRAIN = new SIQuantity(SIConstants.MILLIGRAM.times(64.79891),"gr","the grain");
	/**
	 * The ounce, a smallish unit of measuring mass. One sixteenth of a pound.
	 */
	public static SIQuantity OUNCE = new SIQuantity(POUND.times(0.0625),"oz","the ounce");
	
	// Arggh, the ton is different.
	
	public static SIQuantity TON = new SIQuantity(POUND.times(2240),"t","the ton");
	
	public static SIQuantity USTON = new SIQuantity(POUND.times(2000),"t","the US (short) ton");
	
	public static SIQuantity [] IMPERIALCONSTANTS = {
		ACRE,
		CABLE,CHAIN,CUP,
		FATHOM,FLUIDOUNCE,FOOT,FURLONG,
		GALLON,GRAIN,
		INCH,
		LEAGUE,LINK,
		NAUTICALMILE,
		MILE,
		OUNCE,
		PINT,POUND,
		QUART,
		ROD,
		TON,
		YARD
	};
	public static SIQuantity [] USCUSTOMARYCONSTANTS = {
		FATHOM,FOOT,
		GRAIN,
		INCH,INTERNATIONALNAUTICALMILE,
		MILE,
		OUNCE,
		POUND,
		SECTION,SURVEYFOOT,SURVEYMILE,
		TOWNSHIP,
			USACRE,
			USBUSHEL,
			USCABLE,USCHAIN,
			USDRYGALLON,USDRYPINT,USDRYQUART,
			USFLUIDOUNCE,USFURLONG,
			USGALLON,
			USLEAGUE,USLINK,
			USPECK,USPINT,
			USQUART,
			USROD,
			USTABLESPOON,USTEASPOON,USTON,
		YARD
	};
}

package fr.ferfoui.america2goat;

import fr.ferfoui.america2goat.data.settings.StorageKeys;
import fr.ferfoui.america2goat.unit.DistanceUnit;
import fr.ferfoui.america2goat.unit.MassUnit;
import fr.ferfoui.america2goat.unit.UnitStorage;
import fr.ferfoui.america2goat.unit.VolumeUnit;

/**
 * Constants used throughout the application.
 */
public class Constants {

    // Datastore name
    public static final String DATASTORE_NAME = "settings";


    // Unit type names
    public static final String DISTANCE_UNIT_TYPE_NAME = "distance_unit";
    public static final String MASS_UNIT_TYPE_NAME = "mass_unit";
    public static final String VOLUME_UNIT_TYPE_NAME = "volume_unit";


    // Conversion factors
    public static final double KILOMETER_TO_METER = 1000d;
    public static final double CM_TO_METER = 0.01d;
    public static final double MILE_TO_METER = 1609.344d;
    public static final double YARD_TO_METER = 0.9144d;
    public static final double INCH_TO_METER = 0.0254d;
    public static final double FEET_TO_METER = 12 * INCH_TO_METER;

    public static final double KILOGRAM_TO_GRAM = 1000d;
    public static final double POUND_TO_GRAM = 453.59237d;
    public static final double OUNCE_TO_GRAM = POUND_TO_GRAM / 16d;

    public static final double CUBIC_METER_TO_LITER = 1000d;
    public static final double MILLILITER_TO_LITER = 0.001d;
    public static final double GALLON_TO_LITER = 3.78541d;
    public static final double QUART_TO_LITER = GALLON_TO_LITER / 4d;
    public static final double PINT_TO_LITER = QUART_TO_LITER / 2d;
    public static final double CUP_TO_LITER = PINT_TO_LITER / 2d;
    public static final double FLUID_OUNCE_TO_LITER = CUP_TO_LITER / 8d;


    // Default values
    public static final String DEFAULT_UNIT_TYPE = DISTANCE_UNIT_TYPE_NAME;

    public static final DistanceUnit DEFAULT_INPUT_DISTANCE_UNIT = DistanceUnit.INCH;
    public static final DistanceUnit DEFAULT_OUTPUT_DISTANCE_UNIT = DistanceUnit.CENTIMETER;

    public static final MassUnit DEFAULT_INPUT_MASS_UNIT = MassUnit.POUND;
    public static final MassUnit DEFAULT_OUTPUT_MASS_UNIT = MassUnit.KILOGRAM;

    public static final VolumeUnit DEFAULT_INPUT_VOLUME_UNIT = VolumeUnit.GALLON;
    public static final VolumeUnit DEFAULT_OUTPUT_VOLUME_UNIT = VolumeUnit.LITER;

    public static final UnitStorage DISTANCE_UNIT_STORAGE = new UnitStorage(
            StorageKeys.INPUT_DISTANCE_UNIT_STORAGE_KEY,
            StorageKeys.OUTPUT_DISTANCE_UNIT_STORAGE_KEY,
            DEFAULT_INPUT_DISTANCE_UNIT, DEFAULT_OUTPUT_DISTANCE_UNIT
    );

    public static final UnitStorage MASS_UNIT_STORAGE = new UnitStorage(
            StorageKeys.INPUT_MASS_UNIT_STORAGE_KEY,
            StorageKeys.OUTPUT_MASS_UNIT_STORAGE_KEY,
            DEFAULT_INPUT_MASS_UNIT, DEFAULT_OUTPUT_MASS_UNIT
    );

    public static final UnitStorage VOLUME_UNIT_STORAGE = new UnitStorage(
            StorageKeys.INPUT_VOLUME_UNIT_STORAGE_KEY,
            StorageKeys.OUTPUT_VOLUME_UNIT_STORAGE_KEY,
            DEFAULT_INPUT_VOLUME_UNIT, DEFAULT_OUTPUT_VOLUME_UNIT
    );


    public static final int DEFAULT_ROUND_PREFERENCE = 4;
}

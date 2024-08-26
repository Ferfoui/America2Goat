package fr.ferfoui.america2goat;

import fr.ferfoui.america2goat.unit.DistanceUnit;

public class Constants {

    public static final double INCH_TO_METER = 0.0254d;
    public static final double FEET_TO_METER = 12 * INCH_TO_METER;
    public static final double CM_TO_METER = 0.01d;

    public static final DistanceUnit DEFAULT_INPUT_DISTANCE_UNIT = DistanceUnit.CENTIMETER;
    public static final DistanceUnit DEFAULT_OUTPUT_DISTANCE_UNIT = DistanceUnit.INCH;

    public static final int DEFAULT_ROUND_PREFERENCE = 4;

}

package fr.ferfoui.america2goat.unit;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.R;

/**
 * Enum representing the distance units.
 */
public enum DistanceUnit implements Unit {
    METER(1d, R.string.meter, R.string.meter_abbreviation),
    CENTIMETER(Constants.CM_TO_METER, R.string.centimeter, R.string.centimeter_abbreviation),
    INCH(Constants.INCH_TO_METER, R.string.inch, R.string.inch_abbreviation),
    FOOT(Constants.FEET_TO_METER, R.string.foot, R.string.foot_abbreviation);

    private final double factor;
    private final int resourceNameId;
    private final int resourceAbbreviationId;

    DistanceUnit(double factor, int resourceNameId, int resourceAbbreviationId) {
        this.factor = factor;
        this.resourceNameId = resourceNameId;
        this.resourceAbbreviationId = resourceAbbreviationId;
    }

    @Override
    public double getFactor() {
        return factor;
    }

    @Override
    public int getResourceNameId() {
        return resourceNameId;
    }

    @Override
    public int getResourceAbbreviationId() {
        return resourceAbbreviationId;
    }

    @Override
    public String getType() {
        return Constants.DISTANCE_UNIT_TYPE_NAME;
    }
}

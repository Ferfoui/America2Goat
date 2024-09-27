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

    private final double weight;
    private final int resourceNameId;
    private final int resourceAbbreviationId;

    DistanceUnit(double weight, int resourceNameId, int resourceAbbreviationId) {
        this.weight = weight;
        this.resourceNameId = resourceNameId;
        this.resourceAbbreviationId = resourceAbbreviationId;
    }

    @Override
    public double getWeight() {
        return weight;
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

package fr.ferfoui.america2goat.data.conversion;

import fr.ferfoui.america2goat.R;
import fr.ferfoui.america2goat.Constants;

public enum Unit {
    METER(1d, R.string.meter, R.string.meter_abbreviation),
    CENTIMETER(Constants.CM_TO_METER, R.string.centimeter, R.string.centimeter_abbreviation),
    INCH(Constants.INCH_TO_METER, R.string.inch, R.string.inch_abbreviation),
    FOOT(Constants.FEET_TO_METER, R.string.foot, R.string.foot_abbreviation);


    private final double weight;
    private final int resourceNameId;
    private final int resourceAbbreviationId;

    Unit(double weight, int resourceNameId, int resourceAbbreviationId) {
        this.weight = weight;
        this.resourceNameId = resourceNameId;
        this.resourceAbbreviationId = resourceAbbreviationId;
    }

    public double getWeight() {
        return weight;
    }

    public int getResourceNameId() {
        return resourceNameId;
    }

    public int getResourceAbbreviationId() {
        return resourceAbbreviationId;
    }
}

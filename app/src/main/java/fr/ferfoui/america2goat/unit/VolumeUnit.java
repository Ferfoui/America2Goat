package fr.ferfoui.america2goat.unit;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.R;

/**
 * Enum representing the volume units.
 */
public enum VolumeUnit implements Unit {
    CUBIC_METER(Constants.CUBIC_METER_TO_LITER, R.string.cubic_meter, R.string.cubic_meter_abbreviation),
    LITER(1d, R.string.liter, R.string.liter_abbreviation),
    MILLILITER(Constants.MILLILITER_TO_LITER, R.string.milliliter, R.string.milliliter_abbreviation),
    GALLON(Constants.GALLON_TO_LITER, R.string.gallon, R.string.gallon_abbreviation),
    QUART(Constants.QUART_TO_LITER, R.string.quart, R.string.quart_abbreviation),
    PINT(Constants.PINT_TO_LITER, R.string.pint, R.string.pint_abbreviation),
    CUP(Constants.CUP_TO_LITER, R.string.cup, R.string.cup_abbreviation),
    FLUID_OUNCE(Constants.FLUID_OUNCE_TO_LITER, R.string.fluid_ounce, R.string.fluid_ounce_abbreviation);

    private final double factor;
    private final int resourceNameId;
    private final int resourceAbbreviationId;

    VolumeUnit(double factor, int resourceNameId, int resourceAbbreviationId) {
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
        return Constants.VOLUME_UNIT_TYPE_NAME;
    }
}

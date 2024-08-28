package fr.ferfoui.america2goat.unit;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.R;

public enum MassUnit implements Unit {
    GRAM(1, R.string.gram, R.string.gram_abbreviation),
    KILOGRAM(Constants.KILOGRAM_TO_GRAM, R.string.kilogram, R.string.kilogram_abbreviation),
    POUND(Constants.POUND_TO_GRAM, R.string.pound, R.string.pound_abbreviation),
    OUNCE(Constants.OUNCE_TO_GRAM, R.string.ounce, R.string.ounce_abbreviation);

    private final double weight;
    private final int resourceNameId;
    private final int resourceAbbreviationId;

    MassUnit(double weight, int resourceNameId, int resourceAbbreviationId) {
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
}

package fr.ferfoui.america2goat.unit;

/**
 * Interface representing a unit with various properties.
 */
public interface Unit {

    /**
     * Gets the weight of the unit compared to the base unit.
     *
     * @return the weight of the unit
     */
    double getWeight();

    /**
     * Gets the resource id of the unit name.
     *
     * @return the resource id of the unit name
     */
    int getResourceNameId();

    /**
     * Gets the resource id of the unit abbreviation.
     *
     * @return the resource id of the unit abbreviation
     */
    int getResourceAbbreviationId();

    /**
     * Gets the ordinal of the unit.
     *
     * @return the ordinal of the unit
     */
    int ordinal();

    /**
     * Gets the type of the unit.
     *
     * @return the type of the unit
     */
    String getType();
}

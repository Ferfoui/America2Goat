package fr.ferfoui.america2goat.unit;

public interface Unit {

    /**
     * @return the weight of the unit compared to the base unit
     */
    double getWeight();

    /**
     * @return the resource id of the unit name
     */
    int getResourceNameId();

    /**
     * @return the resource id of the unit abbreviation
     */
    int getResourceAbbreviationId();

    /**
     * @return the ordinal of the unit
     */
    int ordinal();

    /**
     * @return the type of the unit
     */
    String getType();
}

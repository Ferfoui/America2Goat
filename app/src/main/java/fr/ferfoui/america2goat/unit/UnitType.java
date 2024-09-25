package fr.ferfoui.america2goat.unit;

/**
 * Represents a type of unit with associated properties.
 */
public class UnitType {

    private final Unit[] units;
    private final String unitTypeName;
    private final int resourceNameId;
    private final UnitStorage unitStorage;

    /**
     * Constructs a new UnitType.
     *
     * @param unitTypeName   the name of the unit type
     * @param resourceNameId the resource ID associated with the unit type
     * @param units          an array of units of this type
     * @param unitStorage    the storage associated with the units
     */
    public UnitType(String unitTypeName, int resourceNameId, Unit[] units, UnitStorage unitStorage) {
        this.unitTypeName = unitTypeName;
        this.resourceNameId = resourceNameId;
        this.units = units;
        this.unitStorage = unitStorage;
    }

    /**
     * Gets the resource ID associated with the unit type.
     *
     * @return the resource ID
     */
    public int getResourceNameId() {
        return resourceNameId;
    }

    /**
     * Gets the units of this type.
     *
     * @return an array of units
     */
    public Unit[] getUnits() {
        return units;
    }

    /**
     * Gets the name of the unit type.
     *
     * @return the unit type name
     */
    public String getName() {
        return unitTypeName;
    }

    /**
     * Gets the storage associated with the units.
     *
     * @return the unit storage
     */
    public UnitStorage getUnitStorage() {
        return unitStorage;
    }
}

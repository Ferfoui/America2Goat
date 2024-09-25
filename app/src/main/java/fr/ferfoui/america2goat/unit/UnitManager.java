package fr.ferfoui.america2goat.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.R;

/**
 * Manages different unit types and provides methods to access them.
 */
public class UnitManager {

    private static final HashMap<String, UnitType> unitTypes = new HashMap<>(2);

    static {
        putUnitType(Constants.DISTANCE_UNIT_TYPE_NAME, R.string.distance_unit_name, DistanceUnit.values(), Constants.DEFAULT_DISTANCE_UNIT_STORAGE);
        putUnitType(Constants.MASS_UNIT_TYPE_NAME, R.string.mass_unit_name, MassUnit.values(), Constants.DEFAULT_MASS_UNIT_STORAGE);
    }

    /**
     * Adds a new unit type to the manager.
     *
     * @param unitTypeName   the name of the unit type
     * @param resourceNameId the resource ID for the unit type name
     * @param units          the units associated with this unit type
     * @param unitStorage    the storage mechanism for this unit type
     */
    private static void putUnitType(String unitTypeName, int resourceNameId, Unit[] units, UnitStorage unitStorage) {
        unitTypes.put(unitTypeName, new UnitType(unitTypeName, resourceNameId, units, unitStorage));
    }

    /**
     * Retrieves a unit type by its name.
     *
     * @param unitTypeName the name of the unit type
     * @return the UnitType object
     * @throws IllegalArgumentException if the unit type is unknown
     */
    public static UnitType getUnitType(String unitTypeName) {
        UnitType unitType = unitTypes.get(unitTypeName);

        if (unitType == null) {
            throw new IllegalArgumentException("Unknown unit type: " + unitTypeName);
        }

        return unitType;
    }

    /**
     * Returns a list of all unit type names.
     *
     * @return a list of unit type names
     */
    public static List<String> getUnitTypeNames() {
        return new ArrayList<>(unitTypes.keySet());
    }

    /**
     * Returns a collection of all unit types.
     *
     * @return a collection of UnitType objects
     */
    public static Collection<UnitType> getUnitTypes() {
        return unitTypes.values();
    }
}

package fr.ferfoui.america2goat.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.R;

public class UnitManager {

    private static final HashMap<String, UnitType> unitTypes = new HashMap<>(2);

    static {
        putUnitType(Constants.DISTANCE_UNIT_TYPE_NAME, R.string.distance_unit_name, DistanceUnit.values());
        putUnitType(Constants.MASS_UNIT_TYPE_NAME, R.string.mass_unit_name, MassUnit.values());
    }

    private static void putUnitType(String unitTypeName, int resourceNameId, Unit[] units) {
        unitTypes.put(unitTypeName, new UnitType(unitTypeName, resourceNameId, units));
    }

    public static UnitType getUnitType(String unitTypeName) {

        UnitType unitType = unitTypes.get(unitTypeName);

        if (unitType == null) {
            throw new IllegalArgumentException("Unknown unit type: " + unitTypeName);
        }

        return unitType;
    }

    public static List<String> getUnitTypeNames() {
        return new ArrayList<>(unitTypes.keySet());
    }

    public static Collection<UnitType> getUnitTypes() {
        return unitTypes.values();
    }
}

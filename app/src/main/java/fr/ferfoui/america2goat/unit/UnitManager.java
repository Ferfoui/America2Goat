package fr.ferfoui.america2goat.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.ferfoui.america2goat.Constants;

public class UnitManager {

    private static final HashMap<String, Unit[]> unitTypes = new HashMap<>(2);

    static {
        unitTypes.put(Constants.DISTANCE_UNIT_TYPE_NAME, DistanceUnit.values());
        unitTypes.put(Constants.MASS_UNIT_TYPE_NAME, MassUnit.values());
    }

    public static Unit[] getUnits(String unitTypeName) {
        try {
            return unitTypes.get(unitTypeName);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Unknown unit type: " + unitTypeName, e);
        }
    }

    public static List<String> getUnitTypeNames() {
        return new ArrayList<>(unitTypes.keySet());
    }
}

package fr.ferfoui.america2goat.unit;

public class UnitType {

    private final Unit[] units;
    private final String unitTypeName;
    private final int resourceNameId;

    public UnitType(String unitTypeName, int resourceNameId, Unit[] units) {
        this.unitTypeName = unitTypeName;
        this.resourceNameId = resourceNameId;
        this.units = units;
    }

    public int getResourceNameId() {
        return resourceNameId;
    }

    public Unit[] getUnits() {
        return units;
    }

    public String getName() {
        return unitTypeName;
    }
}

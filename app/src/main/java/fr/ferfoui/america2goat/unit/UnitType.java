package fr.ferfoui.america2goat.unit;

public class UnitType {

    private final Unit[] units;
    private final int resourceNameId;

    public UnitType(int resourceNameId, Unit[] units) {
        this.resourceNameId = resourceNameId;
        this.units = units;
    }

    public int getResourceNameId() {
        return resourceNameId;
    }

    public Unit[] getUnits() {
        return units;
    }
}

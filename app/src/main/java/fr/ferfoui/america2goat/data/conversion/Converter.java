package fr.ferfoui.america2goat.data.conversion;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.unit.Unit;

public class Converter {

    private Unit inputUnit;
    private Unit outputUnit;

    public Converter() {
        this(Constants.DEFAULT_INPUT_DISTANCE_UNIT, Constants.DEFAULT_OUTPUT_DISTANCE_UNIT);
    }

    public Converter(Unit initialInputUnit, Unit initialOutputUnit) {
        this.inputUnit = initialInputUnit;
        this.outputUnit = initialOutputUnit;
    }

    public double convert(double value) {
        return value * inputUnit.getWeight() / outputUnit.getWeight();
    }

    public void setInputUnit(Unit inputUnit) {
        this.inputUnit = inputUnit;
    }

    public void setOutputUnit(Unit outputUnit) {
        this.outputUnit = outputUnit;
    }

    public Unit getInputUnit() {
        return inputUnit;
    }

    public Unit getOutputUnit() {
        return outputUnit;
    }
}

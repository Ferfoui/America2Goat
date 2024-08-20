package fr.ferfoui.america2goat.data.conversion;

public class Converter {

    private Unit inputUnit;
    private Unit outputUnit;

    public Converter() {
        this(Unit.METER, Unit.INCH);
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

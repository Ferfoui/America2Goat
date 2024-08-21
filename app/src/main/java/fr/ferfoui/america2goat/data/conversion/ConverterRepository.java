package fr.ferfoui.america2goat.data.conversion;

import fr.ferfoui.america2goat.unit.Unit;

public class ConverterRepository {

    private final Converter converter;

    public ConverterRepository() {
        converter = new Converter();
    }

    public double convert(double value) {
        return converter.convert(value);
    }

    public void setInputUnit(Unit inputUnit) {
        converter.setInputUnit(inputUnit);
    }

    public void setOutputUnit(Unit outputUnit) {
        converter.setOutputUnit(outputUnit);
    }

    public Unit getInputUnit() {
        return converter.getInputUnit();
    }

    public Unit getOutputUnit() {
        return converter.getOutputUnit();
    }
}

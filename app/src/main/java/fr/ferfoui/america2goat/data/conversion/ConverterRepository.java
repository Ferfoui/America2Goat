package fr.ferfoui.america2goat.data.conversion;

import fr.ferfoui.america2goat.unit.Unit;

public class ConverterRepository {

    private final Converter converter;

    public ConverterRepository(Converter converter) {
        this.converter = converter;
    }

    public double convert(double value) {
        return converter.convert(value);
    }

    public Unit getInputUnit() {
        return converter.getInputUnit();
    }

    public void setInputUnit(Unit inputUnit) {
        converter.setInputUnit(inputUnit);
    }

    public Unit getOutputUnit() {
        return converter.getOutputUnit();
    }

    public void setOutputUnit(Unit outputUnit) {
        converter.setOutputUnit(outputUnit);
    }
}

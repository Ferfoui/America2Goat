package fr.ferfoui.america2goat.data.conversion;

import fr.ferfoui.america2goat.unit.Unit;

/**
 * Repository class for handling conversions using a {@link Converter}.
 */
public class ConverterRepository {

    private final Converter converter;

    /**
     * Constructs a ConverterRepository with the specified Converter.
     *
     * @param converter the Converter to be used for conversions
     */
    public ConverterRepository(Converter converter) {
        this.converter = converter;
    }

    /**
     * Converts the given value using the currently defined input and output units.
     *
     * @param value the value to be converted
     * @return the converted value
     */
    public double convert(double value) {
        return converter.convert(value);
    }

    /**
     * Gets the currently used input for conversions.
     *
     * @return the input unit
     */
    public Unit getInputUnit() {
        return converter.getInputUnit();
    }

    /**
     * Sets a new input unit to use for conversions.
     *
     * @param inputUnit the input unit to be set
     */
    public void setInputUnit(Unit inputUnit) {
        converter.setInputUnit(inputUnit);
    }

    /**
     * Gets the currently used output unit for conversions.
     *
     * @return the output unit
     */
    public Unit getOutputUnit() {
        return converter.getOutputUnit();
    }

    /**
     * Sets a new output unit to use for conversions.
     *
     * @param outputUnit the output unit to be set
     */
    public void setOutputUnit(Unit outputUnit) {
        converter.setOutputUnit(outputUnit);
    }
}

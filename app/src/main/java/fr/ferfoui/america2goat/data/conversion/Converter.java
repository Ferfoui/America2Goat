package fr.ferfoui.america2goat.data.conversion;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.unit.Unit;

/**
 * Converter class for converting values between different units.
 */
public class Converter {

    private Unit inputUnit;
    private Unit outputUnit;

    /**
     * Default constructor initializing with default units.
     */
    public Converter() {
        this(Constants.DEFAULT_INPUT_DISTANCE_UNIT, Constants.DEFAULT_OUTPUT_DISTANCE_UNIT);
    }

    /**
     * Constructor initializing with specified units.
     *
     * @param initialInputUnit  the initial input unit
     * @param initialOutputUnit the initial output unit
     */
    public Converter(Unit initialInputUnit, Unit initialOutputUnit) {
        this.inputUnit = initialInputUnit;
        this.outputUnit = initialOutputUnit;
    }

    /**
     * Converts a value from the input unit to the output unit.
     *
     * @param value the value to convert
     * @return the converted value
     */
    public double convert(double value) {
        return value * inputUnit.getFactor() / outputUnit.getFactor();
    }

    /**
     * Gets the input unit.
     *
     * @return the input unit
     */
    public Unit getInputUnit() {
        return inputUnit;
    }

    /**
     * Sets the input unit.
     *
     * @param inputUnit the new input unit
     */
    public void setInputUnit(Unit inputUnit) {
        this.inputUnit = inputUnit;
    }

    /**
     * Gets the output unit.
     *
     * @return the output unit
     */
    public Unit getOutputUnit() {
        return outputUnit;
    }

    /**
     * Sets the output unit.
     *
     * @param outputUnit the new output unit
     */
    public void setOutputUnit(Unit outputUnit) {
        this.outputUnit = outputUnit;
    }
}

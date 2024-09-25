package fr.ferfoui.america2goat.unit;

import androidx.datastore.preferences.core.Preferences;

/**
 * A class that handles the storage of unit preferences.
 * The default input and output units are also stored.
 */
public class UnitStorage {

    private final Preferences.Key<Integer> inputUnitStorageKey;
    private final Preferences.Key<Integer> outputUnitStorageKey;
    private final Unit defaultInputUnit;
    private final Unit defaultOutputUnit;

    /**
     * Constructs a new UnitStorage instance.
     * It stores the keys for the input and output units, as well as the default input and output units.
     *
     * @param inputUnitStorageKey  the key for storing the input unit
     * @param outputUnitStorageKey the key for storing the output unit
     * @param defaultInputUnit     the default input unit
     * @param defaultOutputUnit    the default output unit
     */
    public UnitStorage(Preferences.Key<Integer> inputUnitStorageKey, Preferences.Key<Integer> outputUnitStorageKey, Unit defaultInputUnit, Unit defaultOutputUnit) {
        this.inputUnitStorageKey = inputUnitStorageKey;
        this.outputUnitStorageKey = outputUnitStorageKey;
        this.defaultInputUnit = defaultInputUnit;
        this.defaultOutputUnit = defaultOutputUnit;
    }

    /**
     * Gets the key for storing the input unit.
     *
     * @return the input unit storage key
     */
    public Preferences.Key<Integer> getInputUnitStorageKey() {
        return inputUnitStorageKey;
    }

    /**
     * Gets the key for storing the output unit.
     *
     * @return the output unit storage key
     */
    public Preferences.Key<Integer> getOutputUnitStorageKey() {
        return outputUnitStorageKey;
    }

    /**
     * Gets the default input unit.
     *
     * @return the default input unit
     */
    public Unit getDefaultInputUnit() {
        return defaultInputUnit;
    }

    /**
     * Gets the default output unit.
     *
     * @return the default output unit
     */
    public Unit getDefaultOutputUnit() {
        return defaultOutputUnit;
    }
}

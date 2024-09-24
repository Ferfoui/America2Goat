package fr.ferfoui.america2goat.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.ferfoui.america2goat.data.conversion.ConverterRepository;
import fr.ferfoui.america2goat.data.settings.SettingsRepository;
import fr.ferfoui.america2goat.unit.Unit;
import fr.ferfoui.america2goat.unit.UnitManager;
import fr.ferfoui.america2goat.unit.UnitType;

/**
 * ViewModel for the Home screen.
 * Manages the conversion and settings data.
 */
public class HomeViewModel extends ViewModel {

    // Repository for handling conversion-related data
    private final ConverterRepository converterRepository;
    // Repository for handling settings-related data
    private final SettingsRepository settingsRepository;

    // LiveData for the conversion result
    private final MutableLiveData<String> result;
    // LiveData for the input unit ordinal
    private final MutableLiveData<Integer> inputUnitOrdinal;
    // LiveData for the output unit ordinal
    private final MutableLiveData<Integer> outputUnitOrdinal;
    // LiveData for the changed input value
    private final MutableLiveData<Double> changedInputValue;
    // LiveData for the changed unit type
    private final MutableLiveData<UnitType> changedUnitType;

    private UnitType currentUnitType;
    private double currentInputValue;

    /**
     * Constructor for HomeViewModel.
     * Initializes the repositories and LiveData objects.
     *
     * @param converterRepository Repository for handling conversion-related data.
     * @param settingsRepository  Repository for handling settings-related data.
     */
    public HomeViewModel(ConverterRepository converterRepository, SettingsRepository settingsRepository) {
        this.converterRepository = converterRepository;
        this.settingsRepository = settingsRepository;

        inputUnitOrdinal = new MutableLiveData<>();
        outputUnitOrdinal = new MutableLiveData<>();
        result = new MutableLiveData<>();
        changedInputValue = new MutableLiveData<>();
        changedUnitType = new MutableLiveData<>();

        currentUnitType = UnitManager.getUnitType(settingsRepository.getUnitTypePreference());
        currentInputValue = 0d;

        setInputUnit(settingsRepository.getInputUnitPreference(currentUnitType.getName()));
        setOutputUnit(settingsRepository.getOutputUnitPreference(currentUnitType.getName()));

        settingsRepository.getUnitTypePreferenceLiveData().observeForever(this::updateUnitType);
    }

    /**
     * Converts the given value using the current unit settings.
     *
     * @param value The value to be converted.
     */
    public void convert(double value) {
        currentInputValue = value;
        setRoundedResult(converterRepository.convert(value));
    }

    /**
     * Swaps the input and output units and values.
     */
    public void swapUnitsAndValues() {
        currentInputValue = round(converterRepository.convert(currentInputValue));

        changedInputValue.setValue(currentInputValue);
        setInputUnit(converterRepository.getOutputUnit().ordinal());
    }

    /**
     * Resets the input value to zero.
     */
    public void resetInputValue() {
        currentInputValue = 0d;
        changedInputValue.setValue(currentInputValue);
        setRoundedResult(0d);
    }

    /**
     * Sets the rounded result value.
     *
     * @param resultValue The result value to be rounded and set.
     */
    private void setRoundedResult(double resultValue) {
        result.setValue(String.valueOf(round(resultValue)));
    }

    /**
     * Rounds the given value based on the user's preference.
     *
     * @param value The value to be rounded.
     * @return The rounded value.
     */
    private double round(double value) {
        int roundPreference = settingsRepository.getRoundPreference();

        if (roundPreference > -1) {
            double multiplier = Math.pow(10, roundPreference);
            return Math.round(value * multiplier) / multiplier;
        } else {
            return value;
        }
    }

    /**
     * Updates the unit type based on the given unit type name.
     *
     * @param unitTypeName The name of the new unit type.
     */
    private void updateUnitType(String unitTypeName) {
        UnitType newUnitType = UnitManager.getUnitType(unitTypeName);

        if (newUnitType == currentUnitType)
            return;

        resetInputValue();
        currentUnitType = newUnitType;

        setInputUnit(settingsRepository.getInputUnitPreference(unitTypeName));
        setOutputUnit(settingsRepository.getOutputUnitPreference(unitTypeName));

        changedUnitType.setValue(currentUnitType);
    }

    /**
     * Returns the LiveData for the conversion result.
     *
     * @return LiveData for the conversion result.
     */
    public LiveData<String> getResultLiveData() {
        return result;
    }

    /**
     * Returns the LiveData for the changed input value.
     *
     * @return LiveData for the changed input value.
     */
    public LiveData<Double> getChangedInputValueLiveData() {
        return changedInputValue;
    }

    /**
     * Returns the LiveData for the changed unit type.
     *
     * @return LiveData for the changed unit type.
     */
    public LiveData<UnitType> getChangedUnitTypeLiveData() {
        return changedUnitType;
    }

    /**
     * Returns the LiveData for the input unit ordinal.
     *
     * @return LiveData for the input unit ordinal.
     */
    public LiveData<Integer> getInputUnitOrdinalLiveData() {
        return inputUnitOrdinal;
    }

    /**
     * Returns the LiveData for the output unit ordinal.
     *
     * @return LiveData for the output unit ordinal.
     */
    public LiveData<Integer> getOutputUnitOrdinalLiveData() {
        return outputUnitOrdinal;
    }

    /**
     * Returns the current input unit.
     *
     * @return The current input unit.
     */
    public Unit getInputUnit() {
        return converterRepository.getInputUnit();
    }

    /**
     * Sets the input unit based on the given ordinal.
     *
     * @param inputUnitOrdinal The ordinal of the input unit to be set.
     */
    public void setInputUnit(int inputUnitOrdinal) {
        int oldInputUnitOrdinal = converterRepository.getInputUnit().ordinal();

        converterRepository.setInputUnit(currentUnitType.getUnits()[inputUnitOrdinal]);
        this.inputUnitOrdinal.setValue(inputUnitOrdinal);

        if (inputUnitOrdinal == converterRepository.getOutputUnit().ordinal()) {
            setOutputUnit(oldInputUnitOrdinal);
        } else {
            convert(currentInputValue);
        }
    }

    /**
     * Returns the current output unit.
     *
     * @return The current output unit.
     */
    public Unit getOutputUnit() {
        return converterRepository.getOutputUnit();
    }

    /**
     * Sets the output unit based on the given ordinal.
     *
     * @param outputUnitOrdinal The ordinal of the output unit to be set.
     */
    public void setOutputUnit(int outputUnitOrdinal) {
        int oldOutputUnitOrdinal = converterRepository.getOutputUnit().ordinal();

        converterRepository.setOutputUnit(currentUnitType.getUnits()[outputUnitOrdinal]);
        this.outputUnitOrdinal.setValue(outputUnitOrdinal);

        if (outputUnitOrdinal == converterRepository.getInputUnit().ordinal()) {
            setInputUnit(oldOutputUnitOrdinal);
        } else {
            convert(currentInputValue);
        }
    }

    /**
     * Returns the units of the current unit type.
     *
     * @return An array of units of the current unit type.
     */
    public Unit[] getCurrentUnits() {
        return currentUnitType.getUnits();
    }

    /**
     * Returns the currently selected unit type.
     *
     * @return The current unit type.
     */
    public UnitType getCurrentUnitType() {
        return currentUnitType;
    }

    /**
     * Returns the current input value.
     *
     * @return The current input value.
     */
    public double getCurrentInputValue() {
        return currentInputValue;
    }
}
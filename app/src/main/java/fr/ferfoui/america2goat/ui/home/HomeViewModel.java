package fr.ferfoui.america2goat.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.ferfoui.america2goat.data.conversion.ConverterRepository;
import fr.ferfoui.america2goat.data.settings.SettingsRepository;
import fr.ferfoui.america2goat.unit.Unit;
import fr.ferfoui.america2goat.unit.UnitManager;
import fr.ferfoui.america2goat.unit.UnitType;

public class HomeViewModel extends ViewModel {

    private final ConverterRepository converterRepository;
    private final SettingsRepository settingsRepository;

    private final MutableLiveData<String> result;
    private final MutableLiveData<Integer> inputUnitOrdinal;
    private final MutableLiveData<Integer> outputUnitOrdinal;
    private final MutableLiveData<Double> changedInputValue;
    private final MutableLiveData<UnitType> changedUnitType;

    private UnitType currentUnitType;
    private double currentInputValue;

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

    public void convert(double value) {
        currentInputValue = value;
        setRoundedResult(converterRepository.convert(value));
    }

    public void swapUnitsAndValues() {
        currentInputValue = round(converterRepository.convert(currentInputValue));

        changedInputValue.setValue(currentInputValue);
        setInputUnit(converterRepository.getOutputUnit().ordinal());
    }

    public void resetInputValue() {
        currentInputValue = 0d;
        changedInputValue.setValue(currentInputValue);
        setRoundedResult(0d);
    }

    private void setRoundedResult(double resultValue) {
        result.setValue(String.valueOf(round(resultValue)));
    }

    private double round(double value) {
        int roundPreference = settingsRepository.getRoundPreference();

        if (roundPreference > -1) {
            double multiplier = Math.pow(10, roundPreference);
            return Math.round(value * multiplier) / multiplier;
        } else {
            return value;
        }
    }

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

    public LiveData<String> getResultLiveData() {
        return result;
    }

    public LiveData<Double> getChangedInputValueLiveData() {
        return changedInputValue;
    }

    public LiveData<UnitType> getChangedUnitTypeLiveData() {
        return changedUnitType;
    }

    public LiveData<Integer> getInputUnitOrdinalLiveData() {
        return inputUnitOrdinal;
    }

    public LiveData<Integer> getOutputUnitOrdinalLiveData() {
        return outputUnitOrdinal;
    }


    public Unit getInputUnit() {
        return converterRepository.getInputUnit();
    }

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


    public Unit getOutputUnit() {
        return converterRepository.getOutputUnit();
    }

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


    public Unit[] getCurrentUnits() {
        return currentUnitType.getUnits();
    }

    public UnitType getCurrentUnitType() {
        return currentUnitType;
    }

    public double getCurrentInputValue() {
        return currentInputValue;
    }
}
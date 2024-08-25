package fr.ferfoui.america2goat.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.ferfoui.america2goat.data.conversion.ConverterRepository;
import fr.ferfoui.america2goat.data.settings.SettingsRepository;
import fr.ferfoui.america2goat.unit.Unit;

public class HomeViewModel extends ViewModel {

    private final ConverterRepository converterRepository;
    private final SettingsRepository settingsRepository;

    private final MutableLiveData<String> result;
    private final MutableLiveData<Integer> inputUnitOrdinal;
    private final MutableLiveData<Integer> outputUnitOrdinal;
    private final MutableLiveData<Double> changedInputValue;

    private double currentInputValue;

    public HomeViewModel(ConverterRepository converterRepository, SettingsRepository settingsRepository) {
        this.converterRepository = converterRepository;
        this.settingsRepository = settingsRepository;

        inputUnitOrdinal = new MutableLiveData<>();
        outputUnitOrdinal = new MutableLiveData<>();
        result = new MutableLiveData<>();
        changedInputValue = new MutableLiveData<>();

        currentInputValue = 0d;

        Log.d("HomeViewModel", "Getting input and output unit preferences");
        setInputUnit(settingsRepository.getInputUnitPreference());
        setOutputUnit(settingsRepository.getOutputUnitPreference());
        Log.d("HomeViewModel", "Input and output unit preferences are set");
    }

    public void convert(double value) {
        currentInputValue = value;
        setRoundedResult(converterRepository.convert(value));
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

    public LiveData<String> getResult() {
        return result;
    }

    public LiveData<Double> getChangedInputValue() {
        return changedInputValue;
    }

    public LiveData<Integer> getInputUnitOrdinal() {
        return inputUnitOrdinal;
    }

    public LiveData<Integer> getOutputUnitOrdinal() {
        return outputUnitOrdinal;
    }

    public Unit getInputUnit() {
        return converterRepository.getInputUnit();
    }

    public void setInputUnit(int inputUnitOrdinal) {
        int oldInputUnitOrdinal = converterRepository.getInputUnit().ordinal();

        converterRepository.setInputUnit(Unit.values()[inputUnitOrdinal]);
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

        converterRepository.setOutputUnit(Unit.values()[outputUnitOrdinal]);
        this.outputUnitOrdinal.setValue(outputUnitOrdinal);

        if (outputUnitOrdinal == converterRepository.getInputUnit().ordinal()) {
            setInputUnit(oldOutputUnitOrdinal);
        } else {
            convert(currentInputValue);
        }
    }

    public void swapUnitsAndValues() {

        currentInputValue = round(converterRepository.convert(currentInputValue));

        changedInputValue.setValue(currentInputValue);
        setInputUnit(converterRepository.getOutputUnit().ordinal());
    }
}
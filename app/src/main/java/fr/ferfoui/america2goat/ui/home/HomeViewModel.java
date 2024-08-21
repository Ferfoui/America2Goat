package fr.ferfoui.america2goat.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.ferfoui.america2goat.data.conversion.ConverterRepository;
import fr.ferfoui.america2goat.unit.Unit;

public class HomeViewModel extends ViewModel {

    private final ConverterRepository converterRepository;

    private double currentInputValue;
    private final MutableLiveData<String> result;


    public HomeViewModel() {
        converterRepository = new ConverterRepository();

        result = new MutableLiveData<>();
        currentInputValue = 0d;
        convert(currentInputValue);
    }

    public void convert(double value) {
        currentInputValue = value;
        result.setValue(String.valueOf(converterRepository.convert(value)));
    }

    public void setInputUnit(Unit inputUnit) {
        converterRepository.setInputUnit(inputUnit);
        convert(currentInputValue);
    }

    public void setOutputUnit(Unit outputUnit) {
        converterRepository.setOutputUnit(outputUnit);
        convert(currentInputValue);
    }

    public LiveData<String> getResult() {
        return result;
    }

    public Unit getInputUnit() {
        return converterRepository.getInputUnit();
    }

    public Unit getOutputUnit() {
        return converterRepository.getOutputUnit();
    }
}
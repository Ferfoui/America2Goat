package fr.ferfoui.america2goat.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.ferfoui.america2goat.data.settings.SettingsRepository;

public class DashboardViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;

    private final MutableLiveData<Integer> inputUnitOrdinal;
    private final MutableLiveData<Integer> outputUnitOrdinal;

    public DashboardViewModel(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;

        inputUnitOrdinal = new MutableLiveData<>();
        outputUnitOrdinal = new MutableLiveData<>();

        inputUnitOrdinal.setValue(settingsRepository.getInputUnitPreference());
        outputUnitOrdinal.setValue(settingsRepository.getOutputUnitPreference());
    }

    public MutableLiveData<Integer> getInputUnitLiveData() {
        return inputUnitOrdinal;
    }

    public MutableLiveData<Integer> getOutputUnitLiveData() {
        return outputUnitOrdinal;
    }

    public int getInputUnitPreference() {
        return settingsRepository.getInputUnitPreference();
    }

    public void setInputUnitPreference(int inputUnitPreferenceOrdinal) {
        int oldInputUnitOrdinal = settingsRepository.getInputUnitPreference();

        inputUnitOrdinal.setValue(inputUnitPreferenceOrdinal);
        settingsRepository.setInputUnitPreference(inputUnitPreferenceOrdinal);

        if (inputUnitPreferenceOrdinal == getOutputUnitPreference()) {
            setOutputUnitPreference(oldInputUnitOrdinal);
        }
    }

    public int getOutputUnitPreference() {
        return settingsRepository.getOutputUnitPreference();
    }

    public void setOutputUnitPreference(int unitPreferenceOrdinal) {
        int oldOutputUnitOrdinal = settingsRepository.getOutputUnitPreference();

        outputUnitOrdinal.setValue(unitPreferenceOrdinal);
        settingsRepository.setOutputUnitPreference(unitPreferenceOrdinal);

        if (unitPreferenceOrdinal == getInputUnitPreference()) {
            setInputUnitPreference(oldOutputUnitOrdinal);
        }
    }

}
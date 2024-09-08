package fr.ferfoui.america2goat.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.ferfoui.america2goat.data.settings.SettingsRepository;
import fr.ferfoui.america2goat.unit.Unit;
import fr.ferfoui.america2goat.unit.UnitManager;

public class DashboardViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;

    private final MutableLiveData<Integer> inputUnitOrdinal;
    private final MutableLiveData<Integer> outputUnitOrdinal;

    private final int roundSeekBarMax;
    private final Unit[] currentUnits;

    public DashboardViewModel(SettingsRepository settingsRepository, int roundSeekBarMax) {
        this.settingsRepository = settingsRepository;

        inputUnitOrdinal = new MutableLiveData<>();
        outputUnitOrdinal = new MutableLiveData<>();

        inputUnitOrdinal.setValue(settingsRepository.getInputUnitPreference());
        outputUnitOrdinal.setValue(settingsRepository.getOutputUnitPreference());

        this.roundSeekBarMax = roundSeekBarMax;

        currentUnits = UnitManager.getUnits(settingsRepository.getUnitTypePreference());
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

    public void setRoundPreference(int progress) {
        if (progress >= roundSeekBarMax) {
            progress = -1;
        }

        settingsRepository.setRoundPreference(progress);
    }

    public int getSeekBarPosition() {
        int roundPreference = settingsRepository.getRoundPreference();

        if (roundPreference <= -1) {
            return roundSeekBarMax;
        } else {
            return roundPreference;
        }
    }

    public Unit[] getCurrentUnits() {
        return currentUnits;
    }
}
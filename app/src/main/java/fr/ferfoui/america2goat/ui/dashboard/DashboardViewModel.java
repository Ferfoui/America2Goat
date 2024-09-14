package fr.ferfoui.america2goat.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.ferfoui.america2goat.data.settings.SettingsRepository;
import fr.ferfoui.america2goat.unit.Unit;
import fr.ferfoui.america2goat.unit.UnitManager;
import fr.ferfoui.america2goat.unit.UnitType;

public class DashboardViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;

    private final MutableLiveData<Integer> inputUnitOrdinal;
    private final MutableLiveData<Integer> outputUnitOrdinal;

    private final int roundSeekBarMax;

    private UnitType currentUnitType;

    public DashboardViewModel(SettingsRepository settingsRepository, int roundSeekBarMax) {
        this.settingsRepository = settingsRepository;

        inputUnitOrdinal = new MutableLiveData<>();
        outputUnitOrdinal = new MutableLiveData<>();

        currentUnitType = UnitManager.getUnitType(settingsRepository.getUnitTypePreference());

        inputUnitOrdinal.setValue(settingsRepository.getInputUnitPreference(currentUnitType.getName()));
        outputUnitOrdinal.setValue(settingsRepository.getOutputUnitPreference(currentUnitType.getName()));

        this.roundSeekBarMax = roundSeekBarMax;
    }

    public MutableLiveData<Integer> getInputUnitLiveData() {
        return inputUnitOrdinal;
    }

    public MutableLiveData<Integer> getOutputUnitLiveData() {
        return outputUnitOrdinal;
    }


    public String getUnitTypePreference() {
        return settingsRepository.getUnitTypePreference();
    }

    public void setUnitTypePreference(String unitType) {
        currentUnitType = UnitManager.getUnitType(unitType);
        settingsRepository.setUnitTypePreference(unitType);
    }


    public int getInputUnitPreference() {
        return settingsRepository.getInputUnitPreference(currentUnitType.getName());
    }

    public void setInputUnitPreference(int inputUnitPreferenceOrdinal) {
        int oldInputUnitOrdinal = settingsRepository.getInputUnitPreference(currentUnitType.getName());

        inputUnitOrdinal.setValue(inputUnitPreferenceOrdinal);
        settingsRepository.setInputUnitPreference(currentUnitType.getName(), inputUnitPreferenceOrdinal);

        if (inputUnitPreferenceOrdinal == getOutputUnitPreference()) {
            setOutputUnitPreference(oldInputUnitOrdinal);
        }
    }


    public int getOutputUnitPreference() {
        return settingsRepository.getOutputUnitPreference(currentUnitType.getName());
    }

    public void setOutputUnitPreference(int unitPreferenceOrdinal) {
        int oldOutputUnitOrdinal = settingsRepository.getOutputUnitPreference(currentUnitType.getName());

        outputUnitOrdinal.setValue(unitPreferenceOrdinal);
        settingsRepository.setOutputUnitPreference(currentUnitType.getName(), unitPreferenceOrdinal);

        if (unitPreferenceOrdinal == getInputUnitPreference()) {
            setInputUnitPreference(oldOutputUnitOrdinal);
        }
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
        return currentUnitType.getUnits();
    }

    public void setRoundPreference(int progress) {
        if (progress >= roundSeekBarMax) {
            progress = -1;
        }

        settingsRepository.setRoundPreference(progress);
    }
}
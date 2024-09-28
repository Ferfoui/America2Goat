package fr.ferfoui.america2goat.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.ferfoui.america2goat.data.settings.SettingsRepository;
import fr.ferfoui.america2goat.unit.Unit;
import fr.ferfoui.america2goat.unit.UnitManager;
import fr.ferfoui.america2goat.unit.UnitType;

/**
 * ViewModel for the Dashboard screen.
 * Manages the data and settings for the dashboard.
 */
public class DashboardViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;

    private final MutableLiveData<Integer> inputUnitOrdinal;
    private final MutableLiveData<Integer> outputUnitOrdinal;

    private final int roundSeekBarMax;

    private UnitType currentUnitType;

    /**
     * Constructor for DashboardViewModel.
     *
     * @param settingsRepository The repository for settings data.
     * @param roundSeekBarMax    The maximum value for the round seek bar.
     */
    public DashboardViewModel(SettingsRepository settingsRepository, int roundSeekBarMax) {
        this.settingsRepository = settingsRepository;

        inputUnitOrdinal = new MutableLiveData<>();
        outputUnitOrdinal = new MutableLiveData<>();

        currentUnitType = UnitManager.getUnitType(settingsRepository.getUnitTypePreference());

        inputUnitOrdinal.setValue(settingsRepository.getInputUnitPreference(currentUnitType.getName()));
        outputUnitOrdinal.setValue(settingsRepository.getOutputUnitPreference(currentUnitType.getName()));

        this.roundSeekBarMax = roundSeekBarMax;
    }

    /**
     * Gets the live data for the input unit ordinal.
     *
     * @return MutableLiveData for the input unit ordinal.
     */
    public MutableLiveData<Integer> getInputUnitLiveData() {
        return inputUnitOrdinal;
    }

    /**
     * Gets the live data for the output unit ordinal.
     *
     * @return MutableLiveData for the output unit ordinal.
     */
    public MutableLiveData<Integer> getOutputUnitLiveData() {
        return outputUnitOrdinal;
    }

    /**
     * Gets the unit type preference.
     *
     * @return The unit type preference as a String.
     */
    public String getUnitTypePreference() {
        return settingsRepository.getUnitTypePreference();
    }

    /**
     * Sets the unit type preference.
     *
     * @param unitType The unit type to set.
     */
    public void setUnitTypePreference(String unitType) {
        currentUnitType = UnitManager.getUnitType(unitType);
        settingsRepository.setUnitTypePreference(unitType);
    }

    /**
     * Gets the input unit preference.
     *
     * @return The input unit preference ordinal.
     */
    public int getInputUnitPreference() {
        return settingsRepository.getInputUnitPreference(currentUnitType.getName());
    }

    /**
     * Sets the input unit preference.
     *
     * @param inputUnitPreferenceOrdinal The ordinal of the input unit preference to set.
     */
    public void setInputUnitPreference(int inputUnitPreferenceOrdinal) {
        int oldInputUnitOrdinal = settingsRepository.getInputUnitPreference(currentUnitType.getName());

        inputUnitOrdinal.setValue(inputUnitPreferenceOrdinal);
        settingsRepository.setInputUnitPreference(currentUnitType.getName(), inputUnitPreferenceOrdinal);

        if (inputUnitPreferenceOrdinal == getOutputUnitPreference()) {
            setOutputUnitPreference(oldInputUnitOrdinal);
        }
    }

    /**
     * Gets the output unit preference.
     *
     * @return The output unit preference ordinal.
     */
    public int getOutputUnitPreference() {
        return settingsRepository.getOutputUnitPreference(currentUnitType.getName());
    }

    /**
     * Sets the output unit preference.
     *
     * @param unitPreferenceOrdinal The ordinal of the output unit preference to set.
     */
    public void setOutputUnitPreference(int unitPreferenceOrdinal) {
        int oldOutputUnitOrdinal = settingsRepository.getOutputUnitPreference(currentUnitType.getName());

        outputUnitOrdinal.setValue(unitPreferenceOrdinal);
        settingsRepository.setOutputUnitPreference(currentUnitType.getName(), unitPreferenceOrdinal);

        if (unitPreferenceOrdinal == getInputUnitPreference()) {
            setInputUnitPreference(oldOutputUnitOrdinal);
        }
    }

    /**
     * Gets the position of the seek bar.
     *
     * @return The position of the seek bar.
     */
    public int getSeekBarPosition() {
        int roundPreference = settingsRepository.getRoundPreference();

        if (roundPreference <= -1) {
            return roundSeekBarMax;
        } else {
            return roundPreference;
        }
    }

    /**
     * Gets the current units for the unit type.
     *
     * @return An array of current units.
     */
    public Unit[] getCurrentUnits() {
        return currentUnitType.getUnits();
    }

    /**
     * Sets the round preference.
     *
     * @param progress The progress value to set.
     */
    public void setRoundPreference(int progress) {
        if (progress >= roundSeekBarMax) {
            progress = -1;
        }

        settingsRepository.setRoundPreference(progress);
    }
}
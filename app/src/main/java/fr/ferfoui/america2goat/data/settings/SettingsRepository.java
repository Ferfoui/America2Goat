package fr.ferfoui.america2goat.data.settings;

import fr.ferfoui.america2goat.Constants;

public class SettingsRepository {
    private final AppSettings appSettings;

    public SettingsRepository(AppSettings appSettings) {
        this.appSettings = appSettings;
    }


    public String getUnitTypePreference() {
        if (appSettings.isDataAvailable(StorageKeys.UNIT_TYPE_STORAGE_KEY)) {
            return appSettings.getData(StorageKeys.UNIT_TYPE_STORAGE_KEY);
        }
        setUnitTypePreference(Constants.DEFAULT_UNIT_TYPE);

        return Constants.DEFAULT_UNIT_TYPE;
    }

    public void setUnitTypePreference(String unitType) {
        appSettings.setData(StorageKeys.UNIT_TYPE_STORAGE_KEY, unitType);
    }


    public int getInputUnitPreference() {
        if (appSettings.isDataAvailable(StorageKeys.INPUT_UNIT_STORAGE_KEY)) {
            return appSettings.getData(StorageKeys.INPUT_UNIT_STORAGE_KEY);
        }
        setInputUnitPreference(Constants.DEFAULT_INPUT_DISTANCE_UNIT.ordinal());

        return Constants.DEFAULT_INPUT_DISTANCE_UNIT.ordinal();
    }

    public void setInputUnitPreference(int unitPreferenceOrdinal) {
        appSettings.setData(StorageKeys.INPUT_UNIT_STORAGE_KEY, unitPreferenceOrdinal);
    }


    public int getOutputUnitPreference() {
        if (appSettings.isDataAvailable(StorageKeys.OUTPUT_UNIT_STORAGE_KEY)) {
            return appSettings.getData(StorageKeys.OUTPUT_UNIT_STORAGE_KEY);
        }
        setOutputUnitPreference(Constants.DEFAULT_OUTPUT_DISTANCE_UNIT.ordinal());

        return Constants.DEFAULT_OUTPUT_DISTANCE_UNIT.ordinal();
    }

    public void setOutputUnitPreference(int unitPreferenceOrdinal) {
        appSettings.setData(StorageKeys.OUTPUT_UNIT_STORAGE_KEY, unitPreferenceOrdinal);
    }


    /**
     * Get the round preference, it is a number that represents the number of decimal places to round the result to.
     * If the round preference is -1, the result will not be rounded.
     * @return the round preference
     */
    public int getRoundPreference() {
        if (appSettings.isDataAvailable(StorageKeys.ROUND_PREFERENCE_STORAGE_KEY)) {
            return appSettings.getData(StorageKeys.ROUND_PREFERENCE_STORAGE_KEY);
        }
        setRoundPreference(Constants.DEFAULT_ROUND_PREFERENCE);

        return Constants.DEFAULT_ROUND_PREFERENCE;
    }

    public void setRoundPreference(int roundPreference) {
        appSettings.setData(StorageKeys.ROUND_PREFERENCE_STORAGE_KEY, roundPreference);
    }
}

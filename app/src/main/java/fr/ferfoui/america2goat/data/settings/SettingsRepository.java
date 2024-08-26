package fr.ferfoui.america2goat.data.settings;

import android.util.Log;

import fr.ferfoui.america2goat.Constants;

public class SettingsRepository {
    private final AppSettings appSettings;

    public SettingsRepository(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public int getInputUnitPreference() {
        Log.d("SettingsRepository", "Checking if input unit preference is available");

        if (appSettings.isDataAvailable(StorageKeys.INPUT_UNIT_STORAGE_KEY)) {
            Log.d("SettingsRepository", "It is available");
            return appSettings.getData(StorageKeys.INPUT_UNIT_STORAGE_KEY);
        }
        Log.d("SettingsRepository", "It is not available, setting default input unit preference");
        setInputUnitPreference(Constants.DEFAULT_INPUT_DISTANCE_UNIT.ordinal());

        Log.d("SettingsRepository", "Using default input unit preference");
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
        if (appSettings.isDataAvailable(StorageKeys.ROUND_PREFERENCE)) {
            return appSettings.getData(StorageKeys.ROUND_PREFERENCE);
        }
        setRoundPreference(Constants.DEFAULT_ROUND_PREFERENCE);

        return Constants.DEFAULT_ROUND_PREFERENCE;
    }

    public void setRoundPreference(int roundPreference) {
        appSettings.setData(StorageKeys.ROUND_PREFERENCE, roundPreference);
    }
}

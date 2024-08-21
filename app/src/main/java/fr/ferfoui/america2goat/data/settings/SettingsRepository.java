package fr.ferfoui.america2goat.data.settings;

import android.util.Log;

import fr.ferfoui.america2goat.Constants;

public class SettingsRepository {
    private final AppSettings appSettings;

    public SettingsRepository(AppSettings appSettings) {
        this.appSettings = appSettings;
        Log.d("SettingsRepository", "SettingsRepository is created");
    }

    public void setInputUnitPreference(int unitPreferenceOrdinal) {
        appSettings.setData(StorageKeys.INPUT_UNIT_STORAGE_KEY, unitPreferenceOrdinal);
    }

    public void setOutputUnitPreference(int unitPreferenceOrdinal) {
        appSettings.setData(StorageKeys.OUTPUT_UNIT_STORAGE_KEY, unitPreferenceOrdinal);
    }

    public int getInputUnitPreference() {
        Log.d("SettingsRepository", "Checking if input unit preference is available");

        if (appSettings.isDataAvailable(StorageKeys.INPUT_UNIT_STORAGE_KEY)) {
            Log.d("SettingsRepository", "It is available");
            return appSettings.getData(StorageKeys.INPUT_UNIT_STORAGE_KEY);
        }
        Log.d("SettingsRepository", "It is not available, setting default input unit preference");
        setInputUnitPreference(Constants.DEFAULT_INPUT_UNIT.ordinal());

        Log.d("SettingsRepository", "Using default input unit preference");
        return Constants.DEFAULT_INPUT_UNIT.ordinal();
    }

    public int getOutputUnitPreference() {
        Log.d("SettingsRepository", "Checking if output unit preference is available");

        if (appSettings.isDataAvailable(StorageKeys.OUTPUT_UNIT_STORAGE_KEY)) {
            Log.d("SettingsRepository", "It is available");
            return appSettings.getData(StorageKeys.OUTPUT_UNIT_STORAGE_KEY);
        }
        Log.d("SettingsRepository", "It is not available, setting default output unit preference");
        setOutputUnitPreference(Constants.DEFAULT_OUTPUT_UNIT.ordinal());

        Log.d("SettingsRepository", "Using default output unit preference");
        return Constants.DEFAULT_OUTPUT_UNIT.ordinal();
    }
}

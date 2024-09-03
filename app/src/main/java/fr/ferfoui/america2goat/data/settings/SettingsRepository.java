package fr.ferfoui.america2goat.data.settings;

import androidx.datastore.preferences.core.Preferences;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.data.DataStorage;

public class SettingsRepository {
    private final DataStorage dataStorage;

    public SettingsRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }


    public String getUnitTypePreference() {
        return getStoredPreferenceOrSetDefault(StorageKeys.UNIT_TYPE_STORAGE_KEY, Constants.DEFAULT_UNIT_TYPE);
    }

    public void setUnitTypePreference(String unitType) {
        dataStorage.setData(StorageKeys.UNIT_TYPE_STORAGE_KEY, unitType);
    }


    public int getInputUnitPreference() {
        return getStoredPreferenceOrSetDefault(StorageKeys.INPUT_UNIT_STORAGE_KEY, Constants.DEFAULT_INPUT_DISTANCE_UNIT.ordinal());
    }

    public void setInputUnitPreference(int unitPreferenceOrdinal) {
        dataStorage.setData(StorageKeys.INPUT_UNIT_STORAGE_KEY, unitPreferenceOrdinal);
    }


    public int getOutputUnitPreference() {
        return getStoredPreferenceOrSetDefault(StorageKeys.OUTPUT_UNIT_STORAGE_KEY, Constants.DEFAULT_OUTPUT_DISTANCE_UNIT.ordinal());
    }

    public void setOutputUnitPreference(int unitPreferenceOrdinal) {
        dataStorage.setData(StorageKeys.OUTPUT_UNIT_STORAGE_KEY, unitPreferenceOrdinal);
    }


    /**
     * Get the round preference, it is a number that represents the number of decimal places to round the result to.
     * If the round preference is -1, the result will not be rounded.
     *
     * @return the round preference
     */
    public int getRoundPreference() {
        return getStoredPreferenceOrSetDefault(StorageKeys.ROUND_PREFERENCE_STORAGE_KEY, Constants.DEFAULT_ROUND_PREFERENCE);
    }

    public void setRoundPreference(int roundPreference) {
        dataStorage.setData(StorageKeys.ROUND_PREFERENCE_STORAGE_KEY, roundPreference);
    }


    /**
     * Get a stored preference or set a default value if the preference is not stored.
     *
     * @param key          the key of the preference
     * @param defaultValue the default value to set if the preference is not stored
     * @param <T>          the type of the preference
     * @return the stored preference or the default value
     */
    private <T> T getStoredPreferenceOrSetDefault(Preferences.Key<T> key, T defaultValue) {
        if (dataStorage.isDataAvailable(key)) {
            return dataStorage.getData(key);
        }
        dataStorage.setData(key, defaultValue);

        return defaultValue;
    }
}

package fr.ferfoui.america2goat.data.settings;

import androidx.datastore.preferences.core.Preferences;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.data.DataStorage;

public class SettingsRepository {
    private final DataStorage dataStorage;

    public SettingsRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }


    /**
     * Get the unit type preference, it is a string that represents the current unit type.
     *
     * @return the unit type preference
     * @see Constants#DEFAULT_UNIT_TYPE
     */
    public String getUnitTypePreference() {
        return getStoredPreferenceOrSetDefault(StorageKeys.UNIT_TYPE_STORAGE_KEY, Constants.DEFAULT_UNIT_TYPE);
    }

    /**
     * Set the unit type preference, it is a string that represents the current unit type.
     *
     * @param unitType the unit type to set
     * @see Constants#DEFAULT_UNIT_TYPE
     */
    public void setUnitTypePreference(String unitType) {
        dataStorage.setData(StorageKeys.UNIT_TYPE_STORAGE_KEY, unitType);
    }


    /**
     * Get the input unit preference, it is a number that represents the ordinal of the current input unit.
     *
     * @param unitType the unit type to get the input unit preference for
     * @return the input unit preference
     */
    public int getInputUnitPreference(String unitType) {
        Preferences.Key<Integer> key;
        int defaultUnitOrdinal;

        switch (unitType) {
            case Constants.DISTANCE_UNIT_TYPE_NAME:
                key = StorageKeys.INPUT_DISTANCE_UNIT_STORAGE_KEY;
                defaultUnitOrdinal = Constants.DEFAULT_INPUT_DISTANCE_UNIT.ordinal();
                break;
            case Constants.MASS_UNIT_TYPE_NAME:
                key = StorageKeys.INPUT_MASS_UNIT_STORAGE_KEY;
                defaultUnitOrdinal = Constants.DEFAULT_INPUT_MASS_UNIT.ordinal();
                break;
            default:
                throw new IllegalArgumentException("Unknown unit type: " + unitType);
        }

        return getStoredPreferenceOrSetDefault(key, defaultUnitOrdinal);
    }

    /**
     * Set the input unit preference, it is a number that represents the ordinal of the input unit.
     *
     * @param unitType            the unit type to set the input unit preference for
     * @param unitPreferenceOrdinal the ordinal of the input unit to set
     */
    public void setInputUnitPreference(String unitType, int unitPreferenceOrdinal) {
        Preferences.Key<Integer> key;

        switch (unitType) {
            case Constants.DISTANCE_UNIT_TYPE_NAME:
                key = StorageKeys.INPUT_DISTANCE_UNIT_STORAGE_KEY;
                break;
            case Constants.MASS_UNIT_TYPE_NAME:
                key = StorageKeys.INPUT_MASS_UNIT_STORAGE_KEY;
                break;
            default:
                throw new IllegalArgumentException("Unknown unit type: " + unitType);
        }

        dataStorage.setData(key, unitPreferenceOrdinal);
    }


    /**
     * Get the output unit preference, it is a number that represents the ordinal of the current output unit.
     *
     * @param unitType the unit type to get the output unit preference for
     * @return the output unit preference
     */
    public int getOutputUnitPreference(String unitType) {
        Preferences.Key<Integer> key;
        int defaultUnitOrdinal;

        switch (unitType) {
            case Constants.DISTANCE_UNIT_TYPE_NAME:
                key = StorageKeys.OUTPUT_DISTANCE_UNIT_STORAGE_KEY;
                defaultUnitOrdinal = Constants.DEFAULT_OUTPUT_DISTANCE_UNIT.ordinal();
                break;
            case Constants.MASS_UNIT_TYPE_NAME:
                key = StorageKeys.OUTPUT_MASS_UNIT_STORAGE_KEY;
                defaultUnitOrdinal = Constants.DEFAULT_OUTPUT_MASS_UNIT.ordinal();
                break;
            default:
                throw new IllegalArgumentException("Unknown unit type: " + getUnitTypePreference());
        }

        return getStoredPreferenceOrSetDefault(key, defaultUnitOrdinal);
    }

    /**
     * Set the output unit preference, it is a number that represents the ordinal of the output unit.
     *
     * @param unitType            the unit type to set the output unit preference for
     * @param unitPreferenceOrdinal the ordinal of the output unit to set
     */
    public void setOutputUnitPreference(String unitType, int unitPreferenceOrdinal) {
        Preferences.Key<Integer> key;

        switch (unitType) {
            case Constants.DISTANCE_UNIT_TYPE_NAME:
                key = StorageKeys.OUTPUT_DISTANCE_UNIT_STORAGE_KEY;
                break;
            case Constants.MASS_UNIT_TYPE_NAME:
                key = StorageKeys.OUTPUT_MASS_UNIT_STORAGE_KEY;
                break;
            default:
                throw new IllegalArgumentException("Unknown unit type: " + unitType);
        }

        dataStorage.setData(key, unitPreferenceOrdinal);
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

    /**
     * Set the round preference, it is a number that represents the number of decimal places to round the result to.
     * If the round preference is -1, the result will not be rounded.
     *
     * @param roundPreference the number of decimal places to round the result to
     */
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

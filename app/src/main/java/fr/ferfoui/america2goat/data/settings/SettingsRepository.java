package fr.ferfoui.america2goat.data.settings;

import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import fr.ferfoui.america2goat.Constants;
import fr.ferfoui.america2goat.data.store.DataStorage;
import fr.ferfoui.america2goat.unit.UnitManager;
import fr.ferfoui.america2goat.unit.UnitStorage;

/**
 * Repository class for managing settings-related data.
 */
public class SettingsRepository {
    private final DataStorage dataStorage;

    private final MutableLiveData<String> unitTypePreferenceLiveData = new MutableLiveData<>();

    /**
     * Constructor for SettingsRepository.
     *
     * @param dataStorage the data storage instance
     */
    public SettingsRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Get the LiveData for unit type preference.
     *
     * @return LiveData object containing the unit type preference
     */
    public LiveData<String> getUnitTypePreferenceLiveData() {
        return unitTypePreferenceLiveData;
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
        unitTypePreferenceLiveData.setValue(unitType);
    }

    /**
     * Get the input unit preference, it is a number that represents the ordinal of the current input unit.
     *
     * @param unitType the unit type to get the input unit preference for
     * @return the input unit preference
     */
    public int getInputUnitPreference(String unitType) {
        UnitStorage unitStorage = UnitManager.getUnitType(unitType).getUnitStorage();

        Preferences.Key<Integer> key = unitStorage.getInputUnitStorageKey();
        int defaultUnitOrdinal = unitStorage.getDefaultInputUnit().ordinal();

        return getStoredPreferenceOrSetDefault(key, defaultUnitOrdinal);
    }

    /**
     * Set the input unit preference, it is a number that represents the ordinal of the input unit.
     *
     * @param unitType              the unit type to set the input unit preference for
     * @param unitPreferenceOrdinal the ordinal of the input unit to set
     */
    public void setInputUnitPreference(String unitType, int unitPreferenceOrdinal) {
        Preferences.Key<Integer> key = UnitManager.getUnitType(unitType).getUnitStorage().getInputUnitStorageKey();
        dataStorage.setData(key, unitPreferenceOrdinal);
    }

    /**
     * Get the output unit preference, it is a number that represents the ordinal of the current output unit.
     *
     * @param unitType the unit type to get the output unit preference for
     * @return the output unit preference
     */
    public int getOutputUnitPreference(String unitType) {
        UnitStorage unitStorage = UnitManager.getUnitType(unitType).getUnitStorage();

        Preferences.Key<Integer> key = unitStorage.getOutputUnitStorageKey();
        int defaultUnitOrdinal = unitStorage.getDefaultOutputUnit().ordinal();

        return getStoredPreferenceOrSetDefault(key, defaultUnitOrdinal);
    }

    /**
     * Set the output unit preference, it is a number that represents the ordinal of the output unit.
     *
     * @param unitType              the unit type to set the output unit preference for
     * @param unitPreferenceOrdinal the ordinal of the output unit to set
     */
    public void setOutputUnitPreference(String unitType, int unitPreferenceOrdinal) {
        Preferences.Key<Integer> key = UnitManager.getUnitType(unitType).getUnitStorage().getOutputUnitStorageKey();
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

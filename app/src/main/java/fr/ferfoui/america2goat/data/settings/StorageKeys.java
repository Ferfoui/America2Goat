package fr.ferfoui.america2goat.data.settings;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;

/**
 * Class for storing the keys used to access the data store.
 */
public class StorageKeys {

    public static final Preferences.Key<Integer> INPUT_DISTANCE_UNIT_STORAGE_KEY = PreferencesKeys.intKey("input_distance_unit_preference");
    public static final Preferences.Key<Integer> INPUT_MASS_UNIT_STORAGE_KEY = PreferencesKeys.intKey("input_mass_unit_preference");

    public static final Preferences.Key<Integer> OUTPUT_DISTANCE_UNIT_STORAGE_KEY = PreferencesKeys.intKey("output_distance_unit_preference");
    public static final Preferences.Key<Integer> OUTPUT_MASS_UNIT_STORAGE_KEY = PreferencesKeys.intKey("output_mass_unit_preference");

    public static final Preferences.Key<Integer> INPUT_VOLUME_UNIT_STORAGE_KEY = PreferencesKeys.intKey("input_volume_unit_preference");
    public static final Preferences.Key<Integer> OUTPUT_VOLUME_UNIT_STORAGE_KEY = PreferencesKeys.intKey("output_volume_unit_preference");

    public static final Preferences.Key<Integer> ROUND_PREFERENCE_STORAGE_KEY = PreferencesKeys.intKey("round_preference");

    public static final Preferences.Key<String> UNIT_TYPE_STORAGE_KEY = PreferencesKeys.stringKey("unit_type_preference");
}

package fr.ferfoui.america2goat.data.settings;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;

public class StorageKeys {

    public static final Preferences.Key<Integer> INPUT_UNIT_STORAGE_KEY = PreferencesKeys.intKey("input_unit_preference");
    public static final Preferences.Key<Integer> OUTPUT_UNIT_STORAGE_KEY = PreferencesKeys.intKey("output_unit_preference");

    public static final Preferences.Key<Integer> ROUND_PREFERENCE = PreferencesKeys.intKey("round_preference");
}

package fr.ferfoui.america2goat.data.settings;

public class SettingsRepository {
    private final AppSettings appSettings;

    public SettingsRepository(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public void setUnitPreference(int unitPreference) {
        appSettings.setUnitPreference(unitPreference);
    }

    public int getInputUnitPreference() {
        return appSettings.getInputUnitPreference();
    }

    public int getOutputUnitPreference() {
        return appSettings.getOutputUnitPreference();
    }
}

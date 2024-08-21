package fr.ferfoui.america2goat.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.ferfoui.america2goat.data.settings.SettingsRepository;

public class DashboardViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;
    private final MutableLiveData<String> mText;

    public DashboardViewModel(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;

        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
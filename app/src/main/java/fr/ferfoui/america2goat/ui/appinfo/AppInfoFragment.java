package fr.ferfoui.america2goat.ui.appinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.ferfoui.america2goat.databinding.FragmentAppInfoBinding;

public class AppInfoFragment extends Fragment {

    private FragmentAppInfoBinding binding;
    private AppInfoViewModel appInfoViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appInfoViewModel = new ViewModelProvider(this).get(AppInfoViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAppInfoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    // TODO: Use a ClickableSpan to open the app's GitHub page in a browser instead of a TextView

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
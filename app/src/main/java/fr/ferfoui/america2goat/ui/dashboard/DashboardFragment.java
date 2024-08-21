package fr.ferfoui.america2goat.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.ferfoui.america2goat.databinding.FragmentDashboardBinding;
import fr.ferfoui.america2goat.unit.UnitSpinnersConfiguration;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;
    private FragmentDashboardBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Spinner inputUnitSpinner = binding.inputUnitPreferenceSpinner;
        Spinner outputUnitSpinner = binding.outputUnitPreferenceSpinner;

        UnitSpinnersConfiguration.configureSpinners(getContext(), inputUnitSpinner,
                outputUnitSpinner, 1, 2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
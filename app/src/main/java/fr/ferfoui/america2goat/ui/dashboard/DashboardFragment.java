package fr.ferfoui.america2goat.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.ferfoui.america2goat.R;
import fr.ferfoui.america2goat.databinding.FragmentDashboardBinding;
import fr.ferfoui.america2goat.injection.ViewModelFactory;
import fr.ferfoui.america2goat.unit.UnitSpinnersConfiguration;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;
    private FragmentDashboardBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(DashboardViewModel.class);
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
        SeekBar roundPreferenceSeekBar = binding.roundPreferenceSeekBar;


        UnitSpinnersConfiguration.configureSpinners(getContext(), inputUnitSpinner, outputUnitSpinner,
                viewModel.getInputUnitPreference(), viewModel.getOutputUnitPreference(), createOnUnitSelectedListener());


        int barPosition = viewModel.getSeekBarPosition();

        roundPreferenceSeekBar.setProgress(barPosition);
        roundPreferenceSeekBar.setOnSeekBarChangeListener(createSeekBarListener());
        setRoundPreferenceText(barPosition);


        viewModel.getInputUnitLiveData().observe(getViewLifecycleOwner(), inputUnitOrdinal -> {
            if (inputUnitSpinner.getSelectedItemPosition() != inputUnitOrdinal)
                inputUnitSpinner.setSelection(inputUnitOrdinal);
        });

        viewModel.getOutputUnitLiveData().observe(getViewLifecycleOwner(), outputUnitOrdinal -> {
            if (outputUnitSpinner.getSelectedItemPosition() != outputUnitOrdinal)
                outputUnitSpinner.setSelection(outputUnitOrdinal);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private UnitSpinnersConfiguration.OnUnitSelectedListener createOnUnitSelectedListener() {
        return (unitOrdinal, isInput) -> {
            if (isInput) {
                viewModel.setInputUnitPreference(unitOrdinal);
            } else {
                viewModel.setOutputUnitPreference(unitOrdinal);
            }
        };
    }

    private SeekBar.OnSeekBarChangeListener createSeekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.setRoundPreference(progress);

                setRoundPreferenceText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    private void setRoundPreferenceText(int progress) {
        String roundPreferenceText;
        if (progress >= getResources().getInteger(R.integer.round_seek_bar_max)) {
            roundPreferenceText = getString(R.string.no_rounding);
        } else {
            roundPreferenceText = String.valueOf(progress);
        }

        binding.selectedRoundPreferenceText.setText(roundPreferenceText);
    }
}
package fr.ferfoui.america2goat.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.ferfoui.america2goat.databinding.FragmentHomeBinding;
import fr.ferfoui.america2goat.injection.ViewModelFactory;
import fr.ferfoui.america2goat.unit.Unit;
import fr.ferfoui.america2goat.unit.UnitSpinnersConfiguration;

public class HomeFragment extends Fragment {

    int inputSpinnerPosition = 1;
    int outputSpinnerPosition = 2;
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getResult().observe(getViewLifecycleOwner(), result -> binding.outputLengthText.setText(result));

        configureInputEditText();
        configureSpinners();

        viewModel.getInputUnitOrdinal().observe(getViewLifecycleOwner(), inputUnitOrdinal -> {
            if (binding.inputUnitSpinner.getSelectedItemPosition() != inputUnitOrdinal)
                binding.inputUnitSpinner.setSelection(inputUnitOrdinal);
        });

        viewModel.getOutputUnitOrdinal().observe(getViewLifecycleOwner(), outputUnitOrdinal -> {
            if (binding.outputUnitSpinner.getSelectedItemPosition() != outputUnitOrdinal)
                binding.outputUnitSpinner.setSelection(outputUnitOrdinal);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void configureInputEditText() {
        EditText inputEditText = binding.inputLengthEditText;

        addFiltersToInputEditText(inputEditText);

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nothing to do here
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    viewModel.convert(0d);
                    return;
                }
                viewModel.convert(Double.parseDouble(s.toString()));
            }
        });
    }

    private void addFiltersToInputEditText(EditText inputEditText) {
        InputFilter commaDecimalTextFilter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);

                if (Character.isDigit(character) || character == '.')
                    continue;

                if (character == ',')
                    return ".";

                return "";
            }
            return null;
        };

        InputFilter lengthFilter = new InputFilter.LengthFilter(10);
        inputEditText.setFilters(new InputFilter[]{commaDecimalTextFilter, lengthFilter});
    }

    private void configureSpinners() {
        Spinner inputUnitSpinner = binding.inputUnitSpinner;
        Spinner outputUnitSpinner = binding.outputUnitSpinner;

        UnitSpinnersConfiguration.configureSpinners(requireContext(), inputUnitSpinner,
                outputUnitSpinner, inputSpinnerPosition, outputSpinnerPosition);

        inputUnitSpinner.setOnItemSelectedListener(createUnitSpinnerListener(true));
        outputUnitSpinner.setOnItemSelectedListener(createUnitSpinnerListener(false));
    }

    private AdapterView.OnItemSelectedListener createUnitSpinnerListener(boolean isInput) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (Unit.values().length <= position) {
                    throw new IllegalStateException("Unexpected value: " + position);
                }

                if (isInput) {
                    viewModel.setInputUnit(position);
                    inputSpinnerPosition = position;
                } else {
                    viewModel.setOutputUnit(position);
                    outputSpinnerPosition = position;
                }

                changeUnitText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void changeUnitText() {
        Unit[] units = Unit.values();

        binding.inputUnitText.setText(units[inputSpinnerPosition].getResourceNameId());
        binding.outputUnitText.setText(units[outputSpinnerPosition].getResourceNameId());
    }
}
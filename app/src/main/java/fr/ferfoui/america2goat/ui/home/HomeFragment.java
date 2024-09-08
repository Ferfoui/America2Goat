package fr.ferfoui.america2goat.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    int inputSpinnerPosition;
    int outputSpinnerPosition;
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

        binding.swapButton.setOnClickListener(v -> viewModel.swapUnitsAndValues());

        viewModel.getInputUnitOrdinal().observe(getViewLifecycleOwner(), inputUnitOrdinal -> {
            if (binding.inputUnitSpinner.getSelectedItemPosition() != inputUnitOrdinal)
                binding.inputUnitSpinner.setSelection(inputUnitOrdinal);
        });

        viewModel.getOutputUnitOrdinal().observe(getViewLifecycleOwner(), outputUnitOrdinal -> {
            if (binding.outputUnitSpinner.getSelectedItemPosition() != outputUnitOrdinal)
                binding.outputUnitSpinner.setSelection(outputUnitOrdinal);
        });

        viewModel.getChangedInputValue().observe(getViewLifecycleOwner(), changedInputValue -> {
            binding.inputLengthEditText.setText(String.valueOf(changedInputValue));
            binding.inputLengthEditText.setSelection(binding.inputLengthEditText.getText().length());
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

        inputSpinnerPosition = viewModel.getInputUnit().ordinal();
        outputSpinnerPosition = viewModel.getOutputUnit().ordinal();

        UnitSpinnersConfiguration.configureSpinners(requireContext(), inputUnitSpinner, outputUnitSpinner,
                inputSpinnerPosition, outputSpinnerPosition, viewModel.getCurrentUnits(), createOnUnitSelectedListener());
    }

    private UnitSpinnersConfiguration.OnUnitSelectedListener createOnUnitSelectedListener() {
        return (unitOrdinal, isInput) -> {
            if (isInput) {
                viewModel.setInputUnit(unitOrdinal);
                inputSpinnerPosition = unitOrdinal;
            } else {
                viewModel.setOutputUnit(unitOrdinal);
                outputSpinnerPosition = unitOrdinal;
            }

            changeUnitText(viewModel.getCurrentUnits());
        };
    }

    private void changeUnitText(Unit[] currentUsedUnits) {
        binding.inputUnitText.setText(currentUsedUnits[inputSpinnerPosition].getResourceNameId());
        binding.outputUnitText.setText(currentUsedUnits[outputSpinnerPosition].getResourceNameId());
    }
}
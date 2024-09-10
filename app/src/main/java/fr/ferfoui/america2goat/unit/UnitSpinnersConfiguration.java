package fr.ferfoui.america2goat.unit;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnitSpinnersConfiguration {

    public static void configureUnitSpinners(Context context, Spinner inputSpinner, Spinner outputSpinner,
                                             int inputSpinnerPosition, int outputSpinnerPosition,
                                             Unit[] usedUnits, OnUnitSelectedListener listener) {

        List<CharSequence> unitAbbreviations = getUnitAbbreviations(context, usedUnits);

        // The spinners need an ArrayAdapter to display items
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, unitAbbreviations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputSpinner.setAdapter(adapter);
        inputSpinner.setSelection(inputSpinnerPosition);
        outputSpinner.setAdapter(adapter);
        outputSpinner.setSelection(outputSpinnerPosition);

        inputSpinner.setOnItemSelectedListener(createUnitSpinnerListener(listener, true, usedUnits.length));
        outputSpinner.setOnItemSelectedListener(createUnitSpinnerListener(listener, false, usedUnits.length));
    }

    public static void configureUnitTypeSpinner(Context context, Spinner unitTypeSpinner, int unitTypePosition, OnUnitTypeSelectedListener listener) {
        List<CharSequence> unitTypeNames = UnitManager.getUnitTypes().stream()
                .map(UnitType::getResourceNameId)
                .map(context::getString)
                .collect(Collectors.toList());

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, unitTypeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unitTypeSpinner.setAdapter(adapter);
        unitTypeSpinner.setSelection(unitTypePosition);

        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onUnitTypeSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static void refreshUnitSpinners(Context context, Spinner inputSpinner, Spinner outputSpinner,
                                           int inputSpinnerPosition, int outputSpinnerPosition,
                                           Unit[] usedUnits, OnUnitSelectedListener listener) {

        List<CharSequence> unitAbbreviations = getUnitAbbreviations(context, usedUnits);

        inputSpinner.setOnItemSelectedListener(null);
        outputSpinner.setOnItemSelectedListener(null);

        try {
            //noinspection unchecked
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) inputSpinner.getAdapter();
            adapter.clear();
            adapter.addAll(unitAbbreviations);
            adapter.notifyDataSetChanged();

        } catch (ClassCastException e) {
            throw new IllegalStateException("Unexpected adapter type", e);
        }

        inputSpinner.setOnItemSelectedListener(createUnitSpinnerListener(listener, true, usedUnits.length));
        outputSpinner.setOnItemSelectedListener(createUnitSpinnerListener(listener, false, usedUnits.length));

        inputSpinner.setSelection(inputSpinnerPosition);
        outputSpinner.setSelection(outputSpinnerPosition);
    }

    private static List<CharSequence> getUnitAbbreviations(Context context, Unit[] units) {
        return Arrays.stream(units)
                .map(Unit::getResourceAbbreviationId)
                .map(context::getString)
                .collect(Collectors.toList());
    }

    private static AdapterView.OnItemSelectedListener createUnitSpinnerListener(OnUnitSelectedListener listener, boolean isInput, int unitsLength) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (unitsLength <= position) {
                    throw new IllegalStateException("Unexpected value: " + position);
                }

                listener.onUnitSelected(position, isInput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    public interface OnUnitSelectedListener {
        void onUnitSelected(int unitOrdinal, boolean isInput);
    }

    public interface OnUnitTypeSelectedListener {
        void onUnitTypeSelected(int unitTypeOrdinal);
    }

}

package fr.ferfoui.america2goat.unit;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for configuring the spinners used to select units.
 * It provides methods to configure the spinners with the appropriate units and listeners.
 */
public class UnitSpinnersConfiguration {

    /**
     * Configures the input and output spinners with the given units and positions.
     * The spinners will be populated with the abbreviations of the units,
     * they will be set to the given positions,
     * and they will have listeners to notify the listener when a unit is selected.
     *
     * @param context               the context of the fragment
     * @param inputSpinner          the input spinner
     * @param outputSpinner         the output spinner
     * @param inputSpinnerPosition  the initial position of the input spinner
     * @param outputSpinnerPosition the initial position of the output spinner
     * @param usedUnits             the units to use in the spinners
     * @param listener              the listener to notify when a unit is selected
     */
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

    /**
     * Configures the unit type spinner with the given unit type and listener.
     * The spinner will be populated with the names of the unit types,
     * it will be set to the given unit type,
     * and it will have a listener to notify the listener when a unit type is selected.
     *
     * @param context         the context of the fragment
     * @param unitTypeSpinner the unit type spinner
     * @param initialUnitType the initial unit type
     * @param listener        the listener to notify when a unit type is selected
     */
    public static void configureUnitTypeSpinner(Context context, Spinner unitTypeSpinner, String initialUnitType, OnUnitTypeSelectedListener listener) {
        List<CharSequence> unitTypeResourceNames = UnitManager.getUnitTypes().stream()
                .map(UnitType::getResourceNameId)
                .map(context::getString)
                .collect(Collectors.toList());

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, unitTypeResourceNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> unitTypeNames = UnitManager.getUnitTypeNames();

        unitTypeSpinner.setAdapter(adapter);
        unitTypeSpinner.setSelection(unitTypeNames.indexOf(initialUnitType));

        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= unitTypeNames.size()) {
                    throw new IllegalStateException("Unexpected value: " + position);
                }
                listener.onUnitTypeSelected(unitTypeNames.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Refreshes the input and output spinners with the given units and positions.
     * The spinners will be repopulated with the abbreviations of the new units,
     * and they will be set to the given positions.
     *
     * @param context               the context of the fragment
     * @param inputSpinner          the input spinner
     * @param outputSpinner         the output spinner
     * @param inputSpinnerPosition  the initial position of the input spinner
     * @param outputSpinnerPosition the initial position of the output spinner
     * @param usedUnits             the units to use in the spinners
     * @param listener              the listener to notify when a unit is selected
     * @throws IllegalStateException if the adapter of the given spinners is not of the expected type
     * @see #configureUnitSpinners(Context, Spinner, Spinner, int, int, Unit[], OnUnitSelectedListener)
     */
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

    /**
     * Returns a list of the abbreviations of the given units.
     *
     * @param context the context of the fragment
     * @param units   the units
     * @return the list of abbreviations
     */
    private static List<CharSequence> getUnitAbbreviations(Context context, Unit[] units) {
        return Arrays.stream(units)
                .map(Unit::getResourceAbbreviationId)
                .map(context::getString)
                .collect(Collectors.toList());
    }

    /**
     * Creates a listener for the unit spinners.
     *
     * @param listener    the listener to notify when a unit is selected
     * @param isInput     true if the selected unit is the input spinner, false if it is the output spinner
     * @param unitsLength the number of units
     * @return the listener
     */
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

    /**
     * Listener to notify when a unit is selected.
     */
    public interface OnUnitSelectedListener {
        /**
         * Notifies the listener that a unit has been selected.
         *
         * @param unitOrdinal the ordinal of the selected unit
         * @param isInput     true if the selected unit is the input spinner, false if it is the output spinner
         */
        void onUnitSelected(int unitOrdinal, boolean isInput);
    }

    /**
     * Listener to notify when a unit type is selected.
     */
    public interface OnUnitTypeSelectedListener {
        /**
         * Notifies the listener that a unit type has been selected.
         *
         * @param unitType the selected unit type
         */
        void onUnitTypeSelected(String unitType);
    }

}

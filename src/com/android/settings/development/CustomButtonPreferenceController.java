package com.android.settings.development;

import android.content.Context;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

public class CustomButtonPreferenceController extends DeveloperOptionsPreferenceController implements
        Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    private static final String CUSTOM_TOGGLE_KEY = "custom_toggle";

    public CustomButtonPreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return CUSTOM_TOGGLE_KEY;
    }

    @Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		boolean isEnabled = (Boolean) newValue;
		updateSummary(preference, isEnabled);
		// Handle any other logic for the toggle here
		return true;
	}

	private void updateSummary(Preference preference, boolean isEnabled) {
		String staticDescription = "This is a custom toggle. ";
		String dynamicDescription = isEnabled ? "Currently enabled." : "Currently disabled.";
		preference.setSummary(staticDescription + dynamicDescription);
	}

	// You should also set the initial state of the summary when the preference is displayed
	@Override
	public void updateState(Preference preference) {
		boolean isEnabled = ((SwitchPreference) preference).isChecked();
		updateSummary(preference, isEnabled);
		
		// Set visibility of the Button
        boolean shouldShowButton = false;/* your condition, e.g. false to hide it */
        preference.setVisible(shouldShowButton);
	}

    @Override
    protected void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        ((SwitchPreference) mPreference).setChecked(false);
    }
}


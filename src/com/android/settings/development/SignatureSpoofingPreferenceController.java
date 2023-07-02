package com.android.settings.development;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

public class SignatureSpoofingPreferenceController extends DeveloperOptionsPreferenceController implements
        Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    private static final String SIGNATURE_SPOOFING_KEY = "signature_spoofing";

    public SignatureSpoofingPreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return SIGNATURE_SPOOFING_KEY;
    }

    @Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String value = (String) newValue;
		
		// In order to be used in others files
		android.os.SystemProperties.set("sys.signature_spoofing", value);
		
		updateSummary(preference, value);
		return true;
	}

	private void updateSummary(Preference preference, String value) {
		String dynamicDescription = "";

		if ("Off".equals(value)) {
		    dynamicDescription = "Current state : Off.\n\nApps can have the Signature Spoofing permission but every attempt will be denied.";
		} else if ("On".equals(value)) {
		    dynamicDescription = "Current state : On.\n\nApp that have the Signature Spoofing permission can change their signature without restriction.";
		} else {
		    dynamicDescription = "Current state : Restricted.\n\nApp that have the Signature Spoofing permission will only be allow to spoof the hard coded Google signature. Other attempts will be denied."; 
		}
		
		String staticDescriptionEnd = "Warning : Editing this setting does NOT affect older requests of signature spoofing.";
		
		String description = dynamicDescription + "\n\n" + staticDescriptionEnd;
		preference.setSummary(description);
	}

	@Override
	public void updateState(Preference preference) {
		ListPreference listPreference = (ListPreference) preference;
		String value = listPreference.getValue();
		if (value == null) {
			// Check if we have already set a value before & Signature Spoofing is "Off" by default if not
		    value = android.os.SystemProperties.get("sys.signature_spoofing", "Off");
		    listPreference.setValue(value);
		}
		updateSummary(preference, value);
	}

    @Override
    protected void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        ((ListPreference) mPreference).setValue(null);
    }
}


package ntou.hearingaid.hearingaid;

import ntou.hearingaid.parameter.Parameter;
import ntou.hearingaid.sound.SoundControl;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;


public class PrefSetting extends PreferenceActivity {

	private ListPreference AudioSource;
	private ListPreference DefaultMode;
	private PreferenceScreen FilterBank;
	private PreferenceScreen Gain;
	private SoundControl control = SoundControl.getSoundControl();
	public SharedPreferences setting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_config);
		
		AudioSource = (ListPreference)findPreference("AudioSource");
		DefaultMode = (ListPreference)findPreference("DefaultMode");
		FilterBank = (PreferenceScreen)findPreference("FilterBank");
		Gain = (PreferenceScreen)findPreference("Gain");
		
		DefaultMode.setOnPreferenceChangeListener(new onPreferenceChangeListener());
		
		//Log.e("DEBUG", DefaultMode.getValue());
		
		//讀取設定檔預設值
		setting = getSharedPreferences(Parameter.PreferencesStr, 0);
		if(setting.contains("DefaultMode"))
		{
			String res = setting.getString("DefaultMode", "5");
			if(res.equals("5"))
			{
				FilterBank.setEnabled(true);
				Gain.setEnabled(true);
			}
			else
			{
				FilterBank.setEnabled(false);
				Gain.setEnabled(false);
			}
			
		}
		
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// TODO Auto-generated method stub
		if(preference.getKey().equals("FilterBank"))
		{
			Intent intent = new Intent();
			intent.setClass(PrefSetting.this, FilterBankSetting.class);
			startActivity(intent);
		}
		else if(preference.getKey().equals("Gain"))
		{
			Intent intent = new Intent();
			intent.setClass(PrefSetting.this, GainSetting.class);
			startActivity(intent);
		}
		else if(preference.getKey().equals("BackupRestoreScreen"))
		{
			Intent intent = new Intent();
			intent.setClass(PrefSetting.this, BackupRestore.class);
			startActivity(intent);
		}
		
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	private class onPreferenceChangeListener implements OnPreferenceChangeListener
	{

		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// TODO Auto-generated method stub
			if(preference.getKey().equals("DefaultMode"))
			{
				if(newValue.equals("5"))
				{
					FilterBank.setEnabled(true);
					Gain.setEnabled(true);
				}
				else
				{
					FilterBank.setEnabled(false);
					Gain.setEnabled(false);
				}
				DefaultMode.setValue(newValue.toString());
				//Toast.makeText(PrefSetting.this, newValue.toString(), 5).show();
			}
			
			if(preference.getKey().equals("AudioSource"))
			{
				if(newValue.equals("0"))
				{
					control.setMicInput(false);
				}
				else
				{
					control.setMicInput(true);
				}
				AudioSource.setValue(newValue.toString());
			}
			return false;
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		SharedPreferences.Editor edit = setting.edit();
		
		if(setting.getString("DefaultMode", "-1").equals("2"))	//低頻
		{
			edit.putInt("FilterBankNumber", 3);
			String FilterLow = "FilterLow"+Integer.toString(1);
			String FilterHi = "FilterHi"+Integer.toString(1);
			String Gain40 = "Gain40db"+String.valueOf(1);
			String Gain60 = "Gain60db"+String.valueOf(1);
			String Gain80 = "Gain80db"+String.valueOf(1);
			String Gain40R = "Gain40db"+String.valueOf(1)+"R";
			String Gain60R = "Gain60db"+String.valueOf(1)+"R";
			String Gain80R = "Gain80db"+String.valueOf(1)+"R";
			edit.putInt(FilterLow, 176);
			edit.putInt(FilterHi, 353);
			edit.putInt(Gain40, 8);
			edit.putInt(Gain60, 15);
			edit.putInt(Gain80, 5);
			edit.putInt(Gain40R, 8);
			edit.putInt(Gain60R, 15);
			edit.putInt(Gain80R, 5);
			
			FilterLow = "FilterLow"+Integer.toString(2);
			FilterHi = "FilterHi"+Integer.toString(2);
			Gain40 = "Gain40db"+String.valueOf(2);
			Gain60 = "Gain60db"+String.valueOf(2);
			Gain80 = "Gain80db"+String.valueOf(2);
			Gain40R = "Gain40db"+String.valueOf(2)+"R";
			Gain60R = "Gain60db"+String.valueOf(2)+"R";
			Gain80R = "Gain80db"+String.valueOf(2)+"R";
			edit.putInt(FilterLow, 353);
			edit.putInt(FilterHi, 707);
			edit.putInt(Gain40, 5);
			edit.putInt(Gain60, 15);
			edit.putInt(Gain80, 6);
			edit.putInt(Gain40R, 5);
			edit.putInt(Gain60R, 15);
			edit.putInt(Gain80R, 6);
			
			FilterLow = "FilterLow"+Integer.toString(3);
			FilterHi = "FilterHi"+Integer.toString(3);
			Gain40 = "Gain40db"+String.valueOf(3);
			Gain60 = "Gain60db"+String.valueOf(3);
			Gain80 = "Gain80db"+String.valueOf(3);
			Gain40R = "Gain40db"+String.valueOf(3)+"R";
			Gain60R = "Gain60db"+String.valueOf(3)+"R";
			Gain80R = "Gain80db"+String.valueOf(3)+"R";
			edit.putInt(FilterLow, 707);
			edit.putInt(FilterHi, 3500);
			edit.putInt(Gain40, 8);
			edit.putInt(Gain60, 15);
			edit.putInt(Gain80, 8);
			edit.putInt(Gain40R, 8);
			edit.putInt(Gain60R, 15);
			edit.putInt(Gain80R, 8);
			/*
			FilterLow = "FilterLow"+Integer.toString(4);
			FilterHi = "FilterHi"+Integer.toString(4);
			Gain40 = "Gain40db"+String.valueOf(4);
			Gain60 = "Gain60db"+String.valueOf(4);
			Gain80 = "Gain80db"+String.valueOf(4);
			edit.putInt(FilterLow, 707);
			edit.putInt(FilterHi, 3500);
			edit.putInt(Gain40, 0);
			edit.putInt(Gain60, 0);
			edit.putInt(Gain80, 0);
			
			*/
		}
		else if(setting.getString("DefaultMode", "-1").equals("3"))	//高頻
		{
			edit.putInt("FilterBankNumber", 4);
			String FilterLow = "FilterLow"+Integer.toString(1);
			String FilterHi = "FilterHi"+Integer.toString(1);
			String Gain40 = "Gain40db"+String.valueOf(1);
			String Gain60 = "Gain60db"+String.valueOf(1);
			String Gain80 = "Gain80db"+String.valueOf(1);
			String Gain40R = "Gain40db"+String.valueOf(1)+"R";
			String Gain60R = "Gain60db"+String.valueOf(1)+"R";
			String Gain80R = "Gain80db"+String.valueOf(1)+"R";
			edit.putInt(FilterLow, 88);
			edit.putInt(FilterHi, 707);
			edit.putInt(Gain40, 4);
			edit.putInt(Gain60, 13);
			edit.putInt(Gain80, 5);
			edit.putInt(Gain40R, 4);
			edit.putInt(Gain60R, 13);
			edit.putInt(Gain80R, 5);
			
			FilterLow = "FilterLow"+Integer.toString(2);
			FilterHi = "FilterHi"+Integer.toString(2);
			Gain40 = "Gain40db"+String.valueOf(2);
			Gain60 = "Gain60db"+String.valueOf(2);
			Gain80 = "Gain80db"+String.valueOf(2);
			Gain40R = "Gain40db"+String.valueOf(2)+"R";
			Gain60R = "Gain60db"+String.valueOf(2)+"R";
			Gain80R = "Gain80db"+String.valueOf(2)+"R";
			edit.putInt(FilterLow, 707);
			edit.putInt(FilterHi, 1414);
			edit.putInt(Gain40, 2);
			edit.putInt(Gain60, 8);
			edit.putInt(Gain80, 4);
			edit.putInt(Gain40R, 2);
			edit.putInt(Gain60R, 8);
			edit.putInt(Gain80R, 4);
			
			FilterLow = "FilterLow"+Integer.toString(3);
			FilterHi = "FilterHi"+Integer.toString(3);
			Gain40 = "Gain40db"+String.valueOf(3);
			Gain60 = "Gain60db"+String.valueOf(3);
			Gain80 = "Gain80db"+String.valueOf(3);
			Gain40R = "Gain40db"+String.valueOf(3)+"R";
			Gain60R = "Gain60db"+String.valueOf(3)+"R";
			Gain80R = "Gain80db"+String.valueOf(3)+"R";
			edit.putInt(FilterLow, 1414);
			edit.putInt(FilterHi, 2828);
			edit.putInt(Gain40, 2);
			edit.putInt(Gain60, 5);
			edit.putInt(Gain80, 3);
			edit.putInt(Gain40R, 2);
			edit.putInt(Gain60R, 5);
			edit.putInt(Gain80R, 3);
			
			FilterLow = "FilterLow"+Integer.toString(4);
			FilterHi = "FilterHi"+Integer.toString(4);
			Gain40 = "Gain40db"+String.valueOf(4);
			Gain60 = "Gain60db"+String.valueOf(4);
			Gain80 = "Gain80db"+String.valueOf(4);
			Gain40R = "Gain40db"+String.valueOf(4)+"R";
			Gain60R = "Gain60db"+String.valueOf(4)+"R";
			Gain80R = "Gain80db"+String.valueOf(4)+"R";
			edit.putInt(FilterLow, 2828);
			edit.putInt(FilterHi, 3500);
			edit.putInt(Gain40, 3);
			edit.putInt(Gain60, 8);
			edit.putInt(Gain80, 2);
			edit.putInt(Gain40R, 3);
			edit.putInt(Gain60R, 8);
			edit.putInt(Gain80R, 2);
		}
		else if(setting.getString("DefaultMode", "-1").equals("4"))	//混合性重聽
		{
			edit.putInt("FilterBankNumber", 5);
			String FilterLow = "FilterLow"+Integer.toString(1);
			String FilterHi = "FilterHi"+Integer.toString(1);
			String Gain40 = "Gain40db"+String.valueOf(1);
			String Gain60 = "Gain60db"+String.valueOf(1);
			String Gain80 = "Gain80db"+String.valueOf(1);
			String Gain40R = "Gain40db"+String.valueOf(1)+"R";
			String Gain60R = "Gain60db"+String.valueOf(1)+"R";
			String Gain80R = "Gain80db"+String.valueOf(1)+"R";
			edit.putInt(FilterLow, 176);
			edit.putInt(FilterHi, 353);
			edit.putInt(Gain40, 2);
			edit.putInt(Gain60, 7);
			edit.putInt(Gain80, -2);
			edit.putInt(Gain40R, 2);
			edit.putInt(Gain60R, 7);
			edit.putInt(Gain80R, -2);
			
			FilterLow = "FilterLow"+Integer.toString(2);
			FilterHi = "FilterHi"+Integer.toString(2);
			Gain40 = "Gain40db"+String.valueOf(2);
			Gain60 = "Gain60db"+String.valueOf(2);
			Gain80 = "Gain80db"+String.valueOf(2);
			Gain40R = "Gain40db"+String.valueOf(2)+"R";
			Gain60R = "Gain60db"+String.valueOf(2)+"R";
			Gain80R = "Gain80db"+String.valueOf(2)+"R";
			edit.putInt(FilterLow, 353);
			edit.putInt(FilterHi, 707);
			edit.putInt(Gain40, 5);
			edit.putInt(Gain60, 10);
			edit.putInt(Gain80, 7);
			edit.putInt(Gain40R, 5);
			edit.putInt(Gain60R, 10);
			edit.putInt(Gain80R, 7);
			
			FilterLow = "FilterLow"+Integer.toString(3);
			FilterHi = "FilterHi"+Integer.toString(3);
			Gain40 = "Gain40db"+String.valueOf(3);
			Gain60 = "Gain60db"+String.valueOf(3);
			Gain80 = "Gain80db"+String.valueOf(3);
			Gain40R = "Gain40db"+String.valueOf(3)+"R";
			Gain60R = "Gain60db"+String.valueOf(3)+"R";
			Gain80R = "Gain80db"+String.valueOf(3)+"R";
			edit.putInt(FilterLow, 707);
			edit.putInt(FilterHi, 1414);
			edit.putInt(Gain40, 3);
			edit.putInt(Gain60, 8);
			edit.putInt(Gain80, 2);
			edit.putInt(Gain40R, 3);
			edit.putInt(Gain60R, 8);
			edit.putInt(Gain80R, 2);
			
			FilterLow = "FilterLow"+Integer.toString(4);
			FilterHi = "FilterHi"+Integer.toString(4);
			Gain40 = "Gain40db"+String.valueOf(4);
			Gain60 = "Gain60db"+String.valueOf(4);
			Gain80 = "Gain80db"+String.valueOf(4);
			Gain40R = "Gain40db"+String.valueOf(4)+"R";
			Gain60R = "Gain60db"+String.valueOf(4)+"R";
			Gain80R = "Gain80db"+String.valueOf(4)+"R";
			edit.putInt(FilterLow, 1414);
			edit.putInt(FilterHi, 2828);
			edit.putInt(Gain40, 6);
			edit.putInt(Gain60, 13);
			edit.putInt(Gain80, 8);
			edit.putInt(Gain40R, 6);
			edit.putInt(Gain60R, 13);
			edit.putInt(Gain80R, 8);
			
			FilterLow = "FilterLow"+Integer.toString(5);
			FilterHi = "FilterHi"+Integer.toString(5);
			Gain40 = "Gain40db"+String.valueOf(5);
			Gain60 = "Gain60db"+String.valueOf(5);
			Gain80 = "Gain80db"+String.valueOf(5);
			Gain40R = "Gain40db"+String.valueOf(5)+"R";
			Gain60R = "Gain60db"+String.valueOf(5)+"R";
			Gain80R = "Gain80db"+String.valueOf(5)+"R";
			edit.putInt(FilterLow, 2828);
			edit.putInt(FilterHi, 3500);
			edit.putInt(Gain40, 2);
			edit.putInt(Gain60, 7);
			edit.putInt(Gain80, 4);
			edit.putInt(Gain40R, 2);
			edit.putInt(Gain60R, 7);
			edit.putInt(Gain80R, 4);
		}
		else if(setting.getString("DefaultMode", "-1").equals("5"))	//自訂
		{
			if(!(setting.contains("FilterBankNumber")))	//判斷是否有預設
			{
				if(setting.getInt("FilterBankNumber", 0)==0)
				{
					//如無設定檔則初始化
					edit.putInt("FilterBankNumber", 5);
					String FilterLow = "FilterLow"+Integer.toString(1);
					String FilterHi = "FilterHi"+Integer.toString(1);
					String Gain40 = "Gain40db"+String.valueOf(1);
					String Gain60 = "Gain60db"+String.valueOf(1);
					String Gain80 = "Gain80db"+String.valueOf(1);
					String Gain40R = "Gain40db"+String.valueOf(1)+"R";
					String Gain60R = "Gain60db"+String.valueOf(1)+"R";
					String Gain80R = "Gain80db"+String.valueOf(1)+"R";
					edit.putInt(FilterLow, 176);
					edit.putInt(FilterHi, 353);
					edit.putInt(Gain40, 0);
					edit.putInt(Gain60, 0);
					edit.putInt(Gain80, 0);
					edit.putInt(Gain40R, 0);
					edit.putInt(Gain60R, 0);
					edit.putInt(Gain80R, 0);
					
					FilterLow = "FilterLow"+Integer.toString(2);
					FilterHi = "FilterHi"+Integer.toString(2);
					Gain40 = "Gain40db"+String.valueOf(2);
					Gain60 = "Gain60db"+String.valueOf(2);
					Gain80 = "Gain80db"+String.valueOf(2);
					Gain40R = "Gain40db"+String.valueOf(2)+"R";
					Gain60R = "Gain60db"+String.valueOf(2)+"R";
					Gain80R = "Gain80db"+String.valueOf(2)+"R";
					edit.putInt(FilterLow, 353);
					edit.putInt(FilterHi, 707);
					edit.putInt(Gain40, 0);
					edit.putInt(Gain60, 0);
					edit.putInt(Gain80, 0);
					edit.putInt(Gain40R, 0);
					edit.putInt(Gain60R, 0);
					edit.putInt(Gain80R, 0);
					
					FilterLow = "FilterLow"+Integer.toString(3);
					FilterHi = "FilterHi"+Integer.toString(3);
					Gain40 = "Gain40db"+String.valueOf(3);
					Gain60 = "Gain60db"+String.valueOf(3);
					Gain80 = "Gain80db"+String.valueOf(3);
					Gain40R = "Gain40db"+String.valueOf(3)+"R";
					Gain60R = "Gain60db"+String.valueOf(3)+"R";
					Gain80R = "Gain80db"+String.valueOf(3)+"R";
					edit.putInt(FilterLow, 707);
					edit.putInt(FilterHi, 1414);
					edit.putInt(Gain40, 0);
					edit.putInt(Gain60, 0);
					edit.putInt(Gain80, 0);
					edit.putInt(Gain40R, 0);
					edit.putInt(Gain60R, 0);
					edit.putInt(Gain80R, 0);
					
					FilterLow = "FilterLow"+Integer.toString(4);
					FilterHi = "FilterHi"+Integer.toString(4);
					Gain40 = "Gain40db"+String.valueOf(4);
					Gain60 = "Gain60db"+String.valueOf(4);
					Gain80 = "Gain80db"+String.valueOf(4);
					Gain40R = "Gain40db"+String.valueOf(4)+"R";
					Gain60R = "Gain60db"+String.valueOf(4)+"R";
					Gain80R = "Gain80db"+String.valueOf(4)+"R";
					edit.putInt(FilterLow, 1414);
					edit.putInt(FilterHi, 2828);
					edit.putInt(Gain40, 0);
					edit.putInt(Gain60, 0);
					edit.putInt(Gain80, 0);
					edit.putInt(Gain40R, 0);
					edit.putInt(Gain60R, 0);
					edit.putInt(Gain80R, 0);
					
					FilterLow = "FilterLow"+Integer.toString(5);
					FilterHi = "FilterHi"+Integer.toString(5);
					Gain40 = "Gain40db"+String.valueOf(5);
					Gain60 = "Gain60db"+String.valueOf(5);
					Gain80 = "Gain80db"+String.valueOf(5);
					Gain40R = "Gain40db"+String.valueOf(5)+"R";
					Gain60R = "Gain60db"+String.valueOf(5)+"R";
					Gain80R = "Gain80db"+String.valueOf(5)+"R";
					edit.putInt(FilterLow, 2828);
					edit.putInt(FilterHi, 3500);
					edit.putInt(Gain40, 0);
					edit.putInt(Gain60, 0);
					edit.putInt(Gain80, 0);
					edit.putInt(Gain40R, 0);
					edit.putInt(Gain60R, 0);
					edit.putInt(Gain80R, 0);
				}
			}
			
		}
		edit.commit();
		Toast.makeText(PrefSetting.this, "設定完請重新啟動!!", 5).show();
		
		super.onDestroy();
	}
}

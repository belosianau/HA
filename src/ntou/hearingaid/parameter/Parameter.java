package ntou.hearingaid.parameter;

import android.content.SharedPreferences;
import android.os.Build;
/*
 * APP相關參數
 */
public class Parameter {
	//內建設定檔名稱
	public static String PreferencesStr = "ntou.hearingaid.hearingaid_preferences";	//參數設定檔
	//軟體版本
	public static String SoftVersion = Build.VERSION.RELEASE;
	/*
	 * 效能計算用
	 */
	public static long Mictime = 0;
	public static long FilterBanktime = 0;
	public static long SpeakerTime = 0;
	//內建設定檔類別
	public static SharedPreferences pref = null;
	
}

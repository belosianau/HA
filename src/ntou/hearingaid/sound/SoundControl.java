package ntou.hearingaid.sound;


import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioManager;
import android.os.Build;

public class SoundControl extends Activity {
	private AudioManager audioManager = null;
	private boolean isMicInput = true;	//true:內建耳機輸入 false:藍芽輸入
	public static SoundControl soundControl = null;
	//記錄起始狀態
	private boolean isWiredHeadsetOn = false;
	private boolean isBluetoothA2DPOn = false;
	
	private SoundControl(AudioManager _audioManager)
	{
		this.audioManager = _audioManager;
		//audioManager.setMode(AudioManager.MODE_IN_CALL);
		audioManager.setMode(AudioManager.MODE_NORMAL);
		
		isWiredHeadsetOn = audioManager.isWiredHeadsetOn();
		isBluetoothA2DPOn = audioManager.isBluetoothA2dpOn();
		
	}
	
	public static SoundControl getSoundControl(AudioManager _audioManager)
	{
		if(SoundControl.soundControl==null)
		{
			soundControl = new SoundControl(_audioManager);
		}
		return soundControl;
	}
	
	public static SoundControl getSoundControl()
	{
		return soundControl;
	}
	
	/*
	 * 檢查是否符合軟體需求
	 * return 取得耳機或藍牙耳機狀態
	 */
	public boolean CheckSoundDeviceState()
	{
		//檢查耳機狀態
		if(audioManager.isWiredHeadsetOn())
		{
			return true;
		}
		
		//檢查藍芽狀態
		if(audioManager.isBluetoothA2dpOn())
		{
			return true;
		}
		
		return false;
	}


	/*
	 * 設定輸入裝置
	 * state - 設定輸入來源 true內建麥克風 false藍牙麥克風
	 * return 設定是否成功
	 */
	public boolean setMicInput(boolean state)
	{
		if(state)
		{
			//使用麥克風輸入
			isMicInput = true;
			audioManager.setBluetoothScoOn(false);
			audioManager.stopBluetoothSco();
			return true;
		}
		else
		{
			//使用藍芽麥克風
			//if(audioManager.isBluetoothA2dpOn())
			if(audioManager.isBluetoothA2dpOn())
			{
				//判斷A2DP是否啟用
				isMicInput = false;
				audioManager.setBluetoothScoOn(true);
				audioManager.startBluetoothSco();
				return true;
			}
		}
		return false;
	}
	//取得目前輸入來源
	public boolean getMicInput()
	{
		return isMicInput;
	}
	
	public void closeSoundControl()
	{
		audioManager.setBluetoothA2dpOn(isBluetoothA2DPOn);
		audioManager.setWiredHeadsetOn(isWiredHeadsetOn);
		audioManager.setBluetoothScoOn(false);
		audioManager.stopBluetoothSco();
	}
}

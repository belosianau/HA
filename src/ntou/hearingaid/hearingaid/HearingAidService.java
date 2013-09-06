package ntou.hearingaid.hearingaid;


import ntou.hearingaid.dsp.FilterBank;
import ntou.hearingaid.dsp.Gain;
import ntou.hearingaid.dsp.FilterBank.OnFilterBankListener;
import ntou.hearingaid.dsp.Gain.OnGainListener;
import ntou.hearingaid.parameter.Parameter;
import ntou.hearingaid.peformance.PerformanceParameter;
import ntou.hearingaid.sound.Microphone;
import ntou.hearingaid.sound.SoundParameter;
import ntou.hearingaid.sound.Speaker;
import ntou.hearingaid.sound.Microphone.OnMicrophoneListener;
import android.app.Service;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

/*
 * 助聽器主要服務
 * 當按下啟動時
 * 則將此服務啟動
 */

public class HearingAidService extends Service {

	public static boolean isService = false;
	public static boolean isPauseByHeadsetUnplug = false;
	private Microphone mic;
	private Speaker speaker;
	private FilterBank filterBank;
	private SharedPreferences setting;
		
	//private Gain gain1 = new Gain();
	
	public HearingAidService()
	{
		
		
		
		/*gain1.setOnGainListener(new OnGainListener() {
			
			public void OnSuccess(short[] data) {
				// TODO Auto-generated method stub
				speaker.AddSignals(data);
			}
		});*/
		
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//依序將執行緒啟動
		isService = true;
		mic.open();
		filterBank.open();
		//gain1.open();
		speaker.open();
		PerformanceParameter.SystemStartTime = System.currentTimeMillis();
		super.onStart(intent, startId);
		
		//Toast.makeText(HearingAidService.this, "Pass", 10).show();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//依序將執行緒中斷
		isService = false;
		mic.close();
		filterBank.close();
		//gain1.close();
		speaker.close();
		super.onDestroy();
		
		
		//Toast.makeText(HearingAidService.this, "Pass2", 10).show();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setting = PreferenceManager.getDefaultSharedPreferences(this);
		mic = new Microphone();
		if(SoundParameter.frequency==8000)
			speaker = new Speaker();
		else
			speaker = new Speaker(SoundParameter.frequency);
		
		filterBank = new FilterBank(setting);
		
		mic.setOnMicrophoneListener(new OnMicrophoneListener() {
			
			public void OnRec(short[] data) {
				// TODO Auto-generated method stub
				filterBank.AddSignals(data);	//mic處理完訊號送入filterBank
				//eq.AddSignals(data);
				
			}
		});
		
		
		filterBank.setOnFilterBankListener(new OnFilterBankListener() {
			
			public void OnSuccess(short[] data) {
				// TODO Auto-generated method stub
				//gain1.AddSignals(data);
				speaker.AddSignals(data);	//filterBank處理完訊號送至speaker
			}
		});
	}

}

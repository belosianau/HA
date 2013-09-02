package ntou.hearingaid.hearingaid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/*
 * 負責監聽手機內部各項廣播
 * 如 耳機插入拔除
 * 電話call in等
 */

public class HeadsetPlugReciver extends BroadcastReceiver {

	public final int HeadsetPlugin = 2;
	public final int HeadsetUnplugin = 0;
	
	private static final int STATE_CONNECTED = 0x00000002; 
	private static final int STATE_DISCONNECTED  = 0x00000000;  
	private static final String EXTRA_STATE = "android.bluetooth.headset.extra.STATE";
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		if(arg1.getAction().equals("android.intent.action.HEADSET_PLUG"))
		{
			//廣播判斷耳機移除需先判斷是否在服務狀態 在服務狀態時需將isPauseByHeadsetUnplug改變 以利接回耳機後啟用
			if(arg1.getIntExtra("state", -1)== HeadsetUnplugin && HearingAidService.isService)	
			{
				HearingAidService.isPauseByHeadsetUnplug = true;	//做為判斷是否為廣播中斷
				arg0.stopService(new Intent("ntou.hearingaid.hearingaid.START_HearingAid"));	//停止服務
			}
			else
			{
				if(HearingAidService.isPauseByHeadsetUnplug)	//如果為廣播中斷服務時 
				{
					HearingAidService.isPauseByHeadsetUnplug = false;	//將其中斷回復
					arg0.startService(new Intent("ntou.hearingaid.hearingaid.START_HearingAid"));	//重新啟動服務
				}
			}
			//Log.d("DEBUG", String.valueOf(arg1.getIntExtra("state", 0)));
		}
		else if(arg1.getAction().equals("android.bluetooth.headset.action.STATE_CHANGED"))	//判斷藍芽狀態是否改變
		{
			
			if(arg1.getIntExtra(EXTRA_STATE, STATE_DISCONNECTED)== STATE_DISCONNECTED && HearingAidService.isService)	
			{
				HearingAidService.isPauseByHeadsetUnplug = true;	//做為判斷是否為廣播中斷
				arg0.stopService(new Intent("ntou.hearingaid.hearingaid.START_HearingAid"));	//停止服務
			}
			else
			{
				if(HearingAidService.isPauseByHeadsetUnplug)	//如果為廣播中斷服務時 
				{
					HearingAidService.isPauseByHeadsetUnplug = false;	//將其中斷回復
					arg0.startService(new Intent("ntou.hearingaid.hearingaid.START_HearingAid"));	//重新啟動服務
				}
			}
		}
	}

}

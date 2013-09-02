package ntou.hearingaid.hearingaid;

import ntou.hearingaid.customerview.FilterView;
import ntou.hearingaid.customerview.GainView;
import ntou.hearingaid.parameter.Parameter;
import ntou.hearingaid.sound.SoundParameter;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GainSetting extends Activity {

	private LinearLayout ll;
	private GainView[] gain;
	private GainView[] gainR;
	private SharedPreferences setting;
	private SharedPreferences.Editor edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gain_setting);
		ll = (LinearLayout)findViewById(R.id.ScrollLinearLayoutView);
		setting = getSharedPreferences(Parameter.PreferencesStr, 0);
		edit = setting.edit();
		if(setting.contains("FilterBankNumber"))
		{
			int GainNumber = setting.getInt("FilterBankNumber", -1);
			if(GainNumber!=-1)
			{
				
				gain = new GainView[GainNumber];
				for(int index = 0; index < GainNumber; index++)
				{
					gain[index] = new GainView(GainSetting.this);
					if(SoundParameter.frequency==8000)
						gain[index].setGainNo(index+1);
					else
						gain[index].setGainNo(index+1,0);
					ll.addView(gain[index]);
				}
				if(SoundParameter.frequency==16000)
				{
 					gainR = new GainView[GainNumber];
 					for(int index = 0; index < GainNumber; index++)
 					{
 						gainR[index] = new GainView(GainSetting.this);
 						gainR[index].setGainNo(index+1,1);
 						ll.addView(gainR[index]);
 					}
				}
				
				
				//將增益寫入
				for(int index = 0; index < GainNumber; index++)
				{
					String db40 = "Gain40db"+String.valueOf(index+1);
					String db60 = "Gain60db"+String.valueOf(index+1);
					String db80 = "Gain80db"+String.valueOf(index+1);
					String db40R = "Gain40db"+String.valueOf(index+1)+"R";
					String db60R = "Gain60db"+String.valueOf(index+1)+"R";
					String db80R = "Gain80db"+String.valueOf(index+1)+"R";
					if(setting.contains(db40))
					{
						int GainValue40 =  setting.getInt(db40, -1);
						gain[index].setGainValue40(GainValue40);
					}
					if(setting.contains(db60))
					{
						int GainValue60 =  setting.getInt(db60, -1);
						gain[index].setGainValue60(GainValue60);
					}
					
					if(setting.contains(db80))
					{
						int GainValue80 =  setting.getInt(db80, -1);
						gain[index].setGainValue80(GainValue80);
					}
					
					if(setting.contains(db40R))
					{
						int GainValue40 =  setting.getInt(db40R, -1);
						gainR[index].setGainValue40(GainValue40);
					}
					if(setting.contains(db60R))
					{
						int GainValue60 =  setting.getInt(db60R, -1);
						gainR[index].setGainValue60(GainValue60);
					}
					
					if(setting.contains(db80R))
					{
						int GainValue80 =  setting.getInt(db80R, -1);
						gainR[index].setGainValue80(GainValue80);
					}
					
				}
				
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//檢查增益值 是否正確設定
		boolean check = true;
		for(int i=0;i<gain.length;i++)
		{
			if(gain[i].getGainValue40()==-1)
			{
				check = false;
			}
			if(gain[i].getGainValue60()==-1)
			{
				check = false;
			}
			if(gain[i].getGainValue80()==-1)
			{
				check = false;
			}
			
			if(gainR[i].getGainValue40()==-1)
			{
				check = false;
			}
			if(gainR[i].getGainValue60()==-1)
			{
				check = false;
			}
			if(gainR[i].getGainValue80()==-1)
			{
				check = false;
			}
			
		}
		
		//檢查完成後將其儲存內建設定檔
		if(check)
		{
			for(int i=0;i<gain.length;i++)
			{
				String db40 = "Gain40db"+String.valueOf(i+1);
				String db60 = "Gain60db"+String.valueOf(i+1);
				String db80 = "Gain80db"+String.valueOf(i+1);
				String db40R = "Gain40db"+String.valueOf(i+1)+"R";
				String db60R = "Gain60db"+String.valueOf(i+1)+"R";
				String db80R = "Gain80db"+String.valueOf(i+1)+"R";
				edit.putInt(db40, gain[i].getGainValue40());
				edit.putInt(db60, gain[i].getGainValue60());
				edit.putInt(db80, gain[i].getGainValue80());
				edit.putInt(db40R, gainR[i].getGainValue40());
				edit.putInt(db60R, gainR[i].getGainValue60());
				edit.putInt(db80R, gainR[i].getGainValue80());
				edit.commit();
				Toast.makeText(GainSetting.this, "設定檔儲存!", 5).show();
			}
		}
	}

}

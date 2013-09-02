package ntou.hearingaid.hearingaid;

import java.util.ArrayList;
import java.util.List;

import ntou.hearingaid.customerview.PureToneGraph;
import ntou.hearingaid.dsp.FilterBank;
import ntou.hearingaid.dsp.FilterBank.OnFilterBankListener;
import ntou.hearingaid.realeartest.PureToneGeneration;
import ntou.hearingaid.sound.SoundParameter;
import ntou.hearingaid.sound.Speaker;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

/*
 * 真耳測試介面
 */

public class PureToneTest extends Activity {

	private Spinner PureToneFreq;
	private EditText PureToneVolme;
	private Button PureToneStart;
	private CheckBox useFilter;
	//private Speaker speaker;
	private PureToneGeneration gen;
	//private int playBufSize;
	//private AudioTrack audioTrack;
	private Speaker speaker;
	private FilterBank filterBank;
	private PureToneGraph graph;
	
	private RadioButton left;
	private RadioButton right;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pure_tone_test);
		
		left = (RadioButton)findViewById(R.id.LeftRadio);
		right = (RadioButton)findViewById(R.id.RightRadio);
		speaker = new Speaker(16000);
		filterBank = new FilterBank();
		
		PureToneFreq = (Spinner)findViewById(R.id.PureToneFreq);
		
		List<String> list = new ArrayList<String>();
		list.add("250");
		list.add("500");
		list.add("1000");
		list.add("2000");
		list.add("4000");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		PureToneFreq.setAdapter(dataAdapter);
		
		PureToneVolme = (EditText)findViewById(R.id.PureToneVolume);
		PureToneStart = (Button)findViewById(R.id.PureToneStart);
		PureToneStart.setOnClickListener(new OnClick());
		useFilter = (CheckBox)findViewById(R.id.usefilter);
		graph = (PureToneGraph)findViewById(R.id.pureToneGraph1);
		
		//speaker = new Speaker();
		gen = new PureToneGeneration(SoundParameter.frequency);
		
		filterBank.setOnFilterBankListener(new OnFilterBankListener() {
			
			public void OnSuccess(short[] data) {
				// TODO Auto-generated method stub
				//Toast.makeText(PureToneTest.this, Integer.toString(data.length), 5).show();
				if(left.isChecked())
				{
					for(int i=1;i<data.length;i+=2)
					{
						data[i]=0;
					}
				}
				if(right.isChecked())
				{
					for(int i=0;i<data.length;i+=2)
					{
						data[i]=0;
					}
				}
				speaker.AddSignals(data);
			}
		});
		
		filterBank.open();
		speaker.open();
		//playBufSize=AudioTrack.getMinBufferSize(SoundParameter.PureTonefrequency, SoundParameter.channelConfiguration, SoundParameter.audioEncoding);
		//STREAM_MUSIC MODE_STREAM
		//audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SoundParameter.PureTonefrequency, SoundParameter.channelConfiguration, SoundParameter.audioEncoding, playBufSize, AudioTrack.MODE_STREAM);
	}
	
	private class OnClick implements OnClickListener{

		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0.getId()==R.id.PureToneStart)
			{
				PureToneStart.setEnabled(false);
				String freq_s = String.valueOf(PureToneFreq.getSelectedItem().toString());
				String db_s = PureToneVolme.getText().toString();
				int freq = Integer.parseInt(freq_s);
				int db = Integer.parseInt(db_s);
				if(left.isChecked())
					graph.setData(freq, db, 0);
				if(right.isChecked())
					graph.setData(freq, db, 1);
				//Toast.makeText(PureToneTest.this, "pass", 5).show();
				switch(freq)
				{
				case 250:
					db = db+26;
					break;
				case 500:
					db+=12;
					break;
				case 1000:
					db+=7;
					break;
				case 2000:
					db+=7;
					break;
				case 4000:
					db+=10;
					break;
				}
				short[] res = gen.GeneratePureTone(freq, 3, db);
				
				short[] res2 = new short[res.length*2];
				if(left.isChecked())
				{
					for(int i=0;i<res2.length;i++)
					{
						//左耳測試
						if(i%2==0)
							res2[i] = res[i/2];
						else
							res2[i] = 0;
						
					}
				}
				else
				{
					for(int i=0;i<res2.length;i++)
					{
						//左耳測試
						if(i%2==1)
							res2[i] = res[i/2];
						else
							res2[i] = 0;
						
					}
				}
				//short[] res = gen.GeneratePureTone(freq, 3, Volume);
				//filterBank.AddSignals(res);
				if(useFilter.isChecked())
				{
					filterBank.AddSignals(res);
				}
				else
				{
					speaker.AddSignals(res2);
				}
				//audioTrack.play();
				//audioTrack.write(res, 0, res.length);
				//audioTrack.stop();
				PureToneStart.setEnabled(true);
				//speaker.AddSignals(gen.GeneratePureTone(250, 5, 10));
				
			}
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		filterBank.close();
		speaker.close();
		SoundParameter.frequency = 16000;
		super.onDestroy();
	}

}

package ntou.hearingaid.customerview;

import ntou.hearingaid.hearingaid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/*
 * 客製化元件-增益補償設定
 */

public class GainView extends LinearLayout {

	private View view;
	private TextView GainNo;	//Gain編號
	private EditText GainValue40;	//Gain40dB增益
	private SeekBar seekBar40;	//Gain40dB 增益seek
	private EditText GainValue60;
	private SeekBar seekBar60;
	private EditText GainValue80;
	private SeekBar seekBar80;
	private int SeekMaxValue = 120;	//seek最大值定義
	public GainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.gainview,this);
		GainNo = (TextView)view.findViewById(R.id.GainNo);
		GainValue40 = (EditText)view.findViewById(R.id.GainValue40);
		seekBar40 = (SeekBar)view.findViewById(R.id.gainValueSeek40);
		seekBar40.setMax(SeekMaxValue);
		GainValue60 = (EditText)view.findViewById(R.id.GainValue60);
		seekBar60 = (SeekBar)view.findViewById(R.id.gainValueSeek60);
		seekBar60.setMax(SeekMaxValue);
		GainValue80 = (EditText)view.findViewById(R.id.GainValue80);
		seekBar80 = (SeekBar)view.findViewById(R.id.gainValueSeek80);
		seekBar80.setMax(SeekMaxValue);
		seekBar40.setOnSeekBarChangeListener(new SeekBarChange());
		seekBar60.setOnSeekBarChangeListener(new SeekBarChange());
		seekBar80.setOnSeekBarChangeListener(new SeekBarChange());
		
	}
	
	//SeekBarChange事件
	public class SeekBarChange implements OnSeekBarChangeListener
	{

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			//依照不同seekBar被改變 更改其對應Text值
			switch(seekBar.getId())
			{
				case R.id.gainValueSeek40:
					GainValue40.setText(String.valueOf(progress-(SeekMaxValue/2)));
					break;
				case R.id.gainValueSeek60:
					GainValue60.setText(String.valueOf(progress-(SeekMaxValue/2)));
					break;
				case R.id.gainValueSeek80:
					GainValue80.setText(String.valueOf(progress-(SeekMaxValue/2)));
					break;
			}
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/*
	 * 設定增益頻道編號
	 * 不含左右聲道
	 * No - 編號
	 */
	public void setGainNo(int No)
	{
		GainNo.setText("頻帶"+Integer.toString(No));
	}
	/*
	 * 設定增益頻道編號
	 * 含左右聲道
	 * No - 編號
	 * LeftorRight - 左(0)或右(1)
	 */
	public void setGainNo(int No,int LeftorRight)
	{
		if(LeftorRight==0)
			GainNo.setText("頻帶"+Integer.toString(No)+"左聲道");
		else
			GainNo.setText("頻帶"+Integer.toString(No)+"右聲道");
	}
	
	/*
	 * 設定40dB增益
	 * value - 增益數值
	 */
	public void setGainValue40(int value)
	{
		GainValue40.setText(Integer.toString(value));
		seekBar40.setProgress(value+(SeekMaxValue/2));
	}
	/*
	 * 取得40dB增益
	 * return 增益值
	 */
	public int getGainValue40()
	{
		try
		{
			return Integer.parseInt(GainValue40.getText().toString());
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	/*
	 * 設定60dB增益
	 * value - 增益數值
	 */
	public void setGainValue60(int value)
	{
		GainValue60.setText(Integer.toString(value));
		seekBar60.setProgress(value+(SeekMaxValue/2));
	}
	/*
	 * 取得60dB增益
	 * return 增益值
	 */
	public int getGainValue60()
	{
		try
		{
			return Integer.parseInt(GainValue60.getText().toString());
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	/*
	 * 設定80dB增益
	 * value - 增益數值
	 */
	public void setGainValue80(int value)
	{
		GainValue80.setText(Integer.toString(value));
		seekBar80.setProgress(value+(SeekMaxValue/2));
	}
	/*
	 * 取得80dB增益
	 * return 增益值
	 */
	public int getGainValue80()
	{
		try
		{
			return Integer.parseInt(GainValue80.getText().toString());
		}
		catch(Exception e)
		{
			return -1;
		}
	}

}

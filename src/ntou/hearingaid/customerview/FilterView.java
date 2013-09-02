package ntou.hearingaid.customerview;

import ntou.hearingaid.hearingaid.BodePlotActivity;
import ntou.hearingaid.hearingaid.MainActivity;
import ntou.hearingaid.hearingaid.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 客製化元件-濾波器切割設定
 */

public class FilterView extends LinearLayout {

	private View view;
	private TextView FreqNo;	//頻帶數
	private TextView LowFreq;	//低頻
	private TextView HiFreq;	//高頻
	private Button BodePlotButton;	//BodePlot檢視button
	private Context context;
	
	public FilterView(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.filterview,this);
		FreqNo = (TextView)view.findViewById(R.id.FreqNo);
		LowFreq = (TextView)view.findViewById(R.id.lowFreq);
		HiFreq = (TextView)view.findViewById(R.id.HiFreq);
		BodePlotButton = (Button)view.findViewById(R.id.bodeplotbutton);
		BodePlotButton.setOnClickListener(new OnClick());
	}
	
	/*
	 * 設定頻帶編號
	 * No - 編號
	 */
	public void setFreqNo(int No)
	{
		FreqNo.setText("頻帶"+Integer.toString(No));
	}
	
	/*
	 * 取得低頻
	 * return LowFreq數值
	 */
	public int getLowFreq()
	{
		try
		{
			return Integer.parseInt(LowFreq.getText().toString());
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	
	/*
	 * 設定低頻
	 * freq - 頻率
	 */
	public void setLowFreq(int freq)
	{
		LowFreq.setText(String.valueOf(freq));
	}
	
	/*
	 * 取得高頻
	 * return HiFreq 數值
	 */
	public int getHiFreq()
	{
		try
		{
			return Integer.parseInt(HiFreq.getText().toString());
		}
		catch(Exception e)
		{
			return -1;
		}
		
	}
	
	/*
	 * 設定高頻
	 * freq - 頻率
	 */
	public void setHiFreq(int freq)
	{
		HiFreq.setText(String.valueOf(freq));
	}
	
	//Button Click事件
	private class OnClick implements OnClickListener
	{

		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//如果按下bodeplotbutton
			if(arg0.getId()==R.id.bodeplotbutton)
			{
				//判斷是否已填上頻率
				if(!(HiFreq.getText().toString().equals("")||LowFreq.getText().toString().equals("")))
				{
					//將資訊傳至另一個頁面
					Bundle bundle = new Bundle();
					bundle.putInt("LowFreq", Integer.parseInt(LowFreq.getText().toString()));
					bundle.putInt("HiFreq", Integer.parseInt(HiFreq.getText().toString()));
					
					Intent intent = new Intent();
					intent.setClass(context, BodePlotActivity.class);
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
				else
				{
					Toast.makeText(context,"請輸入頻帶範圍!!", 5).show();
				}
			}
		}
		
	}
}

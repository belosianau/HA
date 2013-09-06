/*
 * Test value 
 * 
 */


package ntou.hearingaid.customerview;

import ntou.hearingaid.dsp.IIRFilter.Complex;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class BodePlotGraph extends View
{
	private double[] s;	 //  記錄 s-domain 切割後數值
	private double[] db;  //  記錄增益值
	private double[] phase;  //  記錄相位圖值
	public enum BodePlotType{db,phase};  //  列舉圖的類型
	private BodePlotType type = BodePlotType.db;  //  預設使用增意圖
	private Paint paint;  //  設定畫筆	
	private int Width;  //  畫布寬度
	private int Height;  //  畫不高度
	//  由於左右切割比例不同，透過less1Count與greater1Count記錄左右的
	private int less1Count = 0;  //  記錄s小於1時 記錄個數
	private int greater1Count = 0;  //  記錄s大於1時 記錄個數
	private float db_minData = -50;  //  記錄增益圖最小值
	private float db_maxData = 50;  //  記錄增益圖最大值
	private int db_increase = 10;  //  記錄增益圖increase
	private float phase_minData = 0;  //  記錄相位圖最小值
	private float phase_maxData = 0;  //  記錄相位圖最大值
	private int phase_increase = 180;  //  記錄相位圖increase
	
	private int max_y_axis;  //  目前最大y軸值
	private int min_y_axis;  //  目前最小y軸值
	
	/*
	 * 波德圖初始化
	 */
	public BodePlotGraph(Context context)
	{
		super(context);
		paint = new Paint();
	}

	/*
	 * 波德圖初始化
	 */
	public BodePlotGraph(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		paint = new Paint();
	}
	
	/*
	 * 取得目前波德圖形態
	 */
	public BodePlotType getType()
	{
		return this.type;
	}
	
	/*
	 * 設定目前波德圖形態
	 * type - dB or phase
	 */
	public void setType(BodePlotType type)
	{
		this.type = type;
	}

	/*
	 * 刷新畫布
	 * canvas - 欲繪製的畫布
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		DrawBackground(canvas);
		DrawData(canvas);
	}
	
	/*
	 * 偵測圖寬高是否改變
	 * 如改變則更改設定值
	 * w - 目前寬值
	 * h - 目前高值
	 * oldw - 前一筆寬值
	 * oldh - 前一筆高值
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		//Log.e("debug",Integer.toString(w)+","+Integer.toString(h));
		Width = w;
		Height = h;
		
	}
	
	/*
	 * 繪製BodePlot 座標軸 
	 * canvas - 欲繪製的畫布
	 */
	private void DrawBackground(Canvas canvas)
	{	
		int y_num; 
		float x_axis = (Width-40)/2;
		float y_axis;
		
		switch(this.type)
		{
		//db座標軸
		case db:
			y_num = (max_y_axis-min_y_axis)/db_increase;//y軸切割個數
			y_axis = (Height-40)/y_num;
			//設定畫筆基準線顏色
			paint.setColor(Color.BLACK);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(5);
			
			//畫兩條XY基準線
			canvas.drawLine(20, 20, 20, Height-20, paint);
			canvas.drawLine(20, Height-20, Width-20, Height-20, paint);
			
			
			//設定畫筆內部線顏色
			paint.setColor(Color.GRAY);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(2);
			
			canvas.drawText(String.valueOf(min_y_axis), 0, ((Height-20)), paint);
			canvas.drawText(String.valueOf(0.1), 25, ((Height-20))+20, paint);
			
			//畫出y軸 並標上y軸對應數值
			for(int i=0;i<y_num;i++)
			{
				//canvas.drawLine(20, ((Height-20)/2)+(i+1)*y_axis, Width-20, ((Height-20)/2)+(i+1)*y_axis, paint);
				canvas.drawLine(20, ((Height-20))-(i+1)*y_axis, Width-20, ((Height-20))-(i+1)*y_axis, paint);
				//canvas.drawText(String.valueOf((i+1)*10), 0, ((Height-20)/2)-(i+1)*y_axis, paint);
				canvas.drawText(String.valueOf(min_y_axis+(i+1)*db_increase), 0, ((Height-20))-(i+1)*y_axis, paint);
			}
			//畫出x軸 並標上x軸對應數值
			for(int i=0;i<2;i++)
			{
				
				canvas.drawLine(20+(i+1)*x_axis, 20, 20+(i+1)*x_axis, Height-20, paint);
				canvas.drawText(String.valueOf((int)(0.1*Math.pow(10, i+1))), 20+(i+1)*x_axis, ((Height-20))+20, paint);
			}
			break;
		case phase:
			y_num = (max_y_axis-min_y_axis)/phase_increase;//y軸切割個數
			y_axis = (Height-40)/y_num;
			paint.setColor(Color.BLACK);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(5);
			
			canvas.drawLine(20, 20, 20, Height-20, paint);
			canvas.drawLine(20, Height-20, Width-20, Height-20, paint);
			
			//設定畫筆內部線顏色
			paint.setColor(Color.GRAY);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(2);
			canvas.drawText(String.valueOf(min_y_axis), 0, ((Height-20)), paint);
			canvas.drawText(String.valueOf(0.1), 25, ((Height-20))+20, paint);
			
			paint.setColor(Color.GRAY);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(2);
			
			//畫出y軸 並標上y軸對應數值
			for(int i=0;i<y_num;i++)
			{
				//canvas.drawLine(20, ((Height-20)/2)+(i+1)*y_axis, Width-20, ((Height-20)/2)+(i+1)*y_axis, paint);
				canvas.drawLine(20, ((Height-20))-(i+1)*y_axis, Width-20, ((Height-20))-(i+1)*y_axis, paint);
				//canvas.drawText(String.valueOf((i+1)*10), 0, ((Height-20)/2)-(i+1)*y_axis, paint);
				canvas.drawText(String.valueOf(min_y_axis+(i+1)*phase_increase), 0, ((Height-20))-(i+1)*y_axis, paint);
			}
			//畫出x軸 並標上x軸對應數值
			for(int i=0;i<2;i++)
			{
				
				canvas.drawLine(20+(i+1)*x_axis, 20, 20+(i+1)*x_axis, Height-20, paint);
				canvas.drawText(String.valueOf((int)(0.1*Math.pow(10, i+1))), 20+(i+1)*x_axis, ((Height-20))+20, paint);
			}
			break;
		}
		
		
	}
	
	/*
	 * 將資料畫至座標軸上
	 * canvas - 欲繪製的畫布
	 */
	private void DrawData(Canvas canvas)
	{
		if(db==null && phase==null)
			return;
		
		float x_axis = (Width-40)/2;	//將坐標軸對半切取得個別長度
		float x_axis_less1 = (((float)20+x_axis)-20)/(float)less1Count;	//計算s<1間隔大小
		float x_axis_greater1 = (float) ((((float)20+(2.0*x_axis))-((float)20+(1.0*x_axis)))/greater1Count);
		//計算s>1間隔大小
		
		float[] prePoint = new float[2];	//記錄前一個點 [0]:x [1]:y
		//設定Data標點
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(1);
		
		switch(this.type)
		{
		//將資料繪製上圖
		case db:	//繪製增益圖
			
			//Log.d("debug", String.valueOf(x_axis_less1));
			//Log.d("debug",String.valueOf(x_axis_greater1));
			
			for(int i=0;i<s.length;i++)
			{
				//計算y軸每一刻度間隔
				float y_axis = ((float)(Height-40))/(max_y_axis-min_y_axis);
				//Log.d("debug",String.valueOf(y_axis));
				//Log.d("debug",String.valueOf(db[i]));
				
				//當s<1時
				if(s[i]<=1.0)
				{
					if(i==0)
					{
						//第一個點時將prePoint初始化
						prePoint[0] = 20+((0)*x_axis_less1-1);
						prePoint[1] = ((float)(Height-20))-((float)((this.db[999-0]-min_y_axis)*y_axis));
					}
					else
					{
						//將前一節點與當前節點連線
						canvas.drawLine(prePoint[0], prePoint[1], 20+((i)*x_axis_less1-1), ((float)(Height-20))-((float)((this.db[999-i]-min_y_axis)*y_axis)), paint);
						//將目前節點取代前一節點
						prePoint[0] = 20+((i)*x_axis_less1-1);
						prePoint[1] = ((float)(Height-20))-((float)((this.db[999-i]-min_y_axis)*y_axis));
					}
					//canvas.drawPoint(20+((i)*x_axis_less1-1),((float)(Height-20))-((float)((this.db[999-i]-min_y_axis)*y_axis)),paint);
					
				}
				else	//當s>1時
				{
					if(i==0)
					{
						//第一個點時將prePoint初始化
						prePoint[0] = 20+((i)*x_axis_greater1-1);
						prePoint[1] = ((float)(Height-20))-((float)((this.db[999-i]-min_y_axis)*y_axis));
					}
					else
					{
						//將前一節點與當前節點連線
						canvas.drawLine(prePoint[0], prePoint[1], 20+((i)*x_axis_greater1-1), ((float)(Height-20))-((float)((this.db[999-i]-min_y_axis)*y_axis)), paint);
						//將目前節點取代前一節點
						prePoint[0] = 20+((i)*x_axis_greater1-1);
						prePoint[1] = ((float)(Height-20))-((float)((this.db[999-i]-min_y_axis)*y_axis));
					}
					//canvas.drawPoint(20+((i)*x_axis_greater1-1),((float)(Height-20))-((float)((this.db[999-i]-min_y_axis)*y_axis)),paint);
					
				}
			}
			break;
		case phase:	//相位圖
			
			//Log.d("debug", String.valueOf(x_axis_less1));
			//Log.d("debug",String.valueOf(x_axis_greater1));
			for(int i=0;i<s.length;i++)
			{
				//計算y軸每一刻度間隔
				float y_axis = ((float)(Height-40))/(max_y_axis-min_y_axis);
				
				//Log.d("debug",String.valueOf(phase[999-i]));
				//當s<1時
				if(s[i]<=1.0)
				{
					if(i==0)
					{
						//第一個點時將prePoint初始化
						prePoint[0] = 20+((i)*x_axis_less1-1);
						prePoint[1] = ((float)(Height-20))-((float)((this.phase[999-i]-min_y_axis)*y_axis));
					}
					else
					{
						//將前一節點與當前節點連線
						canvas.drawLine(prePoint[0], prePoint[1], 20+((i)*x_axis_less1-1), ((float)(Height-20))-((float)((this.phase[999-i]-min_y_axis)*y_axis)), paint);
						//將目前節點取代前一節點
						prePoint[0] = 20+((i)*x_axis_less1-1);
						prePoint[1] = ((float)(Height-20))-((float)((this.phase[999-i]-min_y_axis)*y_axis));
						
					}
					//canvas.drawPoint(20+((i)*x_axis_less1-1),((float)(Height-20))-((float)((this.phase[999-i]-min_y_axis)*y_axis)),paint);
					
				}
				//當s>1時
				else
				{
					if(i==0)
					{
						//第一個點時將prePoint初始化
						prePoint[0] = 20+((i)*x_axis_greater1-1);
						prePoint[1] = ((float)(Height-20))-((float)((this.phase[999-i]-min_y_axis)*y_axis));
					}
					else
					{
						//將前一節點與當前節點連線
						canvas.drawLine(prePoint[0], prePoint[1], 20+((i)*x_axis_less1-1), ((float)(Height-20))-((float)((this.phase[999-i]-min_y_axis)*y_axis)), paint);
						//將目前節點取代前一節點
						prePoint[0] = 20+((i)*x_axis_less1-1);
						prePoint[1] = ((float)(Height-20))-((float)((this.phase[999-i]-min_y_axis)*y_axis));
					}
					//canvas.drawPoint(20+((i)*x_axis_greater1-1),((float)(Height-20))-((float)((this.phase[999-i]-min_y_axis)*y_axis)),paint);
					
				}
			}
			break;
		}
		
	}
	
	/*
	 * 將資料傳入 方便元件繪圖
	 * s - s平面數值
	 * data - 對應s的相關數值
	 */
	public void setData(double[]s ,double[] data)
	{
		int[] tmp;
		this.s = s;
		less1Count = 0;
		greater1Count = 0;
		/*
		 * 統計s<1及s>1的節點數
		 */
		for(int i=0;i<s.length;i++)
		{
			if(s[i]<1.0)
				less1Count++;
		}
		for(int i=0;i<s.length;i++)
		{
			if(s[i]>1.0)
				greater1Count++;
		}
		/*
		 * 判斷傳入的資料屬於何類型圖
		 * 並依照其類型將圖初始化
		 */
		switch(this.type)
		{
		case db:
			this.db = data;
			tmp = getAxis_Value(this.db);
			min_y_axis = tmp[0];
			max_y_axis = tmp[1];
			break;
		case phase:
			this.phase = data;
			tmp = getAxis_Value(this.phase);
			min_y_axis = tmp[0];
			max_y_axis = tmp[1];
			break;
		}
		this.invalidate();
	}
	
	/*
	 * 取得需設定的座標上下限 
	 * data - 欲分析的訊號資料
	 * return [0]最小值 [1]最大值
	 */
	private int[] getAxis_Value(double[] data)
	{
		//找出數據最大最小值
		double[] tmp = new double[2];
		tmp[0] = data[0];
		tmp[1] = data[0];
		for(int i=1;i<data.length;i++)
		{
			if(tmp[0]>data[i])
				tmp[0] = data[i];
			if(tmp[1]<data[i])
				tmp[1] = data[i];
		}
		
		//定義坐標軸範圍
		int[] res = new int[2];
		if(tmp[0]>=0)
		{
			switch(this.type)
			{
			case db:
				res[0] = ((int)(tmp[0]/db_increase)*db_increase);
				break;
			case phase:
				res[0] = -180;
				break;
			}
		}
		else if(tmp[0]<0)
		{
			switch(this.type)
			{
			case db:
				res[0] = (((int)(tmp[0]/db_increase)-1)*db_increase);
				break;
			case phase:
				res[0] = -180;
				break;
			}
		}
		
		if(tmp[1]>=0)
		{
			switch(this.type)
			{
			case db:
				res[1] = ((((int)(tmp[1]/db_increase))+1)*db_increase);
				break;
			case phase:
				res[1] = 180;
				break;
			}
		}
		else if(tmp[1]<0)
		{
			switch(this.type)
			{
			case db:
				res[1] = (((int)(tmp[1]/db_increase))*db_increase);
				break;
			case phase:
				res[1] = 180;
				break;
			}
		}
		return res;
	}
}

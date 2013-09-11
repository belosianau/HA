package ntou.hearingaid.customerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/*
 * 客制化元件-聽力圖
 */

public class PureToneGraph extends View
{
	private Bitmap background;
	private Paint paint;
	
	private int Width;	//記錄畫面寬度
	private int Height;	//記錄畫面高度
	
	//左耳不同頻帶資料
	private int data250 = 0;
	private int data500 = 0;
	private int data1000 = 0;
	private int data2000 = 0;
	private int data4000 = 0;
	//右耳不同頻帶資料
	private int data250R = 0;
	private int data500R = 0;
	private int data1000R = 0;
	private int data2000R = 0;
	private int data4000R = 0;
	
	
	public PureToneGraph(Context context)
	{
		super(context);
		paint = new Paint();
	}

	public PureToneGraph(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		paint = new Paint();
	}

	/*
	 * 刷新畫布
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		//Log.e("debug","Draw");
		DrawBackground(canvas);
		DrawData(canvas);	
	}

	/*
	 * 偵測圖寬高是否改變
	 * 如改變則更改設定值
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		//Log.e("debug",Integer.toString(w)+","+Integer.toString(h));
		Width = w;
		Height = h;	
	}
	
	//繪製聽力圖 座標軸 
	private void DrawBackground(Canvas canvas)
	{
		//設定畫筆
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(5);
		//畫出主要的x-y軸
		canvas.drawLine(20, 20, 20, Height-20, paint);
		canvas.drawLine(20, Height-20, Width-20, Height-20, paint);
		
		//設定畫筆
		paint.setColor(Color.GRAY);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);
		
		//標上起點座標軸值
		canvas.drawText(String.valueOf(120), 0, Height-20, paint);
		int initfreq = 250;	//初始繪製頻帶線
		int x_axis = (Width-40)/16;	//將16條線平均分配
		/*
		 * 依照相對位置繪製16條線 250 500 750 1000 1250 .... 4000
		 */
		for(int i=0; i < 16; i++)
		{
			int tmp = 250*(i+1);	//計算當前繪製頻帶
			if(tmp == initfreq)	//檢查是否符合所要的下一個頻帶 250 500 1000 2000 4000
			{
				canvas.drawLine(20+((i+1)*x_axis-1), 20, 20+((i+1)*x_axis-1), Height-20, paint);
				canvas.drawText(String.valueOf(initfreq), 10+((i+1)*x_axis-1), 20 , paint);
				canvas.drawText(String.valueOf(initfreq), 10+((i+1)*x_axis-1), Height , paint);
				initfreq*=2;	//將檢查值跳至下一個要繪製的頻帶
			}
		}
		
		int y_axis = (Height-40)/14;	//將 y軸平均14條線
		/*
		 * 逐一畫上14條線
		 * 並標上對應數值
		 */
		for(int i=0; i < 13; i++)
		{
			canvas.drawLine(20, Height-20-(y_axis*(i+1)), Width-20, Height-20-(y_axis*(i+1)), paint);
			canvas.drawText(String.valueOf((120-((i+1)*10))), 0, Height-20-(y_axis*(i+1)), paint);
		}
	}
	
	/*
	 * 將資料畫至座標軸上
	 * canvas - 欲繪製畫布
	 */
	private void DrawData(Canvas canvas)
	{
		//設定藍色畫筆 繪製左耳
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(10);
		//將左耳資料逐一繪出
		int freq = 0;
		int db = 0;
		for(int i=0; i<16; i++)
		{
			switch(250*(i+1))
			{
			case 250:
				freq = 250;
				db = data250;
				break;
			case 500:
				freq = 500;
				db = data500;
				break;
			case 1000:
				freq = 1000;
				db = data1000;
				break;
			case 2000:
				freq = 2000;
				db = data2000;
				break;
			case 4000:
				freq = 4000;
				db = data4000;
				break;
				
			}
			int x_i = (freq/250)-1;
			float y_i = (120.0f-db)/10;
			int x_axis = (Width-40)/16;
			int y_axis = (Height-40)/14;
			canvas.drawPoint(20+((x_i+1)*x_axis-1), Height-20-(y_axis*(y_i)), paint);
		}
		//設定紅色畫筆 繪製右耳
		paint.setColor(Color.RED);
		//將右耳資料逐一繪出
		for(int i=0; i<16; i++)
		{
			switch(250*(i+1))
			{
			case 250:
				freq = 250;
				db = data250R;
				break;
			case 500:
				freq = 500;
				db = data500R;
				break;
			case 1000:
				freq = 1000;
				db = data1000R;
				break;
			case 2000:
				freq = 2000;
				db = data2000R;
				break;
			case 4000:
				freq = 4000;
				db = data4000R;
				break;			
			}
			int x_i = (freq/250)-1;
			float y_i = (120.0f-db)/10;
			int x_axis = (Width-40)/16;
			int y_axis = (Height-40)/14;
			canvas.drawPoint(20+((x_i+1)*x_axis-1), Height-20-(y_axis*(y_i)), paint);
		}
	}
	
	/*
	 * 設定聽力資料
	 * freq - 測試頻帶
	 * db - 音量
	 * LeftorRight - 左耳或右耳
	 */
	public void setData(int freq, int db, int LeftorRight)
	{
		switch(freq)
		{
		case 250:
			if(LeftorRight==0)
				data250 = db;
			else
				data250R = db;
			break;
		case 500:
			if(LeftorRight==0)
				data500 = db;
			else
				data500R = db;
			break;
		case 1000:
			if(LeftorRight==0)
				data1000 = db;
			else
				data1000R = db;
			break;
		case 2000:
			if(LeftorRight==0)
				data2000 = db;
			else
				data2000R = db;
			break;
		case 4000:
			if(LeftorRight==0)
				data4000 = db;
			else
				data4000R = db;
			break;
		}
	}
}
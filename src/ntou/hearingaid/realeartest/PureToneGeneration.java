package ntou.hearingaid.realeartest;

import android.util.Log;

/*
 * 純音產生
 */

public class PureToneGeneration {
	private int SampleRate;
	private double db;
	/*
	 * 建構函式
	 * SampleRate 取樣頻率
	 */
	public PureToneGeneration(int SampleRate)
	{
		this.SampleRate = SampleRate;
	}
	
	/*
	 * 產生純音
	 * freqence - 所需頻帶
	 * second - 秒數
	 * db - 音量
	 * return 產生的訊號
	 */
	public short[] GeneratePureTone(int freqence, int second, double db)
	{
		this.db = Math.pow(10, db/20);	//計算相對音量
		//產生sin波形
		short[] sin = new short[second * SampleRate];
		double samplingInterval = (double) (SampleRate / freqence);
		for (int i = 0; i < sin.length; i++) {
            double angle = (2.0 * Math.PI * i) / samplingInterval;
            //Log.d("pure", Double.toString((Math.sin(angle) * this.db)));
            //sin[i] = (short) (Math.sin(angle) * Volume * Short.MAX_VALUE);
            
            int tmp = (int)(Math.sin(angle) * this.db);
            
            //避免overflow
            if(tmp > Short.MAX_VALUE)
            	sin[i] = Short.MAX_VALUE;
            else if (tmp<Short.MIN_VALUE)
            	sin[i] = Short.MIN_VALUE;
            else
            	sin[i] = (short) (Math.sin(angle) * this.db);
            //Log.d("pure", Integer.toString((int) (Math.sin(angle) * this.db)));
            
        }
		return sin;
	}
}

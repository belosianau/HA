package ntou.hearingaid.dsp;

/*
 * 增益處理
 */

public class Gain2 {

	private double _gain = 0.0;
	/*
	 * 建構函式
	 * gain - 設定增益值
	 */
	public Gain2(double gain)
	{
		_gain = Math.pow(10, gain/20);
		//System.out.print(_gain);
	}
	
	/*
	 * 進行增益計算
	 * data - 欲處理的資料
	 * return 處理後結果
	 */
	public short[] process(short[] data)
	{
		for(int i=0;i<data.length;i++)
		{
			int tmp = data[i];
			tmp = (int) (tmp * _gain);
			//避免overflow
			if(tmp>Short.MAX_VALUE)
				data[i] = Short.MAX_VALUE;
			else if(tmp<Short.MIN_VALUE)
				data[i] = Short.MIN_VALUE;
			else
				data[i] = (short)tmp;
		}
		return data;
	}
}

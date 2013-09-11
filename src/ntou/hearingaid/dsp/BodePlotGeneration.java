package ntou.hearingaid.dsp;

import java.math.BigDecimal;

import android.util.Log;
import ntou.hearingaid.dsp.IIRFilter.Complex;

/*
 * 透過此類別計算出濾波器 系統頻率響應 增益圖及相位圖
 * 此部分不需修改!!!
 * 透過別人excel運算改寫而成
 */

public class BodePlotGeneration
{
	private double minRad;
	private double maxRad;
	private Complex[] s = new Complex[1000];
	private Complex[] Num = new Complex[1000];
	private Complex[] Den = new Complex[1000];
	private double[] db = new double[1000];
	private double[] phase = new double[1000];
	
	/*
	 * 取得s<1個數
	 * return 個數
	 */
	public int get_s_less1()
	{
		int count = 0;
		for(int i = 0; i < s.length; i++)
		{
			if(s[i].im() < 1.0)
				count++;
		}
		return count;
	}
	/*
	 * 取得s>1個數
	 * return 個數
	 */
	public int get_s_greater1()
	{
		int count = 0;
		for(int i = 0; i < s.length; i++)
		{
			if(s[i].im() > 1.0)
				count++;
		}
		return count;
	}
	/*
	 * 取得s 數值
	 * return s 陣列
	 */
	public double[] get_s()
	{
		double[] s = new double[this.s.length];
		for(int i = 0; i < this.s.length; i++)
			s[i] = this.s[i].im();
		return s;
	}
	
	/*
	 * 取得增益補償值
	 * return 增益值陣列
	 */
	public double[] get_db()
	{
		return this.db;
	}
	/*
	 * 取得相位圖值
	 * return 相位值陣列
	 */
	public double[] get_phase()
	{
		return this.phase;
	}
	/*
	 * 建構函式
	 * minRad 需要的最小自然頻率
	 * maxRad 需要的最大自然頻率
	 * Num 濾波器實部參數
	 * Den 濾波器虛部參數
	 * 透過這些參數本類別會自動計算取得波德圖
	 */
	public BodePlotGeneration(double minRad, double maxRad, double[] Num, double[] Den)
	{
		this.minRad = minRad;
		this.maxRad = maxRad;
		
		for(int i=0; i<s.length;i++)
		{
			double tmp = Math.log10(this.minRad)+i*((Math.log10(this.maxRad)-Math.log10(this.minRad))/1000);
			s[i] = new Complex(0, Math.pow(10, tmp));
			
			//初始化 Num 與 Den
			this.Num[i] = new Complex(0, 0);
			this.Den[i] = new Complex(0, 0);
		}
		for(int i=0; i<s.length;i++)
		{
			for(int j=0; j<Num.length;j++)
			{
				BigDecimal bd= new BigDecimal(Num[j]); 
				bd.setScale(4, BigDecimal.ROUND_HALF_UP);
				this.Num[i] = this.Num[i].plus((s[i].pow(j)).times(bd.doubleValue()));
				//this.Num[i] = this.Num[i].plus((s[i].pow(j)).times(Num[j]));
			}
			
			for(int j=0; j<Den.length;j++)
			{
				BigDecimal bd= new BigDecimal(Den[j]); 
				bd.setScale(4, BigDecimal.ROUND_HALF_UP);
				this.Den[i] = this.Den[i].plus((s[i].pow(j)).times(bd.doubleValue()));
				//this.Den[i] = this.Den[i].plus((s[i].pow(j)).times(Den[j]));
			}
			
			this.db[i] = 20*Math.log10((this.Num[i].divides(this.Den[i])).abs());
			Complex tmp = (this.Num[i].divides(this.Den[i]));
			this.phase[i] = - Math.toDegrees(Math.atan2(tmp.im(),tmp.re()));
			/*if(this.phase[i]>0)
			{
				this.phase[i] = this.phase[i];
			}*/
		}
	}
}
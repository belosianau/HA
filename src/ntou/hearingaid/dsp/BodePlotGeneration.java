package ntou.hearingaid.dsp;

import java.math.BigDecimal;

import android.util.Log;
import ntou.hearingaid.dsp.IIRFilter.Complex;

/*
 * 硓筁摸璸衡耾猧竟 ╰参繵瞯臫莱 糤痲瓜の瓜
 * 场だぃ惠э!!!
 * 硓筁excel笲衡э糶τΘ
 */

public class BodePlotGeneration {

	private double minRad;
	private double maxRad;
	private Complex[] s = new Complex[1000];
	private Complex[] Num = new Complex[1000];
	private Complex[] Den = new Complex[1000];
	private double[] db = new double[1000];
	private double[] phase = new double[1000];
	
	/*
	 * 眔s<1计
	 * return 计
	 */
	public int get_s_less1()
	{
		int count = 0;
		for(int i=0;i<s.length;i++)
		{
			if(s[i].im()<1.0)
				count++;
		}
		return count;
	}
	/*
	 * 眔s>1计
	 * return 计
	 */
	public int get_s_greater1()
	{
		int count = 0;
		for(int i=0;i<s.length;i++)
		{
			if(s[i].im()>1.0)
				count++;
		}
		return count;
	}
	/*
	 * 眔s 计
	 * return s 皚
	 */
	public double[] get_s()
	{
		double[] s = new double[this.s.length];
		for(int i=0;i<this.s.length;i++)
			s[i] = this.s[i].im();
		return s;
	}
	
	/*
	 * 眔糤痲干纕
	 * return 糤痲皚
	 */
	public double[] get_db()
	{
		return this.db;
	}
	/*
	 * 眔瓜
	 * return 皚
	 */
	public double[] get_phase()
	{
		return this.phase;
	}
	/*
	 * 篶ㄧΑ
	 * minRad 惠璶程礛繵瞯
	 * maxRad 惠璶程礛繵瞯
	 * Num 耾猧竟龟场把计
	 * Den 耾猧竟店场把计
	 * 硓筁硂ㄇ把计セ摸穦笆璸衡眔猧紈瓜
	 */
	public BodePlotGeneration(double minRad, double maxRad, double[] Num, double[] Den)
	{
		this.minRad = minRad;
		this.maxRad = maxRad;
		
		for(int i=0; i<s.length;i++)
		{
			double tmp = Math.log10(this.minRad)+i*((Math.log10(this.maxRad)-Math.log10(this.minRad))/1000);
			s[i] = new Complex(0, Math.pow(10, tmp));
			
			//﹍て Num 籔 Den
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

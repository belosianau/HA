package ntou.hearingaid.dsp;

import java.util.ArrayList;

/*
 * 無作用!!!此程式內部無法使用
 * 請使用Gain2
 */
public class Gain  extends Thread{

	private boolean isRunning = false;
	private ArrayList<short[]> Signals = new ArrayList<short[]>();
	
	public void run()
	{
		while(isRunning)
		{
			short[] buff = null;
			yield();	//避免此程序一直卡住旗標 造成無法新增訊號
			synchronized (Signals) {
				if(Signals.size()==0)
					continue;
				buff = Signals.get(0);
				Signals.remove(0);
			}
			
			short[] tmp = new short[buff.length];
			System.arraycopy(buff, 0, tmp, 0, buff.length);
			
			double gain = Math.log((double)1000.0/(double)20.0);
			for(int i=0;i<tmp.length;i++)
			{
				tmp[i] = (short)(tmp[i] * gain);
			}
			if(buff !=null)
			{
				onGainListener.OnSuccess(tmp);
			}
		}
	}
	
	public interface OnGainListener
	{
		public void OnSuccess(short[] data);
	}
	
	private OnGainListener onGainListener;
	
	public void setOnGainListener(OnGainListener _onGainListener)
	{
		onGainListener = _onGainListener;
	}
	
	public void AddSignals(short[] data)
	{
		synchronized (Signals) {
			Signals.add(data);
		}
	}
	
	public void open()
	{
		isRunning = true;
		this.start();
	}
	
	public void close()
	{
		isRunning = false;
		this.stop();
	}
}

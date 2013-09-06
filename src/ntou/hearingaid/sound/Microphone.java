package ntou.hearingaid.sound;

import java.sql.Time;
import java.util.ArrayList;

import ntou.hearingaid.parameter.Parameter;
import ntou.hearingaid.peformance.PerformanceParameter;

import android.graphics.AvoidXfermode;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/*
 * 負責不斷接收聲音
 */

public class Microphone extends Thread {

	//建立 監聽事件
	public interface OnMicrophoneListener
	{
		public void OnRec(short[] data);
	}
	
	private OnMicrophoneListener onMicrophoneListener;
	
	public void setOnMicrophoneListener(OnMicrophoneListener _onMicrophoneListener)
	{
		onMicrophoneListener = _onMicrophoneListener;
	}
		
	private int recBufSize;
	private AudioRecord audioRecord;	//錄音類別
	private boolean isRecording = false;
	
	public Microphone()
	{
		//初始化麥克風相關設定
		recBufSize = AudioRecord.getMinBufferSize(SoundParameter.frequency,
				SoundParameter.channelConfiguration, SoundParameter.audioEncoding);
		
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SoundParameter.frequency,
				SoundParameter.channelConfiguration, SoundParameter.audioEncoding, recBufSize);
		
		
	}
	
	public void open()
	{
		isRecording = true;
		this.start();
	}
	
	public void close()
	{
		isRecording = false;
		this.interrupt();
		
		
		//this.stop();
	}
	
	public void run()
	{
		
		
		try {
			short[] buffer = new short[recBufSize];
			audioRecord.startRecording();
			PerformanceParameter.MicTime = new ArrayList<Long>();
			PerformanceParameter.recvTime = new ArrayList<Long>();
			PerformanceParameter.avg_MicTime = 0;
			
			//開始不斷接收聲音
			while (isRecording) {
				/*synchronized (PerformanceParameter.MicTime) 
				{
					PerformanceParameter.MicTime.add(System.currentTimeMillis());
				}
				*/
				//訊號讀取
				int bufferReadResult = audioRecord.read(buffer, 0,
						recBufSize);
				/*
				synchronized (PerformanceParameter.recvTime) 
				{
					PerformanceParameter.recvTime.add(System.currentTimeMillis());
				}
				*/
				//判斷是否有得到資料
				if(bufferReadResult>0)
				{
					
					short[] tmpBuf = new short[bufferReadResult];
					System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);
					onMicrophoneListener.OnRec(tmpBuf);	//將讀取到資料送至下一層
					/*synchronized (PerformanceParameter.MicTime) 
					{
						long MicTime = System.currentTimeMillis()-PerformanceParameter.MicTime.get(0);
						//Log.d("debug", "麥克風接收延遲:"+String.valueOf(MicTime));
						PerformanceParameter.MicTime.remove(0);
						if(PerformanceParameter.avg_MicTime ==0)
							PerformanceParameter.avg_MicTime = MicTime;
						else
							PerformanceParameter.avg_MicTime = (PerformanceParameter.avg_MicTime + MicTime)/2;
						Log.d("debug", "平均麥克風接收延遲:"+String.valueOf(PerformanceParameter.avg_MicTime));
					}*/
					
				}
				/*
				short[] tmpBuf = new short[bufferReadResult];
				System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);
				PerformanceParameter.recvTime.add(System.currentTimeMillis());	//記錄接收封包時間
				onMicrophoneListener.OnRec(tmpBuf);
				time2 = System.currentTimeMillis();
				if(Parameter.Mictime==0)
					Parameter.Mictime = time2-time1;
				else
				{
					Parameter.Mictime=(Parameter.Mictime+(time2-time1))/2;
				}*/	//移動至上方 避免不必要封包 及 計算效能用
				
			}
			audioRecord.stop();
			PerformanceParameter.recvTime.clear();
			PerformanceParameter.MicTime.clear();
		} catch (Throwable t) {
			
		}
		
	}
}

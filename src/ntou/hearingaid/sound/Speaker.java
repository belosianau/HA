package ntou.hearingaid.sound;

import java.util.ArrayList;

import ntou.hearingaid.parameter.Parameter;
import ntou.hearingaid.peformance.PerformanceParameter;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

/*
 * 將處理完成聲音進行輸出
 */

public class Speaker extends Thread
{
	private int playBufSize;
	private AudioTrack audioTrack;
	private boolean isPlaying = false;
	private ArrayList<short[]> Signals = new ArrayList<short[]>();
	
	private int SampleRate = SoundParameter.frequency;
	
	/*
	 * 建構函式
	 * SampleRate - 取樣頻率
	 */
	public Speaker(int SampleRate)
	{
		this.SampleRate = SampleRate;
		//playBufSize=AudioTrack.getMinBufferSize(SampleRate, SoundParameter.channelConfiguration, SoundParameter.audioEncoding);
		playBufSize = AudioTrack.getMinBufferSize(SampleRate, AudioFormat.CHANNEL_CONFIGURATION_STEREO, SoundParameter.audioEncoding);
		
		//STREAM_MUSIC MODE_STREAM
		
		//audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SampleRate, SoundParameter.channelConfiguration, SoundParameter.audioEncoding, playBufSize, AudioTrack.MODE_STREAM);
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SampleRate, AudioFormat.CHANNEL_CONFIGURATION_STEREO, SoundParameter.audioEncoding, playBufSize, AudioTrack.MODE_STREAM);
	}
	
	public Speaker()
	{	
		playBufSize = AudioTrack.getMinBufferSize(SampleRate, SoundParameter.channelConfiguration, SoundParameter.audioEncoding);
		
		//STREAM_MUSIC MODE_STREAM
		
		audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, SampleRate, SoundParameter.channelConfiguration, SoundParameter.audioEncoding, playBufSize, AudioTrack.MODE_STREAM);
		//audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SoundParameter.frequency, SoundParameter.channelConfiguration, SoundParameter.audioEncoding, playBufSize, AudioTrack.MODE_STREAM);
	}
	
	/*
	 * 加入新訊號
	 * data - 欲加入的訊號
	 */
	public void AddSignals(short[] data)
	{
		synchronized (Signals)
		{
			Signals.add(data);
			
			//PerformanceParameter.SpeakerTime.add(System.currentTimeMillis());
		}
		/*synchronized (PerformanceParameter.SpeakerTime) 
		{
			PerformanceParameter.SpeakerTime.add(System.currentTimeMillis());
		}*/
	}
	
	//將聲音訊號進行輸出
	public void run()
	{
		PerformanceParameter.SpeakerTime = new ArrayList<Long>();
		PerformanceParameter.avg_SpeakerTime = 0;
		audioTrack.play();
		while(isPlaying)
		{	
			/*
			 * 讀取最早一筆訊號
			 */
			short[] buff = null;
			yield();
			synchronized (Signals)
			{
				if(Signals.size()==0)
					continue;
				buff = Signals.get(0);
				Signals.remove(0);
			}

				//Calculatedb(buff);
				if(buff.length > 0)
				{
					//撥放
				audioTrack.write(buff, 0, buff.length);
				
	
				/*synchronized (PerformanceParameter.SpeakerTime) 
				{
					if(PerformanceParameter.SpeakerTime.size()>0)
					{
						long delaytime = System.currentTimeMillis()-PerformanceParameter.SpeakerTime.get(0);
						//Log.d("debug", "Speaker接收到輸出延遲:"+String.valueOf(delaytime));
						PerformanceParameter.SpeakerTime.remove(0);
						if(PerformanceParameter.avg_SpeakerTime==0)
							PerformanceParameter.avg_SpeakerTime = delaytime;
						else
							PerformanceParameter.avg_SpeakerTime = (PerformanceParameter.avg_SpeakerTime + delaytime)/2;
						Log.d("debug", "平均Speaker接收到輸出延遲:"+String.valueOf(PerformanceParameter.avg_SpeakerTime));
				
					}
				}*/
				
				/*synchronized (PerformanceParameter.recvTime) 
				{
					if(PerformanceParameter.recvTime.size()>0)
					{
						long delaytime = System.currentTimeMillis()-PerformanceParameter.recvTime.get(0);
						//Log.d("debug", "接收到輸出延遲:"+String.valueOf(delaytime));
						PerformanceParameter.recvTime.remove(0);
						if(PerformanceParameter.avg_recvTime ==0)
							PerformanceParameter.avg_recvTime = delaytime;
						else
							PerformanceParameter.avg_recvTime = (PerformanceParameter.avg_recvTime + delaytime)/2;
						
						Log.d("debug", "平均接收到輸出延遲:"+String.valueOf(PerformanceParameter.avg_recvTime));
					}
				}*/
				
				
				}
		}
		audioTrack.stop();
		Signals.clear();
		PerformanceParameter.SpeakerTime.clear();
		PerformanceParameter.avg_SpeakerTime = 0;
	}
	
	public void open()
	{
		isPlaying = true;
		this.start();
	}
	
	public void close()
	{
		isPlaying = false;
		this.interrupt();	
		
		//this.stop();
	}
	/*
	 * 計算音量
	 * data - 欲分析資料
	 * return 音量
	 */
	private int Calculatedb(short[] data)
	{
		short min = data[0];
		double sum = 0;
		for(int i = 0; i < data.length; i++)
		{		
			sum = sum + Math.pow(data[i],2);
		}
		/*for(int i=0;i<256;i++)
		{
			sum = sum+Math.pow(data[i],2);
		}*/
		sum = 10 * Math.log10(sum / data.length);
		//Log.d("debug", String.valueOf(SoundParameter.bufferSize));
		//Log.d("debug", String.valueOf(sum));
		return (int)sum;
	}
}
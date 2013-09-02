package ntou.hearingaid.sound;

import android.media.AudioFormat;
import android.media.AudioTrack;
/*
 * 麥克風及輸出相關音訊參數設定
 */

public class SoundParameter {
	public static int frequency = 16000;	//44100 SDK 使用藍芽 取樣頻率 只能為8000
	public static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	public static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	public static final int PureTonefrequency = 16000;
	public static final int Speakerfrequency = 16000;
	public static int bufferSize = AudioTrack.getMinBufferSize(SoundParameter.frequency, SoundParameter.channelConfiguration, SoundParameter.audioEncoding);;
}

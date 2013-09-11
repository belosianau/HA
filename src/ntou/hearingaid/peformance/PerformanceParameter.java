package ntou.hearingaid.peformance;

import java.util.ArrayList;
/*
 * 效能統計類別
 */
public class PerformanceParameter
{
	public static long SystemStartTime = 0;
	
	public static ArrayList<Long> recvTime = new ArrayList<Long>();
	public static ArrayList<Long> MicTime = new ArrayList<Long>();
	public static ArrayList<Long> FilterTime = new ArrayList<Long>();
	public static ArrayList<Long> SpeakerTime = new ArrayList<Long>();
	public static long avg_recvTime = 0;
	public static long avg_MicTime = 0;
	public static long avg_FilterTime = 0;
	public static long avg_SpeakerTime = 0;
}

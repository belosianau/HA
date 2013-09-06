package ntou.hearingaid.hearingaid;

import ntou.hearingaid.customerview.BodePlotGraph;
import ntou.hearingaid.customerview.BodePlotGraph.BodePlotType;
import ntou.hearingaid.dsp.BodePlotGeneration;
import ntou.hearingaid.dsp.FilterBank;
import ntou.hearingaid.dsp.IIRFilter.IIR;
import ntou.hearingaid.sound.SoundParameter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
 * 波德圖瀏覽介面
 */

public class BodePlotActivity extends Activity {

	private BodePlotGeneration bpgen;
	private Bundle bundle;
	private BodePlotGraph bpg;
	private Button changeButton;
	private BodePlotType type;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bodeplot);
		bundle = getIntent().getExtras();
		int LowFreq = bundle.getInt("LowFreq");
		int HiFreq = bundle.getInt("HiFreq");
		//int LowFreq = 1000;
		//int HiFreq = 2000;
		IIR iir = new IIR(FilterBank.filterorder, LowFreq, HiFreq);
		double[] Num = iir.getNum();
		double[] Den = iir.getDen();
		bpg = (BodePlotGraph)findViewById(R.id.bodePlotGraph1);
		bpgen = new BodePlotGeneration(0.1,10,iir.getNum(),iir.getDen());
		//bpg.setType(BodePlotType.phase);
		this.type = BodePlotType.db;
		bpg.setData(bpgen.get_s(), bpgen.get_db());
		changeButton = (Button)findViewById(R.id.changeButton);
		if(this.type == BodePlotType.db)
		{
			changeButton.setText("Phase");
		}
		else
		{
			changeButton.setText("Magnitude");
		}
		changeButton.setOnClickListener(new OnClick(this));
	}
	//當改變顯示圖形時執行
	public void change()
	{
		switch(this.type)
		{
		case db:
			this.type = BodePlotType.phase;
			bpg.setType(this.type);
			if(bpg.getType()==BodePlotType.phase)
				bpg.setData(bpgen.get_s(), bpgen.get_phase());
			break;
		case phase:
			this.type = BodePlotType.db;
			bpg.setType(this.type);
			if(bpg.getType()==BodePlotType.db)
				bpg.setData(bpgen.get_s(), bpgen.get_db());
			break;
		}
	}
	
	private class OnClick implements OnClickListener
	{
		private BodePlotActivity act;
		public OnClick(BodePlotActivity act)
		{
			this.act = act;
		}
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(v.getId()==R.id.changeButton)
			{
				if(act.type == BodePlotType.db)
				{
					((Button)v).setText("Magnitude");
				}
				else
				{
					((Button)v).setText("Phase");
				}
				act.change();
			}
		}
		
	}

}

package ntou.hearingaid.hearingaid;

import java.io.File;

import ntou.hearingaid.backuprestore.SPBackupRestore;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/*
 * 程式介面-備份還原操做
 */

public class BackupRestore extends Activity {

	private Button backup;
	private Button restore;
	private SPBackupRestore spbr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup_restore);
		backup = (Button)findViewById(R.id.backup);
		restore = (Button)findViewById(R.id.restore);
		spbr = new SPBackupRestore();
		backup.setOnClickListener(new onClick());
		restore.setOnClickListener(new onClick());
	}
	
	public class onClick implements OnClickListener
	{

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId()==R.id.backup)
			{
				File file = new File("/sdcard/hearingaid.xml");
				if(spbr.saveSharedPreferencesToFile(file))
					Toast.makeText(BackupRestore.this, "備份完成!!", 5).show();
				else
					Toast.makeText(BackupRestore.this, "備份失敗!!", 5).show();
			}
			else if(v.getId()==R.id.restore)
			{
				File file = new File("/sdcard/hearingaid.xml");
				if(spbr.loadSharedPreferencesFromFile(file))
					Toast.makeText(BackupRestore.this, "還原完成!!", 5).show();
				else
					Toast.makeText(BackupRestore.this, "還原失敗!!", 5).show();
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_backup_restore, menu);
		return true;
	}

}

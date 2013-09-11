package ntou.hearingaid.backuprestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Map.Entry;

import ntou.hearingaid.hearingaid.MainActivity;
import ntou.hearingaid.parameter.Parameter;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*
 *		提供設定檔備份還原用API
 */

public class SPBackupRestore
{
	/*
	 * 將設定檔備份
	 * dst - 欲儲存的目標檔案連結位置
	 * return 是否成功
	 */
	public boolean saveSharedPreferencesToFile(File dst)
	{
	    boolean res = false;
	    ObjectOutputStream output = null;
	    try
	    {
	        output = new ObjectOutputStream(new FileOutputStream(dst));	//建立一輸出檔案物件
	        SharedPreferences pref = MainActivity.setting;	//取得內建設定檔
	        output.writeObject(pref.getAll());	//將設定檔內容全部寫入檔案

	        res = true;
	    }
	    catch (FileNotFoundException e)
	    {
	        e.printStackTrace();
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            if (output != null)
	            {
	                output.flush();
	                output.close();
	            }
	        }
	        catch (IOException ex)
	        {
	            ex.printStackTrace();
	        }
	    }
	    return res;
	}
	
	/*
	 * 將設定檔還原
	 * src - 還原檔所在位置
	 * return 是否成功
	 */
	@SuppressWarnings({ "unchecked" })
	public boolean loadSharedPreferencesFromFile(File src)
	{
	    boolean res = false;
	    ObjectInputStream input = null;
	    try
	    {
	        input = new ObjectInputStream(new FileInputStream(src));	//建立輸入檔案物件
	            Editor prefEdit = MainActivity.setting.edit();	//取得設定檔位置
	            prefEdit.clear();	//將原先設定檔全部清空
	            /*
	             * 將備份檔案中全部的key,value讀出
	             * 再逐一寫回內建設定檔
	             */
	            Map<String, ?> entries = (Map<String, ?>) input.readObject();
	            for (Entry<String, ?> entry : entries.entrySet()) {
	                Object v = entry.getValue();
	                String key = entry.getKey();
	                //依照不同型態寫入值
	                if (v instanceof Boolean)
	                    prefEdit.putBoolean(key, ((Boolean) v).booleanValue());
	                else if (v instanceof Float)
	                    prefEdit.putFloat(key, ((Float) v).floatValue());
	                else if (v instanceof Integer)
	                    prefEdit.putInt(key, ((Integer) v).intValue());
	                else if (v instanceof Long)
	                    prefEdit.putLong(key, ((Long) v).longValue());
	                else if (v instanceof String)
	                    prefEdit.putString(key, ((String) v));
	            }
	            
	            prefEdit.commit();	//檔案寫入完成後需使用commit將其儲存
	        res = true;         
	    }
	    catch (FileNotFoundException e)
	    {
	        e.printStackTrace();
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    catch (ClassNotFoundException e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            if (input != null)
	            {
	                input.close();
	            }
	        }
	        catch (IOException ex)
	        {
	            ex.printStackTrace();
	        }
	    }
	    return res;
	}
}
package cn.mcm_test;

import cn.mcm.cryption.AESHelper;
import cn.mcm.cryption.Cryption;
import cn.mcm.key.*;
import cn.mcm.listener.FileOpener;
import cn.mcm.listener.SDCardFileObserver;
import cn.mcm_test.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v4.*;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class Main  extends Activity{

	public static final int FILE_RESULT_CODE = 1;
	private String tmpPath = "/sdcard/tmp/";
	 
	private AESHelper mAESHelper;  
    private EncryptionOrDecryptionTask mTask = null;
    
	ViewPager mViewPager;
	private FileObserver  mFileObserver;  
    private ListView lv;
    private Button add;
    private Button bt_jump;
    private Context context;
    private String imei;
    private String ran;
    private String pin;
    private String filepath;
    
	/**
	 * @param args
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        mAESHelper = new AESHelper();
        
        GenKey gk=new GenKey();
        GetInfo gi=new GetInfo(this);
        /*
		Toast toast=Toast.makeText(Main.this,"IMEIΪ��"+gi.getIMEI()+"\n CPU���к�Ϊ��"+gi.getCpuName()+"\n �������"+gi.getRandom(8), Toast.LENGTH_LONG);
		toast.show();
		
        imei=gi.getIMEI();
        ran=gi.getRandom(8);
        gk.genKey(imei, ran);
        Toast toast=Toast.makeText(Main.this,"keyΪ��"+gk.genKey(imei, ran), Toast.LENGTH_LONG);
		toast.show();
		*/
        
        //����һ����̬����
        lv=(ListView)findViewById(R.id.lv);
        //�������ڴ������
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<5;i++)  
        {  
            HashMap<String, Object> map = new HashMap<String, Object>();  
            map.put("img", R.drawable.pdf);//����ͼƬ            
            map.put("title", "��"+i+"��");  
            map.put("info", "���ǵ�"+i+"��");  
            listItem.add(map);  
        } 
        //�������ݵİ�

        SimpleAdapter adapter = new SimpleAdapter(this,listItem,R.layout.item,
                new String[]{"title","info","img"},
                new int[]{R.id.title,R.id.info,R.id.img});
        //Ϊlistview��������
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				setTitle("�����˵�"+arg2+"��");
			}

        });
        
        //activity����ת����ת��PIN������ҳ��
        bt_jump=(Button)findViewById(R.id.jump);
        bt_jump.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Main.this, InputPin.class);
				Main.this.startActivity(intent);
			}
		});
        
        //����ļ��ļ���
        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Main.this,MyFileManager.class);  
                startActivityForResult(intent, FILE_RESULT_CODE); 
			}
		});
          
        	
     	// Set up the ViewPager with the sections adapter.
     	
     	if(null == mFileObserver) {  
     		String sdpath = Environment.getExternalStorageDirectory().getPath() + "/";		
     		String path = sdpath+ "tmp";
            mFileObserver = new SDCardFileObserver(path,FileObserver.OPEN|FileObserver.CLOSE_NOWRITE);  
            mFileObserver.startWatching(); //��ʼ����  
     	}  
    }
	
	@Override
	protected void onDestroy() {  
		super.onDestroy();
		if(null != mFileObserver) 
			mFileObserver.stopWatching(); //ֹͣ����  
	}  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
	 
	// #######################  
    /** 
     * ���ܽ��� 
     */  
    private class EncryptionOrDecryptionTask extends  
            AsyncTask<Void, Void, Boolean> {  
  
        private String mSourceFile = "";  
        private String mNewFilePath = "";  
        private String mNewFileName = "";  
        private String mSeed = "";  
        private boolean mIsEncrypt = false;  
  
        public EncryptionOrDecryptionTask(boolean isEncrypt, String sourceFile,  
                String newFilePath, String newFileName, String seed) {  
            this.mSourceFile = sourceFile;  
            this.mNewFilePath = newFilePath;  
            this.mNewFileName = newFileName;  
            this.mSeed = seed;  
            this.mIsEncrypt = isEncrypt;  
        }  
  
        @Override  
        protected Boolean doInBackground(Void... params) {  
  
            boolean result = false;  
  
            if (mIsEncrypt) {  
                result = mAESHelper.AESCipher(Cipher.ENCRYPT_MODE, mSourceFile,  
                        mNewFilePath + mNewFileName, mSeed);  
            } else {  
                result = mAESHelper.AESCipher(Cipher.DECRYPT_MODE, mSourceFile,  
                        mNewFilePath + mNewFileName, mSeed);  
            }  
  
            return result;  
        }  
    }  
}  




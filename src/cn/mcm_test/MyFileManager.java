package cn.mcm_test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.security.*;

import javax.crypto.Cipher;

import cn.mcm.cryption.Cryption;
import cn.mcm.listener.FileOpener;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyFileManager extends ListActivity {
	private List<String> items = null;
	private List<String> paths = null;
	private String rootPath = "/sdcard/";
	private String curPath = "/";
	private TextView mPath;
	private final static String TAG = "bb";
	private Context context;
	
	private static Cipher ecipher;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fileselect);
		mPath = (TextView) findViewById(R.id.mPath);
		/*
		Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
		buttonConfirm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent data = new Intent(MyFileManager.this, Main.class);  
                Bundle bundle = new Bundle();  
                bundle.putString("file", curPath);  
                data.putExtras(bundle);  
                setResult(2, data); 
				finish();
			}
		});
		Button buttonCancle = (Button) findViewById(R.id.buttonCancle);
		buttonCancle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		*/
		getFileDir(rootPath);
	}
	private void getFileDir(String filePath) {
		mPath.setText(filePath);
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles();
		if (!filePath.equals(rootPath)) {
			items.add("b1");
			paths.add(rootPath);
			items.add("b2");
			paths.add(f.getParent());
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			items.add(file.getName());
			paths.add(file.getPath());
		}
		setListAdapter(new MyAdapter(this, items, paths));
	}
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		File file = new File(paths.get(position));
		//Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
		
		if (file.isDirectory()) {
			//buttonConfirm.setClickable(false);
			curPath = paths.get(position);
			getFileDir(paths.get(position));
		} else {
			//可以打开文件
			//弹药通过我们设定的渠道
			
			//buttonConfirm.setClickable(true);
			//curPath = paths.get(position);
			FileOpener fo=new FileOpener(context);
			file=new File(paths.get(position));
			fo.openFile(file);
		}
	}
	
}

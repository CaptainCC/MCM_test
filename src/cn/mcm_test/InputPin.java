package cn.mcm_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.mcm.key.GenKey;
import cn.mcm.key.GetInfo;
import cn.mcm_test.R;

public class InputPin extends Activity{
	private EditText et;
	private Button bt;
	private TextView pin_tv;
	GetInfo mgi=new GetInfo();
	String str;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputpin);
        
        pin_tv=(TextView)findViewById(R.id.pinview);
        et=(EditText)findViewById(R.id.inputpin);
        bt=(Button)findViewById(R.id.submit);
        
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str= et.getText().toString();
				
				Toast toast=Toast.makeText(InputPin.this,"ÊäÈëµÄPINÂëÎª£º"+mgi.getPin(str), Toast.LENGTH_LONG);
				toast.show();
				finish();
			}
		});
	}

}

package com.hu.myxposed.demo.dexhook;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hu.myxposeddemo.R;

public class DexHookMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dex_hook_main);
		SharedPreferences preferences=getSharedPreferences("dexhook_sp",MODE_WORLD_READABLE);
		String packageName=preferences.getString("package_name", "com.dingli.pioneer");
		TextView packageNameTextureView=(TextView)findViewById(R.id.packageNameTextView);
		String dumpStatus=packageName.length()==0 ? "Dumping all packages" : "Current package name is "+packageName;
		packageNameTextureView.setText(dumpStatus);
		
        TextView packageNameInputBox = (TextView) findViewById(R.id.packageNameInput);
        packageNameInputBox.setText(packageName);

        String dirPath = preferences.getString("dir_path", "/sdcard");
        TextView dirPathTextView = (TextView) findViewById(R.id.dirPathTextView);
        dirPathTextView.setText("Current output directory is " + dirPath);
        TextView dirPathInputBox = (TextView) findViewById(R.id.dirPathInput);
        dirPathInputBox.setText(dirPath);
	}


    public void apply(View view) {
        EditText packageNameInput = (EditText) findViewById(R.id.packageNameInput);
        String packageName = packageNameInput.getText().toString();

        EditText dirPathInput = (EditText) findViewById(R.id.dirPathInput);
        String dirPath = dirPathInput.getText().toString();

        SharedPreferences pref = getSharedPreferences("dexhook_sp", MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("package_name", packageName);
        editor.putString("dir_path", dirPath);
        editor.apply();
        editor.commit();  
        
        TextView packageNameTextView = (TextView) findViewById(R.id.packageNameTextView);
        String dumpStatus = packageName.length() == 0 ? "Dumping all packages" : "Current package name is "  + packageName;
        packageNameTextView.setText(dumpStatus);

        TextView dirPathTextView = (TextView) findViewById(R.id.dirPathTextView);
        dirPathTextView.setText("Current output directory is " + dirPath);

        Toast.makeText(this, "Settings successfully saved. Please reboot for the changes to take effect.", Toast.LENGTH_SHORT).show();
    }

}

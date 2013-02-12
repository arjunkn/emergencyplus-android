package com.beshi.grill.emerygency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity  implements OnClickListener{
	
	private Button fireButton,hospitalButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        fireButton = (Button) findViewById(R.id.fireBtn);
        hospitalButton = (Button) findViewById(R.id.hospitalBtn);
        
        fireButton.setOnClickListener(this);
        hospitalButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		
		switch (id) {
		case R.id.hospitalBtn:
			gotoHospital();
			break;
			
		case R.id.fireBtn:
			gotoFire();
			break;

		default:
			break;
		}
		
	}
	
	public void gotoFire(){
		Intent fireIntent = new Intent(this,ServiceActivity.class);
		fireIntent.putExtra("service_type", "fire");
		startActivity(fireIntent);
	}
	
	public void gotoHospital(){
		Intent hospitalIntent = new Intent(this,ServiceActivity.class);
		hospitalIntent.putExtra("service_type", "hospital");
		startActivity(hospitalIntent);		
	}
}
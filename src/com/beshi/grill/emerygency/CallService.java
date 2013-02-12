package com.beshi.grill.emerygency;

import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CallService extends Activity implements OnClickListener {
	private LinearLayout ll;
	String total = "";
	private Button[] buttons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ll = new LinearLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		ll.setLayoutParams(params);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.CENTER_HORIZONTAL);
		setContentView(ll);

		try {
			Bundle extras = getIntent().getExtras();
			total = extras.getString("numbers");

			parseNumbers();
		}

		catch (Exception e) {

		}
	}

	public void parseNumbers() {
		StringTokenizer tok = new StringTokenizer(total);
		int count = tok.countTokens();
		int i = 0;

		if (count <= 0) {

		}

		else {
			buttons = new Button[count];
			while (tok.hasMoreTokens()) {
				String num = tok.nextToken();
				buttons[i] = new Button(this);
				buttons[i].setText("Call : " + num);
				buttons[i].setTag(num);
				buttons[i].setBackgroundResource(R.drawable.himel_back_green);
				buttons[i].setOnClickListener(this);
				buttons[i].setTextSize(22);
				buttons[i].setTextColor(Color.WHITE);
				buttons[i].setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				ll.addView(buttons[i]);
				i++;
			}
		}
	}

	@Override
	public void onClick(View v) {
		String numb = (String) v.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + numb));
		startActivity(callIntent);
	}

}

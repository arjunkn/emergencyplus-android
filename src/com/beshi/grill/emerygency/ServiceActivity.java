package com.beshi.grill.emerygency;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceActivity extends Activity implements PostAsyncUser {
	private String serviceTypeString = "";
	private ListView lv;
	private TextView tv;
	ServiceData[] myData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicelist);

		lv = (ListView) findViewById(R.id.service_list);
		tv = (TextView) findViewById(R.id.service_name);

		try {
			Bundle extras = getIntent().getExtras();
			serviceTypeString = extras.getString("service_type");
			tv.setText(serviceTypeString);
			getResultsFromServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getResultsFromServer() throws Exception {
		JSONObject ob = new JSONObject();
		ob.put("url", "http://emergencyplus.net23.net/GetInfo.php");

		JSONArray arr = new JSONArray();

		JSONObject ob1 = new JSONObject();
		ob1.put("label", "type");
		ob1.put("value", serviceTypeString);

		arr.put(ob1);

		ob.put("parameters", arr);

		PostAsyncAdapter pad = new PostAsyncAdapter(this, ob);
		pad.startFetching();
	}

	public void populateList() {
		ServiceDataAdapter adapter = new ServiceDataAdapter(this, myData);
		lv.setAdapter(adapter);
	}

	public void getResult(String resp) {
		if (resp.length() != 0) {

			try {
				JSONObject ob = new JSONObject(resp);

				if (ob.getString("status").equals("ok")) {
					JSONArray dataArray = ob.getJSONArray("data");
					int count = dataArray.length();

					if (count != 0) {

						myData = new ServiceData[count];

						for (int i = 0; i < count; i++) {
							JSONObject job = dataArray.getJSONObject(i);

							myData[i] = new ServiceData();
							myData[i].setName(job.getString("service_name"));
							myData[i].setAddress(job.getString("location"));
							myData[i].setTelephone(job.getString("telephone"));
							myData[i].setType(serviceTypeString);
						}

						populateList();
					} else {
						tv.setText("No data for " + serviceTypeString
								+ " service");
					}
				}

				else {
					tv.setText(ob.getString("message"));
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

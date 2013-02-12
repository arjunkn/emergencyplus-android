package com.beshi.grill.emerygency;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceDataAdapter extends ArrayAdapter<ServiceData> implements
		OnClickListener {
	final Context context;
	final ServiceData[] data;

	public ServiceDataAdapter(Context context, ServiceData[] data) {
		super(context, R.layout.servicelist_row, data);
		this.context = context;
		this.data = data;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater
				.inflate(R.layout.servicelist_row, parent, false);

		TextView name = (TextView) rowView
				.findViewById(R.id.serviceprovider_name);
		TextView address = (TextView) rowView
				.findViewById(R.id.serviceprovider_address);
		Button callButton = (Button) rowView.findViewById(R.id.callButton);
		callButton.setTag(data[position].getTelephone());
		callButton.setOnClickListener(this);

		name.setText(data[position].getName());
		address.setText(data[position].getAddress());

		return rowView;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		switch (id) {
		case R.id.callButton:

			String numbers = (String) v.getTag();
			//Toast.makeText(context, numbers, Toast.LENGTH_LONG).show();
			makeCall(numbers);
			break;

		default:
			break;
		}
	}
	
	private void makeCall(String numb){
		Intent callIntent = new Intent(context,CallService.class);
		callIntent.putExtra("numbers", numb);
		context.startActivity(callIntent);
	}

}
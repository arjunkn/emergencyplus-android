package com.beshi.grill.emerygency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class PostAsync extends AsyncTask<JSONObject, Integer,String>{
	//static String base_url = "http://10.0.2.2/MediBuddy/";
	private String resultString;
	private PostAsyncAdapter pad=null;
	
	public PostAsync() {
	}

	public PostAsync(PostAsyncAdapter postAsyncAdapter) {
		// TODO Auto-generated constructor stub
		this.pad = postAsyncAdapter;
	}

	@Override
	protected String doInBackground(JSONObject... params) {
		JSONObject ob = params[0];
		connect_post(ob);
		return null;
	}
	
	protected void onPostExecute(String result) {
	    super.onPostExecute(result);
	    sendResult();
	}
	
	public void sendResult(){
		try{
			pad.getResult(resultString);
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}

	/**************** no need to modify *************************/
	private void connect_post(JSONObject obj){
		try {
			String url = obj.getString("url");
			
			HttpClient httpclient=new DefaultHttpClient();
			HttpPost httppost=new HttpPost(url);
			
			try{
				JSONArray paramArray=obj.getJSONArray("parameters");
				if(paramArray!=null){
					List<NameValuePair> namePair=new  ArrayList<NameValuePair>(paramArray.length());
					for(int i=0;i<paramArray.length();i++){
						JSONObject job = paramArray.getJSONObject(i);
						namePair.add(new BasicNameValuePair(job.getString("label"), job.getString("value")));
						httppost.setEntity(new UrlEncodedFormEntity(namePair));
					}
				}	
			}
			catch (Exception e) {}//

			HttpResponse response =httpclient.execute(httppost);
			int code=response.getStatusLine().getStatusCode();
			
			Log.d("RESPONSE CODE :", "code is "+code);
			
			resultString=inputStreamToString(response.getEntity().getContent());
			
			/*
			if(code==200 || code==303){
				resultString=inputStreamToString(response.getEntity().getContent());
			}
			
			else if(code==404){
				resultString = "Not found";
			}
			else{
				resultString = "Not Active";
			}*/
			
			
			Log.d("Result String ->>  ", resultString);
			
		} 
		catch (Exception e) {
			Log.d("rupam","connect_post_error>>"+e.getMessage());
		}
	}


	/****************** no need to modify it *******************/
	private String inputStreamToString(InputStream is) {
		Log.i("tag","after response ");
		String s = "";
	    String line = "";
	    
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    
	    
	    try {
			while ((line = rd.readLine()) != null) { s += line; }
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return s;
	}
}
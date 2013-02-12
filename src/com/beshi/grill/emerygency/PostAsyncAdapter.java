package com.beshi.grill.emerygency;

import org.json.JSONObject;

public class PostAsyncAdapter {
	
	private PostAsyncUser user;
	private JSONObject incomingData;
	
	public PostAsyncAdapter(PostAsyncUser u,JSONObject id) {
		// TODO Auto-generated constructor stub
		this.user = u;
		this.incomingData = id;
	}
	
	public void startFetching(){
		PostAsync pos = new PostAsync(this);
		pos.execute(incomingData);
	}
	
	public void getResult(String resp){
		this.user.getResult(resp);
	}
	
}

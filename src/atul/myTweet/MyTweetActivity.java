package atul.myTweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyTweetActivity extends Activity {
    	public class getData extends AsyncTask<String, Void , String>{
    		protected void onPreExecute(String f){
    			f = "Blah";
    		}
    		@Override
    		protected String doInBackground (String... url) {
    			BufferedReader in = null;
    			String data = null;
    			try{
    				HttpClient client = new DefaultHttpClient();
    				URI website = new URI("http://www.iitd.ac.in");
    				HttpGet request = new HttpGet();
    				request.setURI(website);
    				HttpResponse response = client.execute(request);
    				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    				StringBuffer sb = new StringBuffer("");
    				String l = "";
    				String nl = System.getProperty("line.separator");
    				while((l=in.readLine()) != null){
    					sb.append(l+nl);
    					}
    				in.close();
    				data = sb.toString();
    				Log.d("DATA",data);
    				//Data = data;
    				//Log.d("Data", Data);
    				return data;
    			} catch (Exception e) {
    				Log.d("ErrorHereoooHere", e.toString());
    				return null;
    			}finally{
    				if(in !=null){
    					try{
    						in.close();
    						return data;
    					}catch (Exception e) {
    						Log.d("ErrorHere", e.toString());
    					}
    				}
    			}
    		}
    		protected void onPostExecute(String result){
    			httpStuff.setText(result);
    			//Data = result;
    			Log.d("Result", result);
    			
    		}

	}
    public class Read extends AsyncTask<String, Integer, String>{
    	//EditText name = (EditText)findViewById(R.id.editUserName);
		@Override
		protected void onPostExecute(String result) {
			httpStuff.setText(result);
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				json = lastTweet("atuljangra");
				//Log.d("UserName", UserName);
				if(json== null)
					return null;
				return json.getString("text");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
    	
    	
    }
	/** Called when the activity is first created. */
	TextView httpStuff;
	EditText name;
	//String UserName = "yo";
	HttpClient client;
	JSONObject json;
	final static String URL = "http://api.twitter.com/1/statuses/user_timeline.json?screen_name=";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     //   name = (EditText)findViewById(R.id.editUserName);
   //     UserName = name.getText().toString();
       // Log.d("UserName", UserName);
        httpStuff = (TextView)findViewById(R.id.tweetSpace);
        client = new DefaultHttpClient();
        try {
//        	getData test = new getData();
//        	test.execute("something");
//        	Log.d("after", Data);
        	new Read().execute("text"); //search for this string in the jsononject array
		} catch (Exception e) {
			httpStuff.setText("Error");
			Log.d("Error", e.toString());
		}
        
    }
    public JSONObject lastTweet(String Username) throws ClientProtocolException, IOException , JSONException{
    	StringBuilder url = new StringBuilder(URL);
    	url.append(Username);
	    HttpGet get = new HttpGet(url.toString());
	    HttpResponse response = client.execute(get);
	    int status = response.getStatusLine().getStatusCode();
	    if(status== 200){
	    	HttpEntity e = response.getEntity();
	    	String data = EntityUtils.toString(e);
	    	JSONArray timeline = new JSONArray(data);
	    	JSONObject last = timeline.getJSONObject(0);
	    	return last;
	    }
	    if(status==400){
	    	Toast.makeText(getApplicationContext(), "You have reached your hourly limit", Toast.LENGTH_SHORT);
	    	return null;
	    }else{
	    	Toast.makeText(MyTweetActivity.this, "error", Toast.LENGTH_SHORT);
	    	Log.d("Error", String.valueOf(status));
	    	return null;
	    }
	    
	    }
}
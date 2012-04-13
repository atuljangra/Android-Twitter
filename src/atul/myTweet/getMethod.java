package atul.myTweet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class getMethod extends AsyncTask<String, Void , String>{
		
		private Exception exception;
		
		@Override
		protected String doInBackground (String... url) {
			BufferedReader in = null;
			String data = null;
			try{
				HttpClient client = new DefaultHttpClient();
				URI website = new URI("http://www.mybringback.com");
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
				Log.d("data",data);
				return data;
			} catch (Exception e) {
				Log.d("ErrorHereHere", e.toString());
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
}
package com.example.home_onlinsale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends Activity implements OnClickListener {
	
	EditText etId, etTitle, etDesc, etPrice, etSaler, etDate;
	Button btnUpdate, btnDelete;
	int pro_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		etId = (EditText)findViewById(R.id.etId);
		etTitle = (EditText)findViewById(R.id.etTitle);
		etDesc = (EditText)findViewById(R.id.etDesc);
		etPrice = (EditText)findViewById(R.id.etPrice);
		etSaler = (EditText)findViewById(R.id.etSaler);
		etDate = (EditText)findViewById(R.id.etDate);
		btnUpdate = (Button)findViewById(R.id.btnUpdate);
		btnDelete = (Button)findViewById(R.id.btnDelete);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		
		Bundle extras = getIntent().getExtras();
		if(extras == null){
			return;
		}
		
		pro_id = extras.getInt("pro_id");
		etId.setText("" + pro_id);
		
		ReadData task1 = new ReadData();
		task1.execute(new String[]{"http://10.0.2.2:8012/home_onlinesale/?format=json&id=" + pro_id});
		
	}
	
	Product product = new Product();
	
	private class ReadData extends AsyncTask<String, Void, Boolean>{

		private ProgressDialog dialog = new ProgressDialog(EditActivity.this);
		private String error;
		
		InputStream is1;
		String text = "";
		
		@Override
		protected void onPreExecute() {
			dialog.setMessage("Reading Data...");
			dialog.show();
		}
		

		@Override
		protected Boolean doInBackground(String... urls) {
			for(String url: urls){
				try {
					HttpClient client = new DefaultHttpClient();
					HttpPost post = new HttpPost(url); 
					HttpResponse response = client.execute(post);
					is1 = response.getEntity().getContent();
					
				} catch (ClientProtocolException e) {
					error = "ClientProtocolException: " + e.getMessage();
					return false;
				} catch (IOException e) {
					error = "ClientProtocolException: " + e.getMessage();
				}
				
			}
			
			BufferedReader reader;
						
			try {
				reader = new BufferedReader(new InputStreamReader(is1 ,"iso-8859-1"), 8);
				String line = null;
				
				while ((line = reader.readLine()) != null) {
					text += line + "\n";
				}
				
				is1.close();	
				
			} catch (UnsupportedEncodingException e) {
				error = "Unsupport Encoding: " + e.getMessage();
			} catch (IOException e) {
				error = "Error IO: " + e.getMessage();
			}
						
			try {
				JSONArray jArray = new JSONArray(text);
				for(int i=0; i<jArray.length(); i++){
					JSONObject json = jArray.getJSONObject(i);
					
					product.setId(json.getInt("pro_id"));
					product.setTitle(json.getString("pro_title"));
					product.setDesc(json.getString("pro_desc"));
					product.setPrice(json.getDouble("pro_price"));
					product.setSaler(json.getString("pro_saler"));
					product.setDate(json.getString("pro_date"));					
														
				}
			} catch (JSONException e) {
				error = "Error Convert to JSON or Error JSON Format: " + e.getMessage();
			}
			
			
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(dialog.isShowing()){
				dialog.dismiss();
			}
			
			if(result == false){
				Toast.makeText(EditActivity.this, error, Toast.LENGTH_LONG).show();
			}
			else{
				etTitle.setText(product.getTitle());
				etDesc.setText(product.getDesc());
				etPrice.setText("" + product.getPrice());
				etSaler.setText(product.getSaler());
				etDate.setText(product.getDate());
			}
		}
		
	}//End of private class ReadData

	String updateTrigger = "";
	
	@Override
	public void onClick(View sender) {
		if(sender.getId() == R.id.btnUpdate){
			UpdateData taskUpdate = new UpdateData();
			updateTrigger = "Update";
			taskUpdate.execute(new String[]{"http://10.0.2.2:8012/home_onlinesale/edit.php?id="+pro_id});
			Intent in = new Intent(this, MainActivity.class);
			startActivity(in);
		}
		else if(sender.getId() == R.id.btnDelete){
			Builder msgBox = new AlertDialog.Builder(this);
			msgBox.setTitle("Delete Confirmation");
			msgBox.setMessage("Are you sure to delete it?");
			msgBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			msgBox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					UpdateData taskUpdate = new UpdateData();
					updateTrigger = "Delete";
					taskUpdate.execute(new String[]{"http://10.0.2.2:8012/home_onlinesale/edit.php?id="+pro_id});
					Intent in = new Intent(EditActivity.this, MainActivity.class);
					startActivity(in);					
				}
			});
			msgBox.show();
			

		}
		
	}
	
	private class UpdateData extends AsyncTask<String, Void, Boolean>{
		private ProgressDialog dialog = new ProgressDialog(EditActivity.this);
		private String error;
		
		String text = "";
		
		@Override
		protected void onPreExecute() {
			dialog.setMessage("Editting Data...");
			dialog.show();
		}
		
		InputStream is1;
		@Override
		protected Boolean doInBackground(String... urls) {
			for(String url: urls){
				try {
					ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
					pairs.add(new BasicNameValuePair("btnSubmit", updateTrigger));
					pairs.add(new BasicNameValuePair("txtProTitle", etTitle.getText().toString()));
					pairs.add(new BasicNameValuePair("txtProDesc", etDesc.getText().toString()));
					pairs.add(new BasicNameValuePair("txtProPrice", etPrice.getText().toString()));
					pairs.add(new BasicNameValuePair("txtProSaler", etSaler.getText().toString()));
					
					HttpClient client = new DefaultHttpClient();
					HttpPost post = new HttpPost(url); 
					post.setEntity(new UrlEncodedFormEntity(pairs));
					HttpResponse response = client.execute(post);
					is1 = response.getEntity().getContent();
					
				} catch (ClientProtocolException e) {
					error = "ClientProtocolException: " + e.getMessage();
					return false;
				} catch (IOException e) {
					error = "ClientProtocolException: " + e.getMessage();
				}				
			}
			
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(dialog.isShowing()){
				dialog.dismiss();
			}
			
			if(result == false){
				Toast.makeText(EditActivity.this, error, Toast.LENGTH_LONG).show();
			}
			else{
				if(is1 == null){
					Toast.makeText(EditActivity.this, "Sending Wrong Parameters", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(EditActivity.this, "Edit Success", Toast.LENGTH_LONG).show();
					
				}
				
				
			}
		}
	}

}

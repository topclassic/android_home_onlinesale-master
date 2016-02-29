package com.example.home_onlinsale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	ListView lvProduct;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lvProduct = (ListView)findViewById(R.id.lvProduct);
		lvProduct.setOnItemClickListener(this);
		
		ReadData task1 = new ReadData();
		task1.execute(new String[]{"http://10.0.2.2:8012/home_onlinesale/index.php?format=json"});
	}
	
	ArrayList<Product> listProduct;
	ProductArrayAdapter adapter;
	
	private class ReadData extends AsyncTask<String, Void, Boolean>{

		private ProgressDialog dialog = new ProgressDialog(MainActivity.this);
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
			
			listProduct = new ArrayList<Product>();
			
			try {
				JSONArray jArray = new JSONArray(text);
				for(int i=0; i<jArray.length(); i++){
					JSONObject json = jArray.getJSONObject(i);
					
					Product product = new Product();
					product.setId(json.getInt("pro_id"));
					product.setTitle(json.getString("pro_title"));
					product.setDesc(json.getString("pro_desc"));
					product.setPrice(json.getDouble("pro_price"));
					product.setSaler(json.getString("pro_saler"));
					product.setDate(json.getString("pro_date"));
					
					listProduct.add(product);									
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
				Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
			}
			else{
				adapter = new ProductArrayAdapter(
						MainActivity.this, 
						R.layout.list_view, 
						listProduct);
				lvProduct.setAdapter(adapter);
			}
		}
		
	}//End of private class ReadData
	
	private class ProductArrayAdapter extends ArrayAdapter<Product>{

		Context context;
		int resource;
		List<Product> proList;
		
		public ProductArrayAdapter(Context context, int resource,
				List<Product> proList) {
			super(context, resource, proList);
			this.context = context;
			this.resource = resource;
			this.proList = proList;			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View listItem = convertView;
			
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		    listItem = inflater.inflate(resource, parent, false);
		    
		    TextView tvProID = (TextView) listItem.findViewById(R.id.tvProID);
		    TextView tvProTitle = (TextView) listItem.findViewById(R.id.tvProTitle);
		    TextView tvPrice = (TextView) listItem.findViewById(R.id.tvPrice);
		    
		    Product pro = listProduct.get(position);
		    
		    tvProID.setText("" + pro.getId());
		    tvProTitle.setText(pro.getTitle());
		    tvPrice.setText(pro.getPrice().toString());
		     			
			return listItem;
		}
			
	}//end of private class ProductArrayAdapter

	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View clickedView, int pos, long id) {
		Product clickedProduct = (Product) adapter.getItem(pos);
		int pro_id = clickedProduct.getId();
		
		Intent in = new Intent(this, EditActivity.class);
		in.putExtra("pro_id", pro_id);
		startActivity(in);
		
	}
	

}

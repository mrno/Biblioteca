package edu.utn;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends Activity {
    /** Called when the activity is first created. */
	public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_URL = "urllogin";
    private EditText txtUser;
    private EditText txtPass;
 private boolean flag=false;
 
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		  txtUser = (EditText) findViewById(R.id.txtUser);
	       txtPass = (EditText) findViewById(R.id.txtPass);
        
        Button btnIngresar = (Button) findViewById(R.id.btnIngresar);
        
        btnIngresar.setOnClickListener(new OnClickListener() {
           
            public void onClick(View v) {
            	String rdo=null;
            	String usuario =txtUser.getText().toString();
            	String pass =txtPass.getText().toString();
            	Bundle miBundle = new Bundle();
    			miBundle.putString("user", usuario);
    			miBundle.putString("pass", pass);
    			miBundle.putString("rdo", null);
    		
    		     
    		        Dispacher(getLogin(miBundle));
    		
 
            	
          				}
       
        });
	}

    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
        

   }
    
    private Bundle getLogin(Bundle miBundle){
    	String keywords=null;
    	String url = getString(R.string.uriLoginIP);
    //String url = "http://190.226.19.47:8080/BibliotecaVirtual/resources/login";
    
    	
   // String url = "http://biblioVirtual.no-ip.org/BibliotecaVirtual/resources/login";
    	HttpEntity entity = null;
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpContext localContext = new BasicHttpContext();
    	HttpPost httpH = null;
    	HttpResponse response;
	
    	try {
    		 httpH = new HttpPost(url);	
        
    	} catch (Exception e1) {
    		// TODO Auto-generated catch block
    		Log.i("algo origino lo sgte",e1.getCause().toString() , e1);
    		Toast.makeText(this, e1.getMessage(), Toast.LENGTH_LONG).show();
    		flag=true;
    	}
    	String datosquevan = miBundle.getString("user") + "," + miBundle.getString("pass");
    	HttpParams httpParameters = new BasicHttpParams();
    	miBundle.putString("rdo", "fail");
		
    	try {
			httpH.setEntity(new StringEntity(datosquevan));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			flag=true;
			e1.printStackTrace();
			Log.i("algo origino lo sgte",e1.getCause().toString() , e1);
		}
    	try {
    		response = httpClient.execute(httpH);
    		entity = response.getEntity();
    		keywords = EntityUtils.toString(entity);
    		miBundle.putString("rdo", keywords);        	
    	} catch (ClientProtocolException e) {
    		flag=true; 		
    		e.printStackTrace();
    		 		//dialog.dismiss();
    		Log.i("algo origino lo sgte",e.getCause().toString() , e);
    		Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    	} catch (IOException e) {
    		 		e.printStackTrace();
    		 		flag=true;
    		 		//dialog.dismiss();
    		 		Log.i("AAAAAAalgo origino lo sgte",e.getCause().toString() , e);
    		Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    		
    		  	}
    	catch (Exception e){
    		e.printStackTrace();
    		//dialog.dismiss();
    		Log.i("AAAAAAalgo origino lo sgte",e.getCause().toString() , e);
    		Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
    		flag=true;
    	}
    
    	return miBundle;
		
  
    }
    
    private void Dispacher(Bundle rdoBundle){
    	
    	if (rdoBundle.getString("rdo").contentEquals("ok")){
			Intent miIntent = new Intent(this,MenuActivity.class);
			miIntent.putExtras(rdoBundle);
			startActivity(miIntent);
			
	}else{
		if(!flag){
			Toast.makeText(this,"Usuario o Contrasenia Incorrecto", Toast.LENGTH_LONG).show();	
		}
		
		
	}
    	
    }
    

}
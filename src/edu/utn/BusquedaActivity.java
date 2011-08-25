package edu.utn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import edu.utn.frre.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import edu.utn.frre.BiblioContentProvider;
import edu.utn.frre.BiblioContentProvider;

public class BusquedaActivity extends Activity {
	//private BiblioContentProvider DBHelper;
	private Bundle miBundle;
	private Button btnBuscar ;
	private Button btnBuscarAvanzado;
	private BiblioContentProvider DBHelper;
	  
	  
	  
	private void BuscarTodo(){
		DBHelper = new BiblioContentProvider(this);
		DBHelper.open();
		TextView txtAbuscar = (TextView) findViewById(R.id.txtAbuscar);
	miBundle.putString("tipoBusqueda", "todo");
	miBundle.putString("filtro","algunaPalabra");
	//miBundle.putString("aBuscar"," ");
	miBundle.putString("aBuscar", txtAbuscar.getText().toString());
	//miBundle.putString("offset","0");
	  DBHelper.BorrarListado();
		try {
			DBHelper.traerJson(miBundle);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent miIntent = new Intent(this,ResultadoBusquedaListActivity.class);
		miIntent.putExtras(miBundle);
		startActivity(miIntent);
		
	}
	private void BusquedaAvanzada(){
        Intent miIntent = new Intent(this,BusquedaAvanzaActivity.class);
startActivity(miIntent);

	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		btnBuscarAvanzado.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				/* Intento para llamar a la actividad que realiza busqueda*/
	
 BusquedaAvanzada();
 }
		});
		btnBuscar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				/* Intento para llamar a la actividad que realiza busqueda*/
	
	BuscarTodo();
	
	
			}
		});
	     
	
		
	}

    public void onCreate(Bundle savedInstanceState) {
	     	 super.onCreate(savedInstanceState);
	        setContentView(R.layout.busqueda);
	        
	        btnBuscar = (Button) findViewById(R.id.btnBuscar1);
	        btnBuscarAvanzado = (Button) findViewById(R.id.btnBusquedaAvanzada);
	        miBundle = this.getIntent().getExtras();
	        TextView txtUser = (TextView) findViewById(R.id.lblUserLogeado);
	        txtUser.setText(miBundle.getString("user"));
	         }
}

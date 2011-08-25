package edu.utn;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class BusquedaAvanzaActivity extends Activity {
	private Bundle miBundle;
	private Button btnBuscar ;
	private RadioButton rbtnActor;
	private RadioButton rbtnTitulo;
	private RadioButton rbtnCategoria;
	private RadioButton rbtnAlgPal;
	private RadioButton rbtnFraExa;
	private RadioButton rbtnComCon;
	
	private BiblioContentProvider DBHelper;
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		btnBuscar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbtnTitulo = (RadioButton) findViewById(R.id.rbtnTitulo);
				rbtnCategoria = (RadioButton) findViewById(R.id.rbtnCategoria);
				rbtnFraExa = (RadioButton) findViewById(R.id.rbtnfraexa);
				rbtnComCon = (RadioButton) findViewById(R.id.rbtnComCon);
			BuscarTodo();
			}
		});
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busquedaavanzada);
		btnBuscar = (Button) findViewById(R.id.btnAbuscarAvanzada);

		RadioButton rbtnAtutor = (RadioButton) findViewById(R.id.rbtnAutor);
		RadioButton rbtnAlgPal = (RadioButton) findViewById(R.id.rbtnalgpal);
		rbtnAtutor.setChecked(true);
		rbtnAlgPal.setChecked(true);
	}
	private void BuscarTodo(){
		DBHelper = new BiblioContentProvider(this);
		DBHelper.open();
		TextView txtAbuscar = (TextView) findViewById(R.id.txtAbuscarAvanzado);
		miBundle = new Bundle();
		
		if (rbtnFraExa.isChecked()){
			miBundle.putString("filtro", "fraseExacta");	
		}else if (rbtnComCon.isChecked()){
			miBundle.putString("filtro", "comienzaCon");
		}
		else{
			miBundle.putString("filtro", "algunaPalabra");
		}
		
		if (rbtnCategoria.isChecked()){
			miBundle.putString("tipoBusqueda", "categoria");
		}else if (rbtnTitulo.isChecked()){
			miBundle.putString("tipoBusqueda", "titulo");
		} else{
			miBundle.putString("tipoBusqueda", "autor");
		}
		
		
		
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
		//miIntent.putExtras(rdoBundle);
		startActivity(miIntent);
		
	}
}

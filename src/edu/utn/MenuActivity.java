package edu.utn;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.utn.frre.R;

public class MenuActivity extends Activity{

	private Bundle miBundle;
		@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		
	}

		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.menu);
			TextView miUserLogeado = (TextView) findViewById(R.id.lblUserLogeado);
			
			miBundle = this.getIntent().getExtras();
			
			
			miUserLogeado.setText(miBundle.getString("user"));
			
			/* Instanciamiento y llamada a la Accion del Boton "Realizar Busqueda*/
	
			Button realizar_reserva = (Button) findViewById(R.id.btnRealizarReserva);
			realizar_reserva.setOnClickListener(new View.OnClickListener() {
			
				public void onClick(View v) {
					/* Intento para llamar a la actividad que realiza busqueda*/
					//Bundle miBundle = new Bundle();
					//String usuario =getText().toString();
					//miBundle.putString("user", usuario);
					Dispacher(miBundle);
					
					//miIntentBusqueda.putExtras(mi)
					//Log.d("#######cualquier cosa", "entro");
					
					
					
				}
			});
			
	
			
			
			/* Instanciamiento y llamada a la Accion del Boton "Cancelar Reserva*/
			Button cancelar_reserva = (Button) findViewById(R.id.btnCancelarReserva);
			cancelar_reserva.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					/* Intento para llamar a la actividad que cancela reserva*/
					
				}
			});
			
			
			/* Instanciamiento y llamada a la Accion del Boton "Listado de Reservas*/
			Button listar_reservas = (Button) findViewById(R.id.btnListadoReservas);
			listar_reservas.setOnClickListener(new View.OnClickListener() {
			
				public void onClick(View v) {
					/* Intento para llamar a la actividad que lista reservas*/
					
				}
			});
			
			
			/* Instanciamiento y llamada a la Accion del Boton "Listado de Vencimientos*/
			Button listar_vencimientos = (Button) findViewById(R.id.btnListadoVencimientos);
			listar_vencimientos.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					/* Intento para llamar a la actividad que lista vencimientos*/
					
				}
			});
				
		
		}
		
		
	    private void Dispacher(Bundle rdoBundle){
	    	
			
				Intent miIntent = new Intent(this,BusquedaActivity.class);
				miIntent.putExtras(rdoBundle);
				startActivity(miIntent);
		
	    	
	    }
	
	}

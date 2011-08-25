package edu.utn;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.utn.frre.BiblioContentProvider;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
public class BiblioContentProvider {
	private final Context mCtx;
	    public static final String KEY_TITULO = "titulo";
	    public static final String KEY_AUTOR = "autor";
	    public static final String KEY_SOLO_EN_BIBLIO = "solo_en_biblio";
	    public static final String KEY_ISBN="isbn";
	    public static final String KEY_NRO_INV = "nroInv";
	    public static final String KEY_RESERVADO = "reservado";
	    public static final String KEY_ROWID = "_id";
	    private DatabaseHelper mDbHelper;
		private SQLiteDatabase mDb;
		private static final String DATABASE_NAME = "Busqueda";
		 private static final int DATABASE_VERSION = 2;
		 private static final String DATABASE_TABLE = "Listado";
		 private static final String TAG = "ConsultaDbAdapter";
		 private static final String DATABASE_CREATE =
		        "create table Listado (_id integer primary key autoincrement, "			 
		       + 	"nroInv text not null, " +
		       		"titulo text not null, " +
		       		"autor text not null, " +
		        	"solo_en_biblio text not null, " +
		        	"reservado integer not null, " +
		        	"isbn text not null);";
		
		    private static class DatabaseHelper extends SQLiteOpenHelper {
		    	

		    	
		        DatabaseHelper(Context context) {
		            super(context, DATABASE_NAME, null, DATABASE_VERSION);
		            Log.d(TAG, "DatabaseHelper");
		        }

		        @Override
		        public void onCreate(SQLiteDatabase db) {
		        	Log.d(TAG, "DatabaseHelper_oncreate");
		            db.execSQL(DATABASE_CREATE);
		        }

		        @Override
		        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		        	Log.d(TAG, "DatabaseHelper_oncreate");
		        	Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
		                    + newVersion + ", which will destroy all old data");
		            db.execSQL("DROP TABLE IF EXISTS notes");
		            onCreate(db);
		        }
		    }

		    public BiblioContentProvider(Context ctx) {
		        this.mCtx = ctx;
		        Log.d(TAG, "BiblioContentProvider(ctx)");

		    }
			
		    public BiblioContentProvider open() throws SQLException {
		    	Log.d(TAG, "BiblioContentProvider_open1");
		        mDbHelper = new DatabaseHelper(mCtx);
		        Log.d(TAG, "BiblioContentProvider_open2");
		        mDb = mDbHelper.getWritableDatabase();
		        Log.d(TAG, "BiblioContentProvider_open3");

		        return this;
		    }

		    public void close() {
		        mDbHelper.close();
		    }

			public void BorrarListado(){
				mDb.delete(DATABASE_TABLE, null, null);
			}
			
			  public long CantRows() {

		            return DatabaseUtils.queryNumEntries(mDb,DATABASE_TABLE);

		        }

			private String buscarNroInv(String id){
				Cursor cursor = mDb.query(DATABASE_TABLE, new String[] {KEY_NRO_INV}, id + "=" + KEY_ROWID, null, null, null, null);
				cursor.moveToFirst();
				return cursor.getString(0).toString();
				
			}
			  
		    public Cursor fetchAllNotes() {

		        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID,KEY_NRO_INV, KEY_TITULO,KEY_AUTOR ,
		                KEY_SOLO_EN_BIBLIO,KEY_RESERVADO,KEY_ISBN}, null, null, null, null, null);
		    }
			private long insertarLibro(String titulo,String autor, String solo_en_biblio, String nroInv,int reservado,String isbn) {
				  ContentValues initialValues = new ContentValues();
				  Log.d(TAG, "insertarLibro");
				  initialValues.put(KEY_TITULO, titulo);
			        initialValues.put(KEY_AUTOR, autor);
			        initialValues.put(KEY_SOLO_EN_BIBLIO, solo_en_biblio);
			        initialValues.put(KEY_NRO_INV, nroInv);
			        initialValues.put(KEY_RESERVADO, reservado);
			        initialValues.put(KEY_ISBN, isbn);
			        Log.d(TAG, "insertarLibro");
			        return mDb.insert(DATABASE_TABLE, null, initialValues);
			        
			}
			 private static String convertStreamToString(InputStream is) {
			        /*
			         * To convert the InputStream to String we use the BufferedReader.readLine()
			         * method. We iterate until the BufferedReader return null which means
			         * there's no more data to read. Each line will appended to a StringBuilder
			         * and returned as String.
			         */
			        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			        StringBuilder sb = new StringBuilder();
			 
			        String line = null;
			        try {
			            while ((line = reader.readLine()) != null) {
			                sb.append(line + "\n");
			            }
			        } catch (IOException e) {
			            e.printStackTrace();
			        } finally {
			            try {
			                is.close();
			            } catch (IOException e) {
			                e.printStackTrace();
			            }
			        }
			        return sb.toString();
			    }
			    
			 
			 public void traerJson(Bundle datosBusqueda) throws JSONException{
				 Log.d(TAG, "traerJson");
			    	String datosQueVan = null;
			    	String keywords = null;
			    	HttpEntity entity = null;
			    	String tipoBusqueda = datosBusqueda.getString("tipoBusqueda").toString();
			    	String aBuscar = datosBusqueda.getString("aBuscar").toString();
			    	String filtroBusqueda = datosBusqueda.getString("filtro").toString();
			    	
			    	String offset = Long.toString(CantRows());
			    	datosQueVan =tipoBusqueda + "," +filtroBusqueda+  "," + aBuscar +"," + offset;
			    	HttpClient httpClient = new DefaultHttpClient();
			    	String url = mCtx.getString(R.string.uriConsultaIP);
			    	//String url = "http://biblioVirtual.no-ip.org/BibliotecaVirtual/resources/catalogo";
			    	//String url = "http://192.168.0.103:8080/BibliotecaVirtual/resources/catalogo";

			    	HttpPost httpH = new HttpPost(url);
			    	try {
						httpH.setEntity(new StringEntity(datosQueVan));
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	try {
			    	
			    		 Log.d(TAG, "traerJson");
						HttpResponse response = httpClient.execute(httpH);
						entity = response.getEntity();
						InputStream stream = entity.getContent();
						keywords = convertStreamToString(stream);
					
							JSONArray json=new JSONArray(keywords);
							open();
				for (int i=0;i<json.length();i++){
					JSONObject objetoDelArray=(JSONObject) json.get(i);
					     
					     Log.d(TAG, "Inserto el registro: " + i);
					     insertarLibro(	objetoDelArray.get("titulo").toString(),
					    		 		objetoDelArray.get("autor").toString(),
					    		 		objetoDelArray.get("solo_en_biblio").toString(),
					    		 		objetoDelArray.get("nroInv").toString(),
					    		 		0,
					    		 		objetoDelArray.get("isbn").toString());
					     
					
					    
					
				}
			     stream.close();
						 
					     
						
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						Log.i("algo origino lo sgte","ClientProtocolException" , e);
						//Toast.makeText(this, e.getCause().toString(), Toast.LENGTH_LONG).show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						Log.i("AAAAAAalgo origino lo sgte","ClientProtocolException" , e);
						//Toast.makeText(this, e.getCause().toString(), Toast.LENGTH_LONG).show();
						
						//Toast.makeText(this, "Socket timeout or Server No Respondig", Toast.LENGTH_LONG).show();
					}
					
					
			    }
			 public String reservar(Bundle datosReserva){
				 HttpEntity entity=null;  	
				 String user = datosReserva.getString("user").toString();
			    	String pass = datosReserva.getString("pass").toString();
			    	String nroInvRes = buscarNroInv(datosReserva.getString("nroInv").toString());
			    	//String nroInvRes = datosReserva.getString("nroInv").toString();
			    	String datosQueVan =user + "," +pass+  "," + nroInvRes;
			    	HttpClient httpClient = new DefaultHttpClient();
			    	String url = mCtx.getString(R.string.uriReservar);
			    	HttpPost httpH = new HttpPost(url);
			     	try {
						httpH.setEntity(new StringEntity(datosQueVan));
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					HttpResponse response = null;
					try {
						response = httpClient.execute(httpH);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					entity = response.getEntity();
					String rdo="fail";
					try {
						rdo = EntityUtils.toString(entity);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				 return rdo;
			 }

 
}

package edu.utn;





import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import edu.utn.frre.R;

public class ResultadoBusquedaListActivity extends ListActivity
{
	private static final int DELETE_ID = Menu.FIRST + 1;
	MyAdapter mListAdapter;
	private Bundle miBundle;
	private BiblioContentProvider DBHelper;
	 public static final int INSERT_ID = Menu.FIRST;
	 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
    	menu.add(0, INSERT_ID, 0, R.string.menuReservar);

        return super.onCreateOptionsMenu(menu);
        
        
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
    

        return super.onOptionsItemSelected(item);
    }
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mostrarTablaConsulta();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		registerForContextMenu(getListView());
		miBundle = this.getIntent().getExtras();
		
		// setContentView(R.layout.entornoresultado);
	}
	 @Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			super.onCreateContextMenu(menu, v, menuInfo);
		    menu.add(0, DELETE_ID, 0, R.string.menuReservar);

	        // TODO: fill in rest of method
		}
	 @Override
		public boolean onContextItemSelected(MenuItem item) {
			
		        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		        miBundle.putString("nroInv", Long.toString(info.id));
		       String resultado= DBHelper.reservar(miBundle);
		       if (resultado.equals("ok")){
		    	   Toast.makeText(this,"Se ha reservado el libro", Toast.LENGTH_LONG).show();
		       }else {
		    	   Toast.makeText(this,"No se pudo reservado el libro", Toast.LENGTH_LONG).show();
		       }
		        
		 
		    return super.onContextItemSelected(item);

	        // TODO: fill in rest of method
		}
	
	
	public void mostrarTablaConsulta(){
		 DBHelper = new BiblioContentProvider(this);
	     DBHelper.open();
	     Cursor c = DBHelper.fetchAllNotes();
	     startManagingCursor(c);
	     String[] from = new String[] { BiblioContentProvider.KEY_AUTOR, BiblioContentProvider.KEY_TITULO};
	     int[] to = new int[] { R.id.txtRdoTitulo, R.id.txtRdoAutor };
	        mListAdapter = new MyAdapter(ResultadoBusquedaListActivity.this, c);
	        setListAdapter(mListAdapter);
	     
	     
/*	     SimpleCursorAdapter notes =
	         new SimpleCursorAdapter(this, R.layout.rowsrealizarreserva, c, from, to);
	     	     setListAdapter(notes);
*/	 }
    private class MyAdapter extends ResourceCursorAdapter {

        public MyAdapter(Context context, Cursor cur) {
            super(context, R.layout.rowsrealizarreserva, cur);
        }

        @Override
        public View newView(Context context, Cursor cur, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return li.inflate(R.layout.rowsrealizarreserva, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cur) {
            TextView txtRdoAutor = (TextView)view.findViewById(R.id.txtRdoAutor);
            TextView txtRdoTitulo = (TextView)view.findViewById(R.id.txtRdoTitulo);
 

            txtRdoAutor.setText(cur.getString(cur.getColumnIndex("autor")));
            txtRdoTitulo.setText(cur.getString(cur.getColumnIndex("titulo")));
            int vble = cur.getInt(cur.getColumnIndex("reservado"));
            
        }


    }

	 

}

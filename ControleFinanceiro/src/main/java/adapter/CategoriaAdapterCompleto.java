package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import leoluz.com.controlefinanceiro.R;
import model.Categoria;

public class CategoriaAdapterCompleto extends BaseAdapter{

    Context ctx;
    List<Categoria> categorias;

    public CategoriaAdapterCompleto(Context ctx, List<Categoria> categorias) {
        this.ctx = ctx;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        Categoria categoria = categorias.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listacompleta, parent, false);
        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtCategoria = (TextView) linha.findViewById(R.id.txtCategoria);
        TextView txtIncrementar = (TextView) linha.findViewById(R.id.txtTipo);

        txtId.setText(Integer.toString(categoria.getIdCategoria()));
        txtCategoria.setText(categoria.getDescricao());
        if(categoria.getIncremento()==1){
            txtIncrementar.setText("Incremento");
        }else{
            txtIncrementar.setText("Decremento");
        }
        if(categoria.getHabilitado()==1){
            txtId.setTextColor(Color.BLUE);
            txtCategoria.setTextColor(Color.BLUE);
        }else{
            txtId.setTextColor(Color.RED);
            txtCategoria.setTextColor(Color.RED);
        }

        return linha;
    }
}

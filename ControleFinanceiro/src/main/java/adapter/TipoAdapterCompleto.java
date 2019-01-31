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
import model.Tipo;

public class TipoAdapterCompleto extends BaseAdapter{

    Context ctx;
    List<Tipo> tipos;

    public TipoAdapterCompleto(Context ctx, List<Tipo> tipos) {
        this.ctx = ctx;
        this.tipos = tipos;
    }

    @Override
    public int getCount() {
        return tipos.size();
    }

    @Override
    public Object getItem(int position) {
        return tipos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        Tipo tipo = tipos.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listatipo, parent, false);
        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtCategoria = (TextView) linha.findViewById(R.id.txtCategoria);
        TextView txtTipo = (TextView) linha.findViewById(R.id.txtTipo);
        //Resources res = ctx.getResources();
        txtId.setText(Integer.toString(tipo.getIdTipo()));
        txtCategoria.setText(tipo.getCategoriaNome());
        txtTipo.setText(tipo.getDescricao());
        if(tipo.getHabilitado()==1){
            txtId.setTextColor(Color.BLUE);
            txtCategoria.setTextColor(Color.BLUE);
            txtTipo.setTextColor(Color.BLUE);
        }else{
            txtId.setTextColor(Color.RED);
            txtCategoria.setTextColor(Color.RED);
            txtTipo.setTextColor(Color.RED);
        }

        return linha;
    }
}

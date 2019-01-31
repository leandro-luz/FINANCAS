package adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import leoluz.com.controlefinanceiro.R;
import model.Elemento;

public class ElementoAdapterCompleto extends BaseAdapter{

    Context ctx;
    List<Elemento> elementos;

    public ElementoAdapterCompleto(Context ctx, List<Elemento> elementos) {
        this.ctx = ctx;
        this.elementos = elementos;
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public Object getItem(int position) {
        return elementos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        Elemento elemento = elementos.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listaelemento, parent, false);
        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtCategoria = (TextView) linha.findViewById(R.id.txtCategoria);
        TextView txtTipo = (TextView) linha.findViewById(R.id.txtTipo);
        TextView txtItem = (TextView) linha.findViewById(R.id.txtItem);
        TextView txtSubItem = (TextView) linha.findViewById(R.id.txtSubItem);
        TextView txtElemento = (TextView) linha.findViewById(R.id.txtElemento);
        //Resources res = ctx.getResources();
        txtId.setText(Integer.toString(elemento.getIdElemento()));

        txtCategoria.setText(elemento.getCategoriaNome());
        txtTipo.setText(elemento.getTipoNome());
        txtItem.setText(elemento.getItemNome());
        txtSubItem.setText(elemento.getSubitemNome());

        txtElemento.setText(elemento.getDescricao());
        if(elemento.getHabilitado()==1){
            txtId.setTextColor(Color.BLUE);
            txtCategoria.setTextColor(Color.BLUE);
            txtTipo.setTextColor(Color.BLUE);
            txtItem.setTextColor(Color.BLUE);
            txtSubItem.setTextColor(Color.BLUE);
            txtElemento.setTextColor(Color.BLUE);
        }else{
            txtId.setTextColor(Color.RED);
            txtCategoria.setTextColor(Color.RED);
            txtTipo.setTextColor(Color.RED);
            txtItem.setTextColor(Color.RED);
            txtSubItem.setTextColor(Color.RED);
            txtElemento.setTextColor(Color.RED);
        }

        if(elemento.getFavorito()==1){
            txtId.setTypeface(null, Typeface.BOLD);
            txtCategoria.setTypeface(null, Typeface.BOLD);
            txtTipo.setTypeface(null, Typeface.BOLD);
            txtItem.setTypeface(null, Typeface.BOLD);
            txtSubItem.setTypeface(null, Typeface.BOLD);
            txtElemento.setTypeface(null, Typeface.BOLD);
        }

        return linha;
    }
}

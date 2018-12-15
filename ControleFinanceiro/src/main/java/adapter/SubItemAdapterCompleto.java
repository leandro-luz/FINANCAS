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
import model.SubItem;

public class SubItemAdapterCompleto extends BaseAdapter{

    Context ctx;
    List<SubItem> subItens;

    public SubItemAdapterCompleto(Context ctx, List<SubItem> subItens) {
        this.ctx = ctx;
        this.subItens = subItens;
    }

    @Override
    public int getCount() {
        return subItens.size();
    }

    @Override
    public Object getItem(int position) {
        return subItens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        SubItem subItem = subItens.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listacompleta, parent, false);
        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtCategoria = (TextView) linha.findViewById(R.id.txtCategoria);
        TextView txtTipo = (TextView) linha.findViewById(R.id.txtTipo);
        TextView txtItem = (TextView) linha.findViewById(R.id.txtItem);
        TextView txtSubItem = (TextView) linha.findViewById(R.id.txtSubitem);
        //Resources res = ctx.getResources();
        txtId.setText(Integer.toString(subItem.getIdSubItem()));
        txtCategoria.setText(subItem.getCategoriaNome());
        txtTipo.setText(subItem.getTipoNome());
        txtItem.setText(subItem.getItemNome());
        txtSubItem.setText(subItem.getDescricao());
        if(subItem.getHabilitado()==1){
            txtId.setTextColor(Color.BLUE);
            txtCategoria.setTextColor(Color.BLUE);
            txtTipo.setTextColor(Color.BLUE);
            txtItem.setTextColor(Color.BLUE);
            txtSubItem.setTextColor(Color.BLUE);
        }else{
            txtId.setTextColor(Color.RED);
            txtCategoria.setTextColor(Color.RED);
            txtTipo.setTextColor(Color.RED);
            txtItem.setTextColor(Color.RED);
            txtSubItem.setTextColor(Color.RED);
        }
        if(subItem.getFavorito()==1){
            txtId.setTypeface(null, Typeface.BOLD);
            txtCategoria.setTypeface(null, Typeface.BOLD);
            txtTipo.setTypeface(null, Typeface.BOLD);
            txtItem.setTypeface(null, Typeface.BOLD);
            txtSubItem.setTypeface(null, Typeface.BOLD);;
        }

        return linha;
    }
}

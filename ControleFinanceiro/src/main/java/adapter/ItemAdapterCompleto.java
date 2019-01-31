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
import model.Item;

public class ItemAdapterCompleto extends BaseAdapter{

    Context ctx;
    List<Item> itens;

    public ItemAdapterCompleto(Context ctx, List<Item> itens) {
        this.ctx = ctx;
        this.itens = itens;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        Item item = itens.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listaitem, parent, false);
        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtCategoria = (TextView) linha.findViewById(R.id.txtCategoria);
        TextView txtTipo = (TextView) linha.findViewById(R.id.txtTipo);
        TextView txtItem = (TextView) linha.findViewById(R.id.txtItem);
        //Resources res = ctx.getResources();
        txtId.setText(Integer.toString(item.getIdItem()));
        txtCategoria.setText(item.getCategoriaNome());
        txtTipo.setText(item.getTipoNome());
        txtItem.setText(item.getDescricao());
        if(item.getHabilitado()==1){
            txtId.setTextColor(Color.BLUE);
            txtCategoria.setTextColor(Color.BLUE);
            txtTipo.setTextColor(Color.BLUE);
            txtItem.setTextColor(Color.BLUE);
        }else{
            txtId.setTextColor(Color.RED);
            txtCategoria.setTextColor(Color.RED);
            txtTipo.setTextColor(Color.RED);
            txtItem.setTextColor(Color.RED);
        }

        return linha;
    }
}

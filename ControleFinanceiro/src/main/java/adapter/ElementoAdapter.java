package adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import leoluz.com.controlefinanceiro.R;
import model.Elemento;
import model.SubItem;

public class ElementoAdapter extends BaseAdapter{

    Context ctx;
    List<Elemento> elementos;

    public ElementoAdapter(Context ctx, List<Elemento> elementos) {
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
        View linha = LayoutInflater.from(ctx).inflate(R.layout.lista, parent, false);
        TextView txtDescricao = (TextView) linha.findViewById(R.id.txtDescricao);
        Resources res = ctx.getResources();
        txtDescricao.setText(elemento.getDescricao());
        return linha;
    }
}

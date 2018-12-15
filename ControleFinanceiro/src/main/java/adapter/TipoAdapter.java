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
import model.Categoria;
import model.Tipo;

public class TipoAdapter extends BaseAdapter{

    Context ctx;
    List<Tipo> tipos;

    public TipoAdapter(Context ctx, List<Tipo> tipos) {
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
        View linha = LayoutInflater.from(ctx).inflate(R.layout.lista, parent, false);
        TextView txtDescricao = (TextView) linha.findViewById(R.id.txtDescricao);
        Resources res = ctx.getResources();
        txtDescricao.setText(tipo.getDescricao());
        return linha;
    }
}

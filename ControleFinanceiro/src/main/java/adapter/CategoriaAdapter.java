package adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import leoluz.com.controlefinanceiro.R;
import model.Categoria;

public class CategoriaAdapter  extends BaseAdapter{

    Context ctx;
    List<Categoria> categorias;

    public CategoriaAdapter(Context ctx, List<Categoria> categorias) {
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
        View linha = LayoutInflater.from(ctx).inflate(R.layout.lista, parent, false);
        TextView txtDescricao = (TextView) linha.findViewById(R.id.txtDescricao);
        Resources res = ctx.getResources();
        txtDescricao.setText(categoria.getDescricao());
        return linha;
    }
}

package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import leoluz.com.controlefinanceiro.R;
import model.Favorito;

public class FavoritoAdapter extends BaseAdapter {

    Context ctx;
    List<Favorito> favoritos;

    public FavoritoAdapter(Context ctx, List<Favorito> favoritos) {
        this.ctx = ctx;
        this.favoritos = favoritos;
    }

    @Override
    public int getCount() {
        return favoritos.size();
    }

    @Override
    public Object getItem(int position) {
        return favoritos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        Favorito favorito = favoritos.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listafavorito, parent, false);

        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtOpcao = (TextView) linha.findViewById(R.id.txtOpcao);
        TextView txtDescricao = (TextView) linha.findViewById(R.id.txtDescricao);

        //Resources res = ctx.getResources();
        txtId.setText(Integer.toString(favorito.getIdFavorito()));
        txtOpcao.setText(favorito.getOpcao());
        txtDescricao.setText(favorito.getDescricao());

        return linha;
    }
}

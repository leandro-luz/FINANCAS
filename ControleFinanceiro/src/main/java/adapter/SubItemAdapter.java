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
import model.SubItem;

public class SubItemAdapter extends BaseAdapter{

    Context ctx;
    List<SubItem> subitens;

    public SubItemAdapter(Context ctx, List<SubItem> subitens) {
        this.ctx = ctx;
        this.subitens = subitens;
    }

    @Override
    public int getCount() {
        return subitens.size();
    }

    @Override
    public Object getItem(int position) {
        return subitens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        SubItem subitem = subitens.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.lista, parent, false);
        TextView txtDescricao = (TextView) linha.findViewById(R.id.txtDescricao);
        Resources res = ctx.getResources();
        txtDescricao.setText(subitem.getDescricao());
        return linha;
    }
}

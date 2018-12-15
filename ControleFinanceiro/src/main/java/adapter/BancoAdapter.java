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
import model.Banco;
import model.Categoria;

public class BancoAdapter extends BaseAdapter{

    Context ctx;
    List<Banco>  bancos;

    public BancoAdapter(Context ctx, List<Banco> bancos) {
        this.ctx = ctx;
        this.bancos = bancos;
    }

    @Override
    public int getCount() {
        return bancos.size();
    }

    @Override
    public Object getItem(int position) {
        return bancos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        Banco banco = bancos.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.lista, parent, false);
        TextView txtDescricao = (TextView) linha.findViewById(R.id.txtDescricao);
        Resources res = ctx.getResources();
        txtDescricao.setText(banco.getDescricao());
        return linha;
    }
}

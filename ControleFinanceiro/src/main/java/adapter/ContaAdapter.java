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
import model.Conta;

public class ContaAdapter extends BaseAdapter{

    Context ctx;
    List<Conta> contas;

    public ContaAdapter(Context ctx, List<Conta> contas) {
        this.ctx = ctx;
        this.contas = contas;
    }

    @Override
    public int getCount() {
        return contas.size();
    }

    @Override
    public Object getItem(int position) {
        return contas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        Conta conta = contas.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.lista, parent, false);
        TextView txtDescricao = (TextView) linha.findViewById(R.id.txtDescricao);
        Resources res = ctx.getResources();
        txtDescricao.setText(conta.getDescricao());
        return linha;
    }
}

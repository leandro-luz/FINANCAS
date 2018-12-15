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
import model.Lancamento;
import model.SubItem;

public class LancamentoAdapter extends BaseAdapter {

    Context ctx;
    List<Lancamento> lancamentos;

    public LancamentoAdapter(Context ctx, List<Lancamento> lancamentos) {
        this.ctx = ctx;
        this.lancamentos = lancamentos;
    }

    @Override
    public int getCount() {
        return lancamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return lancamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        Lancamento lancamento = lancamentos.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listalancamento, parent, false);
        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtEstrutura = (TextView) linha.findViewById(R.id.txtEstrutura);
        TextView txtConta = (TextView) linha.findViewById(R.id.txtConta);
        TextView txtData = (TextView) linha.findViewById(R.id.txtData);
        TextView txtValor = (TextView) linha.findViewById(R.id.txtValor);

        txtId.setText(String.valueOf(lancamento.getIdLancamento()));
        txtEstrutura.setText(String.valueOf(lancamento.getFavoritoEstruturaNome()));
        txtConta.setText(String.valueOf(lancamento.getFavoritoContaNome()));
        txtData.setText(lancamento.getData());
        txtValor.setText(String.valueOf(lancamento.getValor()));
        return linha;
    }
}

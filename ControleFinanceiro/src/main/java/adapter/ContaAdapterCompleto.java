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
import model.Categoria;
import model.Conta;

public class ContaAdapterCompleto extends BaseAdapter{

    Context ctx;
    List<Conta> contas;

    public ContaAdapterCompleto(Context ctx, List<Conta> contas) {
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
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listacompleta, parent, false);
        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtBanco = (TextView) linha.findViewById(R.id.txtCategoria);
        TextView txtConta = (TextView) linha.findViewById(R.id.txtTipo);
        TextView txtSaldo = (TextView) linha.findViewById(R.id.txtItem);
        //Resources res = ctx.getResources();
        txtId.setText(Integer.toString(conta.getIdBanco()));
        txtBanco.setText(conta.getBancoNome());
        txtConta.setText(conta.getDescricao());
        txtSaldo.setText(conta.getSaldo().toString());

        if(conta.getHabilitado()==1){
            txtId.setTextColor(Color.BLUE);
            txtBanco.setTextColor(Color.BLUE);
            txtConta.setTextColor(Color.BLUE);
            txtSaldo.setTextColor(Color.BLUE);
        }else{
            txtId.setTextColor(Color.RED);
            txtBanco.setTextColor(Color.RED);
            txtConta.setTextColor(Color.RED);
            txtSaldo.setTextColor(Color.RED);
        }
        if(conta.getFavorito()==1){
            txtId.setTypeface(null, Typeface.BOLD);
            txtBanco.setTypeface(null, Typeface.BOLD);
            txtConta.setTypeface(null, Typeface.BOLD);
            txtSaldo.setTypeface(null, Typeface.BOLD);
        }

        return linha;
    }
}

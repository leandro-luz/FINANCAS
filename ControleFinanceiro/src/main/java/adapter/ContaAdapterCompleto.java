package adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import leoluz.com.controlefinanceiro.R;
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
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listaconta, parent, false);
        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtBanco = (TextView) linha.findViewById(R.id.txtBanco);
        TextView txtConta = (TextView) linha.findViewById(R.id.txtConta);
        TextView txtSaldo = (TextView) linha.findViewById(R.id.txtSaldo);
        //Resources res = ctx.getResources();
        txtId.setText(Integer.toString(conta.getIdBanco()));
        txtBanco.setText(conta.getBancoNome());
        txtConta.setText(conta.getDescricao());

        Locale myLocale = new Locale("pt", "BR");
        DecimalFormatSymbols REAL = new DecimalFormatSymbols(myLocale);
        DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00",REAL);

        txtSaldo.setText(DINHEIRO_REAL.format(conta.getSaldo()));
        //txtSaldo.setText(conta.getSaldo().toString());

        if(conta.getSaldo() > 0){
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

        if(conta.getHabilitado()== 0){
            txtId.setPaintFlags(txtId.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtBanco.setPaintFlags(txtBanco.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtConta.setPaintFlags(txtConta.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtSaldo.setPaintFlags(txtSaldo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if(conta.getFavorito()== 1){
            txtId.setTypeface(null, Typeface.BOLD);
            txtBanco.setTypeface(null, Typeface.BOLD);
            txtConta.setTypeface(null, Typeface.BOLD);
            txtSaldo.setTypeface(null, Typeface.BOLD);
        }

        return linha;
    }
}

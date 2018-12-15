package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import leoluz.com.controlefinanceiro.R;
import model.Banco;
import model.Categoria;

public class BancoAdapterCompleto extends BaseAdapter{

    Context ctx;
    List<Banco> bancos;

    public BancoAdapterCompleto(Context ctx, List<Banco> bancos) {
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
        View linha = LayoutInflater.from(ctx).inflate(R.layout.listacompleta, parent, false);
        TextView txtId = (TextView) linha.findViewById(R.id.txtId);
        TextView txtBanco = (TextView) linha.findViewById(R.id.txtCategoria);
        //Resources res = ctx.getResources();
        txtId.setText(Integer.toString(banco.getIdBanco()));
        txtBanco.setText(banco.getDescricao());
        if(banco.getHabilitado()==1){
            txtId.setTextColor(Color.BLUE);
            txtBanco.setTextColor(Color.BLUE);
        }else{
            txtId.setTextColor(Color.RED);
            txtBanco.setTextColor(Color.RED);
        }

        return linha;
    }
}

package leoluz.com.controlefinanceiro.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.BancoAdapterCompleto;
import adapter.CategoriaAdapterCompleto;
import adapter.ContaAdapterCompleto;
import adapter.ElementoAdapterCompleto;
import adapter.ItemAdapterCompleto;
import adapter.SubItemAdapterCompleto;
import adapter.TipoAdapterCompleto;
import leoluz.com.controlefinanceiro.R;
import model.Banco;
import model.Carteira;
import model.Categoria;
import model.Conta;
import model.Elemento;
import model.Item;
import model.Lancamento;
import model.SubItem;
import model.Tipo;
import persist.DAO.ContaDao;
import persist.DAO.FabricaDao;

public class ActivityResumo extends AppCompatActivity  {

    String opcao;
    List<Conta> listaConta;
    ContaAdapterCompleto adapterConta;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        setContentView(listView);

        //alterando o titulo da tela
        setTitle("SALDO DAS CONTAS");

        TextView txtHeader = new TextView(this);
        txtHeader.setText(R.string.txt_cabecalho_listaResumo);
        listView.addHeaderView(txtHeader);

        try {
            ContaDao contaDao = FabricaDao.criarContaDao();
            listaConta = new ArrayList<>();
            listaConta = contaDao.listarTodos();
            adapterConta = new ContaAdapterCompleto(this, listaConta);
            listView.setAdapter(adapterConta);
        } catch (Exception e) {
            Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
            sair();
        }
    }


    private void sair() {
        Intent i = new Intent(this, ActivityMenu.class);
        startActivity(i);
        finish();
    }


}

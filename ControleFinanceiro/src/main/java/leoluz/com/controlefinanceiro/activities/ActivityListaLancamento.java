package leoluz.com.controlefinanceiro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.BancoAdapterCompleto;
import adapter.CategoriaAdapterCompleto;
import adapter.ContaAdapterCompleto;
import adapter.ElementoAdapterCompleto;
import adapter.ItemAdapterCompleto;
import adapter.LancamentoAdapter;
import adapter.SubItemAdapterCompleto;
import adapter.TipoAdapterCompleto;
import leoluz.com.controlefinanceiro.R;
import model.Banco;
import model.Carteira;
import model.Categoria;
import model.Conta;
import model.Elemento;
import model.Favorito;
import model.Item;
import model.Lancamento;
import model.SubItem;
import model.Tipo;
import persist.DAO.BancoDao;
import persist.DAO.CategoriaDao;
import persist.DAO.ContaDao;
import persist.DAO.ElementoDao;
import persist.DAO.FabricaDao;
import persist.DAO.ItemDao;
import persist.DAO.LancamentoDao;
import persist.DAO.SubItemDao;
import persist.DAO.TipoDao;


public class ActivityListaLancamento extends AppCompatActivity {

    String lista;
    String opcao;
    String operacao;

    List<Lancamento> listaLancamento;

    LancamentoAdapter adapterLancamento;

    ListView listView;
    Lancamento lancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        setContentView(listView);
        lancamento = new Lancamento();

        //recebendo os parametros da Intent
        Intent it = getIntent();
        lancamento = (Lancamento) it.getSerializableExtra("lancamento");

        opcao = it.getStringExtra("opcao");
        lista = it.getStringExtra("lista");
        operacao = it.getStringExtra("operacao");

        //alterando o titulo da tela
        setTitle("LISTA DE " + opcao.toUpperCase());

        TextView txtHeader = new TextView(this);
        txtHeader.setText(R.string.txt_cabecalho_listaLancamento);
        listView.addHeaderView(txtHeader);

        LancamentoDao lancamentoDao = FabricaDao.criarLancamentoDao();
        listaLancamento = new ArrayList<>();
        listaLancamento = lancamentoDao.listarTodos();

        if (listaLancamento.size() != 0) {
            adapterLancamento = new LancamentoAdapter(this, listaLancamento);
            listView.setAdapter(adapterLancamento);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    if (id != -1) {
                        Lancamento lancamento = (Lancamento) adapterView.getItemAtPosition(position);

                        Intent i = new Intent(ActivityListaLancamento.this, ActivityCadastroLancamento.class);
                        i.putExtra("operacao", "update");
                        i.putExtra("opcao", opcao);
                        i.putExtra("lancamento", lancamento);
                        startActivity(i);
                        finish();

                    }
                }
            });


        } else {
            Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
        }

    }


    private void sair() {
        //  Intent i = new Intent(ActivityListaLancamento.this, ActivityCadastroContas.class);
        //   i.putExtra("opcao", opcao);
        //  i.putExtra("operacao", "insert");
        //  i.putExtra("carteira", carteira);
        //   startActivity(i);

        finish();
    }

}


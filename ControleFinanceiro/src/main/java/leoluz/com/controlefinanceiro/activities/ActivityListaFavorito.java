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

import adapter.CategoriaAdapterCompleto;
import adapter.FavoritoAdapter;
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
import persist.DAO.CategoriaDao;
import persist.DAO.ContaDao;
import persist.DAO.ElementoDao;
import persist.DAO.FabricaDao;
import persist.DAO.FavoritoDao;
import persist.DAO.SubItemDao;
import util.QuantidadeLista;

public class ActivityListaFavorito extends AppCompatActivity {

    List<Favorito> listaFavorito;

    FavoritoAdapter favoritoAdapter;

    ListView listView;

    Favorito favorito;
    Lancamento lancamento;

    String opcao;
    String lista;
    String operacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        setContentView(listView);

        //recebendo a opção
        final Intent it = getIntent();
        opcao = it.getStringExtra("opcao");
        lista = it.getStringExtra("lista");
        operacao = it.getStringExtra("operacao");
        favorito = new Favorito();


        favorito = (Favorito) it.getSerializableExtra("favorito");
        lancamento = (Lancamento) it.getSerializableExtra("lancamento");

        if(lancamento == null){
            lancamento = new Lancamento();
        }

        //alterando o titulo da tela
        setTitle("LISTA DE FAVORITOS");

        TextView txtHeader = new TextView(this);
        txtHeader.setText(R.string.txt_cabecalho_listaFavorito);
        listView.addHeaderView(txtHeader);

        FavoritoDao daoFavo = FabricaDao.criarFavoritoDao();
        listaFavorito = new ArrayList<>();

        if (lista == null) {
            listaFavorito = daoFavo.listarTodos();
        } else {
            listaFavorito = daoFavo.listarTodosbyOpcao(lista);
        }

        favoritoAdapter = new FavoritoAdapter(this, listaFavorito);
        listView.setAdapter(favoritoAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != -1) {
                    Lancamento lancamento = new Lancamento();
                    if (opcao.equalsIgnoreCase("ler")) {
                        //recebe o favorito escolhido pelo usuario
                        Favorito favorito2 = (Favorito) adapterView.getItemAtPosition(position);
                        lista = favorito2.getOpcao();

                        switch (lista) {
                            case "subitem":
                                //criar um subitem para buscar o restante das suas informações
                                SubItem subItem = new SubItem();
                                subItem.setIdSubItem(favorito2.getIdOpcao());

                                //criar um lancamento e injeta o subitem para buscar as informações
                                lancamento.setSubitem(subItem);

                                //criar uma instancia para buscar as informações
                                SubItemDao subItemDao = FabricaDao.criarSubitemDao();

                                //busca e retorna com as informações
                                lancamento = subItemDao.buscar(lancamento);

                                break;

                            case "elemento":
                                //criar um elemento para buscar o restante das suas informações
                                Elemento elemento = new Elemento();
                                elemento.setIdElemento(favorito2.getIdOpcao());

                                //criar um lancamento e injeta o elemento para buscar as informações
                                lancamento.setElemento(elemento);

                                //criar uma instancia para buscar as informações
                                ElementoDao elementoDao = FabricaDao.criarElementoDao();

                                //busca e retorna com as informações
                                lancamento = elementoDao.buscar(lancamento);

                                break;

                            case "conta":
                                //criar um elemento para buscar o restante das suas informações
                                Conta conta = new Conta();

                                //criar uma instancia para buscar as informações
                                ContaDao contaDao = FabricaDao.criarContaDao();

                                //busca e retorna com as informações
                                conta = contaDao.buscarById(favorito2.getIdOpcao());
                                lancamento.setConta(conta);
                                break;
                        }

                        //volta na tela de cadastro de lancamento com o favorito escolhido
                        Intent i = new Intent(ActivityListaFavorito.this, ActivityFavorito.class);
                        i.putExtra("operacao", "ler");
                        i.putExtra("opcao", lista);
                        i.putExtra("favorito", favorito2);
                        i.putExtra("lancamento", lancamento);
                        startActivity(i);
                        finish();

                    } else {
                        //vai para a tela de favorito para mostrar seus atributos
                        Favorito favorito = (Favorito) adapterView.getItemAtPosition(position);
                        Intent i = new Intent(ActivityListaFavorito.this, ActivityFavorito.class);
                        i.putExtra("operacao", "ler");
                        i.putExtra("opcao", favorito.getOpcao());
                        i.putExtra("favorito", favorito);
                        startActivity(i);
                    }

                }

            }

        });

    }


    private void sair() {
        Intent i = new Intent(this, ActivityConfiguracao.class);
        startActivity(i);
        finish();
    }

}

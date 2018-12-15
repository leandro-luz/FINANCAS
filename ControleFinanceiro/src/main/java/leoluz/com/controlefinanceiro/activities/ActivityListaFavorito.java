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
import model.Categoria;
import model.Conta;
import model.Elemento;
import model.Favorito;
import model.Item;
import model.Lancamento;
import model.SubItem;
import model.Tipo;
import persist.DAO.CategoriaDao;
import persist.DAO.FabricaDao;
import persist.DAO.FavoritoDao;
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

                    if (opcao.equalsIgnoreCase("lancamento")) {
                        //recebe o favorito escolhido pelo usuario
                        Favorito favorito2 = (Favorito) adapterView.getItemAtPosition(position);

                        //injeta o favorito escolhido, no favorito para ser enviado para a tela
                        switch (lista) {
                            case "subitem":
                                Categoria categoria = new Categoria();
                                Tipo tipo = new Tipo();
                                Item item = new Item();
                                SubItem subItem = new SubItem();
                                Elemento elemento = new Elemento();

                                categoria.setIdCategoria(favorito2.getIdCategoria());
                                categoria.setDescricao(favorito2.getCategoriaNome());
                                tipo.setIdTipo(favorito2.getIdTipo());
                                tipo.setDescricao(favorito2.getTipoNome());
                                item.setIdItem(favorito2.getIdItem());
                                item.setDescricao(favorito2.getItemNome());
                                subItem.setIdSubItem(favorito2.getIdSubItem());
                                subItem.setDescricao(favorito2.getSubItemNome());
                                elemento.setIdElemento(0);
                                elemento.setDescricao("");

                                lancamento.setCategoria(categoria);
                                lancamento.setTipo(tipo);
                                lancamento.setItem(item);
                                lancamento.setSubitem(subItem);
                                lancamento.setElemento(elemento);
                                lancamento.setIdFavoritoEstrutura(favorito2.getIdFavorito());
                                lancamento.setFavoritoEstruturaNome(favorito2.getDescricao());
                                break;
                            case "conta":
                                Banco banco = new Banco();
                                Conta conta = new Conta();

                                banco.setIdBanco(favorito2.getIdBanco());
                                banco.setDescricao(favorito2.getBancoNome());
                                conta.setIdConta(favorito2.getIdConta());
                                conta.setDescricao(favorito2.getContaNome());
                                lancamento.setBanco(banco);
                                lancamento.setConta(conta);
                                lancamento.setIdFavoritoConta(favorito2.getIdConta());
                                lancamento.setFavoritoContaNome(favorito2.getDescricao());
                                break;
                        }

                        //volta na tela de cadastro de lancamento com o favorito escolhido
                        Intent i = new Intent(ActivityListaFavorito.this, ActivityCadastroLancamento.class);
                        i.putExtra("operacao", operacao);
                        i.putExtra("opcao", opcao);
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

package leoluz.com.controlefinanceiro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.BancoAdapter;
import adapter.CategoriaAdapter;
import adapter.ContaAdapter;
import adapter.ElementoAdapter;
import adapter.ItemAdapter;
import adapter.SubItemAdapter;
import adapter.TipoAdapter;
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
import persist.DAO.GenericDao;
import persist.DAO.ItemDao;
import persist.DAO.SubItemDao;
import persist.DAO.TipoDao;


public class ActivityLista extends AppCompatActivity {

    String lista;
    String opcao;

    Integer idCategoria;
    Integer idTipo;
    Integer idItem;
    Integer idSubitem;

    List<Categoria> listaCategoria;
    List<Tipo> listaTipo;
    List<Item> listaItem;
    List<SubItem> listaSubitem;
    List<Elemento> listaElemento;
    List<Banco> listaBanco;
    List<Conta> listaConta;

    CategoriaAdapter adapterCategoria;
    TipoAdapter adapterTipo;
    ItemAdapter adapterItem;
    SubItemAdapter adapterSubItem;
    ElementoAdapter adapterElemento;
    BancoAdapter adapterBanco;
    ContaAdapter adapterConta;

    ListView listView;
    Lancamento lancamento;
    Carteira carteira;
    Favorito favorito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        setContentView(listView);

        lancamento = new Lancamento();
        favorito = new Favorito();
        carteira = new Carteira();

        //recebendo os parametros da Intent
        Intent it = getIntent();

        //operacao = it.getStringExtra("operacao");
        lancamento = (Lancamento) it.getSerializableExtra("lancamento");
        favorito = (Favorito) it.getSerializableExtra("favorito");

        opcao = it.getStringExtra("opcao");
        lista = it.getStringExtra("lista");

        //alterando o titulo da tela
        setTitle(lista.toUpperCase());

        idCategoria = it.getIntExtra("categoria", 0);
        idTipo = it.getIntExtra("tipo", 0);
        idItem = it.getIntExtra("item", 0);
        idSubitem = it.getIntExtra("subitem", 0);


        //Montar a lista conforme a opção
        switch (lista) {
            case "categoria":
                try {
                    CategoriaDao daoCat = FabricaDao.criarCategoriaDao();
                    listaCategoria = new ArrayList<>();
                    listaCategoria = daoCat.listarTodosHabilitados();

                    adapterCategoria = new CategoriaAdapter(this, listaCategoria);
                    listView.setAdapter(adapterCategoria);
                } catch (Exception e) {
                    Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                    sair();
                }
                break;

            case "tipo":
                try {
                    TipoDao daoTip = FabricaDao.criarTipoDao();
                    listaTipo = new ArrayList<>();

                    //Se for diferente é porque esta na tela de cadastro
                    //se for igual é porque está na tela de lista/alteracao
                    if (opcao.equalsIgnoreCase(lista)) {
                        listaTipo = daoTip.listarTodosHabilitados();
                    } else {
                        listaTipo = daoTip.listarTodosReferencia(idCategoria);
                    }

                    if (listaTipo.size() != 0) {
                        adapterTipo = new TipoAdapter(this, listaTipo);
                        listView.setAdapter(adapterTipo);
                    } else {
                        Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                        sair();
                    }

                } catch (Exception e) {
                    Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
                    sair();
                }
                break;

            case "item":
                try {
                    ItemDao daoIt = FabricaDao.criarItemDao();
                    listaItem = new ArrayList<>();

                    if (opcao.equalsIgnoreCase(lista)) {
                        listaItem = daoIt.listarTodosHabilitados();
                    } else {
                        listaItem = daoIt.listarTodosReferencia(idCategoria, idTipo);
                    }

                    if (listaItem.size() != 0) {
                        adapterItem = new ItemAdapter(this, listaItem);
                        listView.setAdapter(adapterItem);
                    } else {
                        Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                        sair();
                    }

                } catch (Exception e) {
                    Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
                    sair();
                }
                break;

            case "subitem":
                try {
                    SubItemDao daoSit = FabricaDao.criarSubitemDao();
                    listaSubitem = new ArrayList<>();

                    if (opcao.equalsIgnoreCase(lista)) {
                        listaSubitem = daoSit.listarTodosHabilitados();
                    } else {
                        listaSubitem = daoSit.listarTodosReferencia(idCategoria, idTipo, idItem);
                    }

                    if (listaSubitem.size() != 0) {
                        adapterSubItem = new SubItemAdapter(this, listaSubitem);
                        listView.setAdapter(adapterSubItem);
                    } else {
                        Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                        sair();
                    }
                    break;

                } catch (Exception e) {
                    Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
                    sair();
                }

            case "elemento":
                try {
                    ElementoDao daoEle = FabricaDao.criarElementoDao();
                    listaElemento = new ArrayList<>();

                    if (opcao.equalsIgnoreCase(lista)) {
                        listaElemento = daoEle.listarTodosHabilitados();
                    } else {
                        listaElemento = daoEle.listarTodosReferencia(idCategoria, idTipo, idItem, idSubitem);
                    }

                    if (listaElemento.size() != 0) {
                        adapterElemento = new ElementoAdapter(this, listaElemento);
                        listView.setAdapter(adapterElemento);
                    } else {
                        Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();

                        if (opcao.equalsIgnoreCase("lancamento")) {
                            Intent i = new Intent(ActivityLista.this, ActivityCadastroLancamento.class);
                            i.putExtra("operacao", "insert");
                            i.putExtra("opcao", opcao);
                            i.putExtra("lancamento", lancamento);
                            startActivity(i);
                            finish();
                        } else {
                            sair();
                        }
                    }
                    break;

                } catch (Exception e) {
                    Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
                    sair();
                }


            case "banco":
                try {
                    BancoDao daoBanco = FabricaDao.criarBancoDao();
                    listaBanco = new ArrayList<>();
                    listaBanco = daoBanco.listarTodosHabilitados();
                    adapterBanco = new BancoAdapter(this, listaBanco);
                    listView.setAdapter(adapterBanco);
                    break;
                } catch (Exception e) {
                    Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
                    sair();
                }

            case "conta":
                try {
                    ContaDao daoConta = FabricaDao.criarContaDao();
                    listaConta = new ArrayList<>();
                    listaConta = daoConta.listarTodosHabilitados();
                    adapterConta = new ContaAdapter(this, listaConta);
                    listView.setAdapter(adapterConta);
                    sair();
                    break;

                } catch (Exception e) {
                    Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
                    sair();
                }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Quando clicar na tela buscar o item e enviar para a outra activity
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (lista) {
                    case "categoria":
                        Categoria categoria = (Categoria) adapterView.getItemAtPosition(position);
                        lancamento.setCategoria(categoria);
                        sair();
                        break;
                    case "tipo":
                        Tipo tipo = (Tipo) adapterView.getItemAtPosition(position);
                        lancamento.setTipo(tipo);
                        sair();
                        break;
                    case "item":
                        Item item = (Item) adapterView.getItemAtPosition(position);
                        lancamento.setItem(item);
                        sair();
                        break;
                    case "subitem":
                        SubItem subItem = (SubItem) adapterView.getItemAtPosition(position);
                        lancamento.setSubitem(subItem);
                        sair();
                        break;
                    case "elemento":
                        Elemento elemento = (Elemento) adapterView.getItemAtPosition(position);
                        if (opcao.equalsIgnoreCase("lancamento")) {
                            lancamento.setElemento(elemento);
                            Intent i = new Intent(ActivityLista.this, ActivityCadastroLancamento.class);
                            i.putExtra("operacao", "insert");
                            i.putExtra("opcao", opcao);
                            i.putExtra("lancamento", lancamento);
                            startActivity(i);
                            finish();
                        } else {
                            lancamento.setElemento(elemento);
                            sair();
                        }
                        break;

                    case "banco":
                        Banco banco = (Banco) adapterView.getItemAtPosition(position);
                        carteira.setBanco(banco);
                        sair();
                        break;
                }
            }

        });
    }

    private void sair() {

        if (opcao.equalsIgnoreCase("conta")) {
            Intent i = new Intent(ActivityLista.this, ActivityCadastroContas.class);
            i.putExtra("opcao", opcao);
            i.putExtra("operacao", "insert");
            i.putExtra("carteira", carteira);
            startActivity(i);
        } else {
            Intent i = new Intent(ActivityLista.this, ActivityCadastro.class);
            i.putExtra("opcao", opcao);
            i.putExtra("operacao", "insert");
            i.putExtra("lancamento", lancamento);
            startActivity(i);
        }
        finish();
    }

}


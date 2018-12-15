package leoluz.com.controlefinanceiro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.BancoAdapterCompleto;
import adapter.CategoriaAdapter;
import adapter.CategoriaAdapterCompleto;
import adapter.ContaAdapterCompleto;
import adapter.ElementoAdapter;
import adapter.ElementoAdapterCompleto;
import adapter.ItemAdapter;
import adapter.ItemAdapterCompleto;
import adapter.SubItemAdapter;
import adapter.SubItemAdapterCompleto;
import adapter.TipoAdapter;
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
import persist.DAO.BancoDao;
import persist.DAO.CategoriaDao;
import persist.DAO.ContaDao;
import persist.DAO.ElementoDao;
import persist.DAO.FabricaDao;
import persist.DAO.ItemDao;
import persist.DAO.SubItemDao;
import persist.DAO.TipoDao;


public class ActivityListaCompleta extends AppCompatActivity {

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

    CategoriaAdapterCompleto adapterCategoria;
    TipoAdapterCompleto adapterTipo;
    ItemAdapterCompleto adapterItem;
    SubItemAdapterCompleto adapterSubItem;
    ElementoAdapterCompleto adapterElemento;
    BancoAdapterCompleto adapterBanco;
    ContaAdapterCompleto adapterConta;

    ListView listView;
    Lancamento lancamento;

    Carteira carteira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        setContentView(listView);
        lancamento = new Lancamento();

        //recebendo os parametros da Intent
        Intent it = getIntent();
        lancamento = (Lancamento) it.getSerializableExtra("lancamento");

        carteira = new Carteira();

        opcao = it.getStringExtra("opcao");
        lista = it.getStringExtra("lista");

        //alterando o titulo da tela
        setTitle("LISTA DE " + opcao.toUpperCase());

        idCategoria = it.getIntExtra("categoria", 0);
        idTipo = it.getIntExtra("tipo", 0);
        idItem = it.getIntExtra("item", 0);
        idSubitem = it.getIntExtra("subitem", 0);

        TextView txtHeader = new TextView(this);
        txtHeader.setText(R.string.txt_cabecalho_listaCompleta);
        listView.addHeaderView(txtHeader);

        //Montar a lista conforme a opção
        switch (lista) {

            case "categoria":
                try {
                    CategoriaDao daoCat = FabricaDao.criarCategoriaDao();
                    listaCategoria = new ArrayList<>();
                    listaCategoria = daoCat.listarTodos();

                    if (listaCategoria.size() != 0) {
                        adapterCategoria = new CategoriaAdapterCompleto(this, listaCategoria);
                        listView.setAdapter(adapterCategoria);
                    } else {
                        Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                        sair();
                    }


                } catch (Exception e) {
                    Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
                    sair();
                }

                break;
            case "tipo":
                try {
                    TipoDao daoTip = FabricaDao.criarTipoDao();
                    listaTipo = new ArrayList<>();
                    listaTipo = daoTip.listarTodosNome();

                    if (listaTipo.size() != 0) {
                        adapterTipo = new TipoAdapterCompleto(this, listaTipo);
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
                    listaItem = daoIt.listarTodosNome();

                    if (listaItem.size() != 0) {
                        adapterItem = new ItemAdapterCompleto(this, listaItem);
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
                    listaSubitem = daoSit.listarTodosNome();

                    if (listaSubitem.size() != 0) {
                        adapterSubItem = new SubItemAdapterCompleto(this, listaSubitem);
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
                    listaElemento = daoEle.listarTodosNome();

                    if (listaElemento.size() != 0) {
                        adapterElemento = new ElementoAdapterCompleto(this, listaElemento);
                        listView.setAdapter(adapterElemento);
                    } else {
                        Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                        sair();
                    }

                    break;
                } catch (Exception e) {
                    Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
                    sair();
                }

            case "banco":
                try {
                    BancoDao bancoDao = FabricaDao.criarBancoDao();
                    listaBanco = new ArrayList<>();
                    listaBanco = bancoDao.listarTodos();
                    adapterBanco = new BancoAdapterCompleto(this, listaBanco);
                    listView.setAdapter(adapterBanco);
                } catch (Exception e) {
                    Toast.makeText(this, "Erro: ao consultar a lista", Toast.LENGTH_SHORT).show();
                    sair();
                }

                break;

            case "conta":
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

                break;
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Quando clicar na tela buscar o item e enviar para a outra activity
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != -1) {
                    lancamento = new Lancamento();
                    switch (lista) {
                        case "categoria":
                            Categoria categoria = (Categoria) adapterView.getItemAtPosition(position);
                            lancamento.setCategoria(categoria);
                            break;
                        case "tipo":
                            Tipo tipo = (Tipo) adapterView.getItemAtPosition(position);
                            lancamento.setTipo(tipo);
                            break;
                        case "item":
                            Item item = (Item) adapterView.getItemAtPosition(position);
                            lancamento.setItem(item);
                            break;
                        case "subitem":
                            SubItem subItem = (SubItem) adapterView.getItemAtPosition(position);
                            lancamento.setSubitem(subItem);
                            break;
                        case "elemento":
                            Elemento elemento = (Elemento) adapterView.getItemAtPosition(position);
                            lancamento.setElemento(elemento);
                            break;

                        case "banco":
                            Banco banco = (Banco) adapterView.getItemAtPosition(position);
                            carteira.setBanco(banco);
                            break;

                        case "conta":
                            Conta conta = (Conta) adapterView.getItemAtPosition(position);
                            carteira.setConta(conta);
                            break;
                    }
                    sair();
                }

            }

        });

    }

    private void sair() {

        if(opcao.equalsIgnoreCase("banco")|| opcao.equalsIgnoreCase("conta")){
            Intent i = new Intent(ActivityListaCompleta.this, ActivityCadastroContas.class);
            i.putExtra("opcao", opcao);
            i.putExtra("operacao", "update");
            i.putExtra("carteira", carteira);
            startActivity(i);
        }else{
            Intent i = new Intent(ActivityListaCompleta.this, ActivityCadastro.class);
            i.putExtra("opcao", opcao);
            i.putExtra("operacao", "update");
            i.putExtra("lancamento", lancamento);
            startActivity(i);
        }

        finish();
    }

}


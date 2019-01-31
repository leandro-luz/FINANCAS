package leoluz.com.controlefinanceiro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import leoluz.com.controlefinanceiro.R;
import model.Carteira;
import model.Conta;
import model.Elemento;
import model.Favorito;
import model.Lancamento;
import model.SubItem;
import persist.DAO.CategoriaDao;
import persist.DAO.ContaDao;
import persist.DAO.ElementoDao;
import persist.DAO.FabricaDao;
import persist.DAO.FavoritoDao;
import persist.DAO.SubItemDao;

public class ActivityFavorito extends AppCompatActivity implements View.OnClickListener {

    TextView txtCategoria;
    TextView txtTipo;
    TextView txtItem;
    TextView txtSubItem;
    TextView txtElemento;
    TextView txtBanco;
    TextView txtConta;

    TextView txtCategoriaNome;
    TextView txtTipoNome;
    TextView txtItemNome;
    TextView txtSubItemNome;
    TextView txtBancoNome;
    TextView txtContaNome;
    TextView txtElementoNome;

    EditText edDescricao;

    String opcao;
    String operacao;
    String categoria;
    String tipo;
    String item;
    String subitem;
    String elemento;

    Integer idCategoria;
    Integer idTipo;
    Integer idItem;
    Integer idSubItem;
    Integer idElemento;

    Button btn_Salvar;
    Button btn_Voltar;

    long id;
    long posicao;
    long qtd;

    Favorito favorito;
    Lancamento lancamento;
    Carteira carteira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorito);

        //alterando o titulo da tela
        setTitle("CADASTRO DE FAVORITOS");

        btn_Salvar = findViewById(R.id.btnSalvar);
        btn_Salvar.setOnClickListener(this);
        btn_Voltar = findViewById(R.id.btnVoltar);
        btn_Voltar.setOnClickListener(this);

        edDescricao = findViewById(R.id.textDescricao);

        Intent it = getIntent();
        opcao = it.getStringExtra("opcao");
        operacao = it.getStringExtra("operacao");
        favorito = (Favorito) it.getSerializableExtra("favorito");
        lancamento = (Lancamento) it.getSerializableExtra("lancamento");
        carteira = (Carteira) it.getSerializableExtra("carteira");

        txtCategoriaNome = findViewById(R.id.txtCategoriaNome);
        txtTipoNome = findViewById(R.id.txtTipoNome);
        txtItemNome = findViewById(R.id.txtItemNome);
        txtSubItemNome = findViewById(R.id.txtSubItemNome);
        txtElementoNome = findViewById(R.id.txtElementoNome);
        txtBancoNome = findViewById(R.id.txtBancoNome);
        txtContaNome = findViewById(R.id.txtContaNome);

        txtCategoria = findViewById(R.id.txtCategoria);
        txtTipo = findViewById(R.id.txtTipo);
        txtItem = findViewById(R.id.txtItem);
        txtSubItem = findViewById(R.id.txtSubItem);
        txtElemento = findViewById(R.id.txtElemento);
        txtBanco = findViewById(R.id.txtBanco);
        txtConta = findViewById(R.id.txtConta);

        FavoritoDao favoritoDao = FabricaDao.criarFavoritoDao();

        switch (opcao) {
            case "conta":
                txtCategoria.setEnabled(false);
                txtTipo.setEnabled(false);
                txtItem.setEnabled(false);
                txtSubItem.setEnabled(false);

                switch (operacao) {
                    case "update/insert":
                        //buscar no banco pelo nome, para buscar o id
                        ContaDao contaDao = FabricaDao.criarContaDao();
                        Conta conta = new Conta();
                        conta = contaDao.buscarByNome(carteira.getConta().getDescricao().toUpperCase());
                        carteira.setConta(conta);
                        posicao = conta.getIdConta();

                        txtBancoNome.setText(conta.getBancoNome());
                        txtContaNome.setText(conta.getDescricao());

                        //buscar no banco de favoritos se existia
                        favorito = favoritoDao.buscar(opcao, posicao);
                        if (favorito != null) {
                            //se existe, então atualiza
                            operacao = "update";
                            edDescricao.setText(favorito.getDescricao());
                        } else {
                            //se não exite, insere um nos favoritos
                            operacao = "insert";
                            favorito = new Favorito();
                            favorito.setOpcao(opcao);
                            favorito.setIdOpcao(conta.getIdConta());
                            favorito.setIdBanco(conta.getIdBanco());
                            favorito.setIdConta(conta.getIdConta());

                        }
                        break;

                    case "ler":
                        //somente ler as informações
                        favorito = favoritoDao.buscarById(favorito.getIdFavorito());
                        txtBancoNome.setText(lancamento.getConta().getBancoNome());
                        txtContaNome.setText(lancamento.getConta().getDescricao());
                        edDescricao.setText(favorito.getDescricao());
                        edDescricao.setEnabled(false);
                        btn_Salvar.setEnabled(false);
                        break;
                }

                break;

            case "subitem":
                txtBanco.setEnabled(false);
                txtConta.setEnabled(false);
                switch (operacao) {
                    case "update/insert":

                        //se for novo registro, verificar no banco qual o id, buscado pela descricao
                        if (lancamento.getSubitem().getIdSubItem() == null) {
                            SubItemDao subItemDao = FabricaDao.criarSubitemDao();
                            SubItem subItem = new SubItem();
                            subItem = subItemDao.buscarByNome(lancamento.getSubitem().getDescricao());
                            lancamento.setSubitem(subItem);
                            posicao = subItem.getIdSubItem();
                        } else {
                            //talvez possa tirar essa linha, pois no update/insert sempre será null no ler e que existe
                            posicao = lancamento.getSubitem().getIdSubItem();
                        }

                        //buscar os nomes para preencher na tela
                        SubItemDao daoSub = FabricaDao.criarSubitemDao();
                        lancamento = daoSub.buscar(lancamento);

                        idCategoria = lancamento.getSubitem().getIdCategoria();
                        categoria = lancamento.getSubitem().getCategoriaNome();
                        idTipo = lancamento.getSubitem().getIdTipo();
                        tipo = lancamento.getSubitem().getTipoNome();
                        idItem = lancamento.getSubitem().getIdItem();
                        item = lancamento.getSubitem().getItemNome();
                        idSubItem = lancamento.getSubitem().getIdSubItem();
                        subitem = lancamento.getSubitem().getDescricao();

                        txtCategoriaNome.setText(categoria);
                        txtTipoNome.setText(tipo);
                        txtItemNome.setText(item);
                        txtSubItemNome.setText(subitem);


                        //buscar os parametros se houver
                        favorito = favoritoDao.buscar(opcao, posicao);

                        if (favorito != null) {
                            operacao = "update";
                            edDescricao.setText(favorito.getDescricao());
                        } else {
                            operacao = "insert";
                            favorito = new Favorito();
                            favorito.setOpcao(opcao);
                            favorito.setIdOpcao(idSubItem);
                            favorito.setIdCategoria(idCategoria);
                            favorito.setIdTipo(idTipo);
                            favorito.setIdItem(idItem);
                            favorito.setIdSubItem(idSubItem);

                        }
                        break;

                    case "ler":
                        favorito = favoritoDao.buscarById(favorito.getIdFavorito());
                        txtCategoriaNome.setText(lancamento.getSubitem().getCategoriaNome());
                        txtTipoNome.setText(lancamento.getSubitem().getTipoNome());
                        txtItemNome.setText(lancamento.getSubitem().getItemNome());
                        txtSubItemNome.setText(lancamento.getSubitem().getDescricao());

                        edDescricao.setText(favorito.getDescricao());
                        edDescricao.setEnabled(false);
                        btn_Salvar.setEnabled(false);
                        break;
                }

                break;

            case "elemento":
                txtBanco.setEnabled(false);
                txtConta.setEnabled(false);
                switch (operacao) {
                    case "update/insert":

                        //se for novo registro, verificar no banco qual o id, buscado pela descricao
                        if (lancamento.getElemento().getIdElemento() == null) {
                            ElementoDao elementoDao = FabricaDao.criarElementoDao();
                            Elemento elemento = new Elemento();
                            elemento = elementoDao.buscarByNome(lancamento.getElemento().getDescricao());
                            lancamento.setElemento(elemento);
                            posicao = elemento.getIdElemento();
                        } else {
                            //talvez possa tirar essa linha, pois no update/insert sempre será null no ler e que existe
                            posicao = lancamento.getElemento().getIdElemento();
                        }

                        //buscar os nomes para preencher na tela
                        ElementoDao elementoDao = FabricaDao.criarElementoDao();
                        lancamento = elementoDao.buscar(lancamento);

                        idCategoria = lancamento.getElemento().getIdElemento();
                        categoria = lancamento.getElemento().getCategoriaNome();
                        idTipo = lancamento.getElemento().getIdTipo();
                        tipo = lancamento.getElemento().getTipoNome();
                        idItem = lancamento.getElemento().getIdItem();
                        item = lancamento.getElemento().getItemNome();
                        idSubItem = lancamento.getElemento().getIdSubItem();
                        subitem = lancamento.getElemento().getSubitemNome();
                        elemento = lancamento.getElemento().getDescricao();

                        txtCategoriaNome.setText(categoria);
                        txtTipoNome.setText(tipo);
                        txtItemNome.setText(item);
                        txtSubItemNome.setText(subitem);
                        txtElementoNome.setText(elemento);


                        //buscar os parametros se houver
                        favorito = favoritoDao.buscar(opcao, posicao);

                        if (favorito != null) {
                            operacao = "update";
                            edDescricao.setText(favorito.getDescricao());
                        } else {
                            operacao = "insert";
                            favorito = new Favorito();
                            favorito.setOpcao(opcao);
                            favorito.setIdOpcao(idSubItem);
                            favorito.setIdCategoria(idCategoria);
                            favorito.setIdTipo(idTipo);
                            favorito.setIdItem(idItem);
                            favorito.setIdSubItem(idSubItem);
                            favorito.setIdElemento(idElemento);

                        }
                        break;

                    case "ler":
                        favorito = favoritoDao.buscarById(favorito.getIdFavorito());
                        txtCategoriaNome.setText(lancamento.getElemento().getCategoriaNome());
                        txtTipoNome.setText(lancamento.getElemento().getTipoNome());
                        txtItemNome.setText(lancamento.getElemento().getItemNome());
                        txtSubItemNome.setText(lancamento.getElemento().getSubitemNome());
                        txtElementoNome.setText(lancamento.getElemento().getDescricao());

                        edDescricao.setText(favorito.getDescricao());
                        edDescricao.setEnabled(false);
                        btn_Salvar.setEnabled(false);
                        break;
                }

                break;


        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSalvar:
                favorito.setDescricao(edDescricao.getText().toString());
                FavoritoDao favoritoDao = FabricaDao.criarFavoritoDao();
                if (favoritoDao.listarNomes().contains(favorito.getDescricao())) {
                    Toast.makeText(this, "Erro: Já existe!", Toast.LENGTH_SHORT).show();
                } else {

                    switch (operacao) {
                        case "insert":
                            id = favoritoDao.salvar(favorito);
                            break;
                        case "update":
                            id = favoritoDao.alterar(favorito);
                            break;
                    }

                    if (id != -1) {
                        Toast.makeText(this, R.string.txt_cadastrado, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Erro ao Salvar/Alterar", Toast.LENGTH_SHORT).show();
                    }
                    this.finish();
                }
                break;
            case R.id.btnVoltar:
                this.finish();
                break;
        }
    }
}


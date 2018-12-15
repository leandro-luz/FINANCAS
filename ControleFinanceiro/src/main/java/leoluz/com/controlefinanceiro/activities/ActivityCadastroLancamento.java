package leoluz.com.controlefinanceiro.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import persist.DAO.ElementoDao;
import persist.DAO.FabricaDao;
import persist.DAO.FavoritoDao;
import persist.DAO.ItemDao;
import persist.DAO.LancamentoDao;
import persist.DAO.SubItemDao;
import persist.DAO.TipoDao;

import static leoluz.com.controlefinanceiro.activities.ActivityResumo.DATE_DIALOG_ID;

public class ActivityCadastroLancamento extends AppCompatActivity implements View.OnClickListener {
    private AlertDialog alerta;

    static final int DATE_DIALOG_ID = 0;

    Integer dia;
    Integer mes;
    Integer ano;

    EditText edCategoria;
    EditText edTipo;
    EditText edItem;
    EditText edSubitem;
    EditText edElemento;
    EditText edBanco;
    EditText edConta;
    EditText edData;
    EditText edValor;

    Categoria categoria = new Categoria();
    Tipo tipo = new Tipo();
    Item item = new Item();
    SubItem subItem = new SubItem();
    Elemento elemento = new Elemento();

    Lancamento lancamento;

    String opcao;
    String operacao;

    Button btn_Estrutura;
    Button btn_Elemento;
    Button btn_Conta;
    Button btn_Salvar;
    Button btn_Excluir;
    Button btn_Voltar;
    Button btn_Date;

    AlertDialog.Builder builder;

    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrolancamento);

        //atribuindo ações aos botões
        btn_Salvar = findViewById(R.id.btnSalvar);
        btn_Salvar.setOnClickListener(this);
        btn_Excluir = findViewById(R.id.btnExcluir);
        btn_Excluir.setOnClickListener(this);
        btn_Voltar = findViewById(R.id.btnVoltar);
        btn_Voltar.setOnClickListener(this);
        btn_Estrutura = findViewById(R.id.btnEstrutura);
        btn_Estrutura.setOnClickListener(this);
        btn_Elemento = findViewById(R.id.btnElemento);
        btn_Elemento.setOnClickListener(this);
        btn_Conta = findViewById(R.id.btnConta);
        btn_Conta.setOnClickListener(this);
        btn_Date = findViewById(R.id.btnDate);
        btn_Date.setOnClickListener(this);

        //atribuição ação aos campos de texto
        edCategoria = findViewById(R.id.textCategoria);
        edCategoria.setEnabled(false);
        edTipo = findViewById(R.id.textTipo);
        edTipo.setEnabled(false);
        edItem = findViewById(R.id.textItem);
        edItem.setEnabled(false);
        edSubitem = findViewById(R.id.textSubItem);
        edSubitem.setEnabled(false);
        edElemento = findViewById(R.id.textElemento);
        edElemento.setEnabled(false);
        edBanco = findViewById(R.id.textBanco);
        edBanco.setEnabled(false);
        edConta = findViewById(R.id.textConta);
        edConta.setEnabled(false);
        edData = findViewById(R.id.textData);
        edValor = findViewById(R.id.textValor);

        //recebendo a opção
        Intent it = getIntent();
        opcao = it.getStringExtra("opcao");
        operacao = it.getStringExtra("operacao");
        lancamento = (Lancamento) it.getSerializableExtra("lancamento");

        if (lancamento == null) {
            lancamento = new Lancamento();
        }

        //alterando o titulo da tela
        setTitle("CADASTRO DE " + opcao.toUpperCase());


        switch (operacao) {
            case "insert":
                btn_Excluir.setEnabled(false);
                //preenchendo a tela
                if (lancamento.getCategoria() != null) {
                    edCategoria.setText(lancamento.getCategoria().getDescricao());
                    edTipo.setText(lancamento.getTipo().getDescricao());
                    edItem.setText(lancamento.getItem().getDescricao());
                    edSubitem.setText(lancamento.getSubitem().getDescricao());
                    edElemento.setText(lancamento.getElemento().getDescricao());
                }
                if (lancamento.getBanco() != null) {
                    edBanco.setText(lancamento.getBanco().getDescricao());
                    edConta.setText(lancamento.getConta().getDescricao());
                }
                break;

            case "update":
                //preenchendo a tela
                edCategoria.setText(lancamento.getCategoria().getDescricao());
                edTipo.setText(lancamento.getTipo().getDescricao());
                edItem.setText(lancamento.getItem().getDescricao());
                edSubitem.setText(lancamento.getSubitem().getDescricao());
                edElemento.setText(lancamento.getElemento().getDescricao());
                edBanco.setText(lancamento.getBanco().getDescricao());
                edConta.setText(lancamento.getConta().getDescricao());
                edData.setText(lancamento.getData());
                edValor.setText(lancamento.getValor().toString());
                break;
        }


    }

    //vericar as operações, o modo insert já está pronto, arrumar para o update o tipo salvar
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSalvar:
                switch (operacao) {
                    case "insert":
                        if (edCategoria.length() == 0) {
                            Toast.makeText(this, R.string.txt_Estrutura, Toast.LENGTH_SHORT).show();
                        } else {
                            if (edBanco.length() == 0) {
                                Toast.makeText(this, R.string.txt_Conta, Toast.LENGTH_SHORT).show();
                            } else {
                                if (edData.length() == 0) {
                                    Toast.makeText(this, R.string.txt_Data, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (edValor.length() == 0) {
                                        Toast.makeText(this, R.string.txt_Valor, Toast.LENGTH_SHORT).show();
                                    } else {
                                        LancamentoDao fabricaDao = FabricaDao.criarLancamentoDao();
                                        lancamento.setData(edData.getText().toString());
                                        lancamento.setValor(Float.parseFloat(edValor.getText().toString()));
                                        id = fabricaDao.salvar(lancamento);
                                    }
                                }
                            }
                        }

                        break;
                    case "update":
                        LancamentoDao lancamentoDao = FabricaDao.criarLancamentoDao();
                        lancamento.setData(edData.getText().toString());
                        lancamento.setValor(Float.parseFloat(edValor.getText().toString()));
                        id = lancamentoDao.alterar(lancamento);
                        break;
                }

                if (id != null && id != -1) {
                    novoCadastro();
                }
                break;

            case R.id.btnDate:
                showDialog(DATE_DIALOG_ID);
                break;

            case R.id.btnEstrutura:
                FavoritoDao favoritoDao = FabricaDao.criarFavoritoDao();
                if (favoritoDao.listarNomesbyOpcao("subitem").size() > 0) {
                    Intent fav = new Intent(this, ActivityListaFavorito.class);
                    fav.putExtra("opcao", opcao);
                    fav.putExtra("operacao", operacao);
                    fav.putExtra("lista", "subitem");
                    fav.putExtra("lancamento", lancamento);
                    startActivity(fav);
                    finish();
                } else {
                    Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnConta:
                FavoritoDao favoritoDao2 = FabricaDao.criarFavoritoDao();
                if (favoritoDao2.listarNomesbyOpcao("conta").size() > 0) {
                    Intent fav = new Intent(this, ActivityListaFavorito.class);
                    fav.putExtra("opcao", opcao);
                    fav.putExtra("operacao", operacao);
                    fav.putExtra("lista", "conta");
                    fav.putExtra("lancamento", lancamento);
                    startActivity(fav);
                    finish();
                } else {
                    Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnElemento:
                if (edSubitem.length() == 0) {
                    Toast.makeText(this, R.string.txt_btSubItemVazio, Toast.LENGTH_SHORT).show();
                } else {
                    //buscar a lista de elementos
                    ElementoDao elementoDao = FabricaDao.criarElementoDao();
                    List<Elemento> lista = elementoDao.listarTodosReferencia(
                            lancamento.getCategoria().getIdCategoria(),
                            lancamento.getTipo().getIdTipo(),
                            lancamento.getItem().getIdItem(),
                            lancamento.getSubitem().getIdSubItem());
                    //Verificar se a lista esta vazia
                    if(lista.size()!=0){
                        Intent lisub = new Intent(this, ActivityLista.class);
                        lancamento.setElemento(null);

                        lisub.putExtra("opcao", opcao);
                        lisub.putExtra("lista", "elemento");
                        lisub.putExtra("categoria", lancamento.getCategoria().getIdCategoria());
                        lisub.putExtra("tipo", lancamento.getTipo().getIdTipo());
                        lisub.putExtra("item", lancamento.getItem().getIdItem());
                        lisub.putExtra("subitem", lancamento.getSubitem().getIdSubItem());

                        lisub.putExtra("lancamento", lancamento);
                        startActivity(lisub);
                        finish();
                    }else{
                        Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btnExcluir:
                LancamentoDao fabricaDao = FabricaDao.criarLancamentoDao();
                id = fabricaDao.excluir(lancamento);
                if (id != -1) {
                    novoCadastro();
                } else {
                    Toast.makeText(this, R.string.txt_erroCadastro, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnVoltar:
                this.finish();
                break;
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes,
                        dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    dia = dayOfMonth;
                    mes = monthOfYear + 1;
                    ano = year;
                    String data = String.valueOf(dayOfMonth) + " /"
                            + String.valueOf(monthOfYear + 1) + " /" + String.valueOf(year);
                    edData.setText(data);
                }
            };

    private void novoCadastro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        switch (operacao) {
            case "insert":
                builder.setTitle("Cadastro realizado com sucesso");
                //define a mensagem
                builder.setMessage("Realizar outro cadastro?");
                //define um botão como positivo
                break;
            case "update":
                builder.setTitle("Alteração realizada com sucesso");
                //define a mensagem
                builder.setMessage("Realizar outra alteração?");
                //define um botão como positivo
                break;
            case "excluir":
                builder.setTitle("Exclusão realizada com sucesso");
                //define a mensagem
                builder.setMessage("Realizar outra exclusão?");
                //define um botão como positivo
                break;

        }

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //limparCampos();
                switch (operacao) {
                    case "insert":
                        //it = new Intent(ActivityCadastroLancamento.this, ActivityCadastroLancamento.class);
                        limparCampos();
                        break;
                    case "update":
                      sair();
                        break;
                    case "excluir":
                        sair();
                        break;
                }


            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }


    private void sair(){
        Intent it = null;
        it = new Intent(ActivityCadastroLancamento.this, ActivityListaLancamento.class);
        it.putExtra("lancamento", lancamento);
        it.putExtra("opcao", opcao);
        it.putExtra("lista", opcao);
        startActivity(it);
        finish();
    }



    private void limparCampos() {
        edCategoria.setText("");
        edTipo.setText("");
        edItem.setText("");
        edSubitem.setText("");
        edElemento.setText("");
        edBanco.setText("");
        edConta.setText("");
        edData.setText("");
        edValor.setText("");
    }

}



package leoluz.com.controlefinanceiro.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import leoluz.com.controlefinanceiro.R;
import model.Banco;
import model.Carteira;
import model.Conta;
import model.Elemento;
import model.Lancamento;
import model.SubItem;
import persist.DAO.BancoDao;
import persist.DAO.CategoriaDao;
import persist.DAO.ContaDao;
import persist.DAO.ElementoDao;
import persist.DAO.FabricaDao;
import persist.DAO.FavoritoDao;
import persist.DAO.ItemDao;
import persist.DAO.SubItemDao;
import persist.DAO.TipoDao;
import util.QuantidadeLista;

public class ActivityCadastroContas extends AppCompatActivity implements View.OnClickListener {
    private AlertDialog alerta;

    AlertDialog.Builder builder;

    EditText edSaldo;
    EditText edBanco;
    EditText edConta;

    Button btn_Banco;
    Button btn_Salvar;
    Button btn_Excluir;
    Button btn_Voltar;

    Switch swt_Favorito;
    Switch swt_Habilitado;

    String opcao;
    String operacao;

    List<Banco> listaBanco;
    List<Conta> listaConta;

    Carteira carteira = new Carteira();

    Banco banco = new Banco();
    Conta conta = new Conta();

    Boolean favoritoAlterado = false;
    Boolean excluirFavorito = false;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrocontas);

        btn_Banco = findViewById(R.id.btnBanco);
        btn_Banco.setOnClickListener(this);
        btn_Salvar = findViewById(R.id.btnSalvar);
        btn_Salvar.setOnClickListener(this);
        btn_Excluir = findViewById(R.id.btnExcluir);
        btn_Excluir.setOnClickListener(this);
        btn_Voltar = findViewById(R.id.btnVoltar);
        btn_Voltar.setOnClickListener(this);

        swt_Favorito = findViewById(R.id.swtFavorito);
        swt_Favorito.setOnClickListener(this);
        swt_Habilitado = findViewById(R.id.swtHabilitado);
        swt_Habilitado.setOnClickListener(this);

        edBanco = findViewById(R.id.textBanco);
        edConta = findViewById(R.id.textConta);
        edSaldo = findViewById(R.id.textSaldo);

        //recebendo a opção
        Intent it = getIntent();
        opcao = it.getStringExtra("opcao");
        operacao = it.getStringExtra("operacao");
        carteira = (Carteira) it.getSerializableExtra("carteira");

        setTitle("CADASTRO DE " + opcao.toUpperCase());

        switch (operacao) {
            case "insert":
                btn_Excluir.setEnabled(false);
                swt_Habilitado.setChecked(true);
                swt_Habilitado.setEnabled(false);
                swt_Favorito.setEnabled(false);
                switch (opcao) {
                    case "banco":
                        btn_Banco.setEnabled(false);
                        edConta.setEnabled(false);
                        edSaldo.setEnabled(false);

                        break;
                    case "conta":
                        if (carteira != null) {
                            edBanco.setText(carteira.getBanco().getDescricao());
                        }
                        edBanco.setEnabled(false);
                        break;
                }
                break;
            case "update":

                switch (opcao) {
                    case "banco":
                        //verificar se estava habilitado
                        if (carteira.getBanco().getHabilitado() == 1) {
                            swt_Habilitado.setChecked(true);
                        } else {
                            swt_Habilitado.setChecked(false);
                        }
                        btn_Banco.setEnabled(false);
                        edConta.setEnabled(false);
                        edSaldo.setEnabled(false);
                        swt_Favorito.setEnabled(false);
                        if (carteira.getBanco() != null) {
                            edBanco.setText(carteira.getBanco().getDescricao());
                        }
                        break;
                    case "conta":
                        if (carteira.getConta() != null) {
                            edBanco.setText(carteira.getConta().getBancoNome());
                            edConta.setText(carteira.getConta().getDescricao());
                            edSaldo.setText(carteira.getConta().getSaldo().toString());
                        }
                        //verificar se estava como favorito
                        if (carteira.getConta().getFavorito() == 1) {
                            swt_Favorito.setChecked(true);
                        } else {
                            swt_Favorito.setChecked(false);
                        }
                        //verificar se esta habilitado
                        if (carteira.getConta().getHabilitado() == 1) {
                            swt_Habilitado.setChecked(true);
                        } else {
                            swt_Habilitado.setChecked(false);
                            swt_Favorito.setEnabled(false);
                        }

                        btn_Banco.setEnabled(false);
                        edBanco.setEnabled(false);
                        break;
                }
                break;
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSalvar:

                switch (opcao) {
                    case "banco":
                        if (edBanco.getText().length() == 0) {
                            Toast.makeText(this, R.string.txt_BancoVazio, Toast.LENGTH_SHORT).show();
                        } else {
                            BancoDao bancoDao = FabricaDao.criarBancoDao();
                            switch (operacao) {
                                case "insert":
                                    banco.setDescricao(edBanco.getText().toString());
                                    banco.setHabilitado(1);
                                    id = bancoDao.salvar(banco);
                                    break;
                                case "update":
                                    //verificar se a descrição da tela está diferente da que esta no banco
                                    //se for diferente verifica se a descriação já não existe

                                    List<String> lista = bancoDao.listarTodosString();
                                    if ((!carteira.getBanco().getDescricao().equalsIgnoreCase(edBanco.getText().toString().toUpperCase()) &&
                                            lista.contains(edBanco.getText().toString().toUpperCase()))) {
                                        Toast.makeText(this, "A descrição já existe!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        carteira.getBanco().setDescricao(edBanco.getText().toString().toUpperCase());
                                        id = bancoDao.alterar(carteira);
                                    }
                                    break;
                            }
                            if (id != -1) {
                                novoCadastro();
                            } else {
                                Toast.makeText(this, R.string.txt_naoCadastrado, Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case "conta":
                        if (edBanco.getText().length() == 0) {
                            Toast.makeText(this, R.string.txt_BancoVazio, Toast.LENGTH_SHORT).show();
                        } else {
                            if (edConta.getText().length() == 0) {
                                Toast.makeText(this, R.string.txt_ContaVazio, Toast.LENGTH_SHORT).show();
                            } else {
                                ContaDao contaDao = FabricaDao.criarContaDao();
                                switch (operacao) {
                                    case "insert":
                                        conta.setIdBanco(carteira.getBanco().getIdBanco());
                                        conta.setDescricao(edConta.getText().toString());
                                        //colocar 0, quando não preenchido
                                        if (edSaldo.getText().length() == 0) {
                                            edSaldo.setText("0.00");
                                        }
                                        conta.setSaldo(Float.valueOf(edSaldo.getText().toString()));
                                        conta.setHabilitado(1);
                                        if (swt_Favorito.isChecked()) {
                                            conta.setFavorito(1);
                                            favoritoAlterado = true;
                                        } else {
                                            conta.setFavorito(0);
                                        }
                                        id = contaDao.salvar(conta);
                                        carteira.setConta(conta);
                                        break;

                                    case "update":
                                        //verificar se a descrição da tela está diferente da que esta no banco
                                        //se for diferente verifica se a descriação já não existe

                                        Integer contador = contaDao.contarDescricao(carteira.getConta().getIdBanco(), edConta.getText().toString().toUpperCase());
                                        if(contador > 0){
                                            id = Long.parseLong( String.valueOf(contador*-1));
                                            favoritoAlterado = false;
                                            excluirFavorito = false;
                                        } else {
                                            carteira.getConta().setDescricao(edConta.getText().toString());
                                            carteira.getConta().setSaldo(Float.valueOf(edSaldo.getText().toString()));
                                            //setar o item favorito
                                            if (swt_Favorito.isChecked()) {
                                                carteira.getConta().setFavorito(1);
                                            } else {
                                                carteira.getConta().setFavorito(0);
                                            }
                                            id = contaDao.alterar(carteira);
                                        }
                                        break;
                                }
                                if (id != -1) {
                                    if (favoritoAlterado) {
                                        listaFavorito();
                                    }
                                    if (excluirFavorito) {
                                        FavoritoDao favoritoDao = FabricaDao.criarFavoritoDao();
                                        favoritoDao.excluir("conta", carteira.getConta().getIdConta());
                                    }

                                    novoCadastro();
                                } else {
                                    Toast.makeText(this, R.string.txt_naoCadastrado, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        break;
                }
                break;

            case R.id.btnBanco:
                listaBanco = new ArrayList<>();
                if (QuantidadeLista.verificarQtd("banco") > 0) {
                    Intent listcat = new Intent(this, ActivityLista.class);
                    listcat.putExtra("opcao", opcao);
                    listcat.putExtra("lista", "banco");
                    startActivity(listcat);
                    finish();
                } else {
                    Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnExcluir:
                switch (opcao) {
                    case "banco":
                        BancoDao bancoDao = FabricaDao.criarBancoDao();
                        id = bancoDao.excluir(carteira);
                        break;
                    case "conta":
                        ContaDao contaDao = FabricaDao.criarContaDao();
                        id = contaDao.excluir(carteira);
                        break;
                }
                if (id != -1) {
                    operacao = "excluir";
                    novoCadastro();
                } else {
                    Toast.makeText(this, "Não poder ser excluído!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnVoltar:
                //Intent it = new Intent(this, ActivityAcao.class);
                //it.putExtra("opcao", opcao);
                //startActivity(it);
                this.finish();
                break;

            case R.id.swtHabilitado:
                switch (operacao) {
                    case "update":
                        switch (opcao) {
                            case "banco":
                                BancoDao bancoDao = FabricaDao.criarBancoDao();
                                if (carteira.getBanco().getHabilitado() > 0) {
                                    carteira.getBanco().setHabilitado(0);
                                } else {
                                    carteira.getBanco().setHabilitado(1);
                                }
                                id = bancoDao.alterarStatus(carteira);
                                break;

                            case "conta":
                                ContaDao contaDao = FabricaDao.criarContaDao();
                                if (carteira.getConta().getHabilitado() > 0) {
                                    carteira.getConta().setHabilitado(0);
                                } else {
                                    carteira.getConta().setHabilitado(1);
                                }
                                id = contaDao.alterarStatus(carteira);
                                break;
                        }
                        if (id != -1) {
                            Toast.makeText(this, "Operação realizado com sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Erro neste operação", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;

            case R.id.swtFavorito:
                switch (operacao) {
                    case "update":
                        switch (opcao) {

                            case "conta":
                                ContaDao contaDao = FabricaDao.criarContaDao();
                                if (carteira.getConta().getFavorito() > 0) {
                                    FavoritoDao favoritoDao = FabricaDao.criarFavoritoDao();
                                    favoritoDao.excluir("conta", carteira.getConta().getIdConta());
                                    carteira.getConta().setFavorito(0);
                                } else {
                                    listaFavorito();
                                    carteira.getConta().setFavorito(1);
                                }
                                id = contaDao.alterarStatus(carteira);
                                break;
                        }
                        if (id != -1) {
                            Toast.makeText(this, "Operação realizado com sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Erro neste operação", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
        }
    }


    private void listaFavorito() {
        Intent it = new Intent(ActivityCadastroContas.this, ActivityFavorito.class);
        it.putExtra("carteira", carteira);
        it.putExtra("operacao", "update/insert");
        it.putExtra("opcao", opcao);
        startActivity(it);
    }


    private void novoCadastro() {
        builder = new AlertDialog.Builder(this);
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
                Intent it = null;
                limparCampos();
                switch (operacao) {
                    case "insert":
                        //it = new Intent(ActivityCadastroContas.this, ActivityCadastroContas.class);
                        //it.putExtra("operacao", "insert");
                        //it.putExtra("opcao", opcao);
                        limparCampos();
                        break;
                    case "update":
                        //it = new Intent(ActivityCadastroContas.this, ActivityListaCompleta.class);
                        //it.putExtra("opcao", opcao);
                        //it.putExtra("lista", opcao);
                        //startActivity(it);
                        finish();
                        break;
                    case "excluir":
                        //it = new Intent(ActivityCadastroContas.this, ActivityListaCompleta.class);
                        //it.putExtra("opcao", opcao);
                        //it.putExtra("lista", opcao);
                        //startActivity(it);
                        finish();
                        break;
                }

            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                sair();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();

    }


    private void sair() {
        finish();
    }

    private void limparCampos() {
        edBanco.setText("");
        edConta.setText("");
        edSaldo.setText("");
    }
}
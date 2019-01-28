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

import java.util.List;

import model.Elemento;
import model.Favorito;
import model.Item;
import model.Lancamento;
import model.SubItem;
import model.Tipo;
import persist.DAO.CategoriaDao;
import persist.DAO.ElementoDao;
import persist.DAO.FabricaDao;
import leoluz.com.controlefinanceiro.R;
import model.Categoria;
import persist.DAO.FavoritoDao;
import persist.DAO.ItemDao;
import persist.DAO.SubItemDao;
import persist.DAO.TipoDao;

public class ActivityCadastro extends AppCompatActivity implements View.OnClickListener {
    private AlertDialog alerta;

    EditText edCategoria;
    EditText edTipo;
    EditText edItem;
    EditText edSubitem;
    EditText edElemento;

    Categoria categoria = new Categoria();
    Tipo tipo = new Tipo();
    Item item = new Item();
    SubItem subItem = new SubItem();
    Elemento elemento = new Elemento();
    Lancamento lancamento;

    String opcao;
    String operacao;
    Button btn_Categoria;
    Button btn_Tipo;
    Button btn_Item;
    Button btn_SubItem;
    Button btn_Salvar;
    Button btn_Excluir;
    Button btn_Voltar;

    Switch swt_Favorito;
    Switch swt_Habilitado;
    Switch swt_Incremento;

    AlertDialog.Builder builder;

    Boolean favoritoAlterado = false;
    Boolean excluirFavorito = false;

    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        //atribuindo ações aos botões
        btn_Salvar = findViewById(R.id.btnSalvar);
        btn_Salvar.setOnClickListener(this);
        btn_Excluir = findViewById(R.id.btnExcluir);
        btn_Excluir.setOnClickListener(this);
        btn_Voltar = findViewById(R.id.btnVoltar);
        btn_Voltar.setOnClickListener(this);
        btn_Categoria = findViewById(R.id.btnCategoria);
        btn_Categoria.setOnClickListener(this);
        btn_Tipo = findViewById(R.id.btnTipo);
        btn_Tipo.setOnClickListener(this);
        btn_Item = findViewById(R.id.btnItem);
        btn_Item.setOnClickListener(this);
        btn_SubItem = findViewById(R.id.btnSubItem);
        btn_SubItem.setOnClickListener(this);
        swt_Favorito = findViewById(R.id.swtFavorito);
        swt_Habilitado = findViewById(R.id.swtHabilitado);
        swt_Incremento = findViewById(R.id.swtIncremento);

        //atribuição ação aos campos de texto
        edCategoria = findViewById(R.id.textCategoria);
        edTipo = findViewById(R.id.textTipo);
        edItem = findViewById(R.id.textItem);
        edSubitem = findViewById(R.id.textSubItem);
        edElemento = findViewById(R.id.textElemento);

        //recebendo a opção
        Intent it = getIntent();
        opcao = it.getStringExtra("opcao");
        operacao = it.getStringExtra("operacao");

        lancamento = (Lancamento) it.getSerializableExtra("lancamento");
        //alterando o titulo da tela
        setTitle("CADASTRO DE " + opcao.toUpperCase());

        switch (operacao) {
            case "insert":
                swt_Habilitado.setChecked(true);
                swt_Incremento.setChecked(false);
                swt_Habilitado.setEnabled(false);
                if (lancamento == null) {
                    lancamento = new Lancamento();
                } else {
                    //se não estiver nulo desabilita itens da tela conforme a opção
                    if (lancamento.getCategoria() != null) {
                        edCategoria.setText(lancamento.getCategoria().getDescricao());
                        edCategoria.setEnabled(false);
                    }
                    if (lancamento.getTipo() != null) {
                        edTipo.setText(lancamento.getTipo().getDescricao());
                        edTipo.setEnabled(false);
                    }
                    if (lancamento.getItem() != null) {
                        edItem.setText(lancamento.getItem().getDescricao());
                        edItem.setEnabled(false);
                    }
                    if (lancamento.getSubitem() != null) {
                        edSubitem.setText(lancamento.getSubitem().getDescricao());
                        edSubitem.setEnabled(false);
                    }
                    if (lancamento.getElemento() != null) {
                        edElemento.setText(lancamento.getElemento().getDescricao());
                        edElemento.setEnabled(false);
                    }
                }
                btn_Excluir.setEnabled(false);
                switch (opcao) {
                    case "categoria":
                        btn_Categoria.setEnabled(false);
                        btn_Tipo.setEnabled(false);
                        btn_Item.setEnabled(false);
                        btn_SubItem.setEnabled(false);
                        swt_Favorito.setEnabled(false);
                        swt_Habilitado.setEnabled(false);
                        edTipo.setEnabled(false);
                        edItem.setEnabled(false);
                        edSubitem.setEnabled(false);
                        edElemento.setEnabled(false);
                        break;
                    case "tipo":
                        btn_Tipo.setEnabled(false);
                        btn_Item.setEnabled(false);
                        btn_SubItem.setEnabled(false);
                        swt_Favorito.setEnabled(false);
                        swt_Habilitado.setEnabled(false);
                        swt_Incremento.setEnabled(false);
                        edCategoria.setEnabled(false);
                        edItem.setEnabled(false);
                        edSubitem.setEnabled(false);
                        edElemento.setEnabled(false);
                        break;
                    case "item":
                        btn_Item.setEnabled(false);
                        btn_SubItem.setEnabled(false);
                        edCategoria.setEnabled(false);
                        swt_Favorito.setEnabled(false);
                        swt_Habilitado.setEnabled(false);
                        swt_Incremento.setEnabled(false);
                        edTipo.setEnabled(false);
                        edSubitem.setEnabled(false);
                        edElemento.setEnabled(false);
                        break;
                    case "subitem":
                        btn_SubItem.setEnabled(false);
                        swt_Favorito.setChecked(false);
                        swt_Habilitado.setEnabled(false);
                        swt_Incremento.setEnabled(false);
                        edCategoria.setEnabled(false);
                        edTipo.setEnabled(false);
                        edItem.setEnabled(false);
                        edElemento.setEnabled(false);
                        break;
                    case "elemento":
                        swt_Favorito.setEnabled(false);
                        swt_Habilitado.setEnabled(false);
                        swt_Incremento.setEnabled(false);
                        edCategoria.setEnabled(false);
                        edTipo.setEnabled(false);
                        edItem.setEnabled(false);
                        edSubitem.setEnabled(false);
                        break;
                    default:
                }
                break;

            case "update":
                btn_Categoria.setEnabled(false);
                btn_Tipo.setEnabled(false);
                btn_Item.setEnabled(false);
                btn_SubItem.setEnabled(false);
                switch (opcao) {
                    case "categoria":
                        //verificar se estava habilitado
                        if (lancamento.getCategoria().getHabilitado() == 1) {
                            swt_Habilitado.setChecked(true);
                        } else {
                            swt_Habilitado.setChecked(false);
                        }
                        if (lancamento.getCategoria().getIncremento() == 1) {
                            swt_Incremento.setChecked(true);
                        } else {
                            swt_Incremento.setChecked(false);
                        }
                        swt_Favorito.setEnabled(false);
                        edCategoria.setText(lancamento.getCategoria().getDescricao());
                        edTipo.setEnabled(false);
                        edItem.setEnabled(false);
                        edSubitem.setEnabled(false);
                        edElemento.setEnabled(false);
                        break;
                    case "tipo":
                        //verificar se estava habilitado
                        if (lancamento.getTipo().getHabilitado() == 1) {
                            swt_Habilitado.setChecked(true);
                        } else {
                            swt_Habilitado.setChecked(false);
                        }
                        swt_Favorito.setEnabled(false);
                        swt_Incremento.setEnabled(false);
                        edCategoria.setText(lancamento.getTipo().getCategoriaNome());
                        edCategoria.setEnabled(false);
                        edTipo.setText(lancamento.getTipo().getDescricao());
                        edItem.setEnabled(false);
                        edSubitem.setEnabled(false);
                        edElemento.setEnabled(false);
                        break;
                    case "item":
                        //verificar se estava habilitado
                        if (lancamento.getItem().getHabilitado() == 1) {
                            swt_Habilitado.setChecked(true);
                        } else {
                            swt_Habilitado.setChecked(false);
                        }
                        swt_Favorito.setEnabled(false);
                        swt_Incremento.setEnabled(false);
                        edCategoria.setText(lancamento.getItem().getCategoriaNome());
                        edCategoria.setEnabled(false);
                        edTipo.setText(lancamento.getItem().getTipoNome());
                        edTipo.setEnabled(false);
                        edItem.setText(lancamento.getItem().getDescricao());
                        edSubitem.setEnabled(false);
                        edElemento.setEnabled(false);
                        break;
                    case "subitem":
                        //verificar se estava como favorito
                        if (lancamento.getSubitem().getFavorito() == 1) {
                            swt_Favorito.setChecked(true);
                        } else {
                            swt_Favorito.setChecked(false);
                        }
                        //verificar se estava habilitado
                        if (lancamento.getSubitem().getHabilitado() == 1) {
                            swt_Habilitado.setChecked(true);
                        } else {
                            swt_Habilitado.setChecked(false);
                        }
                        swt_Incremento.setEnabled(false);
                        edCategoria.setText(lancamento.getSubitem().getCategoriaNome());
                        edCategoria.setEnabled(false);
                        edTipo.setText(lancamento.getSubitem().getTipoNome());
                        edTipo.setEnabled(false);
                        edItem.setText(lancamento.getSubitem().getItemNome());
                        edItem.setEnabled(false);
                        edSubitem.setText(lancamento.getSubitem().getDescricao());
                        edElemento.setEnabled(false);
                        break;
                    case "elemento":
                        //verificar se estava habilitado
                        if (lancamento.getElemento().getHabilitado() == 1) {
                            swt_Habilitado.setChecked(true);
                        } else {
                            swt_Habilitado.setChecked(false);
                        }
                        swt_Incremento.setEnabled(false);
                        edCategoria.setText(lancamento.getElemento().getCategoriaNome());
                        edCategoria.setEnabled(false);
                        edTipo.setText(lancamento.getElemento().getTipoNome());
                        edTipo.setEnabled(false);
                        edItem.setText(lancamento.getElemento().getItemNome());
                        edItem.setEnabled(false);
                        edSubitem.setText(lancamento.getElemento().getSubitemNome());
                        edSubitem.setEnabled(false);
                        edElemento.setText(lancamento.getElemento().getDescricao());
                        break;
                    default:
                }
                break;
        }

    }

    //vericar as operações, o modo insert já está pronto, arrumar para o update o tipo salvar
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSalvar:
                switch (opcao) {
                    case "categoria":
                        if (edCategoria.getText().length() == 0) {
                            Toast.makeText(this, R.string.txt_DescricaoVazio, Toast.LENGTH_SHORT).show();
                        } else {
                            CategoriaDao categoriaDao = FabricaDao.criarCategoriaDao();
                            if (swt_Incremento.isChecked()) {//verifica se será um incremento ou decremento de valores das contas
                                categoria.setIncremento(1);
                            } else {
                                categoria.setIncremento(0);
                            }
                            switch (operacao) {
                                case "insert":
                                    categoria.setDescricao(edCategoria.getText().toString());
                                    categoria.setHabilitado(1);//inicia como habilitado
                                    lancamento.setCategoria(categoria);
                                    id = categoriaDao.salvar(lancamento);
                                    break;
                                case "update":
                                    List<String> lista = categoriaDao.listarTodosString();
                                    if ((!lancamento.getCategoria().getDescricao().equalsIgnoreCase(edCategoria.getText().toString().toUpperCase()) &&
                                            lista.contains(edCategoria.getText().toString().toUpperCase()))) {
                                        Toast.makeText(this, "A descrição já existe!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //verifica se está habilitado
                                        if (swt_Habilitado.isChecked()) {
                                            categoria.setHabilitado(1);
                                        } else {
                                            categoria.setHabilitado(0);
                                        }
                                        categoria.setDescricao(edCategoria.getText().toString());
                                        categoria.setIdCategoria(lancamento.getCategoria().getIdCategoria());
                                        lancamento.setCategoria(categoria);
                                        id = categoriaDao.alterar(lancamento);
                                    }
                                    break;
                            }

                            if (id != -1) {
                                novoCadastro();
                            } else {
                                Toast.makeText(this, R.string.txt_naoCadastrado, Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;//fim categoria

                    case "tipo":
                        if (edCategoria.getText().length() == 0) {
                            Toast.makeText(this, R.string.txt_DescricaoVazio, Toast.LENGTH_SHORT).show();
                        } else {
                            if (edTipo.getText().length() == 0) {
                                Toast.makeText(this, R.string.txt_TipoVazio, Toast.LENGTH_SHORT).show();
                            } else {
                                TipoDao tipoDao = FabricaDao.criarTipoDao();
                                switch (operacao) {
                                    case "insert":
                                        tipo.setDescricao(edTipo.getText().toString());
                                        tipo.setHabilitado(1);
                                        lancamento.setTipo(tipo);
                                        id = tipoDao.salvar(lancamento);
                                        break;
                                    case "update":
                                        Integer contador = tipoDao.contarDescricao(lancamento.getTipo().getIdCategoria(), edTipo.getText().toString().toUpperCase());
                                        if(contador > 0){
                                            id = Long.parseLong( String.valueOf(contador*-1));
                                        } else {
                                            //verifica se está habilitado
                                            if (swt_Habilitado.isChecked()) {
                                                tipo.setHabilitado(1);
                                            } else {
                                                tipo.setHabilitado(0);
                                            }
                                            tipo.setDescricao(edTipo.getText().toString());
                                            tipo.setIdTipo(lancamento.getTipo().getIdTipo());
                                            lancamento.setTipo(tipo);
                                            id = tipoDao.alterar(lancamento);
                                        }
                                        break;
                                }

                                if (id != -1) {
                                    novoCadastro();
                                } else {
                                    Toast.makeText(this, R.string.txt_naoCadastrado, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        break;//fim do tipo

                    case "item":
                        if (edCategoria.getText().length() == 0) {
                            Toast.makeText(this, R.string.txt_DescricaoVazio, Toast.LENGTH_SHORT).show();
                        } else {
                            if (edTipo.getText().length() == 0) {
                                Toast.makeText(this, R.string.txt_TipoVazio, Toast.LENGTH_SHORT).show();
                            } else {
                                if (edItem.getText().length() == 0) {
                                    Toast.makeText(this, R.string.txt_ItemVazio, Toast.LENGTH_SHORT).show();
                                } else {

                                    ItemDao itemDao = FabricaDao.criarItemDao();
                                    switch (operacao) {
                                        case "insert":
                                            item.setDescricao(edItem.getText().toString());
                                            item.setHabilitado(1);
                                            lancamento.setItem(item);
                                            id = itemDao.salvar(lancamento);
                                            break;
                                        case "update":
                                            Integer contador = itemDao.contarDescricao(lancamento.getItem().getIdCategoria(),
                                                    lancamento.getItem().getIdTipo(),
                                                    edItem.getText().toString().toUpperCase());
                                            if(contador > 0){
                                                id = Long.parseLong( String.valueOf(contador*-1));
                                            } else {
                                                //verifica se está habilitado
                                                if (swt_Habilitado.isChecked()) {
                                                    item.setHabilitado(1);
                                                } else {
                                                    item.setHabilitado(0);
                                                }
                                                item.setDescricao(edItem.getText().toString());
                                                item.setIdItem(lancamento.getItem().getIdItem());
                                                lancamento.setItem(item);
                                                id = itemDao.alterar(lancamento);
                                            }
                                            break;
                                    }

                                    if (id != -1) {
                                        novoCadastro();
                                    } else {
                                        Toast.makeText(this, R.string.txt_naoCadastrado, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        break;//fim do item

                    case "subitem":
                        if (edCategoria.getText().length() == 0) {
                            Toast.makeText(this, R.string.txt_DescricaoVazio, Toast.LENGTH_SHORT).show();
                        } else {
                            if (edTipo.getText().length() == 0) {
                                Toast.makeText(this, R.string.txt_TipoVazio, Toast.LENGTH_SHORT).show();
                            } else {
                                if (edItem.getText().length() == 0) {
                                    Toast.makeText(this, R.string.txt_ItemVazio, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (edSubitem.getText().length() == 0) {
                                        Toast.makeText(this, R.string.txt_SubItemVazio, Toast.LENGTH_SHORT).show();
                                    } else {
                                        SubItemDao subItemDao = FabricaDao.criarSubitemDao();

                                        //Verifica se eh um dos favoritos
                                        if (swt_Favorito.isChecked()) {
                                            subItem.setFavorito(1);
                                        } else {
                                            subItem.setFavorito(0);
                                        }

                                        //Verificar se eh um novo favorito
                                        if (lancamento.getSubitem() != null && lancamento.getSubitem().getFavorito() < subItem.getFavorito()) {
                                            favoritoAlterado = true;
                                        }
                                        if (lancamento.getSubitem() != null && lancamento.getSubitem().getFavorito() > subItem.getFavorito()) {
                                            excluirFavorito = true;
                                        }
                                        //inserir um novo subitem e registrar como favorito
                                        if (operacao.equalsIgnoreCase("insert") && swt_Favorito.isChecked()) {
                                            favoritoAlterado = true;
                                        }
                                        switch (operacao) {
                                            case "insert":
                                                subItem.setDescricao(edSubitem.getText().toString());
                                                subItem.setHabilitado(1);
                                                lancamento.setSubitem(subItem);
                                                id = subItemDao.salvar(lancamento);
                                                break;
                                            case "update":
                                                //verificar se a descrição da tela está diferente da que esta no banco
                                                //se for diferente verifica se a descriação já não existe
                                                Integer contador = subItemDao.contarDescricao(lancamento.getSubitem().getIdCategoria(),
                                                        lancamento.getSubitem().getIdTipo(),
                                                        lancamento.getSubitem().getIdItem(),
                                                        edSubitem.getText().toString().toUpperCase());
                                                if(contador > 0){
                                                    id = Long.parseLong( String.valueOf(contador*-1));
                                                    favoritoAlterado = false;
                                                    excluirFavorito = false;
                                                } else {
                                                    //verifica se está habilitado
                                                    if (swt_Habilitado.isChecked()) {
                                                        subItem.setHabilitado(1);
                                                    } else {
                                                        subItem.setHabilitado(0);
                                                    }
                                                    subItem.setDescricao(edSubitem.getText().toString());
                                                    subItem.setIdSubItem(lancamento.getSubitem().getIdSubItem());
                                                    lancamento.setSubitem(subItem);
                                                    id = subItemDao.alterar(lancamento);
                                                }
                                                break;
                                        }

                                        if (id != -1) {
                                            if (favoritoAlterado) {
                                                listaFavorito(lancamento);
                                            }
                                            if (excluirFavorito) {
                                                FavoritoDao favoritoDao = FabricaDao.criarFavoritoDao();
                                                favoritoDao.excluir("subitem", lancamento.getSubitem().getIdSubItem());
                                            }

                                            novoCadastro();
                                        } else {
                                            Toast.makeText(this, R.string.txt_naoCadastrado, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }
                        break;//fim do subitem

                    case "elemento":
                        if (edCategoria.getText().length() == 0) {
                            Toast.makeText(this, R.string.txt_DescricaoVazio, Toast.LENGTH_SHORT).show();
                        } else {
                            if (edTipo.getText().length() == 0) {
                                Toast.makeText(this, R.string.txt_TipoVazio, Toast.LENGTH_SHORT).show();
                            } else {
                                if (edItem.getText().length() == 0) {
                                    Toast.makeText(this, R.string.txt_ItemVazio, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (edSubitem.getText().length() == 0) {
                                        Toast.makeText(this, R.string.txt_SubItemVazio, Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (edElemento.getText().length() == 0) {
                                            Toast.makeText(this, R.string.txt_ElementoVazio, Toast.LENGTH_SHORT).show();
                                        } else {
                                            ElementoDao elementoDao = FabricaDao.criarElementoDao();

                                            switch (operacao) {
                                                case "insert":
                                                    elemento.setDescricao(edElemento.getText().toString());
                                                    elemento.setHabilitado(1);
                                                    lancamento.setElemento(elemento);
                                                    id = elementoDao.salvar(lancamento);
                                                    break;
                                                case "update":
                                                    Integer contador = elementoDao.contarDescricao(lancamento.getElemento().getIdCategoria(),
                                                            lancamento.getElemento().getIdTipo(),
                                                            lancamento.getElemento().getIdItem(),
                                                            lancamento.getElemento().getIdSubItem(),
                                                            edElemento.getText().toString().toUpperCase());
                                                    if(contador > 0){
                                                        id = Long.parseLong( String.valueOf(contador*-1));
                                               } else {
                                                        //verifica se está habilitado
                                                        if (swt_Habilitado.isChecked()) {
                                                            elemento.setHabilitado(1);
                                                        } else {
                                                            elemento.setHabilitado(0);
                                                        }
                                                        elemento.setDescricao(edElemento.getText().toString());
                                                        elemento.setIdElemento(lancamento.getElemento().getIdElemento());
                                                        lancamento.setElemento(elemento);
                                                        id = elementoDao.alterar(lancamento);
                                                    }
                                                    break;
                                            }

                                            if (id != -1) {
                                                novoCadastro();
                                            } else {
                                                Toast.makeText(this, R.string.txt_naoCadastrado, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;//fim do elemento
                }
                break;//fim do salvar

            case R.id.btnExcluir:
                switch (opcao) {
                    case "categoria":
                        CategoriaDao categoriaDao = FabricaDao.criarCategoriaDao();
                        id = categoriaDao.excluir(lancamento);
                        break;
                    case "tipo":
                        TipoDao tipoDao = FabricaDao.criarTipoDao();
                        id = tipoDao.excluir(lancamento);
                        break;
                    case "item":
                        ItemDao itemDao = FabricaDao.criarItemDao();
                        id = itemDao.excluir(lancamento);
                        break;
                    case "subitem":
                        SubItemDao subItemDao = FabricaDao.criarSubitemDao();
                        id = subItemDao.excluir(lancamento);
                        break;
                    case "elemento":
                        ElementoDao elementoDao = FabricaDao.criarElementoDao();
                        id = elementoDao.excluir(lancamento);
                        break;
                }
                if (id != -1) {
                    operacao = "excluir";
                    novoCadastro();
                } else {
                    Toast.makeText(this, "Não poder ser excluído!", Toast.LENGTH_SHORT).show();
                }
                break;//fim do excluir

            case R.id.btnVoltar:
                Intent it = new Intent(this, ActivityAcao.class);
                it.putExtra("opcao", opcao);
                startActivity(it);
                this.finish();
                break;
        case R.id.btnCategoria:
                Intent listcat = new Intent(this, ActivityLista.class);
                lancamento.setTipo(null);
                lancamento.setItem(null);
                lancamento.setSubitem(null);
                lancamento.setElemento(null);
                listcat.putExtra("opcao", opcao);
                listcat.putExtra("lista", "categoria");
                listcat.putExtra("lancamento", lancamento);
                startActivity(listcat);
                finish();
                break;

            case R.id.btnTipo:
                if (lancamento.getCategoria() == null) {
                    Toast.makeText(this, R.string.txt_btCategoriaVazio, Toast.LENGTH_SHORT).show();
                } else {
                    Intent listip = new Intent(this, ActivityLista.class);
                    lancamento.setItem(null);
                    lancamento.setSubitem(null);
                    lancamento.setElemento(null);
                    listip.putExtra("opcao", opcao);
                    listip.putExtra("lista", "tipo");
                    listip.putExtra("categoria", lancamento.getCategoria().getIdCategoria());
                    listip.putExtra("lancamento", lancamento);
                    startActivity(listip);
                    finish();
                }
                break;

            case R.id.btnItem:
                if (lancamento.getTipo() == null) {
                    Toast.makeText(this, R.string.txt_btTipoVazio, Toast.LENGTH_SHORT).show();
                } else {
                    Intent lisit = new Intent(this, ActivityLista.class);
                    lancamento.setSubitem(null);
                    lancamento.setElemento(null);
                    lisit.putExtra("opcao", opcao);
                    lisit.putExtra("lista", "item");
                    lisit.putExtra("categoria", lancamento.getCategoria().getIdCategoria());
                    lisit.putExtra("tipo", lancamento.getTipo().getIdTipo());//verificar necessidade
                    lisit.putExtra("lancamento", lancamento);
                    startActivity(lisit);
                    finish();
                }
                break;
            case R.id.btnSubItem:
                if (lancamento.getItem() == null) {
                    Toast.makeText(this, R.string.txt_btSubItemVazio, Toast.LENGTH_SHORT).show();
                } else {
                    Intent lisub = new Intent(this, ActivityLista.class);
                    lancamento.setElemento(null);
                    lisub.putExtra("opcao", opcao);
                    lisub.putExtra("lista", "subitem");
                    lisub.putExtra("categoria", lancamento.getCategoria().getIdCategoria());
                    lisub.putExtra("tipo", lancamento.getTipo().getIdTipo());
                    lisub.putExtra("item", lancamento.getItem().getIdItem());
                    lisub.putExtra("lancamento", lancamento);
                    startActivity(lisub);
                    finish();
                }
                break;
        }
    }

    private void listaFavorito(Lancamento lancamento) {

        Intent it = new Intent(ActivityCadastro.this, ActivityFavorito.class);
        it.putExtra("lancamento", lancamento);
        it.putExtra("operacao", "update/insert");
        it.putExtra("opcao", opcao);
        startActivity(it);
    }

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
                Intent it = null;
                limparCampos();
                switch (operacao) {
                    case "insert":
                        it = new Intent(ActivityCadastro.this, ActivityCadastro.class);
                        limparCampos();
                        break;
                    case "update":
                        it = new Intent(ActivityCadastro.this, ActivityListaCompleta.class);
                        it.putExtra("lancamento", lancamento);
                        finish();
                        break;
                    case "excluir":
                        it = new Intent(ActivityCadastro.this, ActivityListaCompleta.class);
                        it.putExtra("lancamento", lancamento);
                        finish();
                        break;
                }

                it.putExtra("opcao", opcao);
                it.putExtra("lista", opcao);
                startActivity(it);
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


    private void limparCampos() {
        edCategoria.setText("");
        edTipo.setText("");
        edItem.setText("");
        edSubitem.setText("");
        edElemento.setText("");
    }

    private void sair() {
        //Intent i = new Intent(this, ActivityMenu.class);
        //startActivity(i);
        finish();
    }

}



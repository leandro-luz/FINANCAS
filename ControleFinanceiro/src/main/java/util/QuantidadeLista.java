package util;

import java.util.ArrayList;
import java.util.List;

import model.Banco;
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
import persist.DAO.LancamentoDao;
import persist.DAO.SubItemDao;
import persist.DAO.TipoDao;

public class QuantidadeLista {
    String opcao;
    static List<Categoria> listaCategoria;
    static List<Tipo> listaTipo;
    static List<Item> listaItem;
    static List<SubItem> listaSubitem;
    static List<Elemento> listaElemento;
    static List<Banco> listaBanco;
    static List<Conta> listaConta;
    static List<Lancamento> listaLancamento;

    public static long verificarQtd(String opcao) {
        long qtd = 0;
        switch (opcao) {
            case "categoria":
                CategoriaDao daoCat = FabricaDao.criarCategoriaDao();
                listaCategoria = new ArrayList<>();
                listaCategoria = daoCat.listarTodos();
                qtd = listaCategoria.size();
                break;
            case "tipo":
                TipoDao daoTip = FabricaDao.criarTipoDao();
                listaTipo = new ArrayList<>();
                listaTipo = daoTip.listarTodos();
                qtd = listaTipo.size();
                break;
            case "item":
                ItemDao daoIt = FabricaDao.criarItemDao();
                listaItem = new ArrayList<>();
                listaItem = daoIt.listarTodos();
                qtd = listaItem.size();
                break;
            case "subitem":
                SubItemDao daoSub = FabricaDao.criarSubitemDao();
                listaSubitem = new ArrayList<>();
                listaSubitem = daoSub.listarTodos();
                qtd = listaSubitem.size();
                break;
            case "elemento":
                ElementoDao daoEle = FabricaDao.criarElementoDao();
                listaElemento = new ArrayList<>();
                listaElemento = daoEle.listarTodos();
                qtd = listaElemento.size();
                break;

            case "banco":
                BancoDao daoBanco = FabricaDao.criarBancoDao();
                listaBanco = new ArrayList<>();
                listaBanco = daoBanco.listarTodos();
                qtd = listaBanco.size();
                break;

            case "conta":
                ContaDao daoConta = FabricaDao.criarContaDao();
                listaConta = new ArrayList<>();
                listaConta = daoConta.listarTodos();
                qtd = listaConta.size();
                break;

            case "lancamento":
                LancamentoDao lancamentoDao = FabricaDao.criarLancamentoDao();
                listaLancamento = new ArrayList<>();
                listaLancamento = lancamentoDao.listarTodos();
                qtd = listaLancamento.size();
                break;
        }
        return qtd;
    }
}

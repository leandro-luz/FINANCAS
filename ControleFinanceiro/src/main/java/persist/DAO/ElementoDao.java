package persist.DAO;

import java.util.List;

import model.Elemento;
import model.Lancamento;
import model.SubItem;

public interface ElementoDao {
    long    salvar(Lancamento p);
    void   criar(Elemento p);
    Lancamento buscar (Lancamento p);
    long buscarLancamento(Lancamento p);
    long   excluir(Lancamento p);
    long   alterar(Lancamento p);
    List<Elemento> listarTodos();
    List<Elemento> listarTodosHabilitados();
    List<Elemento> listarTodosNome();
    List<Elemento> listarTodosReferencia(Integer cat, Integer tip, Integer itm, Integer subitm);
    List<String> listarTodosString();
}

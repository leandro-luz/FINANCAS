package persist.DAO;

import java.util.List;

import model.Lancamento;
import model.SubItem;

public interface SubItemDao {
    long    salvar(Lancamento p);
    void   criar(SubItem p);
    Lancamento buscar(Lancamento p);
    long buscarLancamento(Lancamento p);
    long   alterar(Lancamento p);
    long   excluir(Lancamento p);
    List<SubItem> listarTodos();
    List<SubItem> listarTodosHabilitados();
    List<SubItem> listarTodosNome();
    SubItem buscarByNome(String nome);
    List<SubItem> listarTodosReferencia(Integer cat, Integer tip, Integer itm);
    List<String> listarTodosString();
}

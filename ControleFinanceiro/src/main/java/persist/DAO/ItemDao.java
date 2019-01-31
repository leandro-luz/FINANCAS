package persist.DAO;

import java.util.List;
import model.Item;
import model.Lancamento;

public interface ItemDao {
    long    salvar(Lancamento p);
    void   criar(Item p);
    long buscar(Lancamento p);
    long   alterar(Lancamento p);
    long   excluir(Lancamento p);
    List<Item> listarTodos();
    List<Item> listarTodosHabilitados();
    List<Item> listarTodosNome();
    List<Item> listarTodosReferencia(Integer cat, Integer tip);
    List<String> listarTodosString();
    Integer contarDescricao(Integer categoria, Integer tipo, String descricao);
    long alterarStatus(Lancamento lancamento);
}

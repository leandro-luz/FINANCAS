package persist.DAO;

import java.util.List;

import model.Favorito;
import model.Lancamento;

public interface LancamentoDao {
    long    salvar(Lancamento p);
    void   criar(Lancamento p);
    long buscar(Lancamento p);
    long   excluir(Lancamento p);
    long   alterar(Lancamento p);
    List<Lancamento> listarTodos();
    List<String> listarTodosString();
    Float buscarValorById(int id);
}

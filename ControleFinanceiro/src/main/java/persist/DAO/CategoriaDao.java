package persist.DAO;

import java.util.List;

import model.Categoria;
import model.Lancamento;

public interface CategoriaDao {
    long    salvar(Lancamento p);
    void   criar(Categoria p);
    long buscar(Lancamento p);
    long   excluir(Lancamento p);
    long   alterar(Lancamento p);
    List<Categoria> listarTodos();
    List<Categoria> listarTodosHabilitados();
    List<String> listarTodosString();

}

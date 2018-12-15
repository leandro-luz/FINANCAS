package persist.DAO;

import java.util.List;

public interface GenericDao {
    long    salvar(Object p);
    void   criar(Object p);
    Object buscar(String p);
    long   alterar(Object p);
    long   excluir(Object p);
    List<Object> listarTodos();
}

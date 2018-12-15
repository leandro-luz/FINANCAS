package persist.DAO;

import java.util.List;

import model.Banco;
import model.Carteira;
import model.Categoria;
import model.Lancamento;

public interface BancoDao {
    long    salvar(Banco p);
    void   criar(Banco p);
    long buscar(Carteira p);
    long   excluir(Carteira p);
    long   alterar(Carteira p);
    List<Banco> listarTodos();
    List<Banco> listarTodosHabilitados();
    List<String> listarTodosString();
}

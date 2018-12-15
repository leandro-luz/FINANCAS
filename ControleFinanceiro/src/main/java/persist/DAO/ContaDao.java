package persist.DAO;

import java.util.List;

import model.Banco;
import model.Carteira;
import model.Conta;

public interface ContaDao {
    long    salvar(Conta p);
    void   criar(Conta p);
    long buscar(Carteira p);
    long   excluir(Carteira p);
    long   alterar(Carteira p);
    List<Conta> listarTodos();
    List<Conta> listarTodosHabilitados();
    List<String> listarTodosString();
    Conta buscarByNome(String nome);
}

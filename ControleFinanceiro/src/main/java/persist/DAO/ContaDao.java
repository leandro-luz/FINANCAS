package persist.DAO;

import java.util.List;

import model.Banco;
import model.Carteira;
import model.Conta;
import model.Lancamento;

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
    void alterarSaldoConta(Lancamento lancamento, int opcao);
    Conta buscarById(Integer id);
    Integer contarDescricao(Integer conta, String descricao);
}

package persist.DAO;

import java.util.List;

import model.Lancamento;
import model.Tipo;

public interface TipoDao  {
    long    salvar(Lancamento p);
    void   criar(Tipo p);
    long buscar(Lancamento p);
    long   alterar(Lancamento p);
    long   excluir(Lancamento p);
    List<Tipo> listarTodos();
    List<Tipo> listarTodosHabilitados();
    List<Tipo> listarTodosNome();
    List<Tipo> listarTodosReferencia(Integer i);
    List<String> listarTodosString();
    Integer contarDescricao(Integer categoria, String descricao);
    long alterarStatus(Lancamento lancamento);
}

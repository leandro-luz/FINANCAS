package persist.DAO;

import java.util.List;

import model.Elemento;
import model.Favorito;
import model.Lancamento;

public interface FavoritoDao {
    long    salvar(Favorito p);
    long buscarQuantidade(String s, long i);
    Favorito buscar(String s, long i);
    Favorito buscarById(long i);
    void   excluir(String s, long i);
    long   alterar(Favorito p);
    List<Favorito> listarTodos();
    List<Favorito> listarTodosbyOpcao(String s);
    List<String> listarNomes();
    List<String> listarNomesbyOpcao(String s);
}

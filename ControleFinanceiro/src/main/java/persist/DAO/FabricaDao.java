package persist.DAO;

import model.Categoria;
import model.Lancamento;
import persist.SqliteDao.BancoDaoSqlite;
import persist.SqliteDao.CategoriaDaoSqlite;
import persist.SqliteDao.ContaDaoSqlite;
import persist.SqliteDao.ElementoDaoSqlite;
import persist.SqliteDao.FavoritoDaoSqlite;
import persist.SqliteDao.ItemDaoSqlite;
import persist.SqliteDao.LancamentoDaoSqlite;
import persist.SqliteDao.SubItemDaoSqlite;
import persist.SqliteDao.TipoDaoSqlite;

public class FabricaDao {

    public static CategoriaDao criarCategoriaDao(){
        return new CategoriaDaoSqlite();
    }
    public static TipoDao criarTipoDao(){
        return new TipoDaoSqlite();
    }
    public static ItemDao criarItemDao(){
        return new ItemDaoSqlite();
    }
    public static SubItemDao criarSubitemDao(){
        return new SubItemDaoSqlite();
    }
    public static ElementoDao criarElementoDao(){return new ElementoDaoSqlite();}
    public static FavoritoDao criarFavoritoDao(){return new FavoritoDaoSqlite();}
    public static LancamentoDao criarLancamentoDao(){return new LancamentoDaoSqlite();}
    public static BancoDao criarBancoDao(){return new BancoDaoSqlite();}
    public static ContaDao criarContaDao(){return new ContaDaoSqlite();}

}

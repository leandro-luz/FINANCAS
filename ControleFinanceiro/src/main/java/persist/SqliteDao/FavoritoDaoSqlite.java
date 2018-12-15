package persist.SqliteDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Elemento;
import model.Favorito;
import model.Lancamento;
import persist.DAO.CategoriaDao;
import persist.DAO.FavoritoDao;

public class FavoritoDaoSqlite extends GenericDaoSqlite implements FavoritoDao {
    @Override
    public long salvar(Favorito favorito) {
        SQLiteDatabase db = getWritebleDB();
        long id;
        db.execSQL("PRAGMA foreign_keys = ON");
        ContentValues values = new ContentValues();
        values.put("descricao", favorito.getDescricao().toUpperCase());
        values.put("opcao", favorito.getOpcao());
        values.put("idOpcao", favorito.getIdOpcao());
        values.put("idCategoria", favorito.getIdCategoria());
        values.put("idTipo", favorito.getIdTipo());
        values.put("idItem", favorito.getIdItem());
        values.put("idSubItem", favorito.getIdSubItem());
        values.put("idBanco", favorito.getIdBanco());
        values.put("idConta", favorito.getIdConta());
        id = db.insert("favorito", null, values);
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;
    }

    public long buscarQuantidade(String tipo, long id) {
        long quantidade = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(*) from favorito where opcao = '" + tipo + "' and idOpcao = " + id, null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    quantidade = resultSet.getInt(0);
                } while (resultSet.moveToNext());
            }
        }
        return quantidade;
    }

    @Override
    public Favorito buscar(String opcao, long id) {
        Favorito favorito = null;
            SQLiteDatabase db = getReadableDB();
            Cursor resultSet = db.rawQuery("select idFavorito, opcao, idOpcao, favorito.descricao, favorito.idCategoria, categoria.descricao, favorito.idTipo, tipo.descricao, " +
                    "favorito.idItem, item.descricao, favorito.idSubItem, subitem.descricao, favorito.idBanco, banco.descricao, favorito.idConta, conta.descricao " +
                    "from favorito " +
                    "left join  categoria on (favorito.idCategoria = categoria.idCategoria) " +
                    "left join  tipo on (favorito.idTipo = tipo.idTipo) " +
                    "left join  item on (favorito.idItem = item.idItem) " +
                    "left join  subitem on (favorito.idSubItem = subitem.idSubItem) " +
                    "left join  banco on (favorito.idBanco = banco.idBanco) " +
                    "left join  conta on (favorito.idConta = conta.idConta) " +
                    "where opcao = '" + opcao +"' and idOpcao = " + id, null);
            if (resultSet != null) {
                if (resultSet.moveToFirst()) {
                    do {
                        favorito = new Favorito();
                        favorito.setIdFavorito(resultSet.getInt(0));
                        favorito.setOpcao(resultSet.getString(1));
                        favorito.setIdOpcao(resultSet.getInt(2));
                        favorito.setDescricao(resultSet.getString(3));
                        favorito.setIdCategoria(resultSet.getInt(4));
                        favorito.setCategoriaNome(resultSet.getString(5));
                        favorito.setIdTipo(resultSet.getInt(6));
                        favorito.setTipoNome(resultSet.getString(7));
                        favorito.setIdItem(resultSet.getInt(8));
                        favorito.setItemNome(resultSet.getString(9));
                        favorito.setIdSubItem(resultSet.getInt(10));
                        favorito.setSubItemNome(resultSet.getString(11));
                        favorito.setIdBanco(resultSet.getInt(12));
                        favorito.setBancoNome(resultSet.getString(13));
                        favorito.setIdConta(resultSet.getInt(14));
                        favorito.setContaNome(resultSet.getString(15));
                    } while (resultSet.moveToNext());
                }
            }
        //}

        return favorito;
    }

    @Override
    public Favorito buscarById(long id) {
        Favorito favorito = null;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select idFavorito, opcao, idOpcao, favorito.descricao, favorito.idCategoria, categoria.descricao, favorito.idTipo, tipo.descricao, " +
                "favorito.idItem, item.descricao, favorito.idSubItem, subitem.descricao, favorito.idBanco, banco.descricao, favorito.idConta, conta.descricao " +
                "from favorito " +
                "left join  categoria on (favorito.idCategoria = categoria.idCategoria) " +
                "left join  tipo on (favorito.idTipo = tipo.idTipo) " +
                "left join  item on (favorito.idItem = item.idItem) " +
                "left join  subitem on (favorito.idSubItem = subitem.idSubItem) " +
                "left join  banco on (favorito.idBanco = banco.idBanco) " +
                "left join  conta on (favorito.idConta = conta.idConta) " +
                "where idFavorito = " + id, null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    favorito = new Favorito();
                    favorito.setIdFavorito(resultSet.getInt(0));
                    favorito.setOpcao(resultSet.getString(1));
                    favorito.setIdOpcao(resultSet.getInt(2));
                    favorito.setDescricao(resultSet.getString(3));
                    favorito.setIdCategoria(resultSet.getInt(4));
                    favorito.setCategoriaNome(resultSet.getString(5));
                    favorito.setIdTipo(resultSet.getInt(6));
                    favorito.setTipoNome(resultSet.getString(7));
                    favorito.setIdItem(resultSet.getInt(8));
                    favorito.setItemNome(resultSet.getString(9));
                    favorito.setIdSubItem(resultSet.getInt(10));
                    favorito.setSubItemNome(resultSet.getString(11));
                    favorito.setIdBanco(resultSet.getInt(12));
                    favorito.setBancoNome(resultSet.getString(13));
                    favorito.setIdConta(resultSet.getInt(14));
                    favorito.setContaNome(resultSet.getString(15));
                } while (resultSet.moveToNext());
            }
        }
        return favorito;
    }

    @Override
    public void excluir(String tipo, long id) {
        SQLiteDatabase db = getWritebleDB();
        db.execSQL("PRAGMA foreign_keys = ON");
        String where = "opcao = '" + tipo + "' and idOpcao = " + id;
        db.delete("favorito", where, null);
        db.execSQL("PRAGMA foreign_keys = OFF");
    }

    @Override
    public long alterar(Favorito favorito) {
        SQLiteDatabase db = getWritebleDB();
        ContentValues values = new ContentValues();
        values.put("descricao", favorito.getDescricao().toUpperCase());
        String where = "idFavorito = ?";
        String argumentos[] = {String.valueOf(favorito.getIdFavorito())};
        long id = db.update("favorito", values, where, argumentos);
        return id;
    }

    @Override
    public List<Favorito> listarTodos() {
        List<Favorito> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select idFavorito, opcao, descricao from favorito", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Favorito favorito = new Favorito();
                    favorito.setIdFavorito(resultSet.getInt(0));
                    favorito.setOpcao(resultSet.getString(1));
                    favorito.setDescricao(resultSet.getString(2));
                    myArray.add(favorito);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<Favorito> listarTodosbyOpcao(String opcao) {
        List<Favorito> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select idFavorito, opcao, idOpcao, favorito.descricao, favorito.idCategoria, categoria.descricao, favorito.idTipo, tipo.descricao, " +
                "favorito.idItem, item.descricao, favorito.idSubItem, subitem.descricao, favorito.idBanco, banco.descricao, favorito.idConta, conta.descricao " +
                "from favorito " +
                "left join  categoria on (favorito.idCategoria = categoria.idCategoria) " +
                "left join  tipo on (favorito.idTipo = tipo.idTipo) " +
                "left join  item on (favorito.idItem = item.idItem) " +
                "left join  subitem on (favorito.idSubItem = subitem.idSubItem) " +
                "left join  banco on (favorito.idBanco = banco.idBanco) " +
                "left join  conta on (favorito.idConta = conta.idConta) " +
                "where opcao = '" + opcao + "'", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Favorito favorito = new Favorito();
                    favorito.setIdFavorito(resultSet.getInt(0));
                    favorito.setOpcao(resultSet.getString(1));
                    favorito.setIdOpcao(resultSet.getInt(2));
                    favorito.setDescricao(resultSet.getString(3));
                    favorito.setIdCategoria(resultSet.getInt(4));
                    favorito.setCategoriaNome(resultSet.getString(5));
                    favorito.setIdTipo(resultSet.getInt(6));
                    favorito.setTipoNome(resultSet.getString(7));
                    favorito.setIdItem(resultSet.getInt(8));
                    favorito.setItemNome(resultSet.getString(9));
                    favorito.setIdSubItem(resultSet.getInt(10));
                    favorito.setSubItemNome(resultSet.getString(11));
                    favorito.setIdBanco(resultSet.getInt(12));
                    favorito.setBancoNome(resultSet.getString(13));
                    favorito.setIdConta(resultSet.getInt(14));
                    favorito.setContaNome(resultSet.getString(15));
                    myArray.add(favorito);

                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<String> listarNomes() {
        List<String> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery(
                "Select * From favorito", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    myArray.add(resultSet.getString(1));
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    public List<String> listarNomesbyOpcao(String opcao) {
        List<String> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery(
                "Select * From favorito where opcao = '" + opcao + "'", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    myArray.add(resultSet.getString(1));
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }
}

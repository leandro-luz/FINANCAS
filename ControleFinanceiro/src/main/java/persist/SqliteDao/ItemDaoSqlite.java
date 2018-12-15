package persist.SqliteDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Item;
import model.Lancamento;
import persist.DAO.ElementoDao;
import persist.DAO.FabricaDao;
import persist.DAO.ItemDao;
import persist.DAO.SubItemDao;

public class ItemDaoSqlite extends GenericDaoSqlite implements ItemDao {

    public long salvar(Lancamento c) {
        SQLiteDatabase db = getWritebleDB();
        db.execSQL("PRAGMA foreign_keys = OFF");
        long id;
        List<String> myArray = new ArrayList<>();
        myArray = listarTodosString();

        if (myArray.contains(c.getItem().getDescricao().toUpperCase())) {
            id = -1;
        } else {
            ContentValues values = new ContentValues();
            values.put("idCategoria", c.getCategoria().getIdCategoria());
            values.put("idTipo", c.getTipo().getIdTipo());
            values.put("descricao", c.getItem().getDescricao().toUpperCase());
            values.put("habilitado", c.getItem().getHabilitado());
            id = db.insert("item", null, values);
        }
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;
    }

    public void criar(Item c) {

    }

    public long buscar(Lancamento lancamento) {
        long quantidade = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(idItem) " +
                "FROM lancamento where idItem = " + lancamento.getItem().getIdItem(), null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    quantidade = resultSet.getInt(0);
                } while (resultSet.moveToNext());
            }
        }
        return quantidade;
    }

    public long alterar(Lancamento lancamento) {
        long id = 0;
        SQLiteDatabase db = getWritebleDB();
        ContentValues values = new ContentValues();
        values.put("descricao", lancamento.getItem().getDescricao().toUpperCase());
        values.put("habilitado", lancamento.getItem().getHabilitado());
        String where = "idItem = ?";
        String argumentos[] = {String.valueOf(lancamento.getItem().getIdItem())};
        id = db.update("item", values, where, argumentos);

        return id;
    }

    public long excluir(Lancamento lancamento) {
        long quantidade, id;
        quantidade = buscar(lancamento);
        //verificar se não existe nos lançamentos
        if (quantidade == 0) {
            SQLiteDatabase db = getWritebleDB();
            db.execSQL("PRAGMA foreign_keys = ON");
            String where = "idItem = " + lancamento.getItem().getIdItem();
            id = db.delete("item", where, null);
            db.execSQL("PRAGMA foreign_keys = OFF");
        } else {
            id = -1;
        }
        return id;
    }

    public List<Item> listarTodos() {
        List<Item> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From item", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setIdItem(resultSet.getInt(0));
                    item.setIdCategoria(resultSet.getInt(1));
                    item.setIdTipo(resultSet.getInt(2));
                    item.setDescricao(resultSet.getString(3));
                    item.setHabilitado(resultSet.getInt(4));
                    myArray.add(item);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    public List<Item> listarTodosHabilitados() {
        List<Item> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From item where habilitado = 1", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setIdItem(resultSet.getInt(0));
                    item.setIdCategoria(resultSet.getInt(1));
                    item.setIdTipo(resultSet.getInt(2));
                    item.setDescricao(resultSet.getString(3));
                    item.setHabilitado(resultSet.getInt(4));
                    myArray.add(item);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    public List<Item> listarTodosReferencia(Integer categoria, Integer tipo) {
        List<Item> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From item where idCategoria=" + categoria + " and idTipo=" + tipo, null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setIdItem(resultSet.getInt(0));
                    item.setIdCategoria(resultSet.getInt(1));
                    item.setIdTipo(resultSet.getInt(2));
                    item.setDescricao(resultSet.getString(3));
                    item.setHabilitado(resultSet.getInt(4));
                    myArray.add(item);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    public List<Item> listarTodosNome() {
        List<Item> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select item.idItem, item.habilitado, item.descricao, categoria.descricao, tipo.descricao\n" +
                "from item inner join categoria On item.idCategoria = categoria.idCategoria \n" +
                "join tipo On item.idTipo = tipo.idTipo", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setIdItem(resultSet.getInt(0));
                    item.setHabilitado(resultSet.getInt(1));
                    item.setDescricao(resultSet.getString(2));
                    item.setCategoriaNome(resultSet.getString(3));
                    item.setTipoNome(resultSet.getString(4));
                    myArray.add(item);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<String> listarTodosString() {
        List<String> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From item ", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    myArray.add(resultSet.getString(3));
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }
}

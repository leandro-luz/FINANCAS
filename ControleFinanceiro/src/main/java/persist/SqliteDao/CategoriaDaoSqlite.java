package persist.SqliteDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Lancamento;
import persist.DAO.CategoriaDao;
import persist.DAO.FabricaDao;
import persist.DAO.ItemDao;
import persist.DAO.TipoDao;

public class CategoriaDaoSqlite extends GenericDaoSqlite implements CategoriaDao {

    public long salvar(Lancamento lancamento) {
        SQLiteDatabase db = getWritebleDB();
        db.execSQL("PRAGMA foreign_keys = ON");
        long id;
        List<String> myArray = new ArrayList<>();
        myArray = this.listarTodosString();

        if (myArray.contains(lancamento.getCategoria().getDescricao().toUpperCase())) {
            id = -1;
        } else {
            ContentValues values = new ContentValues();
            values.put("descricao", lancamento.getCategoria().getDescricao().toUpperCase());
            values.put("habilitado", lancamento.getCategoria().getHabilitado());
            values.put("incremento", lancamento.getCategoria().getIncremento());
            id = db.insert("categoria", null, values);
        }
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;

    }

    public void criar(Categoria c) {
    }

    public long buscar(Lancamento lancamento) {
        long quantidade = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(idCategoria) " +
                "FROM lancamento where idCategoria = " + lancamento.getCategoria().getIdCategoria(), null);
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
        values.put("descricao", lancamento.getCategoria().getDescricao().toUpperCase());
        values.put("habilitado", lancamento.getCategoria().getHabilitado());
        values.put("incremento", lancamento.getCategoria().getIncremento());
        String where = "idCategoria = ?";
        String argumentos[] = {String.valueOf(lancamento.getCategoria().getIdCategoria())};
        id = db.update("categoria", values, where, argumentos);

        return id;
    }

    public long excluir(Lancamento lancamento) {

        long quantidade, id;
        quantidade = buscar(lancamento);
        //verificar se não existe nos lançamentos
        if (quantidade == 0) {
            SQLiteDatabase db = getWritebleDB();
            db.execSQL("PRAGMA foreign_keys = ON");
            String where = "idCategoria = " + lancamento.getCategoria().getIdCategoria();
            id = db.delete("categoria", where, null);
            db.execSQL("PRAGMA foreign_keys = OFF");
        } else {
            id = -1;
        }

        return id;
    }

    @Override
    public List<Categoria> listarTodos() {
        List<Categoria> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From categoria", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(resultSet.getInt(0));
                    categoria.setDescricao(resultSet.getString(1));
                    categoria.setHabilitado(resultSet.getInt(2));
                    categoria.setIncremento(resultSet.getInt(3));
                    myArray.add(categoria);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<Categoria> listarTodosHabilitados() {
        List<Categoria> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From categoria where habilitado = 1", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(resultSet.getInt(0));
                    categoria.setDescricao(resultSet.getString(1));
                    categoria.setHabilitado(resultSet.getInt(2));
                    categoria.setIncremento(resultSet.getInt(3));
                    myArray.add(categoria);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<String> listarTodosString() {
        List<String> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From categoria", null);
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

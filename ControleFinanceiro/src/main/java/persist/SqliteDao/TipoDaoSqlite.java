package persist.SqliteDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Lancamento;
import model.Tipo;
import persist.DAO.FabricaDao;
import persist.DAO.ItemDao;
import persist.DAO.SubItemDao;
import persist.DAO.TipoDao;

public class TipoDaoSqlite extends GenericDaoSqlite implements TipoDao {


    public long salvar(Lancamento c) {
        SQLiteDatabase db = getWritebleDB();
        db.execSQL("PRAGMA foreign_keys = ON");
        long id;
        Integer contador = contarDescricao(c.getCategoria().getIdCategoria(), c.getTipo().getDescricao().toUpperCase());
        if (contador > 0) {
            id = -1;
        } else {
            ContentValues values = new ContentValues();
            values.put("idCategoria", c.getCategoria().getIdCategoria());
            values.put("descricao", c.getTipo().getDescricao().toUpperCase());
            values.put("habilitado", c.getTipo().getHabilitado());
            id = db.insert("tipo", null, values);
        }
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;
    }

    public void criar(Tipo c) {

    }

    public long buscar(Lancamento lancamento) {
        long quantidade = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(idTipo) " +
                "FROM lancamento where idTipo = " + lancamento.getTipo().getIdTipo(), null);
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
        values.put("descricao", lancamento.getTipo().getDescricao().toUpperCase());
        values.put("habilitado", lancamento.getTipo().getHabilitado());
        String where = "idTipo = ?";
        String argumentos[] = {String.valueOf(lancamento.getTipo().getIdTipo())};
        id = db.update("tipo", values, where, argumentos);

        return id;
    }

    public long excluir(Lancamento lancamento) {
        long quantidade, id;
        quantidade = buscar(lancamento);
        //verificar se não existe nos lançamentos
        if (quantidade == 0) {
            SQLiteDatabase db = getWritebleDB();
            db.execSQL("PRAGMA foreign_keys = ON");
            String where = "idTipo = " + lancamento.getTipo().getIdTipo();
            id = db.delete("tipo", where, null);
            db.execSQL("PRAGMA foreign_keys = OFF");
        } else {
            id = -1;
        }
        return id;
    }

    @Override
    public List<Tipo> listarTodos() {
        List<Tipo> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From tipo", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Tipo tipo = new Tipo();
                    tipo.setIdTipo(resultSet.getInt(0));
                    tipo.setIdcategoria(resultSet.getInt(1));
                    tipo.setDescricao(resultSet.getString(2));
                    tipo.setHabilitado(resultSet.getInt(3));
                    myArray.add(tipo);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<Tipo> listarTodosHabilitados() {
        List<Tipo> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From tipo where habilitado = 1", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Tipo tipo = new Tipo();
                    tipo.setIdTipo(resultSet.getInt(0));
                    tipo.setIdcategoria(resultSet.getInt(1));
                    tipo.setDescricao(resultSet.getString(2));
                    tipo.setHabilitado(resultSet.getInt(3));
                    myArray.add(tipo);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<Tipo> listarTodosNome() {
        List<Tipo> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select tipo.idTipo, tipo.habilitado, tipo.descricao, categoria.idCategoria, categoria.descricao " +
                "from tipo inner join categoria On tipo.idCategoria = categoria.idCategoria", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Tipo tipo = new Tipo();
                    tipo.setIdTipo(resultSet.getInt(0));
                    tipo.setHabilitado(resultSet.getInt(1));
                    tipo.setDescricao(resultSet.getString(2));
                    tipo.setIdcategoria(resultSet.getInt(3));
                    tipo.setCategoriaNome(resultSet.getString(4));
                    myArray.add(tipo);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    public List<Tipo> listarTodosReferencia(Integer categoria) {
        List<Tipo> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From tipo where idCategoria =" + categoria, null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Tipo tipo = new Tipo();
                    tipo.setIdTipo(resultSet.getInt(0));
                    tipo.setIdcategoria(resultSet.getInt(1));
                    tipo.setDescricao(resultSet.getString(2));
                    tipo.setHabilitado(resultSet.getInt(3));
                    myArray.add(tipo);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<String> listarTodosString() {
        List<String> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From tipo", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    myArray.add(resultSet.getString(2));
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }


    @Override
    public Integer contarDescricao(Integer categoria, String descricao) {
        Integer contador = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(*) from tipo where tipo.idCategoria = " + categoria + " and tipo.descricao = '" + descricao + "'", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                   contador = resultSet.getInt(0);
               } while (resultSet.moveToNext());
            }
        }
        return contador;
    }
}

package persist.SqliteDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Elemento;
import model.Lancamento;
import model.SubItem;
import persist.DAO.ElementoDao;
import persist.DAO.SubItemDao;

public class ElementoDaoSqlite extends GenericDaoSqlite implements ElementoDao {
    public long salvar(Lancamento c) {
        SQLiteDatabase db = getWritebleDB();
        db.execSQL("PRAGMA foreign_keys = ON");
        long id;
        Integer contador = contarDescricao(c.getCategoria().getIdCategoria(),
                c.getTipo().getIdTipo(), c.getItem().getIdItem(),
                c.getSubitem().getIdSubItem(), c.getElemento().getDescricao().toUpperCase());
        if (contador > 0) {
            id = -1;
        } else {
            ContentValues values = new ContentValues();
            values.put("idCategoria", c.getCategoria().getIdCategoria());
            values.put("idTipo", c.getTipo().getIdTipo());
            values.put("idItem", c.getItem().getIdItem());
            values.put("idSubItem", c.getSubitem().getIdSubItem());
            values.put("descricao", c.getElemento().getDescricao().toUpperCase());
            values.put("habilitado", c.getSubitem().getHabilitado());
            id = db.insert("elemento", null, values);
        }
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;
    }

    public void criar(Elemento c) {

    }

    public Lancamento buscar(Lancamento lancamento) {
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select elemento.idElemento, categoria.descricao, tipo.descricao, " +
                "item.descricao, subitem.descricao, elemento.descricao, " +
                "categoria.idCategoria, tipo.idTipo, item.idItem, subitem.idSubItem, elemento.idElemento " +
                "from elemento " +
                "inner join categoria on elemento.idCategoria = categoria.idCategoria " +
                "inner join tipo on elemento.idTipo = tipo.idTipo " +
                "inner join item on elemento.idItem = item.idItem " +
                "inner join subitem on elemento.idSubItem = subitem.idSubItem " +
                "where elemento.idElemento = " + lancamento.getElemento().getIdElemento(), null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Elemento elemento = new Elemento();
                    elemento.setIdElemento(resultSet.getInt(0));
                    elemento.setCategoriaNome(resultSet.getString(1));
                    elemento.setTipoNome(resultSet.getString(2));
                    elemento.setItemNome(resultSet.getString(3));
                    elemento.setSubitemNome(resultSet.getString(4));
                    elemento.setDescricao(resultSet.getString(5));

                    elemento.setIdCategoria(resultSet.getInt(6));
                    elemento.setIdTipo(resultSet.getInt(7));
                    elemento.setIdItem(resultSet.getInt(8));
                    elemento.setIdSubItem(resultSet.getInt(9));
                    elemento.setIdSubItem(resultSet.getInt(10));

                    lancamento.setElemento(elemento);
                } while (resultSet.moveToNext());
            }
        }
        return lancamento;
    }


    public long buscarLancamento(Lancamento lancamento) {
        long quantidade = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(idElemento) " +
                "FROM lancamento where idElemento = " + lancamento.getElemento().getIdElemento(), null);
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
        values.put("descricao", lancamento.getElemento().getDescricao().toUpperCase());
        values.put("habilitado", lancamento.getElemento().getHabilitado());
        String where = "idElemento = ?";
        String argumentos[] = {String.valueOf(lancamento.getElemento().getIdElemento())};
        id = db.update("elemento", values, where, argumentos);

        return id;
    }

    public long excluir(Lancamento lancamento) {
        long quantidade, id;
        quantidade = buscarLancamento(lancamento);
        //verificar se não existe nos lançamentos
        if (quantidade == 0) {
            SQLiteDatabase db = getWritebleDB();
            db.execSQL("PRAGMA foreign_keys = ON");
            String where = "idElemento = " + lancamento.getElemento().getIdElemento();
            id = db.delete("elemento", where, null);
            db.execSQL("PRAGMA foreign_keys = OFF");
        } else {
            id = -1;
        }
        return id;
    }

    public List<Elemento> listarTodos() {
        List<Elemento> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From elemento", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Elemento elemento = new Elemento();
                    elemento.setIdElemento(resultSet.getInt(0));
                    elemento.setIdCategoria(resultSet.getInt(1));
                    elemento.setIdTipo(resultSet.getInt(2));
                    elemento.setIdItem(resultSet.getInt(3));
                    elemento.setIdSubItem(resultSet.getInt(4));
                    elemento.setDescricao(resultSet.getString(5));
                    elemento.setHabilitado(resultSet.getInt(6));
                    myArray.add(elemento);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    public List<Elemento> listarTodosHabilitados() {
        List<Elemento> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From elemento where habilitado = 1", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Elemento elemento = new Elemento();
                    elemento.setIdElemento(resultSet.getInt(0));
                    elemento.setIdCategoria(resultSet.getInt(1));
                    elemento.setIdTipo(resultSet.getInt(2));
                    elemento.setIdItem(resultSet.getInt(3));
                    elemento.setIdSubItem(resultSet.getInt(4));
                    elemento.setDescricao(resultSet.getString(5));
                    elemento.setHabilitado(resultSet.getInt(6));
                    myArray.add(elemento);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<Elemento> listarTodosNome() {
        List<Elemento> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select elemento.idElemento, elemento.habilitado, elemento.descricao, " +
                "categoria.descricao, tipo.descricao, item.descricao, subitem.descricao, categoria.idCategoria, " +
                "tipo.idTipo, item.idItem, subitem.idSubItem from elemento inner " +
                "join categoria On elemento.idCategoria = categoria.idCategoria " +
                "join tipo On elemento.idTipo = tipo.idTipo " +
                "join item On elemento.idItem = item.idItem " +
                "join subitem On elemento.idSubItem = subitem.idSubItem", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Elemento elemento = new Elemento();
                    elemento.setIdElemento(resultSet.getInt(0));
                    elemento.setHabilitado(resultSet.getInt(1));
                    elemento.setDescricao(resultSet.getString(2));
                    elemento.setCategoriaNome(resultSet.getString(3));
                    elemento.setTipoNome(resultSet.getString(4));
                    elemento.setItemNome(resultSet.getString(5));
                    elemento.setSubitemNome(resultSet.getString(6));
                    elemento.setIdCategoria(resultSet.getInt(7));
                    elemento.setIdTipo(resultSet.getInt(8));
                    elemento.setIdItem(resultSet.getInt(9));
                    elemento.setIdSubItem(resultSet.getInt(10));

                    myArray.add(elemento);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    public List<Elemento> listarTodosReferencia(Integer categoria, Integer tipo, Integer item, Integer subitem) {
        List<Elemento> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery(
                "Select * From elemento " +
                        "where idCategoria= " + categoria +
                        " and idTipo= " + tipo +
                        " and idItem = " + item +
                        " and idSubItem = " + subitem +
                        " and habilitado = 1", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Elemento elemento = new Elemento();
                    elemento.setIdElemento(resultSet.getInt(0));
                    elemento.setIdCategoria(resultSet.getInt(1));
                    elemento.setIdTipo(resultSet.getInt(2));
                    elemento.setIdItem(resultSet.getInt(3));
                    elemento.setIdSubItem(resultSet.getInt(4));
                    elemento.setDescricao(resultSet.getString(5));
                    elemento.setHabilitado(resultSet.getInt(6));
                    myArray.add(elemento);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<String> listarTodosString() {
        List<String> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery(
                "Select * From elemento ", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    myArray.add(resultSet.getString(4));
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public Integer contarDescricao(Integer categoria, Integer tipo, Integer item, Integer subitem, String descricao) {
        Integer contador = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(*) from elemento " +
                "where elemento.idCategoria = " + categoria +
                " and elemento.idTipo = " + tipo +
                " and elemento.idItem = " + item +
                " and elemento.idSubItem = "+ subitem +
                " and elemento.descricao = '" + descricao + "'", null);
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

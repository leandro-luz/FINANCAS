package persist.SqliteDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Lancamento;
import model.SubItem;
import persist.DAO.ElementoDao;
import persist.DAO.FabricaDao;
import persist.DAO.SubItemDao;
import persist.DAO.TipoDao;

public class SubItemDaoSqlite extends GenericDaoSqlite implements SubItemDao {
    public long salvar(Lancamento c) {
        SQLiteDatabase db = getWritebleDB();
        db.execSQL("PRAGMA foreign_keys = ON");
        long id;

        Integer contador = contarDescricao(c.getCategoria().getIdCategoria(),
                c.getTipo().getIdTipo(), c.getItem().getIdItem(),
                c.getSubitem().getDescricao().toUpperCase());

        if (contador > 0) {
            id = -1;
        } else {
            ContentValues values = new ContentValues();
            values.put("idCategoria", c.getCategoria().getIdCategoria());
            values.put("idTipo", c.getTipo().getIdTipo());
            values.put("idItem", c.getItem().getIdItem());
            values.put("descricao", c.getSubitem().getDescricao().toUpperCase());
            values.put("habilitado", c.getSubitem().getHabilitado());
            values.put("favorito", c.getSubitem().getFavorito());
            id = db.insert("subitem", null, values);
        }
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;
    }

    public void criar(SubItem c) {

    }

    public Lancamento buscar(Lancamento lancamento) {
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select subitem.idSubItem, categoria.descricao, tipo.descricao, item.descricao, subitem.descricao, " +
                "categoria.idCategoria, tipo.idTipo, item.idItem " +
                "from subitem " +
                "inner join categoria on subitem.idCategoria = categoria.idCategoria " +
                "inner join tipo on subitem.idTipo = tipo.idTipo " +
                "inner join item on subitem.idItem = item.idItem " +
                "where subitem.idSubItem = " + lancamento.getSubitem().getIdSubItem(), null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    SubItem subitem = new SubItem();
                    subitem.setIdSubItem(resultSet.getInt(0));
                    subitem.setCategoriaNome(resultSet.getString(1));
                    subitem.setTipoNome(resultSet.getString(2));
                    subitem.setItemNome(resultSet.getString(3));
                    subitem.setDescricao(resultSet.getString(4));

                    subitem.setIdCategoria(resultSet.getInt(5));
                    subitem.setIdTipo(resultSet.getInt(6));
                    subitem.setIdItem(resultSet.getInt(7));

                    lancamento.setSubitem(subitem);
                } while (resultSet.moveToNext());
            }
        }
        return lancamento;
    }

    public long buscarLancamento(Lancamento lancamento) {
        long quantidade = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(idSubItem) " +
                "FROM lancamento where idSubItem = " + lancamento.getSubitem().getIdSubItem(), null);
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
        values.put("descricao", lancamento.getSubitem().getDescricao().toUpperCase());
        String where = "idSubItem = ?";
        String argumentos[] = {String.valueOf(lancamento.getSubitem().getIdSubItem())};
        id = db.update("subitem", values, where, argumentos);

        return id;
    }

    public long excluir(Lancamento lancamento) {
        long quantidade, id;
        quantidade = buscarLancamento(lancamento);
        //verificar se não existe nos lançamentos
        if (quantidade == 0) {
            SQLiteDatabase db = getWritebleDB();
            db.execSQL("PRAGMA foreign_keys = ON");
            String where = "idSubItem = " + lancamento.getSubitem().getIdSubItem();
            id = db.delete("subitem", where, null);
            db.execSQL("PRAGMA foreign_keys = OFF");
        } else {
            id = -1;
        }
        return id;
    }

    public List<SubItem> listarTodos() {
        List<SubItem> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From subitem", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    SubItem subitem = new SubItem();
                    subitem.setIdSubItem(resultSet.getInt(0));
                    subitem.setIdCategoria(resultSet.getInt(1));
                    subitem.setIdTipo(resultSet.getInt(2));
                    subitem.setIdItem(resultSet.getInt(3));
                    subitem.setDescricao(resultSet.getString(4));
                    subitem.setHabilitado(resultSet.getInt(5));
                    subitem.setFavorito(resultSet.getInt(6));
                    myArray.add(subitem);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    public List<SubItem> listarTodosHabilitados() {
        List<SubItem> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From subitem where habilitado = 1", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    SubItem subitem = new SubItem();
                    subitem.setIdSubItem(resultSet.getInt(0));
                    subitem.setIdCategoria(resultSet.getInt(1));
                    subitem.setIdTipo(resultSet.getInt(2));
                    subitem.setIdItem(resultSet.getInt(3));
                    subitem.setDescricao(resultSet.getString(4));
                    subitem.setHabilitado(resultSet.getInt(5));
                    subitem.setFavorito(resultSet.getInt(6));
                    myArray.add(subitem);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<SubItem> listarTodosNome() {
        List<SubItem> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select subitem.idSubItem, subitem.habilitado, subitem.favorito, " +
                "subitem.descricao, categoria.descricao, tipo.descricao, item.descricao, categoria.idCategoria," +
                "tipo.idTipo, item.idItem from subitem inner " +
                "join categoria On subitem.idCategoria = categoria.idCategoria " +
                "join tipo On subitem.idTipo = tipo.idTipo " +
                "join item On subitem.idItem = item.idItem", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    SubItem subitem = new SubItem();
                    subitem.setIdSubItem(resultSet.getInt(0));
                    subitem.setHabilitado(resultSet.getInt(1));
                    subitem.setFavorito(resultSet.getInt(2));
                    subitem.setDescricao(resultSet.getString(3));
                    subitem.setCategoriaNome(resultSet.getString(4));
                    subitem.setTipoNome(resultSet.getString(5));
                    subitem.setItemNome(resultSet.getString(6));
                    subitem.setIdCategoria(resultSet.getInt(7));
                    subitem.setIdTipo(resultSet.getInt(8));
                    subitem.setIdItem(resultSet.getInt(9));

                    myArray.add(subitem);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }


    public List<SubItem> listarTodosReferencia(Integer categoria, Integer tipo, Integer item) {
        List<SubItem> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From subitem where idCategoria=" + categoria + " and idTipo=" + tipo + " and idItem =" + item, null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    SubItem subitem = new SubItem();
                    subitem.setIdSubItem(resultSet.getInt(0));
                    subitem.setIdCategoria(resultSet.getInt(1));
                    subitem.setIdTipo(resultSet.getInt(2));
                    subitem.setIdItem(resultSet.getInt(3));
                    subitem.setDescricao(resultSet.getString(4));
                    subitem.setHabilitado(resultSet.getInt(5));
                    myArray.add(subitem);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    public SubItem buscarByNome(String nome) {
        SubItem subitem = new SubItem();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From subitem where descricao = '" + nome.toUpperCase() + "'", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    subitem.setIdSubItem(resultSet.getInt(0));
                    subitem.setIdCategoria(resultSet.getInt(1));
                    subitem.setIdTipo(resultSet.getInt(2));
                    subitem.setIdItem(resultSet.getInt(3));
                    subitem.setDescricao(resultSet.getString(4));
                    subitem.setHabilitado(resultSet.getInt(5));
                } while (resultSet.moveToNext());
            }
        }
        return subitem;
    }


    @Override
    public List<String> listarTodosString() {
        List<String> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From subitem ", null);
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
    public Integer contarDescricao(Integer categoria, Integer tipo, Integer item, String descricao) {
        Integer contador = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(*) from subitem " +
                "where subitem.idCategoria = " + categoria +
                " and subitem.idTipo = " + tipo +
                " and subitem.idItem = " + item +
                " and subitem.descricao = '" + descricao + "'", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    contador = resultSet.getInt(0);
                } while (resultSet.moveToNext());
            }
        }
        return contador;
    }

    @Override
    public long alterarStatus(Lancamento lancamento) {
        long id = 0;
        SQLiteDatabase db = getWritebleDB();
        ContentValues values = new ContentValues();
        values.put("habilitado", lancamento.getSubitem().getHabilitado());
        values.put("favorito", lancamento.getSubitem().getFavorito());
        String where = "idSubItem = ?";
        String argumentos[] = {String.valueOf(lancamento.getSubitem().getIdSubItem())};
        id = db.update("subitem", values, where, argumentos);
        return id;
    }
}

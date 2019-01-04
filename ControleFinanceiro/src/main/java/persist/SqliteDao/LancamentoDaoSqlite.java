package persist.SqliteDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Banco;
import model.Categoria;
import model.Conta;
import model.Elemento;
import model.Item;
import model.Lancamento;
import model.SubItem;
import model.Tipo;
import persist.DAO.LancamentoDao;

public class LancamentoDaoSqlite extends GenericDaoSqlite implements LancamentoDao {

    @Override
    public long salvar(Lancamento lancamento) {
        SQLiteDatabase db = getWritebleDB();
        long id;
        db.execSQL("PRAGMA foreign_keys = ON");
        ContentValues values = new ContentValues();
        values.put("idCategoria", lancamento.getCategoria().getIdCategoria());
        values.put("idTipo", lancamento.getTipo().getIdTipo());
        values.put("idItem", lancamento.getItem().getIdItem());
        values.put("idSubItem", lancamento.getSubitem().getIdSubItem());
        values.put("idElemento", lancamento.getElemento().getIdElemento());
        values.put("idBanco", lancamento.getBanco().getIdBanco());
        values.put("idConta", lancamento.getConta().getIdConta());
        values.put("data", lancamento.getData().toString());
        values.put("valor", lancamento.getValor());
        values.put("idFavoritoEstrutura", lancamento.getIdFavoritoEstrutura());
        values.put("favoritoEstruturaNome", lancamento.getFavoritoEstruturaNome());
        values.put("idFavoritoConta", lancamento.getIdFavoritoConta());
        values.put("favoritoContaNome", lancamento.getFavoritoContaNome());
        id = db.insert("lancamento", null, values);
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;
    }

    @Override
    public void criar(Lancamento p) {

    }

    @Override
    public long buscar(Lancamento p) {
        return 0;
    }

    @Override
    public long excluir(Lancamento lancamento) {
        SQLiteDatabase db = getWritebleDB();
        db.execSQL("PRAGMA foreign_keys = ON");
        String where = "idLancamento = " + lancamento.getIdLancamento();
        long id = db.delete("lancamento", where, null);
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;
    }

    @Override
    public long alterar(Lancamento lancamento) {
        SQLiteDatabase db = getWritebleDB();
        ContentValues values = new ContentValues();
        values.put("idCategoria", lancamento.getCategoria().getIdCategoria());
        values.put("idTipo", lancamento.getTipo().getIdTipo());
        values.put("idItem", lancamento.getItem().getIdItem());
        values.put("idSubItem", lancamento.getSubitem().getIdSubItem());
        values.put("idElemento", lancamento.getElemento().getIdElemento());
        values.put("idBanco", lancamento.getBanco().getIdBanco());
        values.put("idConta", lancamento.getConta().getIdConta());
        values.put("data", lancamento.getData().toString());
        values.put("valor", lancamento.getValor());
        values.put("idFavoritoEstrutura", lancamento.getIdFavoritoEstrutura());
        values.put("favoritoEstruturaNome", lancamento.getFavoritoEstruturaNome());
        values.put("idFavoritoConta", lancamento.getIdFavoritoConta());
        values.put("favoritoContaNome", lancamento.getFavoritoContaNome());
        String where = "idLancamento = ?";
        String argumentos[] = {String.valueOf(lancamento.getIdLancamento())};
        long id = db.update("lancamento", values, where, argumentos);
        return id;
    }

    @Override
    public List<Lancamento> listarTodos() {
        List<Lancamento> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select idLancamento, categoria.idCategoria, categoria.descricao, tipo.idCategoria, tipo.descricao, " +
                "item.idCategoria, item.descricao, subitem.idCategoria, subitem.descricao, elemento.idElemento, elemento.descricao, " +
                "banco.idBanco, banco.descricao, conta.idConta, conta.descricao, lancamento.data, lancamento.valor, " +
                "lancamento.idFavoritoEstrutura, lancamento.favoritoEstruturaNome, lancamento.idFavoritoConta, lancamento.favoritoContaNome " +
                "from lancamento " +
                "left join categoria on(lancamento.idCategoria = categoria.idCategoria) " +
                "left join tipo on (lancamento.idTipo = tipo.idTipo) " +
                "left join item on (lancamento.idItem = item.idItem) " +
                "left join subitem on (lancamento.idSubItem = subitem.idSubItem) " +
                "left join elemento on(lancamento.idElemento = elemento.idElemento) " +
                "left join banco on(lancamento.idBanco = banco.idBanco) " +
                "left join conta on (lancamento.idConta = conta.idConta)", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Lancamento lancamento = new Lancamento();
                    Categoria categoria = new Categoria();
                    Tipo tipo = new Tipo();
                    Item item = new Item();
                    SubItem subItem = new SubItem();
                    Elemento elemento = new Elemento();
                    Banco banco = new Banco();
                    Conta conta = new Conta();

                    categoria.setIdCategoria(resultSet.getInt(1));
                    categoria.setDescricao(resultSet.getString(2));
                    tipo.setIdTipo(resultSet.getInt(3));
                    tipo.setDescricao(resultSet.getString(4));
                    item.setIdItem(resultSet.getInt(5));
                    item.setDescricao(resultSet.getString(6));
                    subItem.setIdSubItem(resultSet.getInt(7));
                    subItem.setDescricao(resultSet.getString(8));
                    elemento.setIdElemento(resultSet.getInt(9));
                    elemento.setDescricao(resultSet.getString(10));
                    banco.setIdBanco(resultSet.getInt(11));
                    banco.setDescricao(resultSet.getString(12));
                    conta.setIdConta(resultSet.getInt(13));
                    conta.setDescricao(resultSet.getString(14));

                    lancamento.setIdLancamento(resultSet.getInt(0));
                    lancamento.setCategoria(categoria);
                    lancamento.setTipo(tipo);
                    lancamento.setItem(item);
                    lancamento.setSubitem(subItem);
                    lancamento.setElemento(elemento);
                    lancamento.setBanco(banco);
                    lancamento.setConta(conta);
                    lancamento.setData(resultSet.getString(15));
                    lancamento.setValor(resultSet.getFloat(16));
                    lancamento.setIdFavoritoEstrutura(resultSet.getInt(17));
                    lancamento.setFavoritoEstruturaNome(resultSet.getString(18));
                    lancamento.setIdFavoritoConta(resultSet.getInt(19));
                    lancamento.setFavoritoContaNome(resultSet.getString(20));
                    myArray.add(lancamento);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<String> listarTodosString() {
        return null;
    }

    @Override
    public Float buscarValorById(int id) {
        Float valor = null;

        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select valor from lancamento where idLancamento = '" + id + "'" , null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                valor = resultSet.getFloat(0);
            }
        }

        return valor;
    }
}

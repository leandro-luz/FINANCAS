package persist.SqliteDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Banco;
import model.Carteira;
import model.Categoria;
import model.Lancamento;
import persist.DAO.BancoDao;
import persist.DAO.CategoriaDao;

public class BancoDaoSqlite extends GenericDaoSqlite implements BancoDao {
    @Override
    public long salvar(Banco banco) {
        SQLiteDatabase db = getWritebleDB();
        db.execSQL("PRAGMA foreign_keys = ON");
        long id;
        List<String> myArray = new ArrayList<>();
        myArray = this.listarTodosString();

        if (myArray.size() > 0 && myArray.contains(banco.getDescricao().toUpperCase())) {
            id = -1;
        } else {
            ContentValues values = new ContentValues();
            values.put("descricao", banco.getDescricao().toUpperCase());
            values.put("habilitado", banco.getHabilitado());
            id = db.insert("banco", null, values);
        }
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;
    }

    @Override
    public void criar(Banco p) {

    }

    @Override
    public long buscar(Carteira carteira) {
        long quantidade = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(idBanco) " +
                "FROM lancamento where idBanco = " + carteira.getBanco().getIdBanco(), null);
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
    public long excluir(Carteira carteira) {
        long quantidade, id;
        quantidade = buscar(carteira);
        //verificar se não existe nos lançamentos
        if (quantidade == 0) {
            SQLiteDatabase db = getWritebleDB();
            db.execSQL("PRAGMA foreign_keys = ON");
            String where = "idBanco = " + carteira.getBanco().getIdBanco();
            id = db.delete("banco", where, null);
            db.execSQL("PRAGMA foreign_keys = OFF");
        } else {
            id = -1;
        }

        return id;
    }

    @Override
    public long alterar(Carteira carteira) {
        long id = 0;
        SQLiteDatabase db = getWritebleDB();
        ContentValues values = new ContentValues();
        values.put("descricao", carteira.getBanco().getDescricao().toUpperCase());
        String where = "idBanco = ?";
        String argumentos[] = {String.valueOf(carteira.getBanco().getIdBanco())};
        id = db.update("banco", values, where, argumentos);

        return id;
    }

    @Override
    public List<Banco> listarTodosHabilitados() {
        List<Banco> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From banco where habilitado = 1", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Banco banco = new Banco();
                    banco.setIdBanco(resultSet.getInt(0));
                    banco.setDescricao(resultSet.getString(1));
                    banco.setHabilitado(resultSet.getInt(2));
                    myArray.add(banco);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }
    @Override
    public List<Banco> listarTodos() {
        List<Banco> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From banco", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Banco banco = new Banco();
                    banco.setIdBanco(resultSet.getInt(0));
                    banco.setDescricao(resultSet.getString(1));
                    banco.setHabilitado(resultSet.getInt(2));
                    myArray.add(banco);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<String> listarTodosString() {
        List<String> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From banco", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    myArray.add(resultSet.getString(1));
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public long alterarStatus(Carteira carteira) {
        long id = 0;
        SQLiteDatabase db = getWritebleDB();
        ContentValues values = new ContentValues();
        values.put("habilitado", carteira.getBanco().getHabilitado());
        String where = "idBanco = ?";
        String argumentos[] = {String.valueOf(carteira.getBanco().getIdBanco())};
        id = db.update("banco", values, where, argumentos);
        return id;
    }
}

package persist.SqliteDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.Banco;
import model.Carteira;
import model.Conta;
import model.Lancamento;
import model.SubItem;
import persist.DAO.CategoriaDao;
import persist.DAO.ContaDao;

public class ContaDaoSqlite extends GenericDaoSqlite implements ContaDao {
    @Override
    public long salvar(Conta conta) {
        SQLiteDatabase db = getWritebleDB();
        db.execSQL("PRAGMA foreign_keys = ON");
        long id;

        Integer contador = contarDescricao(conta.getIdBanco(), conta.getDescricao().toUpperCase());
        if (contador > 0) {
            id = -1;
        } else {
            ContentValues values = new ContentValues();
            values.put("idBanco", conta.getIdBanco());
            values.put("descricao", conta.getDescricao().toUpperCase());
            values.put("saldo", conta.getSaldo().toString());
            values.put("habilitado", conta.getHabilitado());
            values.put("favorito", conta.getFavorito());
            id = db.insert("conta", null, values);
        }
        db.execSQL("PRAGMA foreign_keys = OFF");
        return id;
    }

    @Override
    public void criar(Conta p) {
    }

    @Override
    public long buscar(Carteira carteira) {
        long quantidade = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(idConta) " +
                "FROM lancamento where idConta = " + carteira.getConta().getIdConta() ,null);
        if(resultSet!= null){
            if(resultSet.moveToFirst()){
                do {
                    quantidade = resultSet.getInt(0);
                }while(resultSet.moveToNext());
            }
        }
        return quantidade;
    }

    @Override
    public long excluir(Carteira carteira) {
        long quantidade, id;
        quantidade = buscar(carteira);
        //verificar se não existe nos lançamentos
        if(quantidade == 0){
            SQLiteDatabase db = getWritebleDB();
            db.execSQL("PRAGMA foreign_keys = ON");
            String where = "idConta = " + carteira.getConta().getIdConta();
            id = db.delete("conta" ,where, null);
            db.execSQL("PRAGMA foreign_keys = OFF");
        }else{
            id = -1;
        }
        return id;
    }

    @Override
    public long alterar(Carteira carteira) {
        long id = 0;
            SQLiteDatabase db = getWritebleDB();
            ContentValues values = new ContentValues();
            values.put("descricao", carteira.getConta().getDescricao().toUpperCase());
            values.put("habilitado",carteira.getConta().getHabilitado());
            values.put("favorito", carteira.getConta().getFavorito());
            values.put("saldo", carteira.getConta().getSaldo());
            String where = "idConta = ?";
            String argumentos[] = {String.valueOf(carteira.getConta().getIdConta())};
            id = db.update("conta", values, where, argumentos);
        return id;
    }

    @Override
    public List<Conta> listarTodosHabilitados() {
        List<Conta> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select conta.idConta, conta.idBanco, banco.descricao,conta.descricao," +
                " conta.habilitado, conta.favorito, conta.saldo\n" +
                "from conta inner join banco on(conta.idBanco = banco.idBanco) where conta.habilitado = 1", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Conta conta = new Conta();
                    conta.setIdConta(resultSet.getInt(0));
                    conta.setIdBanco(resultSet.getInt(1));
                    conta.setBancoNome(resultSet.getString(2));
                    conta.setDescricao(resultSet.getString(3));
                    conta.setHabilitado(resultSet.getInt(4));
                    conta.setFavorito(resultSet.getInt(5));
                    conta.setSaldo(resultSet.getFloat(6));
                    myArray.add(conta);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }
    @Override
    public List<Conta> listarTodos() {
        List<Conta> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select conta.idConta, conta.idBanco, banco.descricao,conta.descricao," +
                " conta.habilitado, conta.favorito, conta.saldo\n" +
                "from conta inner join banco on(conta.idBanco = banco.idBanco)", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    Conta conta = new Conta();
                    conta.setIdConta(resultSet.getInt(0));
                    conta.setIdBanco(resultSet.getInt(1));
                    conta.setBancoNome(resultSet.getString(2));
                    conta.setDescricao(resultSet.getString(3));
                    conta.setHabilitado(resultSet.getInt(4));
                    conta.setFavorito(resultSet.getInt(5));
                    conta.setSaldo(resultSet.getFloat(6));
                    myArray.add(conta);
                } while (resultSet.moveToNext());
            }
        }
        return myArray;
    }

    @Override
    public List<String> listarTodosString() {
        List<String> myArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("Select * From conta", null);
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
    public Conta buscarByNome(String nome) {
        Conta conta = new Conta();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select conta.idConta, conta.idBanco, banco.descricao,conta.descricao, " +
                "conta.habilitado, conta.favorito, conta.saldo " +
                "from conta inner join banco on(conta.idBanco = banco.idBanco) " +
                "where conta.descricao = '" + nome + "'", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    conta.setIdConta(resultSet.getInt(0));
                    conta.setIdBanco(resultSet.getInt(1));
                    conta.setBancoNome(resultSet.getString(2));
                    conta.setDescricao(resultSet.getString(3));
                    conta.setHabilitado(resultSet.getInt(4));
                    conta.setFavorito(resultSet.getInt(5));
                    conta.setSaldo(resultSet.getFloat(6));
                } while (resultSet.moveToNext());
            }
        }
        return conta;
    }

    @Override
    public Conta buscarById(Integer id) {
        Conta conta = new Conta();
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select conta.idConta, conta.idBanco, banco.descricao,conta.descricao, " +
                "conta.habilitado, conta.favorito, conta.saldo " +
                "from conta inner join banco on(conta.idBanco = banco.idBanco) " +
                "where conta.idConta = '" + id + "'", null);
        if (resultSet != null) {
            if (resultSet.moveToFirst()) {
                do {
                    conta.setIdConta(resultSet.getInt(0));
                    conta.setIdBanco(resultSet.getInt(1));
                    conta.setBancoNome(resultSet.getString(2));
                    conta.setDescricao(resultSet.getString(3));
                    conta.setHabilitado(resultSet.getInt(4));
                    conta.setFavorito(resultSet.getInt(5));
                    conta.setSaldo(resultSet.getFloat(6));
                } while (resultSet.moveToNext());
            }
        }
        return conta;
    }

    @Override
    public void alterarSaldoConta(Lancamento lancamento, int opcao) {
        //buscar o valor do saldo
        Conta conta = buscarById(lancamento.getConta().getIdConta());

        //verificando se é despesa ou receita
        int cat = lancamento.getCategoria().getIdCategoria();
        float valor = lancamento.getValor() ;
        if (cat == 1){
            valor = conta.getSaldo() + (valor * -1 * opcao);
        }else{
            valor = conta.getSaldo() + (valor * opcao);
        }
        SQLiteDatabase db = getWritebleDB();
        ContentValues values = new ContentValues();

        values.put("saldo", valor);
        String where = "idConta = ?";
        String argumentos[] = {String.valueOf(lancamento.getConta().getIdConta())};

        long id = db.update("conta", values, where, argumentos);
    }

    @Override
    public Integer contarDescricao(Integer conta, String descricao) {
        Integer contador = 0;
        SQLiteDatabase db = getReadableDB();
        Cursor resultSet = db.rawQuery("select count(*) from conta " +
                "where conta.idBanco = " + conta +
                " and conta.descricao = '" + descricao + "'", null);
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

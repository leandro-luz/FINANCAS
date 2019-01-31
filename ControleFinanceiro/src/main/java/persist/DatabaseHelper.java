package persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "controlefinanceiro";
    private static final int VERSAO = 1;

    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }


    public static DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String categoria = "CREATE TABLE categoria(" +
                "idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT not null, " +
                "habilitado INTEGER not null, " +
                "incremento INTEGER not null)";
        sqLiteDatabase.execSQL(categoria);

        String tipo = "CREATE TABLE tipo(" +
                "idTipo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idCategoria INTEGER not null, " +
                "descricao TEXT not null, " +
                "habilitado INTEGER not null," +
                "CONSTRAINT fk_categoria " +
                "FOREIGN KEY(idCategoria)" +
                "REFERENCES categoria(idCategoria)" +
                "ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(tipo);

        String item = "CREATE TABLE item(" +
                "idItem INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idCategoria INTEGER not null, " +
                "idTipo INTEGER not null, " +
                "descricao TEXT not null, " +
                "habilitado INTEGER not null," +
                "CONSTRAINT fk_tipo " +
                "FOREIGN KEY (idTipo)" +
                "REFERENCES tipo(idTipo)" +
                "ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(item);

        String subitem = "CREATE TABLE subitem(" +
                "idSubItem INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idCategoria INTEGER not null, " +
                "idTipo INTEGER not null, " +
                "idItem INTEGER not null, " +
                "descricao TEXT not null, " +
                "habilitado INTEGER not null," +
                "favorito INTEGER not null," +
                "CONSTRAINT fk_item " +
                "FOREIGN KEY (idItem)" +
                "REFERENCES item(idItem)" +
                "ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(subitem);

        String elemento = "CREATE TABLE elemento(" +
                "idElemento INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idCategoria INTEGER not null, " +
                "idTipo INTEGER not null, " +
                "idItem INTEGER not null, " +
                "idSubItem INTEGER not null, " +
                "descricao TEXT not null, " +
                "habilitado INTEGER not null," +
                "favorito INTEGER not null, " +
                "CONSTRAINT fk_subitem " +
                "FOREIGN KEY (idSubItem)" +
                "REFERENCES subitem(idSubItem)" +
                "ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(elemento);

        String lancamento = "CREATE TABLE lancamento(" +
                "idLancamento INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idCategoria INTEGER not null, " +
                "idTipo INTEGER not null, " +
                "idItem INTEGER not null, " +
                "idSubItem INTEGER not null, " +
                "idElemento INTEGER, " +
                "idBanco INTEGER not null, " +
                "idConta INTEGER not null, " +
                "data String not null, " +
                "valor REAL not null, " +
                "validação Boolen not null, "+
                "idFavoritoEstrutura INTEGER not null, " +
                "favoritoEstruturaNome String not null, " +
                "idFavoritoConta INTEGER not null," +
                "favoritoContaNome String not null)";
        sqLiteDatabase.execSQL(lancamento);

        String favorito = "CREATE TABLE favorito(" +
                "idFavorito INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "opcao TEXT not null," +
                "idOpcao INTEGER not null, " +
                "descricao TEXT not null," +
                "idCategoria INTEGER , " +
                "idTipo INTEGER , " +
                "idItem INTEGER , " +
                "idSubItem INTEGER , " +
                "idElemento INTEGER , " +
                "idBanco INTEGER , " +
                "idConta INTEGER , " +
                "CONSTRAINT fk_subitem FOREIGN KEY (idSubItem) REFERENCES subitem(idSubItem), " +
                "CONSTRAINT fk_elemento FOREIGN KEY (idElemento) REFERENCES elemento(idElemento), " +
                "CONSTRAINT fk_conta FOREIGN KEY (idConta) REFERENCES conta(idConta)" +
                "ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(favorito);

        String banco = "CREATE TABLE banco(" +
                "idBanco INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT not null, " +
                "habilitado INTEGER not null)";
        sqLiteDatabase.execSQL(banco);

        String conta = "CREATE TABLE conta(" +
                "idConta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idBanco INTEGER not null, " +
                "descricao TEXT not null, " +
                "habilitado INTEGER not null," +
                "favorito INTEGER not null," +
                "saldo REAL," +
                "CONSTRAINT fk_conta " +
                "FOREIGN KEY (idBanco)" +
                "REFERENCES banco(idBanco)" +
                "ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(conta);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {


    }
}


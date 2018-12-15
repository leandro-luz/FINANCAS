package leoluz.com.controlefinanceiro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import leoluz.com.controlefinanceiro.R;
import persist.DAO.FabricaDao;
import persist.DAO.FavoritoDao;

public class ActivityConfiguracao extends AppCompatActivity implements View.OnClickListener {
    String cat;
    String tip;
    String operacao;

    Button btnVoltar;
    Button btnCategoria;
    Button btnTipoCat;
    Button btnItem;
    Button btnSubItem;
    Button btnElemento;
    Button btnBanco;
    Button btnConta;
    Button btnFavorito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao);

        //alterando o titulo da tela
        setTitle("CONFIGURAÇÃO");

        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);

        btnCategoria = findViewById(R.id.btnCategoria);
        btnCategoria.setOnClickListener(this);

        btnTipoCat = findViewById(R.id.btnTipoCat);
        btnTipoCat.setOnClickListener(this);

        btnItem = findViewById(R.id.btnItem);
        btnItem.setOnClickListener(this);

        btnSubItem = findViewById(R.id.btnSubItem);
        btnSubItem.setOnClickListener(this);

        btnElemento = findViewById(R.id.btnElemento);
        btnElemento.setOnClickListener(this);

        btnBanco = findViewById(R.id.btnBanco);
        btnBanco.setOnClickListener(this);

        btnConta = findViewById(R.id.btnConta);
        btnConta.setOnClickListener(this);

        btnFavorito = findViewById(R.id.btnFavorito);
        btnFavorito.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        //Ação ao clicar em algum botão
        switch (view.getId()) {
            case R.id.btnCategoria:
                Intent cat = new Intent(this, ActivityAcao.class);
                cat.putExtra("opcao", "categoria");
                startActivity(cat);
                break;
            case R.id.btnTipoCat:
                Intent tip = new Intent(this, ActivityAcao.class);
                tip.putExtra("opcao", "tipo");
                startActivity(tip);
                break;
            case R.id.btnItem:
                Intent it = new Intent(this, ActivityAcao.class);
                it.putExtra("opcao", "item");
                startActivity(it);
                break;
            case R.id.btnSubItem:
                Intent sit = new Intent(this, ActivityAcao.class);
                sit.putExtra("opcao", "subitem");
                startActivity(sit);
                break;
            case R.id.btnBanco:
                Intent ban = new Intent(this, ActivityAcao.class);
                ban.putExtra("opcao", "banco");
                startActivity(ban);
                break;
            case R.id.btnConta:
                Intent cnt = new Intent(this, ActivityAcao.class);
                cnt.putExtra("opcao", "conta");
                startActivity(cnt);
                break;
            case R.id.btnElemento:
                Intent ele = new Intent(this, ActivityAcao.class);
                ele.putExtra("opcao", "elemento");
                startActivity(ele);
                break;

            case R.id.btnFavorito:
                FavoritoDao favoritoDao = FabricaDao.criarFavoritoDao();
                if (favoritoDao.listarNomes().size()>0) {
                    Intent fav = new Intent(this, ActivityListaFavorito.class);
                    //ele.putExtra("opcao","elemento");
                    startActivity(fav);
                }else{
                    Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnVoltar:
                //Intent back = new Intent(this, ActivityMenu.class);
                //startActivity(back);
                finish();
                break;
        }

    }

}

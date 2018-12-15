package leoluz.com.controlefinanceiro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import leoluz.com.controlefinanceiro.R;

public class ActivityMenu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //alterando o titulo da tela
        setTitle("MENU");

        //Criando os botões na tela menu
        Button btnResumo = (Button) findViewById(R.id.btnResumo);
        btnResumo.setOnClickListener(this);

        Button btnExtrato = (Button) findViewById(R.id.btnExtrato);
        btnExtrato.setOnClickListener(this);

        Button btnLancamento = (Button) findViewById(R.id.btnLancamento);
        btnLancamento.setOnClickListener(this);

        Button btnConfiguracao = (Button) findViewById(R.id.btnConfiguracao);
        btnConfiguracao.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Ação ao clicar em algum botão
        switch (view.getId()){
            case R.id.btnResumo:
                Intent res = new Intent(this,ActivityResumo.class);
                startActivity(res);
                break;
            case R.id.btnExtrato:
                Intent ext = new Intent(this,ActivityExtrato.class);
                startActivity(ext);
                break;
            case R.id.btnLancamento:
                Intent lan = new Intent(this,ActivityAcao.class);
                lan.putExtra("opcao", "lancamento");
                startActivity(lan);
                break;
            case R.id.btnConfiguracao:
                Intent conf = new Intent(this,ActivityConfiguracao.class);
                startActivity(conf);
                break;
        }

    }
}

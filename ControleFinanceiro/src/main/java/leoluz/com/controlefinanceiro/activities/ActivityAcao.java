package leoluz.com.controlefinanceiro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import leoluz.com.controlefinanceiro.R;
import util.QuantidadeLista;

public class ActivityAcao extends AppCompatActivity implements View.OnClickListener {
    String opcao;
    String operacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acao);

        Intent it = getIntent();
        opcao = it.getStringExtra("opcao");
        operacao = it.getStringExtra("operacao");

        //alterando o titulo da tela
        setTitle("AÇÃO PARA " + opcao.toUpperCase());

        Button btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);

        Button btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnAlterar.setOnClickListener(this);

        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        //Ação ao clicar em algum botão
        switch (view.getId()) {
            case R.id.btnAlterar:
                long qtd = QuantidadeLista.verificarQtd(opcao);
                if (QuantidadeLista.verificarQtd(opcao) != 0) {
                    if(opcao.equalsIgnoreCase("lancamento")){
                        Intent lis = new Intent(this, ActivityListaLancamento.class);
                        lis.putExtra("opcao", opcao);
                        lis.putExtra("lista", opcao);
                        lis.putExtra("operacao", "update");
                        startActivity(lis);
                    }else{
                        Intent lis = new Intent(this, ActivityListaCompleta.class);
                        lis.putExtra("opcao", opcao);
                        lis.putExtra("lista", opcao);
                        lis.putExtra("operacao", "update");
                        startActivity(lis);
                    }
                } else {
                    Toast.makeText(this, "A lista está vazia", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCadastrar:

                if (opcao.equalsIgnoreCase("banco") || opcao.equalsIgnoreCase("conta")) {
                    Intent cad = new Intent(this, ActivityCadastroContas.class);
                    cad.putExtra("opcao", opcao);
                    cad.putExtra("operacao", "insert");
                    startActivity(cad);
                    break;
                } else if (opcao.equalsIgnoreCase("lancamento")){
                    Intent cad = new Intent(this, ActivityCadastroLancamento.class);
                    cad.putExtra("opcao", opcao);
                    cad.putExtra("operacao", "insert");
                    startActivity(cad);
                    break;
                }

                else {
                    //ainda não implementado o código
                    Intent cad = new Intent(this, ActivityCadastro.class);
                    cad.putExtra("opcao", opcao);
                    cad.putExtra("operacao", "insert");
                    startActivity(cad);
                    break;
                }


            case R.id.btnVoltar:
                //Intent back = new Intent(this, ActivityConfiguracao.class);
                //startActivity(back);
                finish();
                break;
        }

    }

}

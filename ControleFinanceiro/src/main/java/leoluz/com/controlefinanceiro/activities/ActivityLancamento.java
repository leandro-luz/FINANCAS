package leoluz.com.controlefinanceiro.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import leoluz.com.controlefinanceiro.R;

public class ActivityLancamento extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumo);

//alterando o titulo da tela
        setTitle("MENU");

    }

    @Override
    public void onClick(View view) {


    }
}

package infnet.edu.br.rendimentosprof;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultadosActivity extends AppCompatActivity {

    private TextView txtSalarioLiq;
    private TextView txtTotalDesc;
    private TextView txtPorcDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        Intent ResultadosActivity = getIntent();
        String salarioLiquido = ResultadosActivity.getStringExtra("salarioLiquido");
        String totalDesc = ResultadosActivity.getStringExtra("totalDesc");
        String strporcDesc = ResultadosActivity.getStringExtra("strporcDesc");

        txtSalarioLiq = (TextView) findViewById(R.id.txtSalarioLiq);
        txtPorcDesc = (TextView) findViewById(R.id.txtPorcDesc);
        txtTotalDesc = (TextView) findViewById(R.id.txtTotDesc);

        txtSalarioLiq.setText(salarioLiquido);
        txtTotalDesc.setText(totalDesc);
        txtPorcDesc.setText(strporcDesc);

    }
}

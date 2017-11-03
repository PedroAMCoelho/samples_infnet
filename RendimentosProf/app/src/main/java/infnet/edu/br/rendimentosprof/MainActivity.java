package infnet.edu.br.rendimentosprof;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText edtSalarioBruto;
    private EditText edtPlano;
    private EditText edtPensao;
    private EditText edtNdeps;
    private EditText edtOutros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSalarioBruto = (EditText) findViewById(R.id.edtSalarioBruto);
        edtPlano = (EditText) findViewById(R.id.edtPlano);
        edtPensao = (EditText) findViewById(R.id.edtPensao);
        edtNdeps = (EditText) findViewById(R.id.edtNdeps);
        edtOutros = (EditText) findViewById(R.id.edtOutros);
    }

    public void calcSalario(View v) {
        if (edtSalarioBruto.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Por favor, preencha o valor de seu Salário Bruto", Toast.LENGTH_SHORT).show();
        } else {

            try{
                checkIfEmpty(edtPlano);
                checkIfEmpty(edtPensao);
                checkIfEmpty(edtNdeps);
                checkIfEmpty(edtOutros);
                Rendimentos r = new Rendimentos (valorDoEdt(edtSalarioBruto), valorDoEdt(edtPensao), valorDoEdt(edtNdeps), valorDoEdt(edtPlano), valorDoEdt(edtOutros));
                String salarioLiquido = r.getSalarioLiq().toString();
                String totalDesc = r.getTotalDesc().toString();

                double porcDesc = r.getPorcDesc();
                DecimalFormat df = new DecimalFormat("###,##0.00");
                String strporcDesc = df.format(porcDesc) + "%";

                Intent ResultadosActivity = new Intent(this, ResultadosActivity.class);
                ResultadosActivity.putExtra("salarioLiquido", salarioLiquido);
                ResultadosActivity.putExtra("totalDesc", totalDesc);
                ResultadosActivity.putExtra("strporcDesc", strporcDesc);
                startActivity(ResultadosActivity);

            }catch(Exception e) {
                Log.d("erro",e.getMessage());
            }


        }

    }

    public void checkIfEmpty(EditText edttxt){

        if(edttxt.getText().toString().isEmpty()){

            edttxt.setText("0");
        }

    }

    private Double valorDoEdt(EditText edt){

        return Double.parseDouble(edt.getText().toString());

    }

}

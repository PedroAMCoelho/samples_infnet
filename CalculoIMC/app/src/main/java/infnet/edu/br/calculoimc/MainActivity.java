package infnet.edu.br.calculoimc;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView editPeso;
    public TextView editAltura;
    public TextView tvMostraIMC;
    public TextView tvClassificacao;
    public Button btCalcular;
    public int peso;
    public float altura;
    public float imc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editPeso = (TextView) findViewById(R.id.editPeso);
        editAltura = (TextView) findViewById(R.id.editAltura);
        tvMostraIMC = (TextView) findViewById(R.id.tvMostraIMC);
        tvClassificacao = (TextView) findViewById(R.id.tvClassificacao);
        btCalcular = (Button) findViewById(R.id.btCalcular);

    }
                //onClick para calculo do IMC
            public void calculaIMC(View v) {

                String valorPeso = editPeso.getText().toString().trim();
                String valorAltura = editAltura.getText().toString().trim();
                //RegExp para checar se a altura seguiu o formato "m.cm"
                String regexStr = "^([0-2]{1})[.]([0-9]{2})$";

                //Checa se ha input do usuário
                if (!valorPeso.isEmpty() && !valorAltura.isEmpty()) {

                    peso = Integer.parseInt(valorPeso);
                    altura = Float.parseFloat(valorAltura);
                    imc = peso / (altura * altura);

                    if(!valorAltura.matches(regexStr)) {

                        AlertDialog AlertDialog;
                        AlertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        AlertDialog.setMessage("Digite o valor de altura no formato m.cm - Exemplo: 1.70 (obs: O limite é 2m e 99cm)");
                        AlertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }

                        });

                        AlertDialog.show();

                        //força o foco para o campo Altura
                        editAltura.requestFocus();

                        //Exibição dos níveis de IMC na tela
                    }else if (imc < 16) {
                        tvMostraIMC.setText(getString(R.string.imc_msg, imc));
                        tvClassificacao.setText("Magreza grave");
                    }else if(imc < 17){
                        tvMostraIMC.setText(getString(R.string.imc_msg, imc));
                        tvClassificacao.setText("Magreza moderada");
                    }else if (imc < 18.5) {
                        tvMostraIMC.setText(getString(R.string.imc_msg, imc));
                        tvClassificacao.setText("Magreza leve");
                    } else if (imc < 25) {
                        tvMostraIMC.setText(getString(R.string.imc_msg, imc));
                        tvClassificacao.setText("Saudável");
                    } else if (imc < 30) {
                        tvMostraIMC.setText(getString(R.string.imc_msg, imc));
                        tvClassificacao.setText("Sobrepeso");
                    }else if(imc < 35) {
                        tvMostraIMC.setText(getString(R.string.imc_msg, imc));
                        tvClassificacao.setText("Obesidade Grau I");
                    }else if(imc < 40) {
                        tvMostraIMC.setText(getString(R.string.imc_msg, imc));
                        tvClassificacao.setText("Obesidade Grau II (severa)");
                            } else {
                        tvMostraIMC.setText(getString(R.string.imc_msg, imc));
                        tvClassificacao.setText("Obesidade Grau III (mórbida)");

                        //Se não houver input do usuário, modal envia mensagem
                            } }else{
                    AlertDialog AlertDialog;
                    AlertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    AlertDialog.setMessage("Favor preencher todos os campos");
                    AlertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog.show();
                    }
            }
}








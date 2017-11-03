package infnet.edu.br.rendimentosprof;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText edtSalarioBruto;
    private EditText edtPlano;
    private EditText edtPensao;
    private EditText edtNdeps;
    private EditText edtOutros;
    private static final String FILE_NAME = "resultados.txt";


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
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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

                gravarResultados(r,this);
                lerArquivo();


                Intent ResultadosActivity = new Intent(this, ResultadosActivity.class);
                ResultadosActivity.putExtra("salarioLiquido", salarioLiquido);
                ResultadosActivity.putExtra("totalDesc", totalDesc);
                ResultadosActivity.putExtra("strporcDesc", strporcDesc);
                startActivity(ResultadosActivity);

            }else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(getApplicationContext(), "É necessária permissão para salvar os dados", Toast.LENGTH_SHORT).show();

                    }

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }

                } catch(Exception e) {
                Log.d("erro",e.getMessage());
            }


        }

    }


    public void gravarResultados(Object o, Context ctx){

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File arq = new File(getFilesDir(), "resultados.txt");

        if(!arq.exists()) {
            try {
                arq.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{

            fos = ctx.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(o.toString());
            oos.write(System.getProperty("line.separator").getBytes());
            oos.flush();
            fos.close();

        } catch(Exception e) {
            Log.d("erro",e.getMessage());
        }

    }

    public void lerArquivo(){

        BufferedReader entrada = null;
        File arq = null;

        try{
            arq = new File(getFilesDir(), "resultados.txt");

            entrada = new BufferedReader(new InputStreamReader(new FileInputStream(arq)));
            String linha;
            StringBuilder buffer = new StringBuilder();

            while((linha = entrada.readLine()) != null){

                buffer.append(linha);

            }

            Toast.makeText(getApplicationContext(), buffer, Toast.LENGTH_SHORT).show();

        } catch (IOException e){e.printStackTrace ();}

    }

    public void checkIfEmpty(EditText edttxt){

        if(edttxt.getText().toString().isEmpty()){

            edttxt.setText("0");
        }

    }

    private Double valorDoEdt(EditText edt){

        return Double.parseDouble(edt.getText().toString());

    }

    public void limparResultados(View v) {
        File arq = new File(getFilesDir(), "resultados.txt");
        arq.delete();
            Toast.makeText(getApplicationContext(),"Resultados salvos apagados!",Toast.LENGTH_LONG).show();

    }

}

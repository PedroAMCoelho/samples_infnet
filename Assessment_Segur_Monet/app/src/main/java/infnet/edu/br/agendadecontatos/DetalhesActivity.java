package infnet.edu.br.agendadecontatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DetalhesActivity extends AppCompatActivity {

    TextView txtInfo;
    TextView txtTit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        String itemDoAdapter = intent.getStringExtra("itemDoAdapter");

        txtInfo = (TextView) findViewById(R.id.textView);
        txtTit = (TextView) findViewById(R.id.textView6);
        txtTit.setText(itemDoAdapter);
        File arq;
        String lstrlinha;

        try {
            // esse File funciona como uma instância ou um "get()" do arquivo que vou ler
            // ps: esse caminho é idêntico ao do file que criei no mainActivity e salvei os dados
            arq = new File(getFilesDir(), itemDoAdapter);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            /// loop que percorrerá todas as  linhas do arquivo.txt que eu quero ler
            while ((lstrlinha = br.readLine()) != null) {
                if (!txtInfo.getText().toString().equals("")) {
                    txtInfo.append("\n");
                }
                txtInfo.append(lstrlinha);
            }

            Mensagem("Texto Carregado com sucesso!");

        } catch (Exception e)

        {
            Mensagem("Erro : " + e.getMessage());
        }
    }

    private void Mensagem(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}


package infnet.edu.br.agendadecontatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ListView agenda;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //executa metodo estatico do main, mostrando anuncio interstitial
        MainActivity.showInterstitial();

        ArrayList<String> Arquivos = new ArrayList<String>();

        //File diretorio = new File(getFilesDir(), nome);
        File[] arquivos = getFilesDir().listFiles();

        //popula a lista de contatos da segunda activity
        if (arquivos != null) {
            int length = arquivos.length;
            for (int i = 1; i < length; ++i) {

                File f = arquivos[i];

                if (f.isFile()) {
                    Arquivos.add(f.getName());
                }
            }
        }

        agenda = (ListView) findViewById(R.id.lvAgenda);

        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, Arquivos);

        agenda.setAdapter(arrayAdapter);

        agenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DetalhesActivity.class);
                String itemDoAdapter = arrayAdapter.getItem(position);
                intent.putExtra("itemDoAdapter", itemDoAdapter);
                startActivity(intent);
            }
        });
    }

}

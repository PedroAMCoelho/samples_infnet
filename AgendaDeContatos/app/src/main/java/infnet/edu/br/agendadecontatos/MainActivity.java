package infnet.edu.br.agendadecontatos;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private TextView txtRoot;
    private TextView txtNome;
    private TextView txtTelefone;
    private TextView txtEmail;
    private TextView txtCidade;
    Intent secondActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        txtRoot = (TextView) findViewById(R.id.txtRoot2);
        txtNome = (TextView) findViewById(R.id.edtNome);
        txtTelefone = (TextView) findViewById(R.id.edtTelefone);
        txtEmail = (TextView) findViewById(R.id.edtEmail);
        txtCidade = (TextView) findViewById(R.id.edtCidade);
        txtRoot.append(ObterDiretorio());

    }

    private void Mensagem(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //retorna o diretório de armazenamento externo
    private String ObterDiretorio() {
        File root = android.os.Environment.getExternalStorageDirectory();

        return root.toString();
    }

    public void click_Salvar(View v) {


        final String nome;
        final byte[] espaco;
        File arq;

        try {
            nome = txtNome.getText().toString();

            arq = new File(Environment.getExternalStorageDirectory(), nome);
            FileOutputStream fos;

            //transformamos o texto digitado em um “Array de Bytes”
            String strTelefone = txtTelefone.getText().toString();
            String strEmail = txtEmail.getText().toString();
            String strCidade = txtCidade.getText().toString();
            espaco = "\n".getBytes();

            //verifica se ha input do usuario
            if (!nome.isEmpty() && !strTelefone.isEmpty() && !strCidade.isEmpty() && !strEmail.isEmpty()) {

                byte[] telefone = strTelefone.getBytes();
                byte[] email = strEmail.getBytes();
                byte[] cidade = strCidade.getBytes();

                //verifica se há permissão do usuário
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    fos = new FileOutputStream(arq);
                    fos.write(telefone); //inserimos os dados seguido de uma notificação ao usuário
                    fos.write(espaco);
                    fos.write(email);
                    fos.write(espaco);
                    fos.write(cidade);
                    fos.flush();
                    fos.close();
                    Mensagem("Texto salvo com sucesso!");
                    limparFormulario(txtEmail, txtCidade, txtNome, txtTelefone);

                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Mensagem("É necessária permissão para salvar os dados");

                    }

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                }

            } else {

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

        } catch (Exception e) {
            Mensagem("Erro : " + e.getMessage());
        }

    }

    public void click_Carregar(View v) {
        File arq = new File(Environment.getExternalStorageDirectory(), txtNome.getText().toString());
        File[] listOfFiles = arq.listFiles();

        if (listOfFiles != null && listOfFiles.length > 0) {

            //loop para pegar todos os elementos do diretorio
            for (int i = 0; i < listOfFiles.length; i++) {
                //verifica se cada elemento é um arquivo. Se tiver algum, vai pra agenda de contatos
                if (listOfFiles[i].isFile()) {

                    secondActivity = new Intent(this, SecondActivity.class);
                    startActivity(secondActivity);
                    break;

                } else {
                    //if para executar o else apenas uma vez
                    if (i == listOfFiles.length - 1) {
                        AlertDialog AlertDialog;
                        AlertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        AlertDialog.setMessage("Nenhum registro foi encontrado \n Adicione um contato à sua agenda!");
                        AlertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog.show();

                    }
                }
            }

        } else {
            Mensagem("Calma, rapaz! Adicione seu primeiro contato!");
        }

    }

    public void removerArquivos() {
        File diretorio = new File(ObterDiretorio());
        File[] arquivos = diretorio.listFiles();
        for (int i = 1; i < arquivos.length; i++) {
            File f = arquivos[i];
            if (f.isFile()) {
                Mensagem(arquivos[i].getName());
                arquivos[i].delete();
            }
        }
    }

    public void limparFormulario(TextView a, TextView b, TextView c, TextView d) {

        a.setText("");
        b.setText("");
        c.setText("");
        d.setText("");

    }

    public void click_Limpar(View view) {

        removerArquivos();
        Mensagem("Seus contatos foram apagados com sucesso!");
        limparFormulario(txtEmail, txtCidade, txtNome, txtTelefone);

    }

}
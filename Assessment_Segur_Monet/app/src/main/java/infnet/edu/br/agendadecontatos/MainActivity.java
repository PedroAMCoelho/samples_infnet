package infnet.edu.br.agendadecontatos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtCPF;
    private TextView txtEmail;
    private TextView txtSenha;
    private TextView txtConfSenha;
    public static InterstitialAd mInterstitialAd;
    Intent secondActivity;
    String nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        txtNome = (TextView) findViewById(R.id.edtNome);
        txtCPF = (TextView) findViewById(R.id.edtCPF);
        txtEmail = (TextView) findViewById(R.id.edtEmail);
        txtSenha = (TextView) findViewById(R.id.edtSenha);
        txtConfSenha = (TextView) findViewById(R.id.edtConfSenha);
        //criação do anuncio interstitial
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //carregamento do anuncio interstitial
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    //metodo estatico que apresentará o anuncio interstitial na segunda activity
    public static void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    private void Mensagem(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void AlertDialog (String msg){

        AlertDialog AlertDialog;
        AlertDialog = new AlertDialog.Builder(MainActivity.this).create();
        AlertDialog.setMessage(msg);
        AlertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog.show();

    }

    public void click_Salvar(View v) {

        final byte[] espaco;
        File arq;
        String regExEmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        String regExCaract = "^\\w+$";

        try {

            nome = txtNome.getText().toString();
            arq = new File(getFilesDir(), nome);
            FileOutputStream fos;

            //transformamos o texto digitado em um “Array de Bytes”
            String strCPF = txtCPF.getText().toString();
            String strEmail = txtEmail.getText().toString();
            String strSenha = txtSenha.getText().toString();
            String strConfSenha = txtConfSenha.getText().toString();
            espaco = "\n".getBytes();

            //verifica se ha input do usuario
            if (nome.isEmpty() || strCPF.isEmpty() || strSenha.isEmpty() || strEmail.isEmpty() || strConfSenha.isEmpty()) {

                AlertDialog ("Favor preencher todos os campos");

            }else if (!strEmail.matches(regExEmail)){

                AlertDialog ("Formato de E-mail inválido");

                //força o foco para o campo e-mail
                txtEmail.requestFocus();

            }else if (!nome.matches(regExCaract)) {

                AlertDialog ("Nome inválido (caracteres especiais não são permitidos) - Você pode escolher um nome contendo letras, números e underline");

                //força o foco para o campo nome
                txtNome.requestFocus();

            }else if (!strSenha.equals(strConfSenha)) {

                AlertDialog ("Ops, sua confirmação de senha foi digitada incorretamente");

                //força o foco para o campo nome
                txtConfSenha.requestFocus();

            } else {

                byte[] cpf = strCPF.getBytes();
                byte[] email = strEmail.getBytes();
                byte[] senha = strSenha.getBytes();
                byte[] confSenha = strConfSenha.getBytes();

                fos = new FileOutputStream(arq);
                fos.write(cpf); //inserimos os dados seguido de uma notificação ao usuário
                fos.write(espaco);
                fos.write(email);
                fos.write(espaco);
                fos.write(senha);
                fos.write(espaco);
                fos.write(confSenha);
                fos.flush();
                fos.close();
                Mensagem("Texto salvo com sucesso!");
                limparFormulario(txtEmail, txtSenha, txtNome, txtCPF, txtConfSenha);

            }

        } catch (Exception e) {
            Mensagem("Erro : " + e.getMessage());
        }

    }

    public void click_Carregar(View v) {
        //File arq = new File(getFilesDir(), nome);
        File[] listOfFiles = getFilesDir().listFiles();

        if (listOfFiles != null && listOfFiles.length > 0) {

            //loop para pegar todos os elementos do diretorio
            for (int i = 0; i < listOfFiles.length; i++) {
                //verifica se cada elemento é um arquivo. Se tiver algum, vai pra agenda de contatos
                if (listOfFiles[i].isFile()) {

                    secondActivity = new Intent(this, SecondActivity.class);
                    startActivity(secondActivity);

                    break;

                } else {
                    //if para executar o else apenas uma vez e não uma vez p/ cada File
                    if (i == listOfFiles.length - 1) {
                        AlertDialog ("Nenhum registro foi encontrado \n Adicione um contato à sua agenda!");
                    }
                }
            }

        } else {
            Mensagem("Calma, rapaz! Adicione seu primeiro contato!");
        }

    }

    public void removerArquivos() {
        //File diretorio = new File(getFilesDir(), nome);
        File[] arquivos = getFilesDir().listFiles();
        for (int i = 1; i < arquivos.length; i++) {
            File f = arquivos[i];
            if (f.isFile()) {
                Mensagem(arquivos[i].getName());
                arquivos[i].delete();
            }
        }
    }

    public void limparFormulario(TextView a, TextView b, TextView c, TextView d, TextView e) {

        a.setText("");
        b.setText("");
        c.setText("");
        d.setText("");
        e.setText("");

    }

    public void click_Limpar(View view) {

        removerArquivos();
        Mensagem("Seus contatos foram apagados com sucesso!");
        limparFormulario(txtEmail, txtSenha, txtNome, txtCPF, txtConfSenha);

    }

}
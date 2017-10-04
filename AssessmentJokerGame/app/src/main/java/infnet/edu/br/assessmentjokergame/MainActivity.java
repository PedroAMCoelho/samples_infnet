package infnet.edu.br.assessmentjokergame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    public ImageView cardOne;
    public ImageView cardTwo;
    public ImageView cardThree;
    public ImageView selectedCard;
    public Button btnConfirm;
    public TextView labelText;

    public final static Random random = new Random();

    public final static int[] cardDeck = new int[] {

            R.drawable.jokercard,
            R.drawable.jokercard,
            R.drawable.wincard
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardOne = (ImageView) findViewById(R.id.imageView3);
        cardTwo = (ImageView) findViewById(R.id.imageView);
        cardThree = (ImageView) findViewById(R.id.imageView2);
        labelText = (TextView) findViewById(R.id.textView2);

    }
    public void selectCard(View view) {

        selectedCard = (ImageView) view;

        cardOne.setClickable(false);
        cardTwo.setClickable(false);
        cardThree.setClickable(false);

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f);
        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);
        scaleDown.start();

    }
    public void showCard(View v) {

        if (selectedCard == null) {
            AlertDialog AlertDialog;
            AlertDialog = new AlertDialog.Builder(this).create();
            AlertDialog.setMessage("Favor selecionar uma carta");
            AlertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog.show();
        } else {

            int randomIndex = random.nextInt(cardDeck.length);
            selectedCard.setImageResource(cardDeck[randomIndex]);

            btnConfirm = (Button) v;
            btnConfirm.setClickable(false);

            if (randomIndex == 2) {

                labelText.setVisibility(View.VISIBLE);
                labelText.setText("Parabéns, você venceu !!!");

            } else {

                labelText.setVisibility(View.VISIBLE);
                labelText.setText("Que pena, o Coringa venceu !!!");

                   }

            Button newGameBtn = (Button) findViewById(R.id.button2);
            newGameBtn.setVisibility(View.VISIBLE);

               }
    }

        public void newGame(View v){

            selectedCard.setImageResource(R.drawable.facecard);

            cardOne.setClickable(true);
            cardTwo.setClickable(true);
            cardThree.setClickable(true);
            btnConfirm.setClickable(true);
            labelText.setVisibility(View.INVISIBLE);
            v.setVisibility(View.INVISIBLE);
            selectedCard.setScaleX(1);
            selectedCard.setScaleY(1);
            selectedCard = null;
        }

    }






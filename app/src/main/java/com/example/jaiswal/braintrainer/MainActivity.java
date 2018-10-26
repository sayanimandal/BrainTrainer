package com.example.jaiswal.braintrainer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RelativeLayout playLayout;
    TextView timerTextView,questionTextView,scoreTextView,ansTextView1,ansTextView2,ansTextView3,ansTextView4,resultTextView;
    int random1,random2, ansViewNo,rightAns=0,totalQues=0,wrongAns=0,operatorNo,randomSum;
    Button startButton,stopButton;
    Boolean firstSet;
    Random rand;
    String operatorVal;
    ArrayList<Integer> answers = new ArrayList<>(); //not initialising this will give fatal error
    ArrayList<String> operators = new ArrayList<>();
    CountDownTimer countDownTimer=new CountDownTimer(30200+500,1000){
        @Override
        public void onTick(long l) {
            String timerText = Integer.toString((int)l/1000)+"s";
            timerTextView.setText(timerText);
        }
        @Override
        public void onFinish() {
            Log.i("Info","Timer Finished");
            startButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.INVISIBLE);
            resultTextView.setText("");
            startButton.setText("Play Again");
            firstSet =false;
            playLayout.setAlpha(.5f);
        }
    };

    public void startMethod(View view){
        startButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.VISIBLE);
        countDownTimer.start();
        //if (!firstSet) {
            generateQuestion();
        //}
        playLayout.setAlpha(1);
    }
    public void onStop(View view){
        countDownTimer.cancel();
        Log.i("Info","Timer Finished");
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");
        startButton.setText("Play Again");
        firstSet =false;
        playLayout.setAlpha(.5f);
    }

    public void reloadNextSet(){
        totalQues = totalQues + 1;
        String totalQuesString = Integer.toString(totalQues);
        String rightAnsString = Integer.toString(rightAns);
        scoreTextView.setText(rightAnsString + "/" + totalQuesString);
        generateQuestion();
    }

    public void onViewClick(View view){
        if (( view.getTag()).equals(Integer.toString(ansViewNo))) {
            resultTextView.setText("Right!");
            resultTextView.setTextColor(Color.GREEN);
            rightAns = rightAns + 1;
            reloadNextSet();
        } else {
            resultTextView.setText("Wrong!");
            resultTextView.setTextColor(Color.RED);
            reloadNextSet();
        }

    }
    
    /*public String generateRandom(int i){
        String randomString= Integer.toString(rand.nextInt(i));
        while (randomString.equals(Integer.toString(randomSum))) randomString= Integer.toString(rand.nextInt(i));
        return randomString;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playLayout = findViewById(R.id.playLayout);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        questionTextView = findViewById(R.id.questionTextView);
        ansTextView1 = findViewById(R.id.ansTextView1);
        ansTextView2 = findViewById(R.id.ansTextView2);
        ansTextView3 = findViewById(R.id.ansTextView3);
        ansTextView4 = findViewById(R.id.ansTextView4);
        firstSet = true;
        operators.add("+");operators.add("-");operators.add("*");
        //generateQuestion();
    }

    private void generateQuestion() {
        //random1 = new Random().nextInt(100);
        rand = new Random();
        random1 = rand.nextInt(99-13)+12;
        random2 = rand.nextInt(99-13)+12;
        operatorNo = rand.nextInt(3);
        operatorVal = operators.get(operatorNo);
        questionTextView.setText(Integer.toString(random1)+ operatorVal +Integer.toString(random2));
        ansViewNo = rand.nextInt(4); //Random.nextInt(max-min+1)+min, will generate number between 1 to 3
        Log.i("Random View No: ",Integer.toString(ansViewNo));
        answers.clear();
        for(int i=0;i<4;i++){
            if (i == ansViewNo){
                if (operatorNo == 0) randomSum = random1 + random2;
                else if (operatorNo == 1) randomSum = random1 - random2;
                else if (operatorNo == 2) randomSum = random1 * random2;
                answers.add(randomSum);
            } else {
                if (operatorNo !=2) {
                    wrongAns = rand.nextInt(110);
                    while (wrongAns == randomSum) {
                        wrongAns = rand.nextInt(110);
                    }
                } else
                {
                    wrongAns = rand.nextInt(2000-1051)+1050;
                    while (wrongAns == randomSum) {
                        wrongAns = rand.nextInt(2000-1051)+1050;
                    }
                }
                answers.add(wrongAns);
            }
        }
        ansTextView1.setText(Integer.toString(answers.get(0)));
        ansTextView2.setText(Integer.toString(answers.get(1)));
        ansTextView3.setText(Integer.toString(answers.get(2)));
        ansTextView4.setText(Integer.toString(answers.get(3)));
        /*if (ansTextView1.getTag().equals(Integer.toString(ansViewNo))) {
            ansViewTag = "1";
            ansTextView1.setText(Integer.toString(randomSum));
            ansTextView2.setText(generateRandom(150)); ansTextView3.setText(generateRandom(150));ansTextView4.setText(generateRandom(150));
        } else if (ansTextView2.getTag().equals(Integer.toString(ansViewNo))) {
            ansViewTag = "2";
            ansTextView2.setText(Integer.toString(randomSum));
            ansTextView1.setText(generateRandom(150));ansTextView3.setText(generateRandom(150));ansTextView4.setText(generateRandom(150));
        } else if (ansTextView3.getTag().equals(Integer.toString(ansViewNo))) {
            ansViewTag = "3";
            ansTextView3.setText(Integer.toString(randomSum));
            ansTextView1.setText(generateRandom(150));ansTextView2.setText(generateRandom(150));ansTextView4.setText(generateRandom(150));
        } else if (ansTextView4.getTag().equals(Integer.toString(ansViewNo))) {
            ansViewTag = "4";
            ansTextView4.setText(Integer.toString(randomSum));
            ansTextView1.setText(generateRandom(150));ansTextView2.setText(generateRandom(150));ansTextView3.setText(generateRandom(150));
        }*/
    }
}

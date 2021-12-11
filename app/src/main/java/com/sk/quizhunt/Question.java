package com.sk.quizhunt;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class Question extends AppCompatActivity implements View.OnClickListener {

    private TextView que,qcount,timer;
    private Button op1,op2,op3,op4;
    private List<Questionclass> quelist;
    private CountDownTimer countDownTimer;
    private FirebaseFirestore firestore;
    int queno,score,set;
    private static Random random=new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set=getIntent().getIntExtra("class",1);
        firestore=FirebaseFirestore.getInstance();
        // setno=getIntent().getIntExtra("nosets",1);
        setContentView(R.layout.activity_question);
        que=findViewById(R.id.que);
        qcount=findViewById(R.id.count);
        timer=findViewById(R.id.timer);

        op1=findViewById(R.id.o1);
        op2=findViewById(R.id.o2);
        op3=findViewById(R.id.o3);
        op4=findViewById(R.id.o4);
        op1.setOnClickListener(this);
        op2.setOnClickListener(this);
        op3.setOnClickListener(this);
        op4.setOnClickListener(this);
        score=0;

        getQuestionlist();

    }

    private void getQuestionlist() {
    /*    quelist = new ArrayList<>();
        quelist.add(new Questionclass("Question 1","A","B","C","D",2));
        quelist.add(new Questionclass("Question 2","A","B","C","D",3));
        quelist.add(new Questionclass("Question 3","A","B","C","D",1));
        quelist.add(new Questionclass("Question 4","A","B","C","D",4));

        setQuestion();*/
        quelist = new ArrayList<>();
        firestore.collection("QUIZ").document("DAILY CHALLENGE")
                .collection("CAT"+String.valueOf(set))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    QuerySnapshot querySnapshot=task.getResult();
                    for(QueryDocumentSnapshot doc : querySnapshot) {
                        quelist.add(new Questionclass(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("ANSWER"))));
                        Collections.reverse(quelist);
                    }
                    setQuestion();
                }
                else
                {
                    Toast.makeText(Question.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });






    }

    private void setQuestion() {
        timer.setText(String.valueOf(20));//change
        que.setText(quelist.get(0).getQue());
        op1.setText(quelist.get(0).getOpA());
        op2.setText(quelist.get(0).getOpB());
        op3.setText(quelist.get(0).getOpC());
        op4.setText(quelist.get(0).getOpD());
        qcount.setText(String.valueOf(1)+ "/" +String.valueOf(quelist.size()));
        startTimer();

        queno = 0;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long l) {
                if(l<20000) //change time
                    timer.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {

                changeQuestion();
            }
        };
        countDownTimer.start();
    }

    private void changeQuestion() {
        if(queno < quelist.size()-1)
        {

            queno++;
            playanim(op1,0,1);
            playanim(op2,0,2);
            playanim(op3,0,3);
            playanim(op4,0,4);
            playanim(que,0,0);
            qcount.setText(String.valueOf(queno+1)+ "/" +String.valueOf(quelist.size()));
            timer.setText(String.valueOf(20));//change
            startTimer();
            op1.setBackgroundResource(R.drawable.option_unselected);
            op2.setBackgroundResource(R.drawable.option_unselected);
            op3.setBackgroundResource(R.drawable.option_unselected);
            op4.setBackgroundResource(R.drawable.option_unselected);

        }
        else
        {

            Intent intent= new Intent(Question.this,scoreactivity.class);
            intent.putExtra("SCORE",String.valueOf(score)+ "/" +String.valueOf(quelist.size()));
            intent.putExtra("S",score);
            startActivity(intent);
            Question.this.finish();
        }
    }

    private void playanim(final View view, final int value, final int viewno) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if(value== 0)
                        {
                            switch (viewno)
                            {
                                case 0:
                                    ((TextView)view).setText(quelist.get(queno).getQue());
                                    break;
                                case 1:
                                    ((Button)view).setText(quelist.get(queno).getOpA());
                                    break;
                                case 2:
                                    ((Button)view).setText(quelist.get(queno).getOpB());
                                    break;
                                case 3:
                                    ((Button)view).setText(quelist.get(queno).getOpC());
                                    break;
                                case 4:
                                    ((Button)view).setText(quelist.get(queno).getOpD());
                                    break;
                            }
                            if(viewno !=0)
                            {
                              //  ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE8F00")));

                                ((Button)view).setBackgroundResource(R.drawable.option_unselected);
                            }
                      //      ((Button)view).setBackgroundResource(R.drawable.wooden);
                            playanim(view,1,viewno);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        int selectedoption= 0;
        switch (view.getId())
        {
            case R.id.o1:
                selectedoption = 1;
                break;
            case R.id.o2:
                selectedoption = 2;
                break;
            case R.id.o3:
                selectedoption = 3;
                break;
            case R.id.o4:
                selectedoption = 4;
                break;
            default:
        }
        countDownTimer.cancel();
        checkans(selectedoption,view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkans(int selectedoption, final View view) {
        if(selectedoption == quelist.get(queno).getCorrectans())
        {
            //right ans
            score++;
         //   ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            ((Button)view).setBackgroundResource(R.drawable.green);

        }
        else {
            //wrong ans
            //((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            ((Button)view).setBackgroundResource(R.drawable.red);
            switch (quelist.get(queno).getCorrectans()) {
                case 1:
                //    op1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    op1.setBackgroundResource(R.drawable.green);
                    break;
                case 2:
                 //   op2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    op2.setBackgroundResource(R.drawable.green);
                    break;
                case 3:
                  //  op3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    op3.setBackgroundResource(R.drawable.green);
                    break;
                case 4:
                   // op4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    op4.setBackgroundResource(R.drawable.green);
                    break;
            }
        }
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();

            }
        },2000);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
    }
}
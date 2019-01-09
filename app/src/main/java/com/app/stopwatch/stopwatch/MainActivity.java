package com.app.stopwatch.stopwatch;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button mulai,pause,lap,reset;
    TextView myTimer;
    Handler customHandler = new Handler();
    LinearLayout myContainer;
    long waktumulai=0L,waktuMilliSecond=0L,updateTime=0L,timeSwapBuff=0L;
    int secs,mins,miliscnd;
    Runnable updateTimeThread = new Runnable() {
        @Override
        public void run() {
            waktuMilliSecond = SystemClock.uptimeMillis()-waktumulai;
            updateTime = timeSwapBuff + waktuMilliSecond;
            secs = (int)(updateTime/1000);
            mins=secs/60;
            secs%=60;
            miliscnd=(int)(updateTime%1000);
            myTimer.setText(""+mins+":"+String.format("%2d",secs)+":"+String.format("%3d",miliscnd));
            customHandler.postDelayed(this,0);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reset = (Button)findViewById(R.id.btnReset);
        mulai = (Button)findViewById(R.id.btnMulai);
        pause = (Button)findViewById(R.id.btnPause);
        lap = (Button)findViewById(R.id.btnLap);
        myTimer = (TextView)findViewById(R.id.timer);
        myContainer = (LinearLayout)findViewById(R.id.container);

        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waktumulai = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimeThread,0);
                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff+=waktuMilliSecond;
                customHandler.removeCallbacks(updateTimeThread);

                reset.setEnabled(true);
            }
        });
        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View tmbhTampilan = inflater.inflate(R.layout.row,null);
                TextView txtValue = (TextView)tmbhTampilan.findViewById(R.id.txtContent);
                txtValue.setText(myTimer.getText());
                myContainer.addView(tmbhTampilan);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waktumulai = 0L;
                waktuMilliSecond= 0L;
                updateTime=0L;
                timeSwapBuff=0L;
                secs = 0;
                miliscnd = 0;
                mins=0;
                myTimer.setText("0.00.000");

            }
        });

    }

}

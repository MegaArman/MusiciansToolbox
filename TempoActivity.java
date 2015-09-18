package arman.timesignaturecalculator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;



public class TempoActivity extends ActionBarActivity {


//    List<Integer> tracker = new ArrayList<Integer>();
   Calendar calendar;
//    TextView tvBPM;
//    TextView tvMS;
//    TextView tvDiff;
//    TextView tvAVG;
    TextView tvTheBPM;
    TextView tvTaps;
    //int lastTimeClicked = -1;
    //int numClicks = 0;

    int tapCount = 0;
    int diffTotal = 0;
    int diffCount = 0;
    double diffAVG = 0;

    boolean firstTime = true;
    int oldSeconds = -5;
    int oldMS = -5;

//    int seconds = seconds.get(Calendar.SECOND);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo);

        calendar = Calendar.getInstance();
        //tvBPM = (TextView)findViewById(R.id.tvBPM);
        //tvMS = (TextView)findViewById(R.id.tvMS);
      //  tvDiff = (TextView)findViewById(R.id.tvDiff);
    //    tvAVG = (TextView)findViewById(R.id.tvAVG);
        tvTheBPM = (TextView)findViewById(R.id.tvTheBPM);
        tvTaps = (TextView)findViewById(R.id.tvTaps);

        final Button btnTap = (Button) findViewById(R.id.btnTap);
        btnTap.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnTap.setAlpha(1f);
                } else
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnTap.setAlpha(.9f);
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tempo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        if (id == R.id.action_home) {
            startActivity(new Intent(TempoActivity.this, HomeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void tapOnClick(View view)
    {
        tapCount++;
        tvTaps.setText(Integer.toString(tapCount));
        calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.SECOND); //0 -59!!!
        int MS = calendar.get(Calendar.MILLISECOND);
        int difference = MS-oldMS;


        //Compensations for cycles :
        if (seconds == oldSeconds + 1) {
            MS = MS + 1000;
            difference = MS - oldMS;
        }
        else if (seconds == oldSeconds + 2) {
            MS = MS + 2000;
            difference = MS - oldMS;
        }

        if (seconds == 0 && oldSeconds == 59)
        {
            MS = MS + 1000;
            difference = MS - oldMS;
        }

      //  tvDiff.setText(Integer.toString(difference));

        if (!firstTime) {
            if (oldSeconds >= 57)
            {
                if ((seconds+59) - oldSeconds >2 && seconds-oldSeconds < 0)
                {
//                oldSeconds = seconds;
                    Toast.makeText(getApplicationContext(), "INSIDE", Toast.LENGTH_SHORT).show();
                    reset();
                    return;
                }
            }

            if (seconds > oldSeconds + 2)
            {
                //  oldSeconds = seconds;
                reset();
                return;
            }

//            Toast.makeText(getApplicationContext(), "INSIDE", Toast.LENGTH_SHORT).show();
            diffCount++;
            diffTotal = diffTotal + difference;
            diffAVG = diffTotal / diffCount;
            //tvAVG.setText(Double.toString(diffAVG));
            double BPM = (1000/diffAVG) * 60;
            tvTheBPM.setText(Long.toString(Math.round(BPM)));
        }
        else
        {
            firstTime = false;
            oldSeconds = seconds;
            oldMS = calendar.get(Calendar.MILLISECOND);
            return;
       //     return;
        }

            //tvMS.setText(Integer.toString(MS));
            oldSeconds = seconds;
           // tvBPM.setText(Integer.toString(seconds));
            oldMS = calendar.get(Calendar.MILLISECOND);
    }

    private void reset()
    {
        tapCount = 0;
        tvTaps.setText(Integer.toString(tapCount));
        //tvBPM.setText("First Tap");
        tvTheBPM.setText("Reset");
        diffTotal = 0;
        diffCount = 0;
        diffAVG = 0;

        firstTime = true;
        oldSeconds = -5;
        oldMS = -5;
    }

    public void resetOnClick(View view)
    {
        reset();
    }
}

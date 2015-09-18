package arman.timesignaturecalculator;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MetronomeActivity extends ActionBarActivity
{
    private SoundPool soundPool;
    private int metronomeSoundID;
    private EditText tempoET;
    private Timer t;
    private SeekBar sb;

  //  private int oldRate = -1;
    private int rate = 1;
    private final static int MIN = 0;
    private final static int MAX = 300;
    private Switch aSwitch;
    private int timerRate = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);


        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        metronomeSoundID = soundPool.load(this, R.raw.singlehit, 1); //each 1 is .5 secs
        tempoET = (EditText)findViewById(R.id.etTempo);

        aSwitch = (Switch)findViewById(R.id.theSwitch);
        sb = (SeekBar)findViewById(R.id.seekBar);
        sb.setMax(MAX);


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tempoET.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                if (aSwitch.isChecked())
                {
                    t.cancel();
                    t.purge();
                    playMetronome();
                }
            }
        });
        sb.setProgress(120);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_metronome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchOnClick(View view)
    {
        if (aSwitch.isChecked())
        {
            playMetronome();
        }
        else
        {
            //note it starts in off position so it would always take 2 click to reach here
            t.cancel();
            t.purge();
            //        oldRate = -1;
        }
    }

    public void playMetronome() {
            calcRate();
            t = new Timer();
            t.schedule(
                    new TimerTask() {
                        public void run() {

                            soundPool.play(metronomeSoundID, 1, 1, 1, 0, 1);
                        }
                    },
                    0,      // run first occurrence after 0 ms
                    timerRate); // in ms, ONCE CREATED THIS WILL NOT CHANGE..

    }


    private void calcRate()
    {
        String tempo = tempoET.getText().toString();
        double r2;
      //  Toast.makeText(getApplicationContext(), "calcRate()", Toast.LENGTH_SHORT).show();
        if (tempo.length() > 0 && tempo.length() <= 3)
        {
            if (Integer.parseInt(tempo) > MAX || Integer.parseInt(tempo) < MIN) //thus timer rate isn't calculated
            {
                Toast.makeText(getApplicationContext(), "Must be less than " + MAX, Toast.LENGTH_SHORT).show();
                return;
            }
            rate = (Integer.parseInt(tempo));
            sb.setProgress(rate);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Must be less than " + MAX, Toast.LENGTH_SHORT).show();
            return;
        }


        r2 = 1000 / (((double)(rate))/60);
        timerRate= (int)r2;
    }

//    @Override
//    public void onPause()
//    {
//        super.onPause();
//        if (aSwitch.isChecked())
//        {
//            aSwitch.setChecked(false);
//            t.cancel();
//            t.purge();
//        }
//    }
}

//TODO: allow them to change metronome with rad buttons
//TODO ALLOW ONLY ONE INSTANCE AT A TIME
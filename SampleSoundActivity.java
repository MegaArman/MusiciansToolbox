package arman.timesignaturecalculator;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SampleSoundActivity extends ActionBarActivity {

    private RadioGroup RG;
    private MediaPlayer mp;
    private boolean isPlaying = false;
    Context context;
    CharSequence text = "Hello toast!";
    int duration = Toast.LENGTH_SHORT;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_sound);

        RG = (RadioGroup) findViewById(R.id.RG);

        context = getApplicationContext();
        toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP| Gravity.CENTER, 0, 125);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sample_sound, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            startActivity(new Intent(SampleSoundActivity.this, HomeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void playOnClick(View view)
    {
    if (isPlaying)
    {
        mp.stop();
        mp.release();
        isPlaying = false;
    }
//        if (!isPlaying)
//        {
            switch (RG.getCheckedRadioButtonId())
            {
                case R.id.radioButton:

                    toast.setText("Playing Simple Quadruple");
                    mp = MediaPlayer.create(this, R.raw.simplequad);
                    handleCompletion();
                    mp.start();
                    break;
                case R.id.radioButton2:
                    toast.setText("Playing Simple Triple");
                    mp = MediaPlayer.create(this, R.raw.simpletriple);
                    handleCompletion();
                    mp.start();
                    break;
                case R.id.radioButton3:
                    toast.setText("Playing Compound Duple");
                    mp = MediaPlayer.create(this, R.raw.compoundduple);
                    handleCompletion();
                    mp.start();
                    break;
                case R.id.radioButton4:
                    toast.setText("Playing Compound Triple");
                    mp = MediaPlayer.create(this, R.raw.compoundtriple);
                    handleCompletion();
                    mp.start();
                    break;
                default:
                    toast.setText("Select a button!");
                    break;
            }
        toast.show();
        //}
           // }
        }

    private void handleCompletion()
    {
        mp.setVolume(1.5f, 1.5f);
        isPlaying = true;

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                isPlaying = false;
                player.stop();
                player.release();

            }
        });
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if (isPlaying)
        {
            mp.stop();
            mp.release();
            isPlaying = false;
        }
    }
}

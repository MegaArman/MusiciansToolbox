package arman.timesignaturecalculator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


//TODO : add metronome
//TODO : add timesignatures

public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public void launchOnClick(View view)
    {
       startActivity(new Intent(HomeActivity.this, TempoActivity.class));
    }
    public void TSOnClick(View view)
    {
        startActivity(new Intent(HomeActivity.this, SampleSoundActivity.class));
    }
    public void metronomeOnClick(View view)
    {
        startActivity(new Intent(HomeActivity.this, MetronomeActivity.class));
    }

    public void OOPOnClick(View view)
    {
        AudioLooper mySound = new AudioLooper();
        Toast.makeText(getApplicationContext(), mySound.getWord(), Toast.LENGTH_SHORT).show();
    }
}
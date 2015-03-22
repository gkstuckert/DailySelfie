package com.example.g.dailyselfie;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.os.SystemClock;
import android.app.AlarmManager;
import android.content.Intent;
import android.app.PendingIntent;

import android.widget.Toast;


public class DailySelfieActivity extends ActionBarActivity {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private static final long INITIAL_ALARM_DELAY = 5 * 1000L;
    protected static final long JITTER = 5000L;
    private static final long ALARM_REPEAT_INTERVAL = 5*1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        // See Coursera Labe #6 for how to save and restore fragments using
        // onSaveInstanceState and RestoreState.

        // Set the alarm.
        // See: https://developer.android.com/training/scheduling/alarms.html
        // See also Coursera example "AlarmCreateActivity.java"

        alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver2Notify.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        // Set the alarm to go off (inexactly) in two minutes and repeat every
        // two minutes (inexact) thereafter.
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + JITTER + INITIAL_ALARM_DELAY,
                ALARM_REPEAT_INTERVAL, alarmIntent);
        // Show Toast message
        Toast.makeText(getApplicationContext(), "Alarm Set",
                Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_camera:
                return true;
            // case R.id.action_set_alarm:
            //    return setAlarm();
            case R.id.action_cancel_alarm:
                return cancelAlarm();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean cancelAlarm() {
        if( alarmMgr != null ) {
            alarmMgr.cancel(alarmIntent);
            Toast.makeText(getApplicationContext(),
                "Selfie Alarm canceled.", Toast.LENGTH_LONG).show();
        }
        return true;
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}

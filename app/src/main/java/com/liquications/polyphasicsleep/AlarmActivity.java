package com.liquications.polyphasicsleep;

import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.liquications.polyphasicsleep.database.Database;
import com.liquications.polyphasicsleep.preferences.AlarmPreferencesActivity;
import com.liquications.polyphasicsleep.service.AlarmReceiver;

import java.util.Calendar;
import java.util.List;

public class AlarmActivity extends BaseActivity {



	ImageButton newButton;
	ListView mathAlarmListView;
	AlarmListAdapter alarmListAdapter;

    int coreSleep;
    int timeToFirst;
    int timeToSecond;
    int timeToThird;

    int minutesOfNap;
    int AmountOfNaps;
    int timeBetween;
    boolean napOnly;

    private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarm_activity);
        Intent sleepIntent = getIntent();
        Bundle extras = sleepIntent.getExtras();
        if(extras != null){
            int sleepNow = extras.getInt("SLEEP");
            if(sleepNow == 10){
                setCustomAlarm();
            }else if(sleepNow != 0){
                sleepSchedules(sleepNow);
            }
        }

		mathAlarmListView = (ListView) findViewById(android.R.id.list);
		mathAlarmListView.setLongClickable(true);
		mathAlarmListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
				view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
				final Alarm alarm = (Alarm) alarmListAdapter.getItem(position);
				Builder dialog = new Builder(AlarmActivity.this);
				dialog.setTitle("Delete");
				dialog.setMessage("Delete this alarm?");
				dialog.setPositiveButton("Ok", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						Database.init(AlarmActivity.this);
						Database.deleteEntry(alarm);
						AlarmActivity.this.callMathAlarmScheduleService();
						
						updateAlarmList();
					}
				});
				dialog.setNegativeButton("Cancel", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				dialog.show();

				return true;
			}
		});

		callMathAlarmScheduleService();

		alarmListAdapter = new AlarmListAdapter(this);
		this.mathAlarmListView.setAdapter(alarmListAdapter);
		mathAlarmListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				Alarm alarm = (Alarm) alarmListAdapter.getItem(position);
				Intent intent = new Intent(AlarmActivity.this, AlarmPreferencesActivity.class);
				intent.putExtra("alarm", alarm);
				startActivity(intent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);		
		menu.findItem(R.id.menu_item_save).setVisible(false);
		menu.findItem(R.id.menu_item_delete).setVisible(false);
	    return result;
	}
		
	@Override
	protected void onPause() {
		// setListAdapter(null);
		Database.deactivate();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateAlarmList();
	}
	
	public void updateAlarmList(){
		Database.init(AlarmActivity.this);
		final List<Alarm> alarms = Database.getAll();
		alarmListAdapter.setMathAlarms(alarms);
		
		runOnUiThread(new Runnable() {
			public void run() {
				// reload content			
				AlarmActivity.this.alarmListAdapter.notifyDataSetChanged();				
				if(alarms.size() > 0){
					findViewById(android.R.id.empty).setVisibility(View.INVISIBLE);
				}else{
					findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.checkBox_alarm_active) {
			CheckBox checkBox = (CheckBox) v;
			Alarm alarm = (Alarm) alarmListAdapter.getItem((Integer) checkBox.getTag());
			alarm.setAlarmActive(checkBox.isChecked());
			Database.update(alarm);
			AlarmActivity.this.callMathAlarmScheduleService();
			if (checkBox.isChecked()) {
				Toast.makeText(AlarmActivity.this, alarm.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();
			}
		}

	}

    private void createAnAlarmInCode(String name, int m, int h){

        String alarmName = name;
        int min = m;
        int hour = h;
        int mathDif;

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String tempMath = sharedPrefs.getString("defaultMath", "1");
        mathDif = Integer.parseInt(tempMath);


        Alarm codeAlarm = new Alarm();
        codeAlarm.setAlarmName(alarmName);

        Calendar alarmTime = Calendar.getInstance();
        alarmTime.add(Calendar.MINUTE, min);
        alarmTime.add(Calendar.HOUR, hour);
        codeAlarm.setAlarmTime(alarmTime);

        codeAlarm.setDays(Alarm.Day.values());
        switch (mathDif){
            case 1:
                codeAlarm.setDifficulty(Alarm.Difficulty.EASY);
                break;
            case 2:
                codeAlarm.setDifficulty(Alarm.Difficulty.MEDIUM);
                break;
            case 3:
                codeAlarm.setDifficulty(Alarm.Difficulty.HARD);
                break;
            default:
                codeAlarm.setDifficulty(Alarm.Difficulty.MEDIUM);
                break;

        }

        String alarmTonePath = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
        codeAlarm.setAlarmTonePath(alarmTonePath);

        codeAlarm.setVibrate(true);

        Database.init(getApplicationContext());
        Database.create(codeAlarm);
        callMathAlarmScheduleService();

//        updateAlarmList();

//        Toast.makeText(AlarmActivity.this, codeAlarm.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();

    }

    private void sleepSchedules(int alarmCode){

        deleteAllAlarms();


        int code = alarmCode;

        switch(code){
            case 1:
                createAnAlarmInCode("Main", 0, 7);

                Toast.makeText(getApplicationContext(), "Monophasic Alarms Set", Toast.LENGTH_LONG).show();
                break;
            case 2:
                createAnAlarmInCode("Main", 30, 3);

                createAnAlarmInCode("Main2", 0, 9);
                setNotifi(30,5,1);
                Toast.makeText(getApplicationContext(), "Segmented Alarms Set", Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(),"Notification's Set",Toast.LENGTH_SHORT).show();


                break;
            case 3:
                createAnAlarmInCode("Main", 0, 5);
                createAnAlarmInCode("Nap", 30, 13);
                setNotifi(55,11,1);
                Toast.makeText(getApplicationContext(), "Siesta Alarms Set", Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(),"Notification's Set",Toast.LENGTH_SHORT).show();

                break;
            case 4:
                createAnAlarmInCode("Main", 30, 1);
                setNotifi(55,7,1);
                createAnAlarmInCode("Main2", 30, 9);
                setNotifi(55,15,2);
                createAnAlarmInCode("Main3", 30, 17);
                Toast.makeText(getApplicationContext(), "Triphasic Alarms Set", Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(),"Notification's Set",Toast.LENGTH_SHORT).show();

                break;
            case 5:
                createAnAlarmInCode("Main", 30, 3);
                createAnAlarmInCode("Nap", 30, 7);
                setNotifi(5,7,1);
                createAnAlarmInCode("Nap", 30, 11);
                setNotifi(5, 11,2);
                createAnAlarmInCode("Nap", 0, 18);
                setNotifi(35,17,3);
                Toast.makeText(getApplicationContext(), "Everyman Alarms Set", Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(),"Notification's Set",Toast.LENGTH_SHORT).show();

                break;
            case 6:
                createAnAlarmInCode("Main", 30, 3);
                createAnAlarmInCode("Nap", 0, 9);
                setNotifi(25,7,1);
                createAnAlarmInCode("Nap", 20, 15);
                setNotifi(55,14,2);
                Toast.makeText(getApplicationContext(), "Dual Core Alarms Set", Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(),"Notification's Set",Toast.LENGTH_SHORT).show();

                break;
            case 7:
                createAnAlarmInCode("Nap", 20, 0);
                createAnAlarmInCode("Nap", 2, 4);
                setNotifi(42,3,1);
                createAnAlarmInCode("Nap", 44, 7);
                setNotifi(19,7,2);
                createAnAlarmInCode("Nap", 26, 11);
                setNotifi(1,11,3);
                createAnAlarmInCode("Nap", 8, 15);
                setNotifi(43,14,4);
                createAnAlarmInCode("Nap", 50, 18);
                setNotifi(25,18,5);
                Toast.makeText(getApplicationContext(), "Uberman Alarms Set", Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(),"Notification's Set",Toast.LENGTH_SHORT).show();

                break;
            case 8:
                createAnAlarmInCode("Nap", 30, 0);
                createAnAlarmInCode("Nap", 30, 6);
                setNotifi(55,5,1);
                createAnAlarmInCode("Nap", 30, 12);
                setNotifi(55,11,2);
                createAnAlarmInCode("Nap", 30, 18);
                setNotifi(55,17,3);
                Toast.makeText(getApplicationContext(), "Dymaxion Alarms Set", Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(),"Notification's Set",Toast.LENGTH_SHORT).show();

                break;
            case 9:
                Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Something has gone wrong :s", Toast.LENGTH_LONG).show();
                break;

        }
    }

    private void deleteAllAlarms(){
        Database.init(AlarmActivity.this);
        Database.deleteAll();
        AlarmActivity.this.callMathAlarmScheduleService();

//        updateAlarmList();
    }



    private void setNotifi(int mins, int hours, int ID){

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        boolean isOn = sharedPrefs.getBoolean("allowNotifications", true);



        if(isOn){

            int minToSet = mins;
            int hourToSet = hours;
            int notID = ID;
            Calendar cal = Calendar.getInstance();
//        Calendar_Object.set(Calendar.MONTH, 8);
//        Calendar_Object.set(Calendar.YEAR, 2014);
//        Calendar_Object.set(Calendar.DAY_OF_MONTH, 7);
//
//        Calendar_Object.set(Calendar.HOUR_OF_DAY, 17);
//        Calendar_Object.set(Calendar.MINUTE, 30);
//        Calendar_Object.set(Calendar.SECOND, 0);
            cal.add(Calendar.MINUTE, minToSet);
            cal.add(Calendar.HOUR, hourToSet);
            // MyView is my current Activity, and AlarmReceiver is the
            // BoradCastReceiver
            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this,
                    notID, myIntent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		/*
		 * The following sets the Alarm in the specific time by getting the long
		 * value of the alarm date time which is in calendar object by calling
		 * the getTimeInMillis(). Since Alarm supports only long value , we're
		 * using this method.
		 */

            alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(),
                    pendingIntent);
        }



    }

    public void setCustomAlarm(){
        deleteAllAlarms();

        String name = "CUSTOM";
        int count = 0;
        int notificationCount = 0;
        int napOnlyNotiTime;
        int alarmMins;
        int notiMins;
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String temp1 = sharedPrefs.getString("coreSleep", "0");
        coreSleep = Integer.parseInt(temp1);
        temp1 = sharedPrefs.getString("firstNap", "0");
        timeToFirst = Integer.parseInt(temp1);
        temp1 = sharedPrefs.getString("secondNap", "0");
        timeToSecond = Integer.parseInt(temp1);
        temp1 = sharedPrefs.getString("thirdNap", "0");
        timeToThird = Integer.parseInt(temp1);

        temp1 = sharedPrefs.getString("napTime", "0");
        minutesOfNap = Integer.parseInt(temp1);
        temp1 = sharedPrefs.getString("napAmount", "0");
        AmountOfNaps = Integer.parseInt(temp1);
        temp1 = sharedPrefs.getString("napOnlyTimes", "0");
        timeBetween = Integer.parseInt(temp1);
        napOnly = sharedPrefs.getBoolean("napOnly", false);

        if(napOnly){
            createAnAlarmInCode(name, minutesOfNap, 0);
            do{
                minutesOfNap = minutesOfNap + timeBetween;
                createAnAlarmInCode(name, minutesOfNap, 0);
                napOnlyNotiTime = timeBetween - minutesOfNap - 5;
                setNotifi(napOnlyNotiTime,0,notificationCount++);
                count = count + 1;
            } while(count <= AmountOfNaps);
        }else{
            alarmMins = coreSleep;
            int setTimes = 0;
            createAnAlarmInCode(name,alarmMins,0);
            if(AmountOfNaps >= 1) {

                alarmMins = alarmMins + timeToFirst + minutesOfNap;
                createAnAlarmInCode(name, alarmMins, 0);
                notiMins = alarmMins - minutesOfNap - 5;
                setNotifi(notiMins, 0, notificationCount++);
                setTimes++;
            }if(AmountOfNaps >= 2) {


                alarmMins = alarmMins + timeToSecond + minutesOfNap;
                createAnAlarmInCode(name, alarmMins, 0);
                notiMins = alarmMins - minutesOfNap - 5;
                setNotifi(notiMins, 0, notificationCount++);
                setTimes++;
            }if(AmountOfNaps >=3) {

                alarmMins = alarmMins + timeToThird + minutesOfNap;
                createAnAlarmInCode(name, alarmMins, 0);
                notiMins = alarmMins - minutesOfNap - 5;
                setNotifi(notiMins, 0, notificationCount++);
                setTimes++;
            }if(AmountOfNaps >= 4) {
                do{
                    alarmMins = alarmMins + timeBetween + minutesOfNap;
                    createAnAlarmInCode(name, alarmMins, 0);
                    notiMins = alarmMins - minutesOfNap - 5;
                    setNotifi(notiMins, 0, notificationCount++);
                    setTimes++;
                }while(setTimes < AmountOfNaps );
            }



        }
        Toast.makeText(getApplicationContext(), "Custom Alarms Set", Toast.LENGTH_SHORT).show();

            Toast.makeText(getApplicationContext(),"Notification's Set",Toast.LENGTH_SHORT).show();


    }
}
package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.versionedparcelable.ParcelField;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

public class MainActivity extends AppCompatActivity {

    ButtonClickListener press = new ButtonClickListener();
    ButtonClickNavigate navigateThere = new ButtonClickNavigate();

    // servicii
    private IntentFilter intentFilter = new IntentFilter();
    private int serviceStatus = Constants.SERVICE_STOPPED;


    // intent
    private class ButtonClickNavigate implements View.OnClickListener {
        @Override
            public void onClick(View view) {
                String id = ((Button) view).getText().toString();
                if (id.equals("Navigate to secondary activity")){

                    Integer number1 = Integer.parseInt((((EditText) findViewById(R.id.textview1)).getText()).toString());
                    Integer number2 = Integer.parseInt((((EditText) findViewById(R.id.textview2)).getText()).toString());

                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    Integer numberOfClicks = number1 + number2;
                    intent.putExtra("textviewtotal", numberOfClicks);
                    startActivityForResult(intent, 2017);
                }
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
            public void onClick(View view) {
            String pressButton = ((Button) view).getText().toString();
            // incrementeaza numerele
            if (pressButton.equals("Press me!")) {
                Integer newValue = (Integer.parseInt((((EditText) findViewById(R.id.textview1)).getText()).toString())) + 1;
                ((EditText) findViewById(R.id.textview1)).setText(newValue.toString());
            } else {
                Integer newValue = (Integer.parseInt((((EditText) findViewById(R.id.textview2)).getText()).toString())) + 1;
                ((EditText) findViewById(R.id.textview2)).setText(newValue.toString());
            }

            String number1 = (((EditText) findViewById(R.id.textview1)).getText()).toString();
            String number2 = (((EditText) findViewById(R.id.textview2)).getText()).toString();

            // porneste serviciul
            if (Integer.parseInt(number1) + Integer.parseInt(number2) > Constants.NUMBER_OF_CLICKS_THRESHOLD
                && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra(Constants.FIRST_NUMBER, Integer.parseInt(number1));
                intent.putExtra(Constants.SECOND_NUMBER, Integer.parseInt(number2));

                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    // service
    // un ascultator cu difuzare
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Emi", intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    // se opreste serviciul
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    // salveaza valoarea campurilor
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        EditText textView1 = findViewById(R.id.textview1);
        EditText textView2 = findViewById(R.id.textview2);

        savedInstanceState.putString("textview1", textView1.getText().toString());
        savedInstanceState.putString("textview2", textView2.getText().toString());
    }

    // restaureaza valoarea campurilor
    @Override
    protected void onCreate(@SuppressLint("SetTextI18n") Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button press1 = findViewById(R.id.press1);
        press1.setOnClickListener(press);

        Button press2 = findViewById(R.id.press2);
        press2.setOnClickListener(press);

        EditText textView1 = findViewById(R.id.textview1);
        EditText textView2 = findViewById(R.id.textview2);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("textview1")) {
                textView1.setText(savedInstanceState.getString("textview1"));
            }
            if (savedInstanceState.containsKey("textview2")) {
                textView2.setText(savedInstanceState.getString("textview2"));
            }
        }

        // intent

        Button navigate = findViewById(R.id.navigate);
        navigate.setOnClickListener(navigateThere);

        // service
        // filtru pentru acultatorul pentru intentii
        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

    }

    // se afiseaza raspunsul la intoarcerea din activitatea intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 2017) {
            String text = resultCode == RESULT_OK ? "This activity returned with result: RESULT_OK" : "This activity returned with result: RESULT_CANCELED";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }
}
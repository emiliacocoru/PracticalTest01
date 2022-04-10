package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    ButtonClickListener listener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String id = ((Button) view).getText().toString();

            if (id.equals("Ok")) {
                setResult(RESULT_OK, null);
            } else {
                setResult(RESULT_CANCELED, null);
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        EditText totalClicks = findViewById(R.id.textviewtotal);

        // se apeleaza intentia
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("textviewtotal")) {
            int numberOfClicks = intent.getIntExtra("textviewtotal", -1);
            totalClicks.setText(String.valueOf(numberOfClicks));
        }

        Button ok = findViewById(R.id.ok);
        ok.setOnClickListener(listener);

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(listener);
    }
}
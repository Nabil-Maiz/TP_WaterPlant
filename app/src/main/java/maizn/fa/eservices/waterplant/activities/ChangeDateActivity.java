package maizn.fa.eservices.waterplant.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import maizn.fa.eservices.waterplant.R;

public class ChangeDateActivity extends AppCompatActivity {

    CalendarView calendarView;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_date);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setDate(MainActivity.currentDate.getTime());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = new GregorianCalendar(year,month,dayOfMonth);
                date = calendar.getTime();
            }
        });
    }

    public void saveDate(View view){
        MainActivity.currentDate = date;
        Toast.makeText(this,"La date a été sauvegardée",Toast.LENGTH_SHORT).show();
    }
}

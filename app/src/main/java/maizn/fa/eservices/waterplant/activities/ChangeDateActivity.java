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

        // on définit la date au démarrage de calendarView de cette manière
        calendarView.setDate(MainActivity.currentDate.getTime());

        // au click sur une date on stock la valeur selectionnée
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = new GregorianCalendar(year,month,dayOfMonth);
                date = calendar.getTime();
            }
        });
    }

    // cette fonction permet de sauvegarder la date et donner un feedback utilisateur
    public void saveDate(View view){
        MainActivity.currentDate = date;
        Toast.makeText(this,"La date a été sauvegardée",Toast.LENGTH_SHORT).show();
    }
}

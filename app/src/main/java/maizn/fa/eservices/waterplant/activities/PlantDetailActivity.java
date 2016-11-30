package maizn.fa.eservices.waterplant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import maizn.fa.eservices.waterplant.R;
import maizn.fa.eservices.waterplant.entities.DaoSession;
import maizn.fa.eservices.waterplant.entities.Plant;
import maizn.fa.eservices.waterplant.entities.PlantDao;

public class PlantDetailActivity extends AppCompatActivity {

    private Plant plant;
    private PlantDao plantDao;
    private EditText plantName;
    EditText wateringFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        plantDao = daoSession.getPlantDao();

        Intent intent = getIntent();
        Bundle plantBundle = intent.getExtras();
        plant = plantBundle.getParcelable("PLANT_DETAIL");

    }

    @Override
    public void onStart() {
        super.onStart();

        plantName = ((EditText) findViewById(R.id.edit_plant_name));
        wateringFrequency = (EditText) findViewById(R.id.edit_watering_frequency);
        TextView lastWatering = (TextView) findViewById(R.id.text_last_watering_value);

        plantName.setText(plant.getPlantName());
        wateringFrequency.setText(plant.getWateringFrequency() + "");
        lastWatering.setText(plant.getLastWatering().toString());
    }

    public void deletePlant(View view) {

        if (plant != null) {
            plantDao.deleteByKey(plant.getId());
            setResult(RESULT_OK, new Intent());
            finish();
        }
    }

    public void updatePlant(View view) {

        if (plant != null) {
            plantName = ((EditText) findViewById(R.id.edit_plant_name));
            wateringFrequency = (EditText) findViewById(R.id.edit_watering_frequency);

            if (plantName != null && wateringFrequency != null) {
                plant.setPlantName(plantName.getText().toString());
                plant.setWateringFrequency(Integer.parseInt(wateringFrequency.getText().toString()));
                plantDao.update(plant);
                setResult(RESULT_OK, new Intent());
                finish();
            }
        }
    }
}

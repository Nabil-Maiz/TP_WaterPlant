package maizn.fa.eservices.waterplant.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import maizn.fa.eservices.waterplant.R;
import maizn.fa.eservices.waterplant.entities.DaoSession;
import maizn.fa.eservices.waterplant.entities.Plant;
import maizn.fa.eservices.waterplant.entities.PlantDao;

public class AddPlantActivity extends AppCompatActivity {

    EditText editPlantName;
    EditText editWateringFrequency;
    PlantDao plantDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        plantDao = daoSession.getPlantDao();
    }

    public void createPlant(View view) {

        editPlantName = (EditText) findViewById(R.id.edit_add_plant_name);
        editWateringFrequency = (EditText) findViewById(R.id.edit_add_watering_frequency);

        String plantName = editPlantName.getText().toString();
        Integer wateringFrequency = Integer.parseInt(editWateringFrequency.getText().toString());

        Plant plant = new Plant(null, plantName, wateringFrequency, MainActivity.currentDate);
        plantDao.insert(plant);

        Toast.makeText(this, "La plante " + plant.getPlantName() + " a bien été ajoutée.", Toast.LENGTH_SHORT).show();
        finish();
    }
}

package maizn.fa.eservices.waterplant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;

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

        // récupération de la plante envoyée depuis la liste des plantes
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
        TextView nextWatering = (TextView) findViewById(R.id.textView_next_watering_value);

        // on affiche le nom et la fréquence d'arrosage de la plante
        plantName.setText(plant.getPlantName());
        wateringFrequency.setText(plant.getWateringFrequency() + "");

        // on défini le format de la date à afficher
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        lastWatering.setText(dateFormat.format(plant.getLastWatering()));

        // on calcul la date du prochain arrosage et on affiche la date formatée
        LocalDate plantDate = LocalDate.fromDateFields(plant.getLastWatering()).plusDays(plant.getWateringFrequency());
        nextWatering.setText(dateFormat.format(plantDate.toDate()));
    }

    public void deletePlant(View view) {

        if (plant != null) {

            // on supprime la plante
            plantDao.deleteByKey(plant.getId());

            // ce résultat va permettre de faire la mise a jour du côté de la liste
            setResult(RESULT_OK, new Intent());

            //retour utilisateur puis fermeture de l'activité
            Toast.makeText(this,"La plante " + plant.getPlantName()+ " a été supprimée",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void updatePlant(View view) {

        if (plant != null) {

            // on modifie le nom et la fréquence d'arrosage
            plantName = ((EditText) findViewById(R.id.edit_plant_name));
            wateringFrequency = (EditText) findViewById(R.id.edit_watering_frequency);

            if (plantName != null && wateringFrequency != null) {

                // on change les informations si ces dernières ont changées
                if(!plantName.getText().toString().equals(plant.getPlantName()) || Integer.parseInt(wateringFrequency.getText().toString()) != plant.getWateringFrequency()){

                    plant.setPlantName(plantName.getText().toString());
                    plant.setWateringFrequency(Integer.parseInt(wateringFrequency.getText().toString()));

                    // on met a jour la plante
                    plantDao.update(plant);

                    // on indique à la liste que l'operation s'est bien passée
                    setResult(RESULT_OK, new Intent());

                    // puis retour utilisateur et fin de l'activité
                    Toast.makeText(this,"La plante " + plant.getPlantName()+ " a été modifiée",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}

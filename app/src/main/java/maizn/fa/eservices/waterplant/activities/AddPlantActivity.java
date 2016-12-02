package maizn.fa.eservices.waterplant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import maizn.fa.eservices.waterplant.R;
import maizn.fa.eservices.waterplant.entities.DaoSession;
import maizn.fa.eservices.waterplant.entities.Plant;
import maizn.fa.eservices.waterplant.entities.PlantDao;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/*
 * Classe permettant d'ajouter une plante
 */
public class AddPlantActivity extends AppCompatActivity {

    EditText editPlantName;
    EditText editWateringFrequency;
    PlantDao plantDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        //Récupération du DAO
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        plantDao = daoSession.getPlantDao();
    }

    @Override
    public void onBackPressed(){
        Intent intent =new Intent(AddPlantActivity.this,MainActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void createPlant(View view) {

        // on récupère uniquement le nom et la fréquence d'arrosage pour créer une plante
        editPlantName = (EditText) findViewById(R.id.edit_add_plant_name);
        editWateringFrequency = (EditText) findViewById(R.id.edit_add_watering_frequency);

        // on récupère le texte
        String plantName = editPlantName.getText().toString();
        Integer wateringFrequency = Integer.parseInt(editWateringFrequency.getText().toString());

        // on crée la nouvelle plante à jouter et on l'ajoute en base avec un insert
        Plant plant = new Plant(null, plantName, wateringFrequency, MainActivity.currentDate);
        plantDao.insert(plant);

        // retour utilisateur pour indiquer a l'utilistaeur que l'opération s'est bien passée puis fermeture de l'activité
        Toast.makeText(this, "La plante " + plant.getPlantName() + " a bien été ajoutée.", Toast.LENGTH_SHORT).show();
        finish();
    }
}

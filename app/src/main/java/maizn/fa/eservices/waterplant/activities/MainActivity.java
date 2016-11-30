package maizn.fa.eservices.waterplant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

import maizn.fa.eservices.waterplant.R;
import maizn.fa.eservices.waterplant.entities.DaoSession;
import maizn.fa.eservices.waterplant.entities.Plant;
import maizn.fa.eservices.waterplant.entities.PlantDao;

public class MainActivity extends AppCompatActivity {

    private PlantDao plantDao;

    public static Date currentDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        plantDao = daoSession.getPlantDao();
    }

    public void listPlants(View view) {
        startActivity(new Intent(MainActivity.this, ListPlantsActivity.class));
    }

    public void createPlantActivity(View view) {
        startActivity(new Intent(MainActivity.this, AddPlantActivity.class));
    }

    public void populate(View view) {

        String[] plants = {"Acajou", "Achillée", "Amarante", "Camphrier", "Plante du frigo", "Chardon", "Mage-royale", "Feuille-Argent", "Pacifique", "Hélianthèmes ", "Iris", "Julienne"};
        Plant plant;
        Random random = new Random();

        for (int i = 0; i < plants.length; i++) {
            plant = new Plant(null, plants[i], random.nextInt(30) + 1, MainActivity.currentDate);
            plantDao.insert(plant);
        }

        Toast.makeText(this, "Des plantes ont été générés", Toast.LENGTH_SHORT).show();
    }

    public void deleteAllPlants(View view) {
        plantDao.deleteAll();
        Toast.makeText(this, "Toutes les plantes ont été supprimés", Toast.LENGTH_SHORT).show();
    }

    public void changeDate(View view) {
        Intent intent = new Intent(MainActivity.this,ChangeDateActivity.class);
        startActivity(intent);
    }
}

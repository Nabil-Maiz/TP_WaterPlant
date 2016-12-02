package maizn.fa.eservices.waterplant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.LocalDate;

import java.util.List;

import maizn.fa.eservices.waterplant.R;
import maizn.fa.eservices.waterplant.adapter.PlantAdapter;
import maizn.fa.eservices.waterplant.entities.DaoSession;
import maizn.fa.eservices.waterplant.entities.Plant;
import maizn.fa.eservices.waterplant.entities.PlantDao;

public class ListPlantsActivity extends AppCompatActivity {

    static final int PLANT_DETAIL = 1;
    private ListView listView;
    private PlantDao plantDao;
    PlantAdapter plantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plants);

        // récupération du dao
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        plantDao = daoSession.getPlantDao();

        listView = (ListView) findViewById(R.id.list_plants);



        // on charge toutes les plantes de la base
        List<Plant> plants =  plantDao.loadAll();

        // si il n'y a aucune plante dans la base on redirige vers ajouter une plante
        if(plants.isEmpty()){
            startActivity(new Intent(ListPlantsActivity.this,AddPlantActivity.class));
        }

        // initialisation de l'adapter avec une liste contenant toutes les plantes de la base de données
        plantAdapter = new PlantAdapter(this,plants);
        listView.setAdapter(plantAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myElementDetail();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePlants();
    }

    // fonction permettant de définir les comportements lors des clicks sur les éléments de la liste
    private void myElementDetail() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // actions a effectuer au click sur un item de la liste
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //on récupère la plante sélectionée
                Plant plant = (Plant) parent.getAdapter().getItem(position);

                // le bundle va nous permettre d'envoyer l'objet vers une autre activité
                Bundle bundle = new Bundle();
                bundle.putParcelable("PLANT_DETAIL", plant);

                // on crée un intent auquel on ajoute le bundle
                Intent intent = new Intent(ListPlantsActivity.this, PlantDetailActivity.class);
                intent.putExtras(bundle);

                // on envoi le tout a l'activite gérant le détail des plantes
                startActivityForResult(intent, PLANT_DETAIL);
            }
        });

        // un long click va nous servir a arroser la plante
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Plant plant = (Plant) parent.getAdapter().getItem(position);

                // on utilise l'api joda pour manipuler les dates plus facilement
                LocalDate jodaCurrentDate = LocalDate.fromDateFields(MainActivity.currentDate);
                LocalDate jodaNextWateringDate = LocalDate.fromDateFields(plant.getLastWatering()).plusDays(plant.getWateringFrequency());

                // si la plante est déjà arrosée on ne fait rien sinon on l'arrose et on fait un retour utilisateur
                if(jodaCurrentDate.isAfter(jodaNextWateringDate) || jodaCurrentDate.isEqual(jodaNextWateringDate)){
                    plant.setLastWatering(MainActivity.currentDate);
                    Toast.makeText(getApplicationContext(),"La plante a été arrosée",Toast.LENGTH_SHORT).show();
                    updatePlants();
                }
                return true;
            }
        });
    }

    // cette fonction permet de mettre a jour la listView
    public void updatePlants() {
        listView.setAdapter(new PlantAdapter(this, plantDao.loadAll()));
    }

    // après un retour depuis le détail d'une plante on met à jour la listView car on vient soit d'une suppression soit d'une modification
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLANT_DETAIL) {
            if (resultCode == RESULT_OK) {
                updatePlants();
            }
        }
    }
}

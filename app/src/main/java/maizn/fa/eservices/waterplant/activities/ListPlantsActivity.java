package maizn.fa.eservices.waterplant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;

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

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        plantDao = daoSession.getPlantDao();

        listView = (ListView) findViewById(R.id.list_plants);

        plantAdapter = new PlantAdapter(this, plantDao.loadAll());
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

    private void myElementDetail() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Plant plant = (Plant) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("PLANT_DETAIL", plant);
                Intent intent = new Intent(ListPlantsActivity.this, PlantDetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, PLANT_DETAIL);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Plant plant = (Plant) parent.getAdapter().getItem(position);
                plant.setLastWatering(new Date());
                updatePlants();
                return true;
            }
        });
    }

    public void updatePlants() {
        listView.setAdapter(new PlantAdapter(this, plantDao.loadAll()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLANT_DETAIL) {
            if (resultCode == RESULT_OK) {
                updatePlants();
            }
        }
    }
}

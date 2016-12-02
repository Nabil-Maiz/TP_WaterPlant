package maizn.fa.eservices.waterplant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.List;

import maizn.fa.eservices.waterplant.R;
import maizn.fa.eservices.waterplant.activities.MainActivity;
import maizn.fa.eservices.waterplant.entities.Plant;

/*
 * Classe permettant l'affichage de la listView
 */
public class PlantAdapter extends ArrayAdapter<Plant> {

    public PlantAdapter(Context context, List<Plant> plants) {
        super(context, 0, plants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // pour afficher une ligne de notre liste on va indiquer à android comment les afficher avec un layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_plants, parent, false);
        }

        // le PlantViewHolder va nous indiquer quelles informations afficher
        PlantViewHolder plantViewHolder = (PlantViewHolder) convertView.getTag();

        // si il est null on l'initialise
        if (plantViewHolder == null) {
            plantViewHolder = new PlantViewHolder();
            plantViewHolder.plantName = (TextView) convertView.findViewById(R.id.plant_name);
            convertView.setTag(plantViewHolder);
        }

        // on récupère la plante sélectionnée dans la liste
        Plant plant = getItem(position);

        // utilisation de l'api joda pour faire les opérations sur les dates
        LocalDate date = LocalDate.fromDateFields(MainActivity.currentDate);
        LocalDate lastWateringConverted = LocalDate.fromDateFields(plant.getLastWatering()).plusDays(plant.getWateringFrequency());

        /* si la date du dernier arrosage est avant la date courante c'est que la date d'arrosage a été dépassée
         et donc la couleur de fond de la liste sera rouge
         si elle se situe après c'est qu'elle est arrosée et le fond sera vert
         sinon on est la veille ou le jour de l'arrosage et dans ce cas le fond sera orange */
        if (lastWateringConverted.isBefore(date)) {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRedCoquelicot));
        } else if (lastWateringConverted.isAfter(date)) {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBottleGreen));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBurnedOrange));
        }

        // on affiche les informations : nom + couleur
        plantViewHolder.plantName.setText(plant.getPlantName());
        plantViewHolder.plantName.setTextColor(Color.WHITE);
        return convertView;
    }


    private class PlantViewHolder {
        public TextView plantName;
    }
}

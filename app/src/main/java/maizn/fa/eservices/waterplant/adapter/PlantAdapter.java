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


public class PlantAdapter extends ArrayAdapter<Plant> {

    public PlantAdapter(Context context, List<Plant> plants) {
        super(context, 0, plants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_plants, parent, false);
        }

        PlantViewHolder plantViewHolder = (PlantViewHolder) convertView.getTag();
        if (plantViewHolder == null) {
            plantViewHolder = new PlantViewHolder();
            plantViewHolder.plantName = (TextView) convertView.findViewById(R.id.plant_name);
            convertView.setTag(plantViewHolder);
        }

        Plant plant = getItem(position);
        LocalDate date = LocalDate.fromDateFields(MainActivity.currentDate);
        LocalDate lastWateringConverted = LocalDate.fromDateFields(plant.getLastWatering()).plusDays(plant.getWateringFrequency());

        if (lastWateringConverted.isBefore(date)) {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRedCoquelicot));
        } else if (lastWateringConverted.isAfter(date)) {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBottleGreen));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBurnedOrange));
        }

        plantViewHolder.plantName.setText(plant.getPlantName());
        plantViewHolder.plantName.setTextColor(Color.WHITE);
        return convertView;
    }


    private class PlantViewHolder {
        public TextView plantName;
    }
}

package maizn.fa.eservices.waterplant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import maizn.fa.eservices.waterplant.R;
import maizn.fa.eservices.waterplant.entities.Plant;


public class PlantAdapter extends ArrayAdapter<Plant> {

    public PlantAdapter(Context context, List<Plant> plants) {
        super(context, 0,plants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_plants,parent,false);
        }

        PlantViewHolder plantViewHolder = (PlantViewHolder) convertView.getTag();
        if(plantViewHolder == null){
            plantViewHolder = new PlantViewHolder();
            plantViewHolder.plantName = (TextView) convertView.findViewById(R.id.plant_name);
            convertView.setTag(plantViewHolder);
        }

        Plant plant = getItem(position);
        plantViewHolder.plantName.setText(plant.getPlantName());
        return convertView;
    }


    private class PlantViewHolder{
        public TextView plantName;
    }
}

package com.example.germanmemoriserapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DifficultyAdapter extends ArrayAdapter<DifficultyItem> {

    public DifficultyAdapter(Context context, ArrayList<DifficultyItem> difficulties) {
        super(context, 0, difficulties);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.difficulty_spinner_row, parent, false
            );
        }

        ImageView backgroundView = convertView.findViewById(R.id.backgroundImage);
        TextView textView = convertView.findViewById(R.id.difficultyText);

        DifficultyItem currItem = getItem(position);

        if(currItem != null) {
            //backgroundView.setImageResource(currItem.getBackgroundImg());
            textView.setText(currItem.getTitle());
        }

        return convertView;
    }
}

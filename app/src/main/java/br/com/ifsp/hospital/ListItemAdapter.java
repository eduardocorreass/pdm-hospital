package br.com.ifsp.hospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ListItemAdapter extends ArrayAdapter<ListItem> {
    public ListItemAdapter(Context context, List<ListItem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ListItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        if (item != null) {
            String displayText = "Nome: " + item.getName() + "\n\t\t" +
                    "Idade: " + item.getAge() + "\n\t\t" +
                    "Gênero: " + item.getGender() + "\n\t\t" +
                    "Diagnóstico: " + item.getDiagnostic() + "\n\t\t" +
                    "Medicamentos: " + item.getMedicines() + "\n";
            textView.setText(displayText);
        }

        return convertView;
    }
}
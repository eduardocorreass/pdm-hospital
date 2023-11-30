package br.com.ifsp.hospital;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ListItem> itemList;
    private ListItemAdapter adapter;

    private ActivityResultLauncher<Intent> startActivityForResultLauncher;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCreate = findViewById(R.id.btn_create);

        ListView listView = findViewById(R.id.list_view);
        itemList = new ArrayList<>();
        adapter = new ListItemAdapter(this, itemList);
        listView.setAdapter(adapter);

        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivityForResultLauncher.launch(intent);
        });

        startActivityForResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        reloadItemsFromDatabase();
                    }
                }
        );

        reloadItemsFromDatabase();

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            ListItem itemToDelete = itemList.get(position);
            deleteItemFromDatabase(itemToDelete.getId());
            itemList.remove(position);
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    private void reloadItemsFromDatabase() {
        itemList.clear();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("hospital");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ListItem item = snapshot.getValue(ListItem.class);
                    item.setId(snapshot.getKey());
                    itemList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors if needed
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            reloadItemsFromDatabase();
        }
    }

    private void deleteItemFromDatabase(String itemId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("hospital").child(String.valueOf(itemId));
        databaseReference.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Cosulta removida!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao remover!", Toast.LENGTH_SHORT).show();
                });
    }

}
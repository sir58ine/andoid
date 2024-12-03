package isetb.ex.coach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnAdd;
    private RecyclerView recyclerView;
    private ClientDatabase db;
    private ArrayList<Client> listClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recycler);
        listClient = new ArrayList<>();
        db = new ClientDatabase(this);  // Initialize the client database


        int clientCount = db.getClientCount();


        btnAdd.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, add_client.class);
            startActivity(intent);
        });


        if (clientCount == 0) {
            Toast.makeText(this, "Aucun client ajouté!", Toast.LENGTH_SHORT).show();
        } else {
            // Set up the RecyclerView with the client list
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            listClient = db.getAllClients();  // Retrieve all clients
            ClientAdapter adapter = new ClientAdapter(listClient, this);  // Pass context to the adapter
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the client list and RecyclerView on activity resume
        listClient = db.getAllClients();
        ClientAdapter adapter = new ClientAdapter(listClient, this);
        recyclerView.setAdapter(adapter);
    }
}

package isetb.ex.coach;




import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class add_client extends AppCompatActivity {

    Button btn;
    EditText etName, etLastName, etAge, etCIN, etCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);  // Assurez-vous que le layout est bien mis à jour pour les clients

        // Initialisation des champs de saisie
        btn = findViewById(R.id.btn_add_client);
        etName = findViewById(R.id.et_name);
        etLastName = findViewById(R.id.et_last_name);
        etAge = findViewById(R.id.et_age);
        etCIN = findViewById(R.id.et_cin);
        etCountry = findViewById(R.id.et_country);

        // Ajout d'un client à la base de données
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupération des valeurs saisies
                String name = etName.getText().toString();
                String lastName = etLastName.getText().toString();
                String ageStr = etAge.getText().toString();
                String cin = etCIN.getText().toString();
                String country = etCountry.getText().toString();

                // Validation de l'âge (doit être un nombre)
                int age = 0;
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    Toast.makeText( add_client.this, "Veuillez entrer un âge valide.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Création du client
                Client client = new Client(name, lastName, age, cin, country);

                // Appel à la base de données pour ajouter le client
                ClientDatabase db = new ClientDatabase( add_client.this);
                if (db.addClient(client)) {
                    Toast.makeText( add_client.this, "Client ajouté avec succès!", Toast.LENGTH_SHORT).show();
                    finish();  // Retourner à l'écran principal
                } else {
                    Toast.makeText( add_client.this, "Erreur lors de l'ajout du client.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}




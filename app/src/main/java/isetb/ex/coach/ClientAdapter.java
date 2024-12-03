package isetb.ex.coach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private ArrayList<Client> clientList;
    private Context context;
    private ClientDatabase db;

    // Constructor
    public ClientAdapter(ArrayList<Client> clientList, Context context) {
        this.clientList = clientList;
        this.context = context;
        this.db = new ClientDatabase(context); // Initialize database
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_item, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clientList.get(position);

        // Display Name and LastName
        holder.tvName.setText(client.getName());
        holder.tvLastName.setText(client.getLastName());

        // Set up delete button and show details
        holder.deleteButton.setOnClickListener(v -> showDeleteDialog(v, client, position));

        // Handle click to show client details
        holder.itemView.setOnClickListener(v -> showClientDetails(client));
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    // ViewHolder for RecyclerView
    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLastName;
        ImageButton deleteButton;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvLastName = itemView.findViewById(R.id.lastname);
            deleteButton = itemView.findViewById(R.id.btnDelete);  // Initialize delete button
        }
    }

    // Show client details in a dialog
    private void showClientDetails(Client client) {
        // Inflate the custom dialog layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.item_dialog, null);

        // Bind dialog TextViews with client data
        TextView tvName = dialogView.findViewById(R.id.dn);
        TextView tvLastName = dialogView.findViewById(R.id.dln);
        TextView tvAge = dialogView.findViewById(R.id.da);
        TextView tvCin = dialogView.findViewById(R.id.dc);
        TextView tvCountry = dialogView.findViewById(R.id.dct);

        tvName.setText("Name: " + client.getName());
        tvLastName.setText("Last Name: " + client.getLastName());
        tvAge.setText("Age: " + client.getAge());
        tvCin.setText("CIN: " + client.getCin());
        tvCountry.setText("Country: " + client.getCountry());

        // Build and show the AlertDialog
        new AlertDialog.Builder(context)
                .setTitle("Client Details")
                .setView(dialogView)
                .setPositiveButton("Close", null)
                .show();
    }

    private void showDeleteDialog(View view, Client client, int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setTitle("Voulez-vous vraiment supprimer ce client ?");
        alertDialogBuilder.setMessage("Nom: " + client.getName() + " Prénom: " + client.getLastName());

        // Confirm Deletion
        alertDialogBuilder.setPositiveButton("Supprimer", (dialog, which) -> {
            // Remove from database
            boolean isDeleted = db.removeClient(client.getId());
            if (isDeleted) {
                // Remove from the list and notify adapter
                clientList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, clientList.size());
                Toast.makeText(context, "Client supprimé", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erreur lors de la suppression!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        // Cancel Deletion
        alertDialogBuilder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
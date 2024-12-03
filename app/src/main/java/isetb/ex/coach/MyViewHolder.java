package isetb.ex.coach;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    // TextView references for Name and Last Name
    TextView textView1, textView2;
    ImageButton deleteButton;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        // Initialize the TextView references
        textView1 = itemView.findViewById(R.id.name);
        textView2 = itemView.findViewById(R.id.lastname);
    }

    // Method to bind data to the TextViews
    public void bind(Client client) {
        textView1.setText(client.getName());
        textView2.setText(client.getLastName());
    }
}

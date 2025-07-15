package yulia.levitan.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ShoppingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private List<ShoppingItem> currentList = new ArrayList<>();
    private Button btnFinish;
    //private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

       

        recyclerView = findViewById(R.id.shoppingRecyclerView);
        btnFinish = findViewById(R.id.btnFinishShopping);
       // btnBack = findViewById(R.id.btnBack);
        loadShoppingItems();

    }

    private void loadShoppingItems() {
        new Thread(() -> {
            List<ShoppingItem> allItems = AppDatabase.getInstance(this).shoppingDao().getAll();
            runOnUiThread(() -> {
                currentList.clear();
                currentList.addAll(allItems);
                adapter = new ShoppingListAdapter(currentList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
    public void onBack(View view){
        Intent intent = new Intent(ShoppingActivity.this, MainActivityStart.class);
        startActivity(intent);
        finish();
    }

    public void onFinishClicked(View view) {
        float total = 0;
        for (ShoppingItem item : currentList) {
            total += item.getPrice() * item.getQuantity();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Purchase summary :")
                .setMessage("Total payment " + total + " $")
                .setPositiveButton("Sharing", (dialog, which) -> {
                    Toast.makeText(this, "List ready to sent ", Toast.LENGTH_SHORT).show();
                    shareShoppingList();
                })
                .setNegativeButton("Back", null)
                .setNeutralButton("Restart ", (dialog, which) -> loadShoppingItems())
                .show();
    }
    private void shareShoppingList() {
        StringBuilder builder = new StringBuilder();
        builder.append("Items list : \n");

        for (ShoppingItem item : currentList) {
            if (item.getQuantity() > 0) {
                builder.append("- ").append(item.getName())
                        .append(" x").append(item.getQuantity())
                        .append(" (").append(item.getPrice()).append(" $ for one)\n");
            }
        }

        float total = 0;
        for (ShoppingItem item : currentList) {
            total += item.getPrice() * item.getQuantity();
        }
        builder.append("\nTotal payment : ").append(total).append(" $");

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, builder.toString());


        Intent chooser = Intent.createChooser(intent, "Sharing items list :");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        } else {
            Toast.makeText(this, "No suitable app for sharing .", Toast.LENGTH_SHORT).show();
        }
    }



}

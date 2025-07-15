package yulia.levitan.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class ManageItemsActivity extends AppCompatActivity implements ManageItemsAdapter.ItemActionListener {

    private RecyclerView recyclerView;
    private ManageItemsAdapter adapter;
    private EditText nameInput, priceInput;
    private List<ShoppingItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);

        recyclerView = findViewById(R.id.recyclerViewManage);
        nameInput = findViewById(R.id.inputName);
        priceInput = findViewById(R.id.inputPrice);

        loadItems();
    }
    public void onBack(View view){
        Intent intent = new Intent(ManageItemsActivity.this, MainActivityStart.class);
        startActivity(intent);
        finish();
    }
    private void loadItems() {
        new Thread(() -> {
            itemList = AppDatabase.getInstance(this).shoppingDao().getAll();
            runOnUiThread(() -> {
                adapter = new ManageItemsAdapter(itemList, this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

   public void finishManage (View view) {
        finish();
   }
    public void onAddItem(View view) {
        String name = nameInput.getText().toString().trim();
        String priceStr = priceInput.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Enter name and price", Toast.LENGTH_SHORT).show();
            return;
        }

        float price;
        try {
            price = Float.parseFloat(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "price not right", Toast.LENGTH_SHORT).show();
            return;
        }

        ShoppingItem newItem = new ShoppingItem(name, price, 1);

        new Thread(() -> {
            AppDatabase.getInstance(this).shoppingDao().insert(newItem);
            runOnUiThread(() -> {
                nameInput.setText("");
                priceInput.setText("");
                loadItems();
            });
        }).start();
    }

    @Override
    public void onDelete(ShoppingItem item) {
        new Thread(() -> {
            AppDatabase.getInstance(this).shoppingDao().delete(item);
            runOnUiThread(this::loadItems);
        }).start();
    }

    @Override
    public void onPriceUpdated(ShoppingItem item, float newPrice) {
        item.setPrice(newPrice);
        new Thread(() -> AppDatabase.getInstance(this).shoppingDao().update(item)).start();
    }
}
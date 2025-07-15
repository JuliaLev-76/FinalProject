package yulia.levitan.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityStart extends AppCompatActivity {

    private SharedPreferences shp;
    private TextView userNameTextView;
    private ImageView profileImageView;
    private Button btnShopping, btnManageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        userNameTextView = findViewById(R.id.userNameTextView);
        profileImageView = findViewById(R.id.profileImageView);
        btnShopping = findViewById(R.id.btnShopping);
        btnManageItems = findViewById(R.id.btnManageItems);

        shp = getSharedPreferences("appData", MODE_PRIVATE);
        String userName = shp.getString("usern", "user");
        String encodedImage = shp.getString("profileImage", "");

        userNameTextView.setText("Hello , " + userName + "!");

        if (!encodedImage.isEmpty()) {
            byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            profileImageView.setImageBitmap(decodedBitmap);
        }


        btnShopping.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingActivity.class);
            startActivity(intent);
        });


        btnManageItems.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageItemsActivity.class);
            startActivity(intent);
        });
    }
}
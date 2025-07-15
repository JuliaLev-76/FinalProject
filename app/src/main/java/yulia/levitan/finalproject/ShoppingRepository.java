package yulia.levitan.finalproject;

import android.content.Context;

import java.util.List;


public class ShoppingRepository {

    private ShoppingDao shoppingDao;

    public ShoppingRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        shoppingDao = db.shoppingDao();
    }

    public List<ShoppingItem> getAllItems() {
        return shoppingDao.getAll();
    }

    public void insertItem(ShoppingItem item) {
        shoppingDao.insert(item);
    }

    public void deleteItem(ShoppingItem item) {
        shoppingDao.delete(item);
    }

    public void updateItem(ShoppingItem item) {
        shoppingDao.update(item);
    }
}

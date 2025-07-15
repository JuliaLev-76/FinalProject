package yulia.levitan.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingDao {

    @Query("SELECT * FROM ShoppingItem")
    List<ShoppingItem> getAll();

    @Insert
    void insert(ShoppingItem item);

    @Delete
    void delete(ShoppingItem item);

    @Update
    void update(ShoppingItem item);
}

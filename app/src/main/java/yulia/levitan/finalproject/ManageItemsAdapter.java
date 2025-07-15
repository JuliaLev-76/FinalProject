package yulia.levitan.finalproject;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ManageItemsAdapter extends RecyclerView.Adapter<ManageItemsAdapter.ItemViewHolder> {

    public interface ItemActionListener {
        void onDelete(ShoppingItem item);
        void onPriceUpdated(ShoppingItem item, float newPrice);
    }

    private List<ShoppingItem> items;
    private ItemActionListener listener;

    public ManageItemsAdapter(List<ShoppingItem> items, ItemActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ShoppingItem item = items.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(String.valueOf(item.getPrice()));

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(item);
            }
        });

        holder.itemPrice.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    float newPrice = Float.parseFloat(s.toString());
                    if (listener != null) {
                        listener.onPriceUpdated(item, newPrice);
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            @Override public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        EditText itemPrice;
        ImageButton deleteButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.manageItemName);
            itemPrice = itemView.findViewById(R.id.manageItemPrice);
            deleteButton = itemView.findViewById(R.id.btnDelete);
        }
    }
}

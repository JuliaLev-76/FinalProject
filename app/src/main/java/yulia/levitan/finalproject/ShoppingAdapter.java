package yulia.levitan.finalproject;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import yulia.levitan.finalproject.ShoppingItem;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder> {

    private List<ShoppingItem> itemList;

    public ShoppingAdapter(List<ShoppingItem> itemList) {
        this.itemList = itemList;
    }

    public List<ShoppingItem> getItemList() {
        return itemList;
    }

    public void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping, parent, false);
        return new ShoppingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        ShoppingItem item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText("$" + item.getPrice());

        holder.itemQuantity.setText(String.valueOf(item.getQuantity()));


        holder.itemQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int quantity = Integer.parseInt(s.toString());
                    item.setQuantity(quantity);
                } catch (NumberFormatException e) {
                    item.setQuantity(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ShoppingViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice;
        EditText itemQuantity;

        public ShoppingViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}

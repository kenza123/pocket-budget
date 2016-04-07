package fr.ig2i.pocketbudget;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kenzakhamaily on 07/04/2016.
 */
public class SpendingRVAdapter extends RecyclerView.Adapter<SpendingRVAdapter.SpendingViewHolder>{
    List<Spending> spendings;

    public SpendingRVAdapter(List<Spending> spendings) {
        this.spendings = spendings;
    }

    public static class SpendingViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        String tag;
        static TextView spendingName;
        static TextView spendingPrice;
        public SpendingViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_spending);
            spendingName = (TextView) itemView.findViewById(R.id.spending_name);
            spendingPrice = (TextView) itemView.findViewById(R.id.spending_price);
        }
    }

    public void add(Spending item, int position) {
        spendings.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Spending item) {
        int position = spendings.indexOf(item);
        spendings.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public SpendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spending_item, parent, false);
        SpendingViewHolder svh = new SpendingViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(SpendingViewHolder holder, int i) {
        SpendingViewHolder.spendingName.setText(spendings.get(i).name);
        SpendingViewHolder.spendingPrice.setText(spendings.get(i).price.toString() + "€");

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return spendings.size();
    }
}

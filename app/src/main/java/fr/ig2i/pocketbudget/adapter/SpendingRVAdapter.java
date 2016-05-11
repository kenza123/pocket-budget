package fr.ig2i.pocketbudget.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.activity.AddCategory;
import fr.ig2i.pocketbudget.activity.AddSpending;
import fr.ig2i.pocketbudget.model.Category;
import fr.ig2i.pocketbudget.model.Spending;

/**
 * Created by kenzakhamaily on 07/04/2016.
 */
public class SpendingRVAdapter extends RecyclerView.Adapter<SpendingRVAdapter.SpendingViewHolder>{
    List<Spending> spendings;
    private Context context;
    private GlobalState gs;
    private Category category;

    public SpendingRVAdapter(GlobalState gs, Category category) {
        this.gs = gs;
        this.category = category;
        this.spendings = gs.getSpendingService().getSpendingsByCategoryId(category.getId());
    }

    public class SpendingViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        String tag;
        TextView spendingName;
        TextView spendingPrice;
        TextView spendingDate;
        Toolbar toolbar;

        public SpendingViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_spending);
            spendingName = (TextView) itemView.findViewById(R.id.spending_name);
            spendingPrice = (TextView) itemView.findViewById(R.id.spending_price);
            spendingDate = (TextView) itemView.findViewById(R.id.spending_date);
            toolbar = (Toolbar) itemView.findViewById(R.id.card_toolbar2);
            toolbar.inflateMenu(R.menu.card_toolbar);
            toolbar.setOnMenuItemClickListener(
                    new Toolbar.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            int i = getPosition();
                            Spending spending = spendings.get(i);
                            switch (id) {
                                case R.id.edit:
                                    Intent versAddSpending = new Intent(context, AddSpending.class);
                                    versAddSpending.putExtra("spending", spending);
                                    versAddSpending.putExtra("category",category);
                                    context.startActivity(versAddSpending);
                                    break;
                                case R.id.delete:
                                    remove(spending);
                                    break;
                            }
                            return true;
                        }
                    });
        }
    }

    public void remove(Spending item) {
        int position = spendings.indexOf(item);
        spendings.remove(position);
        gs.getSpendingService().deleteSpending(item);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, spendings.size());
    }

    @Override
    public SpendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spending_item, parent, false);
        this.context = parent.getContext();
        SpendingViewHolder svh = new SpendingViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(SpendingViewHolder holder, int i) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        holder.spendingName.setText(spendings.get(i).getLabel());
        holder.spendingPrice.setText(spendings.get(i).getAmount().toString() + "€");
        holder.spendingDate.setText(dateFormatter.format(spendings.get(i).getDate()));

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

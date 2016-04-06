package fr.ig2i.pocketbudget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import junit.framework.Test;

import java.util.List;

/**
 * Created by kenzakhamaily on 24/03/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CategoryViewHolder> {
    List<Category> categories;

    public RVAdapter(List<Category> categories) {
        this.categories = categories;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        static TextView categName;
        static TextView categBudget;
        static ProgressBar cProgress;
        static TextView progressText;
        static TextView spentMoney;
        static Toolbar toolbar;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            categName = (TextView) itemView.findViewById(R.id.categ_name);
            categBudget = (TextView) itemView.findViewById(R.id.categ_budget);
            cProgress = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            progressText = (TextView) itemView.findViewById(R.id.progress_text);
            spentMoney = (TextView) itemView.findViewById(R.id.spent);
            Toolbar toolbar = (Toolbar) itemView.findViewById(R.id.card_toolbar);
            toolbar.inflateMenu(R.menu.card_toolbar);
        }
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoryViewHolder cvh = new CategoryViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int i) {
        CategoryViewHolder.categName.setText(categories.get(i).name);
        CategoryViewHolder.categBudget.setText(categories.get(i).budget.toString() + "€");
        CategoryViewHolder.cProgress.setProgress(categories.get(i).bProgress);
        CategoryViewHolder.progressText.setText(categories.get(i).bProgress+"%");
        double spentM = ((categories.get(i).budget * categories.get(i).bProgress)/100);
        CategoryViewHolder.spentMoney.setText("-"+String.valueOf(spentM)+"€");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}

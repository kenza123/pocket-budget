package fr.ig2i.pocketbudget;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    static List<Category> categories;
    private static Context context;

    public RVAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        static TextView categName;
        static TextView categBudget;
        static ProgressBar cProgress;
        static TextView progressText;
        static TextView spentMoney;
        static Toolbar toolbar;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            cv = (CardView) itemView.findViewById(R.id.cv);
            categName = (TextView) itemView.findViewById(R.id.categ_name);
            categBudget = (TextView) itemView.findViewById(R.id.categ_budget);
            cProgress = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            progressText = (TextView) itemView.findViewById(R.id.progress_text);
            spentMoney = (TextView) itemView.findViewById(R.id.spent);
            toolbar = (Toolbar) itemView.findViewById(R.id.card_toolbar);
            toolbar.inflateMenu(R.menu.card_toolbar);
            toolbar.setOnMenuItemClickListener(
                    new Toolbar.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            switch (id) {
                                case R.id.category_edit:
                                    break;
                                case R.id.category_delete:
                                    break;
                            }
                            return true;
                        }
                    });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getPosition();
                    Category cat = categories.get(i);
                    String tag = "Info";
                    Log.i(tag,"onClick event on category "+cat.getName());
                    Intent versCategorySpendings = new Intent(context,CategorySpendings.class);
                    versCategorySpendings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //versCategorySpendings.putExtra("category_id", i);
                    versCategorySpendings.putExtra("category_name", cat.getName());
                    context.startActivity(versCategorySpendings);
                    //Rediriger vers l'activité CategorySpendings
                    //Pouvoir passer en paramètre le nom de la catégorie et ses dépenses

                }
            });
        }

        /*@Override
        public void onClick(View v) {
            int i = getPosition();
            Category cat = categories.get(i);
            Context context = v.getContext();
            String tag = "Info";
            Log.i(tag,"onClick event on category "+cat.getName());
            Intent versCategorySpendings = new Intent(context,CategorySpendings.class);
            versCategorySpendings.putExtra("category_id", i);
            versCategorySpendings.putExtra("category_name", cat.getName());
            context.startActivity(versCategorySpendings);
            //Rediriger vers l'activité CategorySpendings
            //Pouvoir passer en paramètre le nom de la catégorie et ses dépenses
        }*/
    }

    public void add(Category item, int position) {
        categories.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Category item) {
        int position = categories.indexOf(item);
        categories.remove(position);
        notifyItemRemoved(position);
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

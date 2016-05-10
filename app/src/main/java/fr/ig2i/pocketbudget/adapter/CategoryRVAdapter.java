package fr.ig2i.pocketbudget.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.activity.AddCategory;
import fr.ig2i.pocketbudget.activity.AddEarning;
import fr.ig2i.pocketbudget.activity.CategorySpendings;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.model.Category;

/**
 * Created by kenzakhamaily on 24/03/2016.
 */
public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.CategoryViewHolder> {
    GlobalState gs;
    private List<Category> categories;
    private Context context;

    public CategoryRVAdapter(GlobalState gs) {
        this.gs = gs;
        this.categories = gs.getCategoryService().getAllNotDeletedCategories();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView categName;
        TextView categBudget;
        ProgressBar cProgress;
        TextView progressText;
        TextView spentMoney;
        Toolbar toolbar;

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
                            int i = getPosition();
                            Category category = categories.get(i);
                            switch (id) {
                                case R.id.edit:
                                    Intent versAddCategory = new Intent(context, AddCategory.class);
                                    versAddCategory.putExtra("category",category);
                                    context.startActivity(versAddCategory);
                                    break;
                                case R.id.delete:
                                    remove(category);
                                    break;
                            }
                            return true;
                        }
                    });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getPosition();
                    String position = Integer.toString(i);
                    Category cat = categories.get(i);
                    String tag = "Info";
                    Log.i(tag, "onClick event on category " + cat.getLabel() + " " + i);
                    Intent versCategorySpendings = new Intent(context,CategorySpendings.class);
                    versCategorySpendings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //versCategorySpendings.putExtra("category_id", position);
                    versCategorySpendings.putExtra("category_name", cat.getLabel());
                    context.startActivity(versCategorySpendings);
                    //Rediriger vers l'activité CategorySpendings
                    //Pouvoir passer en paramètre le nom de la catégorie et ses dépenses

                }
            });
        }
    }

    public void add(Category item, int position) {
        categories.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Category item) {
        int position = categories.indexOf(item);
        categories.remove(position);
        gs.getCategoryService().deleteCategory(item);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, categories.size());
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoryViewHolder cvh = new CategoryViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int i) {
        holder.categName.setText(categories.get(i).getLabel());
        holder.categBudget.setText(categories.get(i).getBudget().toString() + "€");
        holder.cProgress.setProgress(categories.get(i).getDepenseProgress());
        holder.progressText.setText(categories.get(i).getDepenseProgress()+"%");
        double spentM = ((categories.get(i).getBudget() * categories.get(i).getDepenseProgress())/100);
        holder.spentMoney.setText("-"+String.valueOf(spentM)+"€");
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
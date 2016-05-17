package fr.ig2i.pocketbudget.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
    private String TAG = "CategoryAdapter";

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
                    Category category = categories.get(i);
                    Intent versCategorySpendings = new Intent(context,CategorySpendings.class);
                    versCategorySpendings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    versCategorySpendings.putExtra("category", category);
                    context.startActivity(versCategorySpendings);
                }
            });
        }
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
        Double totalSpendings = gs.getSpendingService().getTotalSpendingsByCategoryID(categories.get(i).getId());
        Double budget = categories.get(i).getBudget();
        Double warningTreshold = categories.get(i).getWarningThreshold();
        Double prog = (totalSpendings * 100) / budget;
        int progress = (int) Math.ceil(prog);
        Resources resources = context.getResources();
        Drawable drawable;

        holder.categName.setText(categories.get(i).getLabel());
        holder.categBudget.setText(budget.toString() + "€");
        holder.spentMoney.setText("-" + String.valueOf(totalSpendings) + "€");

        if(totalSpendings > budget){
            Log.i(TAG, "totalSpendings > budget");
            holder.cProgress.setProgress(100);
            holder.progressText.setText("+100%");
            holder.progressText.setTextColor(Color.parseColor("#D70000"));
            drawable = resources.getDrawable(R.drawable.progress_alert);
        } else if(totalSpendings >= warningTreshold) {
            Log.i(TAG, "totalSpendings >= warningTreshold");
            holder.cProgress.setProgress(progress);
            holder.progressText.setText(progress + "%");
            holder.progressText.setTextColor(Color.parseColor("#FF8F00"));
            drawable = resources.getDrawable(R.drawable.progress_warning);
        } else {
            Log.i(TAG, "Normal");
            holder.cProgress.setProgress(progress);
            holder.progressText.setText(progress + "%");
            holder.progressText.setTextColor(Color.parseColor("#919191"));
            drawable = resources.getDrawable(R.drawable.progress);
        }
        holder.cProgress.setProgressDrawable(drawable);
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

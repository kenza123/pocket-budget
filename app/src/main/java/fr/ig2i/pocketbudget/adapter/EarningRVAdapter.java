package fr.ig2i.pocketbudget.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.activity.AddEarning;
import fr.ig2i.pocketbudget.activity.Earnings;
import fr.ig2i.pocketbudget.model.Earning;
import fr.ig2i.pocketbudget.model.Spending;
import fr.ig2i.pocketbudget.service.EarningService;

/**
 * Created by kenzakhamaily on 09/04/2016.
 */
public class EarningRVAdapter extends RecyclerView.Adapter<EarningRVAdapter.EarningViewHolder> {
    List<Earning> earnings;
    private Context context;

    public EarningRVAdapter(List<Earning> earnings, Context context) {
        this.earnings = earnings;
        this.context = context;
    }

    public class EarningViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        String tag;
        TextView earningName;
        TextView earningAmount;
        Toolbar toolbar;

        public EarningViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_earning);
            earningName = (TextView) itemView.findViewById(R.id.earning_name);
            earningAmount = (TextView) itemView.findViewById(R.id.earning_amount);
            toolbar = (Toolbar) itemView.findViewById(R.id.card_toolbar3);
            toolbar.inflateMenu(R.menu.card_toolbar);
            toolbar.setOnMenuItemClickListener(
                    new Toolbar.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            int i = getPosition();
                            Earning earning = earnings.get(i);
                            switch (id) {
                                case R.id.edit:
                                    Intent versAddEarning = new Intent(context, AddEarning.class);
                                    versAddEarning.putExtra("earning",earning);
                                    context.startActivity(versAddEarning);
                                    break;
                                case R.id.delete:
                                    remove(earning);
                                    break;
                            }
                            return true;
                        }
            });
        }
    }

    public void remove(Earning item) {
        //show popup
        int position = earnings.indexOf(item);
        earnings.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public EarningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.earning_item, parent, false);
        EarningViewHolder evh = new EarningViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(EarningViewHolder holder, int i) {
        holder.earningName.setText(earnings.get(i).getLabel());
        holder.earningAmount.setText(earnings.get(i).getAmount().toString() + "€");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return earnings.size();
    }
}

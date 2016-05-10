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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.activity.AddEarning;
import fr.ig2i.pocketbudget.model.Earning;

/**
 * Created by kenzakhamaily on 09/04/2016.
 */
public class EarningRVAdapter extends RecyclerView.Adapter<EarningRVAdapter.EarningViewHolder> {
    List<Earning> earnings;
    private Context context;
    private GlobalState gs;
    String TAG = "EarningRVAdapter";

    public EarningRVAdapter(GlobalState gs) {
        this.gs = gs;
        this.earnings = gs.getEarningService().getAllEarningsOfTheMonth();
    }

    public class EarningViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView earningName;
        TextView earningAmount;
        TextView earningDate;
        Toolbar toolbar;

        public EarningViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_earning);
            earningName = (TextView) itemView.findViewById(R.id.earning_name);
            earningAmount = (TextView) itemView.findViewById(R.id.earning_amount);
            earningDate = (TextView) itemView.findViewById(R.id.earning_date);
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
        int position = earnings.indexOf(item);
        earnings.remove(position);
        gs.getEarningService().deleteEarning(item);
        notifyItemRemoved(position);
    }

    @Override
    public EarningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.earning_item, parent, false);
        this.context = parent.getContext();
        EarningViewHolder evh = new EarningViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(EarningViewHolder holder, int i) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        holder.earningName.setText(earnings.get(i).getLabel());
        holder.earningAmount.setText(earnings.get(i).getAmount().toString() + "€");
        holder.earningDate.setText(dateFormatter.format(earnings.get(i).getDate()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return this.earnings.size();
    }
}

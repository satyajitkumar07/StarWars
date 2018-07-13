package com.gami.starwars.view.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gami.starwars.R;
import com.gami.starwars.listener.ActionListenerInterface;
import com.gami.starwars.model.StarWarWarriorsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WarriorsListScreenAdapter extends RecyclerView.Adapter<WarriorsListScreenAdapter.WarriorsListViewHolder>{

    private List<StarWarWarriorsModel> starWarWarriorsList;
    private ActionListenerInterface listenerInterfaceInstance;

    @Override
    public WarriorsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.warrior_list_item_layout,parent,false);
        // return viewholder object
        return new WarriorsListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(WarriorsListViewHolder holder, final int position) {
        final StarWarWarriorsModel data = starWarWarriorsList.get(position);
        if(data.getName()!=null && data.getName().trim().length()>0){
            holder.name.setText(data.getName());
            holder.cardLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerInterfaceInstance.getActionListener(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return starWarWarriorsList==null?0:starWarWarriorsList.size();
    }

    public void setStarWarWarriorsList(List<StarWarWarriorsModel> starWarWarriorsList) {
        this.starWarWarriorsList = starWarWarriorsList;
    }

    public void setListenerInterfaceInstance(ActionListenerInterface listenerInterfaceInstance) {
        this.listenerInterfaceInstance = listenerInterfaceInstance;
    }

    class WarriorsListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.main_layout)
        CardView cardLayout;
        public WarriorsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

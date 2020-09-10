package com.genesis.apps.ui.view.listview.test;


import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class RecyclerDiffCallback<T> extends DiffUtil.Callback {
    private DiffCallbackInterface diffCallbackInterface;
    private List<T> oldListItemViewModels;
    private List<T> newListItemViewModels;

    public RecyclerDiffCallback(
            List<T> oldListItemViewModels,
            List<T> newListItemViewModels,
            DiffCallbackInterface diffCallbackInterface) {
        this.oldListItemViewModels = oldListItemViewModels;
        this.newListItemViewModels = newListItemViewModels;
        this.diffCallbackInterface = diffCallbackInterface;
    }

    @Override
    public int getOldListSize() {
        return oldListItemViewModels.size();
    }

    @Override
    public int getNewListSize() {
        return newListItemViewModels.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldListItemViewModels.get(oldItemPosition);
        T newItem = newListItemViewModels.get(newItemPosition);

        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldListItemViewModels.get(oldItemPosition);
        T newItem = newListItemViewModels.get(newItemPosition);

//        if (!oldItem.getTitle().equals(newItem.getTitle()))
//            return false;
//        if (!oldItem.getContent().equals(newItem.getContent()))
//            return false;

        if(diffCallbackInterface!=null){
            return diffCallbackInterface.areContentsTheSame(oldItem,newItem);
        }else{
            return true;
        }
    }

}
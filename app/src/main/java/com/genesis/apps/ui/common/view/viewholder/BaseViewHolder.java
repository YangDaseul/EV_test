package com.genesis.apps.ui.common.view.viewholder;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<D,B> extends RecyclerView.ViewHolder{

    private final B binding;
    private Context context;
    public BaseViewHolder(View itemView) {
        super(itemView);
        binding = (B) DataBindingUtil.bind(itemView);
        setContext(itemView.getContext());
    }

//    public abstract int getLayout();

    public abstract void onBindView(D item);
    public abstract void onBindView(D item, int pos);
    public abstract void onBindView(D item, int pos, SparseBooleanArray selectedItems);

    public B getBinding() {
        return binding;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

//    public void changeVisibility(View targetView, final boolean isExpanded) {
//        if (isExpanded) {
//            targetView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            targetView.requestLayout();
//        }
//
//        if (targetView.getVisibility() == View.VISIBLE && isExpanded)
//            return;
//
//        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
//        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, targetView.getHeight()) : ValueAnimator.ofInt(targetView.getHeight(), 0);
//        // Animation이 실행되는 시간, n/1000초
//        va.setDuration(500);
//        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // imageView의 높이 변경
//                targetView.getLayoutParams().height = (int) animation.getAnimatedValue();
//                targetView.requestLayout();
//                // imageView가 실제로 사라지게하는 부분
//                targetView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//            }
//        });
//        // Animation start
//        va.start();
//    }
    
}

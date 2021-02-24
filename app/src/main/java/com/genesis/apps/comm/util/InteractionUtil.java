package com.genesis.apps.comm.util;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import javax.annotation.Nullable;

public class InteractionUtil {
    /**
     * 2019-07-25 park
     * View visible 시 expand 애니메이션 효과
     *
     * @param v          대상 view 객체
     * @param scrollView 대상 객체에 대한 애니메이션 재생하는동안 스크롤을 못 하게 하고싶은 뷰
     */
    private final static long UNIT_DURATION_TWO=2L;
    public static void expand(final View v, @Nullable final View scrollView) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(v.getLayoutParams().height!=ViewGroup.LayoutParams.WRAP_CONTENT) {
                    v.getLayoutParams().height = (interpolatedTime == 1) ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                    Log.v("expandtest", "expand:" + v.getLayoutParams().height);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if (scrollView != null) {
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //스크롤 막음
                    scrollView.setOnTouchListener((v1, event) -> true);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //스크롤 살림
                    scrollView.setOnTouchListener(null);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    //do nothing
                }
            });
        }

        a.setDuration(((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density))*UNIT_DURATION_TWO);
        v.startAnimation(a);
    }


    public static void expand2(final View v, @Nullable Runnable runnable) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = (interpolatedTime == 1) ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //스크롤 막음
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    new Handler().postDelayed(runnable, 100);
//                    runnable.run();
                    //스크롤 살림
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    //do nothing
                }
            });

        a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    /**
     * 2019-07-25 park
     * View gone 시 collapse 애니메이션 효과
     *
     * @param v          대상 view 객체
     * @param scrollView 대상 객체에 대한 애니메이션 재생하는동안 스크롤을 못 하게 하고싶은 뷰
     */
    public static void collapse(final View v, @Nullable final View scrollView) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if (scrollView != null) {
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //스크롤 막음
                    scrollView.setOnTouchListener((v1, event) -> true);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //스크롤 살림
                    scrollView.setOnTouchListener(null);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    //do nothing
                }
            });
        }

        a.setDuration( ((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density)));
        v.startAnimation(a);
    }

    public static void collapse(final View v, @Nullable final View scrollView, int duration) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if (scrollView != null) {
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //스크롤 막음
                    scrollView.setOnTouchListener((v1, event) -> true);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //스크롤 살림
                    scrollView.setOnTouchListener(null);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    //do nothing
                }
            });
        }

        a.setDuration(duration);
        v.startAnimation(a);
    }
}

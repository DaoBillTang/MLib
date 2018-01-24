package com.daotangbill.exlib.commons.view;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * project com.daotangbill.exlib.commons.view
 * Created by Bill on 2018/1/24.
 * email: tangbakzi@daotangbill.uu.me
 *
 * @author Bill
 * @version 1.0
 * @description
 */

public abstract class BaseAdapter<T, V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {
    /**
     * 用于保存修改之前的数据源的副本
     */
    protected final List<T> temp;
    /**
     * 数据源
     */
    protected final List<T> datas;

    public BaseAdapter(List<T> datas) {
        this.datas = datas;
        temp = new ArrayList<>(datas);
    }

    private final DiffUtil.Callback diffCallBack = new DiffUtil.Callback() {
        /**
         * 旧的数据源的大小
         */
        @Override
        public int getOldListSize() {
            return temp.size();
        }

        /**
         * 新的数据源的大小
         */
        @Override
        public int getNewListSize() {
            return datas.size();
        }

        /**
         * Called by the DiffUtil to decide whether two object represent the same Item.
         * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
         * For example, if your items have unique ids, this method should check their id equality.
         * 例如，如果你的Item有唯一的id字段，这个方法就 判断id是否相等。
         * 本例判断name字段是否一致
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list
         * @return True if the two items represent the same object or false if they are different.
         */
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return BaseAdapter.this.areItemsTheSame(temp.get(oldItemPosition), datas.get(newItemPosition));
        }

        /**
         * Called by the DiffUtil when it wants to check whether two items have the same data.
         * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
         * DiffUtil uses this information to detect if the contents of an item has changed.
         * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
         * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
         * DiffUtil 用这个方法替代equals方法去检查是否相等。
         * so that you can change its behavior depending on your UI.
         * 所以你可以根据你的UI去改变它的返回值
         * For example, if you are using DiffUtil with a
         * {@link android.support.v7.widget.RecyclerView.Adapter RecyclerView.Adapter}, you should
         * return whether the items' visual representations are the same.
         * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
         * This method is called only if {@link #areItemsTheSame(int, int)} returns
         * {@code true} for these items.
         * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list which replaces the
         *                        oldItem
         * @return True if the contents of the items are the same or false if they are different.
         */
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return BaseAdapter.this.areContentsTheSame(temp.get(oldItemPosition), datas.get(newItemPosition));
        }

        /**
         * When {@link #areItemsTheSame(int, int)} returns {@code true} for two items and
         * {@link #areContentsTheSame(int, int)} returns false for them, DiffUtil
         * calls this method to get a payload about the change.
         *
         * 当{@link #areItemsTheSame(int, int)} 返回true，且{@link #areContentsTheSame(int, int)} 返回false时，DiffUtils会回调此方法，
         * 去得到这个Item（有哪些）改变的payload。
         *
         * For example, if you are using DiffUtil with {@link RecyclerView}, you can return the
         * particular field that changed in the item and your
         * {@link android.support.v7.widget.RecyclerView.ItemAnimator ItemAnimator} can use that
         * information to run the correct animation.
         *
         * 例如，如果你用RecyclerView配合DiffUtils，你可以返回  这个Item改变的那些字段，
         * {@link android.support.v7.widget.RecyclerView.ItemAnimator ItemAnimator} 可以用那些信息去执行正确的动画
         *
         * Default implementation returns {@code null}.\
         * 默认的实现是返回null
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list
         * @return A payload object that represents the change between the two items.
         * 返回 一个 代表着新老item的改变内容的 payload对象，
         */
        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return BaseAdapter.this.getChangePayload(oldItemPosition, newItemPosition);
        }
    };

    /**
     * 该方法用于判断两个 Object 是否是相同的 Item，比如有唯一标识的时候应该比较唯一标识是否相等
     */
    protected abstract boolean areItemsTheSame(T oldItem, T newItem);

    /**
     * 默认情况下，内容不相同的 itme 都不显示同一个界面
     *
     * @param oldItem
     * @param newItem
     * @return
     */
    protected boolean areContentsTheSame(T oldItem, T newItem) {
        return false;
    }

    /**
     * 定向刷新中的部分更新
     * 效率最高
     * 只是没有了ItemChange的白光一闪动画，（反正我也觉得不太重要）
     * TestBean oldBean = mOldDatas.get(oldItemPosition);
     * TestBean newBean = mNewDatas.get(newItemPosition);
     * //这里就不用比较核心字段了,一定相等
     * Bundle payload = new Bundle();
     * if (!oldBean.getDesc().equals(newBean.getDesc())) {
     * payload.putString("KEY_DESC", newBean.getDesc());
     * }
     * if (oldBean.getPic() != newBean.getPic()) {
     * payload.putInt("KEY_PIC", newBean.getPic());
     * }
     * if (payload.size() == 0)//如果没有变化 就传空
     * return null;
     * return payload;//
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    protected Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return null;
    }

    /**
     * 定点部分刷新调用的方法
     *
     * @param holder
     * @param position
     * @param payloads
     */
    @Override
    public void onBindViewHolder(V holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void notifyDiff() {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack);
        diffResult.dispatchUpdatesTo(this);
        // 通知刷新了之后，要更新副本数据到最新
        temp.clear();
        temp.addAll(datas);
    }
}
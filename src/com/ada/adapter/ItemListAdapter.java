/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ada.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * @author Administrator
 *
 * @param <I> 实体类
 */
public abstract class ItemListAdapter<ItemType> extends BaseAdapter {
    /**
     * 数据集合
     */
	protected LinkedList<ItemType> datas;

	public ItemListAdapter(Context context) {
		datas = new LinkedList<ItemType>();
		this.context = context;
	}

	protected Context context;

	@Override
	public int getCount() {
		return datas.size();
	}
    /**
     * 清楚数据
     */
	public void clear() {
		datas.clear();
	}
	/**
     * 获取某一位置的数据
     * @param position 位置
     * @return
     */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}
    /**
     * 获取某一位置的数据
     * @param position 位置
     * @return
     */
	public ItemType getData(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}
	public ItemType getFirst() {
		return datas.getFirst();
	}
	public ItemType getLast() {
		return datas.getLast();
	}
	public boolean add(ItemType item) {
		return datas.add(item);
	}
    /**
     * 添加数据带头部
     * @param item 实体数据
     */
	public boolean addFirst(ItemType item) {
		datas.addFirst(item);
		return true;
	}
    /**
     * 添加数据带尾部
     * @param item 实体数据
     */
	public boolean addLast(ItemType item) {
		datas.addLast(item);
		return true;
	}
    /**
     * 添加数据带尾部
     * @param item 实体数据
     */
	public boolean addAll(List<ItemType> items) {
		return datas.addAll(items);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	 /**
     * Updates a single item in the adapter using isSame() from SilkComparable. Once the filter finds the item, the loop is broken
     * so you cannot update multiple items with a single call.
     * <p/>
     * If the item is not found, it will be added to the adapter.
     *
     * @return True if the item was updated.
     */
    public boolean update(ItemType toUpdate) {
        return update(toUpdate, true);
    }

    /**
     * Updates a single item in the adapter using isSame() from SilkComparable. Once the filter finds the item, the loop is broken
     * so you cannot update multiple items with a single call.
     *
     * @param addIfNotFound Whether or not the item will be added if it's not found.
     * @return True if the item was updated or added.
     */
    public boolean update(ItemType toUpdate, boolean addIfNotFound) {
        boolean found = false;
        for (int i = 0; i < getCount(); i++) {
            ItemType item = getData(i);
            if (toUpdate.equals(item)) {
            	datas.set(i, toUpdate);
                found = true;
                break;
            }
        }
        if (found) {
            notifyDataSetChanged();
            return true;
        } else if (addIfNotFound) {
            add(toUpdate);
            return true;
        }
        return false;
    }
    /**
     * Sets the items in the adapter (clears any previous ones before adding) and notifies the attached ListView.
     */
    public final void set(ItemType[] toSet) {
        set(new ArrayList<ItemType>(Arrays.asList(toSet)));
    }
    private boolean isChanged = false;
    /**
     * Sets the items in the adapter (clears any previous ones before adding) and notifies the attached ListView.
     */
    public final void set(List<ItemType> toSet) {
        isChanged = true;
        this.datas.clear();
        for (ItemType item : toSet) add(item);
    }

    /**
     * Checks whether or not the adapter contains an item based on the adapter's inherited Filter.
     */
    public final boolean contains(ItemType item) {
        for (int i = 0; i < getCount(); i++) {
            ItemType curItem = getData(i);
            if (item.equals(curItem)) return true;
        }
        return false;
    }

    /**
     * Removes an item from the list by its index.
     */
    public void remove(int index) {
        isChanged = true;
        this.datas.remove(index);
        notifyDataSetChanged();
    }

    /**
     * Removes a single item in the adapter using isSame() from SilkComparable. Once the filter finds the item, the loop is broken
     * so you cannot remove multiple items with a single call.
     */
    public void remove(ItemType toRemove) {
        for (int i = 0; i < getCount(); i++) {
            ItemType item = getData(i);
            if (toRemove.equals(item)) {
                this.remove(i);
                break;
            }
        }
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			ItemType item = getData(position);
			view = creatView(position, item);
		} else {
			ItemType item = getData(position);
			view = updateView(position, view, item);
		}
		otherwork(position,view);
		return view;
	}
	 /**
     * Resets the changed state of the adapter, indicating that the adapter has not been changed. Every call
     * to a mutator method (e.g. add, set, remove, clear) will set it back to true.
     */
    public void resetChanged() {
        isChanged = false;
    }

    /**
     * Marks the adapter as changed.
     */
    public void markChanged() {
        isChanged = true;
    }

    /**
     * Gets whether or not the adapter has been changed since the last time {#resetChanged} was called.
     */
    public final boolean isChanged() {
        return isChanged;
    }
	protected  void otherwork(int position, View view){}
	/**
	 * 更新视图
	 * @param position 位置
	 * @param view 视图
	 * @param item 数据实体
	 * @return
	 */
	protected abstract View updateView(int position, View view, ItemType item);
	/**
	 * 创建视图
	 * @param position 位置
	 * @param item 数据实体
	 * @return
	 */
	protected abstract View creatView(int position,ItemType item);
}

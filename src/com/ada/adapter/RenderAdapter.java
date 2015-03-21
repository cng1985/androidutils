package com.ada.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.ada.utils.LogUtils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RenderAdapter<ItemType> extends BaseAdapter {

	protected Context context;
	RenderBuiler<ItemType> builer;
	/**
	 * 数据集合
	 */
	protected LinkedList<ItemType> datas;

	public RenderAdapter(Context context, RenderBuiler<ItemType> builer) {
		datas = new LinkedList<ItemType>();
		this.context = context;
		this.builer = builer;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
	 * 
	 * @param position
	 *            位置
	 * @return
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	/**
	 * 获取某一位置的数据
	 * 
	 * @param position
	 *            位置
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
	 * 
	 * @param item
	 *            实体数据
	 */
	public boolean addFirst(ItemType item) {
		datas.addFirst(item);
		return true;
	}

	/**
	 * 添加数据带尾部
	 * 
	 * @param item
	 *            实体数据
	 */
	public boolean addLast(ItemType item) {
		datas.addLast(item);
		return true;
	}

	/**
	 * 添加数据带尾部
	 * 
	 * @param item
	 *            实体数据
	 */
	public boolean addAll(List<ItemType> items) {
		return datas.addAll(items);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Updates a single item in the adapter using isSame() from SilkComparable.
	 * Once the filter finds the item, the loop is broken so you cannot update
	 * multiple items with a single call.
	 * <p/>
	 * If the item is not found, it will be added to the adapter.
	 * 
	 * @return True if the item was updated.
	 */
	public boolean update(ItemType toUpdate) {
		return update(toUpdate, true);
	}

	/**
	 * Updates a single item in the adapter using isSame() from SilkComparable.
	 * Once the filter finds the item, the loop is broken so you cannot update
	 * multiple items with a single call.
	 * 
	 * @param addIfNotFound
	 *            Whether or not the item will be added if it's not found.
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
	 * Sets the items in the adapter (clears any previous ones before adding)
	 * and notifies the attached ListView.
	 */
	public final void set(ItemType[] toSet) {
		set(new ArrayList<ItemType>(Arrays.asList(toSet)));
	}

	private boolean isChanged = false;

	/**
	 * Sets the items in the adapter (clears any previous ones before adding)
	 * and notifies the attached ListView.
	 */
	public final void set(List<ItemType> toSet) {
		isChanged = true;
		this.datas.clear();
		for (ItemType item : toSet)
			add(item);
	}

	/**
	 * Checks whether or not the adapter contains an item based on the adapter's
	 * inherited Filter.
	 */
	public final boolean contains(ItemType item) {
		for (int i = 0; i < getCount(); i++) {
			ItemType curItem = getData(i);
			if (item.equals(curItem))
				return true;
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
	 * Removes a single item in the adapter using isSame() from SilkComparable.
	 * Once the filter finds the item, the loop is broken so you cannot remove
	 * multiple items with a single call.
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
			LogUtils.i("ada", "creatview");
			RenderBuiler<ItemType> back=builer.copy();
			ItemType content = getData(position);
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			back.onCreate(content, layoutInflater, parent);
			back.render();
			view = back.getRootView();
		} else {
			LogUtils.i("ada", "updateview");
			ItemType content = getData(position);
			RenderBuiler<ItemType> builer = recycle(convertView, content);
			builer.setContent(content);
			builer.render();
			view = builer.getRootView();
		}
		return view;
	}

	private RenderBuiler<ItemType> recycle(View convertView, ItemType content) {
		@SuppressWarnings("unchecked")
		RenderBuiler<ItemType> renderer = (RenderBuiler<ItemType>) convertView
				.getTag();
		renderer.onRecycle(content);
		renderer.setUpView(convertView);
		renderer.hookListeners(convertView);
		return renderer;
	}
}

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

import java.util.LinkedList;

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
public abstract class ItemListHolderAdapter<I> extends BaseAdapter {
    /**
     * 数据集合
     */
	protected LinkedList<I> datas;

	public ItemListHolderAdapter(Context context) {
		datas = new LinkedList<I>();
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
	public I getData(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}
	public I getFirst() {
		return datas.getFirst();
	}
	public I getLast() {
		return datas.getLast();
	}
	public void add(I item) {
		datas.add(item);
	}
    /**
     * 添加数据带头部
     * @param item 实体数据
     */
	public void addFirst(I item) {
		datas.addFirst(item);
	}
    /**
     * 添加数据带尾部
     * @param item 实体数据
     */
	public void addLast(I item) {
		datas.addLast(item);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		///View view = convertView != null ? (View) convertView.getTag() : null;
		View view = convertView;
		if (view == null) {
			I item = getData(position);
			view = creatView(position, item);
			ViewHolder holder=new ViewHolder();
			holder.view=view;
			view.setTag(holder);
		} else {
			@SuppressWarnings("unchecked")
			ViewHolder holder=(ViewHolder) view.getTag();
			I item = getData(position);
			view = updateView(position, holder.view, item);
		}
		otherwork(position,view);
		return view;
	}
	class ViewHolder {
		View view;
	}
	
	
	
	protected abstract void otherwork(int position, View view);
	/**
	 * 更新视图
	 * @param position 位置
	 * @param view 视图
	 * @param item 数据实体
	 * @return
	 */
	protected abstract View updateView(int position, View view, I item);
	/**
	 * 创建视图
	 * @param position 位置
	 * @param item 数据实体
	 * @return
	 */
	protected abstract View creatView(int position, I item);
}

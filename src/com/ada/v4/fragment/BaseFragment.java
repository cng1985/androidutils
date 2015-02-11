package com.ada.v4.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	public abstract int getlayout();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(getlayout(), container, false);
		return rootView;
	}

	/**
	 * 初始化方法
	 * 
	 * @param savedInstanceState
	 */
	public abstract void create(Bundle savedInstanceState);

	private View view;

	/**
	 * 根据id查找控件
	 * 
	 * @param id
	 * @return
	 */
	public final View findViewById(int id) {
		return view.findViewById(id);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (getView() != null) {
			view = getView();
			create(savedInstanceState);
		}
	}
}

package com.ada.v4.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class V4PagerAdapter   extends FragmentPagerAdapter {

	public V4PagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public List<Fragment> fragments = new ArrayList<Fragment>();
	public List<String> titles = new ArrayList<String>();


	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}

	public void add(String title, Fragment fragment) {
		fragments.add(fragment);
		titles.add(title);
	}
}

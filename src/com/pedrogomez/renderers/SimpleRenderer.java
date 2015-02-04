package com.pedrogomez.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class SimpleRenderer<T> extends Renderer<T> {

	public abstract int getlayout();

	@Override
	protected View inflate(LayoutInflater inflater, ViewGroup parent) {
		View inflatedView = inflater.inflate(getlayout(), parent, false);
		return inflatedView;
	}
}

package com.pedrogomez.renderers;

import android.view.LayoutInflater;

public class SimpleRendererAdapter<T> extends RendererAdapter<T> {

	public SimpleRendererAdapter(LayoutInflater layoutInflater,
			RendererBuilder rendererBuilder) {
		super(layoutInflater, rendererBuilder, new ListAdapteeCollection<T>());
	}

}

package com.ada.adapter;

import com.pedrogomez.renderers.Renderer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class RenderBuiler<ItemType> implements Cloneable {

	private View rootView;
	private ItemType content;
	private Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setContent(ItemType content) {
		this.content = content;
	}

	public RenderBuiler(Context context) {
		this.context = context;
	}

	public abstract void render();

	public void onCreate(ItemType content, LayoutInflater layoutInflater,
			ViewGroup parent) {
		this.content = content;
		this.rootView = inflate(layoutInflater, parent);
		if (rootView == null) {

		}
		this.rootView.setTag(this);
		setUpView(rootView);
		hookListeners(rootView);
	}

	/**
	 * Map all the widgets from the rootView to renderer members.
	 */
	protected abstract void setUpView(View rootView);

	/**
	 * Set all the listeners to members mapped in setUpView method.
	 */
	protected abstract void hookListeners(View rootView);

	/**
	 * Inflate renderer layout. The view inflated can't be null. If this method
	 * returns a null view a NotInflateViewException will be thrown.
	 * 
	 * @param inflater
	 *            LayoutInflater service to inflate.
	 * @return View with the inflated layout.
	 */
	protected View inflate(LayoutInflater inflater, ViewGroup parent) {

		return LayoutInflater.from(context).inflate(getlayoutid(), parent,
				false);
	}

	protected abstract int getlayoutid();

	public void onRecycle(ItemType content) {
		this.content = content;
	}

	/**
	 * Method to access the root view rendered in the renderer.
	 * 
	 * @return top view in the view hierarchy of one renderer.
	 */
	public View getRootView() {
		return rootView;
	}

	/**
	 * @return the content stored in the renderer.
	 */
	protected final ItemType getContent() {
		return content;
	}

	 RenderBuiler<ItemType>  copy() {
		 RenderBuiler<ItemType>  copy = null;
		try {
			copy = ( RenderBuiler<ItemType> ) this.clone();
		} catch (CloneNotSupportedException e) {
			Log.e("Renderer", "All your renderers should be clonables.");
		}
		return copy;
	}
}

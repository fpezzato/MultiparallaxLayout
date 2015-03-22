package it.francescopezzato.android.multiparallaxlayout.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import rx.android.internal.Preconditions;

/**
 * Created by francesco on 22/03/2015.
 */
public abstract class BindableAdapter<T> extends BaseAdapter  {

	Context mContext;

	public abstract View newView(LayoutInflater inflater, int position, ViewGroup container);

	public abstract void bindView(T item, Context context, View view);

	public BindableAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = newView(LayoutInflater.from(mContext), position, parent);
			Preconditions.checkState(convertView != null, "The inflated view cannot be null.");
		}
		bindView(getItem(position) ,mContext, convertView);
		return convertView;

	}

	@Override
	public abstract T getItem(int position);
}

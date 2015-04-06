package it.francescopezzato.multiparallaxlayout.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.francescopezzato.multiparallaxlayout.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by francesco on 22/03/2015.
 */
public class ListAdapter extends BindableAdapter<String> {

	private List<String> mData = new ArrayList();

	public ListAdapter(Context context) {
	     	super(context);
	}

	@Override
	public View newView(LayoutInflater inflater, int position, ViewGroup container) {
		return inflater.inflate(R.layout.list_item, container, false);
	}

	@Override
	public void bindView(String item, Context context, View view) {

		((TextView) view.findViewById(android.R.id.text1)).setText(item);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public String getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void replace(Observable<String> dataGenerator){
		mData.clear();
		notifyDataSetChanged();
		dataGenerator
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Subscriber<String>() {
				@Override
				public void onCompleted() {
					notifyDataSetChanged();
				}

				@Override
				public void onError(Throwable e) {
					//nop
				}

				@Override
				public void onNext(String s) {
					mData.add(s);
				}
			});

	}
}

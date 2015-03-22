package it.francescopezzato.android.multiparallaxlayout.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import it.francescopezzato.android.multiparallaxlayout.data.DataGenerator;
import rx.Observable;
import rx.android.widget.OnListViewScrollEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;

import static rx.android.app.AppObservable.bindFragment;

/**
 * Created by francesco on 22/03/2015.
 */
public class ExampleListFragment extends ListFragment {

	ListAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);


	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Observable<OnListViewScrollEvent> listViewScrollEventObservable = WidgetObservable.listScrollEvents(getListView());
		bindFragment(this, listViewScrollEventObservable)
			.subscribe(new Action1<OnListViewScrollEvent>() {
				@Override
				public void call(OnListViewScrollEvent event) {

				}
			});

		mAdapter = new ListAdapter(getActivity());
		setListAdapter(mAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		Observable<String> demoData = new DataGenerator().getDemoData();
		mAdapter.replace(bindFragment(this, demoData));
		setListShown(true);
	}


}

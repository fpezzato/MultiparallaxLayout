package it.francescopezzato.android.multiparallaxlayout.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;

import it.francescopezzato.android.MultiparallaxLayout;
import it.francescopezzato.android.multiparallaxlayout.R;
import it.francescopezzato.android.multiparallaxlayout.data.DataGenerator;
import rx.Observable;

import static rx.android.app.AppObservable.bindFragment;

/**
 * Created by francesco on 22/03/2015.
 */
public class ExampleListFragment extends ListFragment {

	ListAdapter mAdapter;

	MultiparallaxLayout mHeader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mAdapter = new ListAdapter(getActivity());

		mHeader = (MultiparallaxLayout) getActivity().getLayoutInflater().inflate(R.layout.list_header, getListView(), false);
		getListView().addHeaderView(mHeader);

		setListAdapter(mAdapter);

		getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0) {
					View firstChildView = getListView().getChildAt(0);
					if (firstChildView != null) {
						int scrollY = -firstChildView.getTop();
						if (mHeader != null) {
							mHeader.setOffsetY(scrollY);
						}
					}
				}

			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		Observable<String> demoData = new DataGenerator().getDemoData();
		mAdapter.replace(bindFragment(this, demoData));
		setListShown(true);
	}


}

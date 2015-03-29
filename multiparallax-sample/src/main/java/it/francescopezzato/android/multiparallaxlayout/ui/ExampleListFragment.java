package it.francescopezzato.android.multiparallaxlayout.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import it.francescopezzato.android.MultiparallaxLayout;
import it.francescopezzato.android.multiparallaxlayout.R;
import it.francescopezzato.android.multiparallaxlayout.data.DataGenerator;
import rx.Observable;

import static rx.android.app.AppObservable.bindFragment;

/**
 * Created by francesco on 22/03/2015.
 */
public class ExampleListFragment extends Fragment {

	ListView mListView;
	ListAdapter mAdapter;

	MultiparallaxLayout mHeader;
	Drawable mToolbarBackground;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, container, false);
		mListView = (ListView) view.findViewById(android.R.id.list);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mToolbarBackground = new ColorDrawable(Color.RED);
		((ActionBarActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(mToolbarBackground);

		mAdapter = new ListAdapter(getActivity());

		mHeader = (MultiparallaxLayout) getActivity().getLayoutInflater().inflate(R.layout.list_header, mListView, false);
		mListView.addHeaderView(mHeader);

		mListView.setAdapter(mAdapter);

		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0) {
					View firstChildView = mListView.getChildAt(0);
					if (firstChildView != null) {
						float scrollY = -firstChildView.getTop();
						if (mHeader != null) {
							mHeader.setOffsetY((int)scrollY);
							if (firstChildView.getTop() < 0) {
								int actionBarHeight = 0;
								if (getActivity() instanceof ActionBarActivity) {
									actionBarHeight = ((ActionBarActivity) getActivity()).getSupportActionBar().getHeight();
								}
								float ratiod4 = clamp(scrollY / (mHeader.getHeight() - actionBarHeight), 0f, 1f);
								mToolbarBackground.setAlpha((int) (ratiod4 * 255));
							}
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

	}

	private static float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}

}

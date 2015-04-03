package it.francescopezzato.android.multiparallaxlayout.ui;

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
import rx.android.app.AppObservable;

/**
 * Created by francesco on 22/03/2015.
 */
public class ExampleListFragment extends Fragment {

	private ListView mListView;
	private ListAdapter mAdapter;

	private MultiparallaxLayout mHeader;
	private Drawable mToolbarBackground;
	private int mAlpha;

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

		//Keep a reference to the Multiparallax layout
		mHeader = (MultiparallaxLayout) getActivity().getLayoutInflater().inflate(R.layout.list_header, mListView, false);

		//boilerplate code, ignore
		mToolbarBackground = new ColorDrawable(getResources().getColor(R.color.green_1));

		int alpha = 0;
		mToolbarBackground.setAlpha(alpha);
		((ActionBarActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(mToolbarBackground);

		mAdapter = new ListAdapter(getActivity());

		mListView.addHeaderView(mHeader);

		mListView.setAdapter(mAdapter);

		//The current implementation of WidgetObservable.listScrollEvents() has a too low event reporting ratio.
		//Let's go to the bare metal:
		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//nop
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0) {
					View firstChildView = mListView.getChildAt(0);
					if (firstChildView != null) {
						float scrollY = -firstChildView.getTop();
						if (mHeader != null) {

							//'parallax' bit: inform the MultiparallaxLayout of the current offset
							mHeader.setOffset((int) scrollY);

							//Toolbar transparency
							if (firstChildView.getTop() < 0) {
								int actionBarHeight = 0;
								if (getActivity() instanceof ActionBarActivity) {
									actionBarHeight = ((ActionBarActivity) getActivity()).getSupportActionBar().getHeight();
								}
								float ratio = clamp(scrollY / (mHeader.getHeight() - actionBarHeight), 0f, 1f);
								mAlpha = (int) (ratio * 255);
								mToolbarBackground.setAlpha(mAlpha);
							}
						}
					}
				}
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		Observable<String> demoData = new DataGenerator().getDemoData();
		mAdapter.replace(AppObservable.bindFragment(this, demoData));
	}

	private static float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}

}

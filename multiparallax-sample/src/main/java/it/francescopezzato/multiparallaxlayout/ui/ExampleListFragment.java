package it.francescopezzato.multiparallaxlayout.ui;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import it.francescopezzato.multiparallaxlayout.MultiparallaxLayout;
import it.francescopezzato.multiparallaxlayout.R;
import it.francescopezzato.multiparallaxlayout.Utils;
import it.francescopezzato.multiparallaxlayout.data.DataGenerator;
import rx.Observable;
import rx.android.app.AppObservable;

/**
 * Created by francesco on 22/03/2015.
 *
 * This class is a POC of {@link it.francescopezzato.multiparallaxlayout.MultiparallaxLayout}
 * applied on a listView.
 */
public class ExampleListFragment extends Fragment {

	private ListAdapter mAdapter;
	private MultiparallaxLayout mParallaxedView;
	private Drawable mToolbarBackground;
	private int mAlpha;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, container, false);

		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		final ListView listView = (ListView) view.findViewById(android.R.id.list);

		//Keep a reference to the Multiparallax layout
		mParallaxedView = (MultiparallaxLayout) getActivity().getLayoutInflater().inflate(R.layout.list_header, listView, false);

		//The current implementation of WidgetObservable.listScrollEvents() has a too low event reporting ratio.
		//Let's go to the bare metal:
		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//nop
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0) {
					View firstChildView = listView.getChildAt(0);
					if (firstChildView != null) {
						float scrollY = -firstChildView.getTop();
						if (mParallaxedView != null) {

							//'parallax' bit: inform the MultiparallaxLayout of the current offset
							mParallaxedView.setOffset((int) scrollY);

							//and fade the toolbar
							handleToolBarFade(firstChildView, scrollY);
						}
					}
				}
			}
		});


		setUpListAdapter(listView);
		setupToolbarFade();
	}

	private void setupToolbarFade() {
		//boilerplate code, ignore
		mToolbarBackground = new ColorDrawable(getResources().getColor(R.color.green_1));

		int alpha = 0;
		mToolbarBackground.setAlpha(alpha);
		((ActionBarActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(mToolbarBackground);
	}

	private void handleToolBarFade(View firstChildView, float scrollY) {
		//Toolbar transparency
		if (firstChildView.getTop() < 0) {
			int actionBarHeight = 0;
			if (getActivity() instanceof ActionBarActivity) {
				actionBarHeight = ((ActionBarActivity) getActivity()).getSupportActionBar().getHeight();
			}
			float ratio = Utils.clamp(scrollY / (mParallaxedView.getHeight() - actionBarHeight), 0f, 1f);
			mAlpha = (int) (ratio * 255);
			mToolbarBackground.setAlpha(mAlpha);
		}
	}

	private void setUpListAdapter(ListView listView) {

		mAdapter = new ListAdapter(getActivity());

		listView.addHeaderView(mParallaxedView);

		listView.setAdapter(mAdapter);
	}

	@Override
	public void onStart() {
		super.onStart();
		Observable<String> demoData = new DataGenerator().getDemoData();
		mAdapter.replace(AppObservable.bindFragment(this, demoData));
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_list_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = false;
		switch (item.getItemId()) {
			case R.id.action_navigate_scroll:
				if (getActivity() != null) {
					mToolbarBackground.setAlpha(255);
					((MainActivity) getActivity()).navigateTo(MainActivity.ExampleType.AS_SCROLL_VIEW);
					result = true;
				}
				break;
			default:
				break;
		}
		return result;
	}
}

package it.francescopezzato.android.multiparallaxlayout.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.TextView;

import it.francescopezzato.android.multiparallaxlayout.MultiparallaxLayout;
import it.francescopezzato.android.multiparallaxlayout.R;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by francesco on 03/04/2015.
 */
public class ExampleScrollViewFragment extends Fragment {


	ObservableScrollView mScrollView;
	MultiparallaxLayout mMultiparallaxLayout;
	ViewGroup mContent;

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scroll, container, false);
		mMultiparallaxLayout = (MultiparallaxLayout)view.findViewById(R.id.fragment_scroll_header);
		mMultiparallaxLayout.setInterpolator(new AnticipateInterpolator());
		mScrollView = (ObservableScrollView) view.findViewById(R.id.observable_scroll_view);
		mScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
			@Override
			public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
				mMultiparallaxLayout.setOffset(y);
			}
		});
		mContent = (ViewGroup) mScrollView.findViewById(R.id.fragment_scroll_content);
		Observable.range(0, 10).subscribe(new Action1<Integer>() {
			@Override
			public void call(Integer integer) {
				mContent.addView(demoContent(inflater, integer));
			}
		});
		setHasOptionsMenu(true);
		return view;
	}


	private View demoContent(LayoutInflater inflater, int seed) {
		View item = inflater.inflate(R.layout.list_item, mContent, false);
		((TextView) item.findViewById(android.R.id.text1)).setText("Elem." + seed);
		return item;
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_scroll_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = false;
		switch (item.getItemId()) {
			case R.id.action_navigate_list:
				if (getActivity() != null) {
					((MainActivity) getActivity()).navigateTo(MainActivity.ExampleType.AS_LIST);
					result = true;
				}
				break;
			default:
				break;
		}
		return result;
	}
}

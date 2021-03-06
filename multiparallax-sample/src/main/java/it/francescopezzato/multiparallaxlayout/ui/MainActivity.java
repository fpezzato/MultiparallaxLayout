package it.francescopezzato.multiparallaxlayout.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;

import it.francescopezzato.multiparallaxlayout.R;


public class MainActivity extends ActionBarActivity {

	private static final String TAG = MainActivity.class.getCanonicalName();
	private static final String KEY_CURRENT_FRAGMENT = "KEY_CURRENT_FRAGMENT" ;

	///images from http://pixabay.com/
	ExampleType mExampleType = ExampleType.AS_LIST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}


		if(savedInstanceState != null){
			if(savedInstanceState.containsKey(KEY_CURRENT_FRAGMENT)){
				mExampleType = (ExampleType)savedInstanceState.getSerializable(KEY_CURRENT_FRAGMENT);
			}
		}
		navigateTo(mExampleType);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(KEY_CURRENT_FRAGMENT, mExampleType);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			about();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void about() {
		CharSequence content = Html.fromHtml(getString(R.string.dialog_about_content));

		new MaterialDialog.Builder(this)
			.title(R.string.dialog_about_title)
			.content(content)
			.positiveText(R.string.dialog_about_positive)
			.show();
	}

	public enum ExampleType  {
		AS_LIST(ExampleListFragment.class), AS_SCROLL_VIEW(ExampleScrollViewFragment.class);

		private Class<? extends Fragment> mFragmentClass;

		ExampleType(Class<? extends Fragment> fragmentClass) {
			this.mFragmentClass = fragmentClass;
		}
	}

	public void navigateTo(ExampleType type ) {
		mExampleType = type;
		try {
			getSupportFragmentManager()
				.beginTransaction().replace(R.id.content_container, type.mFragmentClass.newInstance()).commit();
		} catch (InstantiationException e) {
			Log.e(TAG,"Unable to instantiate a child fragment",e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "Unable to reach a child fragment", e);
		}
	}
}

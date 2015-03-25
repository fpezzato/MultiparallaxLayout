package it.francescopezzato.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import it.francescopezzato.android.multiparallax_lib.R;

import static it.francescopezzato.android.ViewTreeWalker.newTreeWalker;

/**
 * Created by francesco on 22/03/2015.
 */
public class MultiparallaxLayout extends RelativeLayout {


	private int mOffset;

	private void updateLayout(View view) {
		if (view.getLayoutParams() instanceof LayoutParams) {
			LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();

			//horizontal
			float newOffset = mOffset * layoutParams.ratioX;
			view.setTranslationX((int) newOffset);

			//vertical
			newOffset = mOffset * layoutParams.ratioY;
			view.setTranslationY((int) newOffset);
		}
	}

	public MultiparallaxLayout(Context context) {
		super(context);
	}

	public MultiparallaxLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MultiparallaxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public RelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new LayoutParams(getContext(), attrs);
	}

	enum Attribute {
		NONE(0),
		RATIO_X(R.styleable.MultiparallaxLayout_ratioX),
		RATIO_Y(R.styleable.MultiparallaxLayout_ratioY);

		static class Static {

			static SparseArray<Attribute> sAttributes = new SparseArray<>();

		}

		Attribute(int reference) {
			Static.sAttributes.put(reference, this);
		}

		static Attribute from(int reference) {
			return Static.sAttributes.get(reference, NONE);
		}

	}

	ViewTreeWalker treeWalker = newTreeWalker();

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		for (View view : treeWalker.flat(this)) {
			updateLayout(view);
		}

	}
/*
	public void setOffsetX(int offsetX) {
		if (offsetX != mOffsetY) {
			requestLayout();
		}
		this.mOffsetX = offsetX;
	}*/

	public void setOffsetY(int offsetY) {
		if (offsetY != mOffset) {
			requestLayout();
		}
		this.mOffset = offsetY;
	}

	public static class LayoutParams extends RelativeLayout.LayoutParams {

		public float ratioX;
		public float ratioY;

		public LayoutParams(ViewGroup.LayoutParams source) {
			super(source);
		}

		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);

			TypedArray a = c.obtainStyledAttributes(attrs,
				R.styleable.MultiparallaxLayout);

			for (int i = 0; i < a.getIndexCount(); i++) {
				int attr = a.getIndex(i);
				switch (Attribute.from(attr)) {
					case RATIO_X:
						ratioX = a.getFloat(attr, 1);
						break;
					case RATIO_Y:
						ratioY = a.getFloat(attr, 1);
						break;
					default:
						break;

				}
			}
			a.recycle();
		}

		public LayoutParams(int width, int height) {
			super(width, height);
		}

		public LayoutParams(MarginLayoutParams source) {
			super(source);
		}
	}
}

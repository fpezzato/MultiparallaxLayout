package it.francescopezzato.android.multiparallaxlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import it.francescopezzato.android.multiparallax_lib.R;

import static it.francescopezzato.android.multiparallaxlayout.Utils.clamp;

/**
 * Created by francesco on 22/03/2015.
 */
public class MultiparallaxLayout extends RelativeLayout {

	private int mOffset;

	ViewTreeWalker mTreeWalker = ViewTreeWalker.newTreeWalker();

	private Interpolator mInterpolator;

	public MultiparallaxLayout(Context context) {
		super(context);
	}

	public MultiparallaxLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MultiparallaxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private void updateLayout(View view) {
		if (view.getLayoutParams() instanceof LayoutParams) {
			LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();

			float interpolatorAdjustment = mInterpolator != null ? mInterpolator.getInterpolation(computeRatio()) : 1;

			//horizontal
			float newOffset = mOffset * layoutParams.ratioX * interpolatorAdjustment;
			view.setTranslationX((int) newOffset);

			//vertical
			newOffset = mOffset * layoutParams.ratioY * interpolatorAdjustment;
			view.setTranslationY((int) newOffset);
		}
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

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		for (View view : mTreeWalker.flat(this)) {
			updateLayout(view);
		}
	}

	private float computeRatio() {
		return clamp(mOffset / (float) getHeight(), 0f, 1f);
	}

	public void setOffset(int offset) {
		if (offset != mOffset) {
			requestLayout();
		}
		this.mOffset = offset;
	}

	public void setInterpolator(Interpolator interpolator){
		this.mInterpolator = interpolator;
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

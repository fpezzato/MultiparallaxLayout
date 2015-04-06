MultiparallaxLayout
========

This is a proof-of-concept of an android layout that hosts multiple views moved with different speed. 
This can be used to create multiple parallax effect.

![Multiparallax layout as ListView header](/../screenshots/multiparallaxDemo06.gif?raw=true "Example as ListView header")    


Usage
-----
Declare in your layout a view of type `MultiparallaxLayout`. In the direct child of this view, declare custom attribute(s) `ratioY` and `ratioX`.
Both those attributes accept a float value, used to declare the difference of speed.
The default value is '1'.
```xml
 <it.francescopezzato.multiparallaxlayout.MultiparallaxLayout
            android:id="@+id/fragment_scroll_header"
            android:layout_width="match_parent"
            android:layout_height="@dimen/list_header_height">

            <ImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:src="@drawable/bg_flower"
                multiparallax:ratioY="0.9"
                multiparallax:ratioX="0.8"
                />
</it.francescopezzato.multiparallaxlayout.MultiparallaxLayout>
```


In your code, wire any source of 'movement' to the declared layout calling `setOffset(int offset)` on the declared layout, to perform the parallax effect.
For instance, if you use it inside a scroll view:
```java

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scroll, container, false);
		mMultiparallaxLayout = (MultiparallaxLayout) view.findViewById(R.id.fragment_scroll_header);
		
		mScrollView = (ObservableScrollView) view.findViewById(R.id.observable_scroll_view);
		mScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
			@Override
			public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
				mMultiparallaxLayout.setOffset(y);
			}
		});
		 

```

Interpolator
------------

It's possible to set an Interpolator used during the effect.

```java
mMultiparallaxLayout.setInterpolator(new DecelerateInterpolator());
```


![Multiparallax layout in ScrollView](/../screenshots/multiparallaxDemo07.gif?raw=true "Example in a ScrollView") 




Thanks to pixabay.com for the images used in the sample code. 

package it.francescopezzato.android;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francesco on 22/03/2015.
 */
public class ViewTreeWalker {

	public static ViewTreeWalker newTreeWalker() {
		return new ViewTreeWalker();
	}

	public List<View> flat(ViewGroup root) {
		List<View> accumulator = new ArrayList<>();
		walkTreeBreadthFirstR(root,accumulator);
		return accumulator;

	}

	private void walkTreeBreadthFirstR(final View currentView, List<View> accumulator) {

		if (currentView instanceof ViewGroup) {
			ViewGroup currentGroup = (ViewGroup) currentView;
			if (currentGroup.getChildCount() > 0) {
				for (int i = 0; i < currentGroup.getChildCount(); i++) {
					walkTreeBreadthFirstR(currentGroup.getChildAt(i), accumulator);
				}
			}
		}
		accumulator.add(currentView);
	}
}

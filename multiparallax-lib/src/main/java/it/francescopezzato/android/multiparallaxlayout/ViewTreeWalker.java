package it.francescopezzato.android.multiparallaxlayout;

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

		//skip the root
		for(View view : getChildren(root)){
			walkTreeBreadthFirstR(view, accumulator);
		}
		return accumulator;

	}

	private void walkTreeBreadthFirstR(final View currentView, List<View> accumulator) {

		if (currentView instanceof ViewGroup) {
			ViewGroup currentGroup = (ViewGroup) currentView;
			if (currentGroup.getChildCount() > 0) {
				for(View view : getChildren(currentGroup)){
					walkTreeBreadthFirstR(view, accumulator);
				}
			}
		}
		accumulator.add(currentView);
	}

	private List<View>  getChildren(ViewGroup viewGroup){
		List<View> result = new ArrayList<>(viewGroup.getChildCount());
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			result.add(viewGroup.getChildAt(i));
		}
		return result;
	}

}

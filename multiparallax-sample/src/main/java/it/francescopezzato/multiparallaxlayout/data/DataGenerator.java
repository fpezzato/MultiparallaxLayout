package it.francescopezzato.multiparallaxlayout.data;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by francesco on 22/03/2015.
 */
public class DataGenerator {

	public Observable<String> getDemoData() {
		return Observable.range(0, 100).map(new Func1<Integer, String>() {
			@Override
			public String call(Integer integer) {
				return "Elem. " + integer.toString();
			}
		});
	}

}

package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class CourseSubcriber<T> implements Subscriber<T>{
	private List<T> results;
	private Subscription subscription;
	private CountDownLatch latch;

	public CourseSubcriber() {
		results = new ArrayList<T>();
		latch = new CountDownLatch(1);
	}

	public void onSubscribe(Subscription s) {
		this.subscription = s;
		this.subscription.request(1);
	}

	public void onNext(T t) {
		results.add(t);
		this.subscription.request(1);

	}

	public void onError(Throwable t) {
		t.printStackTrace();

	}

	public void onComplete() {
		latch.countDown();
	}

	public List<T> getResults() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

	public T getSingleResult() {
		return getResults().size() > 0 ? getResults().get(0) : null;
	}
}

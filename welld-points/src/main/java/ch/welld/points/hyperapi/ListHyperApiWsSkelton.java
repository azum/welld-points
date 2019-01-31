package ch.welld.points.hyperapi;

import java.util.List;

import org.apache.http.HttpResponse;

import com.nominanuda.hyperapi.AnnotatedType;
import com.nominanuda.hyperapi.HyperApiWsSkelton;
import com.nominanuda.zen.obj.Arr;
import com.nominanuda.zen.obj.wrap.ObjWrapper;

public class ListHyperApiWsSkelton extends HyperApiWsSkelton {

	@Override
	protected HttpResponse response(Object result, AnnotatedType ap) {
		return super.response(result instanceof List ? from((List<?>) result) : result, ap);
	}
	
	
	private Arr from(List<?> list) {
		Arr arr = Arr.make();
		for (Object o : list) {
			if (o instanceof List) {
				arr.add(from((List<?>) o));
			} else if (o instanceof ObjWrapper) {
				arr.add(((ObjWrapper) o).unwrap());
			} else {
				arr.add(o);
			}
		}
		return arr;
	}
}

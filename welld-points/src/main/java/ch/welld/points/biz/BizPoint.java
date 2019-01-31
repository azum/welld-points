package ch.welld.points.biz;

import static com.nominanuda.zen.obj.wrap.Wrap.WF;

import java.util.Objects;

import ch.welld.points.model.Point;

public class BizPoint {
	public final double x, y;
	
	BizPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	/* equality */
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BizPoint) {
			BizPoint bp = (BizPoint) obj;
			return bp.x == x
				&& bp.y == y;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	
	/* helpers */
	
	public Point toApiPoint() {
		return WF.wrap(Point.class)
			.x(x)
			.y(y);
	}
}

package ch.welld.points.biz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ch.welld.points.model.Point;

public class BizLine {
	private final Set<BizPoint> bizPoints = new HashSet<>();
	
	BizLine(BizPoint... bizPoints) {
		this.bizPoints.addAll(Arrays.asList(bizPoints));
	}
	
	void add(BizPoint bizPoint) {
		bizPoints.add(bizPoint);
	}
	
	int size() {
		return bizPoints.size();
	}
	
	
	/* helpers */
	
	public List<Point> toApiLine() {
		return bizPoints.stream()
			.map(BizPoint::toApiPoint)
			.collect(Collectors.toList());
	}
}

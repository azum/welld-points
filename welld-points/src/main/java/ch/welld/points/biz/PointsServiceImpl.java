package ch.welld.points.biz;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.nominanuda.zen.common.Tuple2;

import ch.welld.points.IPointsService;

public class PointsServiceImpl implements IPointsService {
	private final Set<BizLine> bizLines = new HashSet<>();
	private final Map<BizPoint, Map<Double, BizLine>> bizPoints = new HashMap<>();
	
	
	@Override
	public BizPoint addPoint(double x, double y) {
		BizPoint newBizPoint = new BizPoint(x, y);
		if (!bizPoints.containsKey(newBizPoint)) {
			List<Tuple2<BizPoint, Double>> bizPointsSortedByAngle = bizPoints.keySet().stream()
				.map(bp -> new Tuple2<>(bp, computePositiveAngle(newBizPoint, bp)))
				.sorted((t0, t1) -> t0.get1().compareTo(t1.get1()))
				.collect(Collectors.toList());
			
			Map<Double, BizLine> newBizPointLines = new HashMap<>();
			bizPoints.put(newBizPoint, newBizPointLines);
			
			Double currentAngle = null;
			BizLine newlyCreatedBizLine = null;
			for (Tuple2<BizPoint, Double> bizPointAndAngle : bizPointsSortedByAngle) {
				BizPoint bizPoint = bizPointAndAngle.get0();
				Double angle = bizPointAndAngle.get1();
				
				Map<Double, BizLine> bizPointLines = bizPoints.get(bizPoint);
				if (!angle.equals(currentAngle)) { // new group of points with the same angle
					@Nullable BizLine angledBizLine = bizPointLines.get(angle);
					if (angledBizLine != null) {
						newlyCreatedBizLine = null;
						angledBizLine.add(newBizPoint);
						newBizPointLines.put(angle, angledBizLine); // all other points at the same angle already have a reference to this line
					} else {
						newlyCreatedBizLine = new BizLine(bizPoint, newBizPoint);
						newBizPointLines.put(angle, newlyCreatedBizLine);
						bizPointLines.put(angle, newlyCreatedBizLine);
						bizLines.add(newlyCreatedBizLine);
					}
					currentAngle = angle;
					
				} else if (newlyCreatedBizLine != null) {
					bizPointLines.put(angle, newlyCreatedBizLine);
				}
			}
		}
		return newBizPoint;
	}
	
	
	@Override
	public List<BizLine> getLines(int minLength) {
		return bizLines.stream()
			.filter(l -> l.size() >= minLength)
			.collect(Collectors.toList());
	}
	

	@Override
	public Set<BizPoint> getAll() {
		return new HashSet<>(bizPoints.keySet()); // copy just to avoid externals manipulations to the set
	}
	
	
	@Override
	public void clear() {
		bizPoints.clear();
		bizLines.clear();
	}
	
	
	/* helpers */
	
	private double computePositiveAngle(BizPoint bp0, BizPoint bp1) {
		double x = bp1.x - bp0.x, y = bp1.y - bp0.y;
		if (y < 0) {
			x = -x;
			y = -y;
		}
		return Math.atan2(y, x);
	}
}

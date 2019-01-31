package ch.welld.points;

import java.util.List;
import java.util.Set;

import ch.welld.points.biz.BizLine;
import ch.welld.points.biz.BizPoint;

public interface IPointsService {
	BizPoint addPoint(double x, double y);
	List<BizLine> getLines(int minLength);
	Set<BizPoint> getAll();
	void clear();
}

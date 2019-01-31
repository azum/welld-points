package ch.welld.points;

import java.util.List;
import java.util.stream.Collectors;

import com.nominanuda.web.http.Http400Exception;
import com.nominanuda.web.http.Http404Exception;

import ch.welld.points.api.PointsApi;
import ch.welld.points.biz.BizLine;
import ch.welld.points.biz.BizPoint;
import ch.welld.points.model.Point;

public class PointsApiImpl implements PointsApi {
	private IPointsService pointsService;
	
	
	@Override
	public Point addPoint(Point point) {
		return pointsService
			.addPoint(point.x(), point.y())
			.toApiPoint(); // could be different from the original point (because of validations etc)
	}

	@Override
	public List<Point> getSpace() {
		return pointsService.getAll().stream()
			.map(BizPoint::toApiPoint)
			.collect(Collectors.toList());
	}

	@Override
	public void clearSpace() {
		pointsService.clear();
	}

	@Override
	public List<List<Point>> getLines(int n) {
		if (n > 1) {
			List<BizLine> lines = pointsService.getLines(n);
			if (!lines.isEmpty()) {
				return lines.stream()
					.map(BizLine::toApiLine)
					.collect(Collectors.toList());
			}
			throw new Http404Exception("no lines found");
		}
		throw new Http400Exception("a line must have at least 2 points");
	}
	
	
	/* setters */
	
	public void setPointsService(IPointsService pointsService) {
		this.pointsService = pointsService;
	}
}

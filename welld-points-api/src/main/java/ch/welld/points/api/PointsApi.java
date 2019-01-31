package ch.welld.points.api;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import ch.welld.points.model.Point;

public interface PointsApi {
	
	@POST @Path("/point") // it's idempotent, should be a PUT...
	Point addPoint(
		Point point
	);
	
	@GET @Path(value="/space")
	List<Point> getSpace();
	
	@DELETE @Path("/space")
	void clearSpace();
	
	@GET @Path(value="/lines/{n}")
	List<List<Point>> getLines(
		@QueryParam("n") int n
	);
}

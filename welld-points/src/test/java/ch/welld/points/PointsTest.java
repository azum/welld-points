package ch.welld.points;

import static com.nominanuda.zen.obj.wrap.Wrap.WF;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ch.welld.points.model.Point;

public class PointsTest {
	
	@Test
	public void apiTest( ) {
		System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
//		System.setProperty("spring.profiles.active", "test"); ev in case of specialized beans
		
		try (
			AbstractXmlApplicationContext appCtx = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/welld-points-appctx.xml");
		) {
			PointsApiImpl pointsApi = appCtx.getBean("pointsApi", PointsApiImpl.class);
			assertEquals(0, pointsApi.getSpace().size());
			
			// add a point
			pointsApi.addPoint(WF.wrap(Point.class).x(12d).y(46d)); // 12,46
			assertEquals(1, pointsApi.getSpace().size());
			
			// add same point
			pointsApi.addPoint(WF.wrap(Point.class).x(12d).y(46d)); // 12,46
			assertEquals(1, pointsApi.getSpace().size());
			
			// add new point
			pointsApi.addPoint(WF.wrap(Point.class).x(12d).y(10d)); // 12,10
			assertEquals(2, pointsApi.getSpace().size());
			assertEquals(1, pointsApi.getLines(2).size());
			
			// add new point
			pointsApi.addPoint(WF.wrap(Point.class).x(6d).y(24d)); // 6,24
			assertEquals(3, pointsApi.getSpace().size());
			assertEquals(3, pointsApi.getLines(2).size());
			
			// add new colinear point
			pointsApi.addPoint(WF.wrap(Point.class).x(18d).y(68d)); // 18,68 (colinear with 12,46 - 6,24)
			assertEquals(4, pointsApi.getSpace().size());
			assertEquals(4, pointsApi.getLines(2).size());
			assertEquals(1, pointsApi.getLines(3).size());
			
			// add new colinear point
			pointsApi.addPoint(WF.wrap(Point.class).x(12d).y(51d)); // 12,51 (colinear with 12,46 - 12,10)
			assertEquals(5, pointsApi.getSpace().size());
			assertEquals(6, pointsApi.getLines(2).size());
			assertEquals(2, pointsApi.getLines(3).size());

			// add new colinear point
			pointsApi.addPoint(WF.wrap(Point.class).x(12d).y(-21d)); // 12,-21 (colinear with 12,46 - 12,10 - 12,51)
			assertEquals(6, pointsApi.getSpace().size());
			assertEquals(8, pointsApi.getLines(2).size());
			assertEquals(2, pointsApi.getLines(3).size());
			assertEquals(1, pointsApi.getLines(4).size());
			
			// add new point
			pointsApi.addPoint(WF.wrap(Point.class).x(-31d).y(-1d)); // -31,-1
			assertEquals(7, pointsApi.getSpace().size());
			assertEquals(14, pointsApi.getLines(2).size());
			assertEquals(2, pointsApi.getLines(3).size());
			assertEquals(1, pointsApi.getLines(4).size());
			
			// empty space
			pointsApi.clearSpace();
			assertEquals(0, pointsApi.getSpace().size());
		}
	}
}

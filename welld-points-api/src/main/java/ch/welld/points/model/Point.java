package ch.welld.points.model;

import com.nominanuda.zen.obj.wrap.ObjWrapper;

public interface Point extends ObjWrapper {

	double x();
	Point x(double d);
	
	double y();
	Point y(double d);
}

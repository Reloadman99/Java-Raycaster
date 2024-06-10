package reloadman;

public class Camera {
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public double MOVE_SPEED = .08;
	public double ROTATION_SPEED = .045;
	public Camera(double x, double y, double xd, double yd, double xp, double yp, double ms,double rs) {
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
		MOVE_SPEED = ms;
		ROTATION_SPEED = rs;
	}
	public void moveForward(int[][] map) {
	if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
		xPos+=xDir*MOVE_SPEED;
	}
	if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
		yPos+=yDir*MOVE_SPEED;
	}
	
	public void moveBackwards(int[][] map) {
	if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
		xPos-=xDir*MOVE_SPEED;
	if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
		yPos-=yDir*MOVE_SPEED;
	}
	
	public void turnRight(int[][] map) {
		double oldxDir=xDir;
		xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
		yDir=xDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
	double oldxPlane = xPlane;
		xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
		yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
	}
	
	public void turnLeft(int[][] map) {double oldxDir=xDir;
	xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
	yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
	double oldxPlane = xPlane;
	xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
	yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
	}
	
}

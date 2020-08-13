package forest;

import java.awt.Point;
import java.awt.event.MouseEvent;
import mvc.Controller;

public class ForestController extends Controller {

	public ForestController() {
		super();
		return;
	}
	@Override
	public void mouseClicked(MouseEvent aMouseEvent) {
		Point aPoint = aMouseEvent.getPoint();
		ForestView aView = (ForestView)super.view;
		Node aNode = aView.whichOfNodes(aPoint);

		if(aNode != null) System.out.println(aNode.getName());
		return;
	}

	/**
	 *  
	 */
//	public void mouseDragged(MouseEvent aMouseEvent) {
		// Cursor aCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
		// Component aComponent = (Component)aMouseEvent.getSource();
		// aComponent.setCursor(aCursor);
//		this.current = aMouseEvent.getPoint();
//		int x = this.current.x - this.previous.x;
//		int y = this.current.y - this.previous.y;
//		Point point = new Point(x, y);
//		this.view.scrollBy(point);
//		this.view.update();
//		this.previous = this.current;
//		return;
//	}

//	public void mouseEntered(MouseEvent aMouseEvent) {
//		return;
//	}

//	public void mouseExited(MouseEvent aMouseEvent) {
//		return;
//	}

//	public void mouseMoved(MouseEvent aMouseEvent) {
//		return;
//	}

//	public void mousePressed(MouseEvent aMouseEvent) {
		// Cursor aCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		// Component aComponent = (Component)aMouseEvent.getSource();
		// aComponent.setCursor(aCursor);
//		this.current = aMouseEvent.getPoint();
//		this.previous = this.current;
//		return;
//	}

//	public void mouseReleased(MouseEvent aMouseEvent) {
		// Cursor aCursor = Cursor.getDefaultCursor();
		// Component aComponent = (Component)aMouseEvent.getSource();
		// aComponent.setCursor(aCursor);
//		this.current = aMouseEvent.getPoint();
//		this.previous = this.current;
//		return;
//	}

//	public void mouseWheelMoved(MouseWheelEvent aMouseWheelEvent) {
//		return;
//	}

//	public void setModel(ForestModel aModel) {
//		this.model = aModel;
//		return;
//	}

//	public void setView(ForestView aView) {
//		this.view = aView;
//		this.view.addMouseListener(this);
//		this.view.addMouseMotionListener(this);
//		this.view.addMouseWheelListener(this);
//		return;
//	}

//	public String toString() {
//		StringBuffer aBuffer = new StringBuffer();
//		Class<?> aClass = this.getClass();
//		aBuffer.append(aClass.getName());
//		aBuffer.append("[model=");
//		aBuffer.append(this.model);
//		aBuffer.append(",view=");
//		aBuffer.append(this.view);
//		aBuffer.append("]");
//		return aBuffer.toString();
//	}

}

package utopia.guimaker.view

import utopia.flow.datastructure.mutable.Lazy
import utopia.genesis.handling.Camera
import utopia.genesis.handling.mutable.DrawableHandler
import utopia.genesis.shape.Vector3D
import utopia.genesis.shape.shape2D.{Bounds, Point, Size, Transformation}
import utopia.genesis.util.Drawer

/**
  * This camera displays component contents, but may be scrolled and / or scaled
  * @author Mikko Hilpinen
  * @since 30.5.2019, v1+
  */
class MainCamera(val originalAreaSize: Size) extends Camera[DrawableHandler]
{
	// ATTRIBUTES	------------------
	
	private var _centerPosition = originalAreaSize.toPoint / 2
	private var _zoom = 1.0
	
	private val _viewTransformation = new Lazy(() => Transformation(translation = _centerPosition.toVector,
		scaling = Vector3D(1 / _zoom, 1 / _zoom)))
	
	
	// COMPUTED	----------------------
	
	/**
	  * @return The coordinate of this camera's center point
	  */
	def centerPosition = _centerPosition
	def centerPosition_=(newPosition: Point) =
	{
		_centerPosition = newPosition
		_viewTransformation.reset()
	}
	
	/**
	  * @return The zoom factor of this camera (1.0 means no zooming)
	  */
	def zoom = _zoom
	def zoom_=(newZoom: Double) =
	{
		_zoom = newZoom
		_viewTransformation.reset()
	}
	
	/**
	  * @return The size of this camera's area
	  */
	def size = originalAreaSize / zoom
	
	/**
	  * @return The top left position of this camera
	  */
	def topLeft = centerPosition - size / 2
	def topLeft_=(newPosition: Point) = centerPosition = newPosition + size / 2
	
	
	// IMPLEMENTED	------------------
	
	override protected def makeDrawableHandler(customizer: Drawer => Drawer) = DrawableHandler(customizer = Some(customizer))
	
	/**
	  * This transformation determines the camera's transformation in the 'view world'. For example,
	  * if this transformation has 2x scaling, the camera projects twice as much game world to the
	  * same projection area.
	  */
	override def viewTransformation = _viewTransformation.get
	
	/**
	  * This transformation determines how the camera projects the 'view world' into the 'absolute
	  * world'. For example, the transformation determines the position of the projection's origin
	  * ((0, 0) of the projection area). If this transformation has 2x scaling, the projected
	  * 'camera display' will be twice as large while still drawing the same 'view world'
	  * information.<br>
	  * Usually you only need to determine the drawing origin (translation)
	  */
	override val projectionTransformation = Transformation.translation(originalAreaSize.toVector / 2)
	
	/**
	  * This determines both the size and shape of the camera's projection. You can draw a circular
	  * area by using a circle and a rectangular area by using a rectangle, etc. The shape's center
	  * should always be at the (0, 0) coordinates since that's what the camera is rotated and scaled
	  * around.
	  * <br> You can instead move both the projected and viewed area by using the projection- and
	  * view transformations respectively.
	  */
	override val projectionArea = Bounds.centered(Point.origin, originalAreaSize).toShape
}

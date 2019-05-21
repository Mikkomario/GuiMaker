package utopia.guimaker.view

import utopia.genesis.color.Color
import utopia.genesis.handling.Drawable
import utopia.genesis.handling.mutable.{KeyStateHandler, KeyTypedHandler, MouseButtonStateHandler, MouseMoveHandler, MouseWheelHandler}
import utopia.genesis.shape.shape2D.{Bounds, Point, Size, Transformation}
import utopia.genesis.util.Drawer
import utopia.guimaker.controller.ComponentHierarchy
import utopia.inception.handling.HandlerType
import utopia.reflection.component.stack.Stackable
import utopia.reflection.event.{ResizeEvent, ResizeListener}

/**
  * Components are used for determining component locations & hierarchies. A component represents any draggable component
  * or container
  * @author Mikko Hilpinen
  * @since 20.5.2019, v1+
  */
trait Component extends Draggable with Stackable with Drawable
{
	// ATTRIBUTES	--------------------
	
	private var _parent: Option[Container] = None
	private var _isTransParent = true
	private var _size = Size.zero
	
	var borderWidth = 1
	var borderColor: Option[Color] = None
	
	override var background: Color = Color.white
	override var resizeListeners = Vector[ResizeListener]()
	override var isVisible = true
	
	override val mouseButtonHandler = MouseButtonStateHandler()
	override val mouseMoveHandler = MouseMoveHandler()
	override val mouseWheelHandler = MouseWheelHandler()
	override val keyStateHandler = KeyStateHandler()
	override val keyTypedHandler = KeyTypedHandler()
	
	override var position = Point.origin
	
	
	// ABSTRACT	------------------------
	
	/**
	  * Draws the contents inside this component
	  * @param drawer A drawer with origin at this component's origin and clipped to this component's size
	  */
	protected def drawContent(drawer: Drawer): Unit
	
	
	// COMPUTED	------------------------
	
	override def parent = _parent
	
	override def isTransparent = _isTransParent
	def isTransparent_=(transparency: Boolean) = _isTransParent = transparency
	
	
	// IMPLEMENTED	--------------------
	
	override def size = _size
	override def size_=(newSize: Size) =
	{
		val event = ResizeEvent(_size, newSize)
		_size = newSize
		resizeListeners.foreach { _.onResizeEvent(event) }
	}
	
	override def isAttached = _parent.isDefined
	
	override def stackId = hashCode()
	
	override def detach() =
	{
		ComponentHierarchy.detach(this)
		_parent.foreach { _ -= this }
		_parent = None
		setToOptimalSize()
	}
	
	override protected def drop(mousePosition: Point) =
	{
		// Checks whether this component should be attached to a container
		ComponentHierarchy.componentAtLocation(mousePosition) match
		{
			case Some(container: Container) =>
				_parent = Some(container)
				container += this
				ComponentHierarchy.registerConnection(container, this)
			case _ => Unit
		}
	}
	
	override def allowsHandlingFrom(handlerType: HandlerType) = isVisible
	
	override def draw(drawer: Drawer) =
	{
		// Will not draw anything if size is zero
		if (size.area > 0)
		{
			// Uses a transformed & clipped version of drawer
			val myArea = Bounds(Point.origin, size)
			val transformedDrawer = drawer.transformed(Transformation.translation(position.toVector)).clippedTo(myArea)
			// When dragging, this component is drawn as partly opaque
			(if (isDragging) transformedDrawer.withAlpha(0.55) else transformedDrawer).disposeAfter
			{
				d =>
					// May draw background / border
					if (!isTransparent || (borderColor.isDefined && borderWidth > 0))
					{
						val backgroundDrawer = d.withColor(if (isTransparent) Some(background.toAwt) else None,
							borderColor.map
							{ _.toAwt }).withStroke(Drawer.defaultStroke(borderWidth))
						
						backgroundDrawer.draw(myArea)
					}
					
					// Draws content on top
					drawContent(d)
			}
		}
	}
}

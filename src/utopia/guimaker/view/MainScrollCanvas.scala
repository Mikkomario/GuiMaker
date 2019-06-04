package utopia.guimaker.view

import utopia.genesis.color.Color
import utopia.genesis.handling.Drawable
import utopia.genesis.handling.mutable.DrawableHandler
import utopia.genesis.shape.Axis2D
import utopia.genesis.shape.shape2D.{Bounds, Point, Size}
import utopia.genesis.util.{DepthRange, Drawer}
import utopia.genesis.view.Canvas
import utopia.inception.handling.immutable.Handleable
import utopia.reflection.component.swing.AwtComponentWrapper
import utopia.reflection.component.{Area, ComponentLike, ComponentWrapper}
import utopia.reflection.container.stack.{BoxScrollBarDrawer, ScrollAreaLike, ScrollBarDrawer}
import utopia.reflection.shape.StackSize

import scala.collection.immutable.HashMap

/**
  * This scroll area uses a camera to display content
  * @author Mikko Hilpinen
  * @since 4.6.2019, v1+
  */
class MainScrollCanvas(val viewSize: Size, drawHandler: DrawableHandler) extends ScrollAreaLike with ComponentWrapper
{
	// ATTRIBUTES	-------------------
	
	private val canvas = new Canvas(drawHandler, viewSize)
	private val camera = new MainCamera(viewSize)
	
	private var areaSize = viewSize
	
	
	// INITIAL CODE	-------------------
	
	// Sets initial size
	size = viewSize
	
	// Updates camera zoom on resize
	addResizeListener { e => camera.zoom = (camera.originalAreaSize / e.newSize).dimensions.max }
	
	// Adds scroll bar drawing
	drawHandler += new BarDrawable(BoxScrollBarDrawer.roundedBarOnly(Color.black.withAlpha(0.55)))
	
	
	// COMPUTED	-----------------------
	
	/**
	  * @return The draw handler used in this canvas' hud (non-scrollable area)
	  */
	def hudDrawHandler = canvas.drawHandler
	
	/**
	  * @return The draw handler used in this canvas' camera
	  */
	def cameraDrawHandler = camera.drawHandler
	
	
	// IMPLEMENTED	-------------------
	
	override protected val wrapped: ComponentLike = new CanvasWrapper()
	override val content: Area = new ContentArea()
	override val lengthLimits = HashMap()
	override val limitsToContentSize = true
	override val scrollBarIsInsideContent = true
	override val scrollBarWidth = 16
	override val contentStackSize = StackSize.fixed(viewSize)
	
	override def axes = Axis2D.values
	
	// Repainting is handled within the canvas automatically already
	override def repaint(bounds: Bounds) = Unit
	
	override protected def updateVisibility(visible: Boolean) = super[ComponentWrapper].isVisible_=(visible)
	
	
	// NESTED CLASSES	-----------------
	
	private class CanvasWrapper extends AwtComponentWrapper
	{
		override def component = canvas
	}
	
	private class ContentArea extends Area
	{
		override def position = -camera.topLeft
		override def position_=(p: Point) = camera.topLeft = -p
		
		override def size = areaSize
		override def size_=(s: Size) = areaSize = s
	}
	
	private class BarDrawable(val barDrawer: ScrollBarDrawer) extends Drawable with Handleable
	{
		override def draw(drawer: Drawer) = drawWith(barDrawer, drawer)
		
		override def drawDepth = DepthRange.foreground
	}
}

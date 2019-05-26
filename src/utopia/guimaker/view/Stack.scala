package utopia.guimaker.view

import utopia.genesis.color.Color
import utopia.reflection.shape.LengthExtensions._
import utopia.genesis.shape.Axis._
import utopia.genesis.shape.Axis2D
import utopia.genesis.shape.shape2D.{Bounds, Point}
import utopia.genesis.util.Drawer
import utopia.guimaker.model.Margin
import utopia.guimaker.model.MarginRole.{NoMargin, Normal}
import utopia.reflection.component.stack.CachingStackable
import utopia.reflection.container.stack.StackLayout.Fit
import utopia.reflection.container.stack.{StackLayout, StackLike}

/**
  * This is a stack that can hold multiple components
  * @author Mikko Hilpinen
  * @since 25.5.2019, v1+
  */
class Stack extends Component with StackLike[Component] with CachingStackable with Container
{
	// ATTRIBUTES	--------------------
	
	private var _direction: Axis2D = Y
	private var _layout: StackLayout = Fit
	private var _margin = Margin.any(Normal)
	private var _cap = Margin.fixed(NoMargin)
	
	
	// INITIAL CODE	--------------------
	
	setToOptimalSize()
	setupMouseDrag()
	
	
	// IMPLEMENTED	--------------------
	
	// Draw depth is always higher than that of the components
	override def drawDepth = if (isEmpty) 1 else components.map { _.drawDepth }.max + 1
	
	override def direction = _direction
	def direction_=(newDirection: Axis2D) =
	{
		_direction = newDirection
		revalidate()
	}
	
	override def layout = _layout
	def layout_=(newLayout: StackLayout) =
	{
		_layout = newLayout
		revalidate()
	}
	
	override def margin = _margin.length
	def margin_=(margin: Margin) =
	{
		_margin = margin
		revalidate()
	}
	
	override def cap = _cap.length
	def cap_=(newCap: Margin) =
	{
		_cap = newCap
		revalidate()
	}
	
	override def fontMetrics = None
	override def isFull = false
	
	// Draws a slight border
	override protected def drawContent(drawer: Drawer) =
	{
		drawer.withColor(None, Some(Color.black.toAwt)).withAlpha(0.25).draw(Bounds(Point.origin, size))
	}
	
	override protected def updateVisibility(visible: Boolean) = super[Component].isVisible_=(visible)
	
	// TODO: Consider moving component hierarchy & parent changes here. Until then, this is empty
	override protected def add(component: Component) = println(s"Adding $component to stack")
	override protected def remove(component: Component) = println(s"Removing $component from stack")
	
	override def add(component: Component, position: Point) =
	{
		if (isEmpty)
			+=(component)
		else
		{
			// Calculates the new index for the component
			val projection = position.projectedOver(direction).length
			val largerIndex = components.indexWhere { _.position.projectedOver(direction).length >= projection }
			
			if (largerIndex < 0)
				+=(component)
			else
				insert(component, largerIndex)
		}
	}
	
	override def -=(component: Component) = super[StackLike].-=(component)
	
	// When this stack is empty, it will still show itself
	override protected def calculatedStackSize = if (isEmpty) 64.any x 64.any else super.calculatedStackSize
}

package utopia.guimaker.view

import utopia.genesis.shape.Axis._
import utopia.genesis.shape.Axis2D
import utopia.genesis.shape.shape2D.Point
import utopia.genesis.util.Drawer
import utopia.guimaker.controller.Margins
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
	private var _marginId = 0
	
	
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
	
	override def margin = ??? //Margins(_marginId)
	
	/**
	  * @return The cap at each end of this stack
	  */
	override def cap = ???
	
	// Doesn't have any special content to draw
	override protected def drawContent(drawer: Drawer) = Unit
	
	/**
	  * Within this method the stackable instance should perform the actual visibility change
	  * @param visible Whether this stackable should become visible (true) or invisible (false)
	  */
	override protected def updateVisibility(visible: Boolean) = ???
	
	/**
	  * Adds a new item to this container
	  */
	override protected def add(component: Component) = ???
	
	/**
	  * Removes an item from this container
	  */
	override protected def remove(component: Component) = ???
	
	/**
	  * @return The font metrics object for this component. None if font hasn't been specified.
	  */
	override def fontMetrics = ???
	
	/**
	  * @return Whether this container is already full of items
	  */
	override def isFull = ???
	
	/**
	  * Adds a new item to this container
	  * @param component The component to add
	  * @param position  The relative position of the component / cursor at the time of add
	  */
	override def add(component: Component, position: Point) = ???
	
	/**
	  * Removes an item from this container
	  * @param component A component to be removed
	  */
	override def -=(component: Component) = super[StackLike].-=(component)
}

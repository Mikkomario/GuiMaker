package utopia.guimaker.view

import java.time.{Duration, Instant}

import utopia.flow.util.TimeExtensions._
import utopia.genesis.event.{MouseButtonStateEvent, MouseMoveEvent}
import utopia.genesis.handling.{MouseButtonStateListener, MouseMoveListener}
import utopia.genesis.shape.shape2D.Point
import utopia.inception.handling.immutable.Handleable
import utopia.reflection.component.ComponentLike

/**
  * Draggable items can be dragged by the user
  * @author Mikko Hilpinen
  * @since 20.5.2019, v1+
  */
trait Draggable extends ComponentLike
{
	// ATTRIBUTES	-------------
	
	private var _isDragging = false
	
	
	// ABSTRACT	-----------------
	
	/**
	  * @return Whether this draggable item is currently attached to another component
	  */
	def isAttached: Boolean
	
	/**
	  * Detaches this component from other components
	  */
	def detach(): Unit
	
	/**
	  * This method is called when this component is dropped to a new location
	  * @param mousePosition The mouse cursor position at the time of drop
	  */
	protected def drop(mousePosition: Point): Unit
	
	/**
	  * This method is called when this component is "picked up"
	  */
	protected def pick(): Unit
	
	
	// COMPUTED	-----------------
	
	/**
	  * @return Whether this component is currently being dragged
	  */
	def isDragging = _isDragging
	
	/**
	  * @return Whether this component is free-roaming (not attached)
	  */
	def isFree = !isAttached
	
	
	// OTHER	-----------------
	
	/**
	  * Starts mouse listening within this component
	  */
	protected def setupMouseDrag() =
	{
		val listener = new MouseListener()
		addMouseButtonListener(listener)
		addMouseMoveListener(listener)
	}
	
	
	// NESTED CLASSES	---------
	
	private class MouseListener extends MouseButtonStateListener with MouseMoveListener with Handleable
	{
		// ATTRIBUTES	---------
		
		private var relativeDragPosition = Point.origin
		private var lastClickTime = Instant.now()
		private var isDetaching = false
		
		
		// IMPLEMENTED	---------
		
		// When mouse button is pressed, starts dragging (or detaching), when drag ends, releases this component
		override def onMouseButtonState(event: MouseButtonStateEvent) =
		{
			if (event.isLeftMouseButton)
			{
				if (event.isDown)
				{
					// Case: Left mouse click over this component
					if (event.isOverArea(bounds))
					{
						relativeDragPosition = event.mousePosition - position
						lastClickTime = Instant.now()
						
						// Either starts dragging or detaching
						if (isAttached)
							isDetaching = true
						else
						{
							_isDragging = true
							pick()
						}
					}
				}
				// Case: Drag ends
				else if (isDragging)
				{
					// Drops the component
					_isDragging = false
					drop(event.mousePosition)
				}
				// Case: Detaching ends
				else if (isDetaching)
					isDetaching = false
			}
			
			// Will not consume the event
			false
		}
		
		// On mouse move, follows the drag or handles detaching process
		override def onMouseMove(event: MouseMoveEvent) =
		{
			// Case: Dragging
			if (isDragging)
				position = event.mousePosition - relativeDragPosition
			// Case: Detaching
			else if (isDetaching && Instant.now() > lastClickTime + Duration.ofMillis(500))
			{
				detach()
				isDetaching = false
				_isDragging = true
				pick()
				position = event.mousePosition - relativeDragPosition
			}
		}
	}
}

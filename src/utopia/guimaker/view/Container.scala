package utopia.guimaker.view

import utopia.genesis.shape.shape2D.Point

/**
  * Containers are components that can hold other components
  * @author Mikko Hilpinen
  * @since 21.5.2019, v1+
  */
trait Container extends Component with utopia.reflection.container.Container[Component]
{
	// ABSTRACT	------------------
	
	/**
	  * @return Whether this container is already full of items
	  */
	def isFull: Boolean
	
	/**
	  * Adds a new item to this container
	  * @param component The component to add
	  * @param position The relative position of the component / cursor at the time of add
	  */
	def add(component: Component, position: Point): Unit
	
	
	// OPERATORS	-------------
	
	/**
	  * Adds a new item to this container
	  * @param component A component to be added
	  * @param relativePosition A point relative to this container that shows the 'drop location' of the component
	  */
	def +=(component: Component, relativePosition: Point) = if (!isFull) add(component, relativePosition)
	
	/**
	  * Removes an item from this container
	  * @param component A component to be removed
	  */
	def -=(component: Component) = remove(component)
}

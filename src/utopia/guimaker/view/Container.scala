package utopia.guimaker.view

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
	
	
	// OPERATORS	-------------
	
	/**
	  * Adds a new item to this container
	  * @param component A component to be added
	  */
	def +=(component: Component) = if (!isFull) add(component)
	
	/**
	  * Removes an item from this container
	  * @param component A component to be removed
	  */
	def -=(component: Component) = remove(component)
}

package utopia.guimaker.controller

import utopia.flow.util.CollectionExtensions._
import utopia.flow.datastructure.immutable.Tree
import utopia.genesis.shape.shape2D.Point
import utopia.guimaker.view.Component
import utopia.reflection.container.stack.StackHierarchyManager

/**
  * This object keeps track of all components and their hierarchies with each other
  * @author Mikko Hilpinen
  * @since 20.5.2019, v1+
  */
object ComponentHierarchy
{
	 // ATTRIBUTES	----------------
	
	private var components = Vector[Tree[Component]]()
	
	
	// COMPUTED	--------------------
	
	/**
	  * @return The registered free / root components
	  */
	def rootComponents = components.map { _.content }
	
	/**
	  * @return All registered components
	  */
	def allComponents = components.flatMap { _.allContent }
	
	
	// OTHER	--------------------
	
	/**
	  * Adds a component as a free component (shouldn't exist in the hierarchy yet)
	  * @param component A component
	  */
	def registerFree(component: Component) =
	{
		components :+= Tree(component)
		StackHierarchyManager.registerIndividual(component)
	}
	
	/**
	  * Detaches a component from its parent and adds it as a free component
	  * @param component A component
	  */
	def detach(component: Component) =
	{
		components = componentsWithout(component) :+ Tree(component)
		StackHierarchyManager.registerIndividual(component)
	}
	
	/**
	  * Finds the topmost component at the specified location
	  * @param point Target location
	  * @return The topmost component at the specified location. None if there isn't any component at that location
	  */
	def componentAtLocation(point: Point) = components.findMap { findTopComponentAt(point, _) }
	
	/**
	  * Registers a connection between two components
	  * @param parent The component that will host the child component
	  * @param child The component that will be registered as a child
	  */
	def registerConnection(parent: Component, child: Component) =
	{
		// First detaches the child from its current location
		// Replaces the parent node with one that also contains the child
		components = componentsWithout(child).map { _.findAndReplace(_.content == parent, _ + Tree(child)) }
		StackHierarchyManager.registerConnection(parent, child)
	}
	
	private def componentsWithout(component: Component) =
	{
		if (components.exists { _.content == component })
			components.filterNot { _.content == component }
		else
			components.map { _ - component }
	}
	
	private def findTopComponentAt(point: Point, source: Tree[Component]): Option[Component] =
	{
		// If point is within component, searches the bottom component that contains the point
		if (source.content.bounds.contains(point))
		{
			val childAtPoint = source.children.findMap { findTopComponentAt(point, _) }
			Some(childAtPoint getOrElse source.content)
		}
		else
			None
	}
}

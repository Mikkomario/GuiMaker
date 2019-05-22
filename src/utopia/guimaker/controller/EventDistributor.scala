package utopia.guimaker.controller

import utopia.genesis.event.{KeyStateEvent, KeyTypedEvent, MouseButtonStateEvent, MouseMoveEvent, MouseWheelEvent}
import utopia.genesis.handling.{KeyStateListener, KeyTypedListener, MouseButtonStateListener, MouseMoveListener, MouseWheelListener}
import utopia.inception.handling.immutable.Handleable

/**
  * This class distributes events to components
  * @author Mikko Hilpinen
  * @since 22.5.2019, v1+
  */
class EventDistributor extends MouseButtonStateListener with MouseMoveListener with MouseWheelListener with
	KeyStateListener with KeyTypedListener with Handleable
{
	override def onMouseButtonState(event: MouseButtonStateEvent) =
		event.distributeAmong(ComponentHierarchy.rootComponents) { _.distributeMouseButtonEvent(_) }
	
	override def onMouseMove(event: MouseMoveEvent) = ComponentHierarchy.rootComponents.foreach {
		_.distributeMouseMoveEvent(event) }
	
	override def onMouseWheelRotated(event: MouseWheelEvent) =
		event.distributeAmong(ComponentHierarchy.rootComponents) { _.distributeMouseWheelEvent(_) }
	
	override def onKeyState(event: KeyStateEvent) = ComponentHierarchy.rootComponents.foreach {
		_.distributeKeyStateEvent(event) }
	
	override def onKeyTyped(event: KeyTypedEvent) = ComponentHierarchy.rootComponents.foreach {
		_.distributeKeyTypedEvent(event) }
}

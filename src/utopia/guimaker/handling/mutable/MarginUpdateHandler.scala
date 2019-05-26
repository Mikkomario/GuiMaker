package utopia.guimaker.handling.mutable

import utopia.guimaker.handling
import utopia.guimaker.handling.MarginUpdateListener
import utopia.inception.handling.mutable.DeepHandler

object MarginUpdateHandler
{
	def apply(elements: TraversableOnce[MarginUpdateListener] = Vector()) = new MarginUpdateHandler(elements)
	
	def apply(element: MarginUpdateListener): MarginUpdateHandler = apply(Vector(element))
	
	def apply(first: MarginUpdateListener, second: MarginUpdateListener, more: MarginUpdateListener*): MarginUpdateHandler =
		apply(Vector(first, second) ++ more)
}

/**
  * This is a mutable implementation of the margin update handler
  * @author Mikko Hilpinen
  * @since 26.5.2019, v1+
  */
class MarginUpdateHandler(elements: TraversableOnce[MarginUpdateListener])
	extends DeepHandler[MarginUpdateListener](elements) with handling.MarginUpdateHandler

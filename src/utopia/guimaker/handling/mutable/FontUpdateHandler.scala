package utopia.guimaker.handling.mutable

import utopia.guimaker.handling
import utopia.guimaker.handling.FontUpdateListener
import utopia.inception.handling.mutable.DeepHandler

object FontUpdateHandler
{
	def apply(elements: TraversableOnce[FontUpdateListener] = Vector()) = new FontUpdateHandler(elements)
	
	def apply(element: FontUpdateListener): FontUpdateHandler = apply(Vector(element))
	
	def apply(first: FontUpdateListener, second: FontUpdateListener, more: FontUpdateListener*): FontUpdateHandler = apply(Vector(first, second) ++ more)
}

/**
  * This is a mutable implementation of the fontUpdateHandler trait
  * @author Mikko Hilpinen
  * @since 21.5.2019, v1+
  */
class FontUpdateHandler(initialElements: TraversableOnce[FontUpdateListener]) extends
	DeepHandler[FontUpdateListener](initialElements) with handling.FontUpdateHandler

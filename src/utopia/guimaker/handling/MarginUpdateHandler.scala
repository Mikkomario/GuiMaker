package utopia.guimaker.handling

import utopia.guimaker.handling.MarginUpdateHandler.MarginUpdateHandlerType
import utopia.inception.handling.{Handler, HandlerType}

object MarginUpdateHandler
{
	case object MarginUpdateHandlerType extends HandlerType
	{
		override def supportedClass = classOf[MarginUpdateListener]
	}
}

/**
  * This handler informs multiple instances about margin updates
  * @author Mikko Hilpinen
  * @since 26.5.2019, v1+
  */
trait MarginUpdateHandler extends Handler[MarginUpdateListener] with MarginUpdateListener
{
	override def handlerType = MarginUpdateHandlerType
	
	override def onMarginsUpdated() = handle { _.onMarginsUpdated() }
}

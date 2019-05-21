package utopia.guimaker.handling

import utopia.guimaker.handling.FontUpdateHandler.FontUpdateHandlerType
import utopia.inception.handling.{Handler, HandlerType}

object FontUpdateHandler
{
	case object FontUpdateHandlerType extends HandlerType
	{
		override def supportedClass = classOf[FontUpdateListener]
	}
}

/**
  * This handler informs multiple instances about font updates
  * @author Mikko Hilpinen
  * @since 21.5.2019, v1+
  */
trait FontUpdateHandler extends Handler[FontUpdateListener] with FontUpdateListener
{
	override def handlerType = FontUpdateHandlerType
	
	override def onFontsUpdated() = handle { _.onFontsUpdated() }
}

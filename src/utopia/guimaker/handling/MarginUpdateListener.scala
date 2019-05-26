package utopia.guimaker.handling

import utopia.inception.handling.Handleable

/**
  * These instances are interested margin specification changes
  * @author Mikko Hilpinen
  * @since 26.5.2019, v1+
  */
trait MarginUpdateListener extends Handleable
{
	/**
	  * This method is called each time the margins are updated
	  */
	def onMarginsUpdated(): Unit
}

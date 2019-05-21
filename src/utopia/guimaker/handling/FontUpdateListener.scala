package utopia.guimaker.handling

import utopia.inception.handling.Handleable

/**
  * Font update listeners are informed of font changes
  * @author Mikko Hilpinen
  * @since 21.5.2019, v1+
  */
trait FontUpdateListener extends Handleable
{
	/**
	  * This method will be called when the fonts are updated
	  */
	def onFontsUpdated(): Unit
}

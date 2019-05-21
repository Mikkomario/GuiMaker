package utopia.guimaker.controller

import utopia.flow.util.Counter
import utopia.guimaker.handling.mutable.FontUpdateHandler
import utopia.reflection.text.Font

import scala.collection.immutable.HashMap

/**
  * This object keeps track of all the different fonts selected by the user
  * @author Mikko Hilpinen
  * @since 21.5.2019, v1+
  */
object Fonts
{
	// ATTRIBUTES	------------------
	
	private val indexCounter = new Counter(1, 1)
	
	private var _defaultFont = Font("Arial", 14)
	private var additionalFonts = HashMap[Int, Font]()
	private var fontDisplayNames = HashMap(0 -> "Default")
	
	/**
	  * This handler keeps track of all the font update listeners
	  */
	val updateHandler = FontUpdateHandler()
	
	
	// COMPUTED	-----------------------
	
	/**
	  * @return The font used by default, when no other fonts are available
	  */
	def defaultFont = _defaultFont
	def defaultFont_=(newDefault: Font) =
	{
		_defaultFont = newDefault
		informListeners()
	}
	
	/**
	  * @return The indices of all fonts registered to this object
	  */
	def registeredIds = 0 +: additionalFonts.keys.toVector
	
	
	// OPERATORS	-------------------
	
	/**
	  * Updates a font in these settings
	  * @param index The font's index
	  * @param font The new font
	  */
	def update(index: Int, font: Font) =
	{
		// The 0 index is reserved for the default font
		if (index == 0)
			defaultFont = font
		else
		{
			val existed = additionalFonts.contains(index)
			additionalFonts += (index -> font)
			if (existed)
				informListeners()
		}
	}
	
	/**
	  * Finds a font
	  * @param index The font's index
	  * @return A font for that index or the default font
	  */
	def apply(index: Int) = additionalFonts.getOrElse(index, defaultFont)
	
	
	// OTHER	------------------------
	
	/**
	  * Registers a new font to this object
	  * @param font The font
	  * @param name Font name
	  * @return the index for the new font
	  */
	def register(font: Font, name: String) =
	{
		val index = indexCounter.next()
		fontDisplayNames += (index -> name)
		update(index, font)
		index
	}
	
	/**
	  * @param index Font index
	  * @return The name for the font
	  */
	def nameOf(index: Int) = fontDisplayNames.getOrElse(index, "Unnamed Font")
	
	private def informListeners() = updateHandler.onFontsUpdated()
}

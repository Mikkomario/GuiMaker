package utopia.guimaker.model

import utopia.guimaker.model.LengthType._
import utopia.guimaker.controller.Margins

object Margin
{
	/**
	  * Creates a new margin that allows varying values
	  * @param value The optimal value of this margin
	  * @return A new margin
	  */
	def any(value: MarginRole) = new Margin(Free, value)
	
	/**
	  * Creates a new margin that is fixed to a single value
	  * @param value The fixed value of the margin
	  * @return A new margin
	  */
	def fixed(value: MarginRole) = new Margin(Fixed, value)
}

/**
  * This class represents a margin that may be adjusted by user by modifying global margins
  * @author Mikko Hilpinen
  * @since 26.5.2019, v1+
  */
class Margin(val lengthType: LengthType, primary: MarginRole, more: Seq[MarginRole] = Vector())
{
	// ATTRIBUTES	-------------------
	
	private val values = primary +: more
	
	
	// COMPUTED	-----------------------
	
	/**
	  * @return The length of this margin (up-to-date)
	  */
	def length = lengthType(values.map { Margins(_) })
}

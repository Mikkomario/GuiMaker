package utopia.guimaker.controller

import scala.collection.immutable.HashMap

/**
  * This object keeps track of the margins specified by the user
  * @author Mikko Hilpinen
  * @since 21.5.2019, v1+
  */
object Margins
{
	// ATTRIBUTES	-----------------
	
	// TODO: Make this mutable and add events for changes
	private val margins = HashMap[Int, Int](0 -> 16, 1 -> 4, 2 -> 8, 3 -> 24, 4 -> 32)
	
	
	// COMPUTED	---------------------
	
	/**
	  * @return The default margin used in most situations
	  */
	def default = margins(0)
	/**
	  * @return The very small margin used for a small border, etc.
	  */
	def verySmall = margins(1)
	/**
	  * @return A small margin used between associated items
	  */
	def small = margins(2)
	/**
	  * @return A large margin used when there's a lot of space
	  */
	def large = margins(3)
	/**
	  * @return A very large margin used to separate large concepts when there's a lot of space
	  */
	def veryLarge = margins(4)
	
	
	// OPERATORS	-----------------
	
	/**
	  * @param index Margin index
	  * @return Margin for the index
	  */
	def apply(index: Int) = margins.getOrElse(index, default)
}

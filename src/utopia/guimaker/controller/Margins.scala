package utopia.guimaker.controller

import utopia.flow.datastructure.mutable.PointerWithEvents
import utopia.guimaker.model.MarginRole
import utopia.guimaker.model.MarginRole._

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
	private val margins = HashMap[MarginRole, Int](Normal -> 16, VerySmall -> 4, Small -> 8, Large -> 24, VeryLarge -> 32).map {
		p => p._1 -> new PointerWithEvents(p._2) }
	
	
	// COMPUTED	---------------------
	
	/**
	  * @return The default margin used in most situations
	  */
	def normal = margins(Normal)
	/**
	  * @return The very small margin used for a small border, etc.
	  */
	def verySmall = margins(VerySmall)
	/**
	  * @return A small margin used between associated items
	  */
	def small = margins(Small)
	/**
	  * @return A large margin used when there's a lot of space
	  */
	def large = margins(Large)
	/**
	  * @return A very large margin used to separate large concepts when there's a lot of space
	  */
	def veryLarge = margins(VeryLarge)
	
	
	// OPERATORS	-----------------
	
	/**
	  * @param role Margin role
	  * @return Margin for the role
	  */
	def apply(role: MarginRole) = margins.getOrElse(role, normal)
}

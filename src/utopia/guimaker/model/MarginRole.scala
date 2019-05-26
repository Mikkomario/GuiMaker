package utopia.guimaker.model

object MarginRole
{
	case object Normal extends MarginRole { def name = "Normal" }
	case object VerySmall extends MarginRole { def name = "Very Small" }
	case object Small extends MarginRole { def name = "Small" }
	case object Large extends MarginRole { def name = "Large" }
	case object VeryLarge extends MarginRole { def name = "Very Large" }
}

/**
  * These roles list the different margin categories
  * @author Mikko Hilpinen
  * @since 26.5.2019, v1+
  */
trait MarginRole extends Equals
{
	/**
	  * @return The name of this role
	  */
	def name: String
}

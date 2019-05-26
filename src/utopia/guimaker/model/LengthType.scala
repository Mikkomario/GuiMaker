package utopia.guimaker.model

import utopia.flow.util.CollectionExtensions._
import utopia.reflection.shape.StackLength

object LengthType
{
	object Free extends LengthType
	{
		override def apply(primary: Int, secondary: Option[Int], tertiary: Option[Int]) = StackLength.any(primary)
	}
	
	object Fixed extends LengthType
	{
		override def apply(primary: Int, secondary: Option[Int], tertiary: Option[Int]) = StackLength.fixed(primary)
	}
	
	object Downscaling extends LengthType
	{
		override def apply(primary: Int, secondary: Option[Int], tertiary: Option[Int]) =
			secondary.map { StackLength(_, primary, Some(primary)) } getOrElse StackLength.downscaling(primary)
	}
	
	object Upscaling extends LengthType
	{
		override def apply(primary: Int, secondary: Option[Int], tertiary: Option[Int]) =
			secondary.map { m => StackLength(primary, primary, Some(m)) } getOrElse StackLength.upscaling(primary)
	}
	
	object Custom extends LengthType
	{
		override def apply(primary: Int, secondary: Option[Int], tertiary: Option[Int]) = StackLength(
			secondary getOrElse 0, primary, tertiary)
	}
}

/**
  * These types are used for constructing different types of stack lengths
  * @author Mikko Hilpinen
  * @since 25.5.2019, v1+
  */
sealed trait LengthType
{
	/**
	  * Creates a new stack length. All of the provided values may not always be used
	  * @param primary The primary value for the length
	  * @param secondary The secondary value for the length
	  * @param tertiary The tertiary value for the length
	  * @return A stack length of this type
	  */
	def apply(primary: Int, secondary: Option[Int], tertiary: Option[Int]): StackLength
	
	/**
	  * Creates a new stack length
	  * @param primary The primary length value
	  * @param more Additional length values
	  * @return A stack length of this type
	  */
	def apply(primary: Int, more: Int*): StackLength = apply(primary, more.headOption, more.getOption(1))
	
	/**
	  * Creates a new stack length
	  * @param lengths The lengths that form this length. Must contain at least one value.
	  * @return A stack length of this type
	  */
	def apply(lengths: Seq[Int]): StackLength = apply(lengths.head, lengths.getOption(1), lengths.getOption(2))
}

package utopia.guimaker.model

import utopia.guimaker.model.LengthType._
import utopia.flow.datastructure.mutable.Lazy
import utopia.flow.event.{ChangeEvent, ChangeListener, Changing}
import utopia.guimaker.controller.Margins
import utopia.inception.handling.mutable.Killable
import utopia.reflection.shape.StackLength

object Margin
{
	/**
	  * Creates a new margin that allows varying values
	  * @param value The optimal value of this margin
	  * @return A new margin
	  */
	def any(value: MarginRole) = new Margin(Free, value)
}

/**
  * This class represents a margin that may be adjusted by user by modifying global margins
  * @author Mikko Hilpinen
  * @since 26.5.2019, v1+
  */
class Margin(val lengthType: LengthType, primary: MarginRole, more: Seq[MarginRole] = Vector()) extends
	Changing[StackLength] with Killable
{
	// ATTRIBUTES	-------------------
	
	private val values = primary +: more
	private val _length = new Lazy(() => lengthType(values.map { Margins(_).get }))
	private val listener = new MarginChangeListener()
	
	
	// INITIAL CODE	------------------
	
	// Listens to all used margins
	values.foreach { Margins(_).addListener(listener) }
	
	
	// IMPLEMENTED	------------------
	
	override def value = _length.get
	
	override def kill() =
	{
		values.foreach { Margins(_).removeListener(listener) }
		super.kill()
	}
	
	
	// NESTED CLASSES	--------------
	
	private class MarginChangeListener extends ChangeListener[Int]
	{
		override def onChangeEvent(event: ChangeEvent[Int]) =
		{
			val oldValue = _length.get
			_length.reset()
			fireChangeEvent(oldValue)
		}
	}
}

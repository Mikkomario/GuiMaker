package utopia.guimaker.view
import utopia.genesis.color.Color
import utopia.genesis.util.Drawer
import utopia.reflection.component.Alignment.Center
import utopia.reflection.component.stack.CachingStackable
import utopia.reflection.component.{Alignable, Alignment, TextComponent}
import utopia.reflection.localization.{LocalizedString, Localizer}
import utopia.reflection.text.Font

/**
  * This component simply presents text
  * @author Mikko Hilpinen
  * @since 21.5.2019, v1+
  */
class TextLabel()(implicit defaultLanguageCode: String, localizer: Localizer) extends Component with Alignable
	with TextComponent with CachingStackable
{
	// ATTRIBUTES	------------------
	
	private var _text: LocalizedString = "Label"
	private var _alignment: Alignment = Center
	// private var _font: Font = Font.
	
	
	// IMPLEMENTED	------------------
	
	/**
	  * Draws the contents inside this component
	  * @param drawer A drawer with origin at this component's origin and clipped to this component's size
	  */
	override protected def drawContent(drawer: Drawer) = ???
	
	/**
	  * Updates the layout (and other contents) of this stackable instance. This method will be called if the component,
	  * or its child is revalidated. The stack sizes of this component, as well as those of revalidating children
	  * should be reset at this point.
	  */
	override def updateLayout() = ???
	
	/**
	  * This method is called when this component is "picked up"
	  */
	override protected def pick() = ???
	
	/**
	  * @return The font metrics object for this component. None if font hasn't been specified.
	  */
	override def fontMetrics = ???
	
	/**
	  * @return This component's text alignment
	  */
	override def alignment = ???
	
	/**
	  * @return The text currently presented in this component
	  */
	override def text = ???
	
	/**
	  * Aligns the contents of this component
	  * @param alignment The target alignment
	  */
	override def align(alignment: Alignment) = ???
	
	/**
	  * @return The margins around the text in this component
	  */
	override def margins = ???
	
	/**
	  * @return The font used in this component
	  */
	override def font = ???
	
	/**
	  * @return Whether this component has a minimum width based on text size. If false, text may not always show.
	  */
	override def hasMinWidth = ???
	
	/**
	  * @return The color of the text in this component
	  */
	override def textColor = ???
	
	override def textColor_=(newColor: Color) = ???
	
	/**
	  * Within this method the stackable instance should perform the actual visibility change
	  * @param visible Whether this stackable should become visible (true) or invisible (false)
	  */
	override protected def updateVisibility(visible: Boolean) = ???
}

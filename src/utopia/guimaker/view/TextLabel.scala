package utopia.guimaker.view
import java.awt.FontMetrics

import utopia.genesis.color.Color
import utopia.genesis.shape.shape2D.{Point, Size}
import utopia.genesis.util.Drawer
import utopia.guimaker.controller.Fonts
import utopia.guimaker.handling.FontUpdateListener
import utopia.reflection.component.Alignment.Center
import utopia.reflection.component.stack.CachingStackable
import utopia.reflection.component.{Alignable, Alignment, TextComponent}
import utopia.reflection.localization.{LocalizedString, Localizer}
import utopia.reflection.shape.{StackLength, StackSize}

/**
  * This component simply presents text
  * @author Mikko Hilpinen
  * @since 21.5.2019, v1+
  */
class TextLabel()(implicit defaultLanguageCode: String, localizer: Localizer) extends Component with Alignable
	with FontUpdateListener with TextComponent with CachingStackable
{
	// ATTRIBUTES	------------------
	
	// TODO: Add setters for font & margins
	private var _text: LocalizedString = "Label"
	private var _alignment: Alignment = Center
	private val fontIndex = 0
	private var _fontMetrics: Option[FontMetrics] = None
	private val _hMargin = StackLength.any
	private val _vMargin = StackLength.any
	private var _textColor = Color.textBlack
	
	
	// INITIAL CODE	------------------
	
	size = Size(128, 32)
	setupMouseDrag()
	
	
	// IMPLEMENTED	------------------
	
	override protected def drawContent(drawer: Drawer) =
	{
		// Updates font metrics if necessary
		if (_fontMetrics.isEmpty)
		{
			_fontMetrics = Some(drawer.graphics.getFontMetrics(font.toAwt))
			revalidate()
		}
		
		// Calculates text size
		_fontMetrics.foreach
		{
			metrics =>
				val textWidth = metrics.stringWidth(text.string)
				val textHeight = metrics.getHeight
				
				// Determines text location
				val textX = alignment match
				{
					case Alignment.Left => 0
					case Alignment.Right => width - textWidth
					case _ => (width - textWidth) / 2
				}
				val textY = alignment.vertical match
				{
					case Alignment.Top => 0
					case Alignment.Bottom => height - textHeight
					case _ => (height - textHeight) / 2
				}
			
				// Draws the actual text
				drawer.withEdgeColor(textColor).drawText(_text.string, font.toAwt, Point(textX, textY))
		}
	}
	
	override def updateLayout() =
	{
		println("Updating text label layout")
		if (isFree) setToOptimalSize()
	}
	
	override def fontMetrics = _fontMetrics
	
	override def alignment = _alignment
	
	override def text = _text
	def text_=(newText: LocalizedString) =
	{
		_text = newText
		revalidate()
	}
	
	override def align(alignment: Alignment) =
	{
		_alignment = alignment
		revalidate()
	}
	
	override def margins = StackSize(_hMargin, _vMargin)
	
	override def hMargin = _hMargin
	
	override def vMargin = _vMargin
	
	override def font = Fonts(fontIndex)
	
	override def hasMinWidth = true
	
	override def textColor = _textColor
	override def textColor_=(newColor: Color) = _textColor = newColor
	
	override protected def updateVisibility(visible: Boolean) = super[Component].isVisible_=(visible)
	
	// Resets the font metrics on font change (this will revalidate this component on draw)
	override def onFontsUpdated() = _fontMetrics = None
}

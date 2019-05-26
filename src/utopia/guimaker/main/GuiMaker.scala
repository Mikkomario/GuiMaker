package utopia.guimaker.main

import utopia.reflection.shape.LengthExtensions._
import utopia.flow.async.ThreadPool
import utopia.genesis.color.Color
import utopia.genesis.generic.GenesisDataType
import utopia.genesis.handling.ActorLoop
import utopia.genesis.handling.mutable.{ActorHandler, DrawableHandler, MouseButtonStateHandler, MouseMoveHandler, MouseWheelHandler}
import utopia.genesis.shape.shape2D.{Point, Size}
import utopia.genesis.util.FPS
import utopia.guimaker.controller.{ComponentHierarchy, EventDistributor, Fonts}
import utopia.guimaker.view.{Stack, TextLabel}
import utopia.inception.handling.mutable.HandlerRelay
import utopia.reflection.component.swing.ScrollCanvas
import utopia.reflection.container.stack.{BoxScrollBarDrawer, StackHierarchyManager}
import utopia.reflection.container.swing.window.Frame
import utopia.reflection.localization.{Localizer, NoLocalization}

import scala.concurrent.ExecutionContext

/**
  * This is the main application for the Gui-Maker project
  * @author Mikko Hilpinen
  * @since 22.5.2019, v1+
  */
object GuiMaker extends App
{
	GenesisDataType.setup()
	
	// Creates the handlers
	val actorHandler = ActorHandler()
	val drawHandler = DrawableHandler()
	val mouseButtonHandler = MouseButtonStateHandler()
	val mouseWheelHandler = MouseWheelHandler()
	val mouseMoveHandler = MouseMoveHandler()
	
	val handlers = HandlerRelay(actorHandler, drawHandler, mouseButtonHandler, mouseWheelHandler, mouseMoveHandler,
		Fonts.updateHandler)
	
	// Creates the drawable items
	val worldSize = Size(1400, 700)
	
	implicit val defaultLanguageCode: String = "EN"
	implicit val localizer: Localizer = NoLocalization
	
	val testLabel = new TextLabel()
	testLabel.background = Color.yellow
	testLabel.borderColor = Some(Color.textBlack)
	testLabel.position = Point(32, 32)
	ComponentHierarchy.registerFree(testLabel)
	
	val stack = new Stack()
	stack.position = worldSize.toPoint / 2
	
	handlers ++= (testLabel, stack)
	
	// Creates the canvas
	val canvas = new ScrollCanvas(worldSize, drawHandler, actorHandler, mouseButtonHandler, mouseMoveHandler,
		mouseWheelHandler, 16, BoxScrollBarDrawer(Color.black, Color.gray(0.5)),
		16, false, None)
	
	handlers += new EventDistributor()
	
	// Creates the frame and displays it
	val actionLoop = new ActorLoop(actorHandler)
	implicit val context: ExecutionContext = new ThreadPool("GuiMaker").executionContext
	
	val framing = canvas.framed(0.any x 0.any)
	framing.background = Color.gray(0.2)
	val frame = Frame.windowed(framing, "GuiMaker")
	frame.setToExitOnClose()
	
	actionLoop.registerToStopOnceJVMCloses()
	actionLoop.startAsync()
	StackHierarchyManager.startRevalidationLoop()
	frame.startEventGenerators(actorHandler)
	canvas.startDrawing(FPS(30))
	frame.isVisible = true
	
	println(stack.bounds)
}

/**
 * ------------------------------------------------------------------------
 * Elmo Escape
 * Level 3 programming project
 *
 * Myles Smalley
 *
 * You are in Elmo's dungeon, and are trying to escape.
 * Watch out, he could be anywhere...
 * ------------------------------------------------------------------------
 */


import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLightLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


//=============================================================================================
/**
 * Room Class
 */
class Room(val name: String) {
    var leadingTo: Room? = null
    var nextRoom: Room? = null


    fun addLeadingTo(room: Room) {
        if (leadingTo == null) {
            leadingTo = room
            room.addNextRoom(this)
        }
    }

    fun addNextRoom(room: Room) {
        if (nextRoom == null) {
            nextRoom = room
            room.addLeadingTo(this)
        }
    }

    fun info() {
        println("Hello, I'm $name")
        if (leadingTo != null) {
            println("The room that leads to me is ${leadingTo!!.name}")
        } else {
            println("I have no other room")
        }

        if (nextRoom != null) {
            println("The room I lead to is ${nextRoom!!.name}")
        } else {
            println("I don't lead to a room")
        }
    }
}

//=============================================================================================

/**
 * GUI class
 * Defines the UI and responds to events
 */
class GUI : JFrame(), ActionListener {

    // Data Store
    val locations = mutableListOf<Room>()
    val currentRoom: Room

    // Setup some properties to hold the UI elements
    private lateinit var exampleLabel: JLabel
    private lateinit var exampleButton: JButton

    /**
     * Create, build and run the UI
     */
    init {
        setupRooms()
        setupWindow()
        buildUI()


        // Show the app, centred on screen
        setLocationRelativeTo(null)
        isVisible = true

        currentRoom = locations.first()
        showRoom()
    }

    fun setupRooms() {
        val startRoom = Room("Start")
        val hallway1 = Room("Hallway")
//        val funRoom = Room("Fun Room")
//        val hallway2 = Room("Start")
//        val fridge = Room("Start")
//        val hallway3 = Room("Hallway")
//        val  = Room("Fun Room")
//        val start = Room("Start")
//        val exit = Room("Start")

        location.add(startRoom)
        location.add(hallway1)

        startRoom.addNextRoom(hallway1)

        startRoom.info()

    }

    /**
     * Configure the main window
     */
    private fun setupWindow() {
        title = "Hello, World!"
        contentPane.preferredSize = Dimension(300, 170)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI
     */
    private fun buildUI() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 20)

        exampleLabel = JLabel("Hello, World!", SwingConstants.CENTER)
        exampleLabel.bounds = Rectangle(30, 30, 240, 40)
        exampleLabel.font = baseFont
        add(exampleLabel)

        exampleButton = JButton("Click Me")
        exampleButton.bounds = Rectangle(30,100,240,40)
        exampleButton.font = baseFont
        exampleButton.addActionListener(this)
        add(exampleButton)
    }

    /**
     * Handle any UI events
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            exampleButton -> exampleAction()
        }
    }

    /**
     * An Example Action
     */
    private fun exampleAction() {
        exampleLabel.text = "You Clicked!"
    }
}


//=============================================================================================

/**
 * Launch the application
 */
fun main() {
    // Flat, Dark look-and-feel
    FlatDarkLaf.setup()

    // Create the UI
    GUI()
}



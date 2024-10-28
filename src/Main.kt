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
class Room(val name: String, val description: String ) {
    var cameFrom: Room? = null
    var north: Room? = null


    fun addcameFrom(room: Room) {
        if (cameFrom == null) {
            cameFrom = room
            room.addNextRoom(this)
        }
    }

    fun addNextRoom(room: Room) {
        if (north == null) {
            north = room
            room.addcameFrom(this)
        }
    }

    fun info() {
        println("Hello, I'm $name")
        if (cameFrom != null) {
            println("The room that leads to me is ${cameFrom!!.name}")
        } else {
            println("I have no other room")
        }

        if (north != null) {
            println("The room I lead to is ${north!!.name}")
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
    var currentRoom: Room

    // Setup some properties to hold the UI elements

    private lateinit var northButton: JButton
    private lateinit var southButton: JButton
    private lateinit var westButton: JButton
    private lateinit var eastButton: JButton
    private lateinit var roomLabel: JLabel

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
//        showRoom()
    }

    fun setupRooms() {
        val startRoom = Room("Bedroom", "...")
        val funRoom = Room("Fun Room", "...")
        val fridge = Room("The Fridge", "...")
        val cells = Room("Cells", "...")
        val tickle = Room("Tickle Chamber", "...")
        val exit = Room("Exit", "...")

        locations.add(startRoom)
        locations.add(funRoom)
        locations.add(fridge)
        locations.add(cells)
        locations.add(exit)

        startRoom.addNextRoom(funRoom)
        funRoom.addNextRoom(tickle)
        tickle.addNextRoom(fridge)
        fridge.addNextRoom(cells)
        cells.addNextRoom(exit)

        startRoom.info()

    }

    /**
     * Configure the main window
     */
    private fun setupWindow() {
        title = "Elmo Escape Pro"
        contentPane.preferredSize = Dimension(400, 500)
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
        val smallFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

        northButton = JButton("North")
        northButton.bounds = Rectangle(163,234,90,32)
        northButton.font = smallFont
        northButton.addActionListener(this)
        add(northButton)

        southButton = JButton("South")
        southButton.bounds = Rectangle(163,398,90,32)
        southButton.font = smallFont
        southButton.addActionListener(this)
        add(southButton)

        westButton = JButton("West")
        westButton.bounds = Rectangle(27,307,90,32)
        westButton.font = smallFont
        westButton.addActionListener(this)
        add(westButton)

        eastButton = JButton("East")
        eastButton.bounds = Rectangle(290,307,90,32)
        eastButton.font = smallFont
        eastButton.addActionListener(this)
        add(eastButton)
    }

    /**
     * Handle any UI events
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            northButton ->gotoNorth()
            southButton -> gotoSouth()
        }
    }
    /**
     * An Example Action
     */
    private fun gotoSouth() {
        // Does this person have a parent?
        if (currentRoom.cameFrom != null) {
            // If so, let's point at that parent...
            currentRoom = currentRoom.cameFrom!!
            // And update the UI to show them
//            showRoom()
        }
    }

    private fun gotoNorth() {
        // Does this person have a child?
        if (currentRoom.north != null) {
            // If so, let's point at that child...
            currentRoom = currentRoom.north!!
            // And update the UI to show them
//            showRoom()
        }
    }

//    private fun showRoom() {
//        if (currentRoom.cameFrom != null) {
//            parentLabel.text = currentRoom.cameFrom?.name
//        } else {
//            parentLabel.text = "No Room"
//        }
//
//        if (currentRoom.north != null) {
//            childLabel.text = currentRoom.north?.name
//        } else {
//            childLabel.text = "No Room"
//        }
//
//        personLabel.text = currentRoom.name
//
//    }
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



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
import java.awt.*
import java.awt.event.*
import javax.swing.*




//=============================================================================================
/**
 * Room Class
 */
class Room(val name: String, val description: String ) {
    var south: Room? = null
    var north: Room? = null
    var east: Room? = null
    var west: Room? = null


    fun addNorth(room: Room) {
        if (north == null) {
            north = room
            room.addSouth(this)
        }
    }

    fun addSouth(room: Room) {
        if (south == null) {
            south = room
            room.addNorth(this)
        }
    }

    fun addWest(room: Room) {
        if (west == null) {
            west = room
            room.addEast(this)
        }
    }

    fun addEast(room: Room) {
        if (east == null) {
            east = room
            room.addWest(this)
        }
    }

    fun info() {
        println("Hello, I'm $name, $description")
        if (south != null) {
            println("The room that leads to me is ${south!!.name}")
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
    private lateinit var roomNameLabel: JLabel
    private lateinit var roomDescLabel: JLabel

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
        val startRoom = Room("Bedroom", "...")
        val funRoom = Room("Fun Room", "...")
        val fridge = Room("The Fridge", "...")
        val cells = Room("Cells", "...")
        val tickle = Room("Tickle Chamber", "...")
        val lair = Room("Lair", "...")
        val exit = Room("Exit", "...")

        locations.add(startRoom)
        locations.add(funRoom)
        locations.add(fridge)
        locations.add(cells)
        locations.add(lair)
        locations.add(exit)

        startRoom.addNorth(tickle)
        tickle.addEast(fridge)
        tickle.addWest(funRoom)
        tickle.addSouth(startRoom)
        funRoom.addNorth(cells)
        cells.addEast(lair)
        cells.addWest(exit)

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

        roomNameLabel = JLabel("Room:", SwingConstants.CENTER)
        roomNameLabel.bounds = Rectangle(138, 21, 134, 43)
        roomNameLabel.font = baseFont
        add(roomNameLabel)

        roomDescLabel = JLabel("Room:", SwingConstants.CENTER)
        roomDescLabel.bounds = Rectangle(102, 76, 205, 146)
        roomDescLabel.font = smallFont
        add(roomDescLabel)

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
            eastButton -> gotoEast()
            westButton -> gotoWest()
        }
    }
    /**
     * An Example Action
     */
    private fun gotoSouth() {
        // Does this person have a parent?
        if (currentRoom.south != null) {
            // If so, let's point at that parent...
            currentRoom = currentRoom.south!!
            // And update the UI to show them
            showRoom()
        }
    }

    private fun gotoNorth() {
        // Does this person have a child?
        if (currentRoom.north != null) {
            // If so, let's point at that child...
            currentRoom = currentRoom.north!!
            // And update the UI to show them
            showRoom()
        }
    }

    private fun gotoEast() {
        // Does this person have a child?
        if (currentRoom.east != null) {
            // If so, let's point at that child...
            currentRoom = currentRoom.east!!
            // And update the UI to show them
            showRoom()
        }
    }

    private fun gotoWest() {
        // Does this person have a child?
        if (currentRoom.west != null) {
            // If so, let's point at that child...
            currentRoom = currentRoom.west!!
            // And update the UI to show them
            showRoom()
        }
    }

    private fun showRoom() {
        if (currentRoom.north != null) {
            northButton.isEnabled = true
        } else {
            northButton.isEnabled = false
        }

        if (currentRoom.south != null) {
            southButton.isEnabled = true
        } else {
            southButton.isEnabled = false
        }

        if (currentRoom.west != null) {
            westButton.isEnabled = true
        } else {
            westButton.isEnabled = false
        }

        if (currentRoom.east != null) {
            eastButton.isEnabled = true
        } else {
            eastButton.isEnabled = false
        }

        roomNameLabel.text = currentRoom.name
        roomDescLabel.text = currentRoom.description


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



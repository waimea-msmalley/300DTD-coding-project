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
    var cameFrom: Room? = null
    var nextRoom: Room? = null


    fun addcameFrom(room: Room) {
        if (cameFrom == null) {
            cameFrom = room
            room.addNextRoom(this)
        }
    }

    fun addNextRoom(room: Room) {
        if (nextRoom == null) {
            nextRoom = room
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
    var currentRoom: Room

    // Setup some properties to hold the UI elements
    private lateinit var exampleLabel: JLabel
//    private lateinit var personLabel: JLabel
    private lateinit var parentLabel: JLabel
    private lateinit var childLabel: JLabel
    private lateinit var nextButton: JButton
    private lateinit var backButton: JButton

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
        val funRoom = Room("Fun Room")
//        val hallway2 = Room("Start")
//        val fridge = Room("Start")
//        val hallway3 = Room("Hallway")
//        val  = Room("Fun Room")
//        val start = Room("Start")
//        val exit = Room("Start")

        locations.add(startRoom)
        locations.add(funRoom)

        startRoom.addNextRoom(funRoom)

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

        exampleLabel = JLabel("Elmo Escape Pro", SwingConstants.CENTER)
        exampleLabel.bounds = Rectangle(30, 30, 240, 40)
        exampleLabel.font = baseFont
        add(exampleLabel)

//        personLabel = JLabel("You", SwingConstants.CENTER)
//        personLabel.bounds = Rectangle(108, 163, 185, 56)
//        personLabel.font = baseFont
//        add(personLabel)

        parentLabel = JLabel("Parent Name", SwingConstants.CENTER)
        parentLabel.bounds = Rectangle(163,430,90,32)
        parentLabel.font = baseFont
        add(parentLabel)

        childLabel = JLabel("Child Name", SwingConstants.CENTER)
        childLabel.bounds = Rectangle(163,200,90,32)
        childLabel.font = baseFont
        add(childLabel)

        nextButton = JButton("North")
        nextButton.bounds = Rectangle(163,234,90,32)
        nextButton.font = smallFont
        nextButton.addActionListener(this)
        add(nextButton)

        backButton = JButton("South")
        backButton.bounds = Rectangle(163,398,90,32)
        backButton.font = smallFont
        backButton.addActionListener(this)
        add(backButton)
    }

    /**
     * Handle any UI events
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            nextButton ->gotoNextRoom()
            backButton -> gotoCameFrom()
        }
    }

    /**
     * An Example Action
     */
    private fun gotoCameFrom() {
        // Does this person have a parent?
        if (currentRoom.cameFrom != null) {
            // If so, let's point at that parent...
            currentRoom = currentRoom.cameFrom!!
            // And update the UI to show them
            showRoom()
        }
    }

    private fun gotoNextRoom() {
        // Does this person have a child?
        if (currentRoom.nextRoom != null) {
            // If so, let's point at that child...
            currentRoom = currentRoom.nextRoom!!
            // And update the UI to show them
            showRoom()
        }
    }

    private fun showRoom() {
        if (currentRoom.cameFrom != null) {
            parentLabel.text = currentRoom.cameFrom?.name
        } else {
            parentLabel.text = "No Room"
        }

        if (currentRoom.nextRoom != null) {
            childLabel.text = currentRoom.nextRoom?.name
        } else {
            childLabel.text = "No Room"
        }

//        personLabel.text = currentRoom.name

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



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
        val startRoom = Room("Bedroom", "A rusty, old, musty smelling room. You have no idea how you got there. You hear giggles from everywhere around you. The only thing on your mind is finding a way out, fast.")
        val funRoom = Room("Fun Room", "Although the name on the wall says 'Fun Room', you can tell that what happens in here is far from it. All types of devices full the space in here. You hear the same creepy laughter from the bedroom,  However, you feel a cool, welcoming breeze coming from your right...")
        val fridge = Room("The Fridge", "Your senses have deceived you, it is not the outside breeze you were expecting, but a fridge that seems endless. Worst of all, its a dead end. There are lots of different cuts of meat on the shelves, you hate to think what they could be.")
        val tickle = Room("Tickle Chamber", "This is where the laughing was coming from, the sound of it is deafening. You can see lots of helpless people, being crawled over by tiny red fuzzballs. They may be laughing, but you know it's not because they are having fun. In front of you, you can hear crying. Not the happy crying from the room you are in, but cries of despair. The juxtaposition makes your hair stand on end. ")
        val cells = Room("Cells", "You have never seen or heard so much sadness in one room in your life. The cries asking for help are relentless, and they especially don't let up when they we you on the verge of escaping. You see a tunnel to your left, it has a cold draft coming from it.'Could this be it?' you think...")
        val exit = Room("Exit", "You have done it, you see the warm embrace of the sun as you exit that place most would call hell on earth. You can't help but thing about the people you left behind. You run into the distance trying to find your way back to your life as it was...")

        locations.add(startRoom)
        locations.add(funRoom)
        locations.add(fridge)
        locations.add(cells)

        locations.add(exit)

        startRoom.addNorth(funRoom)
        funRoom.addEast(fridge)
        funRoom.addWest(tickle)
        funRoom.addSouth(startRoom)
        tickle.addNorth(cells)
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
        val descFont = Font(Font.SANS_SERIF, Font.PLAIN, 14)
        val directionFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

        roomNameLabel = JLabel("Room:", SwingConstants.CENTER)
        roomNameLabel.bounds = Rectangle(20, 21, 370, 43)
        roomNameLabel.font = baseFont
        add(roomNameLabel)

        roomDescLabel = JLabel("Room:", SwingConstants.CENTER)
        roomDescLabel.bounds = Rectangle(42, 76, 338, 146)
        roomDescLabel.font = descFont
        add(roomDescLabel)

        northButton = JButton("North")
        northButton.bounds = Rectangle(163,234,90,32)
        northButton.font = directionFont
        northButton.addActionListener(this)
        add(northButton)

        southButton = JButton("South")
        southButton.bounds = Rectangle(163,398,90,32)
        southButton.font = directionFont
        southButton.addActionListener(this)
        add(southButton)

        westButton = JButton("West")
        westButton.bounds = Rectangle(27,307,90,32)
        westButton.font = directionFont
        westButton.addActionListener(this)
        add(westButton)

        eastButton = JButton("East")
        eastButton.bounds = Rectangle(290,307,90,32)
        eastButton.font = directionFont
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
        roomDescLabel.text = "<html>${currentRoom.description}</html>"


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



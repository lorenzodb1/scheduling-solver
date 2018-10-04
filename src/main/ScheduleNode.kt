class ScheduleNode(scheduleString: String) : Node(scheduleString) {

    lateinit var description: String
    lateinit var time: TimeNode
    lateinit var duration: DurationNode
    lateinit var location: LocationNode
    lateinit var dates: Array<DateNode>
    lateinit var guests: Array<GuestNode>

    init {
        //TODO: parse scheduleString and initialize properties
    }

    public override fun interp() {
        //TODO: implement
    }
}
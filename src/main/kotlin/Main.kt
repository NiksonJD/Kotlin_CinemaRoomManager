import java.lang.Exception

fun main(args: Array<String>) {
    var numberOfRows = 0
    var numberOfSeatsInRow = 0
    var correct = false
    while (!correct) {
        try {
            println("Enter the number of rows:")
            numberOfRows = readln().toInt()
            println("Enter the number of seats in each row:")
            numberOfSeatsInRow = readln().toInt()
            if (numberOfRows !in 1..9 || numberOfSeatsInRow !in 1..9)
                throw NumberFormatException()
            correct = true
        } catch (e: Exception) {
            println("Wrong input!")
        }
    }

    val ticketPriceRegular = 8
    val ticketPriceVIP = 10
    val totalNumberOfSeats = numberOfRows * numberOfSeatsInRow
    val playField = List(numberOfRows + 1) { MutableList(numberOfSeatsInRow + 1) { "S" } }
    playField[0][0] = " "
    for (i in 1..numberOfSeatsInRow) playField[0][i] = i.toString()
    for (i in 1..numberOfRows) playField[i][0] = i.toString()
    var numberOfMenu = -1
    fun cinema() {
        println("\nCinema:")
        for (col in playField)
            println(col.joinToString(" "))
    }

    fun buyATicket() {
        correct = false
        while (!correct) {
            println("\nEnter a row number:")
            val rowNumberStr = readln()
            println("Enter a seat number in that row:")
            val seatNumberStr = readln()
            try {
                val rowNumber = rowNumberStr.toInt()
                val seatNumber = seatNumberStr.toInt()
                if (rowNumber !in 1..numberOfRows || seatNumber !in 1..numberOfSeatsInRow) {
                    throw NumberFormatException("Wrong input!")
                } else if (playField[rowNumber][seatNumber] == "B") {
                    throw NumberFormatException("That ticket has already been purchased!")
                }
                correct = true
                playField[rowNumber][seatNumber] = "B"
                println("\nTicket price: $${if (totalNumberOfSeats > 60 && rowNumber > numberOfRows / 2) ticketPriceRegular else ticketPriceVIP}")
            } catch (e: NumberFormatException) {
                println(e.message)
                continue
            }
        }
    }

    fun statistics() {
        // val numberTickets = playField.flatten().filter { it == "B" }.size
        var currentIncome = 0
        var totalIncome = 0
        var numberOfTickets = 0

        for ((index, value) in playField.withIndex()) {
            if (index == 0) continue
            if (totalNumberOfSeats > 60 && index > numberOfRows / 2) {
                currentIncome += value.filter { it == "B" }.size * ticketPriceRegular
                totalIncome += value.lastIndex * ticketPriceRegular
            } else {
                currentIncome += value.filter { it == "B" }.size * ticketPriceVIP
                totalIncome += value.lastIndex * ticketPriceVIP
            }
            numberOfTickets += value.filter { it == "B" }.size
        }
        val percentage = String.format("%.2f", numberOfTickets.toDouble() * 100.00 / totalNumberOfSeats.toDouble())
        println("Number of purchased tickets: $numberOfTickets")
        println("Percentage: $percentage%")
        println("Current income: $$currentIncome")
        println("Total income: $$totalIncome")
    }

    while (numberOfMenu != 0) {
        println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit")
        numberOfMenu = readln().toInt()
        when (numberOfMenu) {
            0 -> break
            1 -> cinema()
            2 -> buyATicket()
            3 -> statistics()
            else -> continue
        }
    }
}
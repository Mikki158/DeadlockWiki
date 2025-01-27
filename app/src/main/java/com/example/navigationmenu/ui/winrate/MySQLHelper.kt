import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import com.example.navigationmenu.ui.winrate.Hero
import java.sql.SQLException
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object MySQLHelper {

    private const val DB_URL = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7738074"
    private const val USER = "sql7738074"
    private const val PASS = "Dhdy3HbnQD"

    fun getWinrate(): List<Hero> {
        val heroList = mutableListOf<Hero>()

        // Установка политики потоков для работы с сетью на основном потоке
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            Class.forName("com.mysql.cj.jdbc.Driver")

            // Подключение к базе данных
            val connection = DriverManager.getConnection(DB_URL, USER, PASS)
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT name, winrate, pickrate, networth, kda FROM heroes_stats")
            print("good")

            // Чтение данных
            while (resultSet.next()) {
                val name = resultSet.getString("name")
                val winrate = resultSet.getString("winrate")
                val pickrate = resultSet.getString("pickrate")
                val networth = resultSet.getDouble("networth")
                val kda = resultSet.getDouble("kda")
                val ksmin = resultSet.getDouble("ksmin")
                val denmin = resultSet.getDouble("denmin")

                //Toast.makeText(requireContext(), "Сообщение успешно выведено!", Toast.LENGTH_SHORT).show()

                heroList.add(Hero(name, winrate, pickrate, networth, kda, ksmin, denmin))
            }

            resultSet.close()
            statement.close()
            connection.close()
        } catch (e: SQLException) {
            print("Error")
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            println("Драйвер не найден: ${e.message}")
        }

        return heroList
    }
}

import kotlin.random.Random
fun main() {
    // Программа шифрования методом Вижинера
    // Написать программу, реализующую шифровку и расшифровку сообщения с помощью шифра Виженера. Программа запрашивает исходное сообщение, ключ и спрашивает, использовать типовую таблицу или генерировать случайную. Типовая таблица предоставлена на экране.
    // В результате работы на консоль выводится исходное сообщение, под ним ключ (повторяющийся столько раз, сколько это необходимо). Третьей строкой выводится зашифрованное сообщение. Буквы должны находиться друг под другом согласно порядкового номера (первая буква ключа под первой буквой исходного сообщения. Под ними же первая буква зашифрованного сообщения и т.д.). Также выводится шифровальная таблица.
    // Случайная шифровальная таблица получается путем случайного перемешивания символов первой строки. Все последующие строки сдвигаются относительно первой строки. Вторая строка - на одну ячейку, третья строка - на две ячейки и т.д.  Для усложнения задачи можно каждую последующую строку сдвигать на случайную величину относительно первой строки, с условием, что величина сдвига для каждой строки различна (не повторяется).
    // Шифр Виженера состоит из последовательности нескольких шифров Цезаря с различными значениями сдвига. Для зашифровывания может использоваться таблица алфавитов, называемая tabula recta или квадрат (таблица) Виженера. Применительно к русскому алфавиту таблица Виженера составляется из строк по 33 символов (начальная строка может быть перемешана), причём каждая следующая строка сдвигается на несколько позиций. Таким образом, в таблице получается 33 различных шифров Цезаря. На каждом этапе шифрования используются различные алфавиты, выбираемые в зависимости от символа ключевого слова.
        // https://wywiwyg.ru/learn_kotlin/tablitsa-shifr-vizhenera.png
    // Например, предположим, что исходный текст и ключ имеют следующий вид:
    // Текст: ШИФРВИЖЕНЕР
    // Ключ:  КОД
    // Чтобы зашифровать текст необходимо записать ключевое слово «КОД» циклически до тех пор, пока его длина не будет соответствовать длине исходного текста.
        // Текст: ШИФРВИЖЕНЕР
        // Ключ:  КОДКОДКОДКО
        // Итог:  ГЧШЫРМСУСПЯ
    // Первый символ исходного текста ("Ш") зашифрован последовательностью К, которая является первым символом ключа. Первый символ зашифрованного текста ("К") находится на пересечении строки К и столбца Ш в таблице Виженера. Точно так же для второго символа исходного текста используется второй символ ключа; то есть второй символ зашифрованного текста ("И") получается на пересечении строки О и столбца И. Остальная часть исходного текста шифруется подобным способом.

    print("Введите сообщение: ")
    val msg = readln()

    print("Введите ключ: ")
    val key = readln()

    print("Использовать типовую таблицу? (д/н) ")
    val typeTable = readln()

    val table = if (typeTable == "y" || typeTable == "Y" || typeTable == "Д" || typeTable == "д") {
        genDefaultTable()
    } else if (typeTable == "n" || typeTable == "N" || typeTable == "Н" || typeTable == "н") {
        genRandomTable()
    } else {
        println("Неверный ввод!")
        return
    }

    print("Действие [1-шифр, 2-дешифр]: ")
    val action = readln()

    val result = when(action){
        "1" -> encrypt(msg,key,table)
        "2" -> decrypt(msg,key,table)
        else -> "Неверный ввод"
    }
    println("\nИтог: $result")
}

fun genDefaultTable(): Array<CharArray> {
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray()
    val table = Array(alphabet.size) { CharArray(alphabet.size) }

    for(i in alphabet.indices){
        for(j in alphabet.indices){
            table[i][j] = alphabet[(i+j)%alphabet.size]
        }
    }
    return table
}

fun genRandomTable(): Array<CharArray> {
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray()
    val table = Array(alphabet.size) { CharArray(alphabet.size) }
    val alphabetTasovan = alphabet.toMutableList().shuffled().toCharArray()

    val sdvig = (0 until alphabet.size).toMutableList().shuffled()

    for(i in alphabet.indices){
        for(j in alphabet.indices){
            table[i][j] = alphabetTasovan[(i+sdvig[i]+j) % alphabet.size]
        }
    }

    return table
}

fun encrypt(msg: String, key: String, table: Array<CharArray>): String{
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray()
    var encryptMsg = ""
    var keyId = 0

    for (i in msg.indices){
        val char = msg[i].uppercaseChar()
        val keyChar = key[keyId].uppercaseChar()

        if(char.isLetter()){
            val row = alphabet.indexOf(keyChar)
            val col = alphabet.indexOf(char)
            encryptMsg += table[row][col]
            keyId = (keyId + 1) % key.length
        } else encryptMsg += char
    }

    return encryptMsg
}

fun decrypt(msg: String, key: String, table: Array<CharArray>): String{
    var alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray()
    var decryptMsg = ""
    var keyId = 0

    for(i in msg.indices) {
        val char = msg[i].uppercaseChar()
        val keyChar = key[keyId].uppercaseChar()

        if(char.isLetter()){
            val row = alphabet.indexOf(keyChar)
            val col = table[row].indexOf(char)
            decryptMsg += alphabet[col]
            keyId = (keyId + 1) % key.length
        } else decryptMsg += char
    }

    return decryptMsg
}

fun printTable(table: Array<CharArray>){
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray()

    for(i in alphabet.indices){
        for(j in alphabet.indices){
            print("$table[i][j]")
        }
        println()
    }
}
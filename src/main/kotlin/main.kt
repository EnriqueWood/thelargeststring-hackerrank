/*

The Largest String
--

Given a string, construct a new string by rearranging the original string and deleting characters as needed. Return the alphabetically largest string that can be constructed respecting a limit as to how many consecutive characters can be the same.

Example:
s='bacc'
k=2

The largest string, alphabetically, is 'cccba' but it is not allowed because it uses the character 'c' more than 2 times consecutively. Therefore, the answer is 'ccbca'.

Function Description
Complete the function getLargestString in the editor below.

getLargestString has the following parameters:
string s[n]: the original string
int k: the maximum number of identical consecutive characters the new string can have

Returns:
string: the alphabetically largest string that can be constructed that has no more than k identical consecutive characters

Constraints

1<= n <= 10^5
1<= k <= 10^3
The string s contains only lowercase English letters.
 */
const val AMOUNT_OF_ENGLISH_LETTERS = 26
const val FIRST_ENGLISH_LETTER = 'a'

const val NOT_FOUND = -1

fun main(args: Array<String>) {
    val baccResult = solve("bacc", 2)
    val zzzazzResult = solve("zzzazz", 2)
    val axxzzxResult = solve("axxzzx", 2)

    println("Result for bacc: " + baccResult)
    println("Result for zzzazz: " + zzzazzResult)
    println("Result for axxzzxResult: " + axxzzxResult)

    assert(baccResult == "ccba")
    assert(zzzazzResult == "zzazz")
    assert(axxzzxResult == "zzxxax")
}

fun solve(string: String, maxIdenticalConsecutiveLetters: Int): String {
    val solution = StringBuilder()

    // Each position of the array represents the amount of times a character from a to z appears in the string
    val letterAppearanceCounter = getAppearancesCounterArray(string)

    val lastArrayIndex = AMOUNT_OF_ENGLISH_LETTERS - 1
    for (i in lastArrayIndex downTo 0) {
        var charAppearances = letterAppearanceCounter[i]
        while (charAppearances > 0) {
            if (charAppearances <= maxIdenticalConsecutiveLetters) {
                solution.append(getCharAt(i).toString().repeat(charAppearances))
                break
            }
            while (charAppearances > maxIdenticalConsecutiveLetters) {
                solution.append(getCharAt(i).toString().repeat(maxIdenticalConsecutiveLetters))
                charAppearances -= maxIdenticalConsecutiveLetters

                val indexOfNextLetterWithAppearances =
                    getIndexOfNextLetterWithAppearances(letterAppearanceCounter, i - 1)

                if (indexOfNextLetterWithAppearances != NOT_FOUND) {
                    solution.append(getCharAt(indexOfNextLetterWithAppearances))
                    letterAppearanceCounter[indexOfNextLetterWithAppearances]--
                } else {
                    charAppearances = 0 //breaks the outer loop
                }
            }
        }
    }
    return solution.toString()
}

private fun getAppearancesCounterArray(string: String): IntArray {
    val letterAppearanceCounter = IntArray(AMOUNT_OF_ENGLISH_LETTERS)
    for (letter in string) {
        letterAppearanceCounter[letter - FIRST_ENGLISH_LETTER]++
    }
    return letterAppearanceCounter
}

fun getCharAt(index: Int): Char {
    return FIRST_ENGLISH_LETTER + index
}

fun getIndexOfNextLetterWithAppearances(appearancesArray: IntArray, startingFrom: Int): Int {
    for (i in startingFrom downTo 0) {
        if (appearancesArray[i] > 0) {
            return i
        }
    }
    return NOT_FOUND
}
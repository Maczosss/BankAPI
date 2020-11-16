package com.example.demo

import java.io.File
import java.io.FileNotFoundException
import java.util.*

class CSVParser {
    @Throws(FileNotFoundException::class)
    fun getFromCSV(fileName: String?): List<List<String>> {
        val line: MutableList<List<String>> = LinkedList()
        val scanner = Scanner(File(fileName))
        while (scanner.hasNext()) {
            line.add(parseLine(scanner.nextLine()))
        }
        scanner.close()
        return line
    }

    companion object {
        private const val DEFAULT_SEPARATOR = ','
        private const val DEFAULT_QUOTE = '"'
        @JvmOverloads
        fun parseLine(cvsLine: String?, separators: Char = DEFAULT_SEPARATOR, customQuote: Char = DEFAULT_QUOTE): List<String> {
            var separators = separators
            var customQuote = customQuote
            val result: MutableList<String> = ArrayList()
            if (cvsLine == null) {
                return result
            }
            if (customQuote == ' ') {
                customQuote = DEFAULT_QUOTE
            }
            if (separators == ' ') {
                separators = DEFAULT_SEPARATOR
            }
            var curVal = StringBuffer()
            var inQuotes = false
            var startCollectChar = false
            var doubleQuotesInColumn = false
            val chars = cvsLine!!.toCharArray()
            for (ch in chars) {
                if (inQuotes) {
                    startCollectChar = true
                    if (ch == customQuote) {
                        inQuotes = false
                        doubleQuotesInColumn = false
                    } else {
                        if (ch == '\"') {
                            if (!doubleQuotesInColumn) {
                                curVal.append(ch)
                                doubleQuotesInColumn = true
                            }
                        } else {
                            curVal.append(ch)
                        }
                    }
                } else {
                    if (ch == customQuote) {
                        inQuotes = true

//                    if (chars[0] != '"' && customQuote == '\"') {
//                        curVal.append('"');
//                    }
//
//                    //double quotes in column will hit this!
//                    if (startCollectChar) {
//                        curVal.append('"');
//                    }
                    } else if (ch == separators) {
                        result.add(curVal.toString())
                        curVal = StringBuffer()
                        startCollectChar = false
                    } else if (ch == '\r') {
                        continue
                    } else if (ch == '\n') {
                        break
                    } else {
                        curVal.append(ch)
                    }
                }
            }
            result.add(curVal.toString())
            return result
        }
    }
}
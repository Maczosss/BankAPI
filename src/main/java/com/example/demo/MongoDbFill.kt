package com.example.demo

import java.io.FileNotFoundException
import java.util.*
import java.util.stream.Collectors

class MongoDbFill(private val customerRepository: CustomerRepository, private val transactionrepository: TransactionRepository, private val accountTypesRepository: AccountTypesRepository, args: Array<out String>) {
    private val filesPaths: List<String>
    @Throws(FileNotFoundException::class)
    fun createDatabase() {
        customerRepository.deleteAll()
        transactionrepository.deleteAll()
        accountTypesRepository.deleteAll()
        val parser = CSVParser()
        val listOfCustomersToAdd: MutableList<Customer> = LinkedList()
        val listOfAccountTypesToAdd: MutableList<AccountTypes> = LinkedList()
        val listOfTransactionsToAdd: MutableList<Transactions> = LinkedList()
        val customerResult = parser.getFromCSV(filesPaths.stream().filter { c: String -> c.contains("customers") }.collect(Collectors.joining()))
        val transactionsResult = parser.getFromCSV(filesPaths.stream().filter { c: String -> c.contains("transaction") }.collect(Collectors.joining()))
        val accountTypesResult = parser.getFromCSV(filesPaths.stream().filter { c: String -> c.contains("account") }.collect(Collectors.joining()))
        customerResult.drop(1)
        transactionsResult.drop(1)
        accountTypesResult.drop(1)
        for (list in customerResult) {
            val count = 0
            try {
                val c = Customer(list[count], list[count + 1], list[count + 2], list[count + 3])
                listOfCustomersToAdd.add(c)
            } catch (e: IndexOutOfBoundsException) {
                println(e)
            }
        }
        for (list in transactionsResult) {
            val count = 0
            try {
                var transactionValue = 0.0
                val value = list[1].replace(',', '.')
                if (value != "") {
                    try {
                        transactionValue = value.toDouble()
                    }catch(e: Exception){
                        println(e)
                    }
                }
                val t = Transactions(list[count], transactionValue, list[count + 2], list[count + 3], list[count + 4])
                listOfTransactionsToAdd.add(t)
            } catch (e: IndexOutOfBoundsException) {
                println(e)
            }
        }
        for (list in accountTypesResult) {
            val count = 0
            try {
                val a = AccountTypes(list[count], list[count + 1])
                listOfAccountTypesToAdd.add(a)
            } catch (e: IndexOutOfBoundsException) {
                println(e)
            }
        }
        customerRepository.saveAll(listOfCustomersToAdd)
        transactionrepository.saveAll(listOfTransactionsToAdd)
        accountTypesRepository.saveAll(listOfAccountTypesToAdd)
    }

    init {
        filesPaths = Arrays.asList(*args) as List<String>
    }
}
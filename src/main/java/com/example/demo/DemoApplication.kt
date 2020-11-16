package com.example.demo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.FileNotFoundException

@SpringBootApplication
open class DemoApplication(private val customerRepository: CustomerRepository, private val transactionrepository: TransactionRepository, private val accountTypesRepository: AccountTypesRepository) : CommandLineRunner {
    override fun run(vararg args: String) {
        val mongo = MongoDbFill(customerRepository, transactionrepository, accountTypesRepository, args)
        try {
            mongo.createDatabase()
        } catch (e: FileNotFoundException) {
            println("Error could not find files needed for creating database")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(DemoApplication::class.java, *args)
        }
    }
}
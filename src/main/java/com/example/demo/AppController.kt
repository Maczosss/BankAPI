package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.stream.Collectors

@RestController
class AppController @Autowired constructor(private val customerRepository: CustomerRepository, private val accountTypesRepository: AccountTypesRepository, private val transactionRepository: TransactionRepository) {
    @RequestMapping(value = ["transactionsTable/AccountTypes={id[]}/ClientIds={clientIds[]}"], method = [RequestMethod.GET])
    fun getTransactions(@PathVariable("id[]") accountTypes: Set<String?>,
                        @PathVariable("clientIds[]") clientIds: Set<String?>): List<String> {
        val response = Response()
        var transactionsList: MutableList<Transactions> = LinkedList()
        val sortedTransactions: List<Transactions> = LinkedList()
        var allClients = false
        var allAccountTypes = false
        clientIds.stream().distinct().close()
        if ((accountTypes.contains("ALL") || accountTypes.isEmpty())
                && (clientIds.contains("ALL") || clientIds.isEmpty())) {
            allAccountTypes = true
            allClients = true
        } else if (accountTypes.contains("ALL") || accountTypes.isEmpty()) {
            allAccountTypes = true
        } else if (clientIds.contains("ALL") || clientIds.isEmpty()) {
            allClients = true
        }
        if (allAccountTypes && allClients) {
            transactionsList = transactionRepository.findAll()
            createResponse(response, transactionsList, sortedTransactions)
        }
        if (allAccountTypes && !allClients) {
            for (id in clientIds) {
                transactionsList.addAll(transactionRepository.findAllByCustomerId(id))
            }
            createResponse(response, transactionsList, sortedTransactions)
        }
        if (!allAccountTypes && allClients) {
            for (type in accountTypes) {
                transactionsList.addAll(transactionRepository.findAllByAccountType(type))
            }
            createResponse(response, transactionsList, sortedTransactions)
        }
        if (!allAccountTypes && !allClients) {
            for (id in clientIds) {
                transactionsList.addAll(transactionRepository.findAllByCustomerId(id))
            }
            transactionsList.removeIf { transaction: Transactions -> !accountTypes.contains(transaction.accountType) }
            createResponse(response, transactionsList, sortedTransactions)
        }
        return if (response.getResponse().isEmpty()) response.returnEmptyResponse() else response.getResponse()
    }

    fun createResponse(response: Response, transactionsList: List<Transactions>, sortedTransactions: List<Transactions>) {
        var sortedTransactions = sortedTransactions
        sortedTransactions = transactionsList.stream().sorted(Comparator.comparingDouble { obj: Transactions -> obj.transactionAmount }).collect(Collectors.toList())
        for (t in sortedTransactions) {
            val at = accountTypesRepository.getAccountTypesByAccountType(t.accountType)
            val c = customerRepository.findCustomerById(t.customerId)
            if (at != null && c != null) {
                response.addToResponse(t.transactionDate, t.transactionId, t.transactionAmount,
                        at.name, c.getName(), c.getLastname())
            }
        }
    }
}
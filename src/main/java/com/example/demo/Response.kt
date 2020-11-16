package com.example.demo

import java.util.*

class Response {
    var messageCount = 1
    private val response: MutableList<String> = LinkedList()
    private val emptyResponse = listOf("No records were found in this input combination")
    fun addToResponse(transactionDate: String?, transactionId: String?, transactionAmount: Double?, transactionType: String?,
                      clientName: String?, clientLastName: String?) {
        response.add(String.format(
                "%d. (Data transakcji: %s, Identyfikator transakcji: %s, Kwota transakcji: %f rodzaj rachunku: %s, Imię zlecającego: %s, Nazwisko Zlecającego: %s",
                messageCount, transactionDate, transactionId, transactionAmount, transactionType, clientName, clientLastName))
        messageCount++
    }

    fun returnEmptyResponse(): List<String> {
        return emptyResponse
    }

    fun getResponse(): List<String> {
        return response
    }
}
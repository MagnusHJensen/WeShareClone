package dk.sdu.weshareclone.model.service

import dk.sdu.weshareclone.model.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentService {
    val requestUserId: String
    val ownedPayments: Flow<List<Payment>>
    suspend fun createPayment(totalAmount: String)
    suspend fun createNotification(requestedUsers: Map<String, Int>)
}
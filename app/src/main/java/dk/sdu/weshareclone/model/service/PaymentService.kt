package dk.sdu.weshareclone.model.service

interface PaymentService {
    val requestUserId: String
    val requestedUserIds: Map<String, Int>
    suspend fun createPayment(totalAmount: Int)
    suspend fun createNotification(requestedUsers: Map<String, Int>)
}
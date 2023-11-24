package dk.sdu.weshareclone.model.service.impl

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dk.sdu.weshareclone.model.Payment
import dk.sdu.weshareclone.model.service.PaymentService
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class PaymentServiceImpl @Inject constructor(private val auth: FirebaseAuth, private val firestore: FirebaseFirestore) :
    PaymentService {
    override val requestUserId: String
        get() = auth.currentUser?.uid.orEmpty()
    override val requestedUserIds: Map<String, Int>
        get() = TODO("Not yet implemented")

    override suspend fun createPayment(totalAmount: Int) {
        val paymentId = UUID.randomUUID()
        val requestedUsers = HashMap<String, Int>()
        requestedUsers["Tommy"] = 100
        Log.d("APP", requestUserId)
        val payment = Payment(paymentId.toString(), amount = totalAmount, owner = requestUserId, requestedUsers = requestedUsers)


        firestore.collection("payments").document(paymentId.toString())
            .set(payment).await()
    }

    override suspend fun createNotification(requestedUsers: Map<String, Int>) {
        TODO("Not yet implemented")
    }

}
package dk.sdu.weshareclone.model.service.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import dk.sdu.weshareclone.model.Payment
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.PaymentService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class PaymentServiceImpl @Inject constructor(private val auth: AccountService, private val firestore: FirebaseFirestore) :
    PaymentService {
    override val requestUserId: String
        get() = auth.currentUserId
    override val ownedPayments: Flow<List<Payment>>
        get() =
            auth.currentUser.flatMapLatest {
                val document = firestore.collection(PAYMENTS_COLLECTION)
                    .whereEqualTo(OWNER_ID_FIELD, requestUserId)
                document.addSnapshotListener { snapshot, e ->
                    snapshot?.toObjects(Payment::class.java)
                }

                document.dataObjects()
            }


    override suspend fun createPayment(totalAmount: String) {
        val paymentId = UUID.randomUUID()
        val requestedUsers = HashMap<String, Int>()
        requestedUsers["Tommy"] = 100
        Log.d("APP", requestUserId)
        val payment = Payment(paymentId.toString(), amount = totalAmount, owner = requestUserId, requestedUsers = requestedUsers)


        firestore.collection(PAYMENTS_COLLECTION).document(paymentId.toString())
            .set(payment).await()
    }

    override suspend fun createNotification(requestedUsers: Map<String, Int>) {
        TODO("Not yet implemented")
    }

    companion object {
        const val PAYMENTS_COLLECTION = "payments"
        const val OWNER_ID_FIELD = "owner"
        const val CREATED_AT_FIELD = "createdAt"
    }
}
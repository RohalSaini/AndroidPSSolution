package sanssys.solution.pssolutions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import sanssys.solution.pssolutions.databinding.ActivityPaymentGatewayBinding
import sanssys.solution.pssolutions.fragment.product.viewmodal.ProductViewModal
import sanssys.solution.pssolutions.modal.Item
import sanssys.solution.pssolutions.modal.OrderBody
import sanssys.solution.pssolutions.util.Left
import sanssys.solution.pssolutions.util.Right
import sanssys.solution.pssolutions.util.Session
import java.lang.reflect.Type
import com.google.gson.Gson
import org.json.JSONArray
import sanssys.solution.pssolutions.fragment.ComingSoon
import kotlin.collections.ArrayList


@AndroidEntryPoint
class PaymentGateway : AppCompatActivity(), PaymentResultListener {
    val modal by viewModels<ProductViewModal>()
    lateinit var session: Session
    private var _binding: ActivityPaymentGatewayBinding? = null
    private val binding get() = _binding!!
    var totalBill :Int = 0
    lateinit var address:String
    lateinit var cell:String
    lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_gateway)
        _binding = ActivityPaymentGatewayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = Session(applicationContext)
        var list = session.getYourCart()
        Log.d("data","list is ${list}");
        var total = 0;
        var discount = 0;
         if (list != null) {
           for (item in list) {
             total = (total + (item.price * item.cart))
             discount += (item.discount * item.cart)
        }
        }
        println("Total price is ${total}")
        val intent = intent
        val myValue = intent.extras?.get("address")
        println("Address ${myValue}")
        binding.addressBar.text = myValue.toString()

        var bill=
        "Total Bill                  :Rs ${total} \nDelivery Charges   :Rs 0 \nOffer                         :Rs $discount \nTotal Amount          :Rs ${total+0-discount}"

        binding.bill.text = bill
        this.totalBill = total+0-discount;
        address = myValue.toString()
        name = intent.extras?.get("name") as String
        cell = intent.extras?.get("phone") as String

        binding.onlinePayment.setOnClickListener {

        /*    if(this.totalBill > 2000) {
                startPayment(paymentType = "online")
            }else {
                Snackbar.make(it," Bill should be greater than 2000 in order to do shopping !!",Snackbar.LENGTH_LONG).show()
            }

         */
            Snackbar.make(it," Online Payment is coming soon !! "+session.getEmail(),Snackbar.LENGTH_LONG).show()
        }
        binding.cash.setOnClickListener {
            if(this.totalBill > 1000) {
                callToApi("cash")
            }else {
                Snackbar.make(it," Bill should be greater than 1000 in order to do shopping !!",Snackbar.LENGTH_LONG).show()
            }
        }
    }
    private fun startPayment(paymentType:String) {
        Checkout.preload(application);
        val co = Checkout()
        co.setKeyID("rzp_test_zpsGpsHzU0VMla")

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount",totalBill*100)//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","rohalsainia94@gmail.com")
            prefill.put("contact",session.getPhone())

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Your order has been placed , it will reach within time ", Toast.LENGTH_LONG).show()
        callToApi("online")
    }

    private  fun callToApi(paymentType:String) {
        var mlist = session.getYourCart()
        for (item in mlist!!) {
            item.add = item.cart
        }
        runBlocking {
            try {
                    val status = modal.order(
                        token = "Bearer " + session.getPassword().toString(),
                        order = OrderBody(
                            customerid = session.getEmail()!!,
                            address = address,
                            cart  = mlist,
                            bill = totalBill,
                            orderType = paymentType,
                            name= name,
                            cell = cell
                        )
                    )
                    when (status) {
                        is Left -> {
                           if(status.a.status) {
                               session.saveOrderList(status.a)
                               session.resetCart()
                               Snackbar.make(_binding!!.root," Your Order has been placed checked in the invoice further", Snackbar.LENGTH_SHORT).show()
                               val intent = Intent(applicationContext, MainActivity::class.java)
                               startActivity(intent)
                           } else {
                               Snackbar.make(_binding!!.root, status.a.error, Snackbar.LENGTH_SHORT).show()
                           }
                        }
                        is Right -> {
                            Log.e("order-error ",status.b.message.toString())
                            Snackbar.make(_binding!!.root, status.b.message.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
            } catch (error:Exception) {
                Snackbar.make(_binding!!.root, "$error", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,p1, Toast.LENGTH_LONG).show()
    }
}
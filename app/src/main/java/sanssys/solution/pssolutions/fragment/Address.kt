package sanssys.solution.pssolutions.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import sanssys.solution.pssolutions.databinding.FragmentAddressBinding
import sanssys.solution.pssolutions.PaymentGateway
import sanssys.solution.pssolutions.util.Session


class Address : Fragment() ,PaymentResultListener {
    lateinit var session:Session
    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //session = Session(requireContext())
        //var list = session.getList()
        var total = 0;
       // if (list != null) {
         //   for (item in list) {
           //     total = (total + (item.price * item.quantity)).toInt()
            //}
        //}
        //println("Total price is ${total}")
        ///val bundle = this.arguments
        //val myValue = bundle!!.getString("address")
        //println("Address ${myValue}")
        //binding.addressBar.text = myValue

        //var bill=
            "Total Bill                  :Rs ${total} \nDelivery Charges   :Rs 125 \nOffer                         :Rs 100 \nTotal Amount          :Rs ${total+125-100}"

        //binding.bill.text =  bill

        binding.onlinePayment.setOnClickListener {
            //startPayment()
            val myIntent = Intent(requireContext(),PaymentGateway::class.java)
            requireContext().startActivity(myIntent)
        }

    }
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        Checkout.preload(requireContext());
        val activity: Activity = requireActivity()
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
            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","50000")//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(requireContext(),"success",Toast.LENGTH_LONG)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(requireContext(),"error",Toast.LENGTH_LONG)
    }
}
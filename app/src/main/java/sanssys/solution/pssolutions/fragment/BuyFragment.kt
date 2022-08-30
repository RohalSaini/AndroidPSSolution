package sanssys.solution.pssolutions.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import sanssys.solution.pssolutions.PaymentGateway
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentBuyBinding
import sanssys.solution.pssolutions.util.Session
import sanssys.solution.pssolutions.util.hideKeyboardFrom

class BuyFragment : Fragment() {
    private var _binding: FragmentBuyBinding? = null
    private val binding get() = _binding!!
    lateinit var session:Session
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentBuyBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = Session(requireContext())
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.planetsSpinner.adapter = adapter
        }

        val pin:ArrayList<String> = ArrayList()

        for ( item in session.getPinList()!!) {
            pin.add(item.pin.toString())
        }
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, pin)
        binding.pincodeSpinner.adapter = adapter
        binding.pincodeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) { }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedObject = binding.pincodeSpinner.selectedItem as String
                Log.d("Selected Pin Code is",selectedObject)
                hideKeyboardFrom(requireContext(), p1!!)
                binding.buttonProccedToPaymant.requestFocus()
            }
        }

        binding.planetsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) { }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedObject = binding.planetsSpinner.selectedItem as String
                // Log.d("Selected Pin Code is",selectedObject)
                binding.pincodeSpinner.requestFocus()
            }
        }

        binding.name.setOnClickListener {
            if(binding.name.text.isBlank()) {
                hideKeyboardFrom(requireContext(),it)
                Snackbar.make(it,"Enter your name for further proceeding!!",Snackbar.LENGTH_SHORT).show()
                binding.name.requestFocus()
            }else {
                binding.address.requestFocus()
            }
        }

        binding.address.setOnClickListener {
            if(binding.address.text.isBlank()) {
                hideKeyboardFrom(requireContext(),it)
                Snackbar.make(it,"Enter your address for further proceeding!!",Snackbar.LENGTH_SHORT).show()
                binding.address.requestFocus()
            }else {
                binding.phone.requestFocus()
            }
        }
        binding.phone.setOnClickListener {
            if(binding.phone.text.isBlank()) {
                hideKeyboardFrom(requireContext(),it)
                try {
                    val phone = binding.phone.text.trim().toString().toInt()
                    if(phone.toString().length != 10) {
                        Snackbar.make(it,"Enter your phone number correctly for further proceeding!!",Snackbar.LENGTH_SHORT).show()
                        binding.phone.requestFocus()
                    }

                }catch (error:Exception) {
                    Snackbar.make(it,"Enter your phone number correctly for further proceeding!!",Snackbar.LENGTH_SHORT).show()
                    binding.phone.requestFocus()
                }
                Snackbar.make(it,"Enter your phone number correctly for further proceeding!!",Snackbar.LENGTH_SHORT).show()
                binding.phone.requestFocus()
            }else {
                hideKeyboardFrom(requireContext(),it)
            }
        }

        binding.buttonProccedToPaymant.setOnClickListener {
            var address = "${binding.name.text.trim()}\n${binding.address.text.trim()} \nPin Code: ${binding.pincodeSpinner.selectedItem.toString().trim()} \nPhone Number : ${binding.phone.text.trim()}"
            session.setAddress(
                name = binding.name.text.trim().toString(),
                address = binding.address.text.trim().toString(),
                phone = binding.phone.text.trim().toString(),
                pin = binding.pincodeSpinner.selectedItem.toString().toInt()
            )
            val myIntent = Intent(requireContext(), PaymentGateway::class.java)
            myIntent.putExtra("address", address)
            myIntent.putExtra("name", binding.name.text.trim().toString())
            myIntent.putExtra("phone", binding.phone.text.trim().toString())
            myIntent.putExtra("pin", pin)
            requireContext().startActivity(myIntent)
        }

        if(session.getNames() != null) {
            binding.name.text  = Editable.Factory.getInstance().newEditable(session.getNames())
        }
        if(session.getAdrress() != null) {
            binding.address.text =  Editable.Factory.getInstance().newEditable(session.getAdrress())
        }
        if(session.getPhone() != null) {
            binding.phone.text =  Editable.Factory.getInstance().newEditable(session.getPhone().toString())
        }
    }
}
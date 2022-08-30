package sanssys.solution.pssolutions.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import sanssys.solution.pssolutions.InvoiceFragment
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentGroceryBinding
import sanssys.solution.pssolutions.adapter.BaseAdapterPinCode
import sanssys.solution.pssolutions.fragment.cart.CartFragment
import sanssys.solution.pssolutions.fragment.product.ProductFragment
import sanssys.solution.pssolutions.fragment.searchbybrand.BrandFragment
import sanssys.solution.pssolutions.fragment.searchforproduct.SearchFragment
import sanssys.solution.pssolutions.util.Session


@AndroidEntryPoint
class GroceryFragment : Fragment() {
    private var _binding: FragmentGroceryBinding? = null
    private val binding get() = _binding!!
    lateinit var session: Session
    lateinit var spinnerAdapter: BaseAdapterPinCode
    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session= Session(requireActivity())
        // spinner  setting
        spinnerAdapter = BaseAdapterPinCode(requireContext(),session.getPinList())
        binding.deliveryLayout.containerDeliverLayer.findViewById<Spinner>(R.id.spinner_pin_code).adapter =spinnerAdapter
        binding.layputToolbar1.contact.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ContactFragment())
                .addToBackStack("contact")
                .commit()
        }
        binding.layputToolbar1.home.text ="Invoice"
        binding.layputToolbar1.home.setOnClickListener {
            if(session.getOrderList() == null) {
                Snackbar.make(it,"Invoice empty ", Snackbar.LENGTH_SHORT).show()
            }else {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, InvoiceFragment()).addToBackStack("grocery")
                    .commit()

            }
        }
        binding.deliveryLayout.textViewSearch.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SearchFragment())
                .addToBackStack("searchByName")
                .commit()
        }
        binding.layputToolbar1.SignOut.text ="Cart"
        binding.layputToolbar1.SignOut.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, CartFragment())
                .addToBackStack("contact")
                .commit()
        }
        binding.layputToolbar1.contact.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ContactFragment())
                .addToBackStack("contact")
                .commit()
        }
        binding.cooking.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "cooking essentials") // Put anything what you want
            val fragment = ProductFragment()
            fragment.arguments = bundle

            try {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("products")
                    .commit()
            }catch (error:NullPointerException) {
                println(error.message)
            }
        }
        binding.health.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "Health And beauty") // Put anything what you want
            val fragment = ProductFragment()
            fragment.arguments = bundle

            try {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("products")
                    .commit()
            }catch (error:NullPointerException) {
                println(error.message)
            }
        }
        binding.baby.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "Baby Care") // Put anything what you want
            val fragment = ProductFragment()
            fragment.arguments = bundle

            try {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("products")
                    .commit()
            }catch (error:NullPointerException) {
                println(error.message)
            }
        }
        binding.men.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "Men Care") // Put anything what you want
            val fragment = ProductFragment()
            fragment.arguments = bundle

            try {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("products")
                    .commit()
            }catch (error:NullPointerException) {
                println(error.message)
            }
        }
        binding.women.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "Women Care") // Put anything what you want
            val fragment = ProductFragment()
            fragment.arguments = bundle

            try {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("products")
                    .commit()
            }catch (error:NullPointerException) {
                println(error.message)
            }
        }
        binding.food.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "Packages food") // Put anything what you want
            val fragment = ProductFragment()
            fragment.arguments = bundle

            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("product")
                .commit()
        }
        binding.shop.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, BrandFragment())
                .addToBackStack("all brands")
                .commit()
        }
        binding.offero.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ComingSoon())
                .addToBackStack("soon")
                .commit()
        }
    }
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentGroceryBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
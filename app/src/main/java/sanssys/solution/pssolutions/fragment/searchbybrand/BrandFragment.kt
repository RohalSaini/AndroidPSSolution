package sanssys.solution.pssolutions.fragment.searchbybrand

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentBrandBinding
import sanssys.solution.pssolutions.fragment.product.ProductFragment
import sanssys.solution.pssolutions.fragment.product.viewmodal.ProductViewModal
import sanssys.solution.pssolutions.fragment.searchbybrand.viewmodal.SearchByBrand
import sanssys.solution.pssolutions.util.Left
import sanssys.solution.pssolutions.util.Right
import sanssys.solution.pssolutions.util.Session
import sanssys.solution.pssolutions.util.hideKeyboardFrom

@AndroidEntryPoint
class BrandFragment : Fragment() {
    private var _binding: FragmentBrandBinding? = null
    val modal by viewModels<SearchByBrand>()
    private val binding get() = _binding!!
    lateinit var session: Session
    private lateinit var adapter: ArrayAdapter<String>
    val pin:ArrayList<String> = ArrayList()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = Session(requireContext())
        pin.clear()
        binding.brand.appName.text = "Brand"
        binding.brand.expandedMenu.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.brand.cart.visibility = View.INVISIBLE

        runBlocking {
            var response = modal.searchByBrand(token = "Bearer " + session.getPassword().toString())
            when(response) {
                is Left -> {
                    hideSpinner()
                    response.a.data.brands.forEach {
                        pin.add(it.name)
                    }
                }
                is Right -> {
                    hideSpinner()
                    // Snackbar.make(_binding!!.root, status.b.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerBrand.adapter = adapter
        }

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, pin)
        binding.spinnerBrand.adapter = adapter
        binding.spinnerBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedObject = binding.spinnerBrand.selectedItem as String
                Log.d("Selected Pin Code is",selectedObject)
                //Snackbar.make(p1!!,selectedObject,Snackbar.LENGTH_LONG).show()
                //hideKeyboardFrom(requireContext(), p1!!)
            }
        }
        binding.go.setOnClickListener {
            //Snackbar.make(it,binding.spinnerBrand.selectedItem.toString(),Snackbar.LENGTH_LONG).show()
            val bundle = Bundle()
            bundle.putString("type",binding.spinnerBrand.selectedItem.toString()) // Put anything what you want
            bundle.putString("api","brand")
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
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSpinner(){
        requireActivity().window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        //_binding!!.spinKit.visibility = View.VISIBLE
    }
    private fun hideSpinner() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        //_binding!!.spinKit.visibility = View.INVISIBLE
    }
}
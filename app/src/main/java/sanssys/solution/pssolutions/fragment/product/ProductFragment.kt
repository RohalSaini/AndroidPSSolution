package sanssys.solution.pssolutions.fragment.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.github.ybq.android.spinkit.style.WanderingCubes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentProductBinding
import sanssys.solution.pssolutions.fragment.cart.CartFragment
import sanssys.solution.pssolutions.fragment.login.LoginFragment
import sanssys.solution.pssolutions.fragment.product.viewmodal.ProductViewModal
import sanssys.solution.pssolutions.modal.Item
import sanssys.solution.pssolutions.modal.Product
import sanssys.solution.pssolutions.modal.ProductItem
import sanssys.solution.pssolutions.recyclerview.ItemRecyclerView
import sanssys.solution.pssolutions.util.Left
import sanssys.solution.pssolutions.util.Right
import sanssys.solution.pssolutions.util.Session

@AndroidEntryPoint
class ProductFragment : Fragment() {
    val modal by viewModels<ProductViewModal>()
    private val scope = MainScope()
    lateinit var session: Session
    var courseList = ArrayList<Item>()
    lateinit var adapter:ItemRecyclerView
    private var _binding: FragmentProductBinding? = null
    lateinit var type:String
    var api = "ok"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            session = Session(this.requireContext())
            showSpinner()
            val bundle = this.arguments
            if (bundle != null) {
                type = bundle.getString("type")!!
                //api = bundle.getString("brand")!!
            }
            if(bundle?.getString("api") != null) {
                api = bundle.getString("api")!!
            }
            Log.d("data",api)
            if(api == "brand"){
                //Snackbar.make(_binding!!.root,api,Snackbar.LENGTH_LONG).show()
                runBlocking {
                    var response = modal.getAllByBrand(token = "Bearer " + session.getPassword().toString(),type)
                    when(response) {
                        is Left -> {
                            hideSpinner()
                            if(response.a.data.productList.isEmpty()){
                                _binding!!.textViewEmptyStock.visibility = View.VISIBLE
                            } else {
                                setAdapter(products= response.a.data.productList)
                            }
                        }
                        is Right -> {
                            hideSpinner()
                            // Snackbar.make(_binding!!.root, status.b.message.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                try {
                    runBlocking {
                        hitApi(type)
                    }
                }catch (error:Exception) {
                    println(error.message);
                }
            }
            _binding!!.cart.cart.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, CartFragment()).addToBackStack("education")
                    .commit()
            }
            _binding!!.cart.expandedMenu.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }catch (error:java.lang.NullPointerException) {
            println(error.message);
        }
    }
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return _binding!!.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun hitApi(type: String) {
        try {
            scope.launch(Dispatchers.Main) {
                val status = modal.login(
                    token = "Bearer " + session.getPassword().toString(),
                    category = type
                )
                when (status) {
                    is Left -> {
                        hideSpinner()
                        if(status.a.data.productList.isEmpty()){
                            _binding!!.textViewEmptyStock.visibility = View.VISIBLE
                        } else {
                             setAdapter(products= status.a.data.productList)
                        }
                    }
                    is Right -> {
                        hideSpinner()
                        Snackbar.make(_binding!!.root, status.b.message.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }catch (error:Exception) {
            _binding!!.spinKit.visibility = View.INVISIBLE
            Snackbar.make(_binding!!.root, "$error", Snackbar.LENGTH_SHORT).show()
        }
    }
    private fun setAdapter(products: ArrayList<Item>) {
        courseList = products
        println(" list is $courseList")
        adapter =  ItemRecyclerView(requireContext(),listener = object :ItemRecyclerView.OnClickListener {
            override fun onClick(position: Int) {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, CartFragment())
                    .addToBackStack("education")
                    .commit()
            }

            override fun onCartAdd(position: Int,item:Item) {
                modal.onCartAdd(position =position,item=item)
                println(" OnCartItem is $item")
            }
        })
        if(_binding != null) {
            _binding!!.rvItems.adapter =adapter
            _binding!!.rvItems.setHasFixedSize(true)
            _binding!!.rvItems.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter.setData(courseList)
        }
    }
    private fun showSpinner(){
        requireActivity().window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        _binding!!.spinKit.visibility = View.VISIBLE
    }
    private fun hideSpinner() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        _binding!!.spinKit.visibility = View.INVISIBLE
    }
}
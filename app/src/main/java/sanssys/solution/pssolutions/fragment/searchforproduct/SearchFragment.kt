package sanssys.solution.pssolutions.fragment.searchforproduct

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentSearchBinding
import sanssys.solution.pssolutions.fragment.cart.CartFragment
import sanssys.solution.pssolutions.fragment.login.LoginFragment
import sanssys.solution.pssolutions.fragment.product.viewmodal.ProductViewModal
import sanssys.solution.pssolutions.fragment.searchforproduct.viewmodal.SearchFragmentModal
import sanssys.solution.pssolutions.modal.Item
import sanssys.solution.pssolutions.modal.Product
import sanssys.solution.pssolutions.modal.ProductItem
import sanssys.solution.pssolutions.recyclerview.ItemRecyclerView
import sanssys.solution.pssolutions.util.Left
import sanssys.solution.pssolutions.util.Right
import sanssys.solution.pssolutions.util.Session
import sanssys.solution.pssolutions.util.hideKeyboardFrom

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var strings = ArrayList<String>()
    lateinit var listAdapter:ArrayAdapter<String>
    lateinit var session:Session
    lateinit var adapter:ItemRecyclerView

    val modal by viewModels<SearchFragmentModal>()
    private val scope = MainScope()

    var courseList = ArrayList<Item>()


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBlocking {
            hitApi()
        }
        //hitApi()
        session = Session(requireContext())


        binding.autocompleteFragment.setOnItemClickListener { adapterView, view, i, l ->
            hideKeyboardFrom(requireContext(),_binding!!.root)
            binding.textView.text = listAdapter.getItem(i)
            var itemName = listAdapter.getItem(i)
            println(itemName)
            var itemId= 0;
            for (item in courseList) {
                strings.add(item.name)
                println(item.name)
                if(item.name == listAdapter.getItem(i)) {
                    itemId = item.id
                }
            }
            var selectedList = ArrayList<Item>()
            selectedList.clear();
            for (item in courseList) {
                if(item.id == itemId) {
                    selectedList.add(item)
                }
            }
            adapter.setData(selectedList)
        }

        _binding!!.cart.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, CartFragment()).addToBackStack("education")
                .commit()
        }

    }

    private fun setAdapter(products: ArrayList<Item>) {
        courseList = products
        adapter =  ItemRecyclerView(requireContext(),listener = object : ItemRecyclerView.OnClickListener {
            override fun onClick(position: Int) {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, CartFragment())
                    .addToBackStack("education")
                    .commit()
            }

            override fun onCartAdd(position: Int, item: Item) {
                modal.onCartAdd(position =position,item=item)
            }
        })
        binding.rvItems.adapter =adapter
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        adapter.setData(courseList)
    }
    private fun hitApi() {
        try {
            scope.launch(Dispatchers.Main) {
                val status = modal.getAllItems(
                    token = "Bearer " + session.getPassword().toString()
                )
                when (status) {
                    is Left -> {
                        courseList = status.a.data.productList
                        setAdapter(products = courseList)
                        onAutoComplete()
                    }
                    is Right -> {
                        //binding.spinKit.visibility = View.INVISIBLE
                        Snackbar.make(_binding!!.root, status.b.message.toString(), Snackbar.LENGTH_SHORT).show()
                        if(status.b.message.toString() == "Access Denied! Unauthorized User" || status.b.message.toString() == "token is missing") {
                            var session = Session(requireContext())
                            session.setLoggedIn(
                                loggedIn = false,
                                email = " ",
                                password = "",
                                username = "",
                                id = ""
                            )
                            requireActivity()
                                .supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.container, LoginFragment())
                                .commit()
                        }
                    }
                }
            }
        }catch (error:Exception) {
            //binding.spinKit.visibility = View.INVISIBLE
            Snackbar.make(_binding!!.root, "$error", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun onAutoComplete() {
        Log.d(" list ",courseList.size.toString())
        for (item in courseList) {
            strings.add(item.name)
            println(item.name)
        }
        listAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line,strings)
        binding.autocompleteFragment.threshold = 1
        binding.autocompleteFragment.setAdapter(listAdapter)
    }

}
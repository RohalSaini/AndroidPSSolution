package sanssys.solution.pssolutions.fragment.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentCartBinding
import sanssys.solution.pssolutions.fragment.BuyFragment
import sanssys.solution.pssolutions.fragment.cart.viewmodal.CartViewModal
import sanssys.solution.pssolutions.modal.Item
import sanssys.solution.pssolutions.modal.Product
import sanssys.solution.pssolutions.modal.ProductItem
import sanssys.solution.pssolutions.recyclerview.CartItem
import sanssys.solution.pssolutions.recyclerview.ItemRecyclerView
import sanssys.solution.pssolutions.util.Left
import sanssys.solution.pssolutions.util.Right
import sanssys.solution.pssolutions.util.Session

@AndroidEntryPoint
class CartFragment : Fragment() {
    private val scope = MainScope()
    val modal by viewModels<CartViewModal>()
    var courseList = ArrayList<Item>()
    var allList = ArrayList<Product>()
    private var _binding: FragmentCartBinding? = null
    lateinit var session: Session
    lateinit var adapter:CartItem
    private val binding get() = _binding!!

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = Session(this.requireContext())
        Log.e(" CART ",session.getYourCart().toString())
        if(session.getYourCart() != null)
            setAdapter(session.getYourCart()!!)
        // hitApi(type = "nothing")
        binding.cart.cart.visibility = View.INVISIBLE
        //binding.cart.cart.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_baseline_shopping_basket_24))
        //binding.cart.cart.setBackgroundResource(R.drawable.ic_baseline_shopping_basket_24)
        binding.cart.cart.setOnClickListener {
            var list = session.getYourCart()
            Log.e("list",list.toString())
            if(session.getYourCart()== null ) {
                Snackbar.make(it," Cart is Empty",Snackbar.LENGTH_LONG).show()
            } else {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, BuyFragment()).addToBackStack("education")
                transaction.commit()
            }
        }
        binding.cart.expandedMenu.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.orderPlace.setOnClickListener {
            println(courseList);
            if(session.getYourCart() == null ) {
                Snackbar.make(it," Cart is Empty",Snackbar.LENGTH_LONG).show()
            } else {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, BuyFragment()).addToBackStack("buy")
                transaction.commit()
            }
        }
    }
    fun onDelete(position: Int) {
        courseList.removeAt(position)
        binding.rvItems.adapter?.notifyItemRemoved(position)
    }

    private fun hitApi(type: String) {
        try {
            scope.launch(Dispatchers.Main) {
                val status = modal.login(
                    token = "Bearer " + session.getPassword().toString()
                )
                when (status) {
                    is Left -> {
                        adapter =  CartItem(requireContext(),listener = object : CartItem.OnClickListener {
                            override fun onClick(position: Int) {
                                // val transaction = requireActivity().supportFragmentManager.beginTransaction()
                                // transaction.replace(R.id.container, CourseFragment()).addToBackStack("course")
                                // transaction.commit()
                                /*
                                var cart = ArrayList<Item>()
                                var list = session.getList()
                                println("list is")
                                println(list?.toArray().toString())
                                if(list == null) {
                                    cart.add(courseList[position])
                                    // list.add(courseList[position])
                                    session.saveList(cart)
                                } else {
                                    list?.add(courseList[position])
                                    // list.add(courseList[position])
                                    if (list != null) {
                                        session.saveList(list)
                                     } */
                            }

                            override fun onAddQuantity(position: Int) {
                                //adapter.setData()
                                //courseList[position].quantity = courseList[position].quantity +1;
                                //adapter.setData(courseList,allList)
                                //session.saveList(courseList)
                                //adapter.notifyItemChanged(position)
                            }

                            override fun onMinusQuantity(position: Int) {
                               // var count = courseList[position].quantity -1
                                /*
                                if(count ==0) {
                                    //Snackbar.make(binding.root,"Items should not br less than 1 ",Snackbar.LENGTH_SHORT).show()
                                } else {
                                    //courseList[position].quantity = courseList[position].quantity -1
                                   // adapter.setData(courseList,allList)
                                    //session.saveList(courseList)
                                    //adapter.notifyItemChanged(position)
                                } */
                            }

                            override fun onCartAdd(position: Int) {
                                /*
                                var cart = ArrayList<Item>()
                                var list = session.getList()
                                println("list is")
                                println(list?.toArray().toString())
                                if(list == null) {
                                    cart.add(courseList[position])
                                    // list.add(courseList[position])
                                    session.saveList(cart)
                                } else {
                                    list?.add(courseList[position])
                                    // list.add(courseList[position])
                                    if (list != null) {
                                        session.saveList(list)
                                    }
                                } */
                            }

                            override fun OnDelete(position: Int) {
                                /*
                                courseList.removeAt(position)
                                //binding.rvItems.adapter?.notifyItemRemoved(position)
                                adapter.setData(courseList,allList)
                                adapter.notifyItemChanged(position)
                                session.saveList(courseList)

                                 */
                            }
                        })
                        binding.rvItems.adapter =adapter
                        binding.rvItems.setHasFixedSize(true)
                        binding.rvItems.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
                        //adapter.setData(courseList,allList)
                        if(courseList.size == 0) {
                            binding.cartListView.visibility = View.VISIBLE
                        }
                        var total = 0
                        for ( item in courseList) {
                            total += item.price.toInt()
                        }
                    }
                    is Right -> {
                        //binding.spinKit.visibility = View.INVISIBLE
                        Snackbar.make(_binding!!.root, status.b.message.toString(), Snackbar.LENGTH_SHORT).show()
                        Log.d("error",status.b.message.toString())
                    }
                }
            }
        }catch (error:Exception) {
           // binding.spinKit.visibility = View.INVISIBLE
            Snackbar.make(_binding!!.root, "$error", Snackbar.LENGTH_SHORT).show()
        }
    }
    private fun setAdapter(cart: ArrayList<Item>) {
        adapter = CartItem(requireContext(),listener = object : CartItem.OnClickListener {
            override fun onClick(position: Int) {
                // val transaction = requireActivity().supportFragmentManager.beginTransaction()
                // transaction.replace(R.id.container, CourseFragment()).addToBackStack("course")
                // transaction.commit()
                /*
                var cart = ArrayList<Item>()
                var list = session.getList()
                println("list is")
                println(list?.toArray().toString())
                if(list == null) {
                    cart.add(courseList[position])
                    // list.add(courseList[position])
                    session.saveList(cart)
                } else {
                    list?.add(courseList[position])
                    // list.add(courseList[position])
                    if (list != null) {
                        session.saveList(list)
                     } */
            }

            override fun onAddQuantity(position: Int) {
                //adapter.setData()
                //courseList[position].quantity = courseList[position].quantity +1;
                // adapter.setData(null,allList)
                // session.saveList(courseList)
                // adapter.notifyItemChanged(position)
                courseList[position].cart += 1
                adapter.setData(courseList)
                println("+++ADD+++++")
                println(courseList)
                println("+++++++++++")
                session.saveYourCart(courseList)
                adapter.notifyItemChanged(position)
            }

            override fun onMinusQuantity(position: Int) {
                var count = courseList[position].cart -1
                if(count ==0) {
                    Snackbar.make(binding.root,"Items should not br less than 1 ",Snackbar.LENGTH_SHORT).show()
                } else {
                    courseList[position].cart -= 1
                    println("+++MINUS+++++")
                    println(courseList)
                    println("+++++++++++")
                    adapter.setData(courseList)
                    session.saveYourCart(courseList)
                    adapter.notifyItemChanged(position)
                }
            }

            override fun onCartAdd(position: Int) {
                /*
                var cart = ArrayList<Item>()
                var list = session.getList()
                println("list is")
                println(list?.toArray().toString())
                if(list == null) {
                    cart.add(courseList[position])
                    // list.add(courseList[position])
                    session.saveList(cart)
                } else {
                    list?.add(courseList[position])
                    // list.add(courseList[position])
                    if (list != null) {
                        session.saveList(list)
                    }
                } */
            }

            override fun OnDelete(position: Int) {
                courseList.removeAt(position)
                binding.rvItems.adapter?.notifyItemRemoved(position)
                adapter.setData(courseList)
                adapter.notifyItemChanged(position)
                session.saveYourCart(courseList)
            }
        })
        if(_binding != null) {
            _binding!!.rvItems.adapter =adapter
            _binding!!.rvItems.setHasFixedSize(true)
            _binding!!.rvItems.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
            courseList = session.getYourCart()!!
            adapter.setData(session.getYourCart()!!)
        }
        /*
        courseList = products
        println(" list is $courseList")
        adapter =  ItemRecyclerView(requireContext(),listener = object : ItemRecyclerView.OnClickListener {
            override fun onClick(position: Int) {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, CartFragment())
                    .addToBackStack("education")
                    .commit()
            }

            override fun onCartAdd(position: Int,item: Item) {
                modal.onCartAdd(position =position,item=item)
                println(" OnCartItem is $item")
            }
        })
        if(_binding != null) {
            _binding!!.rvItems.adapter =adapter
            _binding!!.rvItems.setHasFixedSize(true)
            _binding!!.rvItems.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter.setData(courseList)
        } */
    }
}
package sanssys.solution.pssolutions

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import sanssys.solution.pssolutions.databinding.FragmentInvoiceBinding
import sanssys.solution.pssolutions.fragment.searchbybrand.viewmodal.SearchByBrand
import sanssys.solution.pssolutions.modal.OrderBodyType
import sanssys.solution.pssolutions.recyclerview.OrderView
import sanssys.solution.pssolutions.util.Left
import sanssys.solution.pssolutions.util.Right
import sanssys.solution.pssolutions.util.Session


@AndroidEntryPoint
class InvoiceFragment : Fragment() {
    private var _binding: FragmentInvoiceBinding? = null
    private val binding get() = _binding!!
    lateinit var session:Session
    val modal by viewModels<SearchByBrand>()
    lateinit var adapter: OrderView

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentInvoiceBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = Session(requireContext())
        binding.TextViewInvoice.appName.text ="Invoice"
        // Log.d("invoice",session.getOrder().toString())
        var data = session.getOrder();

        //binding.OrderNumber.text = data?.orderNumber.toString()
        //binding.time.text = data?.createdAt.toString()
        //binding.bill.text = "Rs" +data?.bill.toString()
        //binding.orderType.text = data!!.orderType
        var list= "";
        //var nameList = session.getNameList()
        //Log.d("invoice",session.getOrderList().toString())
       /*
        for (item in data.cart) {
            list+=item.name +" X "
            list+= item.add
            list+="\n"
        }
        binding.items.text = list
        */
        binding.TextViewInvoice.expandedMenu.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.TextViewInvoice.cart.visibility = View.INVISIBLE

        runBlocking {
            var response = modal.getOrders(token = "Bearer " + session.getPassword().toString(), OrderBodyType(email = session.getEmail().toString()))
            when(response) {
                is Left -> {
                    //hideSpinner()
                    adapter =  OrderView(requireContext())
                    binding.rvItems.adapter =adapter
                    binding.rvItems.setHasFixedSize(true)
                    binding.rvItems.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
                    //adapter.setData(courseList,allList)
                    if(response.a.data.orders.size == 0) {
                        binding.invoice.visibility = View.VISIBLE
                    } else {
                        adapter.setData(response.a.data.orders)
                    }
                 //Snackbar.make(_binding!!.root, response.a.toString(), Snackbar.LENGTH_SHORT).show()
                }
                is Right -> {
                    //hideSpinner()
                    // Snackbar.make(_binding!!.root, status.b.message.toString(), Snackbar.LENGTH_SHORT).show()
                    binding.invoice.visibility = View.VISIBLE
                    Snackbar.make(_binding!!.root, "no invoice", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}
package sanssys.solution.pssolutions.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentDashboardBinding
import sanssys.solution.pssolutions.InvoiceFragment
import sanssys.solution.pssolutions.adapter.BaseAdapterPinCode
import sanssys.solution.pssolutions.fragment.login.LoginFragment
import sanssys.solution.pssolutions.fragment.searchforproduct.SearchFragment
import sanssys.solution.pssolutions.util.Session


class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    lateinit var session: Session
    lateinit var spinnerAdapter: BaseAdapterPinCode

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = Session(this.requireContext())
        // spinner setting
        spinnerAdapter = BaseAdapterPinCode(requireContext(),session.getPinList())
        binding.pincodeContainer.containerDeliverLayer.findViewById<Spinner>(R.id.spinner_pin_code).adapter =spinnerAdapter
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
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container,InvoiceFragment()).addToBackStack("grocery")
                .commit()
        }

        // search for Product
        binding.pincodeContainer.textViewSearch.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SearchFragment())
                .addToBackStack("searchByName")
                .commit()
        }

        binding.grocery1.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, GroceryFragment())
                .addToBackStack("grocery")
                .commit()
        }
        binding.layputToolbar1.toolbar.findViewById<Button>(R.id.SignOut).text = "LogOut"
        binding.layputToolbar1.toolbar.findViewById<Button>(R.id.SignOut).setOnClickListener {
            logout()
        }
        backStack()
        binding.grocery.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container,GroceryFragment()).addToBackStack("grocery")
                .commit()
        }
        binding.job.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ComingSoon()).addToBackStack("job")
                .commit()
        }
        binding.consultant.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ComingSoon()).addToBackStack("consultant")
                    .commit()
        }
        binding.education.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ComingSoon())
                    .addToBackStack("education")
                    .commit()
        }
        binding.startup.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ComingSoon())
                    .addToBackStack("sartup")
                    .commit()
        }
        binding.medicine.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ComingSoon())
                    .addToBackStack("medicine")
                    .commit()
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun logout() {
        AlertDialog.Builder(this.requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to LogOut !!")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, i ->
                var session = Session(requireContext())
                session.setLoggedIn(
                    loggedIn = false,
                    email = " ",
                    password = "",
                    username = "",
                    id = ""
                )
                session.setPincode("")
                session.removeAll()
                session.resetCart()
                GoogleSignIn
                    .getClient(this.requireActivity(),
                        GoogleSignInOptions
                            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build()).signOut().addOnCompleteListener(this.requireActivity(), OnCompleteListener<Void?> {
                        requireActivity()
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, LoginFragment())
                            .addToBackStack("login")
                            .commit()
                    })
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
            }).show()
    }

    private fun backStack() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(requireActivity(), object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(requireActivity().supportFragmentManager.backStackEntryCount == 1) {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setCancelable(false)
                        builder.setMessage("Do you want to Exit?")
                        builder.setPositiveButton("Yes") { dialog, which -> //if user pressed "yes", then he is allowed to exit from application
                            requireActivity().finish()
                        }
                        builder.setNegativeButton("No") { dialog, which -> //if user select "No", just cancel this dialog and continue with app
                            dialog.cancel()
                        }.create().show()
                    } else {
                        requireActivity().supportFragmentManager.popBackStack()
                    }

                }
            }
            )
    }
}
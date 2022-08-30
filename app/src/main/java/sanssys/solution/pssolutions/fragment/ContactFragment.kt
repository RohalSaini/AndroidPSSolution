package sanssys.solution.pssolutions.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import sanssys.solution.pssolutions.databinding.FragmentContactBinding

class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ButtonQuery.setOnClickListener {
            if(binding.EditTextName.text.isBlank()) {
                Snackbar.make(it,"Enter your name",Snackbar.LENGTH_SHORT).show()
            }
            if(binding.EditTextEmail.text.isEmpty()) {
                Snackbar.make(it,"Enter your email address",Snackbar.LENGTH_SHORT).show()
            }

            if(binding.EditTextPhone.text.isEmpty()) {
                Snackbar.make(it,"Enter your phone number",Snackbar.LENGTH_SHORT).show()
            }
            if(binding.EditTextSubject.text.isEmpty()) {
                Snackbar.make(it,"Enter your query",Snackbar.LENGTH_SHORT).show()
            }
            Snackbar.make(it,"Your query has been submitted",Snackbar.LENGTH_LONG).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
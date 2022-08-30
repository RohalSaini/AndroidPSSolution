package sanssys.solution.pssolutions.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentJobBinding

class JobFragment : Fragment() {
    private var _binding: FragmentJobBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.axis.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ComingSoon()).addToBackStack("education")
                .commit()
        }
        binding.bpo.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ComingSoon()).addToBackStack("education")
                .commit()
        }
        binding.hdfc.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ComingSoon()).addToBackStack("education")
                .commit()
        }

        binding.icici.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ComingSoon()).addToBackStack("education")
                .commit()
        }
        binding.immigration.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ComingSoon()).addToBackStack("education")
                    .commit()
        }
        binding.pharma.setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ComingSoon()).addToBackStack("education")
                    .commit()
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentJobBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
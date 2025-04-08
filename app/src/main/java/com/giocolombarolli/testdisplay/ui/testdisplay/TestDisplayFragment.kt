package com.giocolombarolli.testdisplay.ui.testdisplay

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.giocolombarolli.testdisplay.R
import com.giocolombarolli.testdisplay.viewmodel.TestDisplayViewModel
import com.giocolombarolli.testdisplay.databinding.FragmentTestDisplayBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TestDisplayFragment : Fragment() {

    private var _binding: FragmentTestDisplayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TestDisplayViewModel by viewModels()

    private lateinit var txtDoneTest: String
    private lateinit var txtAllSquaresClicked: String
    private lateinit var txtOk: String
    private lateinit var txtTestFailed: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStrings()
        observeViewModel()
        setupGridLayout()
        setupCloseButton()
        startTimeoutCountdown()
    }

    private fun initStrings() {
        txtDoneTest = getString(R.string.txt_done_test)
        txtAllSquaresClicked = getString(R.string.txt_all_squares_clicked)
        txtOk = getString(R.string.txt_ok)
        txtTestFailed = getString(R.string.txt_test_failed)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Contagem de quadrados verdes
                launch {
                    viewModel.greenSquaresCount.collect { greenCount ->

                        if (greenCount == viewModel.totalSquares) {
                            binding.buttonClose.visibility = View.VISIBLE
                        }
                    }
                }
                // Observa o evento de timeout
                launch {
                    viewModel.onTimeout.collect {
                        Toast.makeText(requireContext(), txtTestFailed, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun setupGridLayout() {
        val gridLayout = binding.gridLayout
        val rows = 6
        val columns = 4
        val totalSquares = rows * columns

        viewModel.setTotalSquares(totalSquares)

        repeat(totalSquares) {
            val squareView = createSquareView()
            val layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = 0
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            gridLayout.addView(squareView, layoutParams)
        }
    }

    private fun createSquareView(): View {
        return View(requireContext()).apply {
            setBackgroundColor(Color.WHITE)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setOnClickListener {
                val currentColor = (background as ColorDrawable).color
                if (currentColor != Color.GREEN) {
                    setBackgroundColor(Color.GREEN)
                    viewModel.incrementGreenSquares()
                }
            }
        }
    }

    private fun setupCloseButton() {
        binding.buttonClose.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(txtDoneTest)
                .setMessage(txtAllSquaresClicked)
                .setPositiveButton(txtOk) { _, _ ->
                    findNavController().popBackStack()
                }
                .show()
        }
    }

    private fun startTimeoutCountdown() {
        lifecycleScope.launch {
            delay(10000)
            if (binding.buttonClose.visibility != View.VISIBLE) {
                viewModel.notifyTimeoutReached()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
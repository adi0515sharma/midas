package com.example.midas.Activitys

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.midas.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AboutUsFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        _binding?.webview?.settings?.javaScriptEnabled = true
        _binding?.webview?.settings?.allowContentAccess = true
        _binding?.webview?.settings?.domStorageEnabled = true
        _binding?.webview?.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                _binding?.progressBar?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                _binding?.progressBar?.visibility = View.GONE
            }

        }
        _binding?.webview?.loadUrl("https://en.wikipedia.org/wiki/Android_Studio")
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
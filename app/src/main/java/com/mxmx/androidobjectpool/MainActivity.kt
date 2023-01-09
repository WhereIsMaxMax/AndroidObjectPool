package com.mxmx.androidobjectpool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mxmx.androidobjectpool.databinding.ActivityMainBinding

const val TAG: String = "CUSTOM"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pool = MyObjectPool()
        var testObj: MyObjectPool.PoolContainer<SomeClass>?

        binding.buttonGet.setOnClickListener {

            testObj = pool.pull()

            binding.text.text = "Hold object id = ${testObj?.id} \n " +
                    "Object hex ${Integer.toHexString(testObj?.obj?.hashCode()!!)}"
        }

        binding.buttonUnlink.setOnClickListener {
            testObj = null
        }

        binding.buttonGc.setOnClickListener {
            Runtime.getRuntime().gc()
        }
    }
}
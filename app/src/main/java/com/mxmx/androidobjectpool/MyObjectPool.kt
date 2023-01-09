package com.mxmx.androidobjectpool

import android.util.Log
import java.util.*

class MyObjectPool {

    private var objects: Stack<PoolContainer<SomeClass>> = Stack()

    init {
//        TODO: implement Fabric, create new items on-demand, restrictions on stack size.
        for (i in 0..9) {
            val persistableObject = SomeClass(i, i.toString())
            objects.push(PoolContainer(i, persistableObject))
            Log.d(TAG, "id = $i, hash = $persistableObject")
        }
        Log.d(TAG, "created ${objects.size} objects")
    }

    @Synchronized
    fun pull(): PoolContainer<SomeClass>? {
        return objects.pop()
    }

    @Synchronized
    private fun recycle(id: Int, obj: SomeClass) {
        objects.push(PoolContainer(id, obj))

        Log.d(TAG, "Item recycled, objects size ${objects.size}")

        objects.forEach {
            Log.d(TAG, "Objects: id ${it.id}, hash ${it.obj}")
        }
    }

    inner class PoolContainer<T : Any>(val id: Int, val obj: T) {
        protected fun finalize() {
            Log.d(TAG, "finalize id-$id container-$this object hash-$obj")
            recycle(id, obj as SomeClass)
        }
    }
}
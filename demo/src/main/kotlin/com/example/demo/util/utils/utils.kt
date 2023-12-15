package com.example.demo.util.utils
import com.fasterxml.uuid.Generators

class utils {
    companion object {
        fun newUUID(): String {
            return Generators.timeBasedGenerator().generate().toString()
        }
    }
}
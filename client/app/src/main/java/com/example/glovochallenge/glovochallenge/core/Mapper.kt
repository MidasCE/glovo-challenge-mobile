package com.example.glovochallenge.glovochallenge.core

interface Mapper<in I, out O> {
    fun map(value: I): O
}

package com.example.glovochallenge.glovochallenge.core

interface Mapper<I, O> {
    fun map(value: I): O
}

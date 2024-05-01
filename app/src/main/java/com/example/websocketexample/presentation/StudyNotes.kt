package com.example.websocketexample.presentation

/**
 * LiveData vs Flow
 * Study notes from the article:
 * https://bladecoder.medium.com/kotlins-flow-in-viewmodels-it-s-complicated-556b472e281a
 *
 * LiveData is lifecycle aware, Flow is not
 */

/**
 * Этот код корректно отработает только с холодным flow. С SharedFlow или StateFlow нет
 *
 * viewLifecycleOwner.lifecycleScope.launchWhenStarted {
 *     viewModel.result.collect { data ->
 *         displayResult(data)
 *     }
 * }
 */

/**
 * contrary to the LiveData coroutine builder,
 * the upstream flow will always be restarted automatically by the sharing coroutine
 * when a new observer subscribes after that inactivity period, even if it reached its end during the previous collection.
 */

/**
 * because StateFlow does not support versioning,
 * the trigger will re-emit the latest value when the flow collection restarts.
 * This happens every time the Activity/Fragment becomes visible again after being invisible for more than 5 seconds.
 */

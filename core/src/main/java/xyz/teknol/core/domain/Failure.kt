package xyz.teknol.core.domain

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(val message: String?) {

    object NetworkConnection : Failure("Connection Error")
    object ServerError : Failure("Server Error")
    object DataBaseError : Failure("No Data Available")

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(message: String?) : Failure(message)
}

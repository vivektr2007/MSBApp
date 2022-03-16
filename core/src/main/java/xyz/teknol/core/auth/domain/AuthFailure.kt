package xyz.teknol.core.auth.domain

import xyz.teknol.core.domain.Failure

class AuthFailure {
    class InvalidCredentials(message:String?) : Failure.FeatureFailure(message)
    class UnknownError(message:String?) : Failure.FeatureFailure(message)
}
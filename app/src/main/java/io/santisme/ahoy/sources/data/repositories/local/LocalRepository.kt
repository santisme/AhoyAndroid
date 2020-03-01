package io.santisme.ahoy.sources.data.repositories.local

class LocalRepository {
    companion object {
        lateinit var factory: LocalRepository
    }

    init {
        factory = this
    }
//    public static val factory = LocalRepository()
}

//// Public class to get a repository
//final class Repository {
//    static let factory = LocalRepository()
//}
//
//final class LocalRepository {
//}
//
//extension LocalRepository: SignUpRepositoryProtocol {
//    func signUp(signUpModel: SignUpModel, completion: @escaping (Result<SignUpResponse, Error>) -> ()) {
//        let apiSession = APISession()
//        let signUpRepository = SignUpRepository(apiSession: apiSession)
//
//        signUpRepository.signUp(signUpModel: signUpModel) {
//            completion($0)
//        }
//    }
//}
//
//extension LocalRepository: SignInRepositoryProtocol {
//    func signIn(signInModel: SignInModel, completion: @escaping (Result<SignInResponse, Error>) -> ()) {
//        let apiSession = APISession()
//        let signInRepository = SignInRepository(apiSession: apiSession)
//
//        signInRepository.signIn(signInModel: signInModel) {
//            completion($0)
//        }
//    }
//}
//
//extension LocalRepository: PasswordRecoveryRepositoryProtocol {
//    func resetPassword(passwordRecoveryModel: PasswordRecoveryModel, completion: @escaping (Result<PasswordRecoveryResponse, Error>) -> ()) {
//        let apiSession = APISession()
//        let passwordRecoveryRepository = PasswordRecoveryRepository(apiSession: apiSession)
//
//        passwordRecoveryRepository.resetPassword(passwordRecoveryModel: passwordRecoveryModel) {
//            completion($0)
//        }
//    }
//}
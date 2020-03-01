package io.santisme.ahoy

import io.santisme.ahoy.domain.models.SignInModel
import io.santisme.ahoy.sources.login.signin.SignInModelView
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var dummySignInModel: SignInModel
    private lateinit var wrongSignInModel: SignInModel
    private lateinit var signInModelView: SignInModelView
//    private lateinit var view: SignInModelViewProtocol

    @Before
    fun setUp() {
        dummySignInModel = SignInModel(username = "dummyUsername", password = "1234567890")
        wrongSignInModel = SignInModel(username = "", password = "123")
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testIfSignInModel_isNotNull() {
        assertNotNull(dummySignInModel)
        assertNotNull(wrongSignInModel)
    }

    @Test
    fun testIfSignInModelFieldsAreValid() {
//        switch sigInModelView?.signInModelIsValid(signInModel: dummySignInModel) {

//        }
        //        switch signInModelView?.signInModelIsValid(signInModel: dummySignInModel!) {
//            case .success(let val):
//            XCTAssertTrue(val)
//            case .failure(let error):
//            switch error {
//                case .usernameEmpty:
//                XCTAssert(false, "Username is empty")
//                case .passwordToShort:
//                XCTAssert(false, "Password too short, length must be 10 at least")
//            }
//            case .none: break

    }
//    func testIfSignInModelFieldsAreValid() {
//        switch signInModelView?.signInModelIsValid(signInModel: dummySignInModel!) {
//            case .success(let val):
//            XCTAssertTrue(val)
//            case .failure(let error):
//            switch error {
//                case .usernameEmpty:
//                XCTAssert(false, "Username is empty")
//                case .passwordToShort:
//                XCTAssert(false, "Password too short, length must be 10 at least")
//            }
//            case .none: break
//
//        }
//    }
//
//    func testIfSignInModelFieldsAreInValid() {
//        let result = signInModelView?.signInModelIsValid(signInModel: wrongSignInModel!)
//        switch result {
//            case .success(let val):
//            XCTAssertFalse(val)
//            case .failure(let error):
//            switch error {
//                case .usernameEmpty:
//                XCTAssert(true, "Username is empty")
//                case .passwordToShort:
//                XCTAssert(true, "Password too short, length must be 10 at least")
//            }
//            case .none: break
//
//        }
//    }
//
//    func testIfSignInModelUsernameIsValid() {
//        switch signInModelView?.usernameIsValid(username: dummySignInModel?.username ?? "") {
//            case .success(let val):
//            XCTAssertTrue(val)
//            case .failure(let error):
//            switch error {
//                case .usernameEmpty:
//                XCTAssert(false, "Username is empty")
//                default: break
//            }
//
//            case .none:
//            break
//        }
//    }
//
//    func testIfSignInModelUsernameIsInValid() {
//        switch signInModelView?.usernameIsValid(username: wrongSignInModel?.username ?? "") {
//            case .success(let val):
//            XCTAssertFalse(val)
//            case .failure(let error):
//            switch error {
//                case .usernameEmpty:
//                XCTAssert(true, "Username is empty")
//                default: break
//            }
//
//            case .none:
//            break
//        }
//    }
//
//    func testIfSignInModelPasswordIsValid() {
//        switch signInModelView?.passwordIsValid(password: dummySignInModel?.password ?? "") {
//            case .success(let val):
//            XCTAssertTrue(val)
//            case .failure(let error):
//            switch error {
//                case .usernameEmpty:
//                XCTAssert(false, "Password too short, length must be 10 at least")
//                default: break
//            }
//
//            case .none:
//            break
//        }
//    }
//
//    func testIfSignInModelPasswordIsInValid() {
//        switch signInModelView?.passwordIsValid(password: wrongSignInModel?.password ?? "") {
//            case .success(let val):
//            XCTAssertFalse(val)
//            case .failure(let error):
//            switch error {
//                case .usernameEmpty:
//                XCTAssert(true, "Password too short, length must be 10 at least")
//                default: break
//            }
//
//            case .none:
//            break
//        }
//    }
}

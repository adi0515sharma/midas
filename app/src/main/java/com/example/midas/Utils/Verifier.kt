package com.example.midas.Utils

class Verifier {

    companion object{

        fun isPhoneNumberValid(phoneNumber : String) : Boolean{
            if (phoneNumber.length == 10) {
                // Phone number is valid
                return true
            }
            return false;
        }
        fun isPasswordValid(password : String) : Boolean{
            if (password.length==4) {
                return true
            }
            return false;
        }
        fun isEmailCorrect(email : String) : Boolean{

            val regex = Regex("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
            val isMatched = regex.matches(email)
            if (isMatched) {
                return true
            }
            return false;
        }

        fun isUserNameCorrect(userName : String)  :Boolean{
            val regex = Regex("^[a-zA-Z ]+\$")
            val isMatched = regex.matches(userName)
            if (isMatched) {
                return true
            }
            return false;
        }
    }
}